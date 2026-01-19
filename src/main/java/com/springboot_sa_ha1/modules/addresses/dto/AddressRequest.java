package com.springboot_sa_ha1.modules.addresses.dto;

public record AddressRequest(
        String address,
        String city,
        String region,
        Integer zip_code,
        Long id_customer
) {}

