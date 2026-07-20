package com.labelflow.labelchange.auth.repository;

import com.labelflow.labelchange.auth.entity.Role;
import com.labelflow.labelchange.auth.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Short> {

    Optional<Role> findByRoleName(RoleType roleName);

    boolean existsByRoleName(RoleType roleName);
}
