package com.authservice.auth_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del permiso no puede estar vacío")
    @Column(name = "name_permission", nullable = false)
    private String name_permission;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> role = new HashSet<>();
}
