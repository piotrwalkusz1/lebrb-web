function showInTable() {
    var words = JSON.parse(sessionStorage.getItem("words"));
    var knownWords = $("#knownWords").text().split(",");
    words = words.filter(function(el) {
        return knownWords.indexOf(el.lem) < 0;
    });
    $("input[name=from]").attr("value", sessionStorage.getItem("sourceLanguage"));
    $("input[name=to]").attr("value", sessionStorage.getItem("destinationLanguage"));
    var table = "";
    for (i in words) {
        table += "<tr>";
        table += "<td>" + (Number(i) + 1) + "</td>";
        table += "<td>" + words[i].ori + "</td>";
        table += "<td>" + words[i].tra + "</td>";
        table += "<td>" + words[i].num + "</td>";
        table += "<td><input type='checkbox' name='toLearn' value='" + words[i].lem + "'></td>";
        table += "</tr>";
    }
    $("tbody").html(table);
}

$(document).ready(function () {
    setTimeout(showInTable, 0);
});