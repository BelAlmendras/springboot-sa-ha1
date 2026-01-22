package com.springboot_sa_ha1.security.dto;

import com.springboot_sa_ha1.modules.customers.model.RolCustomer;

public record AuthResponse(
        Long id,
        String name,
        String email,
        RolCustomer rol,
        String token
) {}
