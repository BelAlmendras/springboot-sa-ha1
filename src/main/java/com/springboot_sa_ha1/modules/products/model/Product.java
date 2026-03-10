package com.springboot_sa_ha1.modules.products.model;


import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.order_products.model.OrderProduct;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotNull
  @Column(nullable = false)
  private Long price;

  private Long stock;

  @NotBlank
  @Column(nullable = false)
  private String description;

  // 🔹 List en lugar de Set para mantener orden
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderColumn(name = "position") // respeta el orden de la lista
  private List<ProductImage> images = new ArrayList<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_category", nullable = false)
  private Category category;

  // 🔹 Quitar @Column, Set está bien aquí
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ProductCollection> productCollections = new HashSet<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<OrderProduct> orderProducts = new HashSet<>();
}



