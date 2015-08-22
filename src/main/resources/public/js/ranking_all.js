$(document).ready(function() {
  $.getScript('js/common.js', function() {
    // init user ID / club ID
    $.get("/rest/whoami", function(userId) {
        $('#userId').html('<i class="fa fa-user"></i> '+userId+' <span class="caret"></span>');
        var clubId = userId;

        var $tbd = $("#tbd");

        var today = new Date();
        var thisYear = today.getFullYear(), thisMonth = today.getMonth(),
            currentYear = thisYear, currentMonth = thisMonth;
        var lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

        // init date picker
        $('#monthPicker').val(thisYear+"-"+(thisMonth+1));
        $('.input-group.date').datepicker({
            minViewMode: 1,
            autoclose: true,
            orientation: "top",
            format: "yyyy-mm",
            language: "zh-TW",
            todayHighlight: true
        });
        $('.input-group.date').datepicker().on('changeDate', function(ev) {
            currentYear = ev.date.getFullYear();
            currentMonth = ev.date.getMonth();
            getRankingAll();
        });

        var getRankingAll, sortIcon, sortBy = 'MaxWorkOuts', iconDesc = $('<i class="fa fa-sort-desc fa-lg"></i>');//, isAsc = false;
        (getRankingAll = function () {
          $tbd.empty();

          var searchText = $("#searchText").val();
          var uri = "/rest/ranking_all?searchText="+searchText+"&year="+currentYear+"&month="+currentMonth+"&sort_by="+sortBy;

          // add sort icon to table header
          iconDesc.remove();
          $('th[data-sort-by="'+sortBy+'"]').append(iconDesc);

          $.getJSON(uri, function(rankingAll) {
              fillSheet(rankingAll);
          }).fail(function() {
              showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
          });
        })();

        // add sort icon to table header according to sort_by & sort_asc
        $('th[data-sort-by]').click(function() {
          //isAsc = sortBy != $(this).attr('data-sort-by');
          sortBy = $(this).attr('data-sort-by');
          getRankingAll();
        });

        $('#btnSearch').click(function() {
          getRankingAll();
        });

        function fillSheet(rankingAll) {
          for (var clubName in rankingAll) {
            if ( !rankingAll.hasOwnProperty(clubName) ) {
              continue;
            }

            var row = rankingAll[clubName], content = '';
            if (clubName === 'Max.' || clubName === 'Avg.' || clubName === '>50% Avg.') {
              for (var i = 0; i < row.length; i++) {
                var v;
                if (i === 18 || i === 19 || i === 24 || i === 25 || i === 26 || i === 27 || i === 28) {
                  v = (row[i]*100).toFixed(1)+'%'
                } else {
                  v = row[i].toFixed(0);
                }
                content += '<td>'+v+'</td>';
              }
            } else {
              for (var i = 0; i < row.length; i++) {
                content += '<td>'+row[i]+'</td>';
              }
            }
            $tbd.append($('<tr/>').append('<td>'+clubName+'</td>' + content));
         }
         //clearNaN();
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR STATUS: "+textStatus);
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });
  });
});
