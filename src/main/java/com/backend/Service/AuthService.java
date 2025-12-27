// src/main/java/com/backend/service/AuthService.java
package com.backend.Service;

import com.backend.DTO.LoginRequestDTO;
import com.backend.DTO.LoginResponseDTO;
import com.backend.DTO.RegistroClienteDTO;
import com.backend.DTO.RecuperarSenhaRequestDTO;
import com.backend.config.JwtTokenProvider;
import com.backend.model.ClientModel;
import com.backend.model.UsuarioModel;
import com.backend.repository.ClientRepository;
import com.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider; // Você precisa criar esta classe

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequest) {
        UsuarioModel usurious = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), usurious.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        if (!usurious.isAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        String token = tokenProvider.generateToken(usurious.getEmail(), usurious.getRole().name());

        return new LoginResponseDTO(token, usurious.getId(), usurious.getEmail(), usurious.getRole().name());
    }

    public ClientModel registrarCliente(RegistroClienteDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Criar usuário
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(registroDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(registroDTO.getSenha()));
        usuario.setRole(UsuarioModel.Role.ROLE_CLIENTE);
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        // Criar cliente
        ClientModel cliente = new ClientModel();
        cliente.setNome(registroDTO.getNome());
        cliente.setEndereco(registroDTO.getEndereco());
        cliente.setTelefone(registroDTO.getTelefone());
        cliente.setUsuario(usuario);

        return clientRepository.save(cliente);
    }

    public UsuarioModel registrarAdmin(UsuarioModel usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(UsuarioModel.Role.ROLE_SUPER_ADMIN);
        usuario.setAtivo(true);

        return usuarioRepository.save(usuario);
    }

    public String recuperarSenha(RecuperarSenhaRequestDTO request) {
        Optional<ClientModel> clienteOpt = clientRepository.findByEmail(request.getEmail());
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado para o e-mail fornecido.");
        }

        ClientModel cliente = clienteOpt.get();
        UsuarioModel usuario = cliente.getUsuario();

        String novaSenha = gerarNovaSenha();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        // Em um cenário real, você enviaria a novaSenha por e-mail.
        // Por simplicidade, estamos retornando a nova senha.
        return novaSenha;
    }

    private String gerarNovaSenha() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}