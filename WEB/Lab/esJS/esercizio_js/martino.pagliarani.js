const images = document.querySelectorAll("div.slider-image img");

document.addEventListener("DOMContentLoaded", function () {
    // Nascondo tutte le immagini tranne le prime due
    for (let i = 2; i < images.length; i++) {
        images[i].style.display = "none";
    }

    images[0].classList.add("current");

    // Aggiungo l'evento click a tutte le immagini
    for (let i = 0; i < images.length; i++) {
        images[i].addEventListener("click", onClick);
    }
});

// Funzione che aggiunge la classe current all'immagine cliccata
function onClick() {
    if (!this.classList.contains("current")) {
        for (let i = 0; i < images.length; i++) {
            images[i].classList.remove("current");
            images[i].style.display = "none";
        }
        this.classList.add("current");

        showImages(images, this);
    }
}

// Funzione che mostra l'immagine cliccata e quelle adiacenti
function showImages(images, clickedImage) {
    let currentIndex = Array.from(images).indexOf(clickedImage);

    clickedImage.style.display = "";

    if (currentIndex > 0) {
        images[currentIndex - 1].style.display = "";
    }

    if (currentIndex < images.length - 1) {
        images[currentIndex + 1].style.display = "";
    }
}
