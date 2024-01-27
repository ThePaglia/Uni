document.addEventListener('DOMContentLoaded', function () {
    let main = document.querySelector("main");
    let table = document.createElement('table');
    table.id = 'numeri';

    let row = document.createElement('tr');
    table.appendChild(row);

    for (let i = 1; i <= 9; i++) {
        let td = document.createElement('td');
        td.textContent = i;
        row.appendChild(td);
    }
    main.appendChild(table);


    // Click event handler for cells with class "tabellone"
    var tabelloneCells = document.querySelectorAll('.tabellone td');
    tabelloneCells.forEach(function (cell) {
        cell.addEventListener('click', function () {
            var currentCell = this;

            // Toggle background color on click
            if (currentCell.style.backgroundColor === 'rgb(202, 202, 202)') {
                // Cell is already highlighted, reset to normal
                currentCell.style.backgroundColor = '';
            } else {
                // Reset background color for all cells and highlight the clicked cell
                tabelloneCells.forEach(function (cell) {
                    cell.style.backgroundColor = '';
                });
                currentCell.style.backgroundColor = '#cacaca';
            }
        });
    });

    // Click event handler for cells in the "numeri" table
    var numeriCells = document.querySelectorAll('#numeri td');
    numeriCells.forEach(function (numeriCell) {
        numeriCell.addEventListener('click', function () {
            var selectedNumber = this.textContent;
            var highlightedCell = document.querySelector('.tabellone td[style="background-color: rgb(202, 202, 202);"]');

            if (!highlightedCell) {
                // No cell is highlighted
                document.querySelector('.log').textContent = 'Cella non selezionata';
            } else {
                // Insert the selected number into the highlighted cell
                highlightedCell.textContent = selectedNumber;
                highlightedCell.style.backgroundColor = ''; // Reset background color
                document.querySelector('.log').textContent = 'Numero inserito correttamente';
            }
        });
    });
});
