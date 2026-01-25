package com.springboot_sa_ha1.modules.customers.dto;

public record CustomerResponse (
        Long id,
        String nombre,
        String email,
        String phone,
        String rol
) {}
