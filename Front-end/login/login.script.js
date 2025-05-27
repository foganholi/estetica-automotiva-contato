// Em: login/login.script.js

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('email'); // Mantido para clareza, se usado
    const senhaInput = document.getElementById('senha'); // Mantido para clareza, se usado

    // ... (suas funções showError e clearError como antes) ...
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

    function clearError(fieldId) {
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = '';
            errorElement.style.display = 'none';
        }
        if (inputElement) {
            inputElement.style.borderColor = '#ccc'; 
        }
    }


    if (loginForm) {
        // ... (seu código de validação do loginForm existente) ...
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); 
            let isValid = true;

            clearError('email');
            clearError('senha');

            const email = emailInput.value.trim(); // Usando as vars já definidas
            const senha = senhaInput.value.trim(); // Usando as vars já definidas

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
            
            if (isValid) {
                alert('Login "simulado" com sucesso! Redirecionando...'); 
                window.location.href = '../index.html'; 
            }
        });

        const inputs = loginForm.querySelectorAll('.form-group input');
        inputs.forEach(input => {
            input.addEventListener('input', () => {
                clearError(input.id);
            });
        });
    }

    // Funcionalidade de mostrar/ocultar senha
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