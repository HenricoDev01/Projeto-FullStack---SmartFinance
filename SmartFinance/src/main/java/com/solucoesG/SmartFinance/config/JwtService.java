package com.solucoesG.SmartFinance.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {

    private final SecretKey chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long TEMPO_EXPIRACAO = 1000 * 60 * 60;

    public String gerarToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TEMPO_EXPIRACAO))
                .signWith(chaveSecreta)
                .compact();
    }

    public String extrairEmail(String token) {
        return  Jwts.parser()
                .verifyWith(chaveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(chaveSecreta)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
