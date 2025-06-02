package com.esteticaAutomotiva.domain.pessoa.cliente;

import com.esteticaAutomotiva.domain.pessoa.Pessoa;
import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataRegistroCliente;
import com.esteticaAutomotiva.domain.roles.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "quantidade_agendamento")
	private Long quantidadeAgendamento;
	
	
    public Cliente() {
        super();
    }

	public Cliente(DataRegistroCliente data, Role role) {
		super(data.dataRegistroPessoa(),
			 role);
		this.quantidadeAgendamento = 0L;
	}

	public Long getQuantidadeAgendamento() {
		return quantidadeAgendamento;
	}

	public void setQuantidadeAgendamento(Long quantidadeAgendamento) {
		this.quantidadeAgendamento = quantidadeAgendamento;
	}	
	
}
