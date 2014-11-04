$(document).ready(function() {

$('#userId').text('000000');

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
        }
    ]
};
var barData = {
    labels: ["2014-4", "2014-5", "2014-6", "2014-7", "2014-8", "2014-9"],
    datasets: [
        {
            fillColor: "rgba(151,187,205,0.5)",
            strokeColor: "rgba(151,187,205,0.8)",
            highlightFill: "rgba(151,187,205,0.75)",
            highlightStroke: "rgba(151,187,205,1)",
        }
    ]
};

var ctxTotalRevenue = $("#totalRevenue").get(0).getContext("2d");
var chartTotalRevenue = new Chart(ctxTotalRevenue).Line(lineData);

var ctxNewSales = $("#newSales").get(0).getContext("2d");
var chartNewSales = new Chart(ctxNewSales).Bar(barData);

var ctxActiveMember = $("#activeMember").get(0).getContext("2d");
var chartActiveMember = new Chart(ctxActiveMember).Bar(barData);

var ctxBuddyReferral = $("#buddyReferral").get(0).getContext("2d");
var chartBuddyReferral = new Chart(ctxBuddyReferral).Line(lineData);

var ctxWorkouts = $("#workouts").get(0).getContext("2d");
var chartWorkouts = new Chart(ctxWorkouts).Line(lineData);

});
