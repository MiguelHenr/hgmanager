
function filtrarRecursos() {
    const estadoSelecionado = document.getElementById('filtroEstado').value;

    if (estadoSelecionado === 'TODOS') {
        renderizarRecursos(todosRecursos);
    } else {
        const recursosFiltrados = todosRecursos.filter(recurso => recurso.estado === estadoSelecionado);
        renderizarRecursos(recursosFiltrados);
    }
}

