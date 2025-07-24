package com.backend.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivel;
    private CategoriaDTO categoria;

}
