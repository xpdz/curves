$(document).ready(function() {

$('#userId').text('000000');

getClubs();

var clubId;

function getClubs() {
    $.getJSON("/rest/clubs", function(clubs) {
        for (var i = 0; i < clubs.length; i++) {
            $('#tbd').append($('<tr/>').append(
                $('<td>' + clubs[i].clubId + '</td>'),
                $('<td>' + clubs[i].name + '</td>'),
                $('<td>' + clubs[i].owner + '</td>'),
                $('<td>' + clubs[i].manager + '</td>'),
                $('<td><button type="button" class="btn btn-primary" clubId4Trend="'+clubs[i].clubId+'">Trends</button></td>'),
                $('<td><button type="button" class="btn btn-primary" clubId4Benchmarking="'+clubs[i].clubId+'">Benchmarking</button></td>')
            ));
        }
        $("button[clubId4Trend]").click(function(event) {
            clubId = $(this).attr('clubId4Trend');
            $('#showTrends').modal({});
        });
        $("button[clubId4Benchmarking]").click(function(event) {
            var clubId = $(this).attr('clubId4Benchmarking');
            $('#showBenchmarking').modal({});
            getBenchmarking(clubId);
        });
    }).fail(function() {
        alert("oops! I cannot find clubs, please try again.");
    });
}

$("button[trendType]").click(function(event) {
    $('#trendTitle').text($(this).text());
    getTrends($(this).attr('trendType'));
});

var lineData = {
    labels: ["2014-4", "2014-5", "2014-6", "2014-7", "2014-8", "2014-9"],
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

function getTrends(myType) {
    $.getJSON("/rest/trends", {clubId: clubId, trendType: myType}, function(resData) {
        lineData.datasets[0].data = resData;
        var dt = new Date();
        var m = dt.getMonth();
        dt.setMonth(m - 6);
        lineData.labels[0] = dt.getFullYear()+'-'+(dt.getMonth()+1);
        dt.setMonth(m - 5);
        lineData.labels[1] = dt.getFullYear()+'-'+(dt.getMonth()+1);
        dt.setMonth(m - 4);
        lineData.labels[2] = dt.getFullYear()+'-'+(dt.getMonth()+1);
        dt.setMonth(m - 3);
        lineData.labels[3] = dt.getFullYear()+'-'+(dt.getMonth()+1);
        dt.setMonth(m - 2);
        lineData.labels[4] = dt.getFullYear()+'-'+(dt.getMonth()+1);
        dt.setMonth(m - 1);
        lineData.labels[5] = dt.getFullYear()+'-'+(dt.getMonth()+1);

        var ctxTotalRevenue = $("#chartTrends").get(0).getContext("2d");
        var chartTotalRevenue = new Chart(ctxTotalRevenue).Line(lineData);
    }).fail(function() {
        alert("oops! I cannot find data, please refresh.");
    });
}

function getBenchmarking(clubId) {
    $.getJSON("/rest/benchmarking", {clubId: clubId}, function(data) {
console.log(data);
        $( "#rankTM" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['TM'][0],
          max: data['TM'][1],
          value: data['TM'][2]
        });
        $( "#rankTAM" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['TAM'][0],
          max: data['TAM'][1],
          value: data['TAM'][2]
        });
        $( "#rankHoldRatio" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['HoldRatio'][0],
          max: data['HoldRatio'][1],
          value: data['HoldRatio'][2]
        });
        $( "#rankTotalWo" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['TotalWo'][0],
          max: data['TotalWo'][1],
          value: data['TotalWo'][2]
        });
        $( "#rankExitsRatio" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['ExitsRatio'][0],
          max: data['ExitsRatio'][1],
          value: data['ExitsRatio'][2]
        });
        $( "#rankActive12" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['Active12'][0],
          max: data['Active12'][1],
          value: data['Active12'][2]
        });
        $( "#rankPostFlyer" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['PostFlyer'][0],
          max: data['PostFlyer'][1],
          value: data['PostFlyer'][2]
        });
        $( "#rankHandFlyer" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['HandFlyer'][0],
          max: data['HandFlyer'][1],
          value: data['HandFlyer'][2]
        });
        $( "#rankOutGp" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['OutGp'][0],
          max: data['OutGp'][1],
          value: data['OutGp'][2]
        });
        $( "#rankOwnRefs" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['OwnRefs'][0],
          max: data['OwnRefs'][1],
          value: data['OwnRefs'][2]
        });
        $( "#rankFaSum" ).slider({
          orientation: "vertical",
          disabled: true,
          min: data['FaSum'][0],
          max: data['FaSum'][1],
          value: data['FaSum'][2]
        });
    }).fail(function() {
        alert("oops! I cannot find data, please refresh.");
    });
}

});
