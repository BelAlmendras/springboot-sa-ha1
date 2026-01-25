package com.springboot_sa_ha1.modules.cart.repository;

import com.springboot_sa_ha1.modules.cart.model.CartProduct;
import com.springboot_sa_ha1.modules.customers.model.Customer;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    // Obtener todos los items del carrito de un cliente
    List<CartProduct> findByCustomerId(Long customerId);

    // Obtener un item específico del carrito
    Optional<CartProduct> findByCustomerIdAndProductId(Long customerId, Long productId);

    // Eliminar todos los items del carrito de un cliente
    @Modifying
    @Query("DELETE FROM CartProduct cp WHERE cp.customer.id = :customerId")
    void deleteAllByCustomerId(@Param("customerId") Long customerId);

    // Contar items en el carrito de un cliente
    Long countByCustomerId(Long customerId);

    // Verificar si un producto ya está en el carrito
    boolean existsByCustomerAndProduct(Customer customer, Product product);
}