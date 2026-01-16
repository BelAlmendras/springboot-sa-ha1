package com.springboot_sa_ha1.customers.dto;

public record CustomerRequest(
        String name,
        String email,
        String phone,
        String password
) {}
