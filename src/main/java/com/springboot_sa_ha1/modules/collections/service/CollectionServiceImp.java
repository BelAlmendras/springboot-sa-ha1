package com.springboot_sa_ha1.modules.collections.service;

import com.springboot_sa_ha1.modules.collections.dto.CollectionRequest;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.mapper.CollectionMapper;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.collections.service.CollectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImp implements CollectionService {
  //private final CollectionRepository collectionRepository;

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

    return mapper.toResponse(repository.save(collection));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }
}
