function sendPost() {
    const error = document.getElementById('error');
    const success = document.getElementById('success');
    let classId1 = document.getElementById('classFirstChoise').value;
    console.log(classId1);
    let classId = classId1.split('-');
    console.log(classId[0]);

    let formData = new FormData();

    formData.append("file", document.getElementById('res').files[0]);

    return $.ajax({
        type: "POST",
        url: "/edit-mode/check/resource/"+classId[0],
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

