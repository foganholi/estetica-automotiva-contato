// Em: cadastro/cadastro.script.js
document.addEventListener('DOMContentLoaded', function() {
    const cadastroForm = document.getElementById('cadastroForm');
    const nomeInput = document.getElementById('nome');
    const emailInput = document.getElementById('email');
    const telefoneInput = document.getElementById('telefone'); // Certifique-se que este ID existe no seu HTML
    const dataNascimentoInput = document.getElementById('dataNascimento');
    const cpfInput = document.getElementById('cpf');
    const senhaInput = document.getElementById('senha');
    const confirmarSenhaInput = document.getElementById('confirmarSenha');

    // Funções auxiliares para mostrar e limpar erros
    function showError(fieldId, message) {
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
        }
        if (inputElement) {
            inputElement.style.borderColor = 'var(--cor-erro)';
            inputElement.classList.add('input-error');
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
            inputElement.classList.remove('input-error');
        }
    }

    // Máscara para CPF
    if (cpfInput) {
        cpfInput.addEventListener('input', function (e) {
            let value = e.target.value.replace(/\D/g, '');
            const maxLength = 11;
            value = value.substring(0, maxLength);
            let maskedValue = '';
            if (value.length > 0) maskedValue = value.substring(0,3);
            if (value.length > 3) maskedValue += '.' + value.substring(3,6);
            if (value.length > 6) maskedValue += '.' + value.substring(6,9);
            if (value.length > 9) maskedValue += '-' + value.substring(9,11);
            e.target.value = maskedValue;
        });
    }

    // Funcionalidade de mostrar/ocultar senha
    const togglePasswordIcons = document.querySelectorAll('.toggle-password-visibility');
    togglePasswordIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            const targetInputId = this.getAttribute('data-target');
            const targetInput = document.getElementById(targetInputId);
            if (targetInput) {
                targetInput.type = targetInput.type === 'password' ? 'text' : 'password';
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            }
        });
    });

    if (cadastroForm) {
        cadastroForm.addEventListener('submit', async function(event) {
            event.preventDefault();
            let isValid = true;

            const fieldsToClear = ['nome', 'email', 'telefone', 'dataNascimento', 'cpf', 'senha', 'confirmarSenha'];
            fieldsToClear.forEach(clearError);

            const nome = nomeInput.value.trim();
            const email = emailInput.value.trim();
            const telefone = telefoneInput ? telefoneInput.value.trim().replace(/\D/g, '') : "";
            const dataNascimento = dataNascimentoInput.value;
            const cpf = cpfInput.value.replace(/\D/g, '');
            const senha = senhaInput.value;
            const confirmarSenha = confirmarSenhaInput.value;
            const loginValue = email; // Ajuste se o login for diferente do email

            // --- Validações ---
            if (nome === '') { showError('nome', 'Nome completo é obrigatório.'); isValid = false; }
            if (email === '') { showError('email', 'Email é obrigatório.'); isValid = false; }
            else if (!/^\S+@\S+\.\S+$/.test(email)) { showError('email', 'Formato de email inválido.'); isValid = false; }

            if (!telefoneInput || telefone === '') {
                showError('telefone', 'Telefone é obrigatório.'); isValid = false;
            } else if (telefone.length < 10 || telefone.length > 11) {
                showError('telefone', 'Telefone inválido. Use DDD + número (10 ou 11 dígitos).'); isValid = false;
            }

            if (dataNascimento === '') { showError('dataNascimento', 'Data de nascimento é obrigatória.'); isValid = false; }
            else {
                const hoje = new Date();
                const dataNascObj = new Date(dataNascimento + "T00:00:00");
                hoje.setHours(0, 0, 0, 0);
                if (dataNascObj >= hoje) {
                    showError('dataNascimento', 'Data de nascimento não pode ser hoje ou no futuro.'); isValid = false;
                }
            }

            if (cpf === '') { showError('cpf', 'CPF é obrigatório.'); isValid = false; }
            else if (cpf.length !== 11) { showError('cpf', 'CPF deve conter 11 dígitos.'); isValid = false; }

            if (senha.length < 6) { showError('senha', 'A senha deve ter pelo menos 6 caracteres.'); isValid = false; }
            if (confirmarSenha === '') { showError('confirmarSenha', 'Por favor, confirme sua senha.'); isValid = false; }
            else if (senha !== confirmarSenha) {
                showError('confirmarSenha', 'As senhas não coincidem.');
                showError('senha', ''); // Pode limpar ou adicionar um indicador no campo senha também
                isValid = false;
            }
            // --- Fim Validações ---

            if (isValid) {
                const payload = {
                    dataRegistroPessoa: {
                        nome: nome,
                        cpf: cpf,
                        email: email,
                        telefone: telefone,
                        login: loginValue,
                        senha: senha
                        // Adicione dataNascimento aqui SE o seu backend o espera.
                        // O JSON de exemplo que você forneceu não o tinha.
                        // dataNascimento: dataNascimento,
                    }
                };
                // Se o seu backend espera 'dataNascimento' dentro de 'dataRegistroPessoa':
                if(dataNascimento) { // Garante que a data não está vazia antes de adicionar
                    payload.dataRegistroPessoa.dataNascimento = dataNascimento;
                }


                console.log("Enviando para o backend:", JSON.stringify(payload));

                try {
                    const response = await fetch('http://localhost:8080/publico/register', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(payload)
                    });

                    if (response.ok) {
                        alert('Cadastro realizado com sucesso! Você será redirecionado para a página de login.');
                        window.location.href = '../login/login.html';
                    } else {
                        let errorData = { message: `Erro no cadastro (Status: ${response.status} - ${response.statusText})` };
                        try {
                            const backendError = await response.json();
                            if (backendError && backendError.message) {
                                errorData.message = backendError.message;
                            } else if (typeof backendError === 'string') {
                                errorData.message = backendError;
                            }
                            // Tratar erros de validação por campo se o backend os enviar:
                            // if (backendError.errors && Array.isArray(backendError.errors)) {
                            //     let detailedErrors = backendError.errors.map(e => `${e.field}: ${e.defaultMessage}`).join('\n');
                            //     errorData.message += `\nDetalhes:\n${detailedErrors}`;
                            // }
                        } catch (e) {
                            console.warn("Resposta de erro do backend não é JSON válido ou está vazia.", e);
                        }
                        showError('confirmarSenha', errorData.message); // Exibe erro geral próximo ao botão
                        console.error('Erro no cadastro:', errorData.message);
                    }
                } catch (error) {
                    console.error('Falha na requisição de cadastro (erro de rede/conexão):', error);
                    showError('confirmarSenha', 'Não foi possível conectar ao servidor. Verifique sua conexão e tente novamente.');
                }
            }
        }); // Fim do addEventListener 'submit'

        // Listeners para limpar erros ao digitar - MOVEMOS PARA FORA DO SUBMIT, MAS DENTRO DE IF(CADASTROFORM)
        const inputs = cadastroForm.querySelectorAll('.form-group input');
        inputs.forEach(input => {
            if (input) { // Garante que o input existe
                input.addEventListener('input', () => {
                    clearError(input.id);
                    // Lógica para limpar erro de confirmação de senha
                    if (input.id === 'senha' || input.id === 'confirmarSenha') {
                        if (senhaInput && confirmarSenhaInput && senhaInput.value === confirmarSenhaInput.value) {
                            clearError('senha');
                            clearError('confirmarSenha');
                        }
                    }
                });
            }
        });

    } // Fim do if (cadastroForm)
}); // Fim do DOMContentLoaded