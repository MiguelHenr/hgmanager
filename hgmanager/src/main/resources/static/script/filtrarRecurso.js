
function filtrarRecursos() {
    const estadoSelecionado = document.getElementById('filtroEstado').value;

    if (estadoSelecionado === 'TODOS') {
        renderizarRecursos(todosRecursos); // Exibe todos os recursos
    } else {
        const recursosFiltrados = todosRecursos.filter(recurso => recurso.estado === estadoSelecionado);
        renderizarRecursos(recursosFiltrados); // Exibe apenas os recursos filtrados
    }
}

