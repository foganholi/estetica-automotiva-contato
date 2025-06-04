package com.esteticaAutomotiva.controller;

import com.esteticaAutomotiva.domain.agendamento.AgendamentoService;
import com.esteticaAutomotiva.domain.agendamento.dto.DataDetalhesAgendamento;
import com.esteticaAutomotiva.domain.agendamento.enums.StatusAgendamento; // Se aplicável para filtro inicial
import com.esteticaAutomotiva.domain.pessoa.Pessoa;
import com.esteticaAutomotiva.domain.pessoa.PessoaRepository;
import org.springframework.http.ResponseEntity; // Para o método de agendamentos
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/perfil")
public class PerfilPageController {

    private final PessoaRepository pessoaRepository;
    private final AgendamentoService agendamentoService; // Injete o serviço de agendamento

    public PerfilPageController(PessoaRepository pessoaRepository, AgendamentoService agendamentoService) {
        this.pessoaRepository = pessoaRepository;
        this.agendamentoService = agendamentoService;
    }

    @GetMapping("/editar")
    public String perfilPage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() ||
            (authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UserDetails userDetails = pessoaRepository.findByLogin(username);

        if (userDetails instanceof Pessoa) {
            Pessoa pessoa = (Pessoa) userDetails;
            model.addAttribute("nomeCompleto", pessoa.getNome());
            model.addAttribute("email", pessoa.getEmail());
            model.addAttribute("cpf", pessoa.getCpf());
            // O DTO DataRegistroPessoa não tem dataNascimento, nem a entidade Pessoa diretamente.
            // Se você armazenar dataNascimento, precisará buscá-la.
            // Por enquanto, o perfil.script.js carrega um valor mock para dataNascimento.
            // model.addAttribute("dataNascimento", pessoa.getDataNascimento()); // Se existir
        } else {
            model.addAttribute("nomeCompleto", username);
            // Adicione valores padrão ou mensagens de erro se os detalhes não puderem ser carregados
        }

        // Carregar agendamentos do usuário
        // O perfil.script.js atualmente carrega do localStorage. Para dados reais:
        List<DataDetalhesAgendamento> meusAgendamentos = new ArrayList<>();
        try {
            // Exemplo: buscar todos os agendamentos pendentes e confirmados
            // O método agendamentos no AgendamentoService retorna ResponseEntity. Precisamos do corpo.
            ResponseEntity<?> responsePendente = agendamentoService.agendamentos(username, StatusAgendamento.MARCADO); // Ou o status que você usa para "Pendente/Confirmado"
            if (responsePendente.getStatusCode().is2xxSuccessful() && responsePendente.getBody() instanceof List) {
                meusAgendamentos.addAll((List<DataDetalhesAgendamento>) responsePendente.getBody());
            }
            // Você pode querer buscar outros status também e combinar ou filtrar
        } catch (Exception e) {
            // Lidar com exceções ao buscar agendamentos
            System.err.println("Erro ao buscar agendamentos para o perfil: " + e.getMessage());
        }
        model.addAttribute("meusAgendamentos", meusAgendamentos);
        model.addAttribute("agendamentosLoadingMsg", meusAgendamentos.isEmpty() && true); // Para controlar a msg de loading/vazio

        return "perfil"; // Resolve para templates/perfil.html
    }
}
