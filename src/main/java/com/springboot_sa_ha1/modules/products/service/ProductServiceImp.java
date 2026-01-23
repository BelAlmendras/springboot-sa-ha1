package com.springboot_sa_ha1.modules.products.service;

import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;
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

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final CollectionRepository collectionRepository;
  private final ProductMapper mapper;

  public ProductServiceImp(
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      CollectionRepository collectionRepository,
      ProductMapper mapper
  ) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.collectionRepository = collectionRepository;
    this.mapper = mapper;
  }

  @Override
  public List<ProductResponse> searchByTerm(String term) {
    return productRepository.searchByTerm(term).stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<ProductResponse> listarTodos() {
    return productRepository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse obtenerPorId(Long id) {
    return productRepository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
  }

  @Override
  public ProductResponse guardar(ProductRequest request) {
    // Buscar la categoría
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    Product product = new Product();
    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // Asociar imágenes
    if (request.images() != null && !request.images().isEmpty()) {
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    // Asociar colecciones
    if (request.collections() != null && !request.collections().isEmpty()) {
      for (CollectionResponse colResp : request.collections()) {
        Collection collection = collectionRepository.findById(colResp.id())
            .orElseThrow(() -> new RuntimeException("Colección no encontrada: " + colResp.id()));
        ProductCollection pc = new ProductCollection();
        pc.setProduct(product);
        pc.setCollection(collection);
        product.getProductCollections().add(pc);
      }
    }

    return mapper.toResponse(productRepository.save(product));
  }

  @Override
  public ProductResponse actualizar(Long id, ProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // Limpiar y agregar nuevas imágenes
    product.getImages().clear();
    if (request.images() != null && !request.images().isEmpty()) {
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    // Limpiar y agregar nuevas colecciones
    product.getProductCollections().clear();
    if (request.collections() != null && !request.collections().isEmpty()) {
      for (CollectionResponse colResp : request.collections()) {
        Collection collection = collectionRepository.findById(colResp.id())
            .orElseThrow(() -> new RuntimeException("Colección no encontrada: " + colResp.id()));

        ProductCollection pc = new ProductCollection();

        // crear ID compuesto
        ProductCollectionId pcId = new ProductCollectionId();
        pcId.setProductId(product.getId());
        pcId.setCollectionId(collection.getId());
        pc.setId(pcId);

        pc.setProduct(product);
        pc.setCollection(collection);
        product.getProductCollections().add(pc);
      }
    }

    return mapper.toResponse(productRepository.save(product));
  }

  @Override
  public void eliminar(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    productRepository.delete(product);
  }
}