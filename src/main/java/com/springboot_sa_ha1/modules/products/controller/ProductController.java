package com.springboot_sa_ha1.modules.products.controller;

import com.springboot_sa_ha1.modules.products.dto.ProductRequest;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
//@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String term) {
    return ResponseEntity.ok(productService.searchByTerm(term));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> listar() {
    return ResponseEntity.ok(productService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(productService.obtenerPorId(id));
  }

  @PostMapping("/create")
  public ResponseEntity<ProductResponse> crear(@Valid @RequestBody ProductRequest request) {
    return ResponseEntity.ok(productService.guardar(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
    return ResponseEntity.ok(productService.actualizar(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    productService.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}