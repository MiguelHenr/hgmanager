function deletarRecurso(id) {


    const confirmarBtn = document.getElementById('confirmar');
    const cancelarBtn = document.getElementById('cancelar');
    const confirmacaoDiv = document.getElementById('confirma');

    if (confirmacaoDiv) {
        confirmacaoDiv.style.display = 'flex';
    } else {
        console.error('Elemento com ID "confirmacaoDiv" não encontrado.');
    }



    confirmarBtn.onclick = () => {
        confirmacaoDiv.style.display = 'none';

        const url = `http://localhost:8080/Recurso/deletar_recurso/${id}`;

        fetch(url, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro ao deletar recurso com ID ${id}: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                console.log(data);


                const recursoDiv = document.getElementById(`recurso-${id}`);
                if (recursoDiv) {
                    recursoDiv.remove();
                } else {
                    console.log(`Elemento com ID "recurso-${id}" não encontrado.`);
                }
            })
            .catch(error => {
                console.error(error.message);
            });
    };


    cancelarBtn.onclick = () => {
        confirmacaoDiv.style.display = 'none';
        console.log("Ação de exclusão cancelada pelo usuário.");
    };
}
