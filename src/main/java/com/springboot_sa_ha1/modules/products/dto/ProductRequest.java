package com.springboot_sa_ha1.modules.products.dto;

import java.util.List;

public record ProductRequest(
    String name,
    Long price,
    Long stock,
    String description,
    Long categoryId,
    List<String> images
) {}