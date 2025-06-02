package com.esteticaAutomotiva.domain.pessoa.cliente;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.esteticaAutomotiva.domain.mail.EmailService;
import com.esteticaAutomotiva.domain.pessoa.Pessoa;
import com.esteticaAutomotiva.domain.pessoa.PessoaRepository;
import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataDetalhesCliente;
import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataRegistroCliente;
import com.esteticaAutomotiva.domain.pessoa.dto.DataAutentication;
import com.esteticaAutomotiva.domain.roles.Role;
import com.esteticaAutomotiva.domain.roles.RoleService;
import com.esteticaAutomotiva.infra.exception.ErrorHandler;
import com.esteticaAutomotiva.infra.security.TokenDataJWT;
import com.esteticaAutomotiva.infra.security.TokenService;
import com.esteticaAutomotiva.infra.utils.PasswordUtil;
import com.esteticaAutomotiva.infra.utils.Utils;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Service
public class ClienteService {
	
	@Autowired
	private PasswordUtil passwordUtil;
	
	@Autowired
	private Utils utils;
	
    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private EmailService emailService;
    
	
	public ResponseEntity<TokenDataJWT> login(@Valid DataAutentication data) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
		var authentication = manager.authenticate(authenticationToken);
		
		//impede o login antes da confirmacao do email.
		Pessoa pessoa = (Pessoa) authentication.getPrincipal();
		
		if (!pessoa.isEmailConfirmado()) {
			throw new ErrorHandler.EmailNotConfirmedException("Você precisa confirmar seu e-mail para acessar a conta!");
		}
		
		var tokenJWT = tokenService.gerarToken((Pessoa) authentication.getPrincipal());  
		
		List<String> roles = pessoa.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
		
        return ResponseEntity.ok(new TokenDataJWT(tokenJWT,roles)); 
	}
	
	public ResponseEntity<DataDetalhesCliente> registroCliente(@Valid DataRegistroCliente data,
				  													 UriComponentsBuilder uriBuilder) {

		utils.validacoesCadastro(data.dataRegistroPessoa().login(),
								 data.dataRegistroPessoa().email(),
								 data.dataRegistroPessoa().cpf());
		
		Role role = roleService.findByNameRole("CLIENTE");
		var pessoa = new Cliente(data,role);
		
		pessoa.setSenha(passwordUtil.encrypt(data.dataRegistroPessoa().senha()));
		pessoa.gerarTokenConfirmacao();
		
		var uri = uriBuilder.path("").buildAndExpand(pessoa.getId()).toUri();
		
		try {
			emailService.sendVerificacaoEmail(pessoa);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		
		pessoaRepository.save(pessoa);
		
		return ResponseEntity.created(uri).body(new DataDetalhesCliente(pessoa));
	}

	public Cliente buscaClienteLogin(String name) {
		return (Cliente) pessoaRepository.findByLogin(name);
	}

}
