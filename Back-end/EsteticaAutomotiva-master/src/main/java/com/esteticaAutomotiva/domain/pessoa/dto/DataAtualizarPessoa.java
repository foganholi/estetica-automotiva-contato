package com.esteticaAutomotiva.domain.pessoa.dto;

public record DataAtualizarPessoa(String login,
								  String senha,
								  String nome,
								  String cpf,
								  String email,
								  String telefone) {}
