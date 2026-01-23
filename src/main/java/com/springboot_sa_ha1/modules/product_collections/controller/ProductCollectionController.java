package com.springboot_sa_ha1.modules.product_collections.controller;

import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionRequest;
import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionResponse;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;
import com.springboot_sa_ha1.modules.product_collections.service.ProductCollectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product_collection")
public class ProductCollectionController {

    private final ProductCollectionService productCollectionService;

    public ProductCollectionController(
        ProductCollectionService productCollectionService
    ) {
        this.productCollectionService = productCollectionService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCollectionResponse>> listar() {
        return ResponseEntity.ok(
            productCollectionService.listarTodos()
        );
    }

    @GetMapping("/product/{productId}/collection/{collectionId}")
    public ResponseEntity<ProductCollectionResponse> obtenerPorId(
        @PathVariable Long productId,
        @PathVariable Long collectionId
    ) {
        return ResponseEntity.ok(
            productCollectionService.obtenerPorId(
                productId,
                collectionId
            )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ProductCollectionResponse> crear(
        @Valid @RequestBody ProductCollectionRequest request
    ) {
        return ResponseEntity.ok(
            productCollectionService.guardar(request)
        );
    }

    @DeleteMapping("/product/{productId}/collection/{collectionId}")
    public ResponseEntity<Void> eliminar(
        @PathVariable Long productId,
        @PathVariable Long collectionId
    ) {
        productCollectionService.eliminar(
            productId,
            collectionId
        );
        return ResponseEntity.noContent().build();
    }
}
