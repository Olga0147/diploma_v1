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
                let rez = JSON.parse(jqXHR.response);
                error.innerHTML =`<div><span>${rez}</span></div>`
                console.log(rez.errorMessage);
            } else if (jqXHR.status === 401) {
                console.log('401');
            } else if (jqXHR.status === 403) {
                console.log('403');
            } else if (jqXHR.status === 404) {
                console.log('404');
            } else if (exception === 'abort') {
                console.log('Ajax request aborted.');
            } else {
                console.log('Uncaught Error. ' + jqXHR.responseText);
            }
    }
    });
}

