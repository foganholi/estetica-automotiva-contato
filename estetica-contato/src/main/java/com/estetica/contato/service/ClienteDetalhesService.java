package com.estetica.contato.service;

import com.estetica.contato.model.Cliente;
import com.estetica.contato.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; // User do Spring Security
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors; // Se for usar roles a partir de uma string

@Service
public class ClienteDetalhesService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional // Garante que a entidade Cliente seja carregada dentro de uma transação
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com o e-mail: " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();
        // Se você tiver um campo 'roles' na sua entidade Cliente (ex: "ROLE_CLIENTE,ROLE_ADMIN")
        // String rolesDoCliente = cliente.getRoles();
        // if (rolesDoCliente != null && !rolesDoCliente.isEmpty()) {
        //     authorities = Arrays.stream(rolesDoCliente.split(","))
        //                        .map(SimpleGrantedAuthority::new)
        //                        .collect(Collectors.toList());
        // } else {
        //     // Adiciona uma role padrão se não houver nenhuma definida
             authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        // }


        return new User(cliente.getEmail(), cliente.getSenha(), authorities);
    }

    // Método para construir UserDetails a partir de um Cliente (usado no AuthController)
    @Transactional
    public UserDetails buildUserDetails(Cliente cliente) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // String rolesDoCliente = cliente.getRoles();
        // if (rolesDoCliente != null && !rolesDoCliente.isEmpty()) {
        //     authorities = Arrays.stream(rolesDoCliente.split(","))
        //                        .map(SimpleGrantedAuthority::new)
        //                        .collect(Collectors.toList());
        // } else {
             authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        // }
        return new User(cliente.getEmail(), cliente.getSenha(), authorities);
    }
}
