package com.labelflow.labelchange.auth.security;

import com.labelflow.labelchange.auth.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    public static UserPrincipal from(User user){
        Set<GrantedAuthority> authorities =
                user.getUserRoles()
                        .stream()
                        .map(userRole -> new SimpleGrantedAuthority(
                                userRole.getRole().getRoleName().name()
                        ))
                        .collect(Collectors.toSet());

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getActive(),
                user.getAccountLocked(),
                authorities
        );
    }

    private final UUID id;

    private final String email;

    private final String password;

    private final boolean active;

    private final boolean accountLocked;

    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
