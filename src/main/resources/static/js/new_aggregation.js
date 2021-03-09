function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');

    let class_name = document.getElementById('name').value;
    let startFull = document.getElementById('pageStartChoise').value;
    let endFull = document.getElementById('pageEndChoise').value;

    let start = startFull.split('-')[0];
    let end = endFull.split('-')[0];

    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/check/aggregation", true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log('200');
            let rez = JSON.parse(xhr.response).message;
            $("#toDelete").remove();
            success.innerHTML =`<div><span>${rez}</span></div>`
        } else if (xhr.status === 400) {
            let rez = JSON.parse(xhr.response);
            error.innerHTML =`<div><span>${rez}</span></div>`
            console.log(rez.errorMessage);
        } else if (xhr.status === 401) {
            console.log('401');
        } else if (xhr.status === 403) {
            console.log('403');
        } else if (xhr.status === 404) {
            console.log('404');
        }
    }

    let json = JSON.stringify({
        name: class_name,
        fromClassId: start,
        toClassId: end
    });

    xhr.send(json);
}
