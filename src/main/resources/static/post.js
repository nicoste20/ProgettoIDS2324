const commentButtons = document.querySelectorAll('.btn-comment');

// Itera su ciascun bottone per aggiungere l'evento click
commentButtons.forEach(button => {
    button.addEventListener('click', function() {
        // Effettua il reindirizzamento alla pagina dei commenti
        window.location.href = 'commenti.html';
    });
});
