const datiTabella = [{
    "Autore": "Gino Pino",
    "Email": "ginopino@blogtw.com",
    "Argomenti": "HTML, CSS, JS"
},
{
    "Autore": "Cippa Lippa",
    "Email": "cippalippa@blogtw.com",
    "Argomenti": "PHP"
}]

function stringaToID(stringa) {
    return stringa.toLowerCase().replace(/[^a-zA-Z]/g, "");
}

const ths = Object.keys(datiTabella[0]);
const tabella = document.getElementById("tabella");

let th_row = `<tr>`;
for (let i = 0; i < ths.length; i++) {
    th_row += `<th id='${stringaToID(ths[i])}'>${ths[i]}</th>`;
}
th_row += `</tr>`;

tabella.innerHTML += th_row;

for (let row = 0; row < datiTabella.length; row++) {
    let td_row = `<tr>`;
    let row_id = stringaToID(datiTabella[row][ths[0]]);

    td_row += `<th id="${row_id}">${datiTabella[row][ths[0]]}</th>`;
}

for (let i = 1; i < ths.length; i++) {
    td_row += `<td headers="${row_id} ${stringaToID(ths[i])}"> ${datiTabella[row][ths[i]]}</td >`;
}

td_row += `</tr>`;

tabella.innerHTML += td_row;

console.log(td_row);
