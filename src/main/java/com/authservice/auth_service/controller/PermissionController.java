package com.authservice.auth_service.controller;

import com.authservice.auth_service.entity.Permission;
import com.authservice.auth_service.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api-serve/permission")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPermission(@RequestBody Permission permission) {
        try {
            permissionService.create(permission);
            return ResponseEntity.status(HttpStatus.CREATED).body("Permission created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error while creating the permission: " + e.getMessage());
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<Permission>> getPermissions() {
        try {
            List<Permission> permissions = permissionService.view();
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

}
