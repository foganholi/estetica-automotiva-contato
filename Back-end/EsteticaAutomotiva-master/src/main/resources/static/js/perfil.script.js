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

    const successMessageDiv = document.getElementById('successMessage'); // Para sucesso geral do formulário
    const successMessagePasswordDiv = document.getElementById('successMessagePassword'); // Para sucesso da senha

    // Simulação: Carregar dados do usuário (do localStorage ou mock)
    function carregarDadosDoUsuario() {
        // Prioriza dados do localStorage se existirem (simulando usuário logado)
        const storedNome = localStorage.getItem('userNameMarquesAutoDetail');
        const storedEmail = localStorage.getItem('userEmailMarquesAutoDetail');
        // Para dataNascimento e CPF, vamos continuar usando mock se não vierem do backend/localStorage
        // Aqui você integraria uma chamada real à API para buscar os dados completos do perfil

        const dadosUsuarioMock = {
            nomeCompleto: storedNome || "Usuário Exemplo Silva",
            email: storedEmail || "usuario@exemplo.com",
            dataNascimento: "1990-05-15", // Manter mock ou buscar da API
            cpf: "123.456.789-00"       // Manter mock ou buscar da API
        };

        if (nomeCompletoInput) nomeCompletoInput.value = dadosUsuarioMock.nomeCompleto;
        if (emailInput) emailInput.value = dadosUsuarioMock.email;
        if (dataNascimentoInput) dataNascimentoInput.value = dadosUsuarioMock.dataNascimento;
        if (cpfInput) cpfInput.value = dadosUsuarioMock.cpf;
        // Campos de senha ficam vazios por padrão
    }

    carregarDadosDoUsuario(); // Chamar a função para carregar os dados

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
        if (inputElement) inputElement.style.borderColor = '#ccc'; // Cor padrão da borda
    }
    
    function showSuccess(message, type = 'general') {
        let targetDiv;
        if (type === 'password' && successMessagePasswordDiv) {
            targetDiv = successMessagePasswordDiv;
        } else if (successMessageDiv) {
            targetDiv = successMessageDiv;
        }

        if (targetDiv) {
            targetDiv.textContent = message;
            targetDiv.style.display = 'block';
            // Rolar para a mensagem de sucesso se ela estiver fora da vista
            targetDiv.scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'nearest' });
            setTimeout(() => {
                targetDiv.style.display = 'none';
            }, 5000); // Mensagem desaparece após 5 segundos
        } else {
            // Fallback se o div específico não existir (embora o HTML deva tê-los)
            alert(message);
        }
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
            let isProfileValid = true;
            let isPasswordChangeValid = true;
            let passwordChanged = false;

            // Limpar erros anteriores
            clearError('nomeCompleto');
            clearError('email');
            clearError('dataNascimento');
            clearError('senhaAtual');
            clearError('novaSenha');
            clearError('confirmarNovaSenha');
            if(successMessageDiv) successMessageDiv.style.display = 'none';
            if(successMessagePasswordDiv) successMessagePasswordDiv.style.display = 'none';


            // Validação do Nome
            if (nomeCompletoInput.value.trim() === '') {
                showError('nomeCompleto', 'Nome completo é obrigatório.');
                isProfileValid = false;
            }

            // Validação do Email (formato básico)
            // Nota: A alteração de email geralmente requer confirmação e não é feita diretamente assim.
            // A mensagem no HTML "Para alterar seu email, entre em contato com o suporte." é uma boa prática.
            if (emailInput.value.trim() === '') {
                showError('email', 'Email é obrigatório.');
                isProfileValid = false;
            } else if (!/^\S+@\S+\.\S+$/.test(emailInput.value.trim())) {
                showError('email', 'Formato de email inválido.');
                isProfileValid = false;
            }
            
            // Validação da Data de Nascimento
            if (dataNascimentoInput.value === '') {
                showError('dataNascimento', 'Data de nascimento é obrigatória.');
                isProfileValid = false;
            } else {
                const hoje = new Date();
                const dataNascObj = new Date(dataNascimentoInput.value + "T00:00:00"); // Adiciona T00:00:00 para evitar problemas de fuso
                hoje.setHours(0, 0, 0, 0); // Zera a hora para comparar apenas a data
                const idadeMinima = 10; // Exemplo: idade mínima de 10 anos
                const dataMinima = new Date(hoje.getFullYear() - idadeMinima, hoje.getMonth(), hoje.getDate());

                if (dataNascObj >= hoje) {
                    showError('dataNascimento', 'Data de nascimento não pode ser hoje ou no futuro.');
                    isProfileValid = false;
                } else if (dataNascObj > dataMinima) {
                    showError('dataNascimento', `Você deve ter pelo menos ${idadeMinima} anos.`);
                    isProfileValid = false;
                }
            }

            // Validação de mudança de senha
            const senhaAtual = senhaAtualInput.value;
            const novaSenha = novaSenhaInput.value;
            const confirmarNovaSenha = confirmarNovaSenhaInput.value;

            if (novaSenha !== '' || confirmarNovaSenha !== '' || (senhaAtual !== '' && (novaSenha !== '' || confirmarNovaSenha !== ''))) {
                passwordChanged = true; // Intenção de mudar a senha
                if (senhaAtual === '') {
                    showError('senhaAtual', 'Senha atual é obrigatória para alterar a senha.');
                    isPasswordChangeValid = false;
                }
                if (novaSenha === '') {
                    showError('novaSenha', 'Nova senha é obrigatória.');
                    isPasswordChangeValid = false;
                } else if (novaSenha.length < 6) {
                    showError('novaSenha', 'Nova senha deve ter pelo menos 6 caracteres.');
                    isPasswordChangeValid = false;
                }
                if (confirmarNovaSenha === '') {
                    showError('confirmarNovaSenha', 'Confirmação da nova senha é obrigatória.');
                    isPasswordChangeValid = false;
                } else if (novaSenha !== confirmarNovaSenha) {
                    showError('confirmarNovaSenha', 'As novas senhas não coincidem.');
                    showError('novaSenha', ''); // Limpa possível erro anterior de "obrigatório" se este for o problema principal
                    isPasswordChangeValid = false;
                }
                // SIMULAÇÃO: Validar senhaAtual com o backend aqui
                // if (isPasswordChangeValid && senhaAtual !== "senhaAntigaCorretaMock") {
                //     showError('senhaAtual', 'Senha atual incorreta.');
                //     isPasswordChangeValid = false;
                // }
            }


            if (isProfileValid) {
                // Simulação de envio dos dados do perfil para o backend
                console.log('Dados do perfil para enviar (sem senha):');
                const profileFormData = {
                    nomeCompleto: nomeCompletoInput.value.trim(),
                    email: emailInput.value.trim(), // Considerar implicações de segurança e confirmação para alteração de email
                    dataNascimento: dataNascimentoInput.value,
                    // CPF não é enviado pois é readonly
                };
                console.log(profileFormData);
                // Aqui ocorreria o fetch para atualizar o perfil
                // Ex: fetch('/api/user/profile', { method: 'PUT', body: JSON.stringify(profileFormData), ... })

                // Atualizar dados no localStorage se eles são usados em outros lugares (como na página de agendamento)
                localStorage.setItem('userNameMarquesAutoDetail', profileFormData.nomeCompleto);
                localStorage.setItem('userEmailMarquesAutoDetail', profileFormData.email);
                
                showSuccess('Dados do perfil atualizados com sucesso! (Simulação)');
            }

            if (passwordChanged && isPasswordChangeValid) {
                 // Simulação de envio da nova senha para o backend
                console.log('Dados de alteração de senha para enviar:');
                const passwordFormData = {
                    senhaAtual: senhaAtual, // Backend validaria essa senha
                    novaSenha: novaSenha
                };
                console.log(passwordFormData);
                // Aqui ocorreria o fetch para atualizar a senha
                // Ex: fetch('/api/user/change-password', { method: 'POST', body: JSON.stringify(passwordFormData), ... })

                showSuccess('Senha alterada com sucesso! (Simulação)', 'password');
                // Limpar campos de senha após o sucesso
                if (senhaAtualInput) senhaAtualInput.value = '';
                if (novaSenhaInput) novaSenhaInput.value = '';
                if (confirmarNovaSenhaInput) confirmarNovaSenhaInput.value = '';
            }
            
            if (!isProfileValid || (passwordChanged && !isPasswordChangeValid)) {
                 // Focar no primeiro erro para melhor UX
                const firstErrorInput = editProfileForm.querySelector('.form-group input.input-error, .form-group .error-message[style*="display: block"]');
                if (firstErrorInput) {
                    const fieldToFocus = firstErrorInput.tagName === 'INPUT' ? firstErrorInput : document.getElementById(firstErrorInput.id.replace('Error', ''));
                    if(fieldToFocus) fieldToFocus.focus();
                }
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
                        clearError('novaSenha'); // Limpa se o erro era de "não coincidem"
                        clearError('confirmarNovaSenha');
                    }
                }
            });
        });
    }

    // --- INÍCIO DA LÓGICA PARA "MEUS AGENDAMENTOS" ---
    const meusAgendamentosListDiv = document.getElementById('meusAgendamentosList');
    const agendamentosLoadingMsg = document.getElementById('agendamentosLoadingMsg');

    function carregarMeusAgendamentos() {
        if (!meusAgendamentosListDiv || !agendamentosLoadingMsg) {
            console.warn("Elementos para 'Meus Agendamentos' não encontrados.");
            return;
        }

        // SIMULAÇÃO: Pegar agendamentos do localStorage
        const agendamentosDoUsuario = JSON.parse(localStorage.getItem('mockUserAgendamentos')) || [];
        
        setTimeout(() => { // Simula delay de API
            if (agendamentosLoadingMsg) agendamentosLoadingMsg.style.display = 'none';
            meusAgendamentosListDiv.innerHTML = ''; // Limpa a mensagem de loading ou agendamentos anteriores

            if (agendamentosDoUsuario.length === 0) {
                const p = document.createElement('p');
                p.className = 'nenhum-agendamento';
                p.textContent = 'Você ainda não possui agendamentos.';
                meusAgendamentosListDiv.appendChild(p);
                return;
            }

            // Ordenar: mais recentes primeiro (pela data e hora do agendamento)
            agendamentosDoUsuario.sort((a, b) => {
                const dateA = new Date(`${a.data}T${a.horario}`);
                const dateB = new Date(`${b.data}T${b.horario}`);
                return dateB - dateA;
            });

            agendamentosDoUsuario.forEach(ag => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'agendamento-item';

                const titulo = document.createElement('h3');
                titulo.textContent = ag.servicoNome || "Serviço não especificado";
                
                const dataP = document.createElement('p');
                const dataObj = new Date(ag.data + "T00:00:00");
                const diaSemana = dataObj.toLocaleDateString('pt-BR', { weekday: 'long' });
                dataP.innerHTML = `<strong>Data:</strong> ${dataObj.toLocaleDateString('pt-BR', {day: '2-digit', month: '2-digit', year: 'numeric'})} (${diaSemana.split('-')[0]})`;

                const horarioP = document.createElement('p');
                horarioP.innerHTML = `<strong>Horário:</strong> ${ag.horario || "N/A"}`;

                const veiculoP = document.createElement('p');
                veiculoP.innerHTML = `<strong>Veículo:</strong> ${ag.veiculo || "N/A"}`;

                const statusP = document.createElement('p');
                const statusClass = ag.status ? ag.status.toLowerCase().replace(/\s+/g, '-') : 'desconhecido';
                statusP.innerHTML = `<strong>Status:</strong> <span class="agendamento-status status-${statusClass}">${ag.status || "Desconhecido"}</span>`;
                
                itemDiv.appendChild(titulo);
                itemDiv.appendChild(dataP);
                itemDiv.appendChild(horarioP);
                itemDiv.appendChild(veiculoP);
                itemDiv.appendChild(statusP);

                meusAgendamentosListDiv.appendChild(itemDiv);
            });

        }, 500);
    }

    carregarMeusAgendamentos();
    // --- FIM DA LÓGICA PARA "MEUS AGENDAMENTOS" ---

    // Atualizar ano no rodapé
    const currentYearSpan = document.getElementById('currentYearFooter'); // Usando o ID do HTML que forneci
    if (currentYearSpan) {
        currentYearSpan.textContent = new Date().getFullYear();
    }

});