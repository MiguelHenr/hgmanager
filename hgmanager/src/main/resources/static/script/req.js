const rejectBts = document.getElementsByClassName('reject');
const approveBts = document.getElementsByClassName('approve');

function update(event, action) {
    const requestElement = event.target.closest("[data-index]");
    const id = requestElement.getAttribute("data-index");
    const dataToSend = JSON.stringify({ id: id, action: action });

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/update-sol", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            reloadRequestsContainer();
        } else {
            console.error("Server error:", xhr.response);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed. Check your network connection.");
    };

    xhr.send(dataToSend);
}

function reloadRequestsContainer() {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/requests", true);

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            
            document.getElementById("reqs").innerHTML = xhr.responseText;
            attachEventListeners(); 
        } else {
            console.error("Failed to reload requests:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Failed to reload requests. Check your network connection.");
    };

    xhr.send();
}

function attachEventListeners() {
    for (let btn of approveBts)
        btn.addEventListener("click", event => update(event, "approve"));

    for (let btn of rejectBts)
        btn.addEventListener("click", event => update(event, "reject"));
}

attachEventListeners();