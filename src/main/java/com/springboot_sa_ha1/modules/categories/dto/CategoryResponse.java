package com.springboot_sa_ha1.modules.categories.dto;

public record CategoryResponse(
    Long id,
    String name,
    String description,
    String slug,
    String image
) {}