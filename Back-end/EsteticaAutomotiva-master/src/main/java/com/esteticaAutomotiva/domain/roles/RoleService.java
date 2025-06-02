package com.esteticaAutomotiva.domain.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findByNameRole(String role) {
		return roleRepository.findByName(role).get();
	}

}
