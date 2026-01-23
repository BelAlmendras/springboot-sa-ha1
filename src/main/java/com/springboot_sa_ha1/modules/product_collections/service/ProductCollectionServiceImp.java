package com.springboot_sa_ha1.modules.product_collections.service;

import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionRequest;
import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionResponse;
import com.springboot_sa_ha1.modules.product_collections.mapper.ProductCollectionMapper;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;
import com.springboot_sa_ha1.modules.product_collections.repository.ProductCollectionRepository;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCollectionServiceImp implements ProductCollectionService {

  private final ProductCollectionRepository repository;
  private final ProductCollectionMapper mapper;
  private final ProductRepository productRepository;
  private final CollectionRepository collectionRepository;

  public ProductCollectionServiceImp(
      ProductCollectionRepository repository,
      ProductRepository productRepository,
      CollectionRepository collectionRepository,
      ProductCollectionMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
    this.productRepository = productRepository;
    this.collectionRepository = collectionRepository;
  }

  @Override
  public List<ProductCollectionResponse> listarTodos() {
    return repository.findAll()
        .stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public ProductCollectionResponse obtenerPorId(
      Long productId,
      Long collectionId
  ) {
    ProductCollectionId id =
        new ProductCollectionId(productId, collectionId);

    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() ->
            new RuntimeException("Relación Product-Collection no encontrada")
        );
  }

  @Override
  public ProductCollectionResponse guardar(
      ProductCollectionRequest request
  ) {
    ProductCollectionId id =
        new ProductCollectionId(
            request.productId(),
            request.collectionId()
        );

    if (repository.existsById(id)) {
      throw new RuntimeException("La relación Product-Collection ya existe");
    }

    Product product = productRepository.findById(request.productId())
        .orElseThrow(() ->
            new RuntimeException("Product no existe")
        );

    Collection collection = collectionRepository.findById(request.collectionId())
        .orElseThrow(() ->
            new RuntimeException("Collection no existe")
        );

    ProductCollection productCollection = new ProductCollection();
    productCollection.setId(id);
    productCollection.setProduct(product);
    productCollection.setCollection(collection);

    return mapper.toResponse(
        repository.save(productCollection)
    );
  }

  @Override
  public void eliminar(
      Long productId,
      Long collectionId
  ) {
    ProductCollectionId id =
        new ProductCollectionId(productId, collectionId);

    if (!repository.existsById(id)) {
      throw new RuntimeException("Relación Product-Collection no encontrada");
    }

    repository.deleteById(id);
  }
}



