package com.springboot_sa_ha1.modules.products.dto;

public record ProductRequest(
    String product_name,
    String price,
    Long stock,
    String description,
    Long id_category
) {}
