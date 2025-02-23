package com.authservice.auth_service.service;

import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.repository.RoleRepository;
import com.authservice.auth_service.request.LoginRequest;
import com.authservice.auth_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public String accessLogin(LoginRequest user) {
        User foundUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!encoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return foundUser.getRole().getName_rol();
    }

    public String newUser(final User user) {
        try {
            userRepository.findByEmail(user.getEmail())
                    .ifPresent(existingUser -> {
                        throw new IllegalArgumentException("El correo electrónico ya está registrado.");
                    });

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return "Usuario creado exitosamente";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            throw new IllegalArgumentException("Error al registrar usuario. Inténtalo de nuevo más tarde.");
        }
    }

}
