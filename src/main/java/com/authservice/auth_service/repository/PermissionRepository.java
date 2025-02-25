package com.authservice.auth_service.repository;

import com.authservice.auth_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
