$(document).ready(function() {

var weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
var $tbd = $("#tbd");

var today = new Date();
var thisYear = today.getFullYear(), thisMonth = today.getMonth(),
    currentYear = thisYear, currentMonth = thisMonth;
var lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

// init user ID / club ID
$.getJSON("/rest/whoami", function(club) {
    $('#clubId').text(club.clubId);
    $('#userId').html('<i class="fa fa-user"></i> '+club.clubId);
}).fail(function() {
    showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
});

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
    getPJ();
});

// init page
getPJ();

function showAlert(alertClass, msg) {
    $('.alert').removeClass('hide');
    $('.alert').addClass(alertClass);
    $('.alert').text(msg);
    setTimeout(function() {
        $('.alert').addClass('hide');
        $('.alert').removeClass(alertClass);
    }, 5000);
}

function getPJ() {
    $tbd.empty();

    $('#year').text(currentYear);
    $('#month').text(currentMonth + 1);
    $.getJSON("/rest/PJ", {year: currentYear, month: currentMonth}, function(pjSum) {
        fillSheet(pjSum);
    }).fail(function() {
        showAlert("alert-danger", "Cannot load data. Please refresh and retry.");
    });
}

function fillSheet(pjSum) {
    $('#newSales').text(pjSum.newSales);
    $('#exits').text(pjSum.exits);
    $('#shiftIn').text(pjSum.shiftIn);
    $('#shiftOut').text(pjSum.shiftIn);
    $('#increment').text(pjSum.increment);
    $('#revenue').text(pjSum.revenue);
    $('#enrolled').text(pjSum.enrolled);
    $('#leaves').text(pjSum.leaves);
    $('#valids').text(pjSum.valids);
    $('#salesRatio').text((pjSum.salesRatio*100).toFixed(0)+'%');
    $('#exitRatio').text((pjSum.exitRatio*100).toFixed(1)+'%');
    $('#leaveRatio').text((pjSum.leaveRatio*100).toFixed(1)+'%');
    for (var idx = 1; idx <= lastDayOfMonth; idx++) {
        var pj = pjSum.pjSet[idx-1];
        if ( !pj ) {
            pj = {};
        }
        var pjDay = currentYear + '-' + (currentMonth + 1) + '-' + idx;
        var d = new Date(pjDay);
        $tbd.append(
          $('<tr/>').append(
            $('<td id="date-' + idx + '" style="background-color: #FFFF99">' + pjDay + '</td>'),
            $('<td>' + weekdays[d.getDay()] + '</td>'),
            $('<td>' + idx + '</td>'),
            $('<td id="workingDays-' + idx + '" contenteditable="true" style="background-color: #FFFF99" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.workingDays + '</td>'),
            $('<td id="workOuts-' + idx + '" contenteditable="true" style="background-color: #CCFFFF" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.workOuts + '</td>'),
            $('<td id="newSalesRevenue-' + idx + '" contenteditable="true" style="background-color: #FFFF99" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.newSalesRevenue + '</td>'),
            $('<td id="duesDraftsRevenue-' + idx + '" contenteditable="true" style="background-color: #FFFF99" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.duesDraftsRevenue + '</td>'),
            $('<td id="productsRevenue-' + idx + '" contenteditable="true" style="background-color: #FFFF99" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.productsRevenue + '</td>'),
            $('<td id="otherRevenue-' + idx + '" contenteditable="true" style="background-color: #FFFF99" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.otherRevenue + '</td>'),
            $('<td id="incomingCalls-' + idx + '" contenteditable="true" style="background-color: #B7DEE8" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.incomingCalls + '</td>'),
            $('<td id="incomingApo-' + idx + '" contenteditable="true" style="background-color: #B7DEE8" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.incomingApo + '</td>'),
            $('<td id="outgoingCalls-' + idx + '" contenteditable="true" style="background-color: #B7DEE8" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.outgoingCalls + '</td>'),
            $('<td id="outgoingApo-' + idx + '" contenteditable="true" style="background-color: #B7DEE8" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.outgoingApo + '</td>'),
            $('<td id="brOwnRef-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brOwnRef + '</td>'),
            $('<td id="brOthersRef-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brOthersRef + '</td>'),
            $('<td id="brandedNewspaper-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brandedNewspaper + '</td>'),
            $('<td id="brandedTv-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brandedTv + '</td>'),
            $('<td id="brandedInternet-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brandedInternet + '</td>'),
            $('<td id="brandedSign-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brandedSign + '</td>'),
            $('<td id="brandedMate-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brandedMate + '</td>'),
            $('<td id="brandedOthers-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.brandedOthers + '</td>'),
            $('<td id="agpInDirectMail-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpInDirectMail + '</td>'),
            $('<td id="agpInMailFlyer-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpInMailFlyer + '</td>'),
            $('<td id="agpInHandFlyer-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpInHandFlyer + '</td>'),
            $('<td id="agpInCp-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpInCp + '</td>'),
            $('<td id="agpOutApoOut-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpOutApoOut + '</td>'),
            $('<td id="agpOutApoIn-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpOutApoIn + '</td>'),
            $('<td id="agpOutApoBlog-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpOutApoBlog + '</td>'),
            $('<td id="agpOutApoBag-' + idx + '" contenteditable="true" style="background-color: #FF99CC" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.agpOutApoBag + '</td>'),
            $('<td id="fa-' + idx + '" style="background-color: LightGray" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.fa + '</td>'),
            $('<td id="enrollAch-' + idx + '" contenteditable="true" style="background-color: #CCFFFF" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.enrollAch + '</td>'),
            $('<td id="enrollMonthly-' + idx + '" contenteditable="true" style="background-color: #CCFFFF" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.enrollMonthly + '</td>'),
            $('<td id="enrollAllPrepay-' + idx + '" contenteditable="true" style="background-color: #CCFFFF" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.enrollAllPrepay + '</td>'),
            $('<td id="exits-' + idx + '" contenteditable="true" style="background-color: #CCFFFF" data-container="table" data-toggle="popover" data-placement="top" data-trigger="focus" title="'+pjDay+'">' + pj.exits + '</td>')
          )
        );
    }

    if (currentYear === thisYear && currentMonth === thisMonth) {
        $('#btnSave').prop("disabled", false);
        $('td[contenteditable]').css('border', '1px dashed green !important');
        $('td[contenteditable="false"]').prop('contenteditable', true);
    } else {
        $('#btnSave').prop("disabled", true);
        $('#btnSave').toggleClass('btn-success btn-default')
        $('td[contenteditable]').css('border', '1px solid #ddd !important');
        $('td[contenteditable="true"]').prop('contenteditable', false);
    }

    clearZeroAndNaN();

    fillSummary(pjSum);
}

function fillSummary(pjSum) {
    // add summary row (background color - lightGray)
    $tbd.append(
      $('<tr style="background-color: LightGray"/>').append(
        $('<td/><td/><td/>'),
        $('<td>営業日数</td>'),
        $('<td>最高人数</td>'),
        $('<td>新銷售營收</td>'),
        $('<td>月費營收</td>'),
        $('<td>產品營收</td>'),
        $('<td>本月營收</td>'),
        $('<td> 退會率</td>'),
        $('<td>請假率</td>'),
        $('<td>New Sales</td>'),
        $('<td>Sales %</td>'),
        $('<td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/><td/>')
      )
    );
    $tbd.append(
      $('<tr style="background-color: LightGray"/>').append(
        $('<td/><td/><td/>'),
        $('<td id="workingDays" >' + (pjSum.workingDays) + '</td>'),
        $('<td id="maxWorkOuts" >' + (pjSum.maxWorkOuts) + '</td>'),
        $('<td id="newSalesRevenue" >' + (pjSum.newSalesRevenue) + '</td>'),
        $('<td id="duesDraftsRevenue" >' + (pjSum.duesDraftsRevenue) + '</td>'),
        $('<td id="productsRevenue" >' + (pjSum.productsRevenue) + '</td>'),
        $('<td id="otherRevenue" >' + (pjSum.otherRevenue) + '</td>'),
        $('<td id="incomingCalls" >' + (pjSum.incomingCalls) + '</td>'),
        $('<td id="incomingApo" >' + (pjSum.incomingApo) + '</td>'),
        $('<td id="outgoingCalls" >' + (pjSum.outgoingCalls) + '</td>'),
        $('<td id="outgoingApo" >' + (pjSum.outgoingApo) + '</td>'),
        $('<td id="brOwnRef" >' + (pjSum.brOwnRef) + '</td>'),
        $('<td id="brOthersRef" >' + (pjSum.brOthersRef) + '</td>'),
        $('<td id="brandedNewspaper" >' + (pjSum.brandedNewspaper) + '</td>'),
        $('<td id="brandedTv" >' + (pjSum.brandedTv) + '</td>'),
        $('<td id="brandedInternet" >' + (pjSum.brandedInternet) + '</td>'),
        $('<td id="brandedSign" >' + (pjSum.brandedSign) + '</td>'),
        $('<td id="brandedMate" >' + (pjSum.brandedMate) + '</td>'),
        $('<td id="brandedOthers" >' + (pjSum.brandedOthers) + '</td>'),
        $('<td id="agpInDirectMail" >' + (pjSum.agpInDirectMail) + '</td>'),
        $('<td id="agpInMailFlyer" >' + (pjSum.agpInMailFlyer) + '</td>'),
        $('<td id="agpInHandFlyer" >' + (pjSum.agpInHandFlyer) + '</td>'),
        $('<td id="agpInCp" >' + (pjSum.agpInCp) + '</td>'),
        $('<td id="agpOutApoOut" >' + (pjSum.agpOutApoOut) + '</td>'),
        $('<td id="agpOutApoIn" >' + (pjSum.agpOutApoIn) + '</td>'),
        $('<td id="agpOutApoBlog" >' + (pjSum.agpOutApoBlog) + '</td>'),
        $('<td id="agpOutApoBag" >' + (pjSum.agpOutApoBag) + '</td>'),
        $('<td id="faSum" >' + (pjSum.faSum) + '</td>'),
        $('<td id="enrollAch" >' + (pjSum.enrollAch) + '</td>'),
        $('<td id="enrollMonthly" >' + (pjSum.enrollMonthly) + '</td>'),
        $('<td id="enrollAllPrepay" >' + (pjSum.enrollAllPrepay) + '</td>'),
        $('<td id="exits" >' + (pjSum.exits) + '</td>')
      )
    );

    $(function () {
      $('[data-toggle="popover"]').popover()
    })

    $('td[contenteditable="true"]').focusout(function() {
        runFormula();
    }).focusin(function(e) {
        var thisId = $(this).attr('id').split('-');
        var colContent = $('#'+thisId[0]+'Title').text();
        $(this).attr('data-content', colContent);
        $(this).popover('show');
    }).keydown(function(e) {
        // keyCode: 8-BackSpace,9-Tab,13-Enter,46-Delete,110-KP_Decimal,190-period colon,35-40:Home-End-ArrowKey,48-57:0-9,96-105:KP-0-9
        if (e.which == 8 || e.which == 9 || e.which == 13 || e.which == 46 || e.which == 110 || e.which == 190
            || (e.which >= 35 && e.which <= 40) || (e.which >= 48 && e.which <= 57) || (e.which >= 96 && e.which <= 105)) {
            switch (e.which) {
                case 13: // enter
                case 39: // right
                    $(this).next().focus();
                    break;
                case 37: // left
                    $(this).prev().focus();
                    break;
                case 38: // up
                    var thisId = $(this).attr('id').split('-');
                    $(this).closest('tr').prev().find('td[id^='+thisId[0]+']').focus();
                    break;
                case 40: // down
                    var thisId = $(this).attr('id').split('-');
                    $(this).closest('tr').next().find('td[id^='+thisId[0]+']').focus();
                    break;
                default: return; // exit this handler for other keys
            }
        }
        e.preventDefault(); // prevent the default action (scroll / move caret)
    });
}

function clearZero() {
    $('td[contenteditable]').each(function() {
        var valueX = $(this).text();
        if (valueX === '-1' || valueX === '0' || valueX === '0.0') {
            $(this).text('');
        }
    });
}

function clearNaN() {
    $('td').each(function() {
        var valueX = $(this).text();
        if (valueX === 'undefined' || valueX === 'NaN' || valueX === 'NaN%' || valueX === 'Infinity%') {
            $(this).text('');
        }
    });
}

function clearZeroAndNaN() {
    $('td').each(function() {
        var valueX = $(this).text();
        if (valueX === '0' || valueX === '0.0' || valueX === 'undefined' || valueX === 'NaN' || valueX === 'NaN%' || valueX === 'Infinity%') {
            $(this).text('');
        }
    });
}

// save PJ
$("#btnSave").click(function() {
    $(this).prop('disabled', true);

    runFormula();

    var pjSum = {};
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
    pjSum.salesRatio = parseFloat($('#salesRatio').text())/100;
    pjSum.exitRatio = parseFloat($('#exitRatio').text())/100;
    pjSum.leaveRatio = parseFloat($('#leaveRatio').text())/100;

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
    }).done(function(data) {
        showAlert("alert-success", "Save successfully.");
    }).fail(function() {
        showAlert("alert-danger", "Save Fail. Please refresh and retry.");
    });

    $(this).prop('disabled', false);
});

function runFormula() {
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
        brOwnRef += Number($('#brOwnRef-' + idx).text());
        brOthersRef += Number($('#brOthersRef-' + idx).text());
        brandedNewspaper += Number($('#brandedNewspaper-' + idx).text());
        brandedTv += Number($('#brandedTv-' + idx).text());
        brandedInternet += Number($('#brandedInternet-' + idx).text());
        brandedSign += Number($('#brandedSign-' + idx).text());
        brandedMate += Number($('#brandedMate-' + idx).text());
        brandedOthers += Number($('#brandedOthers-' + idx).text());
        agpInDirectMail += Number($('#agpInDirectMail-' + idx).text());
        agpInMailFlyer += Number($('#agpInMailFlyer-' + idx).text());
        agpInHandFlyer += Number($('#agpInHandFlyer-' + idx).text());
        agpInCp += Number($('#agpInCp-' + idx).text());
        agpOutApoOut += Number($('#agpOutApoOut-' + idx).text());
        agpOutApoIn += Number($('#agpOutApoIn-' + idx).text());
        agpOutApoBlog += Number($('#agpOutApoBlog-' + idx).text());
        agpOutApoBag += Number($('#agpOutApoBag-' + idx).text());
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
        $('#salesRatio').text((newSales*100/faSum).toFixed(0) + "%");
    }

    var enrolled = Number($('#enrolled').text());
    if (enrolled != 0) {
        $('#exitRatio').text((exits*100/enrolled).toFixed(1) + "%");
        var leaves = Number($('#leaves').text());
        $('#leaveRatio').text((leaves*100/enrolled).toFixed(1) + "%");
    }

    clearNaN();
}

});
