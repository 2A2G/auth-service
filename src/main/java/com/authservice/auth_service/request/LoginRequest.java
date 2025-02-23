package com.authservice.auth_service.request;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
