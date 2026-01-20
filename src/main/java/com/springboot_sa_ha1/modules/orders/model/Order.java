package com.springboot_sa_ha1.modules.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "order_date", nullable = false)
  private LocalDate orderDate;

  @NotNull
  private Long total;

  @NotNull
  @Column(name = "id_customer")
  private Integer customerId;
}



