package com.springboot_sa_ha1.modules.addresses.mapper;
import com.springboot_sa_ha1.modules.addresses.dto.AddressResponse;
import com.springboot_sa_ha1.modules.addresses.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
  public AddressResponse toResponse(Address address) {
    return new AddressResponse(
        address.getId_address(),
        address.getAddress(),
        address.getCity(),
        address.getRegion(),
        address.getZip_code(),
        address.getId_customer()
    );
  }
}