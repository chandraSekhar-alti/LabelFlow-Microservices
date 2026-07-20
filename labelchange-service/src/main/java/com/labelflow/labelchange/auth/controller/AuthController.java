package com.labelflow.labelchange.auth.controller;

import com.labelflow.labelchange.auth.dto.request.LoginRequestDto;
import com.labelflow.labelchange.auth.dto.request.RegisterRequestDto;
import com.labelflow.labelchange.auth.dto.response.CurrentUserResponseDto;
import com.labelflow.labelchange.auth.dto.response.LoginResponseDto;
import com.labelflow.labelchange.auth.dto.response.RegisterResponseDto;
import com.labelflow.labelchange.auth.service.AuthService;
import com.labelflow.labelchange.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto loginResponse = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponseDto>builder()
                        .success(true)
                        .message("Login successful")
                        .data(loginResponse)
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto request) {

        RegisterResponseDto response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RegisterResponseDto>builder()
                                .success(true)
                                .message("User registered successfully")
                                .data(response)
                                .build()
                );
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CurrentUserResponseDto>> getCurrentUser() {

        CurrentUserResponseDto response = authService.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.<CurrentUserResponseDto>builder()
                        .success(true)
                        .message("User details fetched successfully")
                        .data(response)
                        .build()
        );
    }

}
