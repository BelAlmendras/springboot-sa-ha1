package com.springboot_sa_ha1.modules.product_collections.repository;

import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCollectionRepository extends JpaRepository<ProductCollection, ProductCollectionId> {
}
