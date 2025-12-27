package com.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN ou CLIENTE

    @Column(nullable = false)
    private boolean ativo = true;

    // Relacionamento com ClientModel (se necessário)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private ClientModel cliente;

    public enum Role {
        ROLE_ADMIN,
        ROLE_CLIENTE
    }

    // Construtor
    public UsuarioModel() {}

    public UsuarioModel(String email, String senha, Role role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.ativo = true;
    }
}
