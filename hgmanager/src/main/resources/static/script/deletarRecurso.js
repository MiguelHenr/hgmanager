function deletarRecurso(id) {
    // Obtém os elementos de confirmação
    const confirmacaoDiv = document.getElementById('confirmacaoDiv');
    const confirmarBtn = document.getElementById('confirmar');
    const cancelarBtn = document.getElementById('cancelar');

    if (confirmacaoDiv) {
        confirmacaoDiv.style.display = 'flex';
    } else {
        console.error('Elemento com ID "confirmacaoDiv" não encontrado.');
    }


    // Adiciona o comportamento ao botão "Sim"
    confirmarBtn.onclick = () => {
        confirmacaoDiv.style.display = 'none'; // Esconde a confirmação

        const url = `http://localhost:8080/Recurso/deletarRecurso/${id}`;

        fetch(url, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro ao deletar recurso com ID ${id}: ${response.status}`);
                }
                return response.text(); // Espera o texto de resposta ("deletado com sucesso")
            })
            .then(data => {
                console.log(data); // Exibe "deletado com sucesso" no console

                // Remover o elemento da tela, se necessário:
                const recursoDiv = document.getElementById(`recurso-${id}`);
                if (recursoDiv) {
                    recursoDiv.remove(); // Remove o div do recurso da tela
                } else {
                    console.log(`Elemento com ID "recurso-${id}" não encontrado.`);
                }
            })
            .catch(error => {
                console.error(error.message); // Log no console para depuração
            });
    };

    // Adiciona o comportamento ao botão "Cancelar"
    cancelarBtn.onclick = () => {
        confirmacaoDiv.style.display = 'none'; // Apenas esconde a confirmação
        console.log("Ação de exclusão cancelada pelo usuário.");
    };
}
