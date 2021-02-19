function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const classObj = document.getElementById('classId');
    var classId = classObj.innerText || classObj.textContent;

    let length = $(".attr").length;
    let class_attributes = [];

    for (var i = 0; i < length; i++) {
        let singleObj = {};

        var labelObj = document.getElementById('label'+i);
        singleObj['name'] = labelObj.innerText || labelObj.textContent;

        singleObj['value'] = document.getElementById('attr'+i).value;

        class_attributes.push(singleObj);

         console.log(singleObj['name']);
         console.log(singleObj['value']);
    }


    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/check/object/"+classId, true);
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
        attributes: class_attributes
    });

    xhr.send(json);
}

