package com.springboot_sa_ha1.customers.dto;

public record CustomerResponse (
        Long id,
        String nombre,
        String correo,
        String telefono
) {}
