package com.springboot_sa_ha1.modules.cart.service;

import com.springboot_sa_ha1.modules.cart.dto.CartProductRequest;
import com.springboot_sa_ha1.modules.cart.dto.CartProductResponse;
import com.springboot_sa_ha1.modules.cart.mapper.CartProductMapper;
import com.springboot_sa_ha1.modules.cart.model.CartProduct;
import com.springboot_sa_ha1.modules.cart.repository.CartProductRepository;
import com.springboot_sa_ha1.modules.customers.model.Customer;
import com.springboot_sa_ha1.modules.customers.repository.CustomerRepository;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartProductServiceImp implements CartProductService {

    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartProductMapper cartProductMapper;

    @Override
    public List<CartProductResponse> getCartByCustomerId(Long customerId) {
        List<CartProduct> cartItems = cartProductRepository.findByCustomerId(customerId);
        return cartItems.stream()
                .map(cartProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CartProductResponse addToCart(Long customerId, CartProductRequest request) {
        // Validar que el cliente existe
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Validar que el producto existe
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si ya existe en el carrito
        Optional<CartProduct> existingCartProduct = cartProductRepository
                .findByCustomerIdAndProductId(customerId, request.productId());

        if (existingCartProduct.isPresent()) {
            // ✅ CORREGIDO: SUMAR a la cantidad existente
            CartProduct cartProduct = existingCartProduct.get();
            int newQuantity = cartProduct.getQuantity() + request.quantity();  // ← SUMA
            cartProduct.setQuantity(newQuantity);
            CartProduct updated = cartProductRepository.save(cartProduct);
            return cartProductMapper.toResponse(updated);
        } else {
            // Crear nuevo item en el carrito
            CartProduct newCartProduct = new CartProduct();
            newCartProduct.setCustomer(customer);
            newCartProduct.setProduct(product);
            newCartProduct.setQuantity(request.quantity());

            CartProduct saved = cartProductRepository.save(newCartProduct);
            return cartProductMapper.toResponse(saved);
        }
    }

    @Override
    @Transactional
    public CartProductResponse updateQuantity(Long cartProductId, Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new RuntimeException("Item del carrito no encontrado"));

        cartProduct.setQuantity(newQuantity);
        CartProduct updated = cartProductRepository.save(cartProduct);
        return cartProductMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void removeFromCart(Long cartProductId) {
        if (!cartProductRepository.existsById(cartProductId)) {
            throw new RuntimeException("Item del carrito no encontrado");
        }
        cartProductRepository.deleteById(cartProductId);
    }

    @Override
    @Transactional
    public void clearCart(Long customerId) {
        cartProductRepository.deleteAllByCustomerId(customerId);
    }

    @Override
    public Long getCartCount(Long customerId) {
        return cartProductRepository.countByCustomerId(customerId);
    }
}