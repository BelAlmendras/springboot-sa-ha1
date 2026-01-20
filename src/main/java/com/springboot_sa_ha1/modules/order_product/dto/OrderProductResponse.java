package com.springboot_sa_ha1.modules.order_product.dto;

import com.springboot_sa_ha1.modules.order_product.model.OrderProductId;

public record OrderProductResponse (
  OrderProductId id,
  Integer quantity,
  Integer price
){}
