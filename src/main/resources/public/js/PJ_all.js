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
          for (var clubName in pjSums) {
            if (pjSums.hasOwnProperty(clubName)) {
              var pjSum = pjSums[clubName];
              $tbd.append($('<tr/>').append(
                '<td>'+pjSum.clubId+'</td>' +
                '<td>'+clubName+'</td>' +
                '<td>'+pjSum.enrolled+'</td>' +
                '<td>'+pjSum.leaves+'</td>' +
                '<td>'+pjSum.valids+'</td>' +
                '<td>'+pjSum.newSales+'</td>' +
                '<td>'+pjSum.exits+'</td>' +
                '<td>'+pjSum.shiftIn+'</td>' +
                '<td>'+pjSum.shiftOut+'</td>' +
                '<td>'+pjSum.increment+'</td>' +
                '<td>'+pjSum.revenue+'</td>' +
                '<td>'+pjSum.newSalesRevenue+'</td>' +
                '<td>'+pjSum.duesDraftsRevenue+'</td>' +
                '<td>'+pjSum.productsRevenue+'</td>' +
                '<td>'+pjSum.wheyProteinRevenue+'</td>' +
                '<td>'+pjSum.otherRevenue+'</td>' +
                '<td>'+pjSum.incomingCalls+'</td>' +
                '<td>'+pjSum.incomingApo+'</td>' +
                '<td>'+pjSum.outgoingCalls+'</td>' +
                '<td>'+pjSum.outgoingApo+'</td>' +
                '<td>'+pjSum.brOwnRef+'</td>' +
                '<td>'+pjSum.brOthersRef+'</td>' +
                '<td>'+pjSum.brandedNewspaper+'</td>' +
                '<td>'+pjSum.brandedTv+'</td>' +
                '<td>'+pjSum.brandedInternet+'</td>' +
                '<td>'+pjSum.brandedSign+'</td>' +
                '<td>'+pjSum.brandedMate+'</td>' +
                '<td>'+pjSum.brandedOthers+'</td>' +
                '<td>'+pjSum.agpInDirectMail+'</td>' +
                '<td>'+pjSum.agpInMailFlyer+'</td>' +
                '<td>'+pjSum.agpInHandFlyer+'</td>' +
                '<td>'+pjSum.agpInCp+'</td>' +
                '<td>'+pjSum.agpOutApoOut+'</td>' +
                '<td>'+pjSum.agpOutApoIn+'</td>' +
                '<td>'+pjSum.agpOutApoBlog+'</td>' +
                '<td>'+pjSum.agpOutApoBag+'</td>' +
                '<td>'+pjSum.faSum+'</td>' +
                '<td>'+((pjSum.enrollAch+pjSum.enrollAllPrepay)*100/pjSum.newSales).toFixed(0)+'%</td>' +
                '<td>'+(pjSum.enrollMonthly*100/pjSum.newSales).toFixed(0)+'%</td>' +
                '<td>'+(pjSum.salesRatio*100).toFixed(0)+'%</td>' +
                '<td>'+(pjSum.exitRatio*100).toFixed(1)+'%</td>' +
                '<td>'+(pjSum.leaveRatio*100).toFixed(1)+'%</td>' +
                '<td>'+((pjSum.leaveRatio+pjSum.exitRatio)*100).toFixed(1)+'%</td>'
              ));
            }
          }
          clearNaN();
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR STATUS: "+textStatus);
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });
  });
});
