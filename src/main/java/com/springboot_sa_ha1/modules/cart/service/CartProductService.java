package com.springboot_sa_ha1.modules.cart.service;

import com.springboot_sa_ha1.modules.cart.dto.CartProductRequest;
import com.springboot_sa_ha1.modules.cart.dto.CartProductResponse;

import java.util.List;

public interface CartProductService {

    // Obtener carrito completo de un cliente
    List<CartProductResponse> getCartByCustomerId(Long customerId);

    // Agregar producto al carrito (o actualizar si ya existe)
    CartProductResponse addToCart(Long customerId, CartProductRequest request);

    // Actualizar cantidad de un producto en el carrito
    CartProductResponse updateQuantity(Long cartProductId, Integer newQuantity);

    // Eliminar producto del carrito
    void removeFromCart(Long cartProductId);

    // Vaciar carrito completo
    void clearCart(Long customerId);

    // Obtener conteo de items en el carrito
    Long getCartCount(Long customerId);
}