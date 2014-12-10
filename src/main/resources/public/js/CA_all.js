$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');

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
    getCaAll();
});

$('#btnExport').click(function() {
    window.location = "rest/CaAll/CA_overall?caYear="+currentYear+"&caMonth="+currentMonth;
});

// init CA page
getCaAll();

function showAlert(alertClass, msg) {
    $('.alert').removeClass('hide');
    $('.alert').addClass(alertClass);
    $('.alert').text(msg);
    setTimeout(function() {
        $('.alert').addClass('hide');
        $('.alert').removeClass(alertClass);
    }, 5000);
}

function getCaAll() {
    $.getJSON("/rest/CaAll/stat", {caYear: currentYear, caMonth: currentMonth}, function(data) {
        for (var item in data) {
            if ( !data.hasOwnProperty(item) ) {
                continue;
            }
            $('#'+item).append('<td>'+formatValue(item, data[item].sum)+'</td>')
                        .append('<td>'+formatValue(item, data[item].avg)+'</td>')
                        .append('<td>'+formatValue(item, data[item].highest)+'</td>')
                        .append('<td>'+formatValue(item, data[item].lowest)+'</td>');
        }
    }).fail(function() {
        showAlert("alert-danger", "Cannot load data. Please refresh and retry.");
    });

    $.getJSON("/rest/CaAll", {caYear: currentYear, caMonth: currentMonth}, function(cas) {
        var clubsCount = 0;
        var $theader = $('#theader');
        for (var name in cas) {
            if ( !cas.hasOwnProperty(name) ) {
                continue;
            }
            $theader.append('<th style="width: 120px">'+name+'</th>');
            for (var item in cas[name]) {
                $('#'+item).append('<td>'+formatValue(item, cas[name][item])+'</td>');
            }
            clubsCount++;
        }
        var tblWidth = 680 + 320 + clubsCount * 120;
        $('table').css('width', tblWidth + 'px');
    }).fail(function() {
        showAlert("alert-danger", "Cannot load data. Please refresh and retry.");
    });
}

function formatValue(item, value) {
    if (item.indexOf('Ratio') != -1) {
        return (value*100).toFixed(0)+'%';
    } else {
        return Number(value).toFixed(0);
    }
}

});
