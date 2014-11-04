$(document).ready(function() {

$('#userId').text('000000');

getClubs();

$("#rankX").click(function(event) {

});

function getClubs() {
    $.getJSON("/rest/clubs", function(clubs) {
        for (var i = 0; i < clubs.length; i++) {
            $('#tbd').append($('<tr/>').append(
                $('<td>' + clubs[i].clubId + '</td>'),
                $('<td>' + clubs[i].name + '</td>'),
                $('<td>' + clubs[i].owner + '</td>'),
                $('<td>' + clubs[i].manager + '</td>'),
                $('<td/>')
            ));
        }
    }).fail(function() {
        alert("oops! I cannot find clubs, please try again.");
    });
}

});
