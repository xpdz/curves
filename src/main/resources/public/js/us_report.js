$(document).ready(function() {
  $.getScript('js/common.js', function() {
    var today = new Date();
    var thisYear = today.getFullYear();
    var thisMonth = today.getMonth();
    var currentYear = thisYear;
    var currentMonth = thisMonth;

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
        getUsReport();
    });

    $('#btnExport').click(function() {
        window.location = "rest/US_report_for_Taiwan?year="+currentYear+"&month="+currentMonth;
    });

    getUsReport();
    function getUsReport() {
      $('#tbd').empty();
      $.getJSON("/rest/usReport", {year: currentYear, month: currentMonth}, function(pjSums) {
        for (var idx = 0; idx < pjSums.length; idx++) {
          $('#tbd').append($('<tr/>').append(
              $('<td>' + (currentMonth+1) + '</td>'),
              $('<td>' + currentYear + '</td>'),
              $('<td>' + pjSums[idx].clubId + '</td>'),
              $('<td>' + pjSums[idx].maxWorkOuts + '</td>'),
              $('<td>' + pjSums[idx].newSalesRevenue + '</td>'),
              $('<td>0</td>'),
              $('<td>' + pjSums[idx].duesDraftsRevenue + '</td>'),
              $('<td>0</td>'),
              $('<td>' + pjSums[idx].productsRevenue + '</td>'),
              $('<td>0</td>'),
              $('<td>' + pjSums[idx].enrollAch + '</td>'),
              $('<td>' + pjSums[idx].enrollMonthly + '</td>'),
              $('<td>' + pjSums[idx].enrollAllPrepay + '</td>'),
              $('<td>' + pjSums[idx].exits + '</td>'),
              $('<td>' + pjSums[idx].incomingCalls + '</td>'),
              $('<td>' + pjSums[idx].incomingApo + '</td>'),
              $('<td>' + pjSums[idx].outgoingCalls + '</td>'),
              $('<td>' + pjSums[idx].outgoingApo + '</td>'),
              $('<td>' + (pjSums[idx].brOwnRef+pjSums[idx].agpOutApoIn) + '</td>'),
              $('<td>' + pjSums[idx].brandedNewspaper + '</td>'),
              $('<td>' + pjSums[idx].brandedTv + '</td>'),
              $('<td>' + pjSums[idx].brandedInternet + '</td>'),
              $('<td>' + pjSums[idx].agpInDirectMail + '</td>'),
              $('<td>' + pjSums[idx].brandedSign + '</td>'),
              $('<td>' + (pjSums[idx].agpInMailFlyer+pjSums[idx].agpInHandFlyer+pjSums[idx].agpOutApoOut) + '</td>'),
              $('<td>0</td>'),
              $('<td>' + (pjSums[idx].agpInCp+pjSums[idx].agpOutApoBlog+pjSums[idx].agpOutApoBag) + '</td>'),
              $('<td>' + pjSums[idx].brandedMate + '</td>'),
              $('<td>' + pjSums[idx].brandedOthers + '</td>'),
              $('<td>0</td>'),
              $('<td>' + pjSums[idx].enrolled + '</td>')
          ));
        }
      }).fail(function() {
        showAlert("#alertMain", "alert-danger", "Cannot find goal, please refresh and retry.");
      });
    }
  });
});
