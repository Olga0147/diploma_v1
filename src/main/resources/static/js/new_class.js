function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');

    let length = $(".attribute").length;
    let class_attributes = [];

    for (var i = 1; i <= length; i++) {
        let singleObj = {};
        singleObj['name'] = document.getElementById('attribute_name_'+i).value;
        console.log(document.getElementById('attribute_name_'+i).value);

        singleObj['type'] = document.getElementById('attribute_type_'+i).value;
        console.log(document.getElementById('attribute_type_'+i).value);

        singleObj['size'] = document.getElementById('attribute_size_'+i).value;
        console.log(document.getElementById('attribute_size_'+i).value);

        singleObj['content'] = document.getElementById('attribute_content_type_'+i).value;
        console.log(document.getElementById('attribute_content_type_'+i).value);

        singleObj['isNull'] = document.getElementById('attribute_is_null_'+i).value;
        class_attributes.push(singleObj);
    }

    let class_name = document.getElementById('name').value;
    let class_description = document.getElementById('description').value;


    const xhr = new XMLHttpRequest();
    let path = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port;
    xhr.open("POST", path + "/edit-mode/check/class", true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log('200');
                let rez = JSON.parse(xhr.response).message;
                $("#toDelete").remove();
                success.innerHTML = `<div><span>${rez}</span></div>`
            } else if (xhr.status === 400) {
                let rez = JSON.parse(xhr.response).message;
                error.innerHTML = `<div><span>${rez}</span></div>`
                console.log(rez.errorMessage);
            } else {
                error.innerHTML = `<div><span>Упс, произошла ошибка</span></div>`
                console.log("3: Статус ошибки" + xhr.status);
            }
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
    let url = '/edit-mode/part-form/attribute/'+length;
    request.open('GET', url, false);
    request.send();
    let str = request.responseText;
    $(".attributes").append(str);
}
