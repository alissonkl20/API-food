//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.controller;

import com.backend.model.ProdutoModel;
import com.backend.repository.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/produtos"})
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<ProdutoModel> listarProdutos(@RequestParam(required = false) Long categoriaId) {
        return categoriaId != null ? this.produtoRepository.findByCategoriaId(categoriaId) : this.produtoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel criarProduto(@RequestBody ProdutoModel produtomodel) {
        return (ProdutoModel)this.produtoRepository.save(produtomodel);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ProdutoModel> buscarProdutoPorId(@PathVariable Long id) {
        return (ResponseEntity)this.produtoRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoModel produtoModelAtualizado) {

        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produtoModelAtualizado.getNome());
                    produtoExistente.setPreco(produtoModelAtualizado.getPreco());
                    produtoExistente.setDisponivel(produtoModelAtualizado.getDisponivel()); // Alterado para getDisponivel()
                    produtoExistente.setCategoria(produtoModelAtualizado.getCategoria());

                    ProdutoModel produtoAtualizado = produtoRepository.save(produtoExistente);
                    return ResponseEntity.ok(produtoAtualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (!this.produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            this.produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
