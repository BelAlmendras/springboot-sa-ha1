package com.springboot_sa_ha1.security.dto;

import com.springboot_sa_ha1.modules.customers.model.RolCustomer;

public record AuthResponse(
        Long id,
        String name,
        String email,
        String phone,
        RolCustomer rol,
        String token
) {
    // Constructor antiguo para compatibilidad
    public AuthResponse(Long id, String name, String email, RolCustomer rol, String token) {
        this(id, name, email, null, rol, token);
    }
}