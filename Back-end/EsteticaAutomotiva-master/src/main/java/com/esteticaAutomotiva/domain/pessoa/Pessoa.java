package com.esteticaAutomotiva.domain.pessoa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.esteticaAutomotiva.domain.pessoa.dto.DataRegistroPessoa;
import com.esteticaAutomotiva.domain.roles.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoa")
public abstract class Pessoa implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
    private LocalDateTime dataValidacao;
	
	private String login;
	
	private String senha;
	
    @Column(name = "email_confirmado")
    private boolean emailConfirmado = false;
	
	private String tokenConfirmacao;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
	
	public Pessoa() {}
	
    public Pessoa(DataRegistroPessoa data,Role roles) {
    	this.nome = data.nome();
    	this.roles = new HashSet<>();
    	this.roles.add(roles);
    	this.cpf = data.cpf();
    	this.email = data.email();
    	this.telefone = data.telefone();
    	this.login = data.login();
    	this.senha = data.senha();
    	this.dataValidacao = LocalDateTime.now();
	}

	public void gerarTokenConfirmacao() {
        this.tokenConfirmacao = UUID.randomUUID().toString();
    }
	
    public boolean isTokenValido() {
        LocalDateTime prazoExpiracao = this.dataValidacao.plusHours(24);
        return LocalDateTime.now().isAfter(prazoExpiracao);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDateTime getDataValidacao() {
		return dataValidacao;
	}

	public void setDataValidacao(LocalDateTime dataValidacao) {
		this.dataValidacao = dataValidacao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isEmailConfirmado() {
		return emailConfirmado;
	}

	public void setEmailConfirmado(boolean emailConfirmado) {
		this.emailConfirmado = emailConfirmado;
	}

	public String getTokenConfirmacao() {
		return tokenConfirmacao;
	}

	public void setTokenConfirmacao(String tokenConfirmacao) {
		this.tokenConfirmacao = tokenConfirmacao;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
