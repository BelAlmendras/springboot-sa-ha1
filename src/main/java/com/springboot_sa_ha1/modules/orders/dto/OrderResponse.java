package com.springboot_sa_ha1.modules.orders.dto;
import java.time.LocalDate;

public record OrderResponse(
        Integer id,
        LocalDate orderDate,
        long total,
        Integer customerId
) {}
