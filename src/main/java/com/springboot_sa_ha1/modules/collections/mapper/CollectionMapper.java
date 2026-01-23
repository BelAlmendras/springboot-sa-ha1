package com.springboot_sa_ha1.modules.collections.mapper;


import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import org.springframework.stereotype.Component;


@Component
public class CollectionMapper {
  public CollectionResponse toResponse(Collection collection) {
    return new CollectionResponse(
        collection.getId(),
        collection.getName(),
        collection.getDescription(),
        collection.getSlug(),
        collection.getImage()
    );
  }
}
