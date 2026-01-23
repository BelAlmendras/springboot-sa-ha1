package com.springboot_sa_ha1.modules.order_products.controller;

import com.springboot_sa_ha1.modules.order_products.dto.OrderProductRequest;
import com.springboot_sa_ha1.modules.order_products.dto.OrderProductResponse;
import com.springboot_sa_ha1.modules.order_products.service.OrderProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_product")
public class OrderProductController {

  private final OrderProductService orderProductService;

  public OrderProductController(OrderProductService orderProductService) {
    this.orderProductService = orderProductService;
  }

  @GetMapping
  public ResponseEntity<List<OrderProductResponse>> listar() {
    return ResponseEntity.ok(orderProductService.listarTodos());
  }

  @GetMapping("/order/{orderId}/product/{productId}")
  public ResponseEntity<OrderProductResponse> obtenerPorId(
      @PathVariable Long orderId,
      @PathVariable Long productId
  ) {
    return ResponseEntity.ok(
        orderProductService.obtenerPorId(orderId, productId)
    );
  }

  @PostMapping("/create")
  public ResponseEntity<OrderProductResponse> crear(
      @Valid @RequestBody OrderProductRequest request
  ) {
    return ResponseEntity.ok(orderProductService.guardar(request));
  }

  @PutMapping("/order/{orderId}/product/{productId}")
  public ResponseEntity<OrderProductResponse> actualizar(
      @PathVariable Long orderId,
      @PathVariable Long productId,
      @Valid @RequestBody OrderProductRequest request
  ) {
    return ResponseEntity.ok(
        orderProductService.actualizar(orderId, productId, request)
    );
  }

  @DeleteMapping("/order/{orderId}/product/{productId}")
  public ResponseEntity<Void> eliminar(
      @PathVariable Long orderId,
      @PathVariable Long productId
  ) {
    orderProductService.eliminar(orderId, productId);
    return ResponseEntity.noContent().build();
  }
}