function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    const classObj = document.getElementById('classId');
    const classId = classObj.innerText || classObj.textContent;

    let length = $(".attr").length;
    let class_attributes = [];
    let attributes_files = [];
    let j = 0;
    let formData = new FormData();

    for (let i = 0; i < length; i++) {
        let singleObj = {};

        const labelObj = document.getElementById('label' + i);
        singleObj['name'] = labelObj.innerText || labelObj.textContent;
        const type = document.getElementById('attr' + i).getAttribute('type');

        if(type === 'file'){
            const file = document.getElementById('attr' + i).files[0];
            const val = document.getElementById('attr' + i).value;
            attributes_files[j] = file;
            j=j + 1;
            console.log(file.name);
        }else{
            singleObj['value'] = document.getElementById('attr'+i).value;
        }

        //formData.append(singleObj['name'], singleObj['value']);
        class_attributes.push(singleObj);

         console.log('name= '+singleObj['name']);
         console.log('type= '+type);
         console.log('value= '+singleObj['value']);
    }

    //formData.append("attributes", class_attributes);
    formData.append("files", document.getElementById('attr3').files[0]);
    console.log(attributes_files);

    return $.ajax({
        type: "POST",
        url: "/edit-mode/check/object/"+classId,
        data: formData,
        enctype: 'multipart/form-data',
        cache: false,
        contentType: false,
        processData: false


    });


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
        attributes: class_attributes,
        files : attributes_files
    });

    //xhr.send(json);
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
