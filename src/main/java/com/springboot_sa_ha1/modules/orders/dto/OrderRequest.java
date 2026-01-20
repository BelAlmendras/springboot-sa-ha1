package com.springboot_sa_ha1.modules.orders.dto;
import java.time.LocalDate;

public record OrderRequest(
    LocalDate orderDate,
    long total,
    Integer customerId
) {}
