document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita il comportamento predefinito del form

    // Ottieni il valore inserito nell'input email
    var email = document.getElementById('email').value.toLowerCase();
    switch (true){
        case email.includes('animatore'):
            window.location.href = 'animatore.html';
            break;
        case email.includes('contributorautorizzato'):
            window.location.href = 'contributoAutorizzato.html';
            break;
        case email.includes('curatore'):
            window.location.href = 'curatore.html';
            break;
        case email.includes('turistaautorizzato'):
            window.location.href = 'turistaAutorizzato.html';
            break;
        case email.includes('gestorepiattaforma'):
            window.location.href = 'gestorePiattaforma.html';
            break;
        default:
            alert('Effettua il login');
    }
});
