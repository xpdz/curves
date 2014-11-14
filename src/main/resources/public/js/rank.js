$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');

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
    format: "yyyy-mm",
    language: "zh-TW",
    todayHighlight: true
});
$('.input-group.date').datepicker().on('changeDate', function(ev) {
    currentYear = ev.date.getFullYear();
    currentMonth = ev.date.getMonth();
    getRank();
});

getGoal();
getRank();

$('#btnSave').click(function() {
    var value = $('#thisGoal').val();

    $.post("/rest/goal", {year: currentYear, month: currentMonth, item: item, value: value})
        .done(function() {
            showAlert("alert-success", "Save successfully.");
        }).fail(function() {
            showAlert("alert-danger", "Save Fail. Please refresh and retry.");
        });
});

$('a').click(function() {
    item = $(this).attr('id');
    $('#itemTitle').text($(this).text());
    getGoal();
    getRank();
});

function showAlert(alertClass, msg) {
    $('.alert').removeClass('hide');
    $('.alert').addClass(alertClass);
    $('.alert').text(msg);
    setTimeout(function() {
        $('.alert').addClass('hide');
        $('.alert').removeClass(alertClass);
    }, 5000);
}

function getGoal() {
    $.getJSON("/rest/goal", {year: currentYear, month: currentMonth}, function(goal) {
        $('#thisGoal').val(formatValue(goal[item]));
    }).fail(function() {
        showAlert("alert-danger", "Cannot find goal. Please refresh and retry.");
    });
}

function getRank() {
    $('#tbd').empty();
    $.getJSON("/rest/rank", {item: item, year: currentYear, month: currentMonth}, function(rank) {
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
        $('#avgRank').text(formatValue(amount/counter));
    }).fail(function() {
        showAlert("alert-danger", "Cannot find data. Please refresh and retry.");
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
