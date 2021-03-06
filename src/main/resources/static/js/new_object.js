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

function addClassForm() {
    let classId = document.getElementById('classFirstChoise').value;
    console.log(classId);
    let arr = classId.split('-');
    console.log(arr[0]);
    let request = new XMLHttpRequest();
    let url = '/edit-mode/part-form/object/'+arr[0];
    request.open('GET', url, false);
    request.send();
    let str = request.responseText;
    $(".innerForm").empty();
    $(".innerForm").append(str);
}
