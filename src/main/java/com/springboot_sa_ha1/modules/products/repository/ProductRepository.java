package com.springboot_sa_ha1.modules.products.repository;

import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
