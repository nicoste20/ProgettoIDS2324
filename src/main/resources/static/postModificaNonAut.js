const user= 4;
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



window.addEventListener('DOMContentLoaded', getAllMultimedia);
// Funzione per creare e mostrare il popup
function showPopup(event) {
    const multimediaId = event.target.dataset.id; // Ottieni l'ID del multimedia dal dataset del pulsante
    // Crea il contenitore del popup
    const popupContainer = document.createElement('div');
    popupContainer.className = 'popup-container';

    // Crea il messaggio del popup
    const message = document.createElement('p');
    message.textContent = "Vuoi segnalare questo contenuto?";

    // Crea i bottoni "Sì" e "No"
    const yesButton = document.createElement('button');
    yesButton.textContent = "Sì";
    yesButton.className = "action-button"; // Aggiungi classe per lo stile CSS
    yesButton.addEventListener('click', function() {
        // Chiamata alla funzione per segnalare il contenuto
        reportContent(multimediaId);
        // Chiudi il popup
        popupContainer.remove();
    });

    const noButton = document.createElement('button');
    noButton.textContent = "No";
    noButton.className = "action-button"; // Aggiungi classe per lo stile CSS
    noButton.addEventListener('click', function() {
        // Chiudi il popup
        popupContainer.remove();
    });

    // Aggiungi messaggio e bottoni al contenitore del popup
    popupContainer.appendChild(message);
    popupContainer.appendChild(yesButton);
    popupContainer.appendChild(noButton);

    // Aggiungi il popup al body del documento
    document.body.appendChild(popupContainer);
}



function reportContent(multimediaId){
    const user1=3;
    console.log(multimediaId);
    // Esegui la richiesta fetch
    fetch(`http://localhost:8080/multimedia/signal?userId=${user1}&multimediaId=${multimediaId}`,{
        method: 'PUT'
    }).then(response => {
            // Controlla lo stato della risposta
            if (response.ok) {
                console.log('Multimedia signaled successfully.');
            } else {
                console.error('Failed to signal multimedia.');
            }
        })
        .catch(error => {
            console.error('An error occurred while signaling multimedia:', error);
        });
}

function getAllMultimedia() {
    fetch('http://localhost:8080/multimedia/getAll')
        .then(response => response.json()) // Trasforma la risposta in formato JSON
        .then(multimediaList => { // Utilizza la risposta JSON
            // Se non ci sono immagini nel database, mostra un messaggio
            if (multimediaList.length === 0) {
                document.getElementById('photo-container').innerHTML = "<p>Nessuna immagine trovata.</p>";
                return;
            }

            // Trova l'elemento HTML dove verranno visualizzate le immagini
            const imageContainer = document.getElementById('photo-container');

            // Itera su ogni oggetto multimediale e crea un elemento immagine per ciascuno
            multimediaList.forEach(multimedia => {
                if(multimedia.validate){
                    console.log(multimedia.path);
                    const imgElement = document.createElement('img');
                    imgElement.src = `/upload/${multimedia.path}.jpg`; // Assicurati che il percorso sia corretto
                    imgElement.alt = multimedia.name;
                    imageContainer.appendChild(imgElement);

                    // Imposta la dimensione desiderata per l'immagine (ad esempio larghezza 600px)
                    imgElement.style.width = '600px';

                    // Aggiungi la descrizione sotto l'immagine
                    const descriptionElement = document.createElement('p');
                    descriptionElement.textContent = multimedia.description;

                    // Crea bottoni per "Segnala" e "Commenti"
                    const segnalaButton = document.createElement('button');
                    segnalaButton.textContent = "Segnala";
                    segnalaButton.className = "action-button"; // Aggiungi classe per lo stile CSS
                    segnalaButton.dataset.id = multimedia.id; // Imposta l'ID come attributo personalizzato
                    segnalaButton.addEventListener('click', showPopup); // Aggiungi gestore di eventi per mostrare il popup

                    const commentiButton = document.createElement('button');
                    commentiButton.textContent = "Commenti";
                    commentiButton.className = "action-button"; // Aggiungi classe per lo stile CSS
                    commentiButton.dataset.id = multimedia.id; // Imposta l'ID come attributo personalizzato
                    commentiButton.addEventListener('click', showComment); // Aggiungi gestore di eventi per mostrare il popup

                    // Crea un contenitore per i bottoni
                    const buttonContainer = document.createElement('div');
                    buttonContainer.style.display = 'flex';
                    buttonContainer.style.justifyContent = 'center';
                    buttonContainer.style.marginTop = '10px';
                    buttonContainer.appendChild(segnalaButton);
                    buttonContainer.appendChild(commentiButton);

                    // Crea un contenitore per l'immagine e la descrizione
                    const imageWrapper = document.createElement('div');
                    imageWrapper.appendChild(imgElement);
                    imageWrapper.appendChild(descriptionElement);
                    imageWrapper.appendChild(buttonContainer);
                    imageWrapper.style.marginBottom = "20px"; // Aggiunge spazio inferiore tra le immagini

                    // Aggiungi il contenitore all'elemento container delle immagini
                    imageContainer.appendChild(imageWrapper);
                }
            });
        })
        .catch(error => {
            console.error('Si è verificato un errore durante il recupero delle immagini:', error);
        });
}


function showComment(event){
    const multId = event.target.dataset.id;
    console.log(multId)

    // Costruisci l'URL della pagina HTML desiderata
    const url = `commentiModifica.html?id=${multId}`;

    // Reindirizza il browser alla nuova pagina
    window.location.href = url;
}



// Chiamata alla funzione per recuperare e visualizzare le immagini al caricamento della pagina
window.addEventListener('DOMContentLoaded', getAllMultimedia);


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

    const path = `${pointId}_${generateUniqueNumber()}`;


    const formData = new FormData();
    formData.append('file', file);
    formData.append('path', path);
    formData.append('name',name);
    formData.append('description',description);
    formData.append('pointId',pointId);
    formData.append('userId',user);

    if (!pointId || !file) {
        alert('Compila tutti i campi prima di salvare.');
        return false; // Impedisce l'invio del modulo
    }

    try {
        await fetch(`http://localhost:8080/multimedia/add`, {
            method: 'POST',
            body: formData,
        });
        // Aggiungi qui eventuali azioni dopo il completamento della richiesta
    } catch (error) {
        console.error('Errore durante la richiesta fetch:', error);
        return false; // Impedisce l'invio del modulo in caso di errore
    }
    location.reload();
    return true; // Consente l'invio del modulo

}


function generateUniqueNumber() {
    return Math.floor(Math.random() * 10000); // Cambia 10000 con un numero più grande se necessario
}