console.log("Hello World!");

// span id="ciao"
const tagHello = document.getElementById("ciao");
tagHello.innerHTML = "Hello World!";

// span class="anno" usando getElementsByClassName (restituisce un array)
const tagYear = document.getElementsByClassName("anno")[0];
tagYear.innerHTML = "2023 getElementsByClassName";

// span class="anno" usando querySelector
/*
const tagYear2 = document.querySelector(".anno");
tagYear2.innerHTML = "2023 querySelector";
*/