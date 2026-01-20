package com.springboot_sa_ha1.modules.order_product.repository;

import com.springboot_sa_ha1.modules.order_product.model.OrderProduct;
import com.springboot_sa_ha1.modules.order_product.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository
    extends JpaRepository<OrderProduct, OrderProductId> {  // ¡Objeto como ID!

}