package com.solucoesG.SmartFinance.controller;

import com.solucoesG.SmartFinance.dto.TransacaoRequestDTO;
import com.solucoesG.SmartFinance.dto.TransacaoResponseDTO;
import com.solucoesG.SmartFinance.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController

public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoResponseDTO> cadastrar(@RequestBody TransacaoRequestDTO dto) {
        TransacaoResponseDTO transacaoCriada = transacaoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoCriada);
    }

    @GetMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        TransacaoResponseDTO buscarTransacao = transacaoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(buscarTransacao);
    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodos() {
        List<TransacaoResponseDTO> listarTransacao = transacaoService.listarTodos();
        return ResponseEntity.ok(listarTransacao);
    }


    @GetMapping("/transacoes/periodo")
    public ResponseEntity<List<TransacaoResponseDTO>> listarPorContaEPeriodo(@RequestParam UUID contaId, @RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<TransacaoResponseDTO> listarCP = transacaoService.listarPorContaEPeriodo(contaId, inicio, fim);
        return ResponseEntity.ok(listarCP);
    }

    @GetMapping("/transacoes/categoria")
    public ResponseEntity<List<TransacaoResponseDTO>> listarPorCategoria(@RequestParam UUID categoriaId) {
        List<TransacaoResponseDTO> listarCC = transacaoService.listarPorCategoria(categoriaId);
        return ResponseEntity.ok(listarCC);
    }
}
