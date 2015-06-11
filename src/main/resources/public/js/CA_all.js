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
        getCaAll();
    });

    getCaAll();
    function getCaAll() {
      // first of all, clear last search result
      var $thRow = $('#theader');
      $thRow.empty();
      $.each($('[data-flag]'), function(idx, obj) {
        obj.remove();
      });

      $.getJSON("/rest/CaAll/stat", {caYear: currentYear, caMonth: currentMonth}, function(data) {
        for (var item in data) {
          if ( !data.hasOwnProperty(item) ) {
            continue;
          }
          $('#'+item).append('<td data-flag="">'+formatValue(item, data[item].sum)+'</td>')
                      .append('<td data-flag="">'+formatValue(item, data[item].avg)+'</td>')
                      .append('<td data-flag="">'+formatValue(item, data[item].highest)+'</td>')
                      .append('<td data-flag="">'+formatValue(item, data[item].lowest)+'</td>');
        }
      }).fail(function() {
        showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
      });

      $.getJSON("/rest/CaAll", {caYear: currentYear, caMonth: currentMonth}, function(cas) {
        $thRow.append('<th colspan="3" style="background-color: black; color: white; width: 580px;">Goals</th>');
        $thRow.append('<th style="width: 80px">SUM</th>');
        $thRow.append('<th style="width: 80px">AVG</th>');
        $thRow.append('<th style="width: 80px">HIGHEST</th>');
        $thRow.append('<th style="width: 80px">LOWEST</th>');
        var clubsCount = 0;
        for (var name in cas) {
          if ( !cas.hasOwnProperty(name) ) {
            continue;
          }

          $thRow.append('<th data-flag="" style="width: 120px;">'+name+'</th>');
          for (var item in cas[name]) {
            $('#'+item).append('<td data-flag="">'+formatValue(item, cas[name][item])+'</td>');
          }
          clubsCount++;
        }
        var tblWidth = 580 + 320 + clubsCount * 120;
        $('table').css('max-width', tblWidth+'px');
        $('table').width(tblWidth);
      }).fail(function() {
        showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
      });
    }

    $('#btnExport').click(function() {
        window.location = "rest/CaAll/export?caYear="+currentYear+"&caMonth="+currentMonth;
    });

    function formatValue(item, value) {
      if (item.indexOf('Ratio') != -1 || item === 'SvcMaxWo6' || item === 'Svc12_6'
       || item === 'Svc8to11_6' || item === 'Svc4to7_6' || item === 'Svc1to3_6' || item === 'Svc0_6') {
        if (item.indexOf('ExitsRatio') != -1 || item.indexOf('HoldRatio') != -1) {
          return (value*100).toFixed(1)+'%';
        } else {
          return (value*100).toFixed(0)+'%';
        }
      } else if (item === 'SvcAvgWo6' || item === 'CmHandFlyerHours6' || item === 'CmOutGpHours6'
       || item === 'CmHandHoursPerApo6' || item === 'CmOutGpHoursPerApo6') {
        return value.toFixed(1);
      } else {
        return value.toFixed(0);
      }
    }
  });
});
