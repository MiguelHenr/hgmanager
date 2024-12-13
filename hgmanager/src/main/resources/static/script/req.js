const ghost = document.getElementById('ghost');
const main = document.getElementById('reqs');
const nav = document.getElementById('pages');
const totalPages = parseInt(nav.getAttribute('data-number'));
let currentPage;

function loadAll(page = 1) {
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "requests", true);

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            main.innerHTML = xhr.responseText;

            const rejectBts = document.getElementsByClassName('reject');
            const approveBts = document.getElementsByClassName('approve');

            for (let btn of approveBts)
                btn.addEventListener("click", event => update(event, "approve"));
        
            for (let btn of rejectBts)
                btn.addEventListener("click", event => update(event, "reject"));

            shooGhost();
            fixPagination(page);
        }
    };

    xhr.send(`page=${encodeURIComponent(page)}`);
}

function loadMine(page = 1) {
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/my-requests", true);

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            main.innerHTML = xhr.responseText;

            const cancelBts = document.getElementsByClassName('cancel');
        
            for (let btn of cancelBts)
                btn.addEventListener("click", event => update(event, "cancel"));

            shooGhost();
            fixPagination(page);
        }
    };

    xhr.send(`page=${encodeURIComponent(page)}`);
}

function load(page=1) {
    const mine = main.classList.contains('mine');
    conjureGhost();
    main.innerHTML = '';

    const basePath = window.location.pathname;
    const newUrl = `${basePath}?page=${page}`;
    window.history.pushState({ page }, '', newUrl);

    if (mine)
        loadMine(page);
    else
        loadAll(page);
}

function getEl(tag,classes='') {
    const el = document.createElement(tag);

    el.classList = classes;

    return el;
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

    if (ghost.classList.contains('show'))
        ghost.classList.remove('show');

    ghost.classList.add('shoo');

    await delay(500);

    ghost.style.display = 'none';
}

function conjureGhost() {
    if (ghost.classList.contains('shoo'))
        ghost.classList.remove('shoo');

    ghost.classList.add('show');
    ghost.style.display = 'flex';
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

function fixPagination(page=1) {
    nav.innerHTML = '';

    if (totalPages <= 1)
        return;

    currentPage = page;

    if (currentPage > 1) {
        const first = getEl('span','first clickable');
        const prev = getEl('span','prev clickable');

        first.setAttribute('data-page',1);
        prev.setAttribute('data-page',page-1);

        first.innerHTML = 'Primeira';
        prev.innerHTML = 'Anterior';

        nav.appendChild(first);
        nav.appendChild(prev);
    }

    const current = getEl('span','current');

    current.innerHTML = 'Página ' + currentPage + ' de ' + totalPages;

    nav.appendChild(current);

    if (currentPage < totalPages) {
        const next = getEl('span','next clickable');
        const last = getEl('span','last clickable');

        next.setAttribute('data-page',page+1);
        last.setAttribute('data-page',totalPages);

        next.innerHTML = 'Próxima';
        last.innerHTML = 'Última';

        nav.appendChild(next);
        nav.appendChild(last);
    }

    addListeners();
}

function addListeners() {
    const spans = nav.querySelectorAll('span:not(.current)');

    for (const spn of spans)
        spn.addEventListener('click', e => {
            const page = parseInt(e.target.getAttribute('data-page'));
            load(page);
        })
}

function getPageFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return parseInt(params.get('page')) || 1;
}

window.onload = () => {
    const page = getPageFromUrl();
    load(page);
};

window.addEventListener('popstate', event => {
    const state = event.state;
    const page = state && state.page ? state.page : getPageFromUrl();
    load(page);
});