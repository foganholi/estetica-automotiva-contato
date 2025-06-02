package com.esteticaAutomotiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esteticaAutomotiva.domain.agenda.dto.DataRegistroAgenda;
import com.esteticaAutomotiva.domain.agendamento.AgendamentoService;
import com.esteticaAutomotiva.domain.agendamento.dto.DataAtualizarAgendamento;
import com.esteticaAutomotiva.domain.agendamento.enums.StatusAgendamento;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService agendamentoService;
	
    @Transactional
    @PostMapping("/agendar")
    public ResponseEntity<?> agendar(@RequestBody @Valid DataRegistroAgenda dataRegistroConsulta,
		       																		Authentication authentication) {
        return ResponseEntity.ok(agendamentoService.agendar(dataRegistroConsulta, authentication.getName()));        
    }
    
    @Transactional
    @PutMapping("/reagendar")
    public ResponseEntity<?> reagendar(@RequestBody @Valid DataAtualizarAgendamento dataAtualizarAgendamento,
		       																		Authentication authentication) {
        return ResponseEntity.ok(agendamentoService.reagendar(dataAtualizarAgendamento, authentication.getName()));        
    }
    
    @GetMapping("/agendas/{status}")
    public ResponseEntity<?> agendamentos(@PathVariable StatusAgendamento status,
    													Authentication authentication) {
        return ResponseEntity.ok(agendamentoService.agendamentos(authentication.getName(),status));        
    }
    
    @Transactional
    @DeleteMapping("/agendas/{id}")
    public ResponseEntity<?> deletarAgendamentos(@PathVariable Long id,
    													Authentication authentication) {
        return ResponseEntity.ok(agendamentoService.cancelarAgendamento(authentication.getName(),id));        
    }

}
