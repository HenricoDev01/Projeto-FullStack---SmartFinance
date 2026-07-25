package com.solucoesG.SmartFinance.controller;

import com.solucoesG.SmartFinance.dto.CartaoRequestDTO;
import com.solucoesG.SmartFinance.dto.CartaoResponseDTO;
import com.solucoesG.SmartFinance.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping("/contas/{contaId}/cartoes")
    public ResponseEntity<CartaoResponseDTO> cadastrar(@PathVariable UUID contaId, @RequestBody CartaoRequestDTO dto) {
        CartaoResponseDTO cartaoCriado = cartaoService.cadastrar(dto, contaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoCriado);
    }

    @GetMapping("/cartoes/{id}")
    public ResponseEntity<CartaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        CartaoResponseDTO buscarCartao = cartaoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(buscarCartao);
    }

    @GetMapping("/cartoes")
    public ResponseEntity<List<CartaoResponseDTO>> listarTodos() {
        List<CartaoResponseDTO> listarTodos = cartaoService.listarTodos();
        return ResponseEntity.ok(listarTodos);
    }



}
