package com.springboot_sa_ha1.modules.order_product.model;


import com.springboot_sa_ha1.modules.collections.model.Collection;
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

  @ManyToOne
  @MapsId("idProduct")
  @JoinColumn(name = "id_product")
  private Product product;

  @ManyToOne
  @MapsId("idCollection")
  @JoinColumn(name = "id_collection")
  private Collection collection;

  private Integer quantity;

  private Integer price;
}