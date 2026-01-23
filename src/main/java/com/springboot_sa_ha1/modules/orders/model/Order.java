package com.springboot_sa_ha1.modules.orders.model;

import com.springboot_sa_ha1.modules.order_products.model.OrderProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "order_date", nullable = false)
  private LocalDate orderDate;

  @NotNull
  private Long total;

  @NotNull
  @Column(name = "id_customer")
  private Integer customerId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private Set<OrderProduct> orderProducts = new HashSet<>();

}



