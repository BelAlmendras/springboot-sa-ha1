package com.springboot_sa_ha1.modules.order_products.model;


import com.springboot_sa_ha1.modules.orders.model.Order;
import com.springboot_sa_ha1.modules.products.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedido_producto")
@Getter
@Setter
public class OrderProduct {

  @EmbeddedId
  private OrderProductId id;

  private Integer quantity;

  private Integer price;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "id_product")
  private Product product;

  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name = "id_order")
  private Order order;
}