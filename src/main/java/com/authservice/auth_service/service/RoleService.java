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
            System.err.println("Error retrieving roles: " + e.getMessage());
            throw new IllegalArgumentException("Error retrieving roles.");
        }
    }

    public Object create(Role role) {
        try {
            roleRepository.save(role);
            return role;
        } catch (Exception e) {
            System.err.println("Error creating role: " + e.getMessage());
            throw new IllegalArgumentException("Error registering role. Please try again later.");
        }
    }


}
