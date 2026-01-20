package com.springboot_sa_ha1.modules.order_product.mapper;

import com.springboot_sa_ha1.modules.order_product.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_product.model.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {
  public OrderProductResponse toResponse(OrderProduct orderProduct) {
    return new OrderProductResponse(
        orderProduct.getId(),
        orderProduct.getQuantity(),
        orderProduct.getPrice()
    );
  }
}