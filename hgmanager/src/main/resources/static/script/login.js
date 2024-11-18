let forms = document.getElementById('formulario');

async function validarLogin(event) {

    event.preventDefault();

    // Captura os dados do formulário
    const formData = new FormData(forms);

    // Converte para JSON
    const data = Object.fromEntries(formData.entries());

    // Faz a requisição ao backend
    const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        const error = await response.text();
        console.log(error);
        alert('Erro: ' + error);
        return;
    }

    console.log(response);

    const result = await response.json();

    alert('Logado com sucesso! seja bem vindo: ' + result.nome);

    console.log(result);

}

forms.addEventListener('submit', (event) => validarLogin(event));