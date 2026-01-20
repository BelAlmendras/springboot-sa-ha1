package com.springboot_sa_ha1.modules.customers.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_customer")
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String email;


  private String phone;

  @NotBlank
  private String password;

}