package com.estetica.contato.service;

import com.estetica.contato.model.Cliente;
import com.estetica.contato.payload.CadastroRequest;
import com.estetica.contato.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional; // Certifique-se de que Optional está importado

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository; // Seu repositório já está aqui

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Autowired
    // private EmailService emailService;

    @Transactional
    public Cliente cadastrarCliente(CadastroRequest cadastroRequest) {
        if (clienteRepository.existsByEmail(cadastroRequest.getEmail())) {
            throw new RuntimeException("Erro: O e-mail informado já está em uso!");
        }
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(cadastroRequest.getNome());
        novoCliente.setEmail(cadastroRequest.getEmail());
        novoCliente.setSenha(passwordEncoder.encode(cadastroRequest.getSenha()));
        Cliente clienteSalvo = clienteRepository.save(novoCliente);
        // ... (lógica de e-mail) ...
        return clienteSalvo;
    }

    // NOVO MÉTODO ADICIONADO:
    @Transactional(readOnly = true) // readOnly = true para operações de apenas leitura
    public Optional<Cliente> buscarClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // Outros métodos que você já tem ou possa adicionar...
}