package com.backend.model;


import jakarta.persistence.*;

@Entity
@Table
public class ClientModel {
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

    private boolean ativo = true;

    // getters e setters

    public enum Role {
        ROLE_ADMIN,
        ROLE_CLIENTE
    }
}
