const tagUppercase = document.querySelector("input[value='Testo uppercase']");
const tagLowercase = document.querySelector("input[value='Testo lowercase']");
const tagSubstring = document.querySelector("input[value='Testo substring']");

const tagOutput = document.querySelector("div");

tagUppercase.addEventListener("click", () => {
    tagOutput.innerHTML = tagOutput.innerHTML.toUpperCase();
});

tagLowercase.addEventListener("click", () => {
    tagOutput.innerHTML = tagOutput.innerHTML.toLowerCase();
});

tagSubstring.addEventListener("click", () => {
    //Toglie dalla fine 5 caratteri
    tagOutput.innerHTML = tagOutput.innerHTML.substring(0, tagOutput.innerHTML.length - 5);
    //Toglie dall'inizio 5 caratteri
    tagOutput.innerHTML = tagOutput.innerHTML.slice(5, tagOutput.innerHTML.length);
});