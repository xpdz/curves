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
            getPJ_all();
        });

        $('#btnExport').click(function() {
          window.location = "/rest/PJ_All/export?year=" + currentYear + "&month=" + currentMonth;
        });

        function clearNaN() {
            $('td').each(function() {
                var valueX = $(this).text();
                if (valueX === 'undefined' || valueX === 'NaN' || valueX === 'NaN%' || valueX === 'Infinity%') {
                    $(this).text('');
                }
            });
        }

        getPJ_all();
        function getPJ_all() {
            $tbd.empty();

            $.getJSON("/rest/PJ_All", {year: currentYear, month: currentMonth}, function(pjSums) {
                fillSheet(pjSums);
            }).fail(function() {
                showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
            });
        }

        function fillSheet(pjSums) {
          $.each(pjSums, function(i, e) {
            $tbd.append($('<tr/>').append(
              '<td>'+e.clubId+'</td>' +
              '<td>'+e.year+'</td>' +
              '<td>'+(e.month+1)+'</td>' +
              '<td>'+e.enrolled+'</td>' +
              '<td>'+e.leaves+'</td>' +
              '<td>'+e.valids+'</td>' +
              '<td>'+e.newSales+'</td>' +
              '<td>'+e.exits+'</td>' +
              '<td>'+e.shiftIn+'</td>' +
              '<td>'+e.shiftOut+'</td>' +
              '<td>'+e.increment+'</td>' +
              '<td>'+e.revenue+'</td>' +
              '<td>'+e.newSalesRevenue+'</td>' +
              '<td>'+e.duesDraftsRevenue+'</td>' +
              '<td>'+e.productsRevenue+'</td>' +
              '<td>'+e.wheyProteinRevenue+'</td>' +
              '<td>'+e.otherRevenue+'</td>' +
              '<td>'+e.incomingCalls+'</td>' +
              '<td>'+e.incomingApo+'</td>' +
              '<td>'+e.outgoingCalls+'</td>' +
              '<td>'+e.outgoingApo+'</td>' +
              '<td>'+e.brOwnRef+'</td>' +
              '<td>'+e.brOthersRef+'</td>' +
              '<td>'+e.brandedNewspaper+'</td>' +
              '<td>'+e.brandedTv+'</td>' +
              '<td>'+e.brandedInternet+'</td>' +
              '<td>'+e.brandedSign+'</td>' +
              '<td>'+e.brandedMate+'</td>' +
              '<td>'+e.brandedOthers+'</td>' +
              '<td>'+e.agpInDirectMail+'</td>' +
              '<td>'+e.agpInMailFlyer+'</td>' +
              '<td>'+e.agpInHandFlyer+'</td>' +
              '<td>'+e.agpInCp+'</td>' +
              '<td>'+e.agpOutApoOut+'</td>' +
              '<td>'+e.agpOutApoIn+'</td>' +
              '<td>'+e.agpOutApoBlog+'</td>' +
              '<td>'+e.agpOutApoBag+'</td>' +
              '<td>'+e.faSum+'</td>' +
              '<td>'+((e.enrollAch+e.enrollAllPrepay)*100/e.newSales).toFixed(0)+'%</td>' +
              '<td>'+(e.enrollMonthly*100/e.newSales).toFixed(0)+'%</td>' +
              '<td>'+(e.salesRatio*100).toFixed(0)+'%</td>' +
              '<td>'+(e.exitRatio*100).toFixed(1)+'%</td>' +
              '<td>'+(e.leaveRatio*100).toFixed(1)+'%</td>' +
              '<td>'+((e.leaveRatio+e.exitRatio)*100).toFixed(1)+'%</td>'
            ));
          });
          clearNaN();
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR STATUS: "+textStatus);
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });
  });
});
