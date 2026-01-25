package com.springboot_sa_ha1.modules.cart.controller;

import com.springboot_sa_ha1.modules.cart.dto.CartProductRequest;
import com.springboot_sa_ha1.modules.cart.dto.CartProductResponse;
import com.springboot_sa_ha1.modules.cart.service.CartProductService;
import com.springboot_sa_ha1.modules.customers.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartProductController {

    private final CartProductService cartProductService;
    private final CustomerRepository customerRepository; // ← Importante

    // Obtener carrito del usuario autenticado
    @GetMapping
    public ResponseEntity<List<CartProductResponse>> getCart(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long customerId = getCustomerIdFromUserDetails(userDetails);
        List<CartProductResponse> cart = cartProductService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(cart);
    }

    // Agregar producto al carrito
    @PostMapping("/add")
    public ResponseEntity<CartProductResponse> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartProductRequest request) {
        Long customerId = getCustomerIdFromUserDetails(userDetails);
        CartProductResponse response = cartProductService.addToCart(customerId, request);
        return ResponseEntity.ok(response);
    }

    // Actualizar cantidad
    @PutMapping("/update/{cartProductId}")
    public ResponseEntity<CartProductResponse> updateQuantity(
            @PathVariable Long cartProductId,
            @RequestParam Integer quantity) {
        CartProductResponse response = cartProductService.updateQuantity(cartProductId, quantity);
        return ResponseEntity.ok(response);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/remove/{cartProductId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartProductId) {
        cartProductService.removeFromCart(cartProductId);
        return ResponseEntity.noContent().build();
    }

    // Vaciar carrito
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long customerId = getCustomerIdFromUserDetails(userDetails);
        cartProductService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

    // Obtener conteo de items
    @GetMapping("/count")
    public ResponseEntity<Long> getCartCount(@AuthenticationPrincipal UserDetails userDetails) {
        Long customerId = getCustomerIdFromUserDetails(userDetails);
        Long count = cartProductService.getCartCount(customerId);
        return ResponseEntity.ok(count);
    }

    // Método auxiliar para obtener customerId del usuario autenticado
    private Long getCustomerIdFromUserDetails(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        // El username en UserDetails es el email (según tu JwtService)
        String email = userDetails.getUsername();

        // Buscar customer por email
        return customerRepository.findByEmailIgnoreCase(email)
                .map(customer -> customer.getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para email: " + email));
    }
}