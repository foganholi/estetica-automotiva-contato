// Em static/js/script.js
document.addEventListener('DOMContentLoaded', function() {
    // ... (Lógica do Menu Mobile existente) ...
    const menuToggle = document.querySelector('.menu-toggle');
    const navMenu = document.querySelector('.nav-menu');

    if (menuToggle && navMenu) {
        // ... (código do menu toggle) ...
    }
    
    // ... (Lógica para fechar o menu mobile ao clicar em um link de seção) ...
    const navLinks = document.querySelectorAll('.nav-menu a');
    navLinks.forEach(link => {
        // ... (código para fechar menu) ...
    });
    
    // ... (Destaque do link ativo no menu ao rolar a página) ...
    const sections = document.querySelectorAll('section[id]');
    // ... (código de onScroll) ...


    // --- LÓGICA PARA GERENCIAR VISIBILIDADE DOS LINKS DE LOGIN/PERFIL ---
    // Se você estiver usando sec:authorize do Thymeleaf para controlar a visibilidade,
    // esta parte do JS para mostrar/ocultar links de login/cadastro/perfil/sair
    // pode se tornar redundante ou precisar de ajuste.
    // O Thymeleaf já renderizará o HTML correto baseado no estado de autenticação.
    // A lógica abaixo baseada em localStorage pode ser removida se o Thymeleaf + Spring Security
    // estiverem tratando disso completamente.
    
    /*
    const loginListItem = document.querySelector('.nav-menu ul li a[href="login/login.html"]')?.parentElement; // Atualize o href para @{/login} se o JS ainda precisar disso
    const cadastroListItem = document.querySelector('.nav-menu ul li a[href="cadastro/cadastro.html"]')?.parentElement; // Atualize o href para @{/register}
    const perfilListItem = document.querySelector('.nav-item-perfil'); 
    // const sairListItem = document.querySelector('.nav-item-sair'); // Elemento removido do HTML
    // const logoutLink = document.getElementById('logoutLink'); // Elemento removido do HTML

    function atualizarVisibilidadeMenuComLocalStorage() { // Renomeado para clareza
        const isUserLoggedIn = localStorage.getItem('isUserLoggedInMarquesAutoDetail') === 'true';

        if (isUserLoggedIn) {
            if (loginListItem) loginListItem.style.display = 'none';
            if (cadastroListItem) cadastroListItem.style.display = 'none';
            if (perfilListItem) perfilListItem.style.display = 'list-item'; // Ou 'block' dependendo do seu CSS
            // if (sairListItem) sairListItem.style.display = 'list-item'; // Não mais necessário
        } else {
            if (loginListItem) loginListItem.style.display = 'list-item';
            if (cadastroListItem) cadastroListItem.style.display = 'list-item';
            if (perfilListItem) perfilListItem.style.display = 'none';
            // if (sairListItem) sairListItem.style.display = 'none'; // Não mais necessário
        }
    }

    // Se o Thymeleaf está controlando a visibilidade inicial, você pode não precisar chamar isso
    // ou pode chamá-lo se houver interações que alterem o localStorage e precisem de atualização de UI sem reload.
    // atualizarVisibilidadeMenuComLocalStorage(); 
    */

    // Funcionalidade de Logout foi removida do HTML e da configuração de segurança.
    // O código abaixo para logoutLink não será mais executado se o elemento não existir.
    /*
    if (logoutLink) {
        logoutLink.addEventListener('click', function(event) {
            event.preventDefault();
            // Limpar localStorage é uma boa prática mesmo que o logout seja tratado no backend
            localStorage.removeItem('isUserLoggedInMarquesAutoDetail');
            localStorage.removeItem('userEmailMarquesAutoDetail');
            localStorage.removeItem('userNameMarquesAutoDetail');
            localStorage.removeItem('mockUserAgendamentos');
            localStorage.removeItem('authToken'); // Se você armazena o token JWT
            localStorage.removeItem('userRoles');
            
            alert('Você foi desconectado.');
            // atualizarVisibilidadeMenuComLocalStorage(); // Atualiza o menu
            window.location.href = 'index.html'; // Redireciona para home ou login
        });
    }
    */

    // --- LÓGICA PARA O FORMULÁRIO DE CONTATO VIA WHATSAPP ---
    const contatoForm = document.getElementById('form-contato');
    if (contatoForm) {
        // ... (seu código de formulário de contato existente) ...
    }
    
    // Opcional: Ouvir por mudanças no localStorage de outras abas/janelas
    // window.addEventListener('storage', function(event) {
    //     if (event.key === 'isUserLoggedInMarquesAutoDetail') {
    //         // Chamar uma função para atualizar a UI se necessário
    //     }
    // });
});