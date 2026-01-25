package com.springboot_sa_ha1.modules.customers.service;

import com.springboot_sa_ha1.modules.customers.dto.CustomerRequest;
import com.springboot_sa_ha1.modules.customers.dto.CustomerResponse;
import com.springboot_sa_ha1.modules.customers.mapper.CustomerMapper;
import com.springboot_sa_ha1.modules.customers.model.Customer;
import com.springboot_sa_ha1.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImp implements CustomerService {
    //private final CustomerRepository customerRepository;

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerServiceImp(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerResponse> listarTodos(){
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse obtenerPorId(Long id){
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public CustomerResponse obtenerPorEmail(String email) {
        return repository.findByEmailIgnoreCase(email)
            .map(mapper::toResponse)
            .orElse(null); // devuelve null si no existe
    }

    @Override
    public CustomerResponse guardar(CustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setPasswordHash(request.password());
        return mapper.toResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse actualizar(Long id, CustomerRequest request){
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        customer.setName(request.name());
        // customer.setEmail(request.email()); // ← NO actualizar email (mantener el original)
        // Nota: El email se mantiene como está en la base de datos
        customer.setPasswordHash(request.password());
        return mapper.toResponse(repository.save(customer));
    }

    @Override
    public void eliminar(Long id){
        repository.deleteById(id);
    }


    /*
    private CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getPasswordHash()
        );
    }
    */

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
