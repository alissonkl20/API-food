package com.backend.mapper;

import com.backend.DTO.CategoriaDTO;
import com.backend.DTO.ProdutoRequestDTO;
import com.backend.DTO.ProdutoResponseDTO;
import com.backend.model.*;


public class ProdutoMapper {

    public static ProdutoModel toEntity(ProdutoRequestDTO dto, CategoriaModel categoria) {
        ProdutoModel produto = new ProdutoModel();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : true);
        produto.setCategoria(categoria);
        return produto;
    }

    public static ProdutoResponseDTO toDTO(ProdutoModel produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setDisponivel(produto.getDisponivel());

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(produto.getCategoria().getId());
        categoriaDTO.setNome(produto.getCategoria().getNome());

        dto.setCategoria(categoriaDTO);
        return dto;
    }
}
