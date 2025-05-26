package com.estetica.contato.security;

import com.estetica.contato.service.ClienteDetalhesService; // Importe o UserDetails customizado
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails; // UserDetails do Spring
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class GeradorToken {
    private static final Logger logger = LoggerFactory.getLogger(GeradorToken.class);

    @Value("${estetica.jwt.secret}")
    private String jwtSecretString;

    @Value("${estetica.jwt.expirationMs}")
    private int jwtExpirationMs;

    private SecretKey key;

    // Inicializa a chave a partir do secret string.
    // @PostConstruct // Pode usar @PostConstruct para garantir que a chave seja criada após a injeção de dependências
    private SecretKey getSigningKey() {
        if (key == null) {
            // Garante que a chave tenha o tamanho adequado para o algoritmo HS256 (32 bytes)
            // Se a string secreta for menor, isso pode causar problemas ou ser inseguro.
            // Idealmente, a string secreta configurada já deve ser forte e ter o comprimento adequado.
            byte[] keyBytes = jwtSecretString.getBytes(StandardCharsets.UTF_8);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        }
        return key;
    }

    public String gerarToken(Authentication authentication) {
        // O principal da autenticação DEVE ser UserDetails do Spring Security
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // username aqui é o email
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    // Alternativa para gerar token diretamente do UserDetails (que vem do ClienteDetalhesService)
    public String gerarTokenPorUserDetails(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // username aqui é o email
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String getEmailDoToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }   

    public boolean validarToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT não suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("String de claims JWT vazia: {}", e.getMessage());
        } catch (SignatureException e) { // Para versões mais antigas do jjwt
             logger.error("Assinatura JWT inválida: {}", e.getMessage());
        } catch (io.jsonwebtoken.security.SecurityException e) { // Para versões mais novas jjwt >= 0.11.x
            logger.error("Assinatura JWT inválida (SecurityException): {}", e.getMessage());
        }
        return false;
    }
}
