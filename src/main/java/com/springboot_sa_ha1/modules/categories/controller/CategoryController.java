package com.springboot_sa_ha1.modules.categories.controller;


import com.springboot_sa_ha1.modules.categories.dto.CategoryRequest;
import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.categories.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> listar() {
    return ResponseEntity.ok(categoryService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.obtenerPorId(id));
  }

  @PostMapping("/create")
  public ResponseEntity<CategoryResponse> crear(@Valid @RequestBody CategoryRequest request) {
    return ResponseEntity.ok(categoryService.guardar(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> actualizar(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
    return ResponseEntity.ok(categoryService.actualizar(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    categoryService.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}