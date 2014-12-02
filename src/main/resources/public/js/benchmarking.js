$(document).ready(function() {

// init user ID / club ID
$.getJSON("/rest/whoami", function(club) {
    $('#userId').html('<i class="fa fa-user"></i> '+club.clubId);
}).fail(function() {
    showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
});

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
    getBenchmarking();
});

getBenchmarking();

function getBenchmarking() {
    $.getJSON("/rest/benchmarking", {year: currentYear, month: currentMonth}, function(respData) {
        for (var key in respData) {
            if (respData.hasOwnProperty(key)) {
                var you = respData[key]['you'], max = respData[key]['max'], mid = respData[key]['mid'], min = respData[key]['min']
                    maxMid = mid + (max - mid) / 2, midMin = min + (mid - min) / 2;
                $('#'+key+'-1').text(formatValue(key, max));
                $('#'+key+'-5').text(formatValue(key, mid));
                $('#'+key+'-9').text(formatValue(key, min));
                if ( !you ) {
                    continue;
                }
                var $divYou;
                if (you === max) {
                    $divYou = $('#'+key+'-1');
                } else if (you === mid) {
                    $divYou = $('#'+key+'-5');
                } else if (you === min) {
                    $divYou = $('#'+key+'-9');
                } else if (you === maxMid) {
                    $divYou = $('#'+key+'-3');
                } else if (you === midMin) {
                    $divYou = $('#'+key+'-7');
                } else if (you > maxMid && you < max) {
                    $divYou = $('#'+key+'-2');
                } else if (you < maxMid && you > mid) {
                    $divYou = $('#'+key+'-4');
                } else if (you > midMin && you < mid) {
                    $divYou = $('#'+key+'-6');
                } else if (you < midMin && you > min) {
                    $divYou = $('#'+key+'-8');
                }
                $divYou.text(formatValue(key, you));
                $divYou.addClass('you');
                $('#'+key).text(respData[key]['rank'] + '/' + respData[key]['total']);
            }
        }
    }).fail(function() {
        showAlert("alert-danger", "Cannot find data, please refresh and retry.");
    });
}

function formatValue(item, value) {
    if (item === 'salesRatio6') {
        return (value*100).toFixed(0)+'%';
    } else if (item === 'cmHandFlyerHours6' || item === 'cmOutGpHours6') {
        return value.toFixed(1);
    } else {
        return value.toFixed(0);
    }
}

function showAlert(alertClass, msg) {
    $('.alert').removeClass('hide');
    $('.alert').addClass(alertClass);
    $('.alert').text(msg);
    setTimeout(function() {
        $('.alert').addClass('hide');
        $('.alert').removeClass(alertClass);
    }, 5000);
}

});
