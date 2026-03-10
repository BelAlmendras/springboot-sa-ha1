package com.springboot_sa_ha1.modules.collections.service;

import com.springboot_sa_ha1.modules.collections.dto.CollectionRequest;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionWithProductsResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CollectionService {
  List<CollectionResponse> listarTodos();
  List<CollectionResponse> listarColeccionesPorSlug(List<String> slugs);
  List<CollectionWithProductsResponse> listarColeccionesConProductosPorSlug(List<String> slugs);
  CollectionResponse obtenerPorId(Long id);
  CollectionResponse guardar(CollectionRequest request);
  CollectionResponse actualizar(Long id, CollectionRequest request);
  void eliminar(Long id);

}
