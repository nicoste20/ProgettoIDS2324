const map = L.map('map').setView([43.52, 13.244], 15);

const tiles = L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const marker = L.marker([43.521824,13.244226]).addTo(map)
    .bindPopup('<b>Sono un punto</b><br />.').openPopup();

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