package com.springboot_sa_ha1.customers.repository;

import com.springboot_sa_ha1.customers.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
