package com.solucoesG.SmartFinance.model;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "contas")
@Check(constraints = "saldo >= 0")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Conta {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        private String numeroConta;


        private BigDecimal saldo;

        @ManyToOne
        @JoinColumn(name = "usuario_id", nullable = false)
        private Usuario usuario;
}
