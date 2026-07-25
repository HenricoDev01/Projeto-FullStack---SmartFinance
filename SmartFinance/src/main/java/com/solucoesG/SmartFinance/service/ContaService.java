package com.solucoesG.SmartFinance.service;

import com.solucoesG.SmartFinance.dto.ContaRequestDTO;
import com.solucoesG.SmartFinance.dto.ContaResponseDTO;
import com.solucoesG.SmartFinance.exception.ContaNaoEncontradaException;
import com.solucoesG.SmartFinance.model.Conta;
import com.solucoesG.SmartFinance.model.Usuario;
import com.solucoesG.SmartFinance.repository.ContaRepository;
import com.solucoesG.SmartFinance.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioService usuarioService;

    public ContaService(ContaRepository contaRepository, UsuarioService usuarioService) {
        this.contaRepository = contaRepository;
        this.usuarioService = usuarioService;
    }

    public ContaResponseDTO cadastrar(ContaRequestDTO dto, UUID usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);

        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setSaldo(dto.saldo());
        conta.setNumeroConta(dto.numeroConta());

        Conta contaSalva = contaRepository.save(conta);

        return new ContaResponseDTO(contaSalva.getId(), contaSalva.getNumeroConta(), contaSalva.getSaldo());
    }

    public Conta buscarEntidadePorId(UUID id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));
    }

    public ContaResponseDTO buscarPorId(UUID id) {
        Conta conta = buscarEntidadePorId(id);

        return new ContaResponseDTO(conta.getId(), conta.getNumeroConta(), conta.getSaldo());
    }


    public List<ContaResponseDTO> listarTodos() {
        List<Conta> listaOriginal = contaRepository.findAll();
        List<ContaResponseDTO> listaConvertida = new ArrayList<>();

        for(Conta conta: listaOriginal) {
            ContaResponseDTO dto = new ContaResponseDTO(conta.getId(), conta.getNumeroConta(), conta.getSaldo());
            listaConvertida.add(dto);
        }

        return listaConvertida;
    }


}
