package com.springboot_sa_ha1.modules.orders.service;

import com.springboot_sa_ha1.modules.orders.dto.OrderRequest;
import com.springboot_sa_ha1.modules.orders.dto.OrderResponse;
import com.springboot_sa_ha1.modules.orders.mapper.OrderMapper;
import com.springboot_sa_ha1.modules.orders.model.Order;
import com.springboot_sa_ha1.modules.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
  //private final OrderRepository orderRepository;

  private final OrderRepository repository;
  private final OrderMapper mapper;

  public OrderServiceImp(OrderRepository repository, OrderMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<OrderResponse> listarTodos(){
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public OrderResponse obtenerPorId(Long id){
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
  }

  @Override
  public OrderResponse guardar(OrderRequest request){
    Order order = new Order();
    order.setOrderDate(request.orderDate());
    order.setTotal(request.total());
    order.setCustomerId(request.customerId());
    return mapper.toResponse(repository.save(order));
  }

  @Override
  public OrderResponse actualizar(Long id, OrderRequest request){
    Order order = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    order.setOrderDate(request.orderDate());
    order.setTotal(request.total());
    order.setCustomerId(request.customerId());
    return mapper.toResponse(repository.save(order));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }

}
