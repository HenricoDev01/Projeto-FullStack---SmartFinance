package com.solucoesG.SmartFinance.controller;

import com.solucoesG.SmartFinance.dto.ContaRequestDTO;
import com.solucoesG.SmartFinance.dto.ContaResponseDTO;
import com.solucoesG.SmartFinance.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/usuarios/{usuarioId}/contas")
    public ResponseEntity<ContaResponseDTO> cadastrar(@PathVariable UUID usuarioId, @RequestBody ContaRequestDTO dto) {
        ContaResponseDTO contaCriada = contaService.cadastrar(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaCriada);
    }

    @GetMapping("/contas/{id}")
    public ResponseEntity<ContaResponseDTO> buscarPorId(@PathVariable UUID id) {
        ContaResponseDTO buscarUsuario = contaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(buscarUsuario);
    }


    @GetMapping("/contas")
    public ResponseEntity<List<ContaResponseDTO>> listarTodos() {
        List<ContaResponseDTO> listarContas = contaService.listarTodos();
        return ResponseEntity.ok(listarContas);
    }
}
