package com.springboot_sa_ha1.modules.categories.service;

import com.springboot_sa_ha1.modules.categories.dto.CategoryWithProductsResponse;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.categories.dto.CategoryRequest;
import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.categories.mapper.CategoryMapper;
import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.service.CategoryService;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
  }

  @Override
  public List<CategoryWithProductsResponse> listarCategoriasConProductosPorSlug(List<String> slugs) {

    // Normalización de slugs
    List<String> normalizedSlugs = slugs.stream()
        .filter(Objects::nonNull)
        .map(String::toLowerCase)
        .map(String::trim)
        .map(s -> s.replace("-", "_"))
        .toList();

    // Traemos las categorías con productos usando EntityGraph
    List<Category> categories = repository.findAllBySlugIn(normalizedSlugs);

    // Mapear a DTO
    return categories.stream()
        .filter(Objects::nonNull)
        .map(c -> {
          List<ProductResponse> products = c.getProducts().stream()
              .filter(Objects::nonNull)
              .map(p -> {
                // Lista de URLs de imágenes
                List<String> imageUrls = (p.getImages() != null)
                    ? p.getImages().stream()
                    .filter(Objects::nonNull)
                    .map(ProductImage::getImageUrl)
                    .filter(Objects::nonNull)
                    .toList()
                    : List.of();

                // Lista de colecciones
                List<CollectionResponse> collections = (p.getProductCollections() != null)
                    ? p.getProductCollections().stream()
                    .filter(Objects::nonNull)
                    .map(pc -> {
                      var col = pc.getCollection();
                      return new CollectionResponse(
                          col.getId(),
                          col.getName(),
                          col.getDescription(),
                          col.getSlug(),
                          col.getImage()
                      );
                    })
                    .toList()
                    : List.of();

                // DTO del producto
                return new ProductResponse(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getStock(),
                    p.getDescription(),
                    imageUrls,
                    new CategoryResponse(
                        c.getId(),
                        c.getName(),
                        c.getDescription(),
                        c.getSlug(),
                        c.getImage()
                    ),
                    collections
                );
              })
              .toList();

          return new CategoryWithProductsResponse(
              c.getId(),
              c.getName(),
              c.getDescription(),
              c.getSlug(),
              c.getImage(),
              products
          );
        })
        .toList();
  }

  @Override
  public CategoryResponse guardar(CategoryRequest request){
    // Generar slug automáticamente
    String normalizedSlug = request.name()
        .trim()
        .toLowerCase()
        .replace(" ", "_");
    Category category = new Category();
    category.setName(request.name());
    category.setDescription(request.description());
    category.setSlug(normalizedSlug);
    category.setImage(request.image());
    return mapper.toResponse(repository.save(category));
  }

  @Override
  public CategoryResponse actualizar(Long id, CategoryRequest request){
    Category category = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    category.setName(request.name());
    category.setDescription(request.description());
    category.setImage(request.image());

    return mapper.toResponse(repository.save(category));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }
}