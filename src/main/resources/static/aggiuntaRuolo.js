const mostraModuloBtn = document.getElementById('mostraModuloBtn');
const registrazioneForm = document.getElementById('registrazioneForm');
const rimuoviModuloBtn = document.getElementById('rimuoviModuloBtn');
const rimozioneForm = document.getElementById('rimozioneForm');

// Aggiungi un gestore di eventi al bottone "Aggiungi Utente"
mostraModuloBtn.addEventListener('click', function() {
    // Mostra o nascondi il modulo di registrazione
    if (registrazioneForm.style.display === 'none') {
        registrazioneForm.style.display = 'block';
    } else {
        registrazioneForm.style.display = 'none';
    }
});
document.getElementById("mostraModuloBtn").addEventListener("click", function() {
    document.getElementById("board").style.display = "none"; // Nasconde la board
    document.querySelector(".popup").style.display = "block"; // Mostra il popup
    document.getElementById("popupBackground").style.display = "flex"; // Mostra lo sfondo del popup
});

document.querySelector(".close-button").addEventListener("click", function() {
    document.getElementById("board").style.display = "block"; // Riporta la board
    document.querySelector(".popup").style.display = "none"; // Nasconde il popup
    document.getElementById("popupBackground").style.display = "none"; // Nasconde lo sfondo del popup
});

document.getElementById("registrazioneForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita il comportamento predefinito di submit
    document.getElementById("board").style.display = "block"; // Riporta la board
    document.querySelector(".popup").style.display = "none"; // Nasconde il popup
    document.getElementById("popupBackground").style.display = "none"; // Nasconde lo sfondo del popup
});
// Funzione per nascondere il modulo di registrazione e rimuovere l'attributo required dai campi
document.querySelector(".close-button").addEventListener("click", function() {
    // Nasconde il modulo di registrazione
    document.querySelector(".popup").style.display = "none";
    document.getElementById("popupBackground").style.display = "none";

    // Rimuovi l'attributo required dai campi del modulo di registrazione
    var formFields = document.querySelectorAll("#registrazioneForm input, #registrazioneForm select");
    formFields.forEach(function(field) {
        field.removeAttribute("required");
    });
});

rimuoviModuloBtn.addEventListener('click', function() {
    // Mostra o nascondi il modulo di registrazione
    if (rimozioneForm.style.display === 'none') {
        rimozioneForm.style.display = 'block';
    } else {
        rimozioneForm.style.display = 'none';
    }
});
document.getElementById("rimuoviModuloBtn").addEventListener("click", function() {
    document.getElementById("board").style.display = "none"; // Nasconde la board
    document.querySelector(".popup").style.display = "block"; // Mostra il popup
    document.getElementById("popupBackground").style.display = "flex"; // Mostra lo sfondo del popup
});

document.getElementById("rimozioneForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita il comportamento predefinito di submit
    document.getElementById("board").style.display = "block"; // Riporta la board
    document.querySelector(".popup").style.display = "none"; // Nasconde il popup
    document.getElementById("popupBackground").style.display = "none"; // Nasconde lo sfondo del popup
});

document.querySelector(".close-button").addEventListener("click", function() {
    // Nasconde il modulo di registrazione
    document.querySelector(".popup").style.display = "none";
    document.getElementById("popupBackground").style.display = "none";

    // Rimuovi l'attributo required dai campi del modulo di registrazione
    var formFields = document.querySelectorAll("#rimozioneForm input, #rimozioneForm");
    formFields.forEach(function(field) {
        field.removeAttribute("required");
    });
});
