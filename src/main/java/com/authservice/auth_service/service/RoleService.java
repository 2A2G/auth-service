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

    public List<Role> view() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error al obtener los roles: " + e.getMessage());
            throw new IllegalArgumentException("Error al obtener los roles.");
        }
    }

    public Object create(Role role) {
        try {
            roleRepository.save(role);
            return role;
        } catch (Exception e) {
            System.err.println("Error al crear el rol: " + e.getMessage());
            throw new IllegalArgumentException("Error al registrar rol. Inténtalo de nuevo más tarde.");
        }
    }



}
