package com.springboot_sa_ha1.modules.contact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        String phone,

        @NotBlank(message = "El mensaje es obligatorio")
        @Size(min = 10, max = 500, message = "El mensaje debe tener al menos 10 caracteres")
        String message
) {}
