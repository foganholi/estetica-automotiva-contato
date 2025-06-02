// Em script.js (para index.html)

document.addEventListener('DOMContentLoaded', function() {
    // Lógica do Menu Mobile (hamburguer)
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

    // Lógica para fechar o menu mobile ao clicar em um link
    const navLinks = document.querySelectorAll('.nav-menu a');
    navLinks.forEach(link => {
        // Não fechar o menu se for o link de logout ou um link que não seja de seção interna
        const href = link.getAttribute('href');
        if (link.id !== 'logoutLink' && href && href.startsWith('#') && menuToggle && navMenu) { 
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
    
    // Destaque do link ativo no menu ao rolar a página
    const sections = document.querySelectorAll('section[id]');
    const headerHeight = document.querySelector('.header')?.offsetHeight || 0; // Pega a altura do header

    function onScroll() {
        let scrollY = window.pageYOffset;
        
        sections.forEach(current => {
            if (!current) return; 
            const sectionHeight = current.offsetHeight;
            // Ajusta o sectionTop para considerar a altura do header fixo
            const sectionTop = current.offsetTop - headerHeight - 50; // -50 para um pequeno offset
            let sectionId = current.getAttribute('id');

            let navLinkForSection = document.querySelector('.nav-menu a[href="#' + sectionId + '"]');
            if(navLinkForSection){
                if (scrollY > sectionTop && scrollY <= sectionTop + sectionHeight) {
                    document.querySelectorAll('.nav-menu a').forEach(l => l.classList.remove('active'));
                    navLinkForSection.classList.add('active');
                } else {
                    // navLinkForSection.classList.remove('active'); // Remover 'active' apenas aqui pode causar problemas se outra seção já estiver ativa.
                                                                // O loop acima garante que apenas o link da seção correta tenha 'active'.
                }
            }
        });

        // Caso especial para o topo da página (link Home)
        const homeLink = document.querySelector('.nav-menu a[href="#home"]');
        if (homeLink) {
            if (scrollY < (sections[0]?.offsetTop - headerHeight - 50) || sections.length === 0) { // Se estiver acima da primeira seção ou não houver seções
                 document.querySelectorAll('.nav-menu a').forEach(l => l.classList.remove('active'));
                 homeLink.classList.add('active');
            } else if (scrollY >= (sections[0]?.offsetTop - headerHeight - 50) && !document.querySelector('.nav-menu a.active[href^="#"]')) {
                // Se rolou para a primeira seção mas nenhum link de seção está ativo (pode acontecer no carregamento)
                // A lógica acima no forEach deve cuidar disso.
            }
        }
    }
    window.addEventListener('scroll', onScroll);
    onScroll(); // Executa uma vez ao carregar para definir o estado inicial


    // --- LÓGICA PARA GERENCIAR VISIBILIDADE DOS LINKS DE LOGIN/PERFIL/SAIR ---
    const loginListItem = document.querySelector('.nav-menu ul li a[href="login/login.html"]')?.parentElement;
    const cadastroListItem = document.querySelector('.nav-menu ul li a[href="cadastro/cadastro.html"]')?.parentElement;
    const perfilListItem = document.querySelector('.nav-item-perfil');
    const sairListItem = document.querySelector('.nav-item-sair');
    const logoutLink = document.getElementById('logoutLink');

    function atualizarVisibilidadeMenu() {
        const isUserLoggedIn = localStorage.getItem('isUserLoggedInMarquesAutoDetail') === 'true';

        if (isUserLoggedIn) {
            if (loginListItem) loginListItem.style.display = 'none';
            if (cadastroListItem) cadastroListItem.style.display = 'none';
            if (perfilListItem) perfilListItem.style.display = 'list-item';
            if (sairListItem) sairListItem.style.display = 'list-item';
        } else {
            if (loginListItem) loginListItem.style.display = 'list-item';
            if (cadastroListItem) cadastroListItem.style.display = 'list-item';
            if (perfilListItem) perfilListItem.style.display = 'none';
            if (sairListItem) sairListItem.style.display = 'none';
        }
    }

    atualizarVisibilidadeMenu(); // Atualiza o menu quando a página carrega

    // Funcionalidade de Logout
    if (logoutLink) {
        logoutLink.addEventListener('click', function(event) {
            event.preventDefault();
            localStorage.removeItem('isUserLoggedInMarquesAutoDetail');
            localStorage.removeItem('userEmailMarquesAutoDetail');
            localStorage.removeItem('userNameMarquesAutoDetail'); // Limpar o nome também
            localStorage.removeItem('mockUserAgendamentos'); // Limpar agendamentos mockados ao sair
            
            alert('Você foi desconectado.');
            atualizarVisibilidadeMenu();
            // Opcional: redirecionar para a home ou login após logout
            window.location.href = 'index.html'; // ou 'login/login.html'
        });
    }

    // --- NOVA LÓGICA PARA O FORMULÁRIO DE CONTATO VIA WHATSAPP ---
    const contatoForm = document.getElementById('form-contato');

    if (contatoForm) {
        contatoForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Impede a submissão tradicional do formulário

            const nomeContatoInput = document.getElementById('nome-contato');
            const emailContatoInput = document.getElementById('email-contato');
            const assuntoContatoInput = document.getElementById('assunto-contato');
            const mensagemContatoInput = document.getElementById('mensagem-contato');

            const nome = nomeContatoInput.value.trim();
            const email = emailContatoInput.value.trim();
            const assunto = assuntoContatoInput.value.trim();
            const mensagem = mensagemContatoInput.value.trim();

            // Validação simples
            if (nome === '') {
                alert('Por favor, preencha seu nome.');
                nomeContatoInput.focus();
                return;
            }
            if (assunto === '') { // Assunto também é importante para o contexto
                alert('Por favor, preencha o assunto.');
                assuntoContatoInput.focus();
                return;
            }
            if (mensagem === '') {
                alert('Por favor, escreva sua mensagem.');
                mensagemContatoInput.focus();
                return;
            }

            // **IMPORTANTE: Substitua este número pelo número de WhatsApp do docente da empresa**
            // Formato internacional, sem o '+' ou zeros na frente, apenas código do país e número.
            // Exemplo para Brasil (São Paulo): 55119XXXXXXXX
            const numeroWhatsappEmpresa = "5511930044443"; // << SUBSTITUA AQUI COM O NÚMERO CORRETO

            let mensagemWhatsapp = `Olá, Equipe Marques AutoDetail!\n\n`; // Saudação à empresa
            mensagemWhatsapp += `Meu nome é: ${nome}\n`;
            if (email) { // Inclui email se preenchido
                mensagemWhatsapp += `Meu email para contato é: ${email}\n`;
            }
            mensagemWhatsapp += `Assunto: ${assunto}\n\n`;
            mensagemWhatsapp += `Mensagem:\n${mensagem}\n`;

            const mensagemCodificada = encodeURIComponent(mensagemWhatsapp);
            const urlWhatsapp = `https://wa.me/${numeroWhatsappEmpresa}?text=${mensagemCodificada}`;

            window.open(urlWhatsapp, '_blank'); 

            // Opcional: Limpar o formulário após tentar abrir o WhatsApp
            contatoForm.reset(); 
            // alert('Seu WhatsApp será aberto para que você possa enviar a mensagem.');
        });
    }

    // Opcional: Ouvir por mudanças no localStorage de outras abas/janelas
    // window.addEventListener('storage', function(event) {
    //     if (event.key === 'isUserLoggedInMarquesAutoDetail') {
    //         atualizarVisibilidadeMenu();
    //     }
    // });
});