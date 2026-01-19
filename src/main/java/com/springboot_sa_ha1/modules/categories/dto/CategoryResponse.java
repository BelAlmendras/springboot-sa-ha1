package com.springboot_sa_ha1.modules.categories.dto;

public record CategoryResponse(
    Long id_category,
    String category_name,
    String description
) {}