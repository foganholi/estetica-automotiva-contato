package com.esteticaAutomotiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// Importe outras classes necessárias, como Authentication e SecurityContextHolder, se for usar

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        // Lógica para adicionar dados ao modelo, se necessário
        return "index"; // Retorna o nome do template Thymeleaf (templates/index.html)
    }
}