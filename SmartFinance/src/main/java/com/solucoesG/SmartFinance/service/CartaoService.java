package com.solucoesG.SmartFinance.service;

import com.solucoesG.SmartFinance.dto.CartaoRequestDTO;
import com.solucoesG.SmartFinance.dto.CartaoResponseDTO;
import com.solucoesG.SmartFinance.exception.CartaoNaoEncontradoException;
import com.solucoesG.SmartFinance.model.Cartao;
import com.solucoesG.SmartFinance.model.Conta;
import com.solucoesG.SmartFinance.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ContaService contaService;

    public CartaoService(CartaoRepository cartaoRepository, ContaService contaService) {
        this.cartaoRepository = cartaoRepository;
        this.contaService = contaService;
    }


    public CartaoResponseDTO cadastrar(CartaoRequestDTO dto, UUID contaId) {
        Conta conta = contaService.buscarEntidadePorId(contaId);

        Cartao cartao = new Cartao();
        cartao.setConta(conta);
        cartao.setTipo(dto.tipo());
        cartao.setNumeroFinal(dto.numeroFinal());
        cartao.setValidade(dto.validade());
        cartao.setNome(dto.nome());

        Cartao cartaoSalvo = cartaoRepository.save(cartao);

        return new CartaoResponseDTO(cartaoSalvo.getId(), cartaoSalvo.getTipo(), cartaoSalvo.getNumeroFinal(), cartaoSalvo.getValidade(), cartaoSalvo.getNome());

    }


    public Cartao buscarEntidadePorId(UUID id) {
        return cartaoRepository.findById(id)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado"));
    }

    public CartaoResponseDTO buscarPorId(UUID id) {
        Cartao cartao = buscarEntidadePorId(id);

        return new CartaoResponseDTO(cartao.getId(), cartao.getTipo(), cartao.getNumeroFinal(), cartao.getValidade(), cartao.getNome());
    }

    public List<CartaoResponseDTO> listarTodos() {
        List<Cartao> listaOriginal = cartaoRepository.findAll();
        List<CartaoResponseDTO> listaConvertida = new ArrayList<>();

        for (Cartao cartao : listaOriginal) {
            CartaoResponseDTO dto = new CartaoResponseDTO(cartao.getId(), cartao.getTipo(), cartao.getNumeroFinal(), cartao.getValidade(), cartao.getNome());
            listaConvertida.add(dto);
        }

        return listaConvertida;

    }

}