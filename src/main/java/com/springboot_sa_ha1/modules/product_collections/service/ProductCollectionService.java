package com.springboot_sa_ha1.modules.product_collections.service;

import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionRequest;
import com.springboot_sa_ha1.modules.product_collections.dto.ProductCollectionResponse;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;

import java.util.List;

public interface ProductCollectionService {

  List<ProductCollectionResponse> listarTodos();

  ProductCollectionResponse obtenerPorId(
      Long productId,
      Long collectionId
  );
  ProductCollectionResponse guardar(
      ProductCollectionRequest request
  );
  void eliminar(
      Long productId,
      Long collectionId
  );
}