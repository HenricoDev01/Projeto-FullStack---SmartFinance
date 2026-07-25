package com.solucoesG.SmartFinance.dto;

import com.solucoesG.SmartFinance.model.TipoTransacao;

public record CategoriaRequestDTO(String nome, TipoTransacao tipo, String corHex) {
}
