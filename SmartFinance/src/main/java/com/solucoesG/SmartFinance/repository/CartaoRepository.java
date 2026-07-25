package com.solucoesG.SmartFinance.repository;

import com.solucoesG.SmartFinance.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
}
