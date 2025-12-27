package com.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String endereco;

    // Relacionamento com UsuarioModel
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioModel usuario;

    // Outros campos específicos do cliente
}
