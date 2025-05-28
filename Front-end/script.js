// Em script.js (para index.html)

document.addEventListener('DOMContentLoaded', function() {
    // Lógica do Menu Mobile (hamburguer) - MANTENHA SEU CÓDIGO EXISTENTE AQUI
    const menuToggle = document.querySelector('.menu-toggle');
    const navMenu = document.querySelector('.nav-menu');

    if (menuToggle && navMenu) {
        menuToggle.addEventListener('click', () => {
            navMenu.classList.toggle('active');
            const icon = menuToggle.querySelector('i');
            if (navMenu.classList.contains('active')) {
                icon.classList.remove('fa-bars');
                icon.classList.add('fa-times');
                menuToggle.setAttribute('aria-label', 'Fechar menu');
            } else {
                icon.classList.remove('fa-times');
                icon.classList.add('fa-bars');
                menuToggle.setAttribute('aria-label', 'Abrir menu');
            }
        });
    }

    // Lógica para fechar o menu mobile ao clicar em um link (MANTENHA SEU CÓDIGO EXISTENTE AQUI)
    const navLinks = document.querySelectorAll('.nav-menu a');
    navLinks.forEach(link => {
        // Não fechar o menu se for o link de logout, pois ele tem ação própria
        if (link.id !== 'logoutLink' && menuToggle && navMenu) { 
            link.addEventListener('click', () => {
                if (navMenu.classList.contains('active')) {
                    navMenu.classList.remove('active');
                    const icon = menuToggle.querySelector('i');
                    icon.classList.remove('fa-times');
                    icon.classList.add('fa-bars');
                    menuToggle.setAttribute('aria-label', 'Abrir menu');
                }
            });
        }
    });
    
    // Destaque do link ativo no menu ao rolar a página (MANTENHA SEU CÓDIGO EXISTENTE AQUI)
    const sections = document.querySelectorAll('section[id]');
    function onScroll() {
        let scrollY = window.pageYOffset;
        sections.forEach(current => {
            if (!current) return; // Proteção extra
            const sectionHeight = current.offsetHeight;
            const headerHeight = document.querySelector('.header')?.offsetHeight || 0;
            const sectionTop = current.offsetTop - (headerHeight + 50);
            let sectionId = current.getAttribute('id');

            let navLinkForSection = document.querySelector('.nav-menu a[href*=' + sectionId + ']');
            if(navLinkForSection){
                if (scrollY > sectionTop && scrollY <= sectionTop + sectionHeight) {
                    navLinkForSection.classList.add('active');
                } else {
                    navLinkForSection.classList.remove('active');
                }
            }
        });
    }
    window.addEventListener('scroll', onScroll);
    onScroll(); // Executa uma vez ao carregar


    // --- NOVA LÓGICA PARA GERENCIAR VISIBILIDADE DOS LINKS DE LOGIN/PERFIL/SAIR ---
    const loginListItem = document.querySelector('.nav-item-login');
    const cadastroListItem = document.querySelector('.nav-item-cadastro');
    const perfilListItem = document.querySelector('.nav-item-perfil');
    const sairListItem = document.querySelector('.nav-item-sair');
    const logoutLink = document.getElementById('logoutLink');

    function atualizarVisibilidadeMenu() {
        const isUserLoggedIn = localStorage.getItem('isUserLoggedInMarquesAutoDetail') === 'true';

        if (isUserLoggedIn) {
            if (loginListItem) loginListItem.style.display = 'none';
            if (cadastroListItem) cadastroListItem.style.display = 'none';
            if (perfilListItem) perfilListItem.style.display = 'list-item'; // Ou 'block'/'flex' dependendo do display original dos <li>
            if (sairListItem) sairListItem.style.display = 'list-item';
        } else {
            if (loginListItem) loginListItem.style.display = 'list-item';
            if (cadastroListItem) cadastroListItem.style.display = 'list-item';
            if (perfilListItem) perfilListItem.style.display = 'none';
            if (sairListItem) sairListItem.style.display = 'none';
        }
    }

    // Atualiza o menu quando a página carrega
    atualizarVisibilidadeMenu();

    // Funcionalidade de Logout
    if (logoutLink) {
        logoutLink.addEventListener('click', function(event) {
            event.preventDefault(); // Previne a navegação padrão do link '#'
            localStorage.removeItem('isUserLoggedInMarquesAutoDetail');
            localStorage.removeItem('userEmailMarquesAutoDetail'); // Limpa também o email se estiver guardando
            alert('Você foi desconectado.');
            atualizarVisibilidadeMenu(); // Atualiza o menu imediatamente
            window.location.href = 'login/login.html'; // Redireciona para a página de login
        });
    }

    // Opcional: Ouvir por mudanças no localStorage de outras abas/janelas (mais avançado)
    // window.addEventListener('storage', function(event) {
    //     if (event.key === 'isUserLoggedInMarquesAutoDetail') {
    //         atualizarVisibilidadeMenu();
    //     }
    // });
});