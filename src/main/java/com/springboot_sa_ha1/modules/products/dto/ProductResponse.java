package com.springboot_sa_ha1.modules.products.dto;

public record ProductResponse(
    Long id_product,
    String product_name,
    Long price,
    Long stock,
    String description,
    String imageUrl,
    Long id_category
) {}
