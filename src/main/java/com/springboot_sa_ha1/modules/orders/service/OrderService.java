package com.springboot_sa_ha1.modules.orders.service;

import com.springboot_sa_ha1.modules.orders.dto.OrderRequest;
import com.springboot_sa_ha1.modules.orders.dto.OrderResponse;

import java.util.List;

public interface OrderService {
  List< OrderResponse> listarTodos();
  OrderResponse obtenerPorId(Long id);
  OrderResponse guardar(OrderRequest request);
  OrderResponse actualizar(Long id, OrderRequest request);
  void eliminar(Long id);
}