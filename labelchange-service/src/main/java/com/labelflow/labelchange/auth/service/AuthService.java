package com.labelflow.labelchange.auth.service;

import com.labelflow.labelchange.auth.dto.request.LoginRequestDto;
import com.labelflow.labelchange.auth.dto.request.RegisterRequestDto;
import com.labelflow.labelchange.auth.dto.response.CurrentUserResponseDto;
import com.labelflow.labelchange.auth.dto.response.LoginResponseDto;
import com.labelflow.labelchange.auth.dto.response.RegisterResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);

    RegisterResponseDto register(RegisterRequestDto request);

    CurrentUserResponseDto getCurrentUser();
}
