package com.springboot_sa_ha1.modules.orders.dto;
import java.time.LocalDate;

public record OrderResponse(
        Integer id,
        long quantity,
        LocalDate orderDate,
        long total,
        Integer productId,
        Integer customerId
) {}
