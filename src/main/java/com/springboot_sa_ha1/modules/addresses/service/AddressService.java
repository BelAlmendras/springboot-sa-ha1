package com.springboot_sa_ha1.modules.addresses.service;

import com.springboot_sa_ha1.modules.addresses.dto.AddressRequest;
import com.springboot_sa_ha1.modules.addresses.dto.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> listarTodos();
    AddressResponse obtenerPorId(Long id);
    AddressResponse obtenerPorIdCustomer(Long id);
    AddressResponse guardar(AddressRequest request);
    AddressResponse actualizar(Long id, AddressRequest request);
    void eliminar(Long id);
}
