package com.solucoesG.SmartFinance.dto;

import com.solucoesG.SmartFinance.model.TipoCartao;

import java.time.LocalDate;
import java.util.UUID;

public record CartaoResponseDTO(UUID id, TipoCartao tipo, String numeroFinal, LocalDate validade, String nome) {
}
