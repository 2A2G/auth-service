package com.authservice.auth_service.controller;

import com.authservice.auth_service.entity.Permission;
import com.authservice.auth_service.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            Object createP = permissionService.create(permission);
            return ResponseEntity.status(HttpStatus.CREATED).body(createP.toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al crear el permiso: " + e.getMessage());
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
