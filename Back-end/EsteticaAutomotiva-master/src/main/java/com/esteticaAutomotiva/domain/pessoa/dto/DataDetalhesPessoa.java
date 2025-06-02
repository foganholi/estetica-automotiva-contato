package com.esteticaAutomotiva.domain.pessoa.dto;

import com.esteticaAutomotiva.domain.pessoa.Pessoa;

public record DataDetalhesPessoa(String nome,
								 String cpf,
								 String email,
								 String telefone) {

	public DataDetalhesPessoa(Pessoa pessoa) {
		this(pessoa.getNome(),pessoa.getCpf(),pessoa.getEmail(),pessoa.getTelefone());
	}

}

