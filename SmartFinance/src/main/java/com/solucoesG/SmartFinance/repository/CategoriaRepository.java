package com.solucoesG.SmartFinance.repository;

import com.solucoesG.SmartFinance.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
