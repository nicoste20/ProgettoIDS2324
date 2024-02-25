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
//    document.getElementById("board").style.display = "block"; // Riporta la board
    document.querySelector(".popup").style.display = "none"; // Nasconde il popup
    document.getElementById("popupBackground").style.display = "none"; // Nasconde lo sfondo del popup
});

document.querySelector(".close-button2").addEventListener("click", function() {
    // Nasconde il modulo di registrazione
    document.querySelector(".popup").style.display = "none";
    document.getElementById("popupBackground").style.display = "none";

    // Rimuovi l'attributo required dai campi del modulo di registrazione
    var formFields = document.querySelectorAll("#rimozioneForm input, #rimozioneForm select");
    formFields.forEach(function(field) {
        field.removeAttribute("required");
    });

    // Aggiungi codice per nascondere il form
    document.getElementById("rimozioneForm").style.display = "none";
});

function createUser() {
    var nome = document.getElementById("nome").value;
    var cognome = document.getElementById("cognome").value;
    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    // Ottieni il ruolo selezionato dal menu a lista
    var ruoloSelect = document.getElementById("ruolo");
    var ruoloSelezionato = ruoloSelect.value;

    console.log(ruoloSelezionato);
    const users = { role: ruoloSelezionato, name: nome, surname:cognome,username:username,email:email,password:password };

    fetch('http://localhost:8080/users/add', {
    method: 'POST',
        headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(users),
})
.then(response => response.text())
        .then(data => {
            console.log(data);
            refreshPage(); // Esegui il refresh della pagina dopo l'esecuzione del metodo makeCurator()
        })
        .catch(error => console.error('Error: Utente non creato', error));
}

function deleteUser() {
    var email = document.getElementById("mail").value;


    fetch('http://localhost:8080/users/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: email
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            refreshPage(); // Esegui il refresh della pagina dopo l'esecuzione del metodo makeCurator()
        })
        .catch(error => console.error('Errore: Utente cancellato', error));
}


function getUsersFromDatabase() {
    // Ottieni il titolo dell'elemento <h2>
    const boardTitle = document.querySelector('.board').textContent;

    // Esegui una richiesta Fetch per ottenere gli utenti dal database Spring Boot
    fetch(`http://localhost:8080/users/getAll`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nella richiesta Fetch');
            }
            return response.json();
        })
        .then(users => {
            // Manipola la risposta ottenuta
            displayUsers(users);
        })
        .catch(error => {
            console.error('Si è verificato un errore:', error);
        });
}

function displayUsers(users) {
    // Ottieni la lista degli utenti
    const userListElement = document.querySelector('.user-list');

    // Cancella la lista degli utenti attuale
    userListElement.innerHTML = '';

    // Aggiungi gli utenti alla lista
    users.forEach(user => {
        const li = document.createElement('li');
        li.textContent = "E-mail: " + user.email + " Ruolo: " + user.userType;
        var UEmail= user.email;
        if (user.userType === "Contributor") {
            const button = document.createElement('button');
            button.classList.add('editButton', 'btn', 'btn-secondary');
            button.textContent = 'Modifica ruolo';
            button.addEventListener('click', () => {
                // Creazione della finestra pop-up
                const popupContainer = document.createElement('div');
                popupContainer.classList.add('popup-container');

                const popupContent = document.createElement('div');
                popupContent.classList.add('popup-content');
                popupContent.style.backgroundColor = 'white';
                popupContent.style.padding = '20px';
                popupContent.style.borderRadius = '8px';
                popupContent.style.boxShadow = '0 0 10px rgba(0, 0, 0, 0.3)';
                popupContent.style.maxWidth = '300px';
                popupContent.style.textAlign = 'center';
                popupContent.style.position = 'fixed';
                popupContent.style.top = '50%';
                popupContent.style.left = '50%';
                popupContent.style.transform = 'translate(-50%, -50%)';



                const promptText = document.createElement('p');
                promptText.textContent = `Cosa vuoi fare con il ruolo di ${user.name}?`;
                promptText.style.marginBottom = '15px';

                const buttonChange = document.createElement('button');
                buttonChange.textContent = 'Animatore';
                buttonChange.style.margin = '0 10px';
                buttonChange.style.padding = '8px 16px';
                buttonChange.style.border = 'none';
                buttonChange.style.borderRadius = '4px';
                buttonChange.style.backgroundColor = '#007bff'; // Colore di sfondo del pulsante Cambia ruolo
                buttonChange.style.color = 'white';
                buttonChange.style.cursor = 'pointer';
                buttonChange.addEventListener('click', () => {
                    makeAnimator(UEmail);
                    // Qui puoi aggiungere la logica per cambiare il ruolo dell'utente
                    //console.log(`Cambia ruolo per ${user.name}`);
                    // Chiudi la finestra pop-up
                    popupContainer.remove();
                });

                const buttonCancel = document.createElement('button');
                buttonCancel.textContent = 'Curatore';
                buttonCancel.style.margin = '0 10px';
                buttonCancel.style.padding = '8px 16px';
                buttonCancel.style.border = 'none';
                buttonCancel.style.borderRadius = '4px';
                buttonCancel.style.backgroundColor = '#007bff'; // Colore di sfondo del pulsante Annulla
                buttonCancel.style.color = 'white';
                buttonCancel.style.cursor = 'pointer';
                buttonCancel.addEventListener('click', () => {
                    makeCurator(UEmail);
                    // Chiudi la finestra pop-up
                    popupContainer.remove();
                });

                popupContent.appendChild(promptText);
                popupContent.appendChild(buttonChange);
                popupContent.appendChild(buttonCancel);
                popupContainer.appendChild(popupContent);
                document.body.appendChild(popupContainer);
            });

            li.appendChild(button);
        }

        userListElement.appendChild(li);
    });
}




// Chiamata alla funzione per ottenere gli utenti dal database quando necessario
getUsersFromDatabase();

var ManagerEmail = "ijeievn@example.com";

function makeAnimator(emailNew){

    fetch(`http://localhost:8080/users/addAnimator?email=${emailNew}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: ManagerEmail,
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            refreshPage(); // Esegui il refresh della pagina dopo l'esecuzione del metodo makeCurator()
        })
        .catch(error => console.error('Error: Animatore non creato', error));
}

function makeCurator(email){

    fetch(`http://localhost:8080/users/addCurator?email=${email}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: ManagerEmail,
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            refreshPage(); // Esegui il refresh della pagina dopo l'esecuzione del metodo makeCurator()
        })
        .catch(error => console.error('Error: Curatore non creato', error));
}

function refreshPage() {
    setTimeout(function() {
        location.reload();
    }, 2000); // Aspetta 2 secondi prima di eseguire il refresh della pagina
}