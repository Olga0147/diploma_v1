function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const aggregationObj = document.getElementById('associationId');
    var associationId = aggregationObj.innerText || aggregationObj.textContent;

    let start = document.getElementById('AssFromChoise').value;
    let end = document.getElementById('AssToChoise').value;


    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/check/association-impl/"+associationId, true);
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
        aggregationId: associationId,
        fromObjectId: start,
        toObjectId:end
    });

    xhr.send(json);
}

function addAssForm() {
    let assId = document.getElementById('AssFirstChoise').value;
    console.log(assId);
    let arr = assId.split('-');
    console.log(arr[0]);
    let request = new XMLHttpRequest();
    let url = '/edit-mode/part-form/association-impl/'+arr[0];
    request.open('GET', url, false);
    request.send();
    let str = request.responseText;
    $(".innerForm").empty();
    $(".innerForm").append(str);
}
