package com.labelflow.labelchange.auth.service.impl;

import com.labelflow.labelchange.auth.dto.request.LoginRequestDto;
import com.labelflow.labelchange.auth.dto.request.RegisterRequestDto;
import com.labelflow.labelchange.auth.dto.response.CurrentUserResponseDto;
import com.labelflow.labelchange.auth.dto.response.LoginResponseDto;
import com.labelflow.labelchange.auth.dto.response.RegisterResponseDto;
import com.labelflow.labelchange.auth.entity.Role;
import com.labelflow.labelchange.auth.entity.User;
import com.labelflow.labelchange.auth.entity.UserRole;
import com.labelflow.labelchange.auth.enums.RoleType;
import com.labelflow.labelchange.auth.repository.RoleRepository;
import com.labelflow.labelchange.auth.repository.UserRepository;
import com.labelflow.labelchange.auth.security.UserPrincipal;
import com.labelflow.labelchange.auth.security.jwt.JwtProperties;
import com.labelflow.labelchange.auth.security.jwt.JwtService;
import com.labelflow.labelchange.auth.security.jwt.JwtToken;
import com.labelflow.labelchange.auth.service.AuthService;
import com.labelflow.labelchange.common.constants.AuthenticationConstants;
import com.labelflow.labelchange.common.constants.SecurityConstants;
import com.labelflow.labelchange.common.exception.AccountLockedException;
import com.labelflow.labelchange.common.exception.DuplicateResourceException;
import com.labelflow.labelchange.common.exception.InvalidCredentialsException;
import com.labelflow.labelchange.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (user.isAccountLocked()) {
            throw new AccountLockedException("Your account has been locked. Please contact the administrator.");
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException exception) {
            user.increaseFailedLoginAttempts();

            if (user.getFailedLoginAttempts() >= AuthenticationConstants.MAX_FAILED_LOGIN_ATTEMPTS) {
                user.lockAccount();
            }
            throw new InvalidCredentialsException("Invalid email or password");
        }


        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        user.resetFailedLoginAttempts();
        user.updateLastLogin();

        JwtToken jwtToken = jwtService.generateToken(principal);

        return LoginResponseDto.builder()
                .accessToken(jwtToken.accessToken())
                .expiresIn(jwtToken.expiresIn())
                .tokenType(SecurityConstants.TOKEN_TYPE)
                .build();

    }

    @Override
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {

        validateDuplicateEmail(request.getEmail());

        Set<Role> roles = loadRoles(request.getRoles());

        User user = createUser(request);

        assignRoles(user, roles);

        User savedUser = userRepository.save(user);

        return buildRegisterResponse(savedUser);
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException("User already exists with email: " + email);
        }
    }

    private Set<Role> loadRoles(Set<RoleType> roleTypes) {
        return roleTypes.stream()
                .map(roleType ->
                        roleRepository.findByRoleName(roleType).orElseThrow(() ->
                                new ResourceNotFoundException("Role not found: " + roleType)))
                .collect(Collectors.toSet());
    }

    private User createUser(RegisterRequestDto request) {
        return User.create(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
    }

    private void assignRoles(User user, Set<Role> roles) {
        roles.forEach(user::assignRole);
    }

    private RegisterResponseDto buildRegisterResponse(User user) {

        return RegisterResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(
                        user.getUserRoles()
                                .stream()
                                .map(userRole -> userRole.getRole().getRoleName())
                                .collect(Collectors.toSet())
                )
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CurrentUserResponseDto getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        User user = userRepository.findByEmailIgnoreCase(principal.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return buildCurrentUserResponse(user);
    }


    private CurrentUserResponseDto buildCurrentUserResponse(User user) {

        return CurrentUserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(
                        user.getUserRoles()
                                .stream()
                                .map(userRole -> userRole.getRole().getRoleName())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
