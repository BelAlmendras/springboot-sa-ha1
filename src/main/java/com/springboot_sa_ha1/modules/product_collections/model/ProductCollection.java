package com.springboot_sa_ha1.modules.product_collections.model;

import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.products.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_collection")
@Getter
@Setter
public class ProductCollection {

  @EmbeddedId
  private ProductCollectionId id;

  @ManyToOne
  @MapsId("productId")
  private Product product;

  @ManyToOne
  @MapsId("collectionId")
  private Collection collection;
}



