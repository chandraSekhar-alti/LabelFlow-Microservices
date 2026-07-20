package com.labelflow.labelchange.auth.entity;

import com.labelflow.labelchange.auth.enums.RoleType;
import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseUuidEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "account_locked", nullable = false)
    @Builder.Default
    private Boolean accountLocked = false;

    @Column(name = "failed_login_attempts", nullable = false)
    @Builder.Default
    private Integer failedLoginAttempts = 0;

    @Column(name = "last_login")
    private LocalDateTime lastLoginAt;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> userRoles = new HashSet<>();

    //Util methods
    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }

    public boolean isAccountLocked() {
        return Boolean.TRUE.equals(accountLocked);
    }

    public void increaseFailedLoginAttempts() {
        this.failedLoginAttempts++;
    }

    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
    }

    public void lockAccount() {
        this.accountLocked = true;
    }

    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public static User create(String firstName, String lastName, String email, String passwordHash) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passwordHash(passwordHash)
                .build();
    }

    public void assignRole(Role role) {

        boolean alreadyAssigned = userRoles.stream().anyMatch(userRole -> userRole.getRole().equals(role));

        if (alreadyAssigned) {
            return;
        }

        UserRole userRole = new UserRole(this, role);

        userRoles.add(userRole);

        role.getUserRoles().add(userRole);
    }

    public void removeRole(Role role) {

        userRoles.removeIf(userRole -> {

            boolean match = userRole.getRole().equals(role);

            if (match) {
                role.getUserRoles().remove(userRole);
            }

            return match;
        });
    }

    public boolean hasRole(RoleType roleType) {

        return userRoles.stream()
                .map(UserRole::getRole)
                .anyMatch(role -> role.getRoleName() == roleType);
    }
}
