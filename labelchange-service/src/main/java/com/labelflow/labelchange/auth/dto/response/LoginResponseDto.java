package com.labelflow.labelchange.auth.dto.response;

import com.labelflow.labelchange.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class LoginResponseDto {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;

    private String firstName;
    private String lastName;
    private String email;

    private Set<Role> roles;

}
