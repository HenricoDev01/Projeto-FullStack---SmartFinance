package com.solucoesG.SmartFinance.controller;

import com.solucoesG.SmartFinance.dto.LoginRequestDTO;
import com.solucoesG.SmartFinance.dto.LoginResponseDTO;
import com.solucoesG.SmartFinance.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO logarUsuario = usuarioService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(logarUsuario);
    }
}
