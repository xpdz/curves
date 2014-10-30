$(document).ready(function() {

var today = new Date();
var thisYear = today.getFullYear();
var thisMonth = today.getMonth();

$('#caDate').text(thisYear+"-"+(thisMonth+1)+"-"+today.getDate());

// init user ID / club ID
$.getJSON("/rest/whoami", function(club) {
    $('#userId').text(club.clubId);
    $('#clubName').text(club.name);
    $('#owner').text(club.owner);
    $('#mgr').text(club.mgr);
}).fail(function() {
    alert("oops! I cannot find your ID, please logout and login again.");
});

// init date picker
$('#caDate').value = thisYear + '-' + thisMonth;
$('#caDate').datetimepicker({
    language:  'zh-TW',
    format: 'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 3,
    minView: 3,
    forceParse: 0
});
$('#caDate').datetimepicker().on('changeDate', function(ev) {
    var selYear = ev.date.getFullYear();
    var selMonth = ev.date.getMonth();
    getCA(selYear, selMonth);
});

// init CA page
getCA(thisYear, thisMonth);

function getCA(y, m) {
    $.getJSON("/rest/CA", {year: y, month: m}, function(cas) {
//        fillSheet(y, m, pjSum);
    }).fail(function() {
//        $('#caDate').text(thisYear);
    });

    if (y == thisYear && m == thisMonth) {
        $("#btnSave").attr("disabled", false);
    } else {
        $("#btnSave").attr("disabled", true);
    }
}

// switch star sign
$('.glyphicon').click(function() {
    if ($(this).hasClass('glyphicon-star-empty')) {
        $(this).removeClass('glyphicon-star-empty');
        $(this).addClass('glyphicon-star');
    } else {
        $(this).removeClass('glyphicon-star');
        $(this).addClass('glyphicon-star-empty');
    }
})

// save CA
$("#btnSave").click(function() {
    runFormula();
    var cas = [];

    for (var i = 0; i < 7; i++) {
        var ca = {};
        cas.push(ca);

        ca.clubId = +$('#userId').text();
        ca.year = thisYear;
        ca.month = thisMonth;
        switch (i) {
            case 0: ca.col = 'Wk'; break;
            case 1: ca.col = '1'; break;
            case 2: ca.col = '2'; break;
            case 3: ca.col = '3'; break;
            case 4: ca.col = '4'; break;
            case 5: ca.col = '5'; break;
            case 6: ca.col = 'Monthly'; break;
        }
        if (i == 6) {
            ca.goalsTm = +$('#goalsTm').text();
            ca.goalsLastTm = +$('#goalsLastTm').text();
            ca.goalsLastActive = +$('#goalsLastActive').text();
            ca.goalsLastShowRatio = $('#goalsLastShowRatio').text();
            ca.goalsLastSalesRatio = $('#goalsLastSalesRatio').text();
            ca.goalsExitsRatio = $('#goalsExitsRatio').text();
            ca.goalsNewSales = +$('#goalsNewSales').text();
            ca.goalsAppoints = +$('#goalsAppoints').text();
        }
        ca.serviceTm = +$('#serviceTm-'+i).text();
        ca.serviceShift = +$('#serviceShift-'+i).text();
        ca.serviceHold = +$('#serviceHold-'+i).text();
        ca.serviceActive = +$('#serviceActive-'+i).text();
        ca.serviceHoldRatio = $('#serviceHoldRatio-'+i).text();
        ca.serviceTotalWo = +$('#serviceTotalWo-'+i).text();
        ca.serviceAvgWo = +$('#serviceAvgWo-'+i).text();
        ca.serviceMaxWo = +$('#serviceMaxWo-'+i).text();
        ca.serviceExits = +$('#serviceExits-'+i).text();
        ca.serviceExitsRatio = $('#serviceExitsRatio-'+i).text();
        ca.serviceMeasure = +$('#serviceMeasure-'+i).text();
        ca.serviceMeasureRatio = $('#serviceMeasureRatio-'+i).text();
        if (i == 0 || i == 5 || i == 6) {
            ca.service12 = +$('#service12-'+i).text();
            ca.service8to11 = +$('#service8to11-'+i).text();
            ca.service4to7 = +$('#service4to7-'+i).text();
            ca.service1to3 = +$('#service1to3-'+i).text();
            ca.service0 = +$('#service0-'+i).text();
        }
        if (i < 6) {
            ca.service3More = +$('#service3More-'+i).text();
            ca.serviceInactive = +$('#serviceInactive-'+i).text();
            ca.serviceFwoReview = +$('#serviceFwoReview-'+i).text();
            ca.serviceInterview = +$('#serviceInterview-'+i).text();
            ca.serviceThanks = +$('#serviceThanks-'+i).text();
            ca.service3C = +$('#service3C-'+i).text();
            ca.serviceReward = +$('#serviceReward-'+i).text();
            ca.serviceLoyal = +$('#serviceLoyal-'+i).text();
        }
        ca.cmLeadMailFlyer = +$('#cmLeadMailFlyer-'+i).text();
        ca.cmLeadHandFlyer = +$('#cmLeadHandFlyer-'+i).text();
        ca.cmLeadOut = +$('#cmLeadOut-'+i).text();
        ca.cmLeadCp = +$('#cmLeadCp-'+i).text();
        ca.cmLeadOutGot = +$('#cmLeadOutGot-'+i).text();
        ca.cmLeadInGot = +$('#cmLeadInGot-'+i).text();
        ca.cmLeadBlogGot = +$('#cmLeadBlogGot-'+i).text();
        ca.cmLeadBagGot = +$('#cmLeadBagGot-'+i).text();
        ca.cmLeadTotal = +$('#cmLeadTotal-'+i).text();
        ca.cmCallsTotal = +$('#cmCallsTotal-'+i).text();
        ca.cmCallsOutGot = +$('#cmCallsOutGot-'+i).text();
        ca.cmCallsInGot = +$('#cmCallsInGot-'+i).text();
        ca.cmCallsBlogGot = +$('#cmCallsBlogGot-'+i).text();
        ca.cmCallsBagGot = +$('#cmCallsBagGot-'+i).text();
        ca.cmOwnRefs = +$('#cmOwnRefs-'+i).text();
        ca.cmOtherRefs = +$('#cmOtherRefs-'+i).text();
        ca.cmNewspaper = +$('#cmNewspaper-'+i).text();
        ca.cmTv = +$('#cmTv-'+i).text();
        ca.cmInternet = +$('#cmInternet-'+i).text();
        ca.cmSign = +$('#cmSign-'+i).text();
        ca.cmMate = +$('#cmMate-'+i).text();
        ca.cmOthers = +$('#cmOthers-'+i).text();
        ca.cmAgpInDirectMail = +$('#cmAgpInDirectMail-'+i).text();
        ca.cmAgpInMailFlyer = +$('#cmAgpInMailFlyer-'+i).text();
        ca.cmAgpInHandFlyer = +$('#cmAgpInHandFlyer-'+i).text();
        ca.cmAgpInCp = +$('#cmAgpInCp-'+i).text();
        ca.cmAgpOutApoOut = +$('#cmAgpOutApoOut-'+i).text();
        ca.cmAgpOutApoIn = +$('#cmAgpOutApoIn-'+i).text();
        ca.cmAgpOutApoBlog = +$('#cmAgpOutApoBlog-'+i).text();
        ca.cmAgpOutAgpBag = +$('#cmAgpOutAgpBag-'+i).text();
        ca.cmApoTotal = +$('#cmApoTotal-'+i).text();
        ca.cmInApoRatio = $('#cmInApoRatio-'+i).text();
        ca.cmOutApoRatio = $('#cmOutApoRatio-'+i).text();
        if (i == 6) {
            ca.cmMailPerApo = +$('#cmMailPerApo-'+i).text();
            ca.cmHandPerApo = +$('#cmHandPerApo-'+i).text();
            ca.cmOutPerApo = +$('#cmOutPerApo-'+i).text();
        }
        ca.cmBrAgpRatio = $('#cmBrAgpRatio-'+i).text();
        ca.cmFaSum = +$('#cmFaSum-'+i).text();
        ca.cmShowRatio = $('#cmShowRatio-'+i).text();
        if (i != 6) {
            ca.cmTraining = +$('#cmTraining-'+i).text();
            ca.cmGot3 = +$('#cmGot3-'+i).text();
            ca.cmInvitation = +$('#cmInvitation-'+i).text();
        }
        ca.salesAch = +$('#salesAch-'+i).text();
        ca.salesMonthly = +$('#salesMonthly-'+i).text();
        ca.salesAllPrepay = +$('#salesAllPrepay-'+i).text();
        ca.salesTotal = +$('#salesTotal-'+i).text();
        ca.salesRatio = $('#salesRatio-'+i).text();
        ca.salesAchAppRatio = $('#salesAchAppRatio-'+i).text();
        if (i != 6) {
            ca.salesFaReview = +$('#salesFaReview-'+i).text();
            ca.salesPriceReview = +$('#salesPriceReview-'+i).text();
            ca.salesAck = +$('#salesAck-'+i).text();
            ca.salesTarget = +$('#salesTarget-'+i).text();
            ca.salesMotivation = +$('#salesMotivation-'+i).text();
            ca.salesObstacle = +$('#salesObstacle-'+i).text();
            ca.mgmtMeeting = +$('#mgmtMeeting-'+i).text();
            ca.mgmtCa = +$('#mgmtCa-'+i).text();
            ca.mgmtGp = +$('#mgmtGp-'+i).text();
            ca.mgmtLearn = +$('#mgmtLearn-'+i).text();
            ca.mgmtSheet = +$('#mgmtSheet-'+i).text();
            ca.mgmtPolicy = +$('#mgmtPolicy-'+i).text();
            ca.mgmtCompiantSales = +$('#mgmtCompiantSales-'+i).text();
            ca.mgmtCompiantMethod = +$('#mgmtCompiantMethod-'+i).text();
            ca.mgmtCompiantProduct = +$('#mgmtCompiantProduct-'+i).text();
            ca.mgmtCompiantAd = +$('#mgmtCompiantAd-'+i).text();
            ca.mgmtTraining = +$('#mgmtTraining-'+i).text();
            ca.mgmtReport = +$('#mgmtReport-'+i).text();
            ca.mgmtPlan = +$('#mgmtPlan-'+i).text();
            ca.mgmtMaintain = +$('#mgmtMaintain-'+i).text();
            ca.mgmtFace2Face = +$('#mgmtFace2Face-'+i).text();
        }
        ca.staffEvalList = [];
        if (i == 0) {
            ca.clubSalesRatio = $('#clubSalesRatio').text();
            ca.clubAchAppRatio = $('#clubAchAppRatio').text();
            for (var j = 1; j <= 6; j++) {
                var staffEval = {};
                ca.staffEvalList.push(staffEval);
                staffEval.name = $('#staff'+j).text();
                staffEval.salesRatio = $('#staff'+j+"SalesRatio").text();
                staffEval.achAppRatio = $('#staff'+j+"AchAppRatio").text();
            }
        } else {
            ca.clubAch = +$('#clubAch-'+i).text();
            ca.clubMm = +$('#clubMm-'+i).text();
            ca.clubApp = +$('#clubApp-'+i).text();
            ca.clubNs = +$('#clubNs-'+i).text();
            ca.clubLx = +$('#clubLx-'+i).text();
            for (var j = 1; j <= 6; j++) {
                var staffEval = {};
                ca.staffEvalList.push(staffEval);
                staffEval.ach = +$('#staff'+j+"Ach-"+i).text();
                staffEval.mm = +$('#staff'+j+"Mm-"+i).text();
                staffEval.app = +$('#staff'+j+"App-"+i).text();
                staffEval.ns = +$('#staff'+j+"Ns-"+i).text();
                staffEval.lx = +$('#staff'+j+"Lx-"+i).text();
            }
        }

        ca.thisPlan = $('#thisPlanL').text();
        ca.nextPlan = $('#nextPlanL').text();
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "/rest/CA",
        'data': JSON.stringify(cas),
        'dataType': 'json'
    }).done(function() {
        alert("Save successfully.");
    }).fail(function() {
        alert("oops! Save failed, please try again.");
    });
});

function runFormula() {
    var goalsTm = Number($('#goalsTm').text());
    var goalsExitsRatio = Number($('#goalsExitsRatio').text());
    var goalsLastTm = Number($('#goalsLastTm').text());
    $('#goalsNewSales').text(goalsTm*(1+goalsExitsRatio)-goalsLastTm);
    var goalsLastShowRatio = Number($('#goalsLastShowRatio').text());
    var goalsLastSalesRatio = Number($('#goalsLastSalesRatio').text());
    if (goalsLastShowRatio != 0 && goalsLastSales != 0) {
        $('#goalsNewSales').text((goalsTm*(1+goalsExitsRatio)-goalsLastTm)/goalsLastShow/goalsLastSalesRatio);
    }
}

});
