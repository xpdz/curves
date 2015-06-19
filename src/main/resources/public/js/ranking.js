$(document).ready(function() {
  $.getScript('js/common.js', function() {
    // init user ID / club ID
    $.get("/rest/whoami", function(userId) {
      $('#userId').html('<i class="fa fa-user"></i> '+userId+' <span class="caret"></span>');
      var clubId = userId;
      if (userId.charAt(0) === '0') {
        // user is coach, not club, hide PJ
        clubId = '1' + userId.substring(1);
        $('a[href="PJ.htm"]:parent').hide();
      }

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
          getRanking();
      });

      function formatValue(item, value) {
          if (item === 'SalesRatio6') {
              return (value*100).toFixed(0)+'%';
          } else if (item === 'CmHandFlyerHours6' || item === 'CmOutGpHours6') {
              return value.toFixed(1);
          } else {
              return value.toFixed(0);
          }
      }

      var getRanking;
      (getRanking = function() {
        $.getJSON("/rest/ranking", {clubId: clubId, year: currentYear, month: currentMonth}, function(respData) {
          $('td div').removeClass('you').html('&nbsp;');
          for (var key in respData) {
            if (respData.hasOwnProperty(key)) {
              var you = respData[key].you, max = respData[key].max, halfHigh = respData[key].halfHigh,
               mid = respData[key].mid, maxHalfHigh = halfHigh + (max - halfHigh) / 2, halfHighMid = mid + (halfHigh - mid) / 2;
              $('#'+key+'-1').text(formatValue(key, max));
              $('#'+key+'-5').text(formatValue(key, halfHigh));
              $('#'+key+'-9').text(formatValue(key, mid));
              if ( you == undefined) {
                continue;
              }
              var $divYou;
              if (you === max) {
                $divYou = $('#'+key+'-1');
              } else if (you === halfHigh) {
                $divYou = $('#'+key+'-5');
              } else if (you === mid) {
                $divYou = $('#'+key+'-9');
              } else if (you === maxHalfHigh) {
                $divYou = $('#'+key+'-3');
              } else if (you === halfHighMid) {
                $divYou = $('#'+key+'-7');
              } else if (you > maxHalfHigh && you < max) {
                $divYou = $('#'+key+'-2');
              } else if (you < maxHalfHigh && you > halfHigh) {
                $divYou = $('#'+key+'-4');
              } else if (you > halfHighMid && you < halfHigh) {
                $divYou = $('#'+key+'-6');
              } else if (you < halfHighMid && you > mid) {
                $divYou = $('#'+key+'-8');
              }
              $divYou.text(formatValue(key, you));
              $divYou.addClass('you');
              $('#'+key).text(respData[key].rank);
            }
          }
        }).fail(function() {
          showAlert("#alertMain", "alert-danger", "Cannot find data, please refresh and retry.");
        });
      })();
    });
  });
});
