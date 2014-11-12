$(document).ready(function() {

$('#userId').html('<i class="fa fa-user"></i> 000000');
var clubId = location.search.split('=')[1];

var dt = new Date();
var yEnd = dt.getFullYear();
var mEnd = dt.getMonth();
dt.setMonth(mEnd - 5);
var yStart = dt.getFullYear();
var mStart = dt.getMonth();

// init date picker
$('#x1Date').val(yStart + '-' + (mStart+1));
$('#x1Date').datepicker({
    minViewMode: 1,
    autoclose: true,
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
    bezierCurve: false
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

getTrends();

$('#btnLoad').click(function() {
    getTrends();
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

function getTrends() {
console.log("---"+yStart+","+yEnd+",,,"+mStart+","+mEnd);
    var dt = new Date(yStart+'-'+(mStart+1));
    for (var i = 0; dt.getMonth() <= mEnd; i++) {
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
        alert("oops! I cannot find data, please refresh.");
    });
}

});
