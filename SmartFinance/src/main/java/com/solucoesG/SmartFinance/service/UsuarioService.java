package com.solucoesG.SmartFinance.service;


import com.solucoesG.SmartFinance.config.JwtService;
import com.solucoesG.SmartFinance.dto.LoginRequestDTO;
import com.solucoesG.SmartFinance.dto.LoginResponseDTO;
import com.solucoesG.SmartFinance.dto.UsuarioRequestDTO;
import com.solucoesG.SmartFinance.dto.UsuarioResponseDTO;
import com.solucoesG.SmartFinance.exception.CredenciaisInvalidasException;
import com.solucoesG.SmartFinance.exception.EmailJaCadastradoException;
import com.solucoesG.SmartFinance.exception.UsuarioComContasVinculadasException;
import com.solucoesG.SmartFinance.exception.UsuarioNaoEncontradoException;
import com.solucoesG.SmartFinance.model.Conta;
import com.solucoesG.SmartFinance.model.Usuario;
import com.solucoesG.SmartFinance.repository.ContaRepository;
import com.solucoesG.SmartFinance.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ContaRepository contaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ContaRepository contaRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.contaRepository = contaRepository;
        this.jwtService = jwtService;
    }

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if(usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("Email já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenhaHash(passwordEncoder.encode(dto.senha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioSalvo.getId(), usuarioSalvo.getNome(), usuarioSalvo.getEmail());
    }


    public LoginResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CredenciaisInvalidasException("Usuario e(ou) senha inválidos"));

        if(passwordEncoder.matches(dto.senha(), usuario.getSenhaHash())) {
            String token = jwtService.gerarToken(dto.email());
            return new LoginResponseDTO(dto.email(), token);
        }

        throw new CredenciaisInvalidasException("Suas credenciais estão inválidas");
    }


    public Usuario buscarEntidadePorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado"));
    }


    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario =  usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado"));

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> listaOriginal = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaConvertida = new ArrayList<>();

        for(Usuario usuario: listaOriginal) {
            UsuarioResponseDTO dto = new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
            listaConvertida.add(dto);
        }

        return listaConvertida;
    }


    public void deletar(UUID id) {
        Usuario usuario = buscarEntidadePorId(id);
        if(contaRepository.existsByUsuarioId(id)) {
            throw new UsuarioComContasVinculadasException("Existem contas vinculadas a esse usuário");
        }

        usuarioRepository.deleteById(id);
    }
}
