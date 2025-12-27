package com.backend.controller;

import com.backend.model.CategoriaModel;
import com.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<CategoriaModel> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @SuppressWarnings("null")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaModel criarCategoria(@RequestBody CategoriaModel categoriaModel) {
        return this.categoriaRepository.save(categoriaModel);
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaModel> buscarCategoriaPorId(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings("null")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaModel> atualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaModel categoriaModelAtualizado) {

        return categoriaRepository.findById(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNome(categoriaModelAtualizado.getNome());
                    CategoriaModel categoriaAtualizada = categoriaRepository.save(categoriaExistente);
                    return ResponseEntity.ok(categoriaAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings("null")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}