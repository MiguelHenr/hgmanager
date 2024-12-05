function atualizarRecurso(id, estado) {
    const url = `http://localhost:8080/Recurso/AtualizarRecurso/${id}/${estado}`;

    fetch(url, {
        method: 'PUT',
    })
        .then(response => {
            if (!response.ok) {
                // Lê o corpo da resposta para obter a mensagem de erro do servidor
                return response.text().then(errorMessage => {
                    throw new Error(errorMessage || `Erro ao atualizar recurso com ID ${id}: ${response.status}`);
                });
            }
            return response.text(); // Espera uma mensagem de sucesso (ou uma resposta vazia)
        })
        .then(() => {
            alert(`Recurso com ID ${id} atualizado com sucesso para estado: ${estado}.`);

            // Atualiza a interface para refletir a mudança
            const recursoDiv = document.getElementById(`recurso-${id}`);
            const estadoAtualizado = recursoDiv.querySelector('h3:nth-of-type(1)');
            if (estadoAtualizado) {
                estadoAtualizado.textContent = `estado: ${estado}`;
            }
        })
        .catch(error => {
            console.error('Erro ao atualizar recurso:', error);
            alert(`Erro ao atualizar recurso: ${error.message}`);
        });
}
