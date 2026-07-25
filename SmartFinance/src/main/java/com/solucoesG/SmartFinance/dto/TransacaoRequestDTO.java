package com.solucoesG.SmartFinance.dto;

import com.solucoesG.SmartFinance.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoRequestDTO(
        BigDecimal valor,
        LocalDate data,
        String descricao,
        TipoTransacao tipo,
        UUID contaId,
        UUID categoriaId,
        UUID cartaoId) {
}
