document.addEventListener("DOMContentLoaded", function() {
    // dà le CORS policy e non so come farlo quindi vaffanculo
    const button = document.querySelector("button");
    button.addEventListener("click", function () {
        fetch('data.json')
            .then(response => response.json())
            .then(data => {
                const main = document.querySelector('main');
                data.forEach(item => {
                    const div = document.createElement('div');
                    const ul = document.createElement('ul');
                    const li1 = document.createElement('li');
                    const li2 = document.createElement('li');
                    const li3 = document.createElement('li');
                    const upButton = document.createElement('button');
                    const downButton = document.createElement('button');
    
                    li1.textContent = `id: ${item.id}`;
                    li2.textContent = `nome: ${item.nome}`;
                    li3.textContent = `type: ${item.type}`;
                    upButton.textContent = 'Up';
                    downButton.textContent = 'Down';
    
                    ul.appendChild(li1);
                    ul.appendChild(li2);
                    ul.appendChild(li3);
                    div.appendChild(ul);
                    div.appendChild(upButton);
                    div.appendChild(downButton);
                    main.appendChild(div);
                });
            });
    });
});
