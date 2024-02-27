const user= 2;
document.addEventListener("DOMContentLoaded", function() {
    // Ottieni il modal
    var modal = document.getElementById("signal");

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
// Trova tutti i bottoni "Modifica"
var modifyButtons = document.querySelectorAll('.btn-modify');

modifyButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        var paragraph = button.previousElementSibling;

        if (button.textContent === 'Modifica') {
            // Rendi il paragrafo editabile solo se il testo del bottone è "Modifica"
            paragraph.contentEditable = true;
            paragraph.focus();
            // Cambia il testo del bottone in "Salva"
            button.textContent = 'Salva';
        } else {
            // Quando il testo del bottone è "Salva", salva le modifiche e ripristina il bottone a "Modifica"
            paragraph.contentEditable = false;
            button.textContent = 'Modifica';
        }
    });
});
function getAllMultimedia() {
    const multimedia = { name: name, description: description, path:path };
    fetch(`http://localhost:8080/multimedia/getAll`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(multimedia),
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            // Puoi aggiungere qui ulteriori azioni dopo il salvataggio
        })
        .catch(error => console.error('Errore: Multimedia non creato', error));
}

// Chiama la funzione onPageLoad quando la pagina è completamente caricata
document.addEventListener('DOMContentLoaded', popolaMenuTendina);
function popolaMenuTendina() {
    fetch('http://localhost:8080/points/getAll')
        .then(response => response.json())
        .then(data => {
            const selectPoint = document.getElementById('selectPoint');
            data.forEach(point => {
                const option = document.createElement('option');
                option.value = point.id;
                option.textContent = point.name;
                selectPoint.appendChild(option);
            });
        })
        .catch(error => console.error('Errore durante il recupero dei punti:', error));
}

async function salvaMultimedia(event) {
    event.preventDefault(); // Impedisce il comportamento predefinito di aggiornare la pagina

    const selectPoint = document.getElementById('selectPoint');
    const pointId = selectPoint.value;
    console.log(pointId);
    const name = document.getElementById('multimediaName').value;
    const description = document.getElementById('multimediaDescription').value;
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    const formData = new FormData();
    formData.append('file', file);

    if (!pointId || !file) {
        alert('Compila tutti i campi prima di salvare.');
        return false; // Impedisce l'invio del modulo
    }

    const path = `${pointId}_${generateUniqueNumber()}`;
    try {
        await fetch(`http://localhost:8080/multimedia/add?userId=${user}&pointId=${pointId}&name=${name}&description=${description}&path=${path}`, {
            method: 'POST',
            body: formData,
        });
        // Aggiungi qui eventuali azioni dopo il completamento della richiesta
    } catch (error) {
        console.error('Errore durante la richiesta fetch:', error);
        return false; // Impedisce l'invio del modulo in caso di errore
    }

    return true; // Consente l'invio del modulo
}


function generateUniqueNumber() {
    return Math.floor(Math.random() * 10000); // Cambia 10000 con un numero più grande se necessario
}