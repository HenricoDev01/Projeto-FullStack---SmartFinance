package com.solucoesG.SmartFinance.controller;

import com.solucoesG.SmartFinance.dto.CategoriaRequestDTO;
import com.solucoesG.SmartFinance.dto.CategoriaResponseDTO;
import com.solucoesG.SmartFinance.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseDTO> cadastrar(@RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO categoriaCriada = categoriaService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable UUID id) {
        CategoriaResponseDTO buscarCategoria = categoriaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(buscarCategoria);
    }


    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        List<CategoriaResponseDTO> listarCategoria = categoriaService.listarTodos();
        return ResponseEntity.ok(listarCategoria);
    }


    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deletar(UUID id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
