package com.springboot_sa_ha1.modules.collections.model;


import com.springboot_sa_ha1.modules.order_product.model.OrderProduct;
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
  private Long id_collection;

  @NotBlank
  private String collection_name;

  private String description;

  @OneToMany(mappedBy = "collection")
  private Set<OrderProduct> orderProducts = new HashSet<>();
}
