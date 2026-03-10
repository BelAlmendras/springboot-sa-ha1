package com.springboot_sa_ha1.modules.categories.repository;

import com.springboot_sa_ha1.modules.categories.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long >{

  // Usamos EntityGraph en lugar de JOIN FETCH
  @EntityGraph(attributePaths = {"products", "products.images", "products.productCollections", "products.productCollections.collection"})
  List<Category> findAllBySlugIn(List<String> slugs);
}
