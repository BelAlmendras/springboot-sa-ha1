package com.springboot_sa_ha1.modules.contact.controller;

import com.springboot_sa_ha1.modules.contact.dto.ContactRequest;
import com.springboot_sa_ha1.modules.contact.dto.ContactResponse;
import com.springboot_sa_ha1.modules.contact.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contact") // Ruta base (singular)
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/create") // Ruta completa: /api/contact/create
    public ResponseEntity<ContactResponse> create(@Valid @RequestBody ContactRequest request) {
        return ResponseEntity.ok(contactService.guardar(request));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAll() {
        return ResponseEntity.ok(contactService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}