package com.springboot_sa_ha1.modules.products.model;


import com.springboot_sa_ha1.modules.order_product.model.OrderProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
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
  private String name;

  @NotBlank
  private String price;

  private Long stock;

  @NotBlank
  private String description;

  private Long id_category;

  @OneToMany(mappedBy = "product")
  private Set<OrderProduct> orderProducts = new HashSet<>();
}



