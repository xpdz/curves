$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');

var clubId;

getClubs();

function getClubs() {
    $.getJSON("/rest/clubs", function(clubs) {
        for (var i = 0; i < clubs.length; i++) {
            $('#tbd').append($('<tr/>').append(
                $('<td>' + clubs[i].clubId + '</td>'),
                $('<td>' + clubs[i].name + '</td>'),
                $('<td>' + clubs[i].owner + '</td>'),
                $('<td><a class="btn btn-info" href="PJ.htm?clubId='+clubs[i].clubId+'">PJ</a></td>'),
                $('<td><a class="btn btn-info" href="CA.htm?clubId='+clubs[i].clubId+'">CA</a></td>'),
                $('<td><a class="btn btn-primary" href="trends.htm?clubId=' + clubs[i].clubId+'"><i class="fa fa-line-chart"></i> Trends</a></td>'),
                $('<td><a class="btn btn-primary" href="benchmarking.htm?clubId='+clubs[i].clubId+'">Benchmarking</a></td>')
            ));
        }
    }).fail(function() {
        alert("oops! I cannot find clubs, please try again.");
    });
}

});
