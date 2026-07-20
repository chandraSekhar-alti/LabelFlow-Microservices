package com.labelflow.labelchange.auth.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labelflow.labelchange.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse error = ErrorResponse.builder()
                .success(false)
                .message("Authentication is required to access this resource.")
                .validationErrors(null)
                .timestamp(LocalDateTime.now())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        objectMapper.writeValue(response.getOutputStream(), error);
    }
}
