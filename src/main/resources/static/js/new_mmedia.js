function sendPost(objectId,attributeName) {
    const success = document.getElementById('div_'+attributeName);
    console.log('div_'+attributeName);
    const error = success;
    const toDelete = document.getElementById('form_'+attributeName);
    const toDelete2 = document.getElementById('toDelete');
    console.log('form_'+attributeName);

    let formData = new FormData();
    formData.append("file", document.getElementById(attributeName).files[0]);

    return $.ajax({
        type: "POST",
        url: "/edit-mode/check/mmedia/"+objectId+"/"+attributeName,
        data: formData,
        enctype: 'multipart/form-data',
        cache: false,
        contentType: false,
        processData: false,
        success: function(data){
            console.log('200');
            let rez = data.id;
            toDelete.remove();
            toDelete2.remove();
            success.innerHTML =`<a href="/download-mode/mmedia/${rez}" id=\"toDelete\">${attributeName}</a><div  style="border:1px solid grey" id="div_${attributeName}"><form id="form_${attributeName}" onsubmit="updatePost('${objectId}','${attributeName}');return false;" method="post" class="attribute">
                                                           <input type="file" id="${attributeName}"><input type="submit" value="Обновить"></form></div>`
        },
        error: function(jqXHR, exception){
            if (jqXHR.status === 400) {
                let rez = JSON.parse(xhr.response).message;
                toDelete.remove();
                error.innerHTML =`<div><span>${rez}</span></div>`;
                console.log('400');
                console.log(rez.errorMessage);
            }  else {
                error.innerHTML = `<div><span>Упс, произошла ошибка</span></div>`
                console.log("3: Статус ошибки" + xhr.status);
            }
    }
    });
}

function updatePost(objectId,attributeName) {
    const success = document.getElementById('div_'+attributeName);
    console.log('div_'+attributeName);
    const error = success;
    const toDelete = document.getElementById('form_'+attributeName);
    const toDelete2 = document.getElementById('toDelete');
    console.log('form_'+attributeName);

    let formData = new FormData();
    formData.append("file", document.getElementById(attributeName).files[0]);

    return $.ajax({
        type: "POST",
        url: "/edit-mode/update-check/mmedia/"+objectId+"/"+attributeName,
        data: formData,
        enctype: 'multipart/form-data',
        cache: false,
        contentType: false,
        processData: false,
        success: function(data){
            console.log('200');
            let rez = data.id;
            toDelete.remove();
            toDelete2.remove();
            success.innerHTML =`<a href="/download-mode/mmedia/${rez}" id=\"toDelete\">${attributeName}</a><div  style="border:1px solid grey" id="div_${attributeName}"><form id="form_${attributeName}" onsubmit="updatePost('${objectId}','${attributeName}');return false;" method="post" class="attribute">
                                                           <input type="file" id="${attributeName}"><input type="submit" value="Обновить"></form></div>`
        },
        error: function(jqXHR, exception){
            if (jqXHR.status === 400) {
                let rez = JSON.parse(xhr.response).message;
                toDelete.remove();
                error.innerHTML =`<div><span>${rez}</span></div>`;
                console.log('400');
                console.log(rez.errorMessage);
            }  else {
                error.innerHTML = `<div><span>Упс, произошла ошибка</span></div>`
                console.log("3: Статус ошибки" + xhr.status);
            }
        }
    });
}

