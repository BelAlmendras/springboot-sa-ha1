package com.springboot_sa_ha1.modules.contact.mapper;

import com.springboot_sa_ha1.modules.contact.dto.ContactRequest;
import com.springboot_sa_ha1.modules.contact.dto.ContactResponse;
import com.springboot_sa_ha1.modules.contact.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactResponse toResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getMessage()
        );
    }
public Contact toEntity(ContactRequest request) {
    Contact contact = new Contact();
    contact.setName(request.name());
    contact.setEmail(request.email());
    contact.setPhone(request.phone());
    contact.setMessage(request.message());
    return contact;
    }
}