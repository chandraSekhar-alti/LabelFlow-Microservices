package com.labelflow.labelchange.labelchange.service.impl;

import com.labelflow.labelchange.auth.entity.User;
import com.labelflow.labelchange.auth.enums.RoleType;
import com.labelflow.labelchange.auth.repository.UserRepository;
import com.labelflow.labelchange.common.exception.ForbiddenException;
import com.labelflow.labelchange.common.exception.ResourceNotFoundException;
import com.labelflow.labelchange.common.exception.UnauthorizedException;
import com.labelflow.labelchange.sequence.BusinessCodeGenerator;
import com.labelflow.labelchange.labelchange.dto.request.CreateLabelChangeRequest;
import com.labelflow.labelchange.labelchange.dto.request.TrackingDecisionRequest;
import com.labelflow.labelchange.labelchange.dto.response.CreateLabelChangeResponse;
import com.labelflow.labelchange.labelchange.dto.response.TrackingDecisionResponse;
import com.labelflow.labelchange.labelchange.entity.LabelChange;
import com.labelflow.labelchange.labelchange.entity.LabelChangeTrackingDecision;
import com.labelflow.labelchange.labelchange.enums.ChangeType;
import com.labelflow.labelchange.labelchange.enums.ProcessImpacted;
import com.labelflow.labelchange.labelchange.enums.TrackingDecision;
import com.labelflow.labelchange.labelchange.enums.TriggerType;
import com.labelflow.labelchange.labelchange.repository.LabelChangeRepository;
import com.labelflow.labelchange.labelchange.repository.LabelChangeTrackingDecisionRepository;
import com.labelflow.labelchange.labelchange.service.LabelChangeService;
import com.labelflow.labelchange.master.country.entity.Country;
import com.labelflow.labelchange.master.country.repository.CountryRepository;
import com.labelflow.labelchange.master.product.entity.Product;
import com.labelflow.labelchange.master.product.entity.ProductFamily;
import com.labelflow.labelchange.master.product.repository.ProductFamilyRepository;
import com.labelflow.labelchange.master.product.repository.ProductRepository;
import com.labelflow.labelchange.master.registration.entity.Registration;
import com.labelflow.labelchange.master.registration.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.labelflow.labelchange.labelchange.enums.TrackingDecision.CLOSE;
import static com.labelflow.labelchange.labelchange.enums.TrackingDecision.PROCEED;


@Service
@RequiredArgsConstructor
public class LabelChangeServiceImpl implements LabelChangeService {

    private final LabelChangeRepository labelChangeRepository;
    private final ProductFamilyRepository productFamilyRepository;
    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final LabelChangeTrackingDecisionRepository labelChangeTrackingDecisionRepository;
    private final BusinessCodeGenerator businessCodeGenerator;

    @Override
    @Transactional
    public CreateLabelChangeResponse createLabelChange(CreateLabelChangeRequest request) {

        User currentUser = getCurrentUser();

        validateRequest(request, currentUser);

        ProductFamily productFamily = loadProductFamily(request.getProductFamilyId());

        Set<Product> products = loadProducts(request.getSelectedProductIds());

        Set<Country> countries = loadCountries(request.getSelectedCountryIds());

        Set<Registration> registrations = loadRegistrations(request.getRegistrationIds());

        validateSelections(productFamily, products, countries, registrations);

        LabelChange labelChange = buildLabelChange(request, currentUser, productFamily);

        addProducts(labelChange, products);

        addCountries(labelChange, countries);

        addRegistrations(labelChange, registrations);

        LabelChange savedLabelChange = labelChangeRepository.save(labelChange);

        return buildResponse(savedLabelChange);
    }

    @Override
    @Transactional
    public TrackingDecisionResponse processTrackingDecision(UUID labelChangeId, TrackingDecisionRequest request) {

        User currentUser = getCurrentUser();

        LabelChange labelChange = labelChangeRepository.findById(labelChangeId)
                .orElseThrow(() -> new ResourceNotFoundException("Label Change not found."));

        switch (request.trackingDecision()) {

            case PROCEED -> labelChange.proceed();

            case CLOSE -> labelChange.close();

            default -> throw new UnauthorizedException("Unsupported Tracking Decision.");
        }

        LabelChangeTrackingDecision trackingDecision = LabelChangeTrackingDecision.create(
                labelChange,
                request.trackingDecision(),
                request.comments(),
                currentUser
        );

        labelChangeTrackingDecisionRepository.save(trackingDecision);

        labelChangeRepository.save(labelChange);

        return TrackingDecisionResponse.builder()
                .labelChangeId(labelChange.getId())
                .labelChangeNumber(labelChange.getLabelChangeNumber())
                .status(labelChange.getStatus())
                .build();
    }

    //Load Product Family
    private ProductFamily loadProductFamily(UUID productFamilyId) {

        return productFamilyRepository.findById(productFamilyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Family not found : " + productFamilyId));

    }

    //Load Products
    private Set<Product> loadProducts(Set<UUID> productIds) {

        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new ResourceNotFoundException("One or more Products do not exist.");
        }

        return new HashSet<>(products);
    }

    //Load Countries
    private Set<Country> loadCountries(Set<Short> countryIds) {

        List<Country> countries = countryRepository.findAllById(countryIds);

        if (countries.size() != countryIds.size()) {
            throw new ResourceNotFoundException("One or more Countries do not exist.");
        }

        return new HashSet<>(countries);
    }

    //Load Registrations
    private Set<Registration> loadRegistrations(Set<UUID> registrationIds) {

        List<Registration> registrations = registrationRepository.findAllById(registrationIds);

        if (registrations.size() != registrationIds.size()) {
            throw new ResourceNotFoundException("One or more Registrations do not exist.");
        }

        return new HashSet<>(registrations);
    }

    private void validateRequest(CreateLabelChangeRequest request, User user) {

        validateTriggerType(request.getTriggerType(), user);
        validateStartDate(request.getStartDate(), user);
    }

    private void validateSelections(ProductFamily productFamily, Set<Product> products, Set<Country> countries, Set<Registration> registrations) {
        validateProducts(productFamily, products);
        validateRegistrations(products, countries, registrations);
    }

    private void validateProducts(ProductFamily productFamily, Set<Product> products) {

        for (Product product : products) {
            if (!product.getProductFamily().getId().equals(productFamily.getId())) {

                throw new ForbiddenException("Product " + product.getProductName() + " does not belong to Product Family " + productFamily.getFamilyName());
            }
        }
    }

    private void validateRegistrations(Set<Product> selectedProducts, Set<Country> selectedCountries, Set<Registration> registrations) {

        Set<UUID> productIds = selectedProducts.stream().map(Product::getId).collect(Collectors.toSet());
        Set<Short> countryIds = selectedCountries.stream().map(Country::getId).collect(Collectors.toSet());

        for (Registration registration : registrations) {
            if (!productIds.contains(registration.getProduct().getId())) {
                throw new ForbiddenException("Registration " + registration.getRegistrationNumber() + " does not belong to any selected Product.");
            }

            if (!countryIds.contains(registration.getCountry().getId())) {
                throw new IllegalArgumentException("Registration " + registration.getRegistrationNumber() + " does not belong to any selected Country.");
            }
        }
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));
    }

    private void validateStartDate(LocalDate startDate, User user) {

        if (user.hasRole(RoleType.ROLE_RA) && startDate != null) {
            throw new UnauthorizedException("RA users cannot provide a Start Date.");
        }
    }

    private void validateTriggerType(TriggerType triggerType, User user) {

        if (user.hasRole(RoleType.ROLE_RA)) {
            if (triggerType != TriggerType.TECHNICAL_ARTWORK_CHANGES
                    && triggerType != TriggerType.OTHER
                    && triggerType != TriggerType.HA_REQUEST
                    && triggerType != TriggerType.CMS_CHANGES) {

                throw new UnauthorizedException("Regulatory Affairs users can only select trigger types: " + "TECHNICAL_ARTWORK_CHANGES, OTHER, HA_REQUEST or CMS_CHANGES.");
            }
            return;
        }

        if (user.hasRole(RoleType.ROLE_GL) && triggerType != TriggerType.PROJECT_TEAM_DECISION && triggerType != TriggerType.SAFETY_COMMITTEE_DECISION) {
            throw new UnauthorizedException("Global Labeling users can only select trigger types: " + "PROJECT_TEAM_DECISION or SAFETY_COMMITTEE_DECISION.");
        }

    }

    private LabelChange buildLabelChange(CreateLabelChangeRequest request, User user, ProductFamily productFamily) {

        return LabelChange.create(
                businessCodeGenerator.generateLabelChangeNumber(),
                productFamily,
                request.getTriggerDate(),
                request.getStartDate(),
                determineChangeType(user),
                determineProcessImpacted(user),
                request.getChangeCategory(),
                request.getTriggerType(),
                request.getSignal(),
                request.getShortDescription(),
                request.getDescription()
        );
    }

    private void addProducts(LabelChange labelChange, Set<Product> products) {
        products.forEach(labelChange::addProduct);
    }

    private void addCountries(LabelChange labelChange, Set<Country> countries) {
        countries.forEach(labelChange::addCountry);
    }

    private void addRegistrations(LabelChange labelChange, Set<Registration> registrations) {
        registrations.forEach(labelChange::addRegistration);
    }

    private ProcessImpacted determineProcessImpacted(User user) {
        if (user.hasRole(RoleType.ROLE_GL)) {
            return ProcessImpacted.GLOBAL;
        }
        return ProcessImpacted.LOCAL;
    }

    private ChangeType determineChangeType(User user) {

        if (user.hasRole(RoleType.ROLE_GL)) {
            return ChangeType.CCDS;
        }

        return ChangeType.LOCAL_CHANGE;
    }

    private CreateLabelChangeResponse buildResponse(LabelChange labelChange) {
        return new CreateLabelChangeResponse(
                labelChange.getId(),
                labelChange.getLabelChangeNumber(),
                labelChange.getStatus(),
                labelChange.getTotalRegistrations()
        );
    }

}
