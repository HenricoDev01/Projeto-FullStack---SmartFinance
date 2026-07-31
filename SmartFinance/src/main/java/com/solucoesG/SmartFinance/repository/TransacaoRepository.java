package com.solucoesG.SmartFinance.repository;

import com.solucoesG.SmartFinance.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {

    List<Transacao> findByContaIdAndDataBetween(UUID contaId, LocalDate inicio, LocalDate fim);

    List<Transacao> findByCategoriaId(UUID categoriaId);

    boolean existsByContaId(UUID id);

    boolean existsByCartaoId(UUID cartaoId);

    boolean existsByCategoriaId(UUID categoriaId);
}
