package com.springboot_sa_ha1.modules.products.mapper;
import com.springboot_sa_ha1.modules.categories.mapper.CategoryMapper;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.mapper.CollectionMapper;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductMapper {

  private final CategoryMapper categoryMapper;
  private final CollectionMapper collectionMapper;

  public ProductMapper(CategoryMapper categoryMapper, CollectionMapper collectionMapper) {
    this.categoryMapper = categoryMapper;
    this.collectionMapper = collectionMapper;
  }

  public ProductResponse toResponse(Product product) {
    // 🔹 Manejo seguro de imágenes, ordenadas por posición (nulls last)
    List<String> images = product.getImages() != null
        ? product.getImages().stream()
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(
            ProductImage::getPosition,
            Comparator.nullsLast(Integer::compareTo)
        ))
        .map(ProductImage::getImageUrl)
        .toList()
        : Collections.emptyList();

    // 🔹 Manejo seguro de colecciones
    List<CollectionResponse> collections = product.getProductCollections() != null
        ? product.getProductCollections().stream()
        .map(ProductCollection::getCollection)
        .filter(Objects::nonNull)
        .map(collectionMapper::toResponse)
        .toList()
        : Collections.emptyList();

    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getStock(),
        product.getDescription(),
        images,
        categoryMapper.toResponse(product.getCategory()),
        collections
    );
  }
}

