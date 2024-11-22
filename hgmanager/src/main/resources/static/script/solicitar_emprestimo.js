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

        // Criando botão para cancelar
        let botaoCancelar = document.createElement('button');
        botaoCancelar.textContent = 'Cancelar empréstimo';
        botaoCancelar.classList.add('button-cancelar');
        botaoCancelar.onclick = closePopup;

        // Adicionando botões na div de botoões
        divBotaoesEscolha.appendChild(botaoConfirmar);
        divBotaoesEscolha.appendChild(botaoCancelar);

        // Adicionando div de botões na div principal
        divContainer.appendChild(divBotaoesEscolha);

        // Encontrando recurso escolhido e adicionando eles a div de conteudo
        let divItemMaisProximo = button.closest('.item')
        let listaParagrafos = divItemMaisProximo.querySelectorAll('p');

        listaParagrafos.forEach(paragrafo => {
            let novoParagrafo = document.createElement('h1');
            novoParagrafo.textContent = paragrafo.textContent;
            divContentPopup.insertAdjacentElement("afterbegin", novoParagrafo);
        })

        // Adicionando título ao container principal
        let nomeItem = document.createElement('h2');
        nomeItem.textContent = "Escolha a data e horário do empréstimo"
        divContainer.insertAdjacentElement("afterbegin", nomeItem);

        // Container popup do calendario
        let divDatePopup = document.createElement('div');
        divDatePopup.classList.add('popup-date');
        divDatePopup.insertAdjacentHTML("afterbegin", '<div id="datepicker-inline" inline-datepicker data-date="17/11/2024"></div>\n');

        // Iniciando o calendário com as configurações necessárias
        setTimeout(() => {
            let dataAtual = new Date();
            let dataMax = new Date();
            dataMax = dataMax.setDate(dataAtual.getDate() + 7)
            const datepickerInline = document.querySelector('#datepicker-inline');
            if (datepickerInline) {
                new Datepicker(datepickerInline, {
                    format: 'dd/mm/yyyy',
                    language: 'pt-BR',
                    title: 'Selecione a data do empréstimo',
                    minDate: dataAtual,
                    maxDate: dataMax
                });
            }
        }, 0);


        // Div container para o calendario e para descrição do recurso selecionado
        let divCalendarioDescricaoContainer = document.createElement('div');
        divCalendarioDescricaoContainer.classList.add('content-container');

        divCalendarioDescricaoContainer.insertAdjacentElement("afterbegin", divContentPopup);
        divCalendarioDescricaoContainer.insertAdjacentElement('afterbegin', divDatePopup);

        nomeItem.insertAdjacentElement("afterend", divCalendarioDescricaoContainer);

        // Adicionando div container principal ao body
        document.querySelector('body').appendChild(divContainer);

        // Abrindo o popup de confirmação de empréstimo
        openPopup();
    })
})

// Fechar popup de confirmação de empréstimo
function closePopup() {
    document.querySelector(".item-list").style.display = 'flex';
    document.querySelector(".search-container").style.display = 'flex';
    const popup = document.querySelector(".popup");
    if (popup) {
        popup.remove();
    }
}

// Confirmar empréstimo
function confirmarEmprestimo(){

}

// Abrir popup de confirmação de empréstimo
function openPopup() {
    document.querySelector(".item-list").style.display = 'none';
    document.querySelector(".search-container").style.display = 'none';
    const popup = document.querySelector(".popup");
    if (popup) {
        popup.style.display = 'flex';
    }
}

function filtrar(){
    
}