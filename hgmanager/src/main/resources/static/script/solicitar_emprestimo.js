let idRecurso;

function getHorariosReservaRecurso(){
    $.ajax({
        url: '/recuperar_horarios_recurso',
        type: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        data: ('idRecurso=' + idRecurso),
        success: function(data){
            console.log(data);
        }
    }
    )
}

function getIdRecurso() {
    let idRecurso;
}

// Fechar popup de confirmação de empréstimo
function fecharPopup() {
    document.querySelector(".lista-itens").style.display = 'flex';
    document.querySelector(".container-busca").style.display = 'flex';
    const popup = document.querySelector(".popup");
    if (popup) {
        popup.remove();
    }
}

// Abrir popup de confirmação de empréstimo
function abrirPopup() {
    document.querySelector(".lista-itens").style.display = 'none';
    document.querySelector(".container-busca").style.display = 'none';
    const popup = document.querySelector(".popup");
    if (popup) {
        popup.style.display = 'flex';
    }
}

// Confirmar empréstimo
function confirmarEmprestimo(){
    const $datePickerEl = document.getElementById("datepicker-inline");
    let datePicker = $datePickerEl.datepicker

    let dateFormat = datePicker.getDate();

    let dia = `${dateFormat.getDate().toString()}/${(datePicker.getDate().getMonth() + 1).toString()}/${dateFormat.getFullYear().toString()}`;
    let horario = document.querySelector('input[name="horario"]:checked').value;

    dateFormat.setHours(parseInt(horario.toString().split(":")[0]));
    dateFormat.setMinutes(parseInt(horario.toString().split(":").at(1)) + 50);
    horario += `-${dateFormat.getHours()}:${dateFormat.getMinutes()}`

    $.ajax({
        url: '/confirmar_emprestimo',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            dia: dia,
            horario: horario,
            idRecurso: idRecurso,
            idProfessor: 3
        }),
        success: (data) => {
            window.location.reload();
        }
    });
}

function filtrar(){
    
}

function gerarHorariosEmprestimo() {
    // Variavel com todos os horário
    let horarios = ['07:00', '07:50', '08:40', '09:30', '10:20', '10:40', '11:30', '13:00', '13:50', '14:40', '15:30', '16:40'
        , '17:30', '19:00', '19:50', '20:50', '21:40', '22:30'];

    // Criando os horarios
    let divTime = ``;
    horarios.forEach(horario => {
        let horas = horario.split(":").at(0);
        let minutos = horario.split(":").at(1);
        divTime += `<div class="h-[3em] w-[3em]">
            <input type="radio" id="time-${horarios.indexOf(horario)}" value="${horario}" name="horario" class="hidden peer">
            <label class="label-horario flex flex-col items-center justify-center bg-white p-2 w-full h-full border border-gray-300 
                    rounded-lg text-sm font-bold hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-600 dark:hover:bg-gray-700 
                    peer-checked:bg-blue-600 peer-checked:text-white"
                    for="time-${horarios.indexOf(horario)}">
                    <p>${horas}:</p>
                    <p>${minutos}</p>
            </label>
            </div>`;
    })

    return divTime;
}

function criarCalendarioHora() {
    // Criando calendario
    let divDatePopup = document.createElement('div');
    divDatePopup.classList.add('popup-date');

    let element = `
        <div id="datepicker-inline" class="mb-4"></div>
        <div class="w-[20em] h-[20em] grid grid-cols-4 gap-5 auto-rows-auto bg-gray-100 dark:bg-gray-900 p-4 w-[296px] h-[388px] rounded-md">
            ${gerarHorariosEmprestimo()}
        </div>`;

    divDatePopup.insertAdjacentHTML("afterbegin", element);

    // Iniciando o calendário
    setTimeout(() => {
        let dataAtual = new Date();
        dataAtual.setDate(dataAtual.getDate() + 1);

        let dataMax = new Date();
        dataMax.setDate(dataAtual.getDate() + 7);
        const datepickerInline = document.querySelector('#datepicker-inline');
        if (datepickerInline) {
            new window.Datepicker(datepickerInline, {
                format: 'dd/mm/yyyy',
                language: 'pt-BR',
                title: 'Selecione a data do empréstimo',
                minDate: dataAtual,
                maxDate: dataMax
            });
        }
    }, 0);

    return divDatePopup;
}

// Construir pop-up a partir do elemento clicado
document.querySelectorAll(".clique-botao").forEach(button => {
    button.addEventListener('click', () => {

        // Div container geral
        let divContainer = document.createElement('div');
        divContainer.classList.add('popup');

        // Div container para os nomes
        let divContentPopup = document.createElement('div');
        divContentPopup.classList.add('popup-content');

        // Criando div para os botões de confirmar ou cancelar
        let divBotaoesEscolha = document.createElement('div');
        divBotaoesEscolha.classList.add('popup-buttons');

        // Criando botão para confirmar
        let botaoConfirmar = document.createElement('button');
        botaoConfirmar.textContent = 'Confirmar empréstimo';
        botaoConfirmar.classList.add('button-confirmar');
        botaoConfirmar.onclick = confirmarEmprestimo;

        // Criando botão para cancelar
        let botaoCancelar = document.createElement('button');
        botaoCancelar.textContent = 'Cancelar empréstimo';
        botaoCancelar.classList.add('button-cancelar');
        botaoCancelar.onclick = fecharPopup;

        // Adicionando botões na div de botoões
        divBotaoesEscolha.appendChild(botaoConfirmar);
        divBotaoesEscolha.appendChild(botaoCancelar);

        // Adicionando div de botões na div principal
        divContainer.appendChild(divBotaoesEscolha);

        // Encontrando recurso escolhido e adicionando eles a div de container
        let divItemMaisProximo = button.closest('.item')
        let listaParagrafos = divItemMaisProximo.querySelectorAll('p');
        const titulos = ['Departamento', 'Estado', 'Marca', 'Descrição'];
        let i = 0;
        listaParagrafos.forEach(paragrafo => {
            //Corrigir esse pq vai ficar feio
            if(paragrafo.style.display !== 'none'){
                let novoParagrafo = document.createElement('h1');
                novoParagrafo.textContent = `${titulos[i]}: ${paragrafo.textContent}`;
                divContentPopup.insertAdjacentElement("afterbegin", novoParagrafo);
                i++
            } else{
                idRecurso = paragrafo.textContent;
                return;
            }

        })

        // Adicionando título ao container principal
        let nomeItem = document.createElement('h2');
        nomeItem.textContent = "Escolha a data e horário do empréstimo"
        divContainer.insertAdjacentElement("afterbegin", nomeItem);

        // Criando calendario
        let divDatePopup = criarCalendarioHora();

        // Div container para o calendario e para descrição do recurso selecionado
        let divCalendarioDescricaoContainer = document.createElement('div');
        divCalendarioDescricaoContainer.classList.add('content-container');

        // Adicionando divs ao DOM
        nomeItem.insertAdjacentElement("afterend", divContentPopup);
        divCalendarioDescricaoContainer.insertAdjacentElement('afterbegin', divDatePopup);

        nomeItem.insertAdjacentElement("afterend", divCalendarioDescricaoContainer);

        // Adicionando div container principal ao body
        document.querySelector('body').appendChild(divContainer);

        // Abrindo o popup de confirmação de empréstimo
        abrirPopup();
    })
})

