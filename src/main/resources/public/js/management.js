$(document).ready(function() {
  $.getScript('js/common.js', function() {
    (function() {
        $.getJSON("/rest/clubs", function(clubs) {
            for (var i = 0; i < clubs.length; i++) {
                $('#tbd').append($('<tr/>').append(
                    $('<td>' + clubs[i].clubId + '</td>'),
                    $('<td>' + clubs[i].name + '</td>'),
                    $('<td>' + clubs[i].owner + '</td>'),
                    $('<td><a class="btn btn-info" href="pj_summary.htm?clubId='+clubs[i].clubId+'&amp;clubName='+clubs[i].name+'&amp;clubOwner='+clubs[i].owner+'">PJ</a></td>'),
                    $('<td><a class="btn btn-info" href="ca_summary.htm?clubId='+clubs[i].clubId+'&amp;clubName='+clubs[i].name+'&amp;clubOwner='+clubs[i].owner+'">CA</a></td>'),
                    $('<td><a class="btn btn-primary" href="trends.htm?clubId='+clubs[i].clubId+'"><i class="fa fa-line-chart"></i> Trends</a></td>')
                ));
            }
        }).fail(function() {
            showAlert("#alertMain", "alert-danger", "Cannot find clubs, please refresh and retry.");
        });
    })();

    $('#btnSave1').click(function() {
        var clubId = $('#newClubId').val(), name = $('#newClubName').val(), owner = $('#newClubOwner').val();
        var hasError = false;
        if ( !clubId || !name || !owner) {
            showAlert("#alertAcct", "alert-danger", "All fields must be filled.");
            return;
        }

        $(this).prop("disabled", true);

        $.post("/rest/users", {
            clubId: clubId, name: name, owner: owner
        }).done(function() {
            showAlert("#alertAcct", "alert-success", "Save successfully.");
        }).fail(function() {
            showAlert("#alertAcct", "alert-danger", "Save Fail. Please refresh and retry.");
        }).always(function() {
            $(this).prop("disabled", false);
        });
    });
  });
});
