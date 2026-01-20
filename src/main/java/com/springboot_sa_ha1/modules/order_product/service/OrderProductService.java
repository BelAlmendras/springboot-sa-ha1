package com.springboot_sa_ha1.modules.order_product.service;

import com.springboot_sa_ha1.modules.order_product.dto.OrderProductRequest;
import com.springboot_sa_ha1.modules.order_product.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_product.model.OrderProductId;

import java.util.List;

public interface OrderProductService {
  List<OrderProductResponse> listarTodos();
  OrderProductResponse obtenerPorId(OrderProductId opid);
  OrderProductResponse guardar(OrderProductRequest request);
  OrderProductResponse actualizar(OrderProductId opid, OrderProductRequest request);
  void eliminar(OrderProductId opid);
}