function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const templateObj = document.getElementById('templateId');
    var templateId = templateObj.innerText || templateObj.textContent;

    let body = document.getElementById('body').value;
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;


    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/update-check/template/"+templateId, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE){
            if (xhr.status === 200) {
            console.log('200');
            let rez = JSON.parse(xhr.response).message;
            $("#toDelete").remove();
            success.innerHTML =`<div><span>${rez}</span></div>`
            } else if (xhr.status === 400) {
                let rez = JSON.parse(xhr.response).message;
                error.innerHTML =`<div><span>${rez}</span></div>`
                console.log(rez.errorMessage);
            } else {
                error.innerHTML = `<div><span>Упс, произошла ошибка</span></div>`
                console.log("3: Статус ошибки" + xhr.status);
            }
        }
    }

    let json = JSON.stringify({
        name:name,
        body: body,
        description: description,
        id:templateId
    });

    xhr.send(json);
}

