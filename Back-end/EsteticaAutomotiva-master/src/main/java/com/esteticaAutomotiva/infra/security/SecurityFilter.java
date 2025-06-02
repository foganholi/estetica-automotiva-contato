package com.esteticaAutomotiva.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esteticaAutomotiva.domain.pessoa.PessoaRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private PessoaRepository repository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
	    
		var tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			
			var subject = tokenService.getSubject(tokenJWT);
			var user = repository.findByLogin(subject);
			
			if (user != null) {
			    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			    SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		}

	    filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
	    var authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader != null) {
	    	return authorizationHeader.replace("Bearer ", "");
	    }

	    return null;
	}

}
