// Em: static/js/login.script.js
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');

    function showError(fieldId, message) {
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
        }
        if (inputElement) inputElement.style.borderColor = 'var(--cor-erro)';
    }

    function clearError(fieldId) {
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = '';
            errorElement.style.display = 'none';
        }
        if (inputElement) inputElement.style.borderColor = '#ccc';
    }

    if (loginForm) {
        loginForm.addEventListener('submit', async function(event) {
            event.preventDefault();
            let isValid = true;
            
            clearError('email');
            clearError('senha');

            const emailValue = emailInput.value.trim();
            const senhaValue = senhaInput.value.trim();

            if (emailValue === '') {
                showError('email', 'Por favor, informe seu email.');
                isValid = false;
            } else if (!/^\S+@\S+\.\S+$/.test(emailValue)) { 
                showError('email', 'Por favor, insira um email válido.');
                isValid = false;
            }

            if (senhaValue === '') {
                showError('senha', 'Por favor, informe sua senha.');
                isValid = false;
            }
            
            if (isValid) {
                const loginData = {
                    login: emailValue, // Backend espera 'login'
                    senha: senhaValue
                };

                try {
                    const response = await fetch(loginForm.action, { 
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(loginData)
                    });

                    if (response.ok) {
                        const data = await response.json(); // Espera TokenDataJWT
                        localStorage.setItem('authToken', data.token); 
                        localStorage.setItem('userRoles', JSON.stringify(data.roles));
                        localStorage.setItem('isUserLoggedInMarquesAutoDetail', 'true');
                        localStorage.setItem('userEmailMarquesAutoDetail', loginData.login); 
                        // Para 'userNameMarquesAutoDetail', o ideal seria que o endpoint de login retornasse o nome
                        // ou haver um endpoint /me para buscar dados do usuário após o login.
                        // Por enquanto, para simplificar, pode-se buscar o nome do usuário aqui ou deixar em branco.
                        // Se o seu PessoaRepository.findByLogin() puder ser usado no cliente após login seguro,
                        // ou se o token JWT contiver o nome.
                        
                        alert('Login bem-sucedido! Redirecionando...');
                        // Tenta pegar a URL base do th:href="@{/}" do link da logo
                        const homeLinkElement = document.querySelector('.login-branding-panel .logo-link');
                        const homeUrl = homeLinkElement ? homeLinkElement.href : '/'; // Fallback para "/"
                        window.location.href = homeUrl;
                    } else {
                        let errorText = `Erro (${response.status})`;
                        try {
                           const errorData = await response.json();
                           errorText = errorData.message || errorData.error || errorText;
                        } catch(e) {
                           // Se a resposta de erro não for JSON, use o statusText
                           errorText = response.statusText || errorText;
                        }
                        // O backend ClienteService.login pode lançar EmailNotConfirmedException
                        // e ErrorHandler trata isso devolvendo status 403 com a mensagem.
                        showError('senha', errorText); // Exibe o erro próximo ao campo de senha
                    }
                } catch (error) {
                    console.error('Falha na requisição de login:', error);
                    showError('senha', 'Falha na comunicação com o servidor.');
                }
            }
        });

        const inputs = loginForm.querySelectorAll('.form-group input');
        inputs.forEach(input => {
            input.addEventListener('input', () => {
                clearError(input.id);
            });
        });
    }

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