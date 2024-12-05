const ghost = document.getElementById('ghost');
const main = document.getElementById('reqs');

function loadAll() {
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
}

function loadMine() {
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
}

function update(event, action) {
    const requestElement = event.target.closest("[data-index]");
    const id = requestElement.getAttribute("data-index");
    const dataToSend = JSON.stringify({ id: id, action: action });

    disable(requestElement);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/update-sol", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            updateReqs(requestElement,action);
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

function updateReqs(el,action) {
    const btnEl = el.querySelector('.btn');
    const status = document.createElement('p');

    status.innerHTML = action == 'approve' ? 'APROVADA' : action == 'reject' ? 'REJEITADA' : 'CANCELADA';

    btnEl.remove();
    el.appendChild(status);
}

function disable(el) {
    const btnEl = el.querySelector('.btn');
    const btns = btnEl.querySelectorAll('button');

    if (!btnEl.classList.contains('clicked'))
        btnEl.classList.add('clicked');

    for (let btn of btns)
        btn.disabled = true;
}

function enable(el) {
    const btnEl = el.querySelector('.btn');
    const btns = btnEl.querySelectorAll('button');

    if (btnEl.classList.contains('clicked'))
        btnEl.classList.remove('clicked');

    for (let btn of btns)
        btn.disabled = false;
}

if (main.classList.contains('mine'))
    window.onload = loadMine;
else
    window.onload = loadAll;