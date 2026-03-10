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

    public AuthResponse updateUser(Long userId, String name, String email, String phone, RolCustomer rol, Boolean active) {
        Customer user = customersRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        // ✅ Validar email solo si no es null/empty y es diferente
        if (email != null && !email.trim().isEmpty() && !email.equalsIgnoreCase(user.getEmail())) {
            if (customersRepository.existsByEmailIgnoreCase(email)) {
                throw new BadRequestException("El email ya está en uso");
            }
            user.setEmail(email);
        }
        // Si email es null o empty, NO hacer nada (mantener el existente)

        // ✅ Actualizar campos (solo si no son null)
        if (name != null) user.setName(name);
        if (phone != null) user.setPhone(phone); // ← NUEVO: actualizar teléfono
        if (rol != null) user.setRol(rol);
        if (active != null) user.setActive(active);

        customersRepository.save(user);

        // ✅ Devuelve null token cuando se edita usuario
        return new AuthResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone(),user.getRol(), null);
    }

    public void disableUser(Long userId) {
        Customer user = customersRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        user.setActive(false);
        customersRepository.save(user);
    }
}
