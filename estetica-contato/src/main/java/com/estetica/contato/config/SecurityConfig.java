package com.estetica.contato.config;

import com.estetica.contato.security.FiltroToken;
import com.estetica.contato.security.PontoEntradaAutenticacao;
// O ClienteDetalhesService é injetado pelo Spring Boot automaticamente se for um @Service e PasswordEncoder existir

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // Habilita diferentes tipos de segurança em métodos
public class SecurityConfig {

    @Autowired
    private FiltroToken filtroToken;

    @Autowired
    private PontoEntradaAutenticacao pontoEntradaAutenticacao;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(pontoEntradaAutenticacao))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/auth/**").permitAll() // Todos endpoints sob /api/auth são públicos
                    // Exemplo para seu ClienteController (se existir e você quiser proteger)
                    // .requestMatchers(HttpMethod.GET, "/api/clientes").hasRole("CLIENTE") // Apenas quem tem ROLE_CLIENTE pode listar
                    // .requestMatchers("/api/clientes/**").hasRole("ADMIN") // Apenas ADMIN pode fazer outras operações em clientes
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll() // Para documentação Swagger/OpenAPI
                    .anyRequest().authenticated()
            );

        http.addFilterBefore(filtroToken, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
