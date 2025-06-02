package com.esteticaAutomotiva.domain.pessoa.cliente.dto;

import com.esteticaAutomotiva.domain.pessoa.cliente.Cliente;
import com.esteticaAutomotiva.domain.pessoa.dto.DataDetalhesPessoa;

public record DataDetalhesCliente(DataDetalhesPessoa dataDetalhesPessoa,
								  Long quantidadeAgendamento) {

	public DataDetalhesCliente(Cliente cliente) {
		this(new DataDetalhesPessoa(cliente),
			 cliente.getQuantidadeAgendamento());
	}
}
