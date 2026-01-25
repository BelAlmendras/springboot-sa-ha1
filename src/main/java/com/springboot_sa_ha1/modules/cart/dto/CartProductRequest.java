package com.springboot_sa_ha1.modules.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartProductRequest(
        @NotNull(message = "El ID del producto es obligatorio")
        Long productId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer quantity
) {}