package com.esteticaAutomotiva.domain.agendamento.dto;

import java.time.LocalDateTime;

import com.esteticaAutomotiva.domain.agendamento.Agendamento;
import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataDetalhesCliente;

public record DataDetalhesAgendamento(Long id,
									  LocalDateTime data,
									  LocalDateTime criadoEm,
									  LocalDateTime atualizadoEm,
									  DataDetalhesCliente dataDetalhesCliente) {

	public DataDetalhesAgendamento(Agendamento agendamento) {
		this(agendamento.getId(),
				agendamento.getData(),
				agendamento.getCriadoEm(),
				agendamento.getAtualizadoEm(),
				new DataDetalhesCliente(agendamento.getCliente()));
	}

}
