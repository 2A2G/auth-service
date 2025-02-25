package com.authservice.auth_service.service;

import com.authservice.auth_service.entity.Role;
import com.authservice.auth_service.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRole() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener los roles: " + e.getMessage());
            throw new IllegalArgumentException("Error al obtener los roles.");
        }
    }

}
