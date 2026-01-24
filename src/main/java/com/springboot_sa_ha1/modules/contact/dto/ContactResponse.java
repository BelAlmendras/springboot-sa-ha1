package com.springboot_sa_ha1.modules.contact.dto;

import java.time.LocalDateTime;

public record ContactResponse(
        Long id,
        String nombre,
        String email,
        String message
) {}