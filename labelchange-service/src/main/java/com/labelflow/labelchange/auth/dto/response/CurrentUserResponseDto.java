package com.labelflow.labelchange.auth.dto.response;


import com.labelflow.labelchange.auth.enums.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class CurrentUserResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<RoleType> roles;
}
