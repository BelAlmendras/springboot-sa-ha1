package com.springboot_sa_ha1.modules.collections.model;


import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.products.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "colecciones")
@Getter
@Setter

public class Collection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String collection_name;

  private String description;

  @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ProductCollection> productCollections = new HashSet<>();
}
