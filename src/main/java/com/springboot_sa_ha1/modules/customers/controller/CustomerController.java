package com.springboot_sa_ha1.modules.customers.controller;

import com.springboot_sa_ha1.modules.customers.dto.CustomerRequest;
import com.springboot_sa_ha1.modules.customers.dto.CustomerResponse;
import com.springboot_sa_ha1.modules.customers.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")   //Se define la ruta base del controlador
//@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> listar() {
        return ResponseEntity.ok(customerService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(customerService.obtenerPorId(id));
    }

    @GetMapping("/email/{email:.+}")
    public ResponseEntity<CustomerResponse> obtenerPorEmail(@PathVariable String email) {
        CustomerResponse customer = customerService.obtenerPorEmail(email);
        if (customer == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> crear(@Valid @RequestBody CustomerRequest request){
        return ResponseEntity.ok(customerService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> actualizar(@PathVariable Long id, @Valid @RequestBody CustomerRequest request){
        return ResponseEntity.ok(customerService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        customerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @GetMapping
    public List<Customer> listar() {
        return customerService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> obtenerPorId(@PathVariable Long id){
        return customerService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> crear(@Valid @RequestBody Customer customer){
        return ResponseEntity.ok(customerService.guardar(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> actualizar(@PathVariable Long id, @Valid @RequestBody Customer customer){
        return ResponseEntity.ok(customerService.actualizar(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        customerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    */
}
