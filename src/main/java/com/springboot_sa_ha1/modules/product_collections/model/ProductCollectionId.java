package com.springboot_sa_ha1.modules.product_collections.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCollectionId implements Serializable {

  @Column(name = "id_product")
  private Long productId;

  @Column(name = "id_collection")
  private Long collectionId;

}