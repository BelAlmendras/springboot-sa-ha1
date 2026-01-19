package com.springboot_sa_ha1.modules.customers.service;

import com.springboot_sa_ha1.modules.customers.dto.CustomerRequest;
import com.springboot_sa_ha1.modules.customers.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> listarTodos();
    CustomerResponse obtenerPorId(Long id);
    CustomerResponse guardar(CustomerRequest request);
    CustomerResponse actualizar(Long id, CustomerRequest request);
    void eliminar(Long id);
    /*List<Customer> listarTodos();
    Optional<Customer> obtenerPorId(Long id);
    Customer guardar(Customer customer);
    Customer actualizar(Long id, Customer customer);
    void eliminar(Long id);*/
}
