package com.springboot_sa_ha1.modules.contact.service;

import com.springboot_sa_ha1.modules.contact.dto.ContactRequest;
import com.springboot_sa_ha1.modules.contact.dto.ContactResponse;
import java.util.List;

public interface ContactService {
    ContactResponse guardar(ContactRequest request);
    List<ContactResponse> listarTodos();
    ContactResponse obtenerPorId(Long id);
    void eliminar(Long id);
}