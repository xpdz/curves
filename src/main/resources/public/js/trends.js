$(document).ready(function() {
  $.getScript('js/common.js', function() {
    // init user ID / club ID
    $.get("/rest/whoami", function(userId) {
      $('#userId').html('<i class="fa fa-user"></i> '+userId+' <span class="caret"></span>');
        var clubId = userId;
        if (userId.charAt(0) === '0') {
          // user is coach, not club, hide PJ
          clubId = '1' + userId.substring(1);
          $('ul[data-curves="coach"]').show();
        } else if (userId === 'management' || userId === '999999') {
          $('ul[data-curves="mgmt"]').show();
        } else {
          $('ul[data-curves="club"]').show();
        }
      if ($.QueryString.clubId && $.QueryString.clubId != clubId) {
        clubId = $.QueryString.clubId;
      }

      var today = new Date();
      var tmpDate = new Date(today.getFullYear(), today.getMonth()-1, 1);
      var yEnd = tmpDate.getFullYear();
      var mEnd = tmpDate.getMonth();
      tmpDate.setMonth(mEnd - 5);
      var yStart = tmpDate.getFullYear();
      var mStart = tmpDate.getMonth();

      // init date picker
      $('#x1Date').val(yStart + '-' + (mStart+1));
      $('#x1Date').datepicker({
          minViewMode: 1,
          autoclose: true,
          orientation: "top",
          format: "yyyy-mm",
          language: "zh-TW",
          todayHighlight: true
      });
      $('#x1Date').datepicker().on('changeDate', function(ev) {
          yStart = ev.date.getFullYear();
          mStart = ev.date.getMonth();
      });
      $('#x2Date').val(yEnd + '-' + (mEnd+1));
      $('#x2Date').datepicker({
          minViewMode: 1,
          autoclose: true,
          orientation: "top",
          format: "yyyy-mm",
          language: "zh-TW",
          todayHighlight: true
      });
      $('#x2Date').datepicker().on('changeDate', function(ev) {
          yEnd = ev.date.getFullYear();
          mEnd = ev.date.getMonth();
      });

      var chartOption = {
          animation: false,
          bezierCurve: false,
          scaleBeginAtZero: true
      };
      var lineData = {
          labels: [],
          datasets: [
              {
                  fillColor: "rgba(151,187,205,0.2)",
                  strokeColor: "rgba(151,187,205,1)",
                  pointColor: "rgba(151,187,205,1)",
                  pointStrokeColor: "#fff",
                  pointHighlightFill: "#fff",
                  pointHighlightStroke: "rgba(151,187,205,1)",
                  data: []
              }
          ]
      };

      $('#btnLoad').click(function() {
          getTrends();
      });

      $('a[download]').click(function() {
        var imgFile = $(this).attr('download');
        var canvasId = 'chart' + $(this).attr('id').substring(7);
        var dataUrl = document.getElementById(canvasId).toDataURL('image/png');
        dataUrl = dataUrl.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
        dataUrl = dataUrl.replace(/^data:application\/octet-stream/, 'data:application/octet-stream;headers=Content-Disposition%3A%20attachment%3B%20filename=' + imgFile);
        $(this).attr('href', dataUrl);
      });

      var ctx = $("#chartNewSales").get(0).getContext("2d");
      var chartNewSales = new Chart(ctx);
      ctx = $("#chartFaSum").get(0).getContext("2d");
      var chartFaSum = new Chart(ctx);
      ctx = $("#chartBrOwnRef").get(0).getContext("2d");
      var chartBrOwnRef = new Chart(ctx);
      ctx = $("#chartSvcTotalWo").get(0).getContext("2d");
      var chartSvcTotalWo = new Chart(ctx);
      ctx = $("#chartCmTotalGot").get(0).getContext("2d");
      var chartCmTotalGot = new Chart(ctx);
      ctx = $("#chartCmInApptRatio").get(0).getContext("2d");
      var chartCmInApptRatio = new Chart(ctx);
      ctx = $("#chartCmFaSum").get(0).getContext("2d");
      var chartCmFaSum = new Chart(ctx);
      ctx = $("#chartExitsRatio").get(0).getContext("2d");
      var chartExitsRatio = new Chart(ctx);
      ctx = $("#chartSalesRatio").get(0).getContext("2d");
      var chartSalesRatio = new Chart(ctx);
      ctx = $("#chartProductsRevenue").get(0).getContext("2d");
      var chartProductsRevenue = new Chart(ctx);

      getTrends();
      function getTrends() {
          var dt = new Date(yStart, mStart, 1);
          for (var i = 0; (dt.getFullYear() <= yEnd && dt.getMonth() <= mEnd); i++) {
              lineData.labels[i] = dt.getFullYear()+'-'+(dt.getMonth()+1);
              dt.setMonth(dt.getMonth() + 1);
          }

          $.getJSON("/rest/trends", {clubId: clubId, yStart: yStart, yEnd: yEnd, mStart: mStart, mEnd: mEnd}, function(respData) {
              lineData.datasets[0].data = respData.NewSales;
              chartNewSales.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.ProductsRevenue;
              chartProductsRevenue.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.BrOwnRef;
              chartBrOwnRef.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.FaSum;
              chartFaSum.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.SvcTotalWo;
              chartSvcTotalWo.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.CmTotalGot;
              chartCmTotalGot.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.CmInApptRatio;
              chartCmInApptRatio.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.CmFaSum;
              chartCmFaSum.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.ExitsRatio;
              chartExitsRatio.Line(lineData, chartOption);

              lineData.datasets[0].data = respData.SalesRatio;
              chartSalesRatio.Line(lineData, chartOption);
          }).fail(function() {
              showAlert("#alertMain", "alert-danger", "Cannot find data, please refresh and retry.");
          });
      }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR STATUS: "+textStatus);
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });
  });
});
