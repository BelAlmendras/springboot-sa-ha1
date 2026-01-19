package com.springboot_sa_ha1.modules.products.dto;

public record ProductResponse(
    Long id_product,
    String product_name,
    String price,
    Long stock,
    String description,
    Long id_category
) {}
