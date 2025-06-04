// Em: agendamento/agendamento.script.js
document.addEventListener('DOMContentLoaded', function() {
    // ... (seu código existente: seletores de formulário, inputs, userInfoForScheduling, etc.) ...
    const dataPreferidaInput = document.getElementById('dataPreferida');
    const horarioPreferidoSelect = document.getElementById('horarioPreferido'); // O select para os horários
    const successMessageDiv = document.getElementById('successMessage'); // Para mensagens de sucesso
    const generalErrorDiv = document.getElementById('generalError'); // Para erros gerais
    const agendamentoForm = document.getElementById('agendamentoForm'); // Formulário de agendamento

    // ... (funções showError, clearError, showSuccessMessage como definidas anteriormente) ...
    function showError(fieldId, message, isGeneral = false) {
        if (isGeneral) {
            if (generalErrorDiv) {
                generalErrorDiv.textContent = message;
                generalErrorDiv.style.display = 'block';
            } else { alert(message); }
            return;
        }
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) { errorElement.textContent = message; errorElement.style.display = 'block'; }
        if (inputElement) { inputElement.style.borderColor = 'var(--cor-erro)'; inputElement.classList.add('input-error'); }
    }

    function clearError(fieldId, isGeneral = false) {
        if (isGeneral) {
            if (generalErrorDiv) { generalErrorDiv.textContent = ''; generalErrorDiv.style.display = 'none'; }
            return;
        }
        const errorElement = document.getElementById(fieldId + 'Error');
        const inputElement = document.getElementById(fieldId);
        if (errorElement) { errorElement.textContent = ''; errorElement.style.display = 'none'; }
        if (inputElement) { inputElement.style.borderColor = '#ced4da'; inputElement.classList.remove('input-error'); }
    }

    function showSuccessMessage(message) {
        if (successMessageDiv) {
            successMessageDiv.textContent = message;
            successMessageDiv.style.display = 'block';
            successMessageDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
            setTimeout(() => { successMessageDiv.style.display = 'none'; }, 7000);
        } else { alert(message); }
    }


    /**
     * Função para buscar horários disponíveis da API e popular o select.
     * @param {string} dataSelecionada - A data no formato YYYY-MM-DD.
     */
    async function carregarHorariosDisponiveis(dataSelecionada) {
        if (!dataSelecionada) {
            horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Selecione uma data primeiro...</option>';
            return;
        }

        // Limpa opções anteriores e mostra mensagem de carregamento
        horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Carregando horários...</option>';
        
        // Monta a URL para o endpoint.
        // Se o seu backend espera a data como parâmetro de query, adicione-a.
        // Exemplo: `http://localhost:8080/agenda/horarioDisponivel?data=${dataSelecionada}`
        // Se não precisar de parâmetro de data (buscar todos os horários disponíveis em geral), use apenas a URL base.
        const url = `http://localhost:8080/agenda/horarioDisponivel?date=${dataSelecionada}`; 
        // const url = 'http://localhost:8080/agenda/horarioDisponivel'; // Use esta se não houver parâmetro de data

        console.log(`Buscando horários em: ${url}`);

        try {
            const response = await fetch(url); // Faz a requisição GET

            if (!response.ok) {
                // Se a resposta não for OK (ex: erro 404, 500), lança um erro
                throw new Error(`Erro ao buscar horários: ${response.status} - ${response.statusText}`);
            }

            const horarios = await response.json(); // Converte a resposta para JSON
            // O backend deve retornar um array. Ex: ["08:00", "08:30", "09:00"]
            // Ou um array de objetos: [{ "hora": "08:00", "status": "Disponivel" }, ...]

            horarioPreferidoSelect.innerHTML = ''; // Limpa a mensagem "Carregando..."

            if (horarios && horarios.length > 0) {
                // Adiciona uma opção padrão "Selecione um horário"
                const optionDefault = new Option('Selecione um horário...', '');
                optionDefault.disabled = true;
                optionDefault.selected = true;
                horarioPreferidoSelect.add(optionDefault);

                horarios.forEach(horario => {
                    let valorHorario;
                    let textoHorario;

                    // Adapte esta parte conforme a estrutura exata do JSON que seu backend retorna
                    if (typeof horario === 'string') { // Se for um array de strings ["09:00", "10:00"]
                        valorHorario = horario;
                        textoHorario = horario;
                    } else if (typeof horario === 'object' && horario.hora && horario.status === 'Disponivel') { // Se for um array de objetos ex: {hora: "09:00", status: "Disponivel"}
                        valorHorario = horario.hora;
                        textoHorario = horario.hora; // Você pode formatar este texto se quiser
                    } else {
                        console.warn("Formato de horário não esperado:", horario);
                        return; // Pula este item se o formato não for o esperado
                    }
                    
                    // Adiciona apenas horários dentro do seu range de atendimento (08:00 - 17:30)
                    const [h, m] = valorHorario.split(':').map(Number);
                    if (h >= 8 && (h < 18 || (h === 17 && m <= 30))) {
                        const option = new Option(textoHorario, valorHorario);
                        horarioPreferidoSelect.add(option);
                    }
                });
                
                // Se, após filtrar, só sobrou a opção "Selecione...", significa que não há horários válidos.
                if (horarioPreferidoSelect.options.length <= 1) {
                    horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Nenhum horário disponível para esta data.</option>';
                }

            } else {
                // Se a API retornar um array vazio
                horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Nenhum horário disponível para esta data.</option>';
            }
        } catch (error) {
            console.error('Falha ao carregar horários disponíveis:', error);
            horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Erro ao carregar horários.</option>';
            // Opcional: mostrar um erro mais visível para o usuário
            showError('horarioPreferido', 'Não foi possível carregar os horários. Tente selecionar a data novamente.');
        }
    }

    // Adiciona o listener para o campo de data
    if (dataPreferidaInput) {
        // Define a data mínima como hoje
        const hoje = new Date();
        const dia = String(hoje.getDate()).padStart(2, '0');
        const mes = String(hoje.getMonth() + 1).padStart(2, '0'); // Meses são 0-indexados
        const ano = hoje.getFullYear();
        dataPreferidaInput.min = `${ano}-${mes}-${dia}`;

        // Quando a data mudar, busca os horários
        dataPreferidaInput.addEventListener('change', function() {
            clearError('horarioPreferido'); // Limpa erros anteriores do campo de horário
            const dataSelecionada = this.value;
            if (dataSelecionada) {
                carregarHorariosDisponiveis(dataSelecionada);
            } else {
                horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Selecione uma data primeiro...</option>';
            }
        });
    }
    
    // ... (Restante do seu código em agendamento.script.js, como a submissão do formulário de agendamento,
    // tratamento de dados do usuário logado, atualização do ano no rodapé, etc.) ...
    
    // Exemplo de como a submissão do formulário de agendamento ficaria (simplificado):
    if (agendamentoForm) {
        agendamentoForm.addEventListener('submit', async function(event) {
            event.preventDefault();
            // ... (sua lógica de validação e coleta de dados do formulário) ...
            let isValid = true; // Sua lógica de validação aqui
            // Validação do horário selecionado
             if (horarioPreferidoSelect.value === '') {
                showError('horarioPreferido', 'Por favor, selecione um horário.');
                isValid = false;
            }

            if(isValid) {
                const formData = {
                    // ... (todos os dados do formulário, incluindo dataPreferidaInput.value e horarioPreferidoSelect.value) ...
                    veiculo: document.getElementById('veiculo').value.trim(),
                    servicoId: document.getElementById('servicosDesejados').value,
                    dataPreferida: dataPreferidaInput.value,
                    horarioPreferido: horarioPreferidoSelect.value,
                    observacoes: document.getElementById('observacoes').value.trim(),
                    userEmail: localStorage.getItem('userEmailMarquesAutoDetail'),
                    userName: localStorage.getItem('userNameMarquesAutoDetail')
                };

                console.log("Dados do agendamento para enviar ao backend:", formData);
                // Aqui você faria o POST para o seu endpoint de *salvar* o agendamento
                // Ex: POST http://localhost:8080/agenda/salvar (ou similar)
                // ... fetch para salvar agendamento ...
                 try {
                    const response = await fetch('http://localhost:8080/agenda/salvar', { // <<< AJUSTE ESTE ENDPOINT
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(formData)
                    });

                    if (response.ok) {
                        showSuccessMessage('Agendamento solicitado com sucesso! Verifique o status no seu perfil.');
                        // ... (lógica para adicionar ao localStorage para simulação no perfil, como antes) ...
                        const agendamentosMockados = JSON.parse(localStorage.getItem('mockUserAgendamentos')) || [];
                        const servicosSelect = document.getElementById('servicosDesejados');
                        const novoAgendamento = {
                            id: 'AG' + Date.now(),
                            servicoNome: servicosSelect.options[servicosSelect.selectedIndex].text,
                            data: formData.dataPreferida,
                            horario: formData.horarioPreferido,
                            veiculo: formData.veiculo,
                            status: 'Pendente'
                        };
                        agendamentosMockados.push(novoAgendamento);
                        localStorage.setItem('mockUserAgendamentos', JSON.stringify(agendamentosMockados));
                        
                        agendamentoForm.reset();
                        horarioPreferidoSelect.innerHTML = '<option value="" disabled selected>Selecione uma data primeiro...</option>';
                         const hoje = new Date();
                         dataPreferidaInput.min = `${hoje.getFullYear()}-${String(hoje.getMonth() + 1).padStart(2, '0')}-${String(hoje.getDate()).padStart(2, '0')}`;


                    } else {
                        let errorData = { message: response.statusText };
                        try { errorData = await response.json(); } catch (e) {}
                        showError('horarioPreferido', `Erro ao agendar: ${errorData.message || response.statusText}`);
                    }
                } catch (error) {
                     showError('horarioPreferido', 'Falha ao conectar com o servidor para agendar.');
                }
            }
        });
    }
    
     // Atualizar ano no rodapé
    const currentYearSpan = document.getElementById('currentYear');
    if (currentYearSpan) {
        currentYearSpan.textContent = new Date().getFullYear();
    }

});