// Em: cadastro/cadastro.script.js

document.addEventListener('DOMContentLoaded', function() {
    const cadastroForm = document.getElementById('cadastroForm');
    const nomeInput = document.getElementById('nome');
    const emailInput = document.getElementById('email');
    const dataNascimentoInput = document.getElementById('dataNascimento');
    const cpfInput = document.getElementById('cpf');
    const senhaInput = document.getElementById('senha');
    const confirmarSenhaInput = document.getElementById('confirmarSenha');

    // ... (suas funções showError, clearError e máscara de CPF como antes) ...
    function showError(fieldId, message) { /* ... (código da função) ... */ }
    function clearError(fieldId) { /* ... (código da função) ... */ }

    if (cpfInput) {
        cpfInput.addEventListener('input', function (e) {
            let value = e.target.value.replace(/\D/g, ''); 
            const maxLength = 11;
            value = value.substring(0, maxLength); 

            let maskedValue = '';
            if (value.length > 0) {
                maskedValue = value.substring(0,3);
            }
            if (value.length > 3) {
                maskedValue += '.' + value.substring(3,6);
            }
            if (value.length > 6) {
                maskedValue += '.' + value.substring(6,9);
            }
            if (value.length > 9) {
                maskedValue += '-' + value.substring(9,11);
            }
            e.target.value = maskedValue;
        });
    }

    if (cadastroForm) {
        // ... (seu código de validação do cadastroForm existente) ...
        cadastroForm.addEventListener('submit', function(event) {
            event.preventDefault();
            let isValid = true;

            clearError('nome');
            clearError('email');
            clearError('dataNascimento'); 
            clearError('cpf');            
            clearError('senha');
            clearError('confirmarSenha');

            const nome = nomeInput.value.trim();
            const email = emailInput.value.trim();
            const dataNascimento = dataNascimentoInput.value; 
            const cpf = cpfInput.value; 
            const senha = senhaInput.value;
            const confirmarSenha = confirmarSenhaInput.value;

            if (nome === '') {
                showError('nome', 'Por favor, informe seu nome completo.');
                isValid = false;
            }

            if (email === '') {
                showError('email', 'Por favor, informe seu email.');
                isValid = false;
            } else if (!/\S+@\S+\.\S+/.test(email)) {
                showError('email', 'Por favor, insira um email válido.');
                isValid = false;
            }

            if (dataNascimento === '') {
                showError('dataNascimento', 'Por favor, informe sua data de nascimento.');
                isValid = false;
            } else {
                const hoje = new Date();
                const dataNascObj = new Date(dataNascimento + "T00:00:00");
                hoje.setHours(0, 0, 0, 0);
                if (dataNascObj >= hoje) {
                    showError('dataNascimento', 'A data de nascimento não pode ser no futuro ou hoje.');
                    isValid = false;
                }
            }

            if (cpf === '') { 
                showError('cpf', 'Por favor, informe seu CPF.');
                isValid = false;
            } else if (!cpfInput.checkValidity()) { 
                 showError('cpf', cpfInput.title || 'Formato de CPF inválido. Use 000.000.000-00.');
                 isValid = false;
            }
            
            if (senha.length < 6) {
                showError('senha', 'A senha deve ter pelo menos 6 caracteres.');
                isValid = false;
            }

            if (confirmarSenha === '') {
                showError('confirmarSenha', 'Por favor, confirme sua senha.');
                isValid = false;
            } else if (senha !== confirmarSenha) {
                showError('confirmarSenha', 'As senhas não coincidem.');
                showError('senha', 'As senhas não coincidem.');
                isValid = false;
            }
            
            if (isValid) {
                alert('Validação do cliente OK! Enviando para o backend (simulado)...');
                window.location.href = '../login/login.html';
            }
        });
        
        [nomeInput, emailInput, dataNascimentoInput, cpfInput, senhaInput, confirmarSenhaInput].forEach(input => {
            if (input) { 
                input.addEventListener('input', () => {
                    clearError(input.id);
                    if (input.id === 'confirmarSenha' || input.id === 'senha') {
                        if (senhaInput.value === confirmarSenhaInput.value) {
                            clearError('senha');
                            clearError('confirmarSenha');
                        }
                    }
                    if (input.id === 'cpf') {
                        if (input.checkValidity()) {
                            clearError('cpf');
                        }
                    }
                });
            }
        });
    }

    // Funcionalidade de mostrar/ocultar senha (ADICIONADO AQUI TAMBÉM)
    const togglePasswordIcons = document.querySelectorAll('.toggle-password-visibility');
    togglePasswordIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            const targetInputId = this.getAttribute('data-target');
            const targetInput = document.getElementById(targetInputId);

            if (targetInput) {
                if (targetInput.type === 'password') {
                    targetInput.type = 'text';
                    this.classList.remove('fa-eye');
                    this.classList.add('fa-eye-slash');
                } else {
                    targetInput.type = 'password';
                    this.classList.remove('fa-eye-slash');
                    this.classList.add('fa-eye');
                }
            }
        });
    });
});