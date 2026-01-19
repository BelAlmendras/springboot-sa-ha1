package com.springboot_sa_ha1.modules.customers.mapper;
import com.springboot_sa_ha1.modules.customers.dto.CustomerResponse;
import com.springboot_sa_ha1.modules.customers.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
  public CustomerResponse toResponse(Customer customer) {
    return new CustomerResponse(
        customer.getId(),
        customer.getName(),
        customer.getEmail(),
        customer.getPhone()
    );
  }
}
