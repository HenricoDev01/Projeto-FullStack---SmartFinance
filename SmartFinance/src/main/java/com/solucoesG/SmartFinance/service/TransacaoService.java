package com.solucoesG.SmartFinance.service;

import com.solucoesG.SmartFinance.dto.TransacaoRequestDTO;
import com.solucoesG.SmartFinance.dto.TransacaoResponseDTO;
import com.solucoesG.SmartFinance.exception.TipoTransacaoIncompativelException;
import com.solucoesG.SmartFinance.exception.TransacaoNaoEncontradaException;
import com.solucoesG.SmartFinance.model.Cartao;
import com.solucoesG.SmartFinance.model.Categoria;
import com.solucoesG.SmartFinance.model.Conta;
import com.solucoesG.SmartFinance.model.Transacao;
import com.solucoesG.SmartFinance.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final CartaoService cartaoService;
    private final CategoriaService categoriaSerivce;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaService contaService, CartaoService cartaoService, CategoriaService categoriaSerivce) {
        this.transacaoRepository = transacaoRepository;
        this.contaService = contaService;
        this.cartaoService = cartaoService;
        this.categoriaSerivce = categoriaSerivce;
    }


    public TransacaoResponseDTO cadastrar(TransacaoRequestDTO dto) {
        Conta conta = contaService.buscarEntidadePorId(dto.contaId());
        Categoria categoria = categoriaSerivce.buscarEntidadePorId(dto.categoriaId());

        if(dto.tipo() != categoria.getTipo()) {
            throw new TipoTransacaoIncompativelException("O tipo da transação não corresponde ao tipo da categoria");
        }

        Cartao cartao = null;
        if (dto.cartaoId() != null) {
            cartao = cartaoService.buscarEntidadePorId(dto.cartaoId());
        }

        Transacao transacao = new Transacao();
        transacao.setValor(dto.valor());
        transacao.setData(dto.data());
        transacao.setDescricao(dto.descricao());
        transacao.setTipo(dto.tipo());
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        transacao.setCartao(cartao);

        Transacao transacaoSalva = transacaoRepository.save(transacao);

         UUID cartaoIdResposta = transacaoSalva.getCartao() != null ? transacaoSalva.getCartao().getId() : null;



        return new TransacaoResponseDTO(transacaoSalva.getId(), transacaoSalva.getValor(), transacaoSalva.getData(),
                transacaoSalva.getDescricao(), transacaoSalva.getTipo(), transacaoSalva.getConta().getId(), transacaoSalva.getCategoria().getId(), cartaoIdResposta);
    }


    public Transacao buscarEntidadePorId(UUID id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada"));
    }

    public TransacaoResponseDTO buscarPorId(UUID id) {
        Transacao transacao = buscarEntidadePorId(id);

        UUID cartaoId = transacao.getCartao() != null ? transacao.getCartao().getId() : null;

        return new TransacaoResponseDTO(transacao.getId(), transacao.getValor(), transacao.getData(),
                transacao.getDescricao(), transacao.getTipo(), transacao.getConta().getId(), transacao.getCategoria().getId(), cartaoId);
    }


    public boolean existePorContaId(UUID contaId) {
        return transacaoRepository.existsByContaId(contaId);
    }


    public List<TransacaoResponseDTO> listarTodos() {
        List<Transacao> listaOriginal =  transacaoRepository.findAll();
        List<TransacaoResponseDTO> listaConvertida = new ArrayList<>();

        for (Transacao transacao: listaOriginal) {
            UUID cartaoId = transacao.getCartao() != null ? transacao.getCartao().getId() : null;
            TransacaoResponseDTO dto = new TransacaoResponseDTO(transacao.getId(), transacao.getValor(), transacao.getData(),
                    transacao.getDescricao(), transacao.getTipo(), transacao.getConta().getId(), transacao.getCategoria().getId(), cartaoId);
            listaConvertida.add(dto);
        }

        return listaConvertida;
    }


    public boolean existePorCartaoId(UUID cartaoId) {
        return transacaoRepository.existsByCartaoId(cartaoId);
    }


    public boolean existePorCategoriaId(UUID categoriaId) {
        return transacaoRepository.existsByCategoriaId(categoriaId);
    }




    public List<TransacaoResponseDTO> listarPorContaEPeriodo(UUID contaId, LocalDate inicio, LocalDate fim) {
        List<Transacao> listaOriginal =  transacaoRepository.findByContaIdAndDataBetween(contaId, inicio, fim);
        List<TransacaoResponseDTO> listaConvertida = new ArrayList<>();

        for(Transacao transacao: listaOriginal) {
            UUID cartaoId = transacao.getCartao() != null ? transacao.getCartao().getId() : null;
            TransacaoResponseDTO dto = new TransacaoResponseDTO(transacao.getId(), transacao.getValor()
            , transacao.getData(), transacao.getDescricao(), transacao.getTipo(), transacao.getConta().getId(), transacao.getCategoria().getId(), cartaoId);
            listaConvertida.add(dto);
        }

        return listaConvertida;
    }


    public List<TransacaoResponseDTO> listarPorCategoria(UUID categoriaId) {
        List<Transacao> listaOriginal =  transacaoRepository.findByCategoriaId(categoriaId);
        List<TransacaoResponseDTO> listaConvertida = new ArrayList<>();

        for(Transacao transacao : listaOriginal) {
            UUID cartaoId = transacao.getCartao() != null ? transacao.getCartao().getId() : null;
            TransacaoResponseDTO dto = new TransacaoResponseDTO(transacao.getId(), transacao.getValor(), transacao.getData(), transacao.getDescricao(),
                    transacao.getTipo(), transacao.getConta().getId(), transacao.getCategoria().getId(), cartaoId);
            listaConvertida.add(dto);
        }

        return listaConvertida;
    }


    public void deletar(UUID id) {
        transacaoRepository.deleteById(id);
    }

}
