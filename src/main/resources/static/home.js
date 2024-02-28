document.addEventListener('DOMContentLoaded', function() {
    const loginButton = document.querySelector('.btn-primary');
    const homeLink = document.querySelector('.navbar-brand');

    if (loginButton) {
        loginButton.addEventListener('click', function() {
            window.location.href = 'Login.html';
        });
    }

    if (homeLink) {
        homeLink.addEventListener('click', function() {
            window.location.href = 'home.html';
        });
    }
    if (mapLink) {
        mapLink.addEventListener('click', function() {
            window.location.href = 'mappa.html';
        });
    }
    if (aggiuntaRuoloLink) {
        mapLink.addEventListener('click', function() {
            window.location.href = 'aggiuntaRuolo.html';
        });
    }
});



