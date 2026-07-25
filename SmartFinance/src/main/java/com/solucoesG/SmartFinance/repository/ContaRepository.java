package com.solucoesG.SmartFinance.repository;

import com.solucoesG.SmartFinance.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
}
