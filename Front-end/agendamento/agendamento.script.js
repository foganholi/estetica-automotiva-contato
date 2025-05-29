document.addEventListener('DOMContentLoaded', function() {
    const agendamentoForm = document.getElementById('agendamentoForm');
    
    // Inputs
    const nomeCompletoInput = document.getElementById('nomeCompleto');
    const telefoneInput = document.getElementById('telefone');
    const emailInput = document.getElementById('email');
    const veiculoInput = document.getElementById('veiculo');
    const servicosDesejadosInput = document.getElementById('servicosDesejados');
    const dataPreferidaInput = document.getElementById('dataPreferida');
    const horarioPreferidoInput = document.getElementById('horarioPreferido');
    // const observacoesInput = document.getElementById('observacoes'); // Não necessita validação JS obrigatória no exemplo

    const successMessageDiv = document.getElementById('successMessage');

    // Funções de erro (similares às outras páginas)
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
        if (inputElement) inputElement.style.borderColor = '#ced4da'; // Cor da borda padrão
    }

    function showSuccessMessage(message) {
        if (successMessageDiv) {
            successMessageDiv.textContent = message;
            successMessageDiv.style.display = 'block';
            // Rola para a mensagem de sucesso
            successMessageDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
            setTimeout(() => {
                successMessageDiv.style.display = 'none';
            }, 7000); // Mensagem some após 7 segundos
        } else {
            alert(message); // Fallback
        }
    }
    
    // Máscara simples para telefone (XX) XXXXX-XXXX
    if (telefoneInput) {
        telefoneInput.addEventListener('input', function (e) {
            let value = e.target.value.replace(/\D/g, '');
            const maxLength = 11; // Para (XX) XXXXX-XXXX
            value = value.substring(0, maxLength);
            let maskedValue = '';
            if (value.length > 0) {
                maskedValue = '(' + value.substring(0,2);
            }
            if (value.length > 2) {
                maskedValue += ') ' + value.substring(2,7);
            }
            if (value.length > 7) {
                maskedValue += '-' + value.substring(7,11);
            }
            e.target.value = maskedValue;
        });
    }


    // Definir data mínima para o campo de data preferida (não pode ser no passado)
    if (dataPreferidaInput) {
        const hoje = new Date();
        const dia = ("0" + hoje.getDate()).slice(-2);
        const mes = ("0" + (hoje.getMonth() + 1)).slice(-2);
        const ano = hoje.getFullYear();
        dataPreferidaInput.min = `${ano}-${mes}-${dia}`;
    }


    if (agendamentoForm) {
        agendamentoForm.addEventListener('submit', function(event) {
            event.preventDefault();
            let isValid = true;

            // Limpar erros anteriores
            const fieldsToValidate = ['nomeCompleto', 'telefone', 'email', 'veiculo', 'servicosDesejados', 'dataPreferida', 'horarioPreferido'];
            fieldsToValidate.forEach(id => clearError(id));
            if(successMessageDiv) successMessageDiv.style.display = 'none';


            // Validações
            if (nomeCompletoInput.value.trim() === '') {
                showError('nomeCompleto', 'Nome completo é obrigatório.');
                isValid = false;
            }
            if (telefoneInput.value.replace(/\D/g, '').length < 10) { // Verifica se tem pelo menos 10 dígitos
                showError('telefone', 'Telefone inválido. Inclua o DDD.');
                isValid = false;
            }
            if (emailInput.value.trim() === '') {
                showError('email', 'Email é obrigatório.');
                isValid = false;
            } else if (!/\S+@\S+\.\S+/.test(emailInput.value.trim())) {
                showError('email', 'Formato de email inválido.');
                isValid = false;
            }
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
                hoje.setHours(0,0,0,0); // Zera a hora para comparar apenas a data
                const dataSelecionada = new Date(dataPreferidaInput.value + "T00:00:00"); // Adiciona T00:00:00 para evitar problemas de fuso
                if (dataSelecionada < hoje) {
                    showError('dataPreferida', 'A data não pode ser no passado.');
                    isValid = false;
                }
            }
            if (horarioPreferidoInput.value === '') {
                showError('horarioPreferido', 'Horário preferido é obrigatório.');
                isValid = false;
            }
            // Validação de horário comercial (exemplo)
            const [hora, minuto] = horarioPreferidoInput.value.split(':').map(Number);
            if (hora < 8 || hora > 18 || (hora === 18 && minuto > 0)) {
                 showError('horarioPreferido', 'Horário deve ser entre 08:00 e 18:00.');
                 isValid = false;
            }


            if (isValid) {
                // Simulação de envio para o backend
                console.log('Solicitação de agendamento válida e pronta para enviar:');
                const formData = {
                    nome: nomeCompletoInput.value.trim(),
                    telefone: telefoneInput.value,
                    email: emailInput.value.trim(),
                    veiculo: veiculoInput.value.trim(),
                    servico: servicosDesejadosInput.value,
                    data: dataPreferidaInput.value,
                    hora: horarioPreferidoInput.value,
                    observacoes: document.getElementById('observacoes').value.trim()
                };
                console.log(formData);
                
                showSuccessMessage('Sua solicitação de agendamento foi enviada com sucesso! Entraremos em contato em breve para confirmar.');
                agendamentoForm.reset(); // Limpa o formulário
                
                // Em um cenário real, aqui você faria uma chamada fetch() para sua API backend
                // Ex: fetch('/api/agendamentos', { method: 'POST', body: JSON.stringify(formData), headers: {'Content-Type': 'application/json'} })
                //    .then(response => response.json())
                //    .then(data => { showSuccessMessage('Agendamento enviado!'); agendamentoForm.reset(); })
                //    .catch(error => { showError('geral', 'Erro ao enviar agendamento. Tente novamente.'); });
            } else {
                // Rola para a primeira mensagem de erro para o usuário ver
                const firstError = document.querySelector('.error-message[style*="display: block"]');
                if (firstError) {
                    firstError.parentElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });
        
        // Limpar erros ao digitar
        const allInputsAndSelects = agendamentoForm.querySelectorAll('input, select, textarea');
        allInputsAndSelects.forEach(input => {
            input.addEventListener('input', () => clearError(input.id));
            if (input.type === 'date' || input.type === 'time' || input.tagName === 'SELECT') {
                 input.addEventListener('change', () => clearError(input.id)); // Para date, time e select, 'change' é mais apropriado
            }
        });
    }
    
    // Atualizar o ano no rodapé
    const currentYearSpan = document.getElementById('currentYear');
    if (currentYearSpan) {
        currentYearSpan.textContent = new Date().getFullYear();
    }
});