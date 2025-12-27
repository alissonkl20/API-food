package com.backend.DTO;

import lombok.Data;

@Data
public class RegistroClienteDTO {
    private String email;
    private String senha;
    private String nome;
    private String telefone;
    private String endereco;
}