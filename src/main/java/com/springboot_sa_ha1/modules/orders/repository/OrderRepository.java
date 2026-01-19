package com.springboot_sa_ha1.modules.orders.repository;

import com.springboot_sa_ha1.modules.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
