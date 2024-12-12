let datePickerInstance;

// Recuperar horários de reserva do empréstimo selecionado para confimração
async function getHorariosReserva(id) {
    return await $.ajax({
        url: "/recuperar_horarios_recurso",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        data: ("idRecurso=" + id)
    });
}

// Fechar popup de confirmação de empréstimo
function fecharPopup() {
    document.querySelector(".lista-itens").style.display = "flex";
    document.querySelector(".container-busca").style.display = "flex";
    const popup = document.querySelector(".popup");
    if (popup) {
        popup.remove();
    }
}

// Abrir popup de confirmação de empréstimo
function abrirPopup() {
    document.querySelector(".lista-itens").style.display = "none";
    document.querySelector(".container-busca").style.display = "none";
    const popup = document.querySelector(".popup");
    if (popup) {
        popup.style.display = "flex";
    }
}

// Extrai data e hora início em um array com data e hora de início e de fim
function extrairDataEHora(arrayReservas) {
    let dataResultato = [];

    for (let i = 0; i < arrayReservas.length; i++) {
        let primeiraDataHora = arrayReservas[i];
        let [data, horas] = primeiraDataHora.split(" ");
        let [ano, mes, dia] = data.split("-");
        let hora = horas.split(":").slice(0, 2).join(":");

        dataResultato.push(`${dia}-${mes}-${ano} ${hora}`);
    }
    return dataResultato;
}

// Compara datas do array com data passada
function comparaDatas(dataHoraArray, data){
    let horarios = [];

    dataHoraArray.forEach(array => {
        let [dataReserva, horaReserva] = array.split(" ");

        if(dataReserva === data) {
            horarios.push(horaReserva);
        }
    })
    return horarios;
}

function makeInputDisabled(input){
    input.forEach(input => {
        input.disabled = true;
        input.checked = false;
    });
}

function checkInput(inputs, data, dataSelecionada){
    const [dataAtual, horaAtual] = getDataHoraAtual();
    inputs.forEach(input => {
        if (data.includes(input.value) || (dataSelecionada === dataAtual && input.value <= horaAtual)) {
            input.nextElementSibling.classList.remove("dark:bg-gray-800");
            input.nextElementSibling.classList.remove("dark:border-gray-500");
            input.nextElementSibling.classList.remove("dark:hover:bg-gray-700");
            input.nextElementSibling.classList.add("dark:bg-red-800");
            input.nextElementSibling.classList.add("dark:border-red-500");
            input.nextElementSibling.classList.add("dark:hover:bg-red-700");
            input.disabled = true;
        } else{
            input.nextElementSibling.classList.remove("dark:bg-red-800");
            input.nextElementSibling.classList.remove("dark:border-red-500");
            input.nextElementSibling.classList.remove("dark:hover:bg-red-700");
            input.nextElementSibling.classList.add("dark:bg-gray-800");
            input.nextElementSibling.classList.add("dark:border-gray-500");
            input.nextElementSibling.classList.add("dark:hover:bg-gray-700");
            input.disabled = false;
        }
    });
}

function getDataHoraAtual(){
    const date = new Date();
    const dataAtual = `${date.getDate().toString()}-${(date.getMonth() + 1).toString()}-${date.getFullYear().toString()}`;
    const horaAtual = `${date.getHours().toString()}:${date.getMinutes().toString()}`;

    return [dataAtual, horaAtual];
}

// Define quais horários podem ou não ser clicáveis dependendo do dia escolhido
function setHorariosReserva(){
    const containerToObserve = document.body;

    const callbackFuncao = async () => {
        const datepickerEl = document.querySelector("#datepicker-inline");
        let resposta = await getHorariosReserva(localStorage.getItem("idRecursoSelecionado"));

        if (datepickerEl !== null) {
            document.querySelectorAll(".datepicker-cell").forEach(diaDatepicker => {
                diaDatepicker.addEventListener("click", () => {
                    setTimeout(() => {
                        const inputsHorarios = document.querySelectorAll("input[name='horario']");

                        makeInputDisabled(inputsHorarios);

                        let ano = datePickerInstance.getDate().getFullYear().toString();
                        let mes = (datePickerInstance.getDate().getMonth() + 1).toString();
                        let dia = datePickerInstance.getDate().getDate().toString();

                        const selectedDate = `${dia}-${mes}-${ano}`;

                        let horasIguais = comparaDatas(extrairDataEHora(resposta), selectedDate);

                        checkInput(inputsHorarios, horasIguais, selectedDate);

                    }, 0);
                });
            });
        }
    };

    const containerObserver = new MutationObserver(callbackFuncao);
    const observaConfig = { childList: true, subtree: true };
    containerObserver.observe(containerToObserve, observaConfig);
}

// Confirmar empréstimo
function confirmarEmprestimo(){
    let dateFormat = datePickerInstance.getDate();
    let dia = `${dateFormat.getDate().toString()}/${(dateFormat.getMonth() + 1).toString()}/${dateFormat.getFullYear().toString()}`;
    let horario = document.querySelector("input[name='horario']:checked").value;

    dateFormat.setHours(parseInt(horario.toString().split(":")[0]));
    dateFormat.setMinutes(parseInt(horario.toString().split(":").at(1)) + 50);
    horario += `-${dateFormat.getHours()}:${dateFormat.getMinutes()}`

    $.ajax({
        url: "/confirmar_emprestimo",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            dia: dia,
            horario: horario,
            idRecurso: localStorage.getItem("idRecursoSelecionado")
        }),
        success: () => {
            window.location.reload();
        }
    });
}

function gerarHorariosEmprestimo() {
    // Variavel com todos os horários
    let horarios = ["07:00", "07:50", "08:40", "09:30", "10:20", "10:40", "11:30", "13:00", "13:50", "14:40", "15:30", "16:40"
        , "17:30", "19:00", "19:50", "20:50", "21:40", "22:30"];

    // Criando os horários
    let divTime = ``;
    horarios.forEach(horario => {
        let horas = horario.split(":").at(0);
        let minutos = horario.split(":").at(1);
        divTime += `<div class="h-[3em] w-[3em]">
            <input type="radio" id="time-${horarios.indexOf(horario)}" value="${horario}" name="horario" class="hidden peer" disabled>
            <label class="label-horario flex flex-col items-center justify-center p-2 w-full h-full border border-gray-300 
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
    let divDatePopup = document.createElement("div");
    divDatePopup.classList.add("popup-date");

    let element = `
        <div id="datepicker-inline" class="mb-4"></div>
        <div class="w-[20em] h-[20em] grid grid-cols-4 gap-5 auto-rows-auto bg-gray-100 dark:bg-gray-900 p-4 w-[296px] h-[388px] rounded-md">
            ${gerarHorariosEmprestimo()}
        </div>
        <p class="mensagem-erro text-red-500 text-sm mt-2"></p>`;

    divDatePopup.insertAdjacentHTML("afterbegin", element);

    // Iniciando o calendário
    setTimeout(() => {
        let dataAtual = new Date();
        dataAtual.setDate(dataAtual.getDate());

        let dataMax = new Date();
        dataMax.setDate(dataAtual.getDate() + 7);
        const datepickerInline = document.querySelector("#datepicker-inline");
        if (datepickerInline) {
            datePickerInstance = new window.Datepicker(datepickerInline, {
                format: "dd/mm/yyyy",
                language: "pt-BR",
                title: "Selecione a data do empréstimo",
                minDate: dataAtual,
                maxDate: dataMax,
            });
        }
    }, 0);

    return divDatePopup;
}

// Construir pop-up a partir do elemento clicado
document.querySelectorAll(".clique-botao").forEach(button => {
    button.addEventListener("click", () => {
        // Div container geral
        let divContainer = document.createElement("div");
        divContainer.classList.add("popup");

        // Div container para os nomes
        let divContentPopup = document.createElement("div");
        divContentPopup.classList.add("popup-content");

        // Criando div para os botões de confirmar ou cancelar
        let divBotaoesEscolha = document.createElement("div");
        divBotaoesEscolha.classList.add("popup-buttons");

        // Criando botão para confirmar
        let botaoConfirmar = document.createElement("button");
        botaoConfirmar.textContent = "Confirmar empréstimo";
        botaoConfirmar.classList.add("button-confirmar");
        botaoConfirmar.addEventListener("click", () => {
            botaoConfirmar.disabled = true;
            confirmarEmprestimo(botaoConfirmar);
        })

        // Criando botão para cancelar
        let botaoCancelar = document.createElement("button");
        botaoCancelar.textContent = "Cancelar empréstimo";
        botaoCancelar.classList.add("button-cancelar");
        botaoCancelar.onclick = fecharPopup;

        // Adicionando botões na div de botoões
        divBotaoesEscolha.appendChild(botaoConfirmar);
        divBotaoesEscolha.appendChild(botaoCancelar);

        // Adicionando div de botões na div principal
        divContainer.appendChild(divBotaoesEscolha);

        // Encontrando recurso escolhido e adicionando eles a div de container
        let divItemMaisProximo = button.closest(".item")
        let listaParagrafos = divItemMaisProximo.querySelectorAll("p");
        const titulos = ["Departamento", "Estado", "Marca", "Descrição"];
        let i = 0;
        listaParagrafos.forEach(paragrafo => {
            if(paragrafo.style.display === "none"){
                localStorage.setItem("idRecursoSelecionado", paragrafo.textContent);
                return;
            }
            let novoParagrafo = document.createElement("h1");
            novoParagrafo.textContent = `${titulos[i]}: ${paragrafo.textContent}`;
            divContentPopup.insertAdjacentElement("afterbegin", novoParagrafo);
            i++
        })

        // Adicionando título ao container principal
        let nomeItem = document.createElement("h2");
        nomeItem.textContent = "Escolha a data e horário do empréstimo"
        divContainer.insertAdjacentElement("afterbegin", nomeItem);

        // Criando calendario
        let divDatePopup = criarCalendarioHora();

        // Div container para o calendario e para descrição do recurso selecionado
        let divCalendarioDescricaoContainer = document.createElement("div");
        divCalendarioDescricaoContainer.classList.add("content-container");

        // Adicionando divs ao DOM
        nomeItem.insertAdjacentElement("afterend", divContentPopup);
        divCalendarioDescricaoContainer.insertAdjacentElement("afterbegin", divDatePopup);

        nomeItem.insertAdjacentElement("afterend", divCalendarioDescricaoContainer);

        // Adicionando div container principal ao body
        document.querySelector("body").appendChild(divContainer);

        // Abrindo o popup de confirmação de empréstimo
        abrirPopup();

        setHorariosReserva();
    })
})

document.addEventListener("DOMContentLoaded", () => {
    const inputTexto = document.querySelector(".container-busca input");
    const categoriaPesquisa = document.querySelector(".container-busca select");
    const itens = document.querySelectorAll(".item");

    inputTexto.addEventListener("input", () => {
        const textoPesquisa = inputTexto.value.toLowerCase();
        const categoria = categoriaPesquisa.value;

        itens.forEach(item => {
            const departamento = item.querySelector("p:nth-child(1)").textContent.toLowerCase();
            const estado = item.querySelector("p:nth-child(2)").textContent.toLowerCase();
            const marca = item.querySelector("p:nth-child(3)").textContent.toLowerCase();
            const descricao = item.querySelector("p:nth-child(4)").textContent.toLowerCase();

            let visivel;

            if (categoria === "departamento") {
                visivel = departamento.includes(textoPesquisa);
            } else if (categoria === "marca") {
                visivel = marca.includes(textoPesquisa);
            } else if (categoria === "estado") {
                visivel = estado.includes(textoPesquisa);
            } else{
                visivel = descricao.includes(textoPesquisa);
            }

            item.style.display = visivel ? "flex" : "none";
        });
    });
});