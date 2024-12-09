//const ghost = document.getElementById('ghost');
const main = document.getElementById('cmps');

/*function loadAll() {
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "requests", true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            main.innerHTML = xhr.responseText;
            ghost.style.height = main.offsetHeight + 'px';

            const rejectBts = document.getElementsByClassName('reject');
            const approveBts = document.getElementsByClassName('approve');

            for (let btn of approveBts)
                btn.addEventListener("click", event => update(event, "approve"));
        
            for (let btn of rejectBts)
                btn.addEventListener("click", event => update(event, "reject"));

            shooGhost();
        }
    };

    xhr.send();
}*/

/*function loadMine() {
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/my-requests", true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            main.innerHTML = xhr.responseText;

            const cancelBts = document.getElementsByClassName('cancel');
        
            for (let btn of cancelBts)
                btn.addEventListener("click", event => update(event, "cancel"));

            shooGhost();
        }
    };

    xhr.send();
}*/

function update(event) {
    const requestElement = event.target.closest("[data-ri]");
    const id = requestElement.getAttribute("data-ri");
    const comentario = requestElement.querySelector('.com').value;
    const dataToSend = JSON.stringify({ id: id, ans: comentario });

    disable(requestElement);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/responder", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const response = JSON.parse(xhr.responseText);

            updateCmps(requestElement,response);
        } else {
            enable(requestElement);
            console.error("Server error:", xhr.response);
        }
    };

    xhr.onerror = function () {
        enable(requestElement);
        console.error("Request failed. Check your network connection.");
    };

    xhr.send(dataToSend);
}

function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function shooGhost() {
    await delay(1000);

    ghost.classList.add('shoo');

    await delay(500);

    ghost.remove();
}

function getEl(tag,classes='') {
    const el = document.createElement(tag);

    el.classList = classes;

    return el;
}

function updateCmps(el,comment) {
    let box = el.parentNode;
    const ans = getEl('div', 'ans');
    const figure = getEl('figure','perfil');
    const img = getEl('img');
    const nome = getEl('p');
    const com = getEl('p');
    const data = getEl('p');

    img.src = comment['foto']['src'];
    img.alt = comment['foto']['alt'];
    nome.innerHTML = comment['nome'];
    com.innerHTML = comment['com'];
    data.innerHTML = comment['data'];

    figure.appendChild(img);
    ans.appendChild(figure);
    ans.appendChild(nome);
    ans.appendChild(com);
    ans.append(data);

    box.appendChild(ans);
    enable(el);
}

function disable(el) {
    const btn = el.querySelector('button');

    btn.disabled = true;

    if (!btn.classList.contains('clicked'))
        btn.classList.add('clicked');
}

function enable(el) {
    const btn = el.querySelector('button');

    btn.disabled = false;

    if (btn.classList.contains('clicked'))
        btn.classList.remove('clicked');

    el.querySelector('.com').value = '';
}

/*if (main.classList.contains('mine'))
    window.onload = loadMine;
else
    window.onload = loadAll;*/

const btns = document.querySelectorAll('button');

for (let btn of btns)
    btn.addEventListener("click", event => update(event));