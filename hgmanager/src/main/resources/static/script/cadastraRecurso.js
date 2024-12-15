
document.addEventListener('DOMContentLoaded', function () {


    document.getElementById('formulario').addEventListener('submit', async function (event) {
        event.preventDefault();



        const formData = new FormData(this);

        const data = {
            marca: formData.get('marca'),
            descricao: formData.get('desc'),
            estado: formData.get('estado'),
            codigo: formData.get('cod'),
            departamento: {
                id: formData.get('departamentoSelect')
            }

        };


        if (!data.marca || !data.descricao || !data.estado || !data.departamento.id || !data.codigo)  {
            console.log('preencha todos os campos');
            return;
        }

        console.log('Dados enviados:', data);

        try {
            const response = await fetch('/recurso/cadastro_recurso', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result);
            } else {
                const error = await response.text();
                console.log(error);
            }
        } catch (error) {
            console.error('Erro:', error);
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    listarDepartamentos();
});

function listarDepartamentos() {
    const url = "/Departamento/listar_departamento";

    fetch(url)
        .then(response => {
            if (!response.ok) {
                console.error("Erro ao resgatar departamentos:", response.status);
                throw new Error(`Erro: ${response.status}`);
            }
            return response.json(); // Adicionado o retorno do JSON
        })
        .then(departamentos => {
            const selecao = document.getElementById("departamentoSelect");
            departamentos.forEach(opcao => {
                console.log(opcao);
                const op = document.createElement("option");
                op.value = opcao.id;
                op.textContent = opcao.nome;
                selecao.appendChild(op);
            });
        })
        .catch(error => console.error("Erro ao listar departamentos:", error));
}
