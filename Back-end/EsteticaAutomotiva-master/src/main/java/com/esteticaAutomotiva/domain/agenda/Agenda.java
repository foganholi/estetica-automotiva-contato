package com.esteticaAutomotiva.domain.agenda;

import java.time.LocalDateTime;

import com.esteticaAutomotiva.domain.agenda.enums.StatusAgenda;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    private StatusAgenda disponivel;

    private LocalDateTime criadoEm = LocalDateTime.now();
    
	public Agenda() {}
    
	public Agenda(LocalDateTime horario) {
		this.horario = horario;
		this.disponivel = StatusAgenda.DISPONIVEL;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getHorario() {
		return horario;
	}

	public void setHorario(LocalDateTime horario) {
		this.horario = horario;
	}

	public StatusAgenda getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(StatusAgenda disponivel) {
		this.disponivel = disponivel;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}
    
}
