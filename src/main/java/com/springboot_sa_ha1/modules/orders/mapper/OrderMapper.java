package com.springboot_sa_ha1.modules.orders.mapper;
import com.springboot_sa_ha1.modules.orders.dto.OrderResponse;
import com.springboot_sa_ha1.modules.orders.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
  public OrderResponse toResponse(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getOrderDate(),
        order.getTotal(),
        order.getCustomerId()
    );
  }
}
