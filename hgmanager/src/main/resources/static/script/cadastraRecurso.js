
    document.getElementById('formulario').addEventListener('submit', async function(event) {
        event.preventDefault(); // Impede o comportamento padrão do formulário

        // Captura os dados do formulário
        const formData = new FormData(this);

        // Converte para JSON
        const data = Object.fromEntries(formData.entries());

        try {
            // Faz a requisição ao backend
            const response = await fetch('http://localhost:8080/Recurso/cadastroRecurso', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const result = await response.json();
                alert('Recurso cadastrado com sucesso! ID: ' + result.id);
            } else {
                const error = await response.text();
                alert('Erro ao cadastrar recurso: ' + error);
            }
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao conectar ao servidor.');
        }
    });

