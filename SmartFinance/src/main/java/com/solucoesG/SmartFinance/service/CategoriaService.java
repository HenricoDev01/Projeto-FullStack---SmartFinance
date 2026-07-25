package com.solucoesG.SmartFinance.service;

import com.solucoesG.SmartFinance.dto.CategoriaRequestDTO;
import com.solucoesG.SmartFinance.dto.CategoriaResponseDTO;
import com.solucoesG.SmartFinance.exception.CategoriaNaoEncontradaException;
import com.solucoesG.SmartFinance.model.Categoria;
import com.solucoesG.SmartFinance.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseDTO cadastrar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setTipo(dto.tipo());
        categoria.setCorHex(dto.corHex());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(categoriaSalva.getId(), categoriaSalva.getNome(),
                categoriaSalva.getTipo(), categoriaSalva.getCorHex());
    }

    public Categoria buscarEntidadePorId(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));
    }

    public CategoriaResponseDTO buscarPorId(UUID id) {
        Categoria categoria = buscarEntidadePorId(id);

        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getTipo(), categoria.getCorHex());
    }


    public List<CategoriaResponseDTO> listarTodos() {
        List<Categoria> listaOriginal = categoriaRepository.findAll();
        List<CategoriaResponseDTO> listaConvertida = new ArrayList<>();

        for (Categoria categoria: listaOriginal) {
            CategoriaResponseDTO dto = new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getTipo(), categoria.getCorHex());
            listaConvertida.add(dto);
        }

        return listaConvertida;
    }
}
