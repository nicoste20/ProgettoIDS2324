document.addEventListener('DOMContentLoaded', function() {
    const loginButton = document.querySelector('.btn-primary');
    const homeLink = document.querySelector('.navbar-brand');

    if (loginButton) {
        loginButton.addEventListener('click', function() {
            window.location.href = 'login.html';
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

});


