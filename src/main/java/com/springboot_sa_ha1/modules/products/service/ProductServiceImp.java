package com.springboot_sa_ha1.modules.products.service;

import com.springboot_sa_ha1.modules.products.dto.ProductRequest;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.mapper.ProductMapper;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
  //private final ProductRepository ProductRepository;

  private final ProductRepository repository;
  private final ProductMapper mapper;

  public ProductServiceImp(ProductRepository repository, ProductMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<ProductResponse> listarTodos() {
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse obtenerPorId(Long id) {
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
  }

  @Override
  public ProductResponse guardar(ProductRequest request) {
    Product Product = new Product();
    Product.setName(request.product_name());
    Product.setPrice(request.price());
    Product.setStock(request.stock());
    Product.setDescription(request.description());
    Product.setId_category(request.id_category());
    return mapper.toResponse(repository.save(Product));
  }

  @Override
  public ProductResponse actualizar(Long id, ProductRequest request) {
    Product Product = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    Product.setName(request.product_name());
    Product.setPrice(request.price());
    Product.setStock(request.stock());
    Product.setDescription(request.description());
    Product.setId_category(request.id_category());
    return mapper.toResponse(repository.save(Product));
  }

  @Override
  public void eliminar(Long id) {
    repository.deleteById(id);
  }
}