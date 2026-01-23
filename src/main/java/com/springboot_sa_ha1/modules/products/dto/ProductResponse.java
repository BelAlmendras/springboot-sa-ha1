package com.springboot_sa_ha1.modules.products.dto;
import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;

import java.util.List;

public record ProductResponse(
    Long id,
    String name,
    Long price,
    Long stock,
    String description,
    List<String> imageUrls,
    CategoryResponse category,
    List<CollectionResponse> collections  // <-- nuevo campo
) {}

