package com.solucoesG.SmartFinance.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ContaResponseDTO(UUID id, String numeroConta, BigDecimal saldo) {
}
