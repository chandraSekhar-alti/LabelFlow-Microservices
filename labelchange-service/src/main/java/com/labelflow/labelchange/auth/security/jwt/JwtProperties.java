package com.labelflow.labelchange.auth.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String secret;

    private Long accessTokenExpiration;

    private String issuer;
}
