package com.esteticaAutomotiva.domain.agenda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esteticaAutomotiva.domain.agenda.enums.StatusAgenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>{

	@Query("""
		    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
		    FROM Agenda a
		    WHERE a.horario = :horarioConsulta
		      AND a.disponivel = 'DISPONIVEL'
		""")
		boolean existsHorarioDisponivel(@Param("horarioConsulta") LocalDateTime horarioConsulta);

	@Query("""
		    SELECT a FROM Agenda a
		    WHERE a.id = :id
		      AND a.disponivel = 'DISPONIVEL'
		""")
		Agenda getAgendaHorarioDisponivel(@Param("id")  Long id);

	Agenda findByHorario(LocalDateTime data);

	@Query("""
		    SELECT a FROM Agenda a
		    WHERE a.disponivel = :disponivel
		      AND a.horario > :umaHoraDepois
		    ORDER BY a.horario ASC
		""")
	List<Agenda> findStatusDisponivel(StatusAgenda disponivel, LocalDateTime umaHoraDepois);
	
}
