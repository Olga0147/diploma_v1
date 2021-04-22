function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    let resourceId1 = document.getElementById('resourceId');
    var resourceId = resourceId1.innerText || resourceId1.textContent;

    console.log(resourceId);
    let formData = new FormData();
    formData.append("file", document.getElementById('res').files[0]);
    console.log('200');
    return $.ajax({
        type: "POST",
        url: "/edit-mode/update-check/resource/"+resourceId,
        data: formData,
        enctype: 'multipart/form-data',
        cache: false,
        contentType: false,
        processData: false,
        success: function(data){
            console.log('200');
            let rez = data.message;
            $("#toDelete").remove();
            success.innerHTML =`<div><span>${rez}</span></div>`
        },
        error: function(jqXHR, exception){
            if (jqXHR.status === 400) {
                let rez = JSON.parse(xhr.response).message;
                error.innerHTML =`<div><span>${rez}</span></div>`
                console.log(rez.errorMessage);
            } else {
                error.innerHTML = `<div><span>Упс, произошла ошибка</span></div>`
                console.log("3: Статус ошибки" + xhr.status);
            }
        }
    });
}

