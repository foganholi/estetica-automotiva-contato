-- Tabela Pessoa
CREATE TABLE pessoa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    data_validacao DATE,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    email_confirmado BOOLEAN DEFAULT FALSE,
    token_confirmacao VARCHAR(255)
);

-- Tabela Cliente
CREATE TABLE cliente (
    id BIGINT PRIMARY KEY,
    quantidade_agendamento BIGINT NOT NULL,
    CONSTRAINT fk_cliente_pessoa FOREIGN KEY (id) REFERENCES pessoa(id)
);

-- Tabela Roles
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela de relacionamento de usuario e roles
CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES pessoa(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Agenda de horarios disponiveis
CREATE TABLE agenda (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    horario DATETIME NOT NULL,
    disponivel VARCHAR(20) NOT NULL,
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Agendamento feito pelo cliente
CREATE TABLE agendamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    data DATETIME,
    status_agendamento VARCHAR(50),
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_consulta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Insercao inicial das roles
INSERT INTO roles (role_name) VALUES ('ADMIN'), ('PROFISSIONAL'), ('CLIENTE');
