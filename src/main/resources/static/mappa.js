const map = L.map('map').setView([43.52, 13.244], 15);

const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const marker = L.marker([43.521824,13.244226]).addTo(map)
    .bindPopup('<b>Teatro pergolesi</b>').openPopup();

const popup = L.popup()
    .setLatLng([43.521824, 13.244226])
    .setContent('Centro di Jesi')
    .openOn(map);

function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent(`Hai cliccato la mappa nelle cordinate: ${e.latlng.toString()}`)
        .openOn(map);
}
map.on('click', onMapClick);
// Funzione per spostare la mappa al centro di una posizione selezionata
function centerMapToLocation(latitude, longitude) {
    map.setView([latitude, longitude], 13); // Imposta il livello di zoom desiderato (13 in questo esempio)
}

// Trova il bottone nella pagina
var centerMapButton = document.getElementById('centerMapButton');

// Aggiungi un gestore di eventi al bottone
centerMapButton.addEventListener('click', function() {
    // Chiama la funzione per spostare la mappa al centro di una posizione selezionata
    centerMapToLocation(43.521824, 13.244226); // Coordinate per New York City (Puoi sostituirle con quelle desiderate)
    const popup = L.popup()
        .setLatLng([43.521824, 13.244226])
        .setContent('Centro di Jesi')
        .openOn(map);
});



// Funzione che viene eseguita ogni volta che la pagina viene caricata o aggiornata
function onPageLoad() {
    fetch('http://localhost:8080/points/getAll')
        .then(response => response.json())
        .then(data => {
            var iconMapping = {
                Monument: L.icon({iconUrl: 'monument-icon.png', iconSize: [32, 32]}),
                GreenZone: L.icon({iconUrl: 'greenzone-icon.png', iconSize: [32, 32]}),
                Restaurant: L.icon({iconUrl: 'restaurant-icon.png', iconSize: [32, 32]}),
                Square: L.icon({iconUrl: 'square-icon.png', iconSize: [32, 32]})
            };
            data.forEach(point => {
                var aa = L.marker([point.x, point.y],{icon: icon}).addTo(map);
                aa.bindPopup(`<b>${point.name}</b><br>Tipo: ${point.type}`);
            });
        })
        .catch(error => {
            console.error('Si è verificato un errore durante il caricamento dei punti:', error);
        });
}

// Chiama la funzione onPageLoad quando la pagina è completamente caricata
document.addEventListener('DOMContentLoaded', onPageLoad);


