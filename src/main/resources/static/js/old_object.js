function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const object1 = document.getElementById('objectId');
    var objectId = object1.innerText || object1.textContent;

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
    xhr.open("POST", path + "/edit-mode/update-check/object/"+objectId, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE){
            if (xhr.status === 200) {
            console.log('200');
            let rez = JSON.parse(xhr.response).message;
            let objId = JSON.parse(xhr.response).id;
            $("#toDelete").remove();
            success.innerHTML =`
                    <div><span>${rez}</span></div>
                    <div>Если Объект содержит поле для файлов, то их можно добавить на странице детального просмотра Объекта</div>
                    <div><a href="${path}/view-mode/detail-info/object/${objId}">Перейти к Объекту</a></div>`
            } else if (xhr.status === 400) {
                let rez = JSON.parse(xhr.response).message;
                error.innerHTML = `<div><span>${rez}</span></div>`
                console.log(rez.errorMessage);
            }else {
                error.innerHTML = `<div><span>Упс, произошла ошибка</span></div>`
                console.log("3: Статус ошибки" + xhr.status);
            }
        }
    }

    let json = JSON.stringify({
        attributes: class_attributes
    });

    xhr.send(json);
}

