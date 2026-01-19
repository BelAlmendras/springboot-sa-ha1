package com.springboot_sa_ha1.modules.addresses.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_address;

  @NotBlank
  private String address;

  @NotBlank
  private String city;

  @NotBlank
  private String region;

  private Integer zip_code;

  private Long id_customer;

}



