package com.esteticaAutomotiva.infra.security;

import java.util.List;

public record TokenDataJWT(String Token,
		   				   List<String> roles) {}
