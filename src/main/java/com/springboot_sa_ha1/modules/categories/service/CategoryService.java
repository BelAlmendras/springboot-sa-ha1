package com.springboot_sa_ha1.modules.categories.service;

import com.springboot_sa_ha1.modules.categories.dto.CategoryRequest;
import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
  List<CategoryResponse> listarTodos();
  CategoryResponse obtenerPorId(Long id);
  CategoryResponse guardar(CategoryRequest request);
  CategoryResponse actualizar(Long id, CategoryRequest request);
  void eliminar(Long id);
}
