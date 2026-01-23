package com.springboot_sa_ha1.modules.product_collections.mapper;
import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionResponse;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import org.springframework.stereotype.Component;

@Component
public class ProductCollectionMapper {
  public ProductCollectionResponse toResponse(ProductCollection pc) {
    return new ProductCollectionResponse(
        pc.getProduct().getId(),
        pc.getCollection().getId()
    );
  }
}
