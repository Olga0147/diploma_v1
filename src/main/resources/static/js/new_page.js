function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const block = document.getElementById(".toDelete");

    let length = $(".attribute").length;
    let class_attributes = [];

    for (var i = 1; i <= length; i++) {
        let singleObj = {};
        singleObj['name'] = document.getElementById('attribute_name_'+i).value;
        singleObj['type'] = document.getElementById('attribute_type_'+i).value;
        singleObj['size'] = document.getElementById('attribute_size_'+i).value;
        singleObj['isNull'] = document.getElementById('attribute_is_null_'+i).value;
        class_attributes.push(singleObj);
    }

    let class_name = document.getElementById('name').value;
    let class_description = document.getElementById('description').value;


    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/check/page", true);
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
        description: class_description,
        attributes: class_attributes
    });

    xhr.send(json);
}

function addAttributeForm() {

    let length = $(".attribute").length;
    console.log(length);
    let request = new XMLHttpRequest();
    let url = '/edit-mode/util/attribute/'+length;
    request.open('GET', url, false);
    request.send();
    let str = request.responseText;
    $(".attributes").append(str);
}
