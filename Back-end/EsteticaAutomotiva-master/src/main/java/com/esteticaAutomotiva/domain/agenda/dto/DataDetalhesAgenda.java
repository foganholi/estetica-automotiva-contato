package com.esteticaAutomotiva.domain.agenda.dto;

import java.time.LocalDateTime;

import com.esteticaAutomotiva.domain.agenda.Agenda;
import com.esteticaAutomotiva.domain.agenda.enums.StatusAgenda;

public record DataDetalhesAgenda(Long id,
	     						LocalDateTime horario,
	     						StatusAgenda disponivel,
	     						LocalDateTime criadoEm) {

	public DataDetalhesAgenda(Agenda agenda) {
		this(agenda.getId(),agenda.getHorario(),agenda.getDisponivel(),agenda.getCriadoEm());
	}
}