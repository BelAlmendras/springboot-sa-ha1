package com.springboot_sa_ha1.modules.orders.controller;

import com.springboot_sa_ha1.modules.orders.dto.OrderRequest;
import com.springboot_sa_ha1.modules.orders.dto.OrderResponse;
import com.springboot_sa_ha1.modules.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")   //Se define la ruta base del controlador
//@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listar() {
        return ResponseEntity.ok(orderService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.obtenerPorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> crear(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> actualizar(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        orderService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}