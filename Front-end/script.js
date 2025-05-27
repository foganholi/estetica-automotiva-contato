document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.querySelector('.menu-toggle');
    const navMenu = document.querySelector('.nav-menu');
    const navLinks = document.querySelectorAll('.nav-menu a');
    const sections = document.querySelectorAll('section[id]');

    // Toggle para menu mobile
    if (menuToggle && navMenu) {
        menuToggle.addEventListener('click', () => {
            navMenu.classList.toggle('active');
            // Alterna o ícone do menu hamburguer
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

    // Fecha o menu mobile ao clicar em um link
    navLinks.forEach(link => {
        link.addEventListener('click', () => {
            if (navMenu.classList.contains('active')) {
                navMenu.classList.remove('active');
                const icon = menuToggle.querySelector('i');
                icon.classList.remove('fa-times');
                icon.classList.add('fa-bars');
                menuToggle.setAttribute('aria-label', 'Abrir menu');
            }
        });
    });

    // Destaque do link ativo no menu ao rolar a página
    function onScroll() {
        let scrollY = window.pageYOffset;

        sections.forEach(current => {
            const sectionHeight = current.offsetHeight;
            // Ajuste o offsetTop com a altura do header fixo e um pequeno buffer
            const sectionTop = current.offsetTop - (document.querySelector('.header').offsetHeight + 50);
            let sectionId = current.getAttribute('id');

            if (scrollY > sectionTop && scrollY <= sectionTop + sectionHeight) {
                document.querySelector('.nav-menu a[href*=' + sectionId + ']').classList.add('active');
            } else {
                document.querySelector('.nav-menu a[href*=' + sectionId + ']').classList.remove('active');
            }
        });
    }
    window.addEventListener('scroll', onScroll);
    // Executa uma vez ao carregar para definir o link ativo inicial, se houver hash na URL
    onScroll();

});

document.getElementById('form-contato').addEventListener('submit', function(event) {
    event.preventDefault();
    
    // Coletar os dados do formulário
    const nome = document.getElementById('nome-contato').value;
    const email = document.getElementById('email-contato').value;
    const assunto = document.getElementById('assunto-contato').value;
    const mensagem = document.getElementById('mensagem-contato').value;
    
    // Criar a URL do WhatsApp com os dados
    const whatsappNumber = '559999999999'; // Substitua com seu número do WhatsApp
    const message = `*Nome:* ${nome}%0A*Email:* ${email}%0A*Assunto:* ${assunto}%0A*Mensagem:* ${mensagem}`;
    const whatsappURL = `https://wa.me/${11949496463}?text=${message}`;
    
    // Redirecionar para o WhatsApp
    window.open(whatsappURL, '_blank');
});
