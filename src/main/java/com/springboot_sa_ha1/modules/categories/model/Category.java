package com.springboot_sa_ha1.modules.categories.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_category;

  @NotBlank
  private String category_name;

  @NotBlank
  private String description;

}
