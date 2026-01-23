package com.springboot_sa_ha1.modules.order_products.service;

import com.springboot_sa_ha1.modules.order_products.dto.OrderProductRequest;
import com.springboot_sa_ha1.modules.order_products.dto.OrderProductResponse;

import java.util.List;

public interface OrderProductService {

  List<OrderProductResponse> listarTodos();

  OrderProductResponse obtenerPorId(
      Long orderId,
      Long productId
  );
  OrderProductResponse guardar(
      OrderProductRequest request
  );
  OrderProductResponse actualizar(
      Long orderId,
      Long productId,
      OrderProductRequest request
  );
  void eliminar(
      Long orderId,
      Long productId
  );
}