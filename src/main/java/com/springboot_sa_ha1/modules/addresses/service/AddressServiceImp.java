package com.springboot_sa_ha1.modules.addresses.service;

import com.springboot_sa_ha1.modules.addresses.dto.AddressRequest;
import com.springboot_sa_ha1.modules.addresses.dto.AddressResponse;
import com.springboot_sa_ha1.modules.addresses.mapper.AddressMapper;
import com.springboot_sa_ha1.modules.addresses.model.Address;
import com.springboot_sa_ha1.modules.addresses.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp implements AddressService {
    //private final AddressRepository addressRepository;

    private final AddressRepository repository;
    private final AddressMapper mapper;

    public AddressServiceImp(AddressRepository repository, AddressMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AddressResponse> listarTodos(){
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse obtenerPorId(Long id){
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrado"));
    }

    @Override
    public AddressResponse guardar(AddressRequest request){
        Address address = new Address();
        address.setAddress(request.address());
        address.setCity(request.city());
        address.setRegion(request.region());
        address.setZip_code(request.zip_code());
        address.setId_customer(request.id_customer());
        return mapper.toResponse(repository.save(address));
    }

    @Override
    public AddressResponse actualizar(Long id, AddressRequest request){
        Address address = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
        address.setAddress(request.address());
        address.setCity(request.city());
        address.setRegion(request.region());
        address.setZip_code(request.zip_code());
        address.setId_customer(request.id_customer());
        return mapper.toResponse(repository.save(address));
    }

    @Override
    public void eliminar(Long id){
        repository.deleteById(id);
    }
}
