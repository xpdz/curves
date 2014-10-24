$(document).ready(function() {

var weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
var $tbd = $("#tbd");

var today = new Date();
var y = today.getFullYear();
var m = today.getMonth() + 1;

getPJ(y, m);

//$("#lastMonth").click(function(){
//    var d = new Date(y, today.setMonth(m-1));
//    getPJ(d.getFullYear(), d.getMonth());
//});
//
//$("#nextMonth").click(function(){
//    var d = new Date(y, today.setMonth(m+1));
//    getPJ(d.getFullYear(), d.getMonth());
//});

$('#datetimepicker').value = y + '-' + m;
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
$('#datetimepicker').datetimepicker().on('changeDate', function(ev){
    var selYear = ev.date.getFullYear();
    var selMonth = ev.date.getMonth() + 1;
    console.log("---"+selYear+"-"+selMonth);
    getPJ(selYear, selMonth);
});

$("#save").click(function(){
    runFormula();
    var pjSum = {};
    pjSum.clubId = $('#clubId').text();
    pjSum.year = $('#year').text();
    pjSum.month = $('#month').text();
    pjSum.newSales = $('#newSales').text();
    pjSum.exits = $('#exits').text();
    pjSum.shiftIn = $('#shiftIn').text();
    pjSum.shiftOut = $('#shiftOut').text();
    pjSum.increment = $('#increment').text();
    pjSum.revenue = $('#revenue').text();
    pjSum.enrolled = $('#enrolled').text();
    pjSum.leave = $('#leave').text();
    pjSum.valid = $('#valid').text();
    pjSum.salesRatio = $('#salesRatio').text();
    pjSum.exitRatio = $('#exitRatio').text();
    pjSum.leaveRatio = $('#leaveRatio').text();

    pjSum.workingDays = $('#workingDays').text();
    pjSum.maxWorkOuts = $('#maxWorkOuts').text();
    pjSum.newSalesRevenue = $('#newSalesRevenue').text();
    pjSum.duesDraftsRevenue = $('#duesDraftsRevenue').text();
    pjSum.productsRevenue = $('#productsRevenue').text();
    pjSum.otherRevenue = $('#otherRevenue').text();
    pjSum.incomingCalls = $('#incomingCalls').text();
    pjSum.incomingApo = $('#incomingApo').text();
    pjSum.outgoingCalls = $('#outgoingCalls').text();
    pjSum.outgoingApo = $('#outgoingApo').text();
    pjSum.brOwnRef = $('#brOwnRef').text();
    pjSum.brOthersRef = $('#brOthersRef').text();
    pjSum.brandedNewspaper = $('#brandedNewspaper').text();
    pjSum.brandedTv = $('#brandedTv').text();
    pjSum.brandedInternet = $('#brandedInternet').text();
    pjSum.brandedSign = $('#brandedSign').text();
    pjSum.brandedMate = $('#brandedMate').text();
    pjSum.brandedOthers = $('#brandedOthers').text();
    pjSum.agpInDirectMail = $('#agpInDirectMail').text();
    pjSum.agpInMailFlyer = $('#agpInMailFlyer').text();
    pjSum.agpInHandFlyer = $('#agpInHandFlyer').text();
    pjSum.agpInCp = $('#agpInCp').text();
    pjSum.agpOutApoOut = $('#agpOutApoOut').text();
    pjSum.agpOutApoIn = $('#agpOutApoIn').text();
    pjSum.agpOutApoBlog = $('#agpOutApoBlog').text();
    pjSum.agpOutApoBag = $('#agpOutApoBag').text();
    pjSum.faSum = $('#faSum').text();
    pjSum.enrollAch = $('#enrollAch').text();
    pjSum.enrollMonthly = $('#enrollMonthly').text();
    pjSum.enrollAllPrepay = $('#enrollAllPrepay').text();
    pjSum.exits = $('#exits').text();

    pjSum.pjSet = [];
    $('#tbd').children().each(function(idx) {
        var pj = {};
        pj.date = $('#date-' + idx).text();
        pj.workingDays = $('#workingDays-' + idx).text();
        pj.workOuts = $('#workOuts-' + idx).text();
        pj.newSalesRevenue = $('#newSalesRevenue-' + idx).text();
        pj.duesDraftsRevenue = $('#duesDraftsRevenue-' + idx).text();
        pj.productsRevenue = $('#productsRevenue-' + idx).text();
        pj.otherRevenue = $('#otherRevenue-' + idx).text();
        pj.incomingCalls = $('#incomingCalls-' + idx).text();
        pj.incomingApo = $('#incomingApo-' + idx).text();
        pj.outgoingCalls = $('#outgoingCalls-' + idx).text();
        pj.outgoingApo = $('#outgoingApo-' + idx).text();
        pj.brOwnRef = $('#brOwnRef-' + idx).text();
        pj.brOthersRef = $('#brOthersRef-' + idx).text();
        pj.brandedNewspaper = $('#brandedNewspaper-' + idx).text();
        pj.brandedTv = $('#brandedTv-' + idx).text();
        pj.brandedInternet = $('#brandedInternet-' + idx).text();
        pj.brandedSign = $('#brandedSign-' + idx).text();
        pj.brandedMate = $('#brandedMate-' + idx).text();
        pj.brandedOthers = $('#brandedOthers-' + idx).text();
        pj.agpInDirectMail = $('#agpInDirectMail-' + idx).text();
        pj.agpInMailFlyer = $('#agpInMailFlyer-' + idx).text();
        pj.agpInHandFlyer = $('#agpInHandFlyer-' + idx).text();
        pj.agpInCp = $('#agpInCp-' + idx).text();
        pj.agpOutApoOut = $('#agpOutApoOut-' + idx).text();
        pj.agpOutApoIn = $('#agpOutApoIn-' + idx).text();
        pj.agpOutApoBlog = $('#agpOutApoBlog-' + idx).text();
        pj.agpOutApoBag = $('#agpOutApoBag-' + idx).text();
        pj.fa = $('#fa-' + idx).text();
        pj.enrollAch = $('#enrollAch-' + idx).text();
        pj.enrollMonthly = $('#enrollMonthly-' + idx).text();
        pj.enrollAllPrepay = $('#enrollAllPrepay-' + idx).text();
        pj.exits = $('#exits-' + idx).text();

        pjSum.pjSet.push(pj);
    });

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "/rest/PJ",
        'data': JSON.stringify(pjSum),
        'dataType': 'json'
    });
});

function runFormula() {
}

function getPJ(y, m) {
    $('#tbd').empty();
    $.getJSON( "/rest/PJ", {year: y, month: m}, function( pjSum ) {
        $('#userid').text(pjSum.clubId);
        $('#clubId').text(pjSum.clubId);
        $('#year').text(y);
        $('#month').text(m);
        $('#newSales').text(pjSum.newSales);
        $('#exits').text(pjSum.exits);
        $('#shiftIn').text(pjSum.transferIn);
        $('#shiftOut').text(pjSum.transferOut);
        $('#increment').text(pjSum.delta);
        $('#revenue').text(pjSum.revenue);
        $('#enrolled').text(pjSum.regMember);
        $('#leave').text(pjSum.leave);
        $('#valid').text(pjSum.valid);
        $('#salesRatio').text(pjSum.salesRatio);
        $('#exitRatio').text(pjSum.exitRatio);
        $('#leaveRatio').text(pjSum.leaveRatio);

        $.each(pjSum.pjSet, function(idx, pj) {
            var d = new Date(pj.date);
            $tbd.append(
              $('<tr/>').append(
                $('<td id="date-' + idx + '" style="background-color: #FFFF99">' + y + '-' + m + '-' + d.getDate() + '</td>'),
                $('<td>' + weekdays[d.getDay()] + '</td>'),
                $('<td>' + (idx+1) + '</td>'),
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
        });

        // add remained days
        var lastDayOfMonth = new Date(y, m, 0).getDate();
        for (var i = pjSum.pjSet.length + 1; i <= lastDayOfMonth; i++) {
            var d = new Date(y, m, i);
            $tbd.append(
              $('<tr/>').append(
                $('<td id="date-' + i + '" style="background-color: #FFFF99">' + y + '-' + m + '-' + i + '</td>'),
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
            $('<td id="workingDays">' + pjSum.workingDays + '</td>'),
            $('<td id="maxWorkOuts">' + pjSum.maxWorkOuts + '</td>'),
            $('<td id="newSalesRevenue">' + pjSum.newSalesRevenue + '</td>'),
            $('<td id="duesDraftsRevenue">' + pjSum.duesDraftsRevenue + '</td>'),
            $('<td id="productsRevenue">' + pjSum.productsRevenue + '</td>'),
            $('<td id="otherRevenue">' + pjSum.otherRevenue + '</td>'),
            $('<td id="incomingCalls">' + pjSum.incomingCalls + '</td>'),
            $('<td id="incomingApo">' + pjSum.incomingApo + '</td>'),
            $('<td id="outgoingCalls">' + pjSum.outgoingCalls + '</td>'),
            $('<td id="outgoingApo">' + pjSum.outgoingApo + '</td>'),
            $('<td id="brOwnRef">' + pjSum.brOwnRef + '</td>'),
            $('<td id="brOthersRef">' + pjSum.brOthersRef + '</td>'),
            $('<td id="brandedNewspaper">' + pjSum.brandedNewspaper + '</td>'),
            $('<td id="brandedTv">' + pjSum.brandedTv + '</td>'),
            $('<td id="brandedInternet">' + pjSum.brandedInternet + '</td>'),
            $('<td id="brandedSign">' + pjSum.brandedSign + '</td>'),
            $('<td id="brandedMate">' + pjSum.brandedMate + '</td>'),
            $('<td id="brandedOthers">' + pjSum.brandedOthers + '</td>'),
            $('<td id="agpInDirectMail">' + pjSum.agpInDirectMail + '</td>'),
            $('<td id="agpInMailFlyer">' + pjSum.agpInMailFlyer + '</td>'),
            $('<td id="agpInHandFlyer">' + pjSum.agpInHandFlyer + '</td>'),
            $('<td id="agpInCp">' + pjSum.agpInCp + '</td>'),
            $('<td id="agpOutApoOut">' + pjSum.agpOutApoOut + '</td>'),
            $('<td id="agpOutApoIn">' + pjSum.agpOutApoIn + '</td>'),
            $('<td id="agpOutApoBlog">' + pjSum.agpOutApoBlog + '</td>'),
            $('<td id="agpOutApoBag">' + pjSum.agpOutApoBag + '</td>'),
            $('<td id="faSum">' + pjSum.faSum + '</td>'),
            $('<td id="enrollAch">' + pjSum.enrollAch + '</td>'),
            $('<td id="enrollMonthly">' + pjSum.enrollMonthly + '</td>'),
            $('<td id="enrollAllPrepay">' + pjSum.enrollAllPrepay + '</td>'),
            $('<td id="exits">' + pjSum.exits + '</td>')
          )
        );
    }); // getJSON
}

}); // document.ready
