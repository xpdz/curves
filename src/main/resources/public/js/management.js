$(document).ready(function() {
  $.getScript('js/common.js', function() {
    // init date picker
    var today = new Date();
    $('#newClubOpenDate').val(today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate());
    $('.input-group.date').datepicker({
        autoclose: true,
        orientation: "top",
        format: "yyyy-mm-dd",
        language: "zh-TW",
        todayHighlight: true
    });

    var findClubs, sortIcon, page = 1, sortBy = 'clubId', isAsc = true;
    (findClubs = function () {
      // construct request uri by page and sort
      var uri = "/rest/clubs?page="+page+"&sort_by="+sortBy+"&sort_asc="+isAsc;
      // add sort icon to table header
      if (sortIcon) {
        sortIcon.remove();
      }
      sortIcon = isAsc ? $('<i class="fa fa-sort-asc fa-lg"></i>') : $('<i class="fa fa-sort-desc fa-lg"></i>');
      $('th[data-sort-by="'+sortBy+'"]').append(sortIcon);

      // fetch table content from server side
      $.getJSON(uri, function(clubs) {
        // fill table content
        fillTable(clubs);

        // construct pagination component (empty it first)
        $('.badge').text(clubs.totalElements);
        var $pager = $('.pagination');
        $pager.empty();
        for (var i = 0; i < clubs.totalPages; i++) {
          $pager.append($('<li data-index="'+i+'"/>').append($('<a href="#">'+(i+1)+'</a>')));
        }
        $('.pagination > li[data-index="'+clubs.number+'"]').addClass('active');

        $('.pagination > li > a').click(function() {
          page = $(this).text();
          findClubs();
        });

        // processing edit club btn
        $('a[data-btn="btnEditClub"]').click(function() {
          $('#newClubId').val($(this).attr('data-clubId'));
          $('#newClubName').val($(this).attr('data-name'));
          $('#newClubOwner').val($(this).attr('data-owner'));
          $('#newClubOpenDate').val($(this).attr('data-openDate'));
          $('#addOrEditClub').modal();
        });
        // processing reset password btn
        $('a[data-btn="btnResetPassword"]').click(function() {
          if (confirm("Do you want to reset password for this club?")) {
            $.get('/rest/reset_password?clubId='+$(this).attr('data-clubId'), function() {
              showAlert("#alertMain", "alert-success", "Success!");
            }).fail(function() {
              showAlert("#alertMain", "alert-danger", "Reset password failed. Please refresh and retry.");
            });
          }
        });
      }).fail(function() {
        showAlert("#alertMain", "alert-danger", "Cannot find clubs, please refresh and retry.");
      });
    })();

    // add sort icon to table header according to sort_by & sort_asc
    $('th').click(function() {
      isAsc = sortBy != $(this).attr('data-sort-by');
      sortBy = $(this).attr('data-sort-by');
      page = 1;
      findClubs();
    });

    var fillTable = function(clubs) {
      var $tbd = $('#tbd');
      $tbd.empty();
      for (var i = 0; i < clubs.numberOfElements; i++) {
        var openDay = new Date(clubs.content[i].openDate);
        var od = openDay.getFullYear() + '-' + (openDay.getMonth()+1) + '-' + openDay.getDate();
        $tbd.append($('<tr/>').append(
          $('<td>' + clubs.content[i].clubId + '</td>'),
          $('<td>' + clubs.content[i].name + '</td>'),
          $('<td>' + clubs.content[i].owner + '</td>'),
          $('<td>' + od + '</td>'),
          $('<td><a class="btn btn-info" href="PJ.htm?clubId='+clubs.content[i].clubId+'">PJ</a><a class="btn btn-info" href="pj_summary.htm?clubId='+clubs.content[i].clubId+'&amp;clubName='+clubs.content[i].name+'&amp;clubOwner='+clubs.content[i].owner+'">Summary</a></td>'),
          $('<td><a class="btn btn-info" href="CA.htm?clubId='+clubs.content[i].clubId+'">CA</a><a class="btn btn-info" href="ca_summary.htm?clubId='+clubs.content[i].clubId+'&amp;clubName='+clubs.content[i].name+'&amp;clubOwner='+clubs.content[i].owner+'">Summary</a></td>'),
          $('<td><a class="btn btn-primary" href="trends.htm?clubId='+clubs.content[i].clubId+'"> <i class="fa fa-line-chart fa-lg"></i> </a></td>'),
          $('<td><a class="btn btn-primary" href="#" data-btn="btnEditClub" data-clubId="'+clubs.content[i].clubId+'" data-name="'+clubs.content[i].name+'" data-owner="'+clubs.content[i].owner+'" data-openDate="'+od+'"><i class="fa fa-pencil-square-o fa-lg"></i></a></td>'),
          $('<td><a class="btn btn-danger" href="#" data-btn="btnResetPassword" data-clubId="'+clubs.content[i].clubId+'"><i class="fa fa-key fa-lg"></i></a></td>')
        ));
      }
    }

    $('#btnSave1').click(function() {
        var clubId = $('#newClubId').val(), name = $('#newClubName').val(),
         openDate = $('#newClubOpenDate').val(), owner = $('#newClubOwner').val();
        var hasError = false;
        if ( !clubId || !name || !owner) {
            showAlert("#alertAcct", "alert-danger", "All fields must be filled.");
            return;
        }

        $(this).prop("disabled", true);

        $.post('/rest/clubs', {
          clubId: clubId, name: name, openDate: $('.input-group.date').datepicker('getUTCDate'), owner: owner
        }).done(function() {
          showAlert("#alertClub", "alert-success", "Save successfully.");
        }).fail(function(jqXHR, textStatus, errorThrown) {
          console.log("ERROR STATUS: "+textStatus);
          showAlert("#alertClub", "alert-danger", "Save Fail. Please refresh and retry.");
        });

        $('#btnSave1').prop("disabled", false);
    });
  });
});
