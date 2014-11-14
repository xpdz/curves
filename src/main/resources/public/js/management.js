$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');

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
                $('<td><a class="btn btn-primary" href="trends.htm?clubId=' + clubs[i].clubId+'"><i class="fa fa-line-chart"></i> Trends</a></td>')
            ));
        }
    }).fail(function() {
        showAlert("alert-danger", "Cannot find clubs, please refresh and retry.");
    });
}

function showAlert(alertClass, msg) {
    $('.alert').removeClass('hide');
    $('.alert').addClass(alertClass);
    $('.alert').text(msg);
    setTimeout(function() {
        $('.alert').addClass('hide');
        $('.alert').removeClass(alertClass);
    }, 5000);
}

});
