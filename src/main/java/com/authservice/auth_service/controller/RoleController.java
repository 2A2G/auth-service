package com.authservice.auth_service.controller;

import com.authservice.auth_service.entity.Role;
import com.authservice.auth_service.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api-serve/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        try {
            roleService.create(role);
            return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating role");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getRoles() {
        try {
            List<Role> roles = roleService.view();
            return ResponseEntity.status(HttpStatus.OK).body(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}
