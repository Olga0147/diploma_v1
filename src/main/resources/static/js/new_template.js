function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const classObj = document.getElementById('classId');
    var classId = classObj.innerText || classObj.textContent;

    let body = document.getElementById('body').value;
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;


    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/check/template/"+classId, true);
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
        name:name,
        body: body,
        description: description,
        ownerClassId:classId
    });

    xhr.send(json);
}

function addClassForm() {
    let classId = document.getElementById('classFirstChoise').value;
    console.log(classId);
    let arr = classId.split('-');
    console.log(arr[0]);
    let request = new XMLHttpRequest();
    let url = '/edit-mode/part-form/template/'+arr[0];
    request.open('GET', url, false);
    request.send();
    let str = request.responseText;
    $(".innerForm").empty();
    $(".innerForm").append(str);
}
