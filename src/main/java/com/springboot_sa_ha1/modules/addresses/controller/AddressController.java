package com.springboot_sa_ha1.modules.addresses.controller;

import com.springboot_sa_ha1.modules.addresses.dto.AddressRequest;
import com.springboot_sa_ha1.modules.addresses.dto.AddressResponse;
import com.springboot_sa_ha1.modules.addresses.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")   //Se define la ruta base del controlador
//@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> listar() {
        return ResponseEntity.ok(addressService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.obtenerPorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AddressResponse> crear(@Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> actualizar(@PathVariable Long id, @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        addressService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}