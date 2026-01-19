package com.springboot_sa_ha1.modules.collections.service;

import com.springboot_sa_ha1.modules.collections.dto.CollectionRequest;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;

import java.util.List;

public interface CollectionService {
  List<CollectionResponse> listarTodos();
  CollectionResponse obtenerPorId(Long id);
  CollectionResponse guardar(CollectionRequest request);
  CollectionResponse actualizar(Long id, CollectionRequest request);
  void eliminar(Long id);

}
