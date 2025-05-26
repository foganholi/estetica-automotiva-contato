// Em: login/login.script.js

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');

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

    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Sempre previna o envio padrão inicialmente
            let isValid = true;

            clearError('email');
            clearError('senha');

            const emailInput = document.getElementById('email');
            const senhaInput = document.getElementById('senha');
            const email = emailInput.value.trim();
            const senha = senhaInput.value.trim();

            if (email === '') {
                showError('email', 'Por favor, informe seu email.');
                isValid = false;
            } else if (!/\S+@\S+\.\S+/.test(email)) {
                showError('email', 'Por favor, insira um email válido.');
                isValid = false;
            }

            if (senha === '') {
                showError('senha', 'Por favor, informe sua senha.');
                isValid = false;
            }

            // Se a validação do frontend passar, redirecionamos para a index.html
            // Em um cenário real, aqui você enviaria os dados para o backend
            // e o backend faria o redirecionamento após autenticação bem-sucedida.
            if (isValid) {
                // Simulação de login bem-sucedido
                alert('Login "simulado" com sucesso! Redirecionando...'); // Opcional: para feedback
                window.location.href = '../index.html'; // Redireciona para a página principal
            }
        });

        const inputs = loginForm.querySelectorAll('.form-group input');
        inputs.forEach(input => {
            input.addEventListener('input', () => {
                clearError(input.id);
            });
        });
    }
});