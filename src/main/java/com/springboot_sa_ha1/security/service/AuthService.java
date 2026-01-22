package com.springboot_sa_ha1.security.service;

import com.springboot_sa_ha1.exception.BadRequestException;
import com.springboot_sa_ha1.modules.customers.model.RolCustomer;
import com.springboot_sa_ha1.modules.customers.model.Customer;
import com.springboot_sa_ha1.modules.customers.repository.CustomerRepository;
import com.springboot_sa_ha1.security.dto.AuthRequest;
import com.springboot_sa_ha1.security.dto.AuthResponse;
import com.springboot_sa_ha1.security.dto.RegisterRequest;
import com.springboot_sa_ha1.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final CustomerRepository customersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest req) {
        // autentica credenciales
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));

        // carga customers para responder con sus datos
        Customer u = customersRepository.findByEmailIgnoreCase(req.email())
                .orElseThrow(() -> new UsernameNotFoundException("Customer no encontrado"));

        String jwt = jwtService.generateToken(u.getEmail());
        return new AuthResponse(u.getId(), u.getName(), u.getEmail(), u.getRol(), jwt);
    }

    public AuthResponse register(RegisterRequest r) {
        if (customersRepository.existsByEmailIgnoreCase(r.email())) {
            throw new BadRequestException("El email ya está registrado");
        }
        Customer nuevo = Customer.builder()
                .id(r.id())
                .name(r.name())
                .email(r.email())
                .passwordHash(passwordEncoder.encode(r.password()))
                .rol(r.rol() != null ? r.rol() : RolCustomer.USUARIO)
                .phone(r.phone())
                .active(true)
                .build();

        customersRepository.save(nuevo);

        String jwt = jwtService.generateToken(nuevo.getEmail());
        return new AuthResponse(nuevo.getId(), nuevo.getName(), nuevo.getEmail(), nuevo.getRol(), jwt);
    }
}
