package com.springboot_sa_ha1.modules.categories.mapper;

import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.categories.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
  public CategoryResponse toResponse(Category category) {
    return new CategoryResponse(
        category.getId_category(),
        category.getCategory_name(),
        category.getDescription()
    );
  }
}

