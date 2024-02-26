const map = L.map('map').setView([43.52, 13.244], 15);

const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const marker = L.marker([43.521824, 13.244226]).addTo(map)
    .bindPopup('<b>Teatro pergolesi</b>').openPopup();

const popup = L.popup()
    .setLatLng([43.521824, 13.244226])

const userId= 4;
function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
}

map.on('click', onMapClick);

// Funzione per spostare la mappa al centro di una posizione selezionata
function centerMapToLocation(latitude, longitude) {
    map.setView([latitude, longitude], 13); // Imposta il livello di zoom desiderato (13 in questo esempio)
}

// Trova il bottone nella pagina
var centerMapButton = document.getElementById('centerMapButton');

const user=3;

// Aggiungi un gestore di eventi al bottone
centerMapButton.addEventListener('click', function () {
    // Chiama la funzione per spostare la mappa al centro di una posizione selezionata
    centerMapToLocation(43.521824, 13.244226); // Coordinate per New York City (Puoi sostituirle con quelle desiderate)
    const popup = L.popup()
        .setLatLng([43.521824, 13.244226])
        .setContent('Centro di Jesi')
        .openOn(map);
});
function createPointMonument(name, x, y, type, inaugurationDate , story) {
    const monument= {name: name, x:x, y:y, type:type, inaugurationDate: inaugurationDate, story:story};
    fetch(`http://localhost:8080/points/addMonument?userId=${user}`,
    {
    method: 'POST',
        headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(monument),
})
.then(response => response.text())
    .then(data => console.log(data))
    .catch(error => console.error('Error:Monument non creato', error));
}
function createPointRestaurant(name, x, y, type, typeRestaurant , openingHours) {
    const restaurant= {name: name, x:x, y:y, type:type, typeRestaurant: typeRestaurant, openingHours:openingHours};
    fetch(`http://localhost:8080/points/addRestaurant?userId=${user}`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(restaurant),
        })
        .then(response => response.text())
        .then(data => console.log(data))
        .catch(error => console.error('Error:Restaurant non creato', error));
}
function createPointGreenZone(name, x, y, type, characteristics) {
    const greenZone= {name: name, x:x, y:y, type:type, characteristics: characteristics};
    fetch(`http://localhost:8080/points/addGreenZone?userId=${user}`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(greenZone),
        })
        .then(response => response.text())
        .then(data => console.log(data))
        .catch(error => console.error('Error:Zona verde non creata', error));
}
function createPointSquare(name, x, y, type, history) {
    const square= {name: name, x:x, y:y, type:type, history: history};
    fetch(`http://localhost:8080/points/addGreenZone?userId=${user}`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(square),
        })
        .then(response => response.text())
        .then(data => console.log(data))
        .catch(error => console.error('Error:Piazza non creata', error));
}

// Funzione per rimuovere un marker dalla mappa
/*function removeMarker(name) {
    // Rimuovi il marker dalla mappa
    map.removeLayer(markers[name]);

    // Rimuovi il marker dal localStorage
    localStorage.removeItem(name);

    // Rimuovi il marker dal dizionario
    delete markers[name];
}
*/
// Aggiungi un gestore di eventi per il click sulla mappa
map.on('click', function (e) {
    // Visualizza il menu a tendina con SweetAlert2
    Swal.fire({
        title: 'Seleziona il tipo',
        input: 'select',
        inputOptions: {
            'Monument': 'Monumento',
            'Restaurant': 'Ristorante',
            'Greenzone': 'Parco',
            'Square': 'Piazza'
        },
        inputPlaceholder: 'Seleziona un tipo',
        showCancelButton: true,
        confirmButtonText: 'Avanti',
        cancelButtonText: 'Annulla',
        inputValidator: (value) => {
            return new Promise((resolve) => {
                if (value !== '') {
                    resolve();
                } else {
                    resolve('Devi selezionare un tipo');
                }
            });
        }
    }).then((result) => {
        if (result.isConfirmed) {
            var type = result.value;
            // Se il tipo è "Ristorante", mostra un altro menu per selezionare il tipo specifico
            if (type === 'Restaurant') {
                Swal.fire({
                    title: 'Seleziona il tipo di ristorante',
                    input: 'select',
                    inputOptions: {
                        'Chinese': 'Cinese',
                        'Italian': 'Italiano',
                        'Mexican': 'Messicano',
                        'Japanese': 'Giapponese'
                    },
                    inputPlaceholder: 'Seleziona un tipo di ristorante',
                    showCancelButton: true,
                    confirmButtonText: 'Aggiungi',
                    cancelButtonText: 'Annulla',
                    inputValidator: (value) => {
                        return new Promise((resolve) => {
                            if (value !== '') {
                                resolve();
                            } else {
                                resolve('Devi selezionare un tipo di ristorante');
                            }
                        });
                    }
                }).then((subResult) => {
                    var subType = subResult.value;
                    var latitude = e.latlng.lat;
                    var longitude = e.latlng.lng;
                    var name = prompt('Inserisci il nome per il nuovo punto:');
                    var opening = prompt('Inserisci un orario di apertura (formato HH:MM):');
                   // if (subResult.isConfirmed) {
                        if (name && opening) {
                            createPointRestaurant(name, latitude, longitude, type, subType, opening);
                        }

                    ricaricaPaginaDopoTempo(2000);
                });
            } else if (type === "Monument") {
                // Altrimenti, aggiungi il marker con il tipo selezionato
                var latitude = e.latlng.lat;
                var longitude = e.latlng.lng;
                var name = prompt('Inserisci il nome per il nuovo punto:');
                var history = prompt("Inserisci la storia per questo monumento:")
                var date;
                do {
                    date = prompt('Inserisci una data nel formato YYYY-MM-DD:');
                } while (!isValidDate(date));

                function isValidDate(dateString) {
                    // Utilizza una espressione regolare per controllare se la stringa è nel formato corretto "YYYY-MM-DD"
                    var regex = /^\d{4}-\d{2}-\d{2}$/;
                    return regex.test(dateString);
                }

                if (name && date) {
                    createPointMonument(name, latitude, longitude, type, date, history);
                }

                ricaricaPaginaDopoTempo(2000);
            } else if (type === "Greenzone") {
                // Altrimenti, aggiungi il marker con il tipo selezionato
                var latitude = e.latlng.lat;
                var longitude = e.latlng.lng;
                var name = prompt('Inserisci il nome per il nuovo punto:');
                var characteristics = prompt("Inserisci la storia per questo parco:")
                if (name && characteristics) {
                    createPointGreenZone(name, latitude, longitude, type, characteristics);
                }

                ricaricaPaginaDopoTempo(2000);
            } else {
                // Altrimenti, aggiungi il marker con il tipo selezionato
                var latitude = e.latlng.lat;
                var longitude = e.latlng.lng;
                var name = prompt('Inserisci il nome per il nuovo punto:');
                var history = prompt("Inserisci la storia per questa piazza:")
                if (name && history) {
                    createPointSquare(name, latitude, longitude, type, history)
                }

                ricaricaPaginaDopoTempo(2000);
            }
        }
    });
});

function ricaricaPaginaDopoTempo(tempo) {
    setTimeout(function() {
        location.reload();
    }, tempo);
}

function onPageLoad() {
    fetch('http://localhost:8080/points/getAll')
        .then(response => response.json())
        .then(data => {
            data.forEach(point => {
                var aa = L.marker([point.x, point.y]).addTo(map);
                aa.bindPopup(`<b>${point.name}</b><br>Tipo: ${point.type}`);
            });
        })
        .catch(error => {
            console.error('Si è verificato un errore durante il caricamento dei punti:', error);
        });
}

// Chiama la funzione onPageLoad quando la pagina è completamente caricata
document.addEventListener('DOMContentLoaded', onPageLoad);