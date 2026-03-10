package com.springboot_sa_ha1.security.dto;

import com.springboot_sa_ha1.modules.customers.model.RolCustomer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank @Size(min = 1, max = 50) String name,
        @Email @NotBlank String email,
        String phone,
        RolCustomer rol,
        Boolean active
) {}