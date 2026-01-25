package com.springboot_sa_ha1.modules.cart.mapper;

import com.springboot_sa_ha1.modules.cart.dto.CartProductResponse;
import com.springboot_sa_ha1.modules.cart.model.CartProduct;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartProductMapper {

    public CartProductResponse toResponse(CartProduct cartProduct) {
        Product product = cartProduct.getProduct();

        // Obtener la primera imagen del producto
        String firstImage = product.getImages() != null && !product.getImages().isEmpty()
                ? product.getImages().stream()
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse(null)
                : null;

        return new CartProductResponse(
                cartProduct.getId(),
                product.getId(),
                product.getName(),
                firstImage,
                product.getPrice(),
                cartProduct.getQuantity(),
                cartProduct.getAddedAt(),
                cartProduct.getUpdatedAt()
        );
    }
}