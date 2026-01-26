package com.springboot_sa_ha1.security.controller;

import com.springboot_sa_ha1.security.dto.AuthRequest;
import com.springboot_sa_ha1.security.dto.AuthResponse;
import com.springboot_sa_ha1.security.dto.RegisterRequest;
import com.springboot_sa_ha1.security.dto.UpdateUserRequest;
import com.springboot_sa_ha1.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<AuthResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest req
    ) {
        // Agrega req.phone() al método updateUser
        AuthResponse updated = authService.updateUser(id, req.name(), req.email(), req.phone(), req.rol(), req.active());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        authService.disableUser(id);
        return ResponseEntity.noContent().build();
    }
}
