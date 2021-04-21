function sendPost(objectId,attributeName) {
    const success = document.getElementById('div_'+attributeName);
    console.log('div_'+attributeName);
    const error = success;
    const toDelete = document.getElementById('form_'+attributeName);
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
            success.innerHTML =`<a href="/download-mode/mmedia/${rez}">${rez}</a>`
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

