document.addEventListener('DOMContentLoaded', function () {
    // Ottieni il riferimento al bottone "Carica Dati"
    var caricaDatiButton = document.querySelector('button');

    // Aggiungi un gestore di eventi al clic sul bottone "Carica Dati"
    caricaDatiButton.addEventListener('click', function () {
        // Crea una nuova richiesta XMLHttpRequest
        var xhr = new XMLHttpRequest();

        // Configura la richiesta con il metodo GET e l'URL del file JSON
        xhr.open('GET', 'data.json', true);

        // Configura il gestore di eventi per la risposta alla richiesta
        xhr.addEventListener("load", function () {
            if (xhr.status == 200) {
                // Analizza il JSON ottenuto dalla risposta
                var jsonData = JSON.parse(xhr.responseText);

                // Ottieni il riferimento al tag main
                var mainElement = document.querySelector('main');

                // Per ogni elemento nel vettore data, crea un div e aggiungi al tag main
                jsonData.data.forEach(function (pokemon) {
                    // Crea un nuovo div
                    var newDiv = document.createElement('div');

                    // Crea un elenco non ordinato
                    var ulElement = document.createElement('ul');

                    // Aggiungi ogni proprietà (id, nome e tipo) all'elenco non ordinato
                    Object.keys(pokemon).forEach(function (key) {
                        var liElement = document.createElement('li');
                        liElement.textContent = key + ': ' + pokemon[key];
                        ulElement.appendChild(liElement);
                    });

                    // Aggiungi l'elenco non ordinato al div
                    newDiv.appendChild(ulElement);

                    // Crea due bottoni (Up e Down)
                    var upButton = document.createElement('button');
                    upButton.textContent = 'Up';
                    upButton.addEventListener('click', function () {
                        // Sposta il div sopra
                        mainElement.insertBefore(newDiv, newDiv.previousElementSibling);
                    });

                    var downButton = document.createElement('button');
                    downButton.textContent = 'Down';
                    downButton.addEventListener('click', function () {
                        // Sposta il div sotto
                        mainElement.insertBefore(newDiv.nextElementSibling, newDiv);
                    });

                    // Aggiungi i bottoni al div
                    newDiv.appendChild(upButton);
                    newDiv.appendChild(downButton);

                    // Aggiungi il nuovo div al tag main
                    mainElement.appendChild(newDiv);
                });
            }
        });

        // Invia la richiesta
        xhr.send();
    });
});
