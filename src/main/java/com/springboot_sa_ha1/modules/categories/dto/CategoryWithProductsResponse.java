package com.springboot_sa_ha1.modules.categories.dto;

import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import java.util.List;

public record CategoryWithProductsResponse(
    Long id,
    String name,
    String description,
    String slug,
    String image,
    List<ProductResponse> products
) {}