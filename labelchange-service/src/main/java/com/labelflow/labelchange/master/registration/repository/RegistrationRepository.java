package com.labelflow.labelchange.master.registration.repository;

import com.labelflow.labelchange.master.registration.entity.Registration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    Optional<Registration> findByRegistrationCode(String registrationCode);

    boolean existsByRegistrationCode(String registrationCode);

    List<Registration> findByProductId(UUID productId);

    List<Registration> findByCountryId(Short countryId);

    List<Registration> findByProductIdInAndCountryIdIn(
            Collection<UUID> productIds,
            Collection<Short> countryIds
    );

    List<Registration> findByActiveTrueOrderByTradeNameAsc();
}