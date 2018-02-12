function showInTable() {
    var words = JSON.parse(sessionStorage.getItem("words"));
    $("input[name=from]").attr("value", sessionStorage.getItem("sourceLanguage"));
    $("input[name=to]").attr("value", sessionStorage.getItem("destinationLanguage"));
    for (i in words) {
        var row = $("<tr />");
        $("tbody").append(row);
        row.append($("<td>" + (Number(i) + 1) + "</td>"));
        row.append($("<td>" + words[i].ori + "</td>"));
        row.append($("<td>" + words[i].tra + "</td>"));
        row.append($("<td>" + words[i].num + "</td>"));
        row.append($("<td><input type='checkbox' name='toLearn' value='" + words[i].lem + "'></td>"));
    }
}

$(document).ready(function () {
   showInTable();
});