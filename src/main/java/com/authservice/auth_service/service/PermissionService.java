package com.authservice.auth_service.service;

import com.authservice.auth_service.entity.Permission;
import com.authservice.auth_service.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            System.err.println("Error al crear permiso: " + e.getMessage());
            throw new IllegalArgumentException("Error al registrar permiso. Inténtalo de nuevo más tarde.");
        }
    }

    public List<Permission> view() {
        try {
            return permissionRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener los permisos: " + e.getMessage());
            throw new IllegalArgumentException("Error al obtener los permisos.");
        }
    }


}
