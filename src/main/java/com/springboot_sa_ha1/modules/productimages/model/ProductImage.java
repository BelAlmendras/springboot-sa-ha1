package com.springboot_sa_ha1.modules.productimages.model;

import com.springboot_sa_ha1.modules.products.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductImage {

  @Id
  @GeneratedValue
  private Long id;

  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "id_product")
  private Product product;
}
