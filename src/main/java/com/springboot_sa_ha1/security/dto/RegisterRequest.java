package com.springboot_sa_ha1.security.dto;

import com.springboot_sa_ha1.modules.customers.model.RolCustomer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        Long id,
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(min = 6, max = 100) String password,
        RolCustomer rol,
        String phone
) {}
