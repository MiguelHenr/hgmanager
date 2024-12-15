document.addEventListener('DOMContentLoaded', function() {
    listarRecursos();
});

let todosRecursos = []; // Variável global para armazenar todos os recursos

function listarRecursos() {
    const url = `http://localhost:8080/Recurso/resgatar_recurso`;
    fetch(url)
        .then(response => {
            if (!response.ok) {
                console.log("Erro ao buscar recursos");
                throw new Error(`Erro: ${response.status}`);
            }
            return response.json();
        })
        .then(recursos => {
            todosRecursos = recursos; // Armazena os recursos na variável global
            renderizarRecursos(todosRecursos); // Renderiza todos os recursos inicialmente
        })
        .catch(error => {
            console.log("Erro ao buscar recursos:", error);
        });
}
let paginaAtual = 1;
const recursosPorPagina = 12;

function renderizarRecursosComPaginacao(recursos) {
    const container = document.getElementById('recursosDiv');
    container.innerHTML = ''; // Limpa os recursos existentes

    // Calcula os índices de início e fim para os recursos desta página
    const inicio = (paginaAtual - 1) * recursosPorPagina;
    const fim = inicio + recursosPorPagina;

    // Seleciona os recursos para exibir na página atual
    const recursosExibidos = recursos.slice(inicio, fim);

    container.classList.add('row', 'gx-3', 'gy-3');
    const estados = ['NOVO', 'CONSERVADO', 'VELHO', 'ESTRAGADO'];

    recursosExibidos.forEach(recurso => {
        const div = document.createElement('div');
        div.classList.add('col-md-3', 'recurso', 'col-sm-3', 'col-lg-3');
        div.id = `recurso-${recurso.id}`;

        const estadoOptions = estados.map(estado =>
            `<option value="${estado}" ${recurso.estado === estado ? 'selected' : ''}>${estado}</option>`
        ).join('');

        div.innerHTML = `
            <div class="card p-3 container">
                <h1 class="desc">${recurso.descricao}</h1>
                <h2 class="recurso_estado">ESTADO</h2>
                <h3>${recurso.estado}</h3>
                ${recurso.codigo ? `
                    <h2 class="recurso_codigo">CÓDIGO</h2>
                    <h3>${recurso.codigo}</h3>
                ` : ''}
                <select name="estado" id="estado-${recurso.id}" class="form-select">
                    ${estadoOptions}
                </select>
                <div class="d-flex justify-content-center mt-3">
                    <button type="button" class="btn btn-danger flex-fill" onclick="deletarRecurso(${recurso.id})">Apagar</button>
                    <button type="button" class="btn btn-primary flex-fill" onclick="atualizarRecurso(event, ${recurso.id}, document.getElementById('estado-${recurso.id}').value)">Atualizar estado</button>
                </div>
            </div>
        `;
        container.appendChild(div);
    });

    renderizarPaginacao(recursos);
}

function renderizarPaginacao(recursos) {
    const totalPaginas = Math.ceil(recursos.length / recursosPorPagina);
    const paginacaoContainer = document.getElementById('paginacaoDiv');
    paginacaoContainer.innerHTML = ''; // Limpa os controles existentes

    for (let i = 1; i <= totalPaginas; i++) {
        const botaoPagina = document.createElement('button');
        botaoPagina.classList.add('btn', 'btn-secondary', 'me-2');
        botaoPagina.textContent = i;
        botaoPagina.disabled = i === paginaAtual;
        botaoPagina.onclick = () => {
            paginaAtual = i;
            renderizarRecursosComPaginacao(recursos);
        };
        paginacaoContainer.appendChild(botaoPagina);
    }
}

function renderizarRecursos(recursos) {
    const container = document.getElementById('recursosDiv');
    container.innerHTML = ''; // Limpa os recursos existentes

    container.classList.add('row', 'gx-3', 'gy-3');
    recursos.forEach(recurso => {
        const div = document.createElement('div');
        div.classList.add('col-md-3', 'recurso', 'col-sm-3', 'col-lg-3');
        div.id = `recurso-${recurso.id}`;

        div.innerHTML = `
            <div class="card p-3 container">
                <h1 class="desc">${recurso.descricao}</h1>
                <h2 class="recurso_estado">ESTADO</h2>
                <h3>${recurso.estado}</h3>
                ${recurso.codigo != null ? '<h2 class="recurso_codigo">CODIGO</h2>' +
            '<h3>'+ recurso.codigo +'</h3>' : ''}
                <select name="estado" id="estado-${recurso.id}" class="form-select">
                        <option value="NOVO" ${recurso.estado === 'NOVO' ? 'selected' : ''}>Novo</option>
                        <option value="CONSERVADO" ${recurso.estado === 'CONSERVADO' ? 'selected' : ''}>Conservado</option>
                        <option value="VELHO" ${recurso.estado === 'VELHO' ? 'selected' : ''}>Velho</option>
                        <option value="ESTRAGADO" ${recurso.estado === 'ESTRAGADO' ? 'selected' : ''}>Estragado</option>
                    </select>
                <div class="d-flex justify-content-center">
                    <button type="button" class="btn btn-danger p-2 flex-fill bd-highlight" onclick="deletarRecurso(${recurso.id})">Apagar</button>
                    <button type="button" class="p-2 flex-fill bd-highlight" onclick="atualizarRecurso(event,${recurso.id}, document.getElementById('estado-${recurso.id}').value)">Atualizar estado</button>
                </div>

            </div>
        `;
        container.appendChild(div);
    });
}

window.onload = () => document.getElementById('novo').addEventListener('click', () =>{
    window.location.href = '/cadastra_recurso';
})