document.addEventListener("DOMContentLoaded", function() {
    // Ottieni il modal
    var modal = document.getElementById("myModal");

    // Ottieni tutti i pulsanti di segnalazione
    var btns = document.querySelectorAll(".btn-report");

    // Associa una funzione all'evento clic di ogni pulsante di segnalazione
    btns.forEach(function(btn) {
        btn.addEventListener("click", function() {
            modal.style.display = "block";
        });
    });

    // Ottieni il pulsante per confermare "Sì"
    var btnYes = document.getElementById("btn-confirm-yes");

    // Ottieni il pulsante per confermare "No"
    var btnNo = document.getElementById("btn-confirm-no");

    // Quando l'utente clicca su "Sì", fai qualcosa
    btnYes.addEventListener("click", function() {
        // Qui puoi eseguire l'azione di segnalazione
        // In questo esempio, chiudiamo semplicemente il modal
        modal.style.display = "none";
        alert("Hai segnalato con successo!");
    });

    // Quando l'utente clicca su "No", chiudi semplicemente il modal
    btnNo.addEventListener("click", function() {
        modal.style.display = "none";
    });

    // Chiudi il modal quando l'utente clicca al di fuori di esso
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
const commentButtons = document.querySelectorAll('.btn-comment');

// Itera su ciascun bottone per aggiungere l'evento click
commentButtons.forEach(button => {
    button.addEventListener('click', function() {
        // Effettua il reindirizzamento alla pagina dei commenti
        window.location.href = 'commentiModifica.html';
    });
});
