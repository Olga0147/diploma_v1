function sendPost(objectId,attributeName) {
    const error = document.getElementById(attributeName);
    const success = document.getElementById(attributeName);
    console.log(attributeName);
    console.log(success);
    let formData = new FormData();
    formData.append("file", success.files[0]);
    console.log("/edit-mode/check/mmedia/"+objectId+"/"+attributeName);

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

