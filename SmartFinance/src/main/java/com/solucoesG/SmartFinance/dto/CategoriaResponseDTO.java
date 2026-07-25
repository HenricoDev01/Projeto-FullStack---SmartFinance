package com.solucoesG.SmartFinance.dto;

import com.solucoesG.SmartFinance.model.TipoTransacao;

import java.util.UUID;

public record CategoriaResponseDTO(UUID id, String nome, TipoTransacao tipo, String corHex) {
}
