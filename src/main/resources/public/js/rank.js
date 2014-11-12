$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');

var item = 'cmPostFlyer6';

var today = new Date();
var thisYear = today.getFullYear();
var thisMonth = today.getMonth();
var currentYear = thisYear;
var currentMonth = thisMonth;

// init date picker
$('.input-group.date').val(thisYear+"-"+(thisMonth+1));
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

getRank();

$('#btnSave').click(function() {
    var value = $('#thisGoal').val();

    $.post("/rest/goal", {year: currentYear, month: currentMonth, item: item, value: value})
        .done(function() {
            $('.alert').removeClass('alert-danger');
            $('.alert').addClass('alert-success');
            $('.alert').text("Save successfully.");
        }).fail(function() {
            $('.alert').removeClass('alert-success');
            $('.alert').addClass('alert-danger');
            $('.alert').text("Save Fail. Please refresh and retry.");
        });
});

$('a').click(function() {
    item = $(this).attr('id');
    $('#itemTitle').text($(this).text());
    getGoal();
    getRank();
});

function getGoal() {
    $.getJSON("/rest/goal", {year: currentYear, month: currentMonth}, function(goal) {
        if (goal && goal[item]) {
            $('#thisGoal').val(formatValue(goal[item]));
        }
    }).fail(function() {
        alert("oops! I cannot find goal, please try again.");
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
        alert("oops! I cannot find clubs, please try again.");
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
