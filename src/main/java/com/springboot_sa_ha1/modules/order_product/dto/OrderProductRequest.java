package com.springboot_sa_ha1.modules.order_product.dto;

public record OrderProductRequest (
    Integer quantity,
    Integer price
){}