document.addEventListener('DOMContentLoaded', function() {
    const agendamentoForm = document.getElementById('agendamentoForm');
    
    // Inputs focais para o usuário logado
    const veiculoInput = document.getElementById('veiculo');
    const servicosDesejadosInput = document.getElementById('servicosDesejados');
    const dataPreferidaInput = document.getElementById('dataPreferida');
    const horarioPreferidoInput = document.getElementById('horarioPreferido');
    const observacoesInput = document.getElementById('observacoes');

    const successMessageDiv = document.getElementById('successMessage');
    const generalErrorDiv = document.getElementById('generalError');

    // Elementos para exibir info do usuário
    const userNameDisplay = document.getElementById('userNameDisplay');
    const userEmailDisplay = document.getElementById('userEmailDisplay');

    // SIMULAÇÃO: Obter dados do usuário logado (Ex: do localStorage)
    // No seu backend, você teria o usuário da sessão.
    // Para a simulação, vamos usar dados mockados ou do localStorage como no script.js da index
    const userIsLoggedIn = localStorage.getItem('isUserLoggedInMarquesAutoDetail') === 'true';
    const userEmail = localStorage.getItem('userEmailMarquesAutoDetail'); // Supondo que você guarde o email
    // Para o nome, precisaríamos de um campo 'userNameMarquesAutoDetail' no localStorage ou mockar.
    // Vamos mockar o nome por agora se não tiver.
    const userName = localStorage.getItem('userNameMarquesAutoDetail') || (userEmail ? userEmail.split('@')[0] : "Cliente");

    if (userIsLoggedIn && userNameDisplay && userEmailDisplay) {
        userNameDisplay.textContent = userName;
        userEmailDisplay.textContent = userEmail || "Email não disponível";
    } else if (userNameDisplay) {
        // Se não estiver logado (teoricamente não deveria chegar aqui com a lógica da index)
        // Ou se os elementos não existirem
        const userInfoDiv = document.getElementById('userInfoForScheduling');
        if(userInfoDiv) userInfoDiv.innerHTML = "<p>Por favor, <a href='../login/login.html'>faça login</a> para agendar.</p>";
        if(agendamentoForm) agendamentoForm.style.display = 'none'; // Esconde o form se não logado
    }


    function showError(fieldId, message, isGeneral = false) {
        if (isGeneral) {
            if (generalErrorDiv) {
                generalErrorDiv.textContent = message;
                generalErrorDiv.style.display = 'block';
            } else {
                alert(message); // Fallback
            }
            return;
        }
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

    function clearError(fieldId, isGeneral = false) {
        if (isGeneral) {
            if (generalErrorDiv) {
                generalErrorDiv.textContent = '';
                generalErrorDiv.style.display = 'none';
            }
            return;
        }
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) {
            errorElement.textContent = '';
            errorElement.style.display = 'none';
        }
        if (inputElement) {
            inputElement.style.borderColor = '#ced4da';
            inputElement.classList.remove('input-error');
        }
    }

    function showSuccessMessage(message) {
        if (successMessageDiv) {
            successMessageDiv.textContent = message;
            successMessageDiv.style.display = 'block';
            successMessageDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
            setTimeout(() => {
                successMessageDiv.style.display = 'none';
            }, 7000);
        } else {
            alert(message);
        }
    }
    
    if (dataPreferidaInput) {
        const hoje = new Date();
        const dia = String(hoje.getDate()).padStart(2, '0');
        const mes = String(hoje.getMonth() + 1).padStart(2, '0');
        const ano = hoje.getFullYear();
        dataPreferidaInput.min = `${ano}-${mes}-${dia}`;
    }

    if (agendamentoForm) {
        agendamentoForm.addEventListener('submit', function(event) {
            event.preventDefault();
            if (!userIsLoggedIn) {
                showError('general', 'Você precisa estar logado para fazer um agendamento.', true);
                return;
            }
            let isValid = true;

            const fieldsToValidate = ['veiculo', 'servicosDesejados', 'dataPreferida', 'horarioPreferido'];
            fieldsToValidate.forEach(id => clearError(id));
            clearError('general', true); // Limpa erro geral
            if(successMessageDiv) successMessageDiv.style.display = 'none';

            if (veiculoInput.value.trim() === '') {
                showError('veiculo', 'Informação do veículo é obrigatória.');
                isValid = false;
            }
            if (servicosDesejadosInput.value === '') {
                showError('servicosDesejados', 'Selecione ao menos um serviço.');
                isValid = false;
            }
            if (dataPreferidaInput.value === '') {
                showError('dataPreferida', 'Data preferida é obrigatória.');
                isValid = false;
            } else {
                const hoje = new Date();
                hoje.setHours(0,0,0,0); 
                const dataSelecionada = new Date(dataPreferidaInput.value + "T00:00:00"); 
                if (dataSelecionada < hoje) {
                    showError('dataPreferida', 'A data não pode ser anterior ao dia de hoje.');
                    isValid = false;
                }
            }
            if (horarioPreferidoInput.value === '') {
                showError('horarioPreferido', 'Horário preferido é obrigatório.');
                isValid = false;
            } else {
                const [hora, minuto] = horarioPreferidoInput.value.split(':').map(Number);
                // Verifica se o horário está dentro do permitido (08:00 - 17:30)
                if (hora < 8 || hora > 17 || (hora === 17 && minuto > 30)) {
                     showError('horarioPreferido', 'Horário deve ser entre 08:00 e 17:30.');
                     isValid = false;
                }
            }

            if (isValid) {
                console.log('Solicitação de agendamento válida e pronta para enviar:');
                const formData = {
                    // userId: backend pegaria da sessão
                    userEmail: userEmail, // Enviando email para identificar o usuário
                    userName: userName,     // Enviando nome
                    veiculo: veiculoInput.value.trim(),
                    servico: servicosDesejadosInput.value,
                    dataPreferida: dataPreferidaInput.value,
                    horarioPreferido: horarioPreferidoInput.value,
                    observacoes: observacoesInput.value.trim()
                };
                console.log(formData);
                
                // SIMULAÇÃO DE SUBMISSÃO E RESPOSTA DO BACKEND
                // Substitua por sua lógica fetch real
                showSuccessMessage('Sua solicitação de agendamento foi enviada! Você pode verificar o status na sua página de perfil.');
                
                // Adicionar este agendamento mockado ao localStorage para exibir no perfil (APENAS PARA SIMULAÇÃO)
                const agendamentosMockados = JSON.parse(localStorage.getItem('mockUserAgendamentos')) || [];
                const novoAgendamento = {
                    id: 'AG' + Date.now(), // ID único simples
                    servicoNome: servicosDesejadosInput.options[servicosDesejadosInput.selectedIndex].text,
                    data: dataPreferidaInput.value,
                    horario: horarioPreferidoInput.value,
                    veiculo: veiculoInput.value.trim(),
                    status: 'Pendente' // Status inicial
                };
                agendamentosMockados.push(novoAgendamento);
                localStorage.setItem('mockUserAgendamentos', JSON.stringify(agendamentosMockados));

                agendamentoForm.reset(); 
                dataPreferidaInput.min = `${new Date().getFullYear()}-${String(new Date().getMonth() + 1).padStart(2, '0')}-${String(new Date().getDate()).padStart(2, '0')}`; // Reset min date
                fieldsToValidate.forEach(id => clearError(id));

            } else {
                const firstError = document.querySelector('.agendamento-form-wrapper .error-message[style*="display: block"]');
                if (firstError && firstError.id !== 'generalError') { // Não rolar para o erro geral no topo
                    firstError.parentElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
                } else if (generalErrorDiv.style.display === 'block') {
                     generalErrorDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });
        
        const allInputsAndSelects = agendamentoForm.querySelectorAll('input, select, textarea');
        allInputsAndSelects.forEach(input => {
            const eventType = (input.tagName === 'SELECT' || input.type === 'date' || input.type === 'time') ? 'change' : 'input';
            input.addEventListener(eventType, () => {
                clearError(input.id);
                clearError('general', true); // Limpa erro geral ao interagir com qualquer campo
            });
        });
    }
    
    const currentYearSpan = document.getElementById('currentYear');
    if (currentYearSpan) {
        currentYearSpan.textContent = new Date().getFullYear();
    }
});