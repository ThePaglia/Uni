window.addEventListener("DOMContentLoaded", function () {
    hideElem();
    showForm();
    phpRequest();
});

function hideElem() {
    let form = document.querySelectorAll("form")[0];
    let buttonValuta = document.querySelectorAll("button")[1];
    let span = document.querySelectorAll("span");
    form.style.display = "none";
    buttonValuta.style.display = "none";
    span.forEach(spanElem => {
        spanElem.style.display = "none";
    });
}

function showForm() {
    document.getElementsByTagName("button")[0].addEventListener("click", function () {
        document.getElementsByTagName("form")[0].style.display = "block";
    });
}

function phpRequest() {
    let xhr = new XMLHttpRequest();
    xhr.open("GET", "index.php", true);
    xhr.onload = function () {
        if (xhr.status == 200) {
            createTable(xhr.responseText);
            showForm();
        }
    }
    xhr.send();
}

function phpRequest(data) {
    let table = document.getElementsByTagName("table");

    let rows = data;
    for (let i = 0; i < rows.length; i++) {
        let row = row[i];
        let tr = document.createElement("tr");
        for (let j = 0; j < row.length; j++) {
            let td = document.createElement("td");
            td.textContent = row[j];
            tr.appendChild(td);
        }
    }
    table.appendChild(tr);
}