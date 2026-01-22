package com.springboot_sa_ha1.modules.products.repository;

import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("""
           SELECT p FROM Product p
           WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(p.description) LIKE LOWER(CONCAT('%', :term, '%'))
           """)
  List<Product> searchByTerm(@Param("term") String term);
}
