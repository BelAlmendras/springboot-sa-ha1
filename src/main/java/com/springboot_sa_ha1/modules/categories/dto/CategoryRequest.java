package com.springboot_sa_ha1.modules.categories.dto;

public record CategoryRequest (
        String name,
        String description,
        String image
) {}
