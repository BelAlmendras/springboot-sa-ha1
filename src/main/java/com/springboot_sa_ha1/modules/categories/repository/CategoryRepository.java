package com.springboot_sa_ha1.modules.categories.repository;

import com.springboot_sa_ha1.modules.categories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long >{

}
