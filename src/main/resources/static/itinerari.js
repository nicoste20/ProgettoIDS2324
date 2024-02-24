const map = L.map('map').setView([43.52, 13.244], 15);

const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

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


fetch('http://localhost:8080/itineraries/getAll')
    .then(response => response.json())
    .then(data => {
        // Popola il menu a tendina con gli itinerari ottenuti
        const selectItinerario = document.getElementById('selectItinerario');
        data.forEach(itinerario => {
            const option = document.createElement('option');
            option.value = itinerario.id;
            option.textContent = itinerario.name;
            selectItinerario.appendChild(option);
        });
    })
    .catch(error => console.error('Errore durante il recupero degli itinerari:', error));

selectItinerario.addEventListener('change', function() {
    const selectedItineraryId = this.value;
    console.log(selectedItineraryId);

    // Rimuovi tutti i marker e le polyline dalla mappa
    map.eachLayer(function(layer) {
        if (layer instanceof L.Marker || layer instanceof L.Polyline) {
            map.removeLayer(layer);
        }
    });

    // Fetch per ottenere i punti dell'itinerario selezionato e aggiungerli sulla mappa
    fetch(`http://localhost:8080/itineraries/get/itinerary?id=${selectedItineraryId}`)
        .then(response => response.json())
        .then(data => {
            let points = [];
            data.forEach(point => {
                points.push([point.x, point.y]);
                var marker = L.marker([point.x, point.y]).addTo(map);
                marker.bindPopup(`<b>${point.name}</b><br>Tipo: ${point.type}`);
            });
            // Crea una polyline con i punti dell'itinerario e aggiungila alla mappa
            var polyline = L.polyline(points, {color: 'blue'}).addTo(map);
        })
        .catch(error => console.error('Errore durante il recupero dell\'itinerario:', error));
});


