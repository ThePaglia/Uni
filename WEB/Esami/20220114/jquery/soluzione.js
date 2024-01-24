document.addEventListener("DOMContentLoaded", function () {

    const table = document.querySelectorAll("table")[0];
    const copyTable = document.querySelectorAll("table")[1];
    const button = document.querySelector("button");
    let matrix = [];

    for (let i = 0; i < 6; i++) {
        let row = document.createElement("tr");
        matrix[i] = [];
        for (let j = 0; j < 7; j++) {
            let col = document.createElement("td");
            matrix[i][j] = Math.floor(Math.random() * 2) + 1;
            matrix[i][j] == 1 ? col.style.backgroundColor = "red" : col.style.backgroundColor = "blue";
            row.appendChild(col);
        }
        table.appendChild(row);
    }

    for (let i = 0; i < table.rows.length; i++) {
        for (let j = 0; j < table.rows[i].cells.length; j++) {
            table.rows[i].cells[j].addEventListener("click", function () {
                table.rows[i].cells[j].style.backgroundColor = "";
                matrix[i][j] = 0;
            });
        }
    }

    button.addEventListener("click", function () {
        if (copyTable.rows.length > 0) {
            for (let i = copyTable.rows.length - 1; i >= 0; i--) {
                copyTable.deleteRow(i);
            }
        }
        for (let i = 0; i < 6; i++) {
            let row = document.createElement("tr");
            for (let j = 0; j < 7; j++) {
                let col = document.createElement("td");
                if (matrix[i][j] == 1) {
                    col.style.backgroundColor = "red";
                }
                else if (matrix[i][j] == 2) {
                    col.style.backgroundColor = "blue";
                }
                else {
                    col.style.backgroundColor = "";
                }
                row.appendChild(col);
            }
            copyTable.appendChild(row);
        }
    });

});