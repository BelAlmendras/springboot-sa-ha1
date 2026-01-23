package com.springboot_sa_ha1.modules.collections.dto;

public record CollectionResponse (
    Long id,
    String name,
    String description,
    String slug,
    String image
){}
