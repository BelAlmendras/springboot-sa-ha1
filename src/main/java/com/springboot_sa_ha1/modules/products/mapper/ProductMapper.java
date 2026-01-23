package com.springboot_sa_ha1.modules.products.mapper;
import com.springboot_sa_ha1.modules.categories.mapper.CategoryMapper;
import com.springboot_sa_ha1.modules.collections.mapper.CollectionMapper;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  private final CategoryMapper categoryMapper;
  private final CollectionMapper collectionMapper;

  public ProductMapper(CategoryMapper categoryMapper, CollectionMapper collectionMapper) {
    this.categoryMapper = categoryMapper;
    this.collectionMapper = collectionMapper;
  }

  public ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getStock(),
        product.getDescription(),
        product.getImages().stream()
            .map(ProductImage::getImageUrl)
            .toList(),
        categoryMapper.toResponse(product.getCategory()),
        product.getProductCollections().stream()
            .map(ProductCollection::getCollection) // obtenemos la collection asociada
            .map(collectionMapper::toResponse)    // la convertimos a CollectionResponse
            .toList()
    );
  }
}
