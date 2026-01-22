package com.springboot_sa_ha1.modules.addresses.repository;

import com.springboot_sa_ha1.modules.addresses.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
  @Query("SELECT a FROM Address a WHERE a.id_customer = :customerId")
  Optional<Address> findByIdCustomer(@Param("customerId") Long customerId);
}
