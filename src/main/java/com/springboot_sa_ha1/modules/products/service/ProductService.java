package com.springboot_sa_ha1.modules.products.service;

import com.springboot_sa_ha1.modules.products.dto.ProductRequest;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> searchByTerm(String term);
    List<ProductResponse> listarTodos();
    ProductResponse obtenerPorId(Long id);
    ProductResponse guardar(ProductRequest request);
    ProductResponse actualizar(Long id, ProductRequest request);
    void eliminar(Long id);
}