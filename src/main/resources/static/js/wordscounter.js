function sendFile(csrfToken, file, sourceLanguage, destinationLanguage) {
    $.ajax({
        url: "/wordscounter",
        method: 'POST',
        data: '_csrf=' + csrfToken + '&fileSize=' + file.size
    }).then(function (data) {
        var formData = new FormData();
        formData.append("file", file);
        $.ajax({
            url: data.url + '?from=' + sourceLanguage + '&to=' + destinationLanguage,
            method: 'POST',
            data: formData,
            headers: {
                Authorization: "Bearer " + data.token
            },
            processData: false,
            cache: false,
            contentType: false
        }).then(function (data) {
            sessionStorage.setItem("words", JSON.stringify(data));
            window.location.pathname = "/result"
        })
    });
}

$(document).ready(function () {
    $('#fileselector').fileselect({language: 'en'});

    $("#submit").click(function (event) {
        event.preventDefault();
        var csrfToken = $("#csrf").text();
        var file = $("#fileselector")[0].files[0];
        var sourceLangauge = $("#source-language").val()
        var destinationLangauge = $("#destination-language").val();
        sendFile(csrfToken, file, sourceLangauge, destinationLangauge);
    })
});