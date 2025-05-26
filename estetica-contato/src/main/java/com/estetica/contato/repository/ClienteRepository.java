package com.estetica.contato.repository;

import com.estetica.contato.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para buscar um cliente pelo e-mail (necessário para login e UserDetailsService)
    Optional<Cliente> findByEmail(String email);

    // Método para verificar se um e-mail já existe (útil no cadastro)
    Boolean existsByEmail(String email);
}
