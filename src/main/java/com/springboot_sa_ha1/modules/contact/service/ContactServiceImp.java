package com.springboot_sa_ha1.modules.contact.service;

import com.springboot_sa_ha1.modules.contact.dto.ContactRequest;
import com.springboot_sa_ha1.modules.contact.dto.ContactResponse;
import com.springboot_sa_ha1.modules.contact.mapper.ContactMapper;
import com.springboot_sa_ha1.modules.contact.model.Contact;
import com.springboot_sa_ha1.modules.contact.repository.ContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactServiceImp implements ContactService { // Nombre coincide con tu archivo

    private final ContactRepository repository;
    private final ContactMapper mapper;

    public ContactServiceImp(ContactRepository repository, ContactMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ContactResponse guardar(ContactRequest request) {
        Contact contact = mapper.toEntity(request);
        Contact savedContact = repository.save(contact);
        return mapper.toResponse(savedContact);
    }

    @Override
    public List<ContactResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ContactResponse obtenerPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ID no existe");
        }
        repository.deleteById(id);
    }
}