package com.springboot_sa_ha1.modules.addresses.repository;

import com.springboot_sa_ha1.modules.addresses.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
