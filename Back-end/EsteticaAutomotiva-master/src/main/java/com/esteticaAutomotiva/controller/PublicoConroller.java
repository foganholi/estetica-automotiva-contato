package com.esteticaAutomotiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.esteticaAutomotiva.domain.pessoa.PessoaService;
import com.esteticaAutomotiva.domain.pessoa.cliente.ClienteService;
import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataDetalhesCliente;
import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataRegistroCliente;
import com.esteticaAutomotiva.domain.pessoa.dto.DataAutentication;
import com.esteticaAutomotiva.infra.security.TokenDataJWT;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publico")
public class PublicoConroller {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ClienteService clienteService;
	
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<TokenDataJWT> login(@RequestBody @Valid DataAutentication data) {
        return pessoaService.login(data);
    }
	
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<DataDetalhesCliente> registerCliente(@RequestBody @Valid DataRegistroCliente data,
	UriComponentsBuilder uriBuilder ) {
    	return clienteService.registroCliente(data, uriBuilder);
    }

}
