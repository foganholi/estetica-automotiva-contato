package com.esteticaAutomotiva.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esteticaAutomotiva.domain.agenda.AgendaService;


@RestController
@RequestMapping("/agenda")
public class AgendaController {
	
	@Autowired
	private AgendaService agendaService;
	
	@GetMapping("/horarioDisponivel")
	public ResponseEntity<?> listaAgendaDisponivel() {
	    return ResponseEntity.ok(agendaService.listaAgendaDisponivel());
	}
	
	@Transactional
	@PostMapping("/criar")
	public ResponseEntity<?> criarAgendaDisponivel() {
		
		//usar "em producao" funciona todo dia do mes 1
		
//	    LocalDate primeiroDia = LocalDate.now();
//	    LocalDate ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth());
//		agendaService.gerarHorariosDisponiveisParaPeriodo(primeiroDia,ultimoDia);
		
		//forma de usar para teste pega o proximo mes
		
		LocalDate hoje = LocalDate.now();
		LocalDate primeiroDiaProximoMes = hoje.plusMonths(1).withDayOfMonth(1);
		LocalDate ultimoDiaProximoMes = primeiroDiaProximoMes.withDayOfMonth(primeiroDiaProximoMes.lengthOfMonth());
	    agendaService.gerarHorariosDisponiveisParaPeriodo(primeiroDiaProximoMes,ultimoDiaProximoMes);
	    
	    return ResponseEntity.ok("Agenda Gerada !");
	}

}
