package com.solucoesG.SmartFinance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cartoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    private String numeroFinal;

    private LocalDate validade;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;


}
