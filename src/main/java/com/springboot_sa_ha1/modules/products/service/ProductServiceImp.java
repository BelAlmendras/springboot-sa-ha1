package com.springboot_sa_ha1.modules.products.service;

import com.springboot_sa_ha1.exception.BadRequestException;
import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;
import com.springboot_sa_ha1.modules.product_collections.repository.ProductCollectionRepository;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductRequest;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.mapper.ProductMapper;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final CollectionRepository collectionRepository;
  private final ProductCollectionRepository productCollectionRepository;
  private final ProductMapper mapper;

  public ProductServiceImp(
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      CollectionRepository collectionRepository,
      ProductCollectionRepository productCollectionRepository,
      ProductMapper mapper
  ) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.collectionRepository = collectionRepository;
    this.productCollectionRepository = productCollectionRepository;
    this.mapper = mapper;
  }

  @Override
  public List<ProductResponse> searchByTerm(String term) {
    return productRepository.searchByTerm(term).stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<ProductResponse> listarPorCategoriaSlug(String slug) {

    if (slug == null || slug.isBlank()) {
      throw new BadRequestException("Slug inválido");
    }

    String normalizedSlug = slug
        .trim()
        .toLowerCase()
        .replaceAll("[\\s-]+", "_")   // espacios o guiones → _
        .replaceAll("_+", "_")        // colapsa múltiples _
        .replaceAll("^_|_$", "");     // quita _ al inicio y fin

    return productRepository.findByCategorySlug(normalizedSlug)
        .stream()
        .map(mapper::toResponse)
        .toList();
  }


  @Override
  public List<ProductResponse> listarPorColeccionSlug(String slug) {

    if (slug == null || slug.isBlank()) {
      throw new BadRequestException("Slug inválido");
    }

    String normalizedSlug = slug
        .trim()
        .toLowerCase()
        .replaceAll("[\\s-]+", "_")   // espacios o guiones → _
        .replaceAll("_+", "_")        // colapsa múltiples _
        .replaceAll("^_|_$", "");     // quita _ al inicio y fin

    return productRepository.findByCollectionSlug(normalizedSlug)
        .stream()
        .map(mapper::toResponse)
        .toList();
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
  @Transactional
  public ProductResponse guardar(ProductRequest request) {
    // 🔹 Obtener categoría
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // 🔹 Crear producto básico
    Product product = new Product();
    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // 🔹 Imágenes
    if (request.images() != null && !request.images().isEmpty()) {
      int pos = 0;
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setPosition(pos++);
        image.setProduct(product); // 🔹 importante
        product.getImages().add(image); // 🔹 agregar directamente a la lista
      }
    }

    // 🔹 Guardar producto primero para tener ID
    Product savedProduct = productRepository.save(product);

    // 🔹 Colecciones
    if (request.collections() != null && !request.collections().isEmpty()) {
      Set<ProductCollection> pcs = new HashSet<>();
      for (CollectionResponse colResp : request.collections()) {
        Collection collection = collectionRepository.findById(colResp.id())
            .orElseThrow(() -> new RuntimeException("Colección no encontrada: " + colResp.id()));

        ProductCollection pc = new ProductCollection();
        ProductCollectionId pcId = new ProductCollectionId(savedProduct.getId(), collection.getId());
        pc.setId(pcId);
        pc.setProduct(savedProduct);
        pc.setCollection(collection);
        pcs.add(pc);
      }
      savedProduct.getProductCollections().addAll(pcs);
      savedProduct = productRepository.save(savedProduct);
    }

    return mapper.toResponse(savedProduct);
  }


  @Override
  @Transactional
  public ProductResponse actualizar(Long id, ProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    // 🔹 Actualizar campos básicos
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // 🔹 Imágenes
    // 🔹 Limpiar lista existente
    product.getImages().clear();
    if (request.images() != null && !request.images().isEmpty()) {
      int pos = 0;
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setPosition(pos++);
        image.setProduct(product); // 🔹 referencia bidireccional
        product.getImages().add(image); // 🔹 agregar a la lista existente
      }
    }

    // 🔹 Colecciones
    product.getProductCollections().clear();
    if (request.collections() != null && !request.collections().isEmpty()) {
      Set<ProductCollection> pcs = new HashSet<>();
      for (CollectionResponse colInput : request.collections()) {
        Collection collection;

        if (colInput.id() != null) {
          collection = collectionRepository.findById(colInput.id())
              .orElseThrow(() -> new RuntimeException("Colección no encontrada: " + colInput.id()));
        } else if (colInput.name() != null && !colInput.name().isBlank()) {
          collection = collectionRepository.findByName(colInput.name())
              .orElseGet(() -> {
                Collection c = new Collection();
                c.setName(colInput.name());
                return collectionRepository.save(c);
              });
        } else {
          continue;
        }

        ProductCollection pc = new ProductCollection();
        ProductCollectionId pcId = new ProductCollectionId(product.getId(), collection.getId());
        pc.setId(pcId);
        pc.setProduct(product);
        pc.setCollection(collection);
        pcs.add(pc);
      }
      product.getProductCollections().addAll(pcs);
    }

    Product savedProduct = productRepository.save(product);
    return mapper.toResponse(savedProduct);
  }

  @Override
  public void eliminar(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    productRepository.delete(product);
  }
}