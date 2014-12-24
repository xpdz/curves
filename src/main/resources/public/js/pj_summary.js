$(document).ready(function() {
  $.getScript('js/common.js', function() {
    var $tbd = $('#tbd');
    var clubId = $.QueryString.clubId,
        clubName = $.QueryString.clubName,
        clubOwner = $.QueryString.clubOwner;
    $('#clubName').text(clubName);
    $('#clubOwner').text(clubOwner);

    var today = new Date();
    var dt = new Date(today.getFullYear(), today.getMonth()-1, 1);
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

    $('#btnLoad').click(function() {
        getPjSummary();
    });

    $('#btnExport').click(function() {
        window.location = "rest/PJ_summary?clubId="+clubId+"&yStart="+yStart+"&yEnd="+yEnd+"&mStart="+mStart+"&mEnd="+mEnd;
    });

    getPjSummary();
    function getPjSummary() {
        $.getJSON("/rest/pjSummary", {clubId: clubId, yStart: yStart, yEnd: yEnd, mStart: mStart, mEnd: mEnd}, function(pjs) {
            fillSheet(pjs);
        }).fail(function() {
            showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
        });
    }

    function fillSheet(pjs) {
        $tbd.empty();
        var t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0,t10=0,t11=0,t12=0,t13=0,t14=0,t15=0,t16=0,t17=0,t18=0,t19=0,t20=0,t21=0,t22=0;
        for (var i = 0; i < pjs.length; i++) {
            var pj = pjs[i];
            t1 += pj.newSales; t2 += pj.exits; t3 += pj.shiftIn; t4 += pj.shiftOut; t5 += pj.increment; t6 += pj.revenue;
            t7 += pj.enrolled; t8 += pj.leaves; t9 += pj.valids; t10 += pj.salesRatio; t11 += pj.exitRatio;
            t12 += pj.leaveRatio; t13 += pj.maxWorkOuts; t14 += pj.newSalesRevenue; t15 += pj.duesDraftsRevenue;
            t16 += pj.productsRevenue; t17 += pj.otherRevenue; t18 += pj.faSum; t19 += pj.enrollAch;
            t20 += pj.enrollMonthly; t21 += pj.enrollAllPrepay; t22 += pj.exits;
        }
        $tbd.append($('<tr style="background-color: lightGray"/>').append(
            $('<td colspan="2">AVG</td>'),
            $('<td>' + (t1/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t2/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t3/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t4/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t5/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t6/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t7/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t8/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t9/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t10*100/pjs.length).toFixed(0) + '%</td>'),
            $('<td>' + (t11*100/pjs.length).toFixed(0) + '%</td>'),
            $('<td>' + (t12*100/pjs.length).toFixed(0) + '%</td>'),
            $('<td>' + (t13/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t14/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t15/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t16/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t17/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t18/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t19/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t20/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t21/pjs.length).toFixed(0) + '</td>'),
            $('<td>' + (t22/pjs.length).toFixed(0) + '</td>')
        ));
        $tbd.append($('<tr style="background-color: lightGray"/>').append(
            $('<td colspan="2">SUM</td>'),
            $('<td>' + t1 + '</td>'),
            $('<td>' + t2 + '</td>'),
            $('<td>' + t3 + '</td>'),
            $('<td>' + t4 + '</td>'),
            $('<td>' + t5 + '</td>'),
            $('<td>' + t6 + '</td>'),
            $('<td>' + t7 + '</td>'),
            $('<td>' + t8 + '</td>'),
            $('<td>' + t9 + '</td>'),
            $('<td>' + (t10*100).toFixed(0) + '%</td>'),
            $('<td>' + (t11*100).toFixed(0) + '%</td>'),
            $('<td>' + (t12*100).toFixed(0) + '%</td>'),
            $('<td>' + t13 + '</td>'),
            $('<td>' + t14 + '</td>'),
            $('<td>' + t15 + '</td>'),
            $('<td>' + t16 + '</td>'),
            $('<td>' + t17 + '</td>'),
            $('<td>' + t18 + '</td>'),
            $('<td>' + t19 + '</td>'),
            $('<td>' + t20 + '</td>'),
            $('<td>' + t21 + '</td>'),
            $('<td>' + t22 + '</td>')
        ));
        for (var i = 0; i < pjs.length; i++) {
            var pj = pjs[i];
            $tbd.append($('<tr/>').append(
                $('<td>' + pj.year + '</td>'),
                $('<td>' + (pj.month+1) + '</td>'),
                $('<td>' + pj.newSales + '</td>'),
                $('<td>' + pj.exits + '</td>'),
                $('<td>' + pj.shiftIn + '</td>'),
                $('<td>' + pj.shiftOut + '</td>'),
                $('<td>' + pj.increment + '</td>'),
                $('<td>' + pj.revenue + '</td>'),
                $('<td>' + pj.enrolled + '</td>'),
                $('<td>' + pj.leaves + '</td>'),
                $('<td>' + pj.valids + '</td>'),
                $('<td>' + (pj.salesRatio*100).toFixed(0) + '%</td>'),
                $('<td>' + (pj.exitRatio*100).toFixed(0) + '%</td>'),
                $('<td>' + (pj.leaveRatio*100).toFixed(0) + '%</td>'),
                $('<td>' + pj.maxWorkOuts + '</td>'),
                $('<td>' + pj.newSalesRevenue + '</td>'),
                $('<td>' + pj.duesDraftsRevenue + '</td>'),
                $('<td>' + pj.productsRevenue + '</td>'),
                $('<td>' + pj.otherRevenue + '</td>'),
                $('<td>' + pj.faSum + '</td>'),
                $('<td>' + pj.enrollAch + '</td>'),
                $('<td>' + pj.enrollMonthly + '</td>'),
                $('<td>' + pj.enrollAllPrepay + '</td>'),
                $('<td>' + pj.exits + '</td>')
            ));
        }
    }
  });
});
