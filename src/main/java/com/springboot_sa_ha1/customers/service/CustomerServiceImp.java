package com.springboot_sa_ha1.customers.service;

import com.springboot_sa_ha1.customers.dto.CustomerRequest;
import com.springboot_sa_ha1.customers.dto.CustomerResponse;
import com.springboot_sa_ha1.customers.model.Customer;
import com.springboot_sa_ha1.customers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    //private final CustomerRepository customerRepository;
    @Autowired
    private CustomerRepository repository;

    @Override
    public List<CustomerResponse> listarTodos(){
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse obtenerPorId(Long id){
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public CustomerResponse guardar(CustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        return toResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse actualizar(Long id, CustomerRequest request){
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        return toResponse(repository.save(customer));
    }

    @Override
    public void eliminar(Long id){
        repository.deleteById(id);
    }

    private CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getPassword()
        );
    }
    /*
    @Override
    public List<Customer> listarTodos(){
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> obtenerPorId(Long id){
        return customerRepository.findById(id);
    }

    @Override
    public Customer guardar(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public Customer actualizar(Long id, Customer customer){
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public void eliminar(Long id){
        customerRepository.deleteById(id);
    }

     */
}
