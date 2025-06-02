package com.esteticaAutomotiva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esteticaAutomotiva.domain.mail.EmailService;


@RestController
@RequestMapping("/auth")
public class ConfirmacaoEmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/confirmar-email")
    public ResponseEntity<?> confirmarEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(emailService.confirmarEmail(token));
    }
}
