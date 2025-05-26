package com.estetica.contato.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Opcional, se o nome da tabela for diferente de "cliente"
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; // Alternativa ao @NotEmpty
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes") // Você pode nomear sua tabela como "clientes"
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    // Adicionar outros campos que você já tinha para Cliente (ex: telefone, endereco)
    // private String telefone;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "Formato de e-mail inválido")
    @Column(unique = true, nullable = false) // E-mail será usado para login, deve ser único e não nulo
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") // Apenas validação de tamanho, o hash é responsabilidade do backend
    @Column(nullable = false)
    private String senha; // Esta será a senha HASHED

    // Opcional: campo para roles/perfis
    // private String roles; // Ex: "ROLE_CLIENTE,ROLE_USUARIO"

    // Construtores
    public Cliente() {
    }

    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters para todos os campos (id, nome, email, senha, etc.)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // public String getRoles() {
    //     return roles;
    // }

    // public void setRoles(String roles) {
    //     this.roles = roles;
    // }

    // Considere adicionar toString(), equals() e hashCode() se necessário
}
