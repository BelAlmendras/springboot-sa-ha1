package com.springboot_sa_ha1.modules.customers.dto;

public record CustomerRequest(
        String name,
        String email,
        String phone,
        String password
) {}
