package com.esteticaAutomotiva.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// Importe DTOs e outras classes necessárias, se for usar

import com.esteticaAutomotiva.domain.pessoa.cliente.dto.DataRegistroCliente;
import com.esteticaAutomotiva.domain.pessoa.dto.DataRegistroPessoa;

@Controller
public class AuthController {

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
               authentication.isAuthenticated() &&
               !(authentication.getPrincipal() instanceof String &&
                 authentication.getPrincipal().equals("anonymousUser"));
    }

    @GetMapping("/login")
    public String loginPage(Model model) { // Adicionado Model para possíveis mensagens
        if (isAuthenticated()) {
            return "redirect:/"; // Se já logado, redireciona para home
        }
        // Para exibir mensagens de erro de login passadas via parâmetro pela configuração de segurança (se aplicável)
        // ou mensagens de logout.
        if (model.containsAttribute("error")) { // Exemplo, se o Spring Security redirecionar com ?error
            model.addAttribute("loginError", "Credenciais inválidas ou e-mail não confirmado.");
        }
        if (model.containsAttribute("logout")) { // Exemplo, se o Spring Security redirecionar com ?logout
            model.addAttribute("logoutSuccess", "Você foi desconectado com sucesso.");
        }
        return "login"; // Resolve para templates/login.html
    }

    @GetMapping("/register")
public String registerPage(Model model) {
    if (isAuthenticated()) { // Método helper para verificar autenticação
        return "redirect:/";
    }
    // Garante que o objeto para o form binding está presente
    if (!model.containsAttribute("dataRegistroCliente")) {
        model.addAttribute("dataRegistroCliente", new DataRegistroCliente(
            new DataRegistroPessoa("", "", "", "", "", "") // Inicialize como necessário
        ));
    }
    return "cadastro"; // Resolve para templates/cadastro.html
}


}