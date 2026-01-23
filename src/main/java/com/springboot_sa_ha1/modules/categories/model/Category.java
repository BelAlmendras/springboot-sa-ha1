package com.springboot_sa_ha1.modules.categories.model;


import com.springboot_sa_ha1.modules.products.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String category_name;

  @NotBlank
  private String description;

  private String slug;

  @OneToMany(mappedBy = "category")
  private List<Product> products;
}
