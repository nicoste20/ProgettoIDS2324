function getMultimediaIdFromURL() {
    // Ottieni l'URL della pagina corrente
    const url = window.location.href;

    // Dividi l'URL utilizzando il carattere '?' come separatore
    const parts = url.split('?');

    // Se il numero di parti è uguale a 2, significa che l'URL contiene dei parametri
    if (parts.length === 2) {
        // Dividi la seconda parte utilizzando il carattere '=' come separatore
        const params = parts[1].split('=');

        // Se il numero di parametri è uguale a 2 e il nome del parametro è 'id', restituisci il valore
        if (params.length === 2 && params[0] === 'id') {
            return parseInt(params[1]); // Converti il valore in intero
        }
    }

    // Se non riesci a trovare l'ID nell'URL, restituisci null
    return null;
}



document.addEventListener('DOMContentLoaded', function() {

    var multimediaId = getMultimediaIdFromURL();
    console.log(multimediaId)
    // Esegui una fetch per ottenere i commenti dal server
    fetch(`http://localhost:8080/comment/multimedia?multimediaId=${multimediaId}`)
        .then(response => response.json())
        .then(data => {
            const board = document.querySelector('.board');

            // Itera su ogni commento ricevuto dalla fetch
            data.forEach(comments => {
                // Crea un elemento div per il commento
                const commentDiv = document.createElement('div');
                commentDiv.classList.add('comment');

                // Crea un elemento p per il contenuto del commento
                const commentContent = document.createElement('p');
                commentContent.classList.add('comment-content');
                commentContent.textContent = comments.comment;

                // Aggiungi il contenuto del commento al div del commento
                commentDiv.appendChild(commentContent);

                // Aggiungi il div del commento alla bacheca
                board.appendChild(commentDiv);
            });
        })
        .catch(error => {
            console.error('Errore durante il recupero dei commenti:', error);
        });
});
