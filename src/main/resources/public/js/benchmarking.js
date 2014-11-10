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
$('.input-group.date').val(thisYear+"-"+(thisMonth+1));
$('.input-group.date').datepicker({
    minViewMode: 1,
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
        if (data['CmPostFlyer'][0] == undefined) {
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
        alert("oops! I cannot find data, please refresh.");
    });
}

function makeSliderX(sliderId, sliderData, dataType) {
    $(sliderId).slider({
      orientation: "vertical",
      disabled: true,
      tooltip: 'always',
      min: sliderData[dataType][0],
      max: sliderData[dataType][1],
      value: sliderData[dataType][2]
    });
}

});
