package com.springboot_sa_ha1.modules.collections.controller;

import com.springboot_sa_ha1.modules.collections.dto.CollectionRequest;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
//@RequiredArgsConstructor
public class CollectionController {

  private final CollectionService collectionService;

  public CollectionController(CollectionService collectionService) {
    this.collectionService = collectionService;
  }

  @GetMapping
  public ResponseEntity<List<CollectionResponse>> listar() {
    return ResponseEntity.ok(collectionService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CollectionResponse> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(collectionService.obtenerPorId(id));
  }

  @PostMapping("/create")
  public ResponseEntity<CollectionResponse> crear(@Valid @RequestBody CollectionRequest request) {
    return ResponseEntity.ok(collectionService.guardar(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CollectionResponse> actualizar(@PathVariable Long id, @Valid @RequestBody CollectionRequest request) {
    return ResponseEntity.ok(collectionService.actualizar(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    collectionService.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}