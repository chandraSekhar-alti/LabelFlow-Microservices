package com.labelflow.labelchange.auth.security;

import com.labelflow.labelchange.auth.entity.User;
import com.labelflow.labelchange.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmailWithRoles(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid email or password"));

        return UserPrincipal.from(user);
    }
}