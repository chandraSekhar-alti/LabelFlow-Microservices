package com.labelflow.labelchange.auth.security.jwt;

public record JwtToken(String accessToken, Long expiresIn) {
}
