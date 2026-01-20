package com.springboot_sa_ha1.modules.order_product.controller;

import com.springboot_sa_ha1.modules.order_product.dto.OrderProductRequest;
import com.springboot_sa_ha1.modules.order_product.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_product.model.OrderProductId;
import com.springboot_sa_ha1.modules.order_product.service.OrderProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_product")
//@RequiredArgsConstructor
public class OrderProductController {

  private final OrderProductService orderProductService;

  public OrderProductController(OrderProductService orderProductService) {
    this.orderProductService = orderProductService;
  }

  @GetMapping
  public ResponseEntity<List<OrderProductResponse>> listar() {
    return ResponseEntity.ok(orderProductService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderProductResponse> obtenerPorId(@PathVariable OrderProductId id) {
    return ResponseEntity.ok(orderProductService.obtenerPorId(id));
  }

  @PostMapping("/create")
  public ResponseEntity<OrderProductResponse> crear(@Valid @RequestBody OrderProductRequest request) {
    return ResponseEntity.ok(orderProductService.guardar(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderProductResponse> actualizar(@PathVariable OrderProductId id, @Valid @RequestBody OrderProductRequest request) {
    return ResponseEntity.ok(orderProductService.actualizar(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable OrderProductId id) {
    orderProductService.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}