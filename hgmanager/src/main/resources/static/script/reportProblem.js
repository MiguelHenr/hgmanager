async function sendReport() {

    const comment = document.querySelector("#com").value;

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const reservaId = urlParams.get('id');

    var response = await fetch('registrar_reclamacao', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                comment: comment,
                id: reservaId
            })
    });

    if (await response.ok) {
        window.location.replace('/');
    }

}

let button = document.querySelector("button");

button.addEventListener('click', () => {
    sendReport();
})