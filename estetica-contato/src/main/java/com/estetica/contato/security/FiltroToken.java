package com.estetica.contato.security;

import com.estetica.contato.service.ClienteDetalhesService; // Nosso UserDetailsService
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroToken extends OncePerRequestFilter {

    @Autowired
    private GeradorToken geradorToken;

    @Autowired
    private ClienteDetalhesService clienteDetalhesService;

    private static final Logger logger = LoggerFactory.getLogger(FiltroToken.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = extrairJwtDaRequisicao(request);

            if (jwt != null && geradorToken.validarToken(jwt)) {
                String email = geradorToken.getEmailDoToken(jwt);

                UserDetails userDetails = clienteDetalhesService.loadUserByUsername(email);
                
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // Credenciais (senha) não são necessárias aqui
                                userDetails.getAuthorities());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Não foi possível definir a autenticação do usuário: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String extrairJwtDaRequisicao(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Remove "Bearer " e retorna apenas o token
        }
        return null;
    }
}
