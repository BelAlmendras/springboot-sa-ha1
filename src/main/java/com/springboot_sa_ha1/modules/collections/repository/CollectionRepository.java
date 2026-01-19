package com.springboot_sa_ha1.modules.collections.repository;

import com.springboot_sa_ha1.modules.collections.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
