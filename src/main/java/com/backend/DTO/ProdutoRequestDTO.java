package com.backend.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequestDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivel;
    private Long categoriaId; // Apenas o ID da categoria

}
