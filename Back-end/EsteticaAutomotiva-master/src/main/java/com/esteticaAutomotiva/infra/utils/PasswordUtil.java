package com.esteticaAutomotiva.infra.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
	
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }
}
