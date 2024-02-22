document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita il comportamento predefinito del form

    // Ottieni il valore inserito nell'input email
    var email = document.getElementById('email').value.toLowerCase();
    switch (true){
        case email.includes('animatore'):
            window.location.href = '/templates/UsersType/animatore.html';
            break;
        case email.includes('contributorautorizzato'):
            window.location.href = '/templates/UsersType/contributorAutorizzato.html';
            break;
        case email.includes('curatore'):
            window.location.href = '/templates/UsersType/curatore.html';
            break;
        case email.includes('turistaautorizzato'):
            window.location.href = '/templates/UsersType/turistaAutorizzato.html';
            break;
        case email.includes('gestorepiattaforma'):
            window.location.href = '/templates/UsersType/gestorePiattaforma.html';
            break;
        default:
            alert('Effettua il login');
    }
});
