package com.springboot_sa_ha1.modules.products.controller;

import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.products.dto.ProductRequest;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    void testCrearProducto() {
        ProductRequest request = new ProductRequest(
                "Collar",
                150L,
                10L,
                "Un collar bonito",
                List.of("https://imagen1.jpg", "https://imagen2.jpg"),
                1L,
                List.of(
                        new CollectionResponse(1L, "Verano", "Colección de verano", "extra"),
                        new CollectionResponse(2L, "Invierno", "Colección de invierno", "extra")
                )
        );

        CategoryResponse category = new CategoryResponse(1L, "Accesorios", "Descripción cat", "extra");
        List<CollectionResponse> collections = request.collections();

        ProductResponse response = new ProductResponse(
                1L,
                request.name(),
                request.price(),
                request.stock(),
                request.description(),
                request.images(),
                category,
                collections
        );

        when(productService.guardar(request)).thenReturn(response);

        ResponseEntity<ProductResponse> result = productController.crear(request);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Collar", result.getBody().name());
        assertEquals(150L, result.getBody().price());
        assertEquals(2, result.getBody().collections().size());
        assertEquals(2, result.getBody().imageUrls().size());

        verify(productService, times(1)).guardar(request);
    }

    @Test
    void testListarProductos() {
        CategoryResponse category = new CategoryResponse(1L, "Accesorios", "Descripción cat", "extra");

        ProductResponse prod1 = new ProductResponse(
                1L, "Collar", 150L, 10L, "Bonito collar",
                List.of("img1.jpg"), category,
                List.of(new CollectionResponse(1L, "Verano", "Col verano", "extra"))
        );

        ProductResponse prod2 = new ProductResponse(
                2L, "Pulsera", 100L, 5L, "Bonita pulsera",
                List.of("img2.jpg"), category,
                List.of(new CollectionResponse(2L, "Invierno", "Col invierno", "extra"))
        );

        when(productService.listarTodos()).thenReturn(List.of(prod1, prod2));

        ResponseEntity<List<ProductResponse>> result = productController.listar();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(2, result.getBody().size());
        verify(productService, times(1)).listarTodos();
    }

    @Test
    void testObtenerPorId() {
        Long id = 1L;
        CategoryResponse category = new CategoryResponse(1L, "Accesorios", "Descripción cat", "extra");
        ProductResponse response = new ProductResponse(
                id, "Collar", 150L, 10L, "Bonito collar",
                List.of("img1.jpg"), category,
                List.of(new CollectionResponse(1L, "Verano", "Col verano", "extra"))
        );

        when(productService.obtenerPorId(id)).thenReturn(response);

        ResponseEntity<ProductResponse> result = productController.obtenerPorId(id);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Collar", result.getBody().name());
        verify(productService, times(1)).obtenerPorId(id);
    }

    @Test
    void testActualizarProducto() {
        Long id = 1L;
        ProductRequest request = new ProductRequest(
                "Collar Actualizado",
                200L,
                15L,
                "Collar muy bonito",
                List.of("img1.jpg", "img2.jpg"),
                1L,
                List.of(new CollectionResponse(1L, "Verano", "Col verano", "extra"))
        );

        CategoryResponse category = new CategoryResponse(1L, "Accesorios", "Descripción cat", "extra");
        ProductResponse response = new ProductResponse(
                id,
                request.name(),
                request.price(),
                request.stock(),
                request.description(),
                request.images(),
                category,
                request.collections()
        );

        when(productService.actualizar(id, request)).thenReturn(response);

        ResponseEntity<ProductResponse> result = productController.actualizar(id, request);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Collar Actualizado", result.getBody().name());
        assertEquals(2, result.getBody().imageUrls().size());
        verify(productService, times(1)).actualizar(id, request);
    }

    @Test
    void testEliminarProducto() {
        Long id = 1L;

        doNothing().when(productService).eliminar(id);

        ResponseEntity<Void> result = productController.eliminar(id);

        assertEquals(204, result.getStatusCodeValue());
        verify(productService, times(1)).eliminar(id);
    }
}

