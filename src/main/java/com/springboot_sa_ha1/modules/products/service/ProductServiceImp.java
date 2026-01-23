package com.springboot_sa_ha1.modules.products.service;

import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
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

  private final ProductRepository repository;
  private final CategoryRepository categoryRepository;
  private final ProductMapper mapper;

  public ProductServiceImp(
      ProductRepository repository,
      CategoryRepository categoryRepository,
      ProductMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<ProductResponse> searchByTerm(String term) {
    return repository.searchByTerm(term).stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
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
    // Buscar la categoría
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // Crear el producto
    Product product = new Product();
    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // Asociar imágenes si existen
    if (request.images() != null && !request.images().isEmpty()) {
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    // Guardar producto con imágenes
    return mapper.toResponse(repository.save(product));
  }

  @Override
  public ProductResponse actualizar(Long id, ProductRequest request) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // Limpiar imágenes anteriores y agregar nuevas
    product.getImages().clear();
    if (request.images() != null && !request.images().isEmpty()) {
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    return mapper.toResponse(repository.save(product));
  }

  @Override
  public void eliminar(Long id) {
    Product product = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    repository.delete(product);
  }
}