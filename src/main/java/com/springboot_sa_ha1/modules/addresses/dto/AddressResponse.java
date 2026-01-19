package com.springboot_sa_ha1.modules.addresses.dto;

public record AddressResponse(
        Long id_address,
        String address,
        String city,
        String region,
        Integer zip_code,
        Long id_customer
) {}
