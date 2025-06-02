package com.esteticaAutomotiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esteticaAutomotiva.domain.pessoa.PessoaService;
import com.esteticaAutomotiva.domain.pessoa.dto.DataAtualizarPessoa;
import com.esteticaAutomotiva.domain.pessoa.dto.DataDetalhesPessoa;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
    @Transactional
    @PutMapping("/atualizar")
    public ResponseEntity<DataDetalhesPessoa> atualizar(@RequestBody @Valid DataAtualizarPessoa data,
																	  Authentication authentication) {
    	return pessoaService.atualizarPessoa(data,authentication.getName());
    }
	
	
}
