package com.authservice.auth_service.service;

import com.authservice.auth_service.entity.Permission;
import com.authservice.auth_service.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Object create(Permission permission) {
        try {
            permissionRepository.save(permission);
            return permission;
        } catch (Exception e) {
            System.err.println("Error creating permission: " + e.getMessage());
            throw new IllegalArgumentException("Error registering permission. Please try again later.");
        }
    }

    public List<Permission> view() {
        try {
            return permissionRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error retrieving permissions: " + e.getMessage());
            throw new IllegalArgumentException("Error retrieving permissions.");
        }
    }
}
