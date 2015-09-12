$(document).ready(function() {
  $.getScript('js/common.js', function() {
    // init user ID / club ID
    $.get("/rest/whoami", function(userId) {
        $('#userId').html('<i class="fa fa-user"></i> '+userId+' <span class="caret"></span>');
        var clubId = userId;
        var sortBy = "MaxWorkOuts", isAsc = false;
        var today = new Date();
        var thisYear = today.getFullYear(), thisMonth = today.getMonth(), currentYear = thisYear, currentMonth = thisMonth;
        var lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

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

        var colProp60 = {width: "60px", defaultContent: ""}, colProp80 = {width: "80px", defaultContent: ""}, colProp100 = {width: "100px", defaultContent: ""};
        var tblRank = $('#tblRank').DataTable({
          serverSide: true,
          ajax: "/rest/ranking_all?year="+currentYear+"&month="+currentMonth,
          info: false,
          paging: false,
          scrollY: "58vh",
          scrollX: true,
          scrollCollapse: true,
          fixedColumns: true,
          order: [[1, 'desc']],
          columns: [
            {width: "80px", orderable: false, defaultContent: "      "},
            colProp80,colProp80,colProp60,colProp60,colProp80,colProp80,colProp80,colProp60,colProp60,colProp60,colProp60,
            colProp60,colProp60,colProp60,colProp60,colProp100,colProp80,colProp80,colProp60,colProp60,colProp60,colProp60,
            colProp60,colProp60,colProp60,colProp60,colProp60,colProp100,colProp100
          ]
        });

        var getRankingAll = function () {
          var url = "/rest/ranking_all?year="+currentYear+"&month="+currentMonth;
          tblRank.ajax.url(url).load();
          tblRank.tables().columns.adjust().fixedColumns().relayout();
        }

    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR STATUS: "+textStatus);
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });
  });
});
