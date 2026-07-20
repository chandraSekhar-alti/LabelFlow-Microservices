package com.labelflow.labelchange.auth.repository;

import com.labelflow.labelchange.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);

    @Query("""
    SELECT u
    FROM User u
    LEFT JOIN FETCH u.userRoles ur
    LEFT JOIN FETCH ur.role
    WHERE UPPER(u.email) = UPPER(:email)
    """)
    Optional<User> findByEmailWithRoles(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);
}
