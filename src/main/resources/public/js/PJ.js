$(document).ready(function() {

var weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
var $tbd = $("#tbd");
var pjSumId = -1;
var pjIds = [];

var today = new Date();
var thisYear = today.getFullYear();
var thisMonth = today.getMonth();

// init user ID / club ID
$.getJSON("/rest/whoami", function(club) {
    $('#clubId').text(club.clubId);
    $('#userId').text(club.clubId);
}).fail(function() {
    alert("oops! I cannot find your ID, please logout and login again.");
});

// init date picker
$('#datetimepicker').value = thisYear + '-' + thisMonth;
$('#datetimepicker').datetimepicker({
    language:  'zh-TW',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 3,
    minView: 3,
    forceParse: 0
});
$('#datetimepicker').datetimepicker().on('changeDate', function(ev) {
    var selYear = ev.date.getFullYear();
    var selMonth = ev.date.getMonth();
    getPJ(selYear, selMonth);
});

// init page
getPJ(thisYear, thisMonth);

function getPJ(y, m) {
    $tbd.empty();

    $.getJSON("/rest/PJ", {year: y, month: m}, function(pjSum) {
        $('#year').text(y);
        $('#month').text(m + 1);

        fillSheet(y, m, pjSum);
    }).fail(function() {
        $('#year').text(thisYear);
        $('#month').text(thisMonth + 1);

        fillThisMonth();
    });

    if (y == thisYear && m == thisMonth) {
        $("#btnSave").attr("disabled", false);
    } else {
        $("#btnSave").attr("disabled", true);
    }
}

function fillSheet(y, m, pjSum) {
    pjSumId = pjSum.id;
    $('#newSales').text(pjSum.newSales);
    $('#exits').text(pjSum.exits);
    $('#shiftIn').text(pjSum.shiftIn);
    $('#shiftOut').text(pjSum.shiftIn);
    $('#increment').text(pjSum.delta);
    $('#revenue').text(pjSum.revenue);
    $('#enrolled').text(pjSum.enrolled);
    $('#leaves').text(pjSum.leaves);
    $('#valids').text(pjSum.valids);
    $('#salesRatio').text(pjSum.salesRatio);
    $('#exitRatio').text(pjSum.exitRatio);
    $('#leaveRatio').text(pjSum.leaveRatio);
    for (var idx = 1; idx <= pjSum.pjSet.length; idx++) {
        var pj = pjSum.pjSet[idx-1];
        pjIds[idx] = pj.id;
        var d = new Date(pj.pjDate);
        $tbd.append(
          $('<tr/>').append(
            $('<td id="date-' + idx + '" style="background-color: #FFFF99">' + d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + '</td>'),
            $('<td>' + weekdays[d.getDay()] + '</td>'),
            $('<td>' + idx + '</td>'),
            $('<td id="workingDays-' + idx + '" contenteditable style="background-color: #FFFF99">' + pj.workingDays + '</td>'),
            $('<td id="workOuts-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.workOuts + '</td>'),
            $('<td id="newSalesRevenue-' + idx + '" contenteditable style="background-color: #FFFF99">' + pj.newSalesRevenue + '</td>'),
            $('<td id="duesDraftsRevenue-' + idx + '" contenteditable style="background-color: #FFFF99">' + pj.duesDraftsRevenue + '</td>'),
            $('<td id="productsRevenue-' + idx + '" contenteditable style="background-color: #FFFF99">' + pj.productsRevenue + '</td>'),
            $('<td id="otherRevenue-' + idx + '" contenteditable style="background-color: #FFFF99">' + pj.otherRevenue + '</td>'),
            $('<td id="incomingCalls-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.incomingCalls + '</td>'),
            $('<td id="incomingApo-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.incomingApo + '</td>'),
            $('<td id="outgoingCalls-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.outgoingCalls + '</td>'),
            $('<td id="outgoingApo-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.outgoingApo + '</td>'),
            $('<td id="brOwnRef-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brOwnRef + '</td>'),
            $('<td id="brOthersRef-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brOthersRef + '</td>'),
            $('<td id="brandedNewspaper-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brandedNewspaper + '</td>'),
            $('<td id="brandedTv-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brandedTv + '</td>'),
            $('<td id="brandedInternet-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brandedInternet + '</td>'),
            $('<td id="brandedSign-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brandedSign + '</td>'),
            $('<td id="brandedMate-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brandedMate + '</td>'),
            $('<td id="brandedOthers-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.brandedOthers + '</td>'),
            $('<td id="agpInDirectMail-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpInDirectMail + '</td>'),
            $('<td id="agpInMailFlyer-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpInMailFlyer + '</td>'),
            $('<td id="agpInHandFlyer-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpInHandFlyer + '</td>'),
            $('<td id="agpInCp-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpInCp + '</td>'),
            $('<td id="agpOutApoOut-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpOutApoOut + '</td>'),
            $('<td id="agpOutApoIn-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpOutApoIn + '</td>'),
            $('<td id="agpOutApoBlog-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpOutApoBlog + '</td>'),
            $('<td id="agpOutApoBag-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.agpOutApoBag + '</td>'),
            $('<td id="fa-' + idx + '" style="background-color: LightGray">' + pj.fa + '</td>'),
            $('<td id="enrollAch-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.enrollAch + '</td>'),
            $('<td id="enrollMonthly-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.enrollMonthly + '</td>'),
            $('<td id="enrollAllPrepay-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.enrollAllPrepay + '</td>'),
            $('<td id="exits-' + idx + '" contenteditable style="background-color: #CCFFFF">' + pj.exits + '</td>')
          )
        );
    }

    fillSummary(pjSum);
}

function fillThisMonth() {
    pjSum = {};
    pjSum.pjSet = [];
    var lastDayOfMonth = new Date(thisYear, thisMonth + 1, 0).getDate();
    for (var i = 1; i <= lastDayOfMonth; i++) {
        var d = new Date(thisYear, thisMonth, i);
        $tbd.append(
          $('<tr/>').append(
            $('<td id="date-' + i + '" style="background-color: #FFFF99">' + thisYear + '-' + (thisMonth + 1) + '-' + i + '</td>'),
            $('<td>' + weekdays[d.getDay()] + '</td>'),
            $('<td>' + i + '</td>'),
            $('<td id="workingDays-' + i + '" contenteditable style="background-color: #FFFF99"/>'),
            $('<td id="workOuts-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="newSalesRevenue-' + i + '" contenteditable style="background-color: #FFFF99"/>'),
            $('<td id="duesDraftsRevenue-' + i + '" contenteditable style="background-color: #FFFF99"/>'),
            $('<td id="productsRevenue-' + i + '" contenteditable style="background-color: #FFFF99"/>'),
            $('<td id="otherRevenue-' + i + '" contenteditable style="background-color: #FFFF99"/>'),
            $('<td id="incomingCalls-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="incomingApo-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="outgoingCalls-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="outgoingApo-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brOwnRef-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brOthersRef-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brandedNewspaper-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brandedTv-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brandedInternet-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brandedSign-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brandedMate-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="brandedOthers-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpInDirectMail-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpInMailFlyer-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpInHandFlyer-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpInCp-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpOutApoOut-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpOutApoIn-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpOutApoBlog-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="agpOutApoBag-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="fa-' + i + '" style="background-color: LightGray"/>'),
            $('<td id="enrollAch-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="enrollMonthly-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="enrollAllPrepay-' + i + '" contenteditable style="background-color: #CCFFFF"/>'),
            $('<td id="exits-' + i + '" contenteditable style="background-color: #CCFFFF"/>')
          )
        );
    }

    fillSummary(pjSum);
}

function fillSummary(pjSum) {
    // add summary row (background color - lightGray)
    $tbd.append(
      $('<tr style="background-color: LightGray"/>').append(
        $('<td/>'),$('<td/>'),$('<td/>'),
        $('<td>' + "営業日数" + '</td>'),
        $('<td>' + "最高人数" + '</td>'),
        $('<td/>'),$('<td/>'),$('<td/>'),$('<td/>'),$('<td/>'),$('<td/>'),$('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>'),
        $('<td/>')
      )
    );
    $tbd.append(
      $('<tr style="background-color: LightGray"/>').append(
        $('<td/>'),$('<td/>'),$('<td/>'),
        $('<td id="workingDays" >' + (pjSum.workingDays || 0) + '</td>'),
        $('<td id="maxWorkOuts" >' + (pjSum.maxWorkOuts || 0) + '</td>'),
        $('<td id="newSalesRevenue" >' + (pjSum.newSalesRevenue || 0) + '</td>'),
        $('<td id="duesDraftsRevenue" >' + (pjSum.duesDraftsRevenue || 0) + '</td>'),
        $('<td id="productsRevenue" >' + (pjSum.productsRevenue || 0) + '</td>'),
        $('<td id="otherRevenue" >' + (pjSum.otherRevenue || 0) + '</td>'),
        $('<td id="incomingCalls" >' + (pjSum.incomingCalls || 0) + '</td>'),
        $('<td id="incomingApo" >' + (pjSum.incomingApo || 0) + '</td>'),
        $('<td id="outgoingCalls" >' + (pjSum.outgoingCalls || 0) + '</td>'),
        $('<td id="outgoingApo" >' + (pjSum.outgoingApo || 0) + '</td>'),
        $('<td id="brOwnRef" >' + (pjSum.brOwnRef || 0) + '</td>'),
        $('<td id="brOthersRef" >' + (pjSum.brOthersRef || 0) + '</td>'),
        $('<td id="brandedNewspaper" >' + (pjSum.brandedNewspaper || 0) + '</td>'),
        $('<td id="brandedTv" >' + (pjSum.brandedTv || 0) + '</td>'),
        $('<td id="brandedInternet" >' + (pjSum.brandedInternet || 0) + '</td>'),
        $('<td id="brandedSign" >' + (pjSum.brandedSign || 0) + '</td>'),
        $('<td id="brandedMate" >' + (pjSum.brandedMate || 0) + '</td>'),
        $('<td id="brandedOthers" >' + (pjSum.brandedOthers || 0) + '</td>'),
        $('<td id="agpInDirectMail" >' + (pjSum.agpInDirectMail || 0) + '</td>'),
        $('<td id="agpInMailFlyer" >' + (pjSum.agpInMailFlyer || 0) + '</td>'),
        $('<td id="agpInHandFlyer" >' + (pjSum.agpInHandFlyer || 0) + '</td>'),
        $('<td id="agpInCp" >' + (pjSum.agpInCp || 0) + '</td>'),
        $('<td id="agpOutApoOut" >' + (pjSum.agpOutApoOut || 0) + '</td>'),
        $('<td id="agpOutApoIn" >' + (pjSum.agpOutApoIn || 0) + '</td>'),
        $('<td id="agpOutApoBlog" >' + (pjSum.agpOutApoBlog || 0) + '</td>'),
        $('<td id="agpOutApoBag" >' + (pjSum.agpOutApoBag || 0) + '</td>'),
        $('<td id="faSum" >' + (pjSum.faSum || 0) + '</td>'),
        $('<td id="enrollAch" >' + (pjSum.enrollAch || 0) + '</td>'),
        $('<td id="enrollMonthly" >' + (pjSum.enrollMonthly || 0) + '</td>'),
        $('<td id="enrollAllPrepay" >' + (pjSum.enrollAllPrepay || 0) + '</td>'),
        $('<td id="exits" >' + (pjSum.exits || 0) + '</td>')
      )
    );
}

// save PJ
$("#btnSave").click(function() {
    var lastDayOfMonth = new Date(thisYear, thisMonth + 1, 0).getDate();

    runFormula(lastDayOfMonth);

    var pjSum = {};
    if (pjSumId != -1) {
        pjSum.id = pjSumId;
    }

    pjSum.clubId = +$('#clubId').text();
    pjSum.year = +$('#year').text();
    pjSum.month = +$('#month').text() - 1;
    pjSum.newSales = +$('#newSales').text();
    pjSum.exits = +$('#exits').text();
    pjSum.shiftIn = +$('#shiftIn').text();
    pjSum.shiftOut = +$('#shiftOut').text();
    pjSum.increment = +$('#increment').text();
    pjSum.revenue = +$('#revenue').text();
    pjSum.enrolled = +$('#enrolled').text();
    pjSum.leaves = +$('#leaves').text();
    pjSum.valids = +$('#valids').text();
    pjSum.salesRatio = $('#salesRatio').text();
    pjSum.exitRatio = $('#exitRatio').text();
    pjSum.leaveRatio = $('#leaveRatio').text();

    pjSum.workingDays = +$('#workingDays').text();
    pjSum.maxWorkOuts = +$('#maxWorkOuts').text();
    pjSum.newSalesRevenue = +$('#newSalesRevenue').text();
    pjSum.duesDraftsRevenue = +$('#duesDraftsRevenue').text();
    pjSum.productsRevenue = +$('#productsRevenue').text();
    pjSum.otherRevenue = +$('#otherRevenue').text();
    pjSum.incomingCalls = +$('#incomingCalls').text();
    pjSum.incomingApo = +$('#incomingApo').text();
    pjSum.outgoingCalls = +$('#outgoingCalls').text();
    pjSum.outgoingApo = +$('#outgoingApo').text();
    pjSum.brOwnRef = +$('#brOwnRef').text();
    pjSum.brOthersRef = +$('#brOthersRef').text();
    pjSum.brandedNewspaper = +$('#brandedNewspaper').text();
    pjSum.brandedTv = +$('#brandedTv').text();
    pjSum.brandedInternet = +$('#brandedInternet').text();
    pjSum.brandedSign = +$('#brandedSign').text();
    pjSum.brandedMate = +$('#brandedMate').text();
    pjSum.brandedOthers = +$('#brandedOthers').text();
    pjSum.agpInDirectMail = +$('#agpInDirectMail').text();
    pjSum.agpInMailFlyer = +$('#agpInMailFlyer').text();
    pjSum.agpInHandFlyer = +$('#agpInHandFlyer').text();
    pjSum.agpInCp = +$('#agpInCp').text();
    pjSum.agpOutApoOut = +$('#agpOutApoOut').text();
    pjSum.agpOutApoIn = +$('#agpOutApoIn').text();
    pjSum.agpOutApoBlog = +$('#agpOutApoBlog').text();
    pjSum.agpOutApoBag = +$('#agpOutApoBag').text();
    pjSum.faSum = +$('#faSum').text();
    pjSum.enrollAch = +$('#enrollAch').text();
    pjSum.enrollMonthly = +$('#enrollMonthly').text();
    pjSum.enrollAllPrepay = +$('#enrollAllPrepay').text();
    pjSum.exits = +$('#exits').text();

    pjSum.pjSet = [];
    for (var idx = 1; idx <= lastDayOfMonth; idx++) {
        var pj = {};
        if (pjSumId != -1) {
            pj.id = pjIds[idx];
        }
        pj.pjDate = new Date(thisYear, thisMonth, idx);
        pj.workingDays = +$('#workingDays-' + idx).text();
        pj.workOuts = +$('#workOuts-' + idx).text();
        pj.newSalesRevenue = +$('#newSalesRevenue-' + idx).text();
        pj.duesDraftsRevenue = +$('#duesDraftsRevenue-' + idx).text();
        pj.productsRevenue = +$('#productsRevenue-' + idx).text();
        pj.otherRevenue = +$('#otherRevenue-' + idx).text();
        pj.incomingCalls = +$('#incomingCalls-' + idx).text();
        pj.incomingApo = +$('#incomingApo-' + idx).text();
        pj.outgoingCalls = +$('#outgoingCalls-' + idx).text();
        pj.outgoingApo = +$('#outgoingApo-' + idx).text();
        pj.brOwnRef = +$('#brOwnRef-' + idx).text();
        pj.brOthersRef = +$('#brOthersRef-' + idx).text();
        pj.brandedNewspaper = +$('#brandedNewspaper-' + idx).text();
        pj.brandedTv = +$('#brandedTv-' + idx).text();
        pj.brandedInternet = +$('#brandedInternet-' + idx).text();
        pj.brandedSign = +$('#brandedSign-' + idx).text();
        pj.brandedMate = +$('#brandedMate-' + idx).text();
        pj.brandedOthers = +$('#brandedOthers-' + idx).text();
        pj.agpInDirectMail = +$('#agpInDirectMail-' + idx).text();
        pj.agpInMailFlyer = +$('#agpInMailFlyer-' + idx).text();
        pj.agpInHandFlyer = +$('#agpInHandFlyer-' + idx).text();
        pj.agpInCp = +$('#agpInCp-' + idx).text();
        pj.agpOutApoOut = +$('#agpOutApoOut-' + idx).text();
        pj.agpOutApoIn = +$('#agpOutApoIn-' + idx).text();
        pj.agpOutApoBlog = +$('#agpOutApoBlog-' + idx).text();
        pj.agpOutApoBag = +$('#agpOutApoBag-' + idx).text();
        pj.fa = +$('#fa-' + idx).text();
        pj.enrollAch = +$('#enrollAch-' + idx).text();
        pj.enrollMonthly = +$('#enrollMonthly-' + idx).text();
        pj.enrollAllPrepay = +$('#enrollAllPrepay-' + idx).text();
        pj.exits = +$('#exits-' + idx).text();

        pjSum.pjSet.push(pj);
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "/rest/PJ",
        'data': JSON.stringify(pjSum),
        'dataType': 'json'
    }).done(function() {
        alert("Save successfully.");
    }).fail(function() {
        alert("oops! Save failed, please try again.");
    });
});

function runFormula(lastDayOfMonth) {
    var workingDays = 0, workOuts = 0, newSalesRevenue = 0, duesDraftsRevenue = 0, productsRevenue = 0, otherRevenue = 0, incomingCalls = 0, incomingApo = 0,
        outgoingCalls = 0, outgoingApo = 0, brOwnRef = 0, brOthersRef = 0, brandedNewspaper = 0, brandedTv = 0, brandedInternet = 0, brandedSign = 0,
        brandedMate = 0, brandedOthers = 0, agpInDirectMail = 0, agpInMailFlyer = 0, agpInHandFlyer = 0, agpInCp = 0, agpOutApoOut = 0, agpOutApoIn = 0,
        agpOutApoBlog = 0, agpOutApoBag = 0, faSum = 0, enrollAch = 0, enrollMonthly = 0, enrollAllPrepay = 0, exits = 0;

    for (var idx = 1; idx <= lastDayOfMonth; idx++) {
        var xFa = Number($('#brOwnRef-' + idx).text()) + Number($('#brOthersRef-' + idx).text()) +
            Number($('#brandedNewspaper-' + idx).text()) + Number($('#brandedTv-' + idx).text()) +
            Number($('#brandedInternet-' + idx).text()) +  Number($('#brandedSign-' + idx).text()) +
            Number($('#brandedMate-' + idx).text()) +  Number($('#brandedOthers-' + idx).text()) +
            Number($('#agpInDirectMail-' + idx).text()) + Number($('#agpInMailFlyer-' + idx).text()) +
            Number($('#agpInHandFlyer-' + idx).text()) +  Number($('#agpInCp-' + idx).text()) +
            Number($('#agpOutApoOut-' + idx).text()) +  Number($('#agpOutApoIn-' + idx).text()) +
            Number($('#agpOutApoBlog-' + idx).text()) +  Number($('#agpOutApoBag-' + idx).text());
        $('#fa-' + idx).text(xFa);
        faSum += xFa;
        workingDays = Number($('#workingDays-' + idx).text()) > workingDays ? Number($('#workingDays-' + idx).text()) : workingDays;
        workOuts = Number($('#workOuts-' + idx).text()) > workOuts ? Number($('#workOuts-' + idx).text()) : workOuts;
        newSalesRevenue += Number($('#newSalesRevenue-' + idx).text());
        duesDraftsRevenue += Number($('#duesDraftsRevenue-' + idx).text());
        productsRevenue += Number($('#productsRevenue-' + idx).text());
        otherRevenue += Number($('#otherRevenue-' + idx).text());
        incomingCalls += Number($('#incomingCalls-' + idx).text());
        incomingApo += Number($('#incomingApo-' + idx).text());
        outgoingCalls += Number($('#outgoingCalls-' + idx).text());
        outgoingApo += Number($('#outgoingApo-' + idx).text());
        brOwnRef += Number($('#brOwnRef').text());
        brOthersRef += Number($('#brOthersRef').text());
        brandedNewspaper += Number($('#brandedNewspaper').text());
        brandedTv += Number($('#brandedTv').text());
        brandedInternet += Number($('#brandedInternet').text());
        brandedSign += Number($('#brandedSign').text());
        brandedMate += Number($('#brandedMate').text());
        brandedOthers += Number($('#brandedOthers').text());
        agpInDirectMail += Number($('#agpInDirectMail').text());
        agpInMailFlyer += Number($('#agpInMailFlyer').text());
        agpInHandFlyer += Number($('#agpInHandFlyer').text());
        agpInCp += Number($('#agpInCp').text());
        agpOutApoOut += Number($('#agpOutApoOut').text());
        agpOutApoIn += Number($('#agpOutApoIn').text());
        agpOutApoBlog += Number($('#agpOutApoBlog').text());
        agpOutApoBag += Number($('#agpOutApoBag').text());
        enrollAch += Number($('#enrollAch-' + idx).text());
        enrollMonthly += Number($('#enrollMonthly-' + idx).text());
        enrollAllPrepay += Number($('#enrollAllPrepay-' + idx).text());
        exits += Number($('#exits-' + idx).text());
    }

    $('#workingDays').text(workingDays);
    $('#maxWorkOuts').text(workOuts);
    $('#newSalesRevenue').text(newSalesRevenue);
    $('#duesDraftsRevenue').text(duesDraftsRevenue);
    $('#productsRevenue').text(productsRevenue);
    $('#otherRevenue').text(otherRevenue);
    $('#incomingCalls').text(incomingCalls);
    $('#incomingApo').text(incomingApo);
    $('#outgoingCalls').text(outgoingCalls);
    $('#outgoingApo').text(outgoingApo);
    $('#brOwnRef').text(brOwnRef);
    $('#brOthersRef').text(brOthersRef);
    $('#brandedNewspaper').text(brandedNewspaper);
    $('#brandedTv').text(brandedTv);
    $('#brandedInternet').text(brandedInternet);
    $('#brandedSign').text(brandedSign);
    $('#brandedMate').text(brandedMate);
    $('#brandedOthers').text(brandedOthers);
    $('#agpInDirectMail').text(agpInDirectMail);
    $('#agpInMailFlyer').text(agpInMailFlyer);
    $('#agpInHandFlyer').text(agpInHandFlyer);
    $('#agpInCp').text(agpInCp);
    $('#agpOutApoOut').text(agpOutApoOut);
    $('#agpOutApoIn').text(agpOutApoIn);
    $('#agpOutApoBlog').text(agpOutApoBlog);
    $('#agpOutApoBag').text(agpOutApoBag);
    $('#faSum').text(faSum);
    $('#enrollAch').text(enrollAch);
    $('#enrollMonthly').text(enrollMonthly);
    $('#enrollAllPrepay').text(enrollAllPrepay);
    $('#exits').text(exits);

    var newSales = enrollAch + enrollMonthly + enrollAllPrepay;
    $('#newSales').text(newSales);
    $('#exits').text(exits);
    $('#increment').text(newSales-exits+Number($('#shiftIn').text())-Number($('#shiftOut').text()));
    $('#revenue').text(newSalesRevenue+duesDraftsRevenue+productsRevenue+otherRevenue);
    if (faSum != 0) {
        $('#salesRatio').text(Math.round(newSales/faSum*100) + "%");
    }

    var enrolled = Number($('#enrolled').text());
    if (enrolled != 0) {
        $('#exitRatio').text(Math.round(exits/enrolled*100) + "%");
        var leaves = Number($('#leaves').text());
        $('#leaveRatio').text(Math.round(leaves/enrolled*100) + "%");
    }
}

});
