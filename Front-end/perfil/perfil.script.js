document.addEventListener('DOMContentLoaded', function() {
    const editProfileForm = document.getElementById('editProfileForm');
    
    // Seletores dos campos do formulário
    const nomeCompletoInput = document.getElementById('nomeCompleto');
    const emailInput = document.getElementById('email');
    const dataNascimentoInput = document.getElementById('dataNascimento');
    const cpfInput = document.getElementById('cpf');
    const senhaAtualInput = document.getElementById('senhaAtual');
    const novaSenhaInput = document.getElementById('novaSenha');
    const confirmarNovaSenhaInput = document.getElementById('confirmarNovaSenha');

    // Simulação: Carregar dados do usuário (substitua pela sua lógica de API real)
    function carregarDadosDoUsuario() {
        // Dados mockados
        const dadosUsuarioMock = {
            nomeCompleto: "Usuário Exemplo Silva",
            email: "usuario@exemplo.com",
            dataNascimento: "1990-05-15", // Formato YYYY-MM-DD
            cpf: "123.456.789-00"
        };

        if (nomeCompletoInput) nomeCompletoInput.value = dadosUsuarioMock.nomeCompleto;
        if (emailInput) emailInput.value = dadosUsuarioMock.email;
        if (dataNascimentoInput) dataNascimentoInput.value = dadosUsuarioMock.dataNascimento;
        if (cpfInput) cpfInput.value = dadosUsuarioMock.cpf;
        // Campos de senha ficam vazios por padrão
    }

    // Chamar a função para carregar os dados mockados
    carregarDadosDoUsuario();

    // Funções showError e clearError (similares às da página de cadastro)
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
    
    function showSuccessMessage(message) {
        // Adicione um <div id="successMessage" class="success-message"></div> no seu HTML,
        // por exemplo, antes do botão de submit.
        const successDiv = document.getElementById('successMessage');
        if (!successDiv) { // Cria dinamicamente se não existir (melhor ter no HTML)
            const newSuccessDiv = document.createElement('div');
            newSuccessDiv.id = 'successMessage';
            newSuccessDiv.className = 'success-message';
            editProfileForm.insertBefore(newSuccessDiv, editProfileForm.querySelector('.btn-submit-profile'));
        }
        const finalSuccessDiv = document.getElementById('successMessage');
        finalSuccessDiv.textContent = message;
        finalSuccessDiv.style.display = 'block';
        setTimeout(() => {
            finalSuccessDiv.style.display = 'none';
        }, 5000); // Mensagem desaparece após 5 segundos
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

    // Validação e Submissão do Formulário
    if (editProfileForm) {
        editProfileForm.addEventListener('submit', function(event) {
            event.preventDefault();
            let isValid = true;

            // Limpar erros anteriores
            clearError('nomeCompleto');
            clearError('email');
            clearError('dataNascimento');
            clearError('senhaAtual');
            clearError('novaSenha');
            clearError('confirmarNovaSenha');

            // Validação do Nome
            if (nomeCompletoInput.value.trim() === '') {
                showError('nomeCompleto', 'Nome completo é obrigatório.');
                isValid = false;
            }

            // Validação do Email (formato básico)
            if (emailInput.value.trim() === '') {
                showError('email', 'Email é obrigatório.');
                isValid = false;
            } else if (!/\S+@\S+\.\S+/.test(emailInput.value.trim())) {
                showError('email', 'Formato de email inválido.');
                isValid = false;
            }
            
            // Validação da Data de Nascimento (simples)
            if (dataNascimentoInput.value === '') {
                showError('dataNascimento', 'Data de nascimento é obrigatória.');
                isValid = false;
            } else {
                const hoje = new Date();
                const dataNascObj = new Date(dataNascimentoInput.value + "T00:00:00");
                hoje.setHours(0, 0, 0, 0);
                if (dataNascObj >= hoje) {
                    showError('dataNascimento', 'Data de nascimento inválida.');
                    isValid = false;
                }
            }

            // Validação de mudança de senha
            const senhaAtual = senhaAtualInput.value;
            const novaSenha = novaSenhaInput.value;
            const confirmarNovaSenha = confirmarNovaSenhaInput.value;

            if (novaSenha !== '' || confirmarNovaSenha !== '' || senhaAtual !== '') {
                // Se qualquer campo de senha for preenchido, a senha atual é obrigatória
                if (senhaAtual === '' && (novaSenha !== '' || confirmarNovaSenha !== '')) {
                    showError('senhaAtual', 'Senha atual é obrigatória para alterar a senha.');
                    isValid = false;
                }
                if (novaSenha.length > 0 && novaSenha.length < 6) {
                    showError('novaSenha', 'Nova senha deve ter pelo menos 6 caracteres.');
                    isValid = false;
                }
                if (novaSenha !== confirmarNovaSenha) {
                    showError('confirmarNovaSenha', 'As novas senhas não coincidem.');
                    showError('novaSenha', 'As novas senhas não coincidem.');
                    isValid = false;
                }
                if (novaSenha !== '' && senhaAtual === '') { // Se nova senha preenchida, atual é obrigatória
                     showError('senhaAtual', 'Senha atual é obrigatória para definir uma nova.');
                     isValid = false;
                }
            }


            if (isValid) {
                // Simulação de envio para o backend
                console.log('Dados do formulário válidos e prontos para enviar:');
                const formData = {
                    nomeCompleto: nomeCompletoInput.value.trim(),
                    email: emailInput.value.trim(),
                    dataNascimento: dataNascimentoInput.value,
                    // CPF não é enviado pois é readonly, mas poderia ser se fosse editável
                    // Para mudança de senha, envie os campos relevantes:
                    senhaAtual: senhaAtual !== '' ? senhaAtual : undefined,
                    novaSenha: novaSenha !== '' ? novaSenha : undefined
                };
                console.log(formData);
                
                // Substitua o alert por uma chamada real à sua API backend
                // Ex: fetch('/api/user/profile', { method: 'PUT', body: JSON.stringify(formData), headers: {'Content-Type': 'application/json'} })
                //    .then(response => response.json())
                //    .then(data => console.log(data));
                
                showSuccessMessage('Perfil atualizado com sucesso! (Simulação)');
                // Opcional: limpar campos de senha após o sucesso
                if (senhaAtualInput) senhaAtualInput.value = '';
                if (novaSenhaInput) novaSenhaInput.value = '';
                if (confirmarNovaSenhaInput) confirmarNovaSenhaInput.value = '';

            }
        });
        
        // Limpar erros ao digitar
        const allInputs = editProfileForm.querySelectorAll('input');
        allInputs.forEach(input => {
            input.addEventListener('input', () => {
                clearError(input.id);
                // Lógica específica para limpar erros de confirmação de senha
                if ((input.id === 'novaSenha' || input.id === 'confirmarNovaSenha') && novaSenhaInput && confirmarNovaSenhaInput) {
                    if (novaSenhaInput.value === confirmarNovaSenhaInput.value) {
                        clearError('novaSenha');
                        clearError('confirmarNovaSenha');
                    }
                }
            });
        });
    }
});