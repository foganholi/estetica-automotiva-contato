package com.esteticaAutomotiva.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// Importe PessoaRepository ou um serviço para buscar detalhes do usuário se necessário
import com.esteticaAutomotiva.domain.pessoa.Pessoa; // Sua entidade Pessoa
import com.esteticaAutomotiva.domain.pessoa.PessoaRepository; // Repositório

@Controller
@RequestMapping("/agendamento")
public class AgendamentoPageController {

    private final PessoaRepository pessoaRepository;

    public AgendamentoPageController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping("/novo")
    public String agendamentoPage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() ||
            (authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            return "redirect:/login"; // Usuário não autenticado, redireciona para login
        }

        String username = authentication.getName();
        UserDetails userDetails = pessoaRepository.findByLogin(username); // Assumindo que findByLogin retorna UserDetails

        if (userDetails instanceof Pessoa) {
            Pessoa pessoa = (Pessoa) userDetails;
            model.addAttribute("userNameDisplay", pessoa.getNome()); // Ou o campo apropriado para nome
            model.addAttribute("userEmailDisplay", pessoa.getEmail());
        } else {
            // Fallback ou tratamento de erro se o UserDetails não for da sua classe Pessoa
            model.addAttribute("userNameDisplay", username);
            model.addAttribute("userEmailDisplay", "Email não disponível");
        }
        
        // Você pode adicionar um objeto de formulário ao modelo se for submeter via Spring MVC tradicional,
        // mas seu script JS faz a submissão AJAX.
        // model.addAttribute("agendamentoForm", new SeuAgendamentoDTO());

        return "agendamento"; // Resolve para templates/agendamento.html
    }
}
