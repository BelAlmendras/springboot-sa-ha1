package com.springboot_sa_ha1.modules.products.dto;

import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;

import java.util.List;

public record ProductRequest(
    String name,
    Long price,
    Long stock,
    String description,
    List<String> images,
    Long categoryId,
    List<CollectionResponse> collections
) {}