// src/main/java/com/backend/controller/AuthController.java
package com.backend.controller;

import com.backend.DTO.LoginRequestDTO;
import com.backend.DTO.LoginResponseDTO;
import com.backend.DTO.RegistroClienteDTO;
import com.backend.model.ClientModel;
import com.backend.model.UsuarioModel;
import com.backend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Usando constructor injection (recomendado)
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = authService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/registrar/cliente")
    public ResponseEntity<ClientModel> registrarClienteClientModelResponseEntity(@RequestBody RegistroClienteDTO registroDTO) {
        ClientModel novoCliente = authService.registrarCliente(registroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @PostMapping("/registrar/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioModel> registrarAdmin(@RequestBody UsuarioModel usuario) {
        UsuarioModel novoAdmin = authService.registrarAdmin(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAdmin);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioModel> getUsuarioAtual(@AuthenticationPrincipal UsuarioModel usuario) {
        return ResponseEntity.ok(usuario);
    }

    public String criptografarSenha(String senhaEmTextoPlano) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senhaEmTextoPlano);
    }
}