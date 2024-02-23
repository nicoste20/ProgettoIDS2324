document.addEventListener('DOMContentLoaded', function() {
    const submitButton = document.getElementById('submit-comment');
    const commentBox = document.getElementById('new-comment-form');

    submitButton.addEventListener('click', function() {
        const commentContent = document.getElementById('comment-content').value;
        const commentAuthor = 'Nuovo Utente'; // Puoi definire un'autenticazione utente per ottenere il nome utente reale
        const currentDate = new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });

        // Creazione del nuovo commento
        const newComment = document.createElement('div');
        newComment.classList.add('comment');
        newComment.innerHTML = `
      <p class="comment-content">${commentContent}</p>
      <p class="comment-author">${commentAuthor}</p>
      <p class="comment-date">${currentDate}</p>
    `;

        // Inserimento del nuovo commento prima del box di inserimento
        const commentBoard = document.querySelector('.board');
        commentBoard.insertBefore(newComment, commentBox);

        // Pulizia del campo del commento dopo l'invio
        document.getElementById('comment-content').value = '';
    });
});
