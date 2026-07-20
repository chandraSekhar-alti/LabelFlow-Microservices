package com.labelflow.labelchange.labelchange.dto.request;

import com.labelflow.labelchange.labelchange.enums.ChangeCategory;
import com.labelflow.labelchange.labelchange.enums.SignalType;
import com.labelflow.labelchange.labelchange.enums.TriggerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
public class CreateLabelChangeRequest {

    @NotNull
    private UUID productFamilyId;

    @NotEmpty
    private Set<UUID> selectedProductIds;

    @NotNull
    private Set<Short> selectedCountryIds;

    @NotEmpty
    private Set<UUID> registrationIds;

    @NotNull
    private LocalDate triggerDate;

    private LocalDate startDate;

    @NotNull
    private ChangeCategory changeCategory;

    @NotNull
    private TriggerType triggerType;

    @NotNull
    private SignalType signal;

    @NotBlank
    @Size(max = 500)
    private String shortDescription;

    @Size(max = 10000)
    private String description;

}
