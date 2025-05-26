package com.estetica.contato.controller;

import com.estetica.contato.model.Cliente;
import com.estetica.contato.payload.CadastroRequest;
import com.estetica.contato.payload.JwtResponse;
import com.estetica.contato.payload.LoginRequest;
import com.estetica.contato.security.GeradorToken;
import com.estetica.contato.service.ClienteDetalhesService; // Para obter UserDetails completo
import com.estetica.contato.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; // UserDetails do Spring
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import java.util.List; // Se for usar roles
// import java.util.stream.Collectors; // Se for usar roles

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteDetalhesService clienteDetalhesService; // Para construir UserDetails

    @Autowired
    private GeradorToken geradorToken;

    @PostMapping("/login")
    public ResponseEntity<?> autenticarCliente(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtém o UserDetails autenticado (que é um ClienteUserDetails em nosso caso)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = geradorToken.gerarTokenPorUserDetails(userDetails);

            // Para obter o objeto Cliente completo e retornar mais dados
            Cliente clienteAutenticado = clienteService.ClienteRepository.findByEmail(userDetails.getUsername()).orElse(null);
            // Se você precisar das roles:
            // List<String> roles = userDetails.getAuthorities().stream()
            //        .map(item -> item.getAuthority())
            //        .collect(Collectors.toList());

            if (clienteAutenticado != null) {
    return ResponseEntity.ok(new JwtResponse(jwt,
            clienteAutenticado.getId(),
            clienteAutenticado.getEmail(),
            clienteAutenticado.getNome()
            /* , roles */ ));
} else {
                 // Caso o cliente não seja encontrado após a autenticação (improvável, mas para segurança)
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao recuperar detalhes do cliente após login.");
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Credenciais inválidas - " + e.getMessage());
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody CadastroRequest cadastroRequest) {
        try {
            Cliente clienteCadastrado = clienteService.cadastrarCliente(cadastroRequest);
            // Pode retornar apenas uma mensagem, ou o cliente (sem a senha)
            // Removendo a senha para a resposta:
            clienteCadastrado.setSenha(null); 
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCadastrado);
        } catch (RuntimeException e) {
            // Se o e-mail já existir, por exemplo
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
