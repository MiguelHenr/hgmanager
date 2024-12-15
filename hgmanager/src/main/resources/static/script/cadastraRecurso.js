document.addEventListener('DOMContentLoaded', function () {
    let divResposta = document.querySelector('.resposta');
    divResposta.style.display = 'none';

    document.getElementById('formulario').addEventListener('submit', async function (event) {
        event.preventDefault();
        let botaoSubmit = document.querySelector('#enviar');
        botaoSubmit.disabled = true;
        botaoSubmit.innerHTML = `<div class='spinner'></div>`

        const formData = new FormData(this);

        const data = {
            marca: formData.get('marca'),
            descricao: formData.get('desc'),
            estado: formData.get('estado'),
            codigo: formData.get('cod')
        };


        if (!data.marca || !data.descricao || !data.estado || /*!data.departamento.id ||*/ !data.codigo)  {
            console.log('preencha todos os campos');
            return;
        }

        console.log('Dados enviados:', data);

        const response = await
        fetch('/recurso/cadastro_recurso', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(res => {
            if(res.status === 200) {
                divResposta.textContent = 'Recurso cadastrado com sucesso';
                divResposta.classList.add('show');
                divResposta.style.display = 'block';

                botaoSubmit.disabled = true;
                botaoSubmit.innerHTML = ``
                botaoSubmit.textContent = 'Enviar'
            } else{
                divResposta.textContent = 'Erro ao cadastrar recurso';
                divResposta.classList.add('erro');
                divResposta.style.display = 'block';

                botaoSubmit.disabled = true;
                botaoSubmit.innerHTML = ``
                botaoSubmit.textContent = 'Enviar'
            }
        });
    });
});


/*document.addEventListener('DOMContentLoaded', function () {
    listarDepartamentos();
});

function listarDepartamentos() {
    const url = '/Departamento/listar_departamento';

    fetch(url)
        .then(response => {
            if (!response.ok) {
                console.error('Erro ao resgatar departamentos:', response.status);
                throw new Error(`Erro: ${response.status}`);
            }
            return response.json();
        })
        .then(departamentos => {
            const selecao = document.getElementById('departamentoSelect');
            departamentos.forEach(opcao => {
                console.log(opcao);
                const op = document.createElement('option');
                op.value = opcao.id;
                op.textContent = opcao.nome;
                selecao.appendChild(op);
            });
        })
        .catch(error => console.error('Erro ao listar departamentos:', error));
}*/
