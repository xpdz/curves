$(document).ready(function() {
  $.getScript('js/common.js', function() {
    var item = 'cmPostFlyer6';

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
        getBenchmark();
    });

    $('#btnSave').click(function() {
        var value = $('#thisGoal').val();

        $.post("/rest/goal", {year: currentYear, month: currentMonth, item: item, value: value})
            .done(function() {
                showAlert("#alertMain", "alert-success", "Save successfully.");
        }).fail(function() {
            showAlert("#alertMain", "alert-danger", "Save Fail. Please refresh and retry.");
        });
    });

    $('a[name]').click(function() {
        item = $(this).attr('name');
        $('#itemTitle').text($(this).text());
        getGoal();
        getBenchmark();
    });

    getGoal()
    function getGoal() {
        $.getJSON("/rest/goal", {year: currentYear, month: currentMonth}, function(goal) {
            $('#thisGoal').val(formatValue(goal[item] || 0));
        }).fail(function() {
            showAlert("#alertMain", "alert-danger", "Cannot find goal. Please refresh and retry.");
        });
    }

    getBenchmark();
    function getBenchmark() {
        $('#tbd').empty();
        $.getJSON("/rest/benchmarking", {item: item, year: currentYear, month: currentMonth}, function(rank) {
            var amount = 0, counter = 0;
            $.each(rank, function(key, value) {
                amount += value;
                counter++;
                value = formatValue(value);
                $('#tbd').append($('<tr/>').append(
                    $('<td>' + key + '</td>'),
                    $('<td>' + value + '</td>')
                ));
            });
            $('#avgRank').text(formatValue(amount/counter || 0));
        }).fail(function() {
            showAlert("#alertMain", "alert-danger", "Cannot find data. Please refresh and retry.");
        });
    }

    function formatValue(value) {
        if (item === 'salesRatio6') {
            return (value*100).toFixed(0)+'%';
        } else if (item === 'cmHandFlyerHours6' || item === 'cmOutGpHours6') {
            return value.toFixed(1);
        } else {
            return value.toFixed(0);
        }
    }
  });
});
