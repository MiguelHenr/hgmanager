let forms = document.getElementById('formulario');
const inputs = document.querySelectorAll('input');
const cpfInp = document.getElementById('usuario');
const errorP = document.getElementById('error');
const submitBtn = document.getElementById('entrar');

async function validarLogin(event) {

    console.warn("click!");
    event.preventDefault();
    submitBtn.disabled = true;

    // Captura os dados do formulário
    const formData = new FormData(forms);

    // Converte para JSON
    const data = Object.fromEntries(formData.entries());

    // Faz a requisição ao backend
    const response = await fetch('login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        sinalize(response.text());
        submitBtn.disabled = false;
        return;
    }

    console.log(response);

    const result = await response.json();

    alert('Logado com sucesso! seja bem vindo: ' + result.nome);
    console.log(result);

    submitBtn.disabled = true;

}

async function redirect() {

    const response = await fetch('login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

}

function sinalize(err) {
    if (errorP.classList.contains('hide')) {
        errorP.classList.remove('hide');
        submitBtn.classList.add('error');
    }
}

function normalize() {
    if (!errorP.classList.contains('hide')) {
        errorP.classList.add('hide');
        submitBtn.classList.remove('error');
    }
}

function fixCpf() {
    let cpf = cpfInp.value.replace(/\D/g, '').substring(0, 11);

    if (cpf.length > 2) 
        cpf = cpf.substring(0, 3) + '.' + cpf.substring(3);
    if (cpf.length > 6) 
        cpf = cpf.substring(0, 7) + '.' + cpf.substring(7);
    if (cpf.length > 10) 
        cpf = cpf.substring(0, 11) + '-' + cpf.substring(11);

    cpfInp.value = cpf;
}

function reactCpf(e) {
    if (e.key === 'Backspace') {
        let index = cpfInp.selectionStart;
        let cpf = cpfInp.value;

        if (index > 0 && (cpf[index - 1] === '.' || cpf[index - 1] === '-')) {
            cpf = cpf.substring(0, index - 2) + cpf.substring(index);
            cpfInp.value = cpf;
            cpfInp.setSelectionRange(index - 2, index - 2);
            e.preventDefault();
        }
    }
}

forms.addEventListener('submit', (event) => validarLogin(event));

for (let inp of inputs)
    inp.addEventListener('focus', normalize);

cpfInp.addEventListener('input', fixCpf);

cpfInp.addEventListener('keydown', event => reactCpf(event));