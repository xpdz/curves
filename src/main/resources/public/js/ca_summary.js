$(document).ready(function() {
  $.getScript('js/common.js', function() {
    var clubId = $.QueryString.clubId,
        clubName = $.QueryString.clubName,
        clubOwner = $.QueryString.clubOwner;
    $('th[name="clubId"').text(clubId);
    $('th[name="clubName"').text(clubName);
    $('th[name="clubOwner"').text(clubOwner);

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
        getCaSummary();
    });

    $('#btnExport').click(function() {
        window.location = "rest/CA_summary?clubId="+clubId+"&yStart="+yStart+"&yEnd="+yEnd+"&mStart="+mStart+"&mEnd="+mEnd;
    });

    getCaSummary();
    function getCaSummary() {
        $.getJSON("/rest/caSummary", {clubId: clubId, yStart: yStart, yEnd: yEnd, mStart: mStart, mEnd: mEnd}, function(cas) {
            fillSheet(cas);
        }).fail(function() {
            showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
        });
    }

    function fillSheet(cas) {
        for (var i = 1; i <= cas.length; i++) {
            var ca = cas[i-1];
            $('th[name="mth'+i+'"]').text(ca.caYear+'-'+(ca.caMonth+1));
            $('#svcTm-'+i).text(ca.svcTm6);
            $('#svcShift-'+i).text(ca.svcShift6);
            $('#svcHold-'+i).text(ca.svcHold6);
            $('#svcActive-'+i).text(ca.svcActive6);
            $('#svcHoldRatio-'+i).text((ca.svcHoldRatio6*100).toFixed(1)+'%');
            $('#svcTotalWo-'+i).text(ca.svcTotalWo6);
            $('#svcAvgWo-'+i).text(ca.svcAvgWo6.toFixed(1));
            $('#svcMaxWo-'+i).text((ca.svcMaxWo6*100).toFixed(0)+'%');
            $('#svcExits-'+i).text(ca.svcExits6);
            $('#svcExitsRatio-'+i).text((ca.svcExitsRatio6*100).toFixed(1)+'%');
            $('#svcMeasure-'+i).text(ca.svcMeasure6);
            $('#svcMeasureRatio-'+i).text((ca.svcMeasureRatio6*100).toFixed(0)+'%');
            $('#svc12-'+i).text((ca.svc12_6*100).toFixed(0)+'%');
            $('#svc8to11-'+i).text((ca.svc8to11_6*100).toFixed(0)+'%');
            $('#svc4to7-'+i).text((ca.svc4to7_6*100).toFixed(0)+'%');
            $('#svc1to3-'+i).text((ca.svc1to3_6*100).toFixed(0)+'%');
            $('#svc0-'+i).text((ca.svc0_6*100).toFixed(0)+'%');

            $('#cmPostFlyer-'+i).text(ca.cmPostFlyer6);
            $('#cmHandFlyer-'+i).text(ca.cmHandFlyer6);
            $('#cmHandFlyerHours-'+i).text(ca.cmHandFlyerHours6.toFixed(1));
            $('#cmOutGpHours-'+i).text(ca.cmOutGpHours6.toFixed(1));
            $('#cmOutGp-'+i).text(ca.cmOutGp6);
            $('#cmCpBox-'+i).text(ca.cmCpBox6);
            $('#cmOutGot-'+i).text(ca.cmOutGot6);
            $('#cmInGot-'+i).text(ca.cmInGot6);
            $('#cmBlogGot-'+i).text(ca.cmBlogGot6);
            $('#cmBagGot-'+i).text(ca.cmBagGot6);
            $('#cmTotalGot-'+i).text(ca.cmTotalGot6);
            $('#cmCallIn-'+i).text(ca.cmCallIn6);
            $('#cmOutGotCall-'+i).text(ca.cmOutGotCall6);
            $('#cmInGotCall-'+i).text(ca.cmInGotCall6);
            $('#cmBlogGotCall-'+i).text(ca.cmBlogGotCall6);
            $('#cmBagGotCall-'+i).text(ca.cmBagGotCall6);
            $('#cmOwnRefs-'+i).text(ca.cmOwnRefs6);
            $('#cmOtherRefs-'+i).text(ca.cmOtherRefs6);
            $('#cmNewspaper-'+i).text(ca.cmNewspaper6);
            $('#cmTv-'+i).text(ca.cmTv6);
            $('#cmInternet-'+i).text(ca.cmInternet6);
            $('#cmSign-'+i).text(ca.cmSign6);
            $('#cmMate-'+i).text(ca.cmMate6);
            $('#cmOthers-'+i).text(ca.cmOthers6);
            $('#cmMailAgpIn-'+i).text(ca.cmMailAgpIn6);
            $('#cmPostFlyerAgpIn-'+i).text(ca.cmPostFlyerAgpIn6);
            $('#cmHandFlyerAgpIn-'+i).text(ca.cmHandFlyerAgpIn6);
            $('#cmCpAgpIn-'+i).text(ca.cmCpAgpIn6);
            $('#cmOutAgpOut-'+i).text(ca.cmOutAgpOut6);
            $('#cmInAgpOut-'+i).text(ca.cmInAgpOut6);
            $('#cmBlogAgpOut-'+i).text(ca.cmBlogAgpOut6);
            $('#cmBagAgpOut-'+i).text(ca.cmBagAgpOut6);
            $('#cmApoTotal-'+i).text(ca.cmApoTotal6);
            $('#cmInApptRatio-'+i).text((ca.cmInApptRatio6*100).toFixed(0)+'%');
            $('#cmOutApptRatio-'+i).text((ca.cmOutApptRatio6*100).toFixed(0)+'%');
            $('#cmBrAgpRatio-'+i).text((ca.cmBrAgpRatio6*100).toFixed(0)+'%');
            $('#cmFaSum-'+i).text(ca.cmFaSum6);
            $('#cmShowRatio-'+i).text((ca.cmShowRatio6*100).toFixed(0)+'%');

            $('#salesAch-'+i).text(ca.salesAch6);
            $('#salesMonthly-'+i).text(ca.salesMonthly6);
            $('#salesAllPrepay-'+i).text(ca.salesAllPrepay6);
            $('#salesTotal-'+i).text(ca.salesTotal6);
            $('#salesRatio-'+i).text((ca.salesRatio6*100).toFixed(0)+'%');
            $('#salesAchAppRatio-'+i).text((ca.salesAchAppRatio6*100).toFixed(0)+'%');

            $('#clubAch-'+i).text(ca.clubAch6);
            $('#clubMm-'+i).text(ca.clubMm6);
            $('#clubApp-'+i).text(ca.clubApp6);
            $('#clubNs-'+i).text(ca.clubNs6);
            $('#clubLx-'+i).text(ca.clubLx6);
        }
    }
  });
});
