// Em: cadastro/cadastro.script.js

document.addEventListener('DOMContentLoaded', function() {
    const cadastroForm = document.getElementById('cadastroForm');

    // Função para mostrar erro (mantenha como estava)
    function showError(fieldId, message) {
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
        }
        if (inputElement) {
            inputElement.style.borderColor = 'var(--cor-erro)';
        }
    }

    // Função para limpar erro (mantenha como estava)
    function clearError(fieldId) {
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = '';
            errorElement.style.display = 'none';
        }
        if (inputElement) {
            inputElement.style.borderColor = '#ccc'; // Cor padrão da borda
        }
    }

    if (cadastroForm) {
        cadastroForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Sempre previna o envio padrão inicialmente
            let isValid = true;

            clearError('nome');
            clearError('email');
            clearError('senha');
            clearError('confirmarSenha');

            const nomeInput = document.getElementById('nome');
            const emailInput = document.getElementById('email');
            const senhaInput = document.getElementById('senha');
            const confirmarSenhaInput = document.getElementById('confirmarSenha');

            const nome = nomeInput.value.trim();
            const email = emailInput.value.trim();
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

            // Se a validação do frontend passar, redirecionamos para login.html
            // Em um cenário real, aqui você enviaria os dados para o backend para re   gistrar o usuário.
            if (isValid) {
                // Simulação de cadastro bem-sucedido
                alert('Cadastro "simulado" com sucesso! Redirecionando para o login...'); // Opcional
                window.location.href = '../login/login.html'; // Redireciona para a página de login
            }
        });

        const inputs = cadastroForm.querySelectorAll('.form-group input');
        inputs.forEach(input => {
            input.addEventListener('input', () => {
                clearError(input.id);
                if (input.id === 'confirmarSenha' || input.id === 'senha') {
                    const senhaVal = document.getElementById('senha').value;
                    const confirmarSenhaVal = document.getElementById('confirmarSenha').value;
                    if (senhaVal === confirmarSenhaVal) {
                        clearError('senha');
                        clearError('confirmarSenha');
                    }
                }
            });
        });
    }
});