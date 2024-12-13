function atualizarRecurso(evt,id, estado) {
    const url = `http://localhost:8080/Recurso/AtualizarRecurso/${id}/${estado}`;
    const div = evt.target.parentNode;
    div.classList.add('clicked');

    const btns = div.querySelectorAll('button');

    for (let btn of btns)
        btn.disabled = true;

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
           

            // Atualiza a interface para refletir a mudança
            const recursoDiv = document.getElementById(`recurso-${id}`);
            const estadoAtualizado = recursoDiv.querySelector('h3:nth-of-type(1)');
            if (estadoAtualizado) {
                estadoAtualizado.textContent = `${estado}`;
            }

            for (let btn of btns)
                btn.disabled = false;
            div.classList.remove('clicked');
        })
        .catch(error => {
            console.error('Erro ao atualizar recurso:', error);
           
        });
}
