package com.solucoesG.SmartFinance.dto;

import com.solucoesG.SmartFinance.model.TipoCartao;

import java.time.LocalDate;

public record CartaoRequestDTO(TipoCartao tipo, String numeroFinal, LocalDate validade, String nome) {
}
