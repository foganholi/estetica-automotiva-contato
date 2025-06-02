package com.esteticaAutomotiva.domain.agenda;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.esteticaAutomotiva.domain.agenda.dto.DataDetalhesAgenda;
import com.esteticaAutomotiva.domain.agenda.enums.StatusAgenda;
import com.esteticaAutomotiva.domain.agenda.utils.CalcFeriados;


@Service
public class AgendaService {
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	
	//FUNCAO PARA RESERVAR HORARIO
	public Agenda reservaHorario(Long id) {
		
	    Agenda agenda = agendaRepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado."));

	    if (agenda.getDisponivel() != StatusAgenda.DISPONIVEL) {
	        throw new IllegalStateException("Horário não está disponível para agendamento.");
	    }

	    agenda.setDisponivel(StatusAgenda.OCUPADO);
	    return agendaRepository.save(agenda);
	}
	
	
	@Scheduled(cron = "0 0 1 1 * ?") // Todo dia 1 do mes as 01:00
	public void gerarAgendaMensal() {
			    
	    LocalDate primeiroDia = LocalDate.now().withDayOfMonth(1);
	    LocalDate ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth());


	    gerarHorariosDisponiveisParaPeriodo(primeiroDia, ultimoDia); //Gera uma nova lista de agenda
	    
	}
	
	public void gerarHorariosDisponiveisParaPeriodo(LocalDate inicio, LocalDate fim) {
		
	    List<LocalDate> feriados = CalcFeriados.obterFeriadosNacionais(inicio.getYear());
	    
	    for (LocalDate dia = inicio; !dia.isAfter(fim); dia = dia.plusDays(1)) {
	        
	        if (!feriados.isEmpty() && dia.getYear() != feriados.get(0).getYear()) {
	            feriados = CalcFeriados.obterFeriadosNacionais(dia.getYear());
	        }

	        DayOfWeek diaSemana = dia.getDayOfWeek();
	        boolean diaUtil = diaSemana != DayOfWeek.SUNDAY;
	        boolean naoEhFeriado = !feriados.contains(dia);

	        if (diaUtil && naoEhFeriado) {
	            for (int hora = 8; hora <= 16; hora += 2) {
	                LocalDateTime horario = LocalDateTime.of(dia, LocalTime.of(hora, 0));
	                if (!agendaRepository.existsHorarioDisponivel(horario)) {
	                    agendaRepository.save(new Agenda(horario));
	                }
	            }
	        }
	    }

	}

	public ResponseEntity<List<DataDetalhesAgenda>> listaAgendaDisponivel(){
		
		List<Agenda> lista = listaHorariosDisponiveis();
		
		if(lista == null) {
			throw new IllegalArgumentException("Sem agenda disponivel.");
		}
		
	    List<DataDetalhesAgenda> listaDTO = lista.stream()
	            .map(DataDetalhesAgenda::new)
	            .toList();

	    return ResponseEntity.ok(listaDTO);
	}
	
	public List<Agenda> listaHorariosDisponiveis() {
	    LocalDateTime umaHoraDepois = LocalDateTime.now().plusHours(1);
	    return agendaRepository.findStatusDisponivel(StatusAgenda.DISPONIVEL, umaHoraDepois);
	}


	public void mudarStatusAgenda(LocalDateTime data, StatusAgenda status) {
		Agenda agenda = agendaPorData(data);
		agenda.setDisponivel(status);
		
		agendaRepository.save(agenda);
		
	}
	
	public Agenda agendaPorData(LocalDateTime data) {
		Agenda agenda = agendaRepository.findByHorario(data);
		return agenda;
	}

}
