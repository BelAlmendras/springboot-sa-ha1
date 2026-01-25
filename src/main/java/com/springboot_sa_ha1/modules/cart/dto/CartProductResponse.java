package com.springboot_sa_ha1.modules.cart.dto;

import java.time.LocalDateTime;

public record CartProductResponse(
        Long id,
        Long productId,
        String productName,
        String productImage,  // URL de la primera imagen
        Long productPrice,    // Cambiado de BigDecimal a Long
        Integer quantity,
        LocalDateTime addedAt,
        LocalDateTime updatedAt
) {}