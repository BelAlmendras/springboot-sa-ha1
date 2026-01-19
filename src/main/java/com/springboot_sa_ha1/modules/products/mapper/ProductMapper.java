package com.springboot_sa_ha1.modules.products.mapper;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
  public ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getStock(),
        product.getDescription(),
        product.getId_category()
    );
  }
}
