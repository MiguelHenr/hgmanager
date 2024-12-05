document.addEventListener('DOMContentLoaded', function() {
    listarRecursos();
});

let todosRecursos = []; // Variável global para armazenar todos os recursos

function listarRecursos() {
    const url = "http://localhost:8080/Recurso/resgatarRecurso";
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

function renderizarRecursos(recursos) {
    const container = document.getElementById('recursosDiv');
    container.innerHTML = ''; // Limpa os recursos existentes

    container.classList.add('row', 'gx-3', 'gy-3');
    recursos.forEach(recurso => {
        const div = document.createElement('div');
        div.classList.add('col-md-3', 'recurso');
        div.id = `recurso-${recurso.id}`;

        div.innerHTML = `
            <div class="card p-3">
                <h1 class="desc">${recurso.descricao}</h1>
                <h3>estado: ${recurso.estado}</h3>
                <h3>codigo: ${recurso.codigo}</h3>
                <select name="estado" id="estado-${recurso.id}" class="form-select">
                        <option value="NOVO" ${recurso.estado === 'NOVO' ? 'selected' : ''}>Novo</option>
                        <option value="CONSERVADO" ${recurso.estado === 'CONSERVADO' ? 'selected' : ''}>Conservado</option>
                        <option value="VELHO" ${recurso.estado === 'VELHO' ? 'selected' : ''}>Velho</option>
                        <option value="ESTRAGADO" ${recurso.estado === 'ESTRAGADO' ? 'selected' : ''}>Estragado</option>
                    </select>
                <div class="d-flex justify-content-center">
                    <button type="button" class="btn btn-danger p-2 flex-fill bd-highlight" onclick="deletarRecurso(${recurso.id})">Apagar</button>
                    <button type="button" class="p-2 flex-fill bd-highlight" onclick="atualizarRecurso(${recurso.id}, document.getElementById('estado-${recurso.id}').value)">Atualizar estado</button>
                </div>

            </div>
        `;
        container.appendChild(div);
    });
}