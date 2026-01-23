package com.springboot_sa_ha1.modules.products.mapper;
import com.springboot_sa_ha1.modules.categories.mapper.CategoryMapper;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  private final CategoryMapper categoryMapper;

  public ProductMapper(CategoryMapper categoryMapper) {
    this.categoryMapper = categoryMapper;
  }

  public ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getStock(),
        product.getDescription(),
        product.getImages().stream().map(ProductImage::getImageUrl).toList(),
        categoryMapper.toResponse(product.getCategory())
    );
  }
}
