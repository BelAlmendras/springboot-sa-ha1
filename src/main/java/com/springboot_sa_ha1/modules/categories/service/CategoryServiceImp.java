package com.springboot_sa_ha1.modules.categories.service;

import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.categories.dto.CategoryRequest;
import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.categories.mapper.CategoryMapper;
import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.categories.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

  private final CategoryRepository repository;
  private final CategoryMapper mapper;

  public CategoryServiceImp(CategoryRepository repository, CategoryMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<CategoryResponse> listarTodos(){
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryResponse obtenerPorId(Long id){
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
  }

  @Override
  public CategoryResponse guardar(CategoryRequest request){
    Category category = new Category();
    category.setCategory_name(request.category_name());
    category.setDescription(request.description());
    return mapper.toResponse(repository.save(category));
  }

  @Override
  public CategoryResponse actualizar(Long id, CategoryRequest request){
    Category category = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
    category.setCategory_name(request.category_name());
    category.setDescription(request.description());

    return mapper.toResponse(repository.save(category));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }
}