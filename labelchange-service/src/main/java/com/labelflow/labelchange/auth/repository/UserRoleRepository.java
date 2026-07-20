package com.labelflow.labelchange.auth.repository;

import com.labelflow.labelchange.auth.entity.UserRole;
import com.labelflow.labelchange.auth.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
