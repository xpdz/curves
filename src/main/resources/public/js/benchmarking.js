$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');

var clubId = location.search.split('=')[1];

var today = new Date();
var thisYear = today.getFullYear();
var thisMonth = today.getMonth();
var currentYear = thisYear;
var currentMonth = thisMonth;

getBenchmarking();

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

function getBenchmarking() {
    $.getJSON("/rest/benchmarking", {clubId: clubId, year: currentYear, month: currentMonth}, function(data) {
        if (data.CmPostFlyer.val == undefined) {
            return;
        }

        makeSliderX('#CmPostFlyer', data, 'CmPostFlyer');
        makeSliderX('#CmHandFlyer', data, 'CmHandFlyer');
        makeSliderX('#CmHandFlyerHours', data, 'CmHandFlyerHours');
        makeSliderX('#CmOutGp', data, 'CmOutGp');
        makeSliderX('#CmOutGpHours', data, 'CmOutGpHours');
        makeSliderX('#CmCpBox', data, 'CmCpBox');
        makeSliderX('#CmApoTotal', data, 'CmApoTotal');
        makeSliderX('#CmFaSum', data, 'CmFaSum');
        makeSliderX('#SalesAch', data, 'SalesAch');
        makeSliderX('#SalesRatio', data, 'SalesRatio');
    }).fail(function() {
        showAlert("alert-danger", "Cannot find data, please refresh and retry.");
    });
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

function makeSliderX(sliderId, sliderData, dataType) {
    $(sliderId).slider({
      orientation: "vertical",
      tooltip: 'always',
      min: sliderData[dataType]['max'],
      max: sliderData[dataType]['min'],
      value: sliderData[dataType]['val']
    });
}

});
