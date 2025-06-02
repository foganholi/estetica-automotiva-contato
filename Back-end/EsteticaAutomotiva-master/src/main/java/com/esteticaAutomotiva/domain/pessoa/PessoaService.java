package com.esteticaAutomotiva.domain.pessoa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.esteticaAutomotiva.domain.pessoa.dto.DataAtualizarPessoa;
import com.esteticaAutomotiva.domain.pessoa.dto.DataAutentication;
import com.esteticaAutomotiva.domain.pessoa.dto.DataDetalhesPessoa;
import com.esteticaAutomotiva.domain.roles.Role;
import com.esteticaAutomotiva.infra.exception.ErrorHandler;
import com.esteticaAutomotiva.infra.security.TokenDataJWT;
import com.esteticaAutomotiva.infra.security.TokenService;
import com.esteticaAutomotiva.infra.utils.PasswordUtil;
import com.esteticaAutomotiva.infra.utils.Utils;

import jakarta.validation.Valid;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
    @Autowired
    private AuthenticationManager manager;
	
	@Autowired
	private Utils utils;
	
    @Autowired
    private TokenService tokenService;
	
	@Autowired
	private PasswordUtil passwordUtil;
	

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
		
		System.out.println(pessoa.getRoles());
		
        return ResponseEntity.ok(new TokenDataJWT(tokenJWT,roles)); 
	}
	
	
	public ResponseEntity<DataDetalhesPessoa> atualizarPessoa(@Valid DataAtualizarPessoa data,
			 														 String login) {

		Pessoa pessoa = (Pessoa) pessoaRepository.findByLogin(login);
		
		//pessoa
		if (!utils.isNullOrEmptyString(data.login())) pessoa.setLogin(data.login());		
		if (!utils.isNullOrEmptyString(data.senha())) pessoa.setSenha(passwordUtil.encrypt(data.senha()));		
		if (!utils.isNullOrEmptyString(data.nome())) pessoa.setNome(data.nome());		
		if (!utils.isNullOrEmptyString(data.cpf())) pessoa.setCpf(data.cpf());		
		if (!utils.isNullOrEmptyString(data.email())) pessoa.setEmail(data.email());		
		if (!utils.isNullOrEmptyString(data.telefone())) pessoa.setTelefone(data.telefone());		
		
		pessoaRepository.save(pessoa);
		
		return ResponseEntity.ok(new DataDetalhesPessoa(pessoa));
	}


	public Object findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
