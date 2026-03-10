package com.springboot_sa_ha1.modules.collections.service;

import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionRequest;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionWithProductsResponse;
import com.springboot_sa_ha1.modules.collections.mapper.CollectionMapper;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.collections.service.CollectionService;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.stereotype.Service;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImp implements CollectionService {

  private final CollectionRepository repository;
  private final CollectionMapper mapper;

  public CollectionServiceImp(CollectionRepository repository, CollectionMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<CollectionResponse> listarTodos(){
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }


  @Override
  public CollectionResponse obtenerPorId(Long id){
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
  }

  @Override
  public List<CollectionResponse> listarColeccionesPorSlug(List<String> slugs) {

    // 🔹 Normalización (misma regla que el otro método)
    List<String> normalizedSlugs = slugs.stream()
        .map(String::toLowerCase)
        .map(String::trim)
        .map(s -> s.replace("-", "_"))
        .toList();

    List<Collection> collections =
        repository.findBySlugIn(normalizedSlugs);

    if (collections.isEmpty()) {
      throw new RuntimeException("No se encontraron colecciones");
    }

    return collections.stream()
        .map(mapper::toResponse)
        .toList();
  }


  @Override
  public List<CollectionWithProductsResponse> listarColeccionesConProductosPorSlug(List<String> slugs) {

    if (slugs == null || slugs.isEmpty()) return Collections.emptyList();

    List<String> normalizedSlugs = slugs.stream()
        .filter(Objects::nonNull)
        .map(String::trim)
        .map(String::toLowerCase)
        .map(s -> s.replace("-", "_"))
        .toList();

    List<Collection> collections = repository.findAllBySlugsWithProducts(normalizedSlugs);
    if (collections == null || collections.isEmpty()) return Collections.emptyList();

    List<CollectionWithProductsResponse> result = new ArrayList<>();

    for (Collection c : collections) {
      if (c == null) continue;

      Set<ProductCollection> pcs = (c.getProductCollections() != null) ? c.getProductCollections() : Collections.emptySet();
      List<ProductResponse> products = new ArrayList<>();

      for (ProductCollection pc : pcs) {
        if (pc == null) continue;
        Product p = pc.getProduct();
        if (p == null) continue;

        List<ProductImage> images = (p.getImages() != null) ? p.getImages() : Collections.emptyList();
        List<String> imageUrls = new ArrayList<>();
        for (ProductImage img : images) {
          if (img != null && img.getImageUrl() != null) imageUrls.add(img.getImageUrl());
        }

        CategoryResponse categoryResponse = null;
        if (p.getCategory() != null) {
          categoryResponse = new CategoryResponse(
              p.getCategory().getId(),
              p.getCategory().getName(),
              p.getCategory().getDescription(),
              p.getCategory().getSlug(),
              p.getCategory().getImage()
          );
        }

        Set<ProductCollection> innerPcs = (p.getProductCollections() != null) ? p.getProductCollections() : Collections.emptySet();
        List<CollectionResponse> internalCollections = new ArrayList<>();
        for (ProductCollection innerPc : innerPcs) {
          if (innerPc == null || innerPc.getCollection() == null) continue;
          Collection col = innerPc.getCollection();
          internalCollections.add(new CollectionResponse(
              col.getId(),
              col.getName(),
              col.getDescription(),
              col.getSlug(),
              col.getImage()
          ));
        }

        products.add(new ProductResponse(
            p.getId(),
            p.getName(),
            p.getPrice(),
            p.getStock(),
            p.getDescription(),
            imageUrls,
            categoryResponse,
            internalCollections
        ));
      }

      result.add(new CollectionWithProductsResponse(
          c.getId(),
          c.getName(),
          c.getDescription(),
          c.getSlug(),
          c.getImage(),
          products
      ));
    }

    return result;
  }


  @Override
  public CollectionResponse guardar(CollectionRequest request){
    // Generar slug automáticamente
    String normalizedSlug = request.name()
        .trim()
        .toLowerCase()
        .replace(" ", "_");
    Collection collection = new Collection();
    collection.setName(request.name());
    collection.setDescription(request.description());
    collection.setSlug(normalizedSlug);
    collection.setImage(request.image());

    return mapper.toResponse(repository.save(collection));
  }

  @Override
  public CollectionResponse actualizar(Long id, CollectionRequest request){
    // Generar slug automáticamente
    String normalizedSlug = request.name()
        .trim()
        .toLowerCase()
        .replace(" ", "_");
    Collection collection = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
    collection.setId(id);
    collection.setName(request.name());
    collection.setDescription(request.description());
    collection.setSlug(normalizedSlug);
    collection.setImage(request.image());

    return mapper.toResponse(repository.save(collection));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }
}
