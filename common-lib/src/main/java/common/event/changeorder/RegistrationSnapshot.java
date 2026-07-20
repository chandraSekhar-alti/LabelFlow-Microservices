package common.event.changeorder;

import java.util.UUID;

public record RegistrationSnapshot(
    UUID registrationId,
    String registrationNumber,
    String countryCode,
    String countryName,
    UUID productFamilyId,
    String productFamilyName) {}
