package com.esteticaAutomotiva.domain.pessoa.dto;

import jakarta.validation.constraints.NotBlank;

public record DataAutentication(@NotBlank
							    String login,
							    @NotBlank
							    String senha) {}
