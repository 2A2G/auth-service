package com.authservice.auth_service.request;

import lombok.Data;

@Data
public class UpdatePassword {
    String oldPassword;
    String newPassword;
}
