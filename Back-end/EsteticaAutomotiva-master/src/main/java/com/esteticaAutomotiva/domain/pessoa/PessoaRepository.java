package com.esteticaAutomotiva.domain.pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	UserDetails findByLogin(String username);
	
	boolean existsByEmailOrLogin(String email, String login);

	@Query("SELECT p FROM Pessoa p WHERE " +
		   "(p.email = :emailOuLogin OR p.login = :emailOuLogin) " +
		   "AND p.emailConfirmado = true")
	Optional<Pessoa> findByEmailOrLoginWithConfirmedEmail(
		    @Param("emailOuLogin") String emailOuLogin);
	
	Pessoa findByTokenConfirmacao(String token);

	Object findByEmail(String email);

	Object findByCpf(String cpf);

}
