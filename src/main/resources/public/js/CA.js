$(document).ready(function() {

window.caId = -1;

var today = new Date();
var thisYear = today.getFullYear();
var thisMonth = today.getMonth();

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
$('#caDate').val(thisYear+"-"+(thisMonth+1));
$('#caDate').datetimepicker({
    language:  'zh-TW',
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
$('#btnLoad').click(function(ev) {
    var dt = new Date($('#caDate').val());
    console.log($('#caDate').val());
    console.log(dt);
    var selYear = dt.getFullYear();
    var selMonth = dt.getMonth();
    console.log("---"+selYear+"-"+selMonth);
    getCA(selYear, selMonth);
});

// init CA page
getCA(thisYear, thisMonth);

function getCA(y, m) {
    $.getJSON("/rest/CA", {caYear: y, caMonth: m}, function(ca) {
        fillSheet(ca);
    }).fail(function() {
//        alert('oops! I cannot find the data of ' + y + '-' + m);
    });

    if (y == thisYear && m == thisMonth) {
        $('#btnSave').prop("disabled", false);
        $('td[contenteditable="false"]').prop('contenteditable', 'true');
    } else {
        $('#btnSave').prop("disabled", true);
        $('td[contenteditable="true"]').prop('contenteditable', 'false');
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
});

function fillSheet(ca) {
    window.caId = ca.id;
    $('#goalsTm').text(ca.goalsTm);
    $('#goalsLastTm').text(ca.goalsLastTm);
    $('#goalsLastActive').text(ca.goalsLastActive);
    $('#goalsLastShowRatio').text(ca.goalsLastShowRatio);
    $('#goalsLastSalesRatio').text(ca.goalsLastSalesRatio);
    $('#goalsExitsRatio').text(ca.goalsExitsRatio);
    $('#goalsNewSales').text(ca.goalsNewSales);
    $('#goalsAppoints').text(ca.goalsAppoints);

    if (ca.svcTm0) {
        $('#svcTm-0').removeClass('glyphicon-star-empty');
        $('#svcTm-0').removeClass('glyphicon-star');
    }
    $('#svcTm-1').text(ca.svcTm1);
    $('#svcTm-2').text(ca.svcTm2);
    $('#svcTm-3').text(ca.svcTm3);
    $('#svcTm-4').text(ca.svcTm4);
    $('#svcTm-5').text(ca.svcTm5);
    $('#svcTm-6').text(ca.svcTm6);
    if (ca.svcShift0) {
        $('#svcShift-0').removeClass('glyphicon-star-empty');
        $('#svcShift-0').addClass('glyphicon-star');
    }
    $('#svcShift-1').text(ca.svcShift1);
    $('#svcShift-2').text(ca.svcShift2);
    $('#svcShift-3').text(ca.svcShift3);
    $('#svcShift-4').text(ca.svcShift4);
    $('#svcShift-5').text(ca.svcShift5);
    $('#svcShift-6').text(ca.svcShift6);
    if (ca.svcHold0) {
        $('#svcHold-0').removeClass('glyphicon-star-empty');
        $('#svcHold-0').addClass('glyphicon-star');
    }
    $('#svcHold-1').text(ca.svcHold1);
    $('#svcHold-2').text(ca.svcHold2);
    $('#svcHold-3').text(ca.svcHold3);
    $('#svcHold-4').text(ca.svcHold4);
    $('#svcHold-5').text(ca.svcHold5);
    $('#svcHold-6').text(ca.svcHold6);
    if (ca.svcActive0) {
        $('#svcActive-0').removeClass('glyphicon-star-empty');
        $('#svcActive-0').addClass('glyphicon-star');
    }
    $('#svcActive-1').text(ca.svcActive1);
    $('#svcActive-2').text(ca.svcActive2);
    $('#svcActive-3').text(ca.svcActive3);
    $('#svcActive-4').text(ca.svcActive4);
    $('#svcActive-5').text(ca.svcActive5);
    $('#svcActive-6').text(ca.svcActive6);
    if (ca.svcHoldRatio0) {
        $('#svcHoldRatio-0').removeClass('glyphicon-star-empty');
        $('#svcHoldRatio-0').addClass('glyphicon-star');
    }
    $('#svcHoldRatio-1').text(ca.svcHoldRatio1);
    $('#svcHoldRatio-2').text(ca.svcHoldRatio2);
    $('#svcHoldRatio-3').text(ca.svcHoldRatio3);
    $('#svcHoldRatio-4').text(ca.svcHoldRatio4);
    $('#svcHoldRatio-5').text(ca.svcHoldRatio5);
    $('#svcHoldRatio-6').text(ca.svcHoldRatio6);
    if (ca.svcTotalWo0) {
        $('#svcTotalWo-0').removeClass('glyphicon-star-empty');
        $('#svcTotalWo-0').addClass('glyphicon-star');
    }
    $('#svcTotalWo-1').text(ca.svcTotalWo1);
    $('#svcTotalWo-2').text(ca.svcTotalWo2);
    $('#svcTotalWo-3').text(ca.svcTotalWo3);
    $('#svcTotalWo-4').text(ca.svcTotalWo4);
    $('#svcTotalWo-5').text(ca.svcTotalWo5);
    $('#svcTotalWo-6').text(ca.svcTotalWo6);
    if (ca.svcAvgWo0) {
        $('#svcAvgWo-0').removeClass('glyphicon-star-empty');
        $('#svcAvgWo-0').addClass('glyphicon-star');
    }
    $('#svcAvgWo-1').text(ca.svcAvgWo1);
    $('#svcAvgWo-2').text(ca.svcAvgWo2);
    $('#svcAvgWo-3').text(ca.svcAvgWo3);
    $('#svcAvgWo-4').text(ca.svcAvgWo4);
    $('#svcAvgWo-5').text(ca.svcAvgWo5);
    $('#svcAvgWo-6').text(ca.svcAvgWo6);
    if (ca.svcMaxWo0) {
        $('#svcMaxWo-0').removeClass('glyphicon-star-empty');
        $('#svcMaxWo-0').addClass('glyphicon-star');
    }
    $('#svcMaxWo-1').text(ca.svcMaxWo1);
    $('#svcMaxWo-2').text(ca.svcMaxWo2);
    $('#svcMaxWo-3').text(ca.svcMaxWo3);
    $('#svcMaxWo-4').text(ca.svcMaxWo4);
    $('#svcMaxWo-5').text(ca.svcMaxWo5);
    $('#svcMaxWo-6').text(ca.svcMaxWo6);
    if (ca.svcExits0) {
        $('#svcExits-0').removeClass('glyphicon-star-empty');
        $('#svcExits-0').addClass('glyphicon-star');
    }
    $('#svcExits-1').text(ca.svcExits1);
    $('#svcExits-2').text(ca.svcExits2);
    $('#svcExits-3').text(ca.svcExits3);
    $('#svcExits-4').text(ca.svcExits4);
    $('#svcExits-5').text(ca.svcExits5);
    $('#svcExits-6').text(ca.svcExits6);
    if (ca.svcExitsRatio0) {
        $('#svcExitsRatio-0').removeClass('glyphicon-star-empty');
        $('#svcExitsRatio-0').addClass('glyphicon-star');
    }
    $('#svcExitsRatio-1').text(ca.svcExitsRatio1);
    $('#svcExitsRatio-2').text(ca.svcExitsRatio2);
    $('#svcExitsRatio-3').text(ca.svcExitsRatio3);
    $('#svcExitsRatio-4').text(ca.svcExitsRatio4);
    $('#svcExitsRatio-5').text(ca.svcExitsRatio5);
    $('#svcExitsRatio-6').text(ca.svcExitsRatio6);
    if (ca.svcMeasure0) {
        $('#svcMeasure-0').removeClass('glyphicon-star-empty');
        $('#svcMeasure-0').addClass('glyphicon-star');
    }
    $('#svcMeasure-1').text(ca.svcMeasure1);
    $('#svcMeasure-2').text(ca.svcMeasure2);
    $('#svcMeasure-3').text(ca.svcMeasure3);
    $('#svcMeasure-4').text(ca.svcMeasure4);
    $('#svcMeasure-5').text(ca.svcMeasure5);
    $('#svcMeasure-6').text(ca.svcMeasure6);
    if (ca.svcMeasureRatio0) {
        $('#svcMeasureRatio-0').removeClass('glyphicon-star-empty');
        $('#svcMeasureRatio-0').addClass('glyphicon-star');
    }
    $('#svcMeasureRatio-1').text(ca.svcMeasureRatio1);
    $('#svcMeasureRatio-2').text(ca.svcMeasureRatio2);
    $('#svcMeasureRatio-3').text(ca.svcMeasureRatio3);
    $('#svcMeasureRatio-4').text(ca.svcMeasureRatio4);
    $('#svcMeasureRatio-5').text(ca.svcMeasureRatio5);
    $('#svcMeasureRatio-6').text(ca.svcMeasureRatio6);
    if (ca.svc12_0) {
        $('#svc12-0').removeClass('glyphicon-star-empty');
        $('#svc12-0').addClass('glyphicon-star');
    }
    $('#svc12-5').text(ca.svc12_5);
    $('#svc12-6').text(ca.svc12_6);
    if (ca.svc8to11_0) {
        $('#svc8to11-0').removeClass('glyphicon-star-empty');
        $('#svc8to11-0').addClass('glyphicon-star');
    }
    $('#svc8to11-5').text(ca.svc8to11_5);
    $('#svc8to11-6').text(ca.svc8to11_6);
    if (ca.svc4to7_0) {
        $('#svc4to7-0').removeClass('glyphicon-star-empty');
        $('#svc4to7-0').addClass('glyphicon-star');
    }
    $('#svc4to7-5').text(ca.svc4to7_5);
    $('#svc4to7-6').text(ca.svc4to7_6);
    if (ca.svc1to3_0) {
        $('#svc1to3-0').removeClass('glyphicon-star-empty');
        $('#svc1to3-0').addClass('glyphicon-star');
    }
    $('#svc1to3-5').text(ca.svc1to3_5);
    $('#svc1to3-6').text(ca.svc1to3_6);
    if (ca.svc0_0) {
        $('#svc0-0').removeClass('glyphicon-star-empty');
        $('#svc0-0').addClass('glyphicon-star');
    }
    $('#svc0-5').text(ca.svc0_5);
    $('#svc0-6').text(ca.svc0_6);
    if (ca.svc3More0) {
        $('#svc3More-0').removeClass('glyphicon-star-empty');
        $('#svc3More-0').addClass('glyphicon-star');
    }
    $('#svc3More-1').prop('checked', ca.svc3More1);
    $('#svc3More-2').prop('checked', ca.svc3More2);
    $('#svc3More-3').prop('checked', ca.svc3More3);
    $('#svc3More-4').prop('checked', ca.svc3More4);
    $('#svc3More-5').prop('checked', ca.svc3More5);
    if (ca.svcInactive0) {
        $('#svcInactive-0').removeClass('glyphicon-star-empty');
        $('#svcInactive-0').addClass('glyphicon-star');
    }
    $('#svcInactive-1').prop('checked', ca.svcInactive1);
    $('#svcInactive-2').prop('checked', ca.svcInactive2);
    $('#svcInactive-3').prop('checked', ca.svcInactive3);
    $('#svcInactive-4').prop('checked', ca.svcInactive4);
    $('#svcInactive-5').prop('checked', ca.svcInactive5);
    if (ca.svcFwoReview0) {
        $('#svcFwoReview-0').removeClass('glyphicon-star-empty');
        $('#svcFwoReview-0').addClass('glyphicon-star');
    }
    $('#svcFwoReview-1').prop('checked', ca.svcFwoReview1);
    $('#svcFwoReview-2').prop('checked', ca.svcFwoReview2);
    $('#svcFwoReview-3').prop('checked', ca.svcFwoReview3);
    $('#svcFwoReview-4').prop('checked', ca.svcFwoReview4);
    $('#svcFwoReview-5').prop('checked', ca.svcFwoReview5);
    if (ca.svcInterview0) {
        $('#svcInterview-0').removeClass('glyphicon-star-empty');
        $('#svcInterview-0').addClass('glyphicon-star');
    }
    $('#svcInterview-1').prop('checked', ca.svc3More1);
    $('#svcInterview-2').prop('checked', ca.svcInterview2);
    $('#svcInterview-3').prop('checked', ca.svcInterview3);
    $('#svcInterview-4').prop('checked', ca.svcInterview4);
    $('#svcInterview-5').prop('checked', ca.svcInterview5);
    if (ca.svcThanks0) {
        $('#svcThanks-0').removeClass('glyphicon-star-empty');
        $('#svcThanks-0').addClass('glyphicon-star');
    }
    $('#svcThanks-1').prop('checked', ca.svcThanks1);
    $('#svcThanks-2').prop('checked', ca.svcThanks2);
    $('#svcThanks-3').prop('checked', ca.svcThanks3);
    $('#svcThanks-4').prop('checked', ca.svcThanks4);
    $('#svcThanks-5').prop('checked', ca.svcThanks5);
    if (ca.svc3C0) {
        $('#svc3C-0').removeClass('glyphicon-star-empty');
        $('#svc3C-0').addClass('glyphicon-star');
    }
    $('#svc3C-1').prop('checked', ca.svc3C1);
    $('#svc3C-2').prop('checked', ca.svc3C2);
    $('#svc3C-3').prop('checked', ca.svc3C3);
    $('#svc3C-4').prop('checked', ca.svc3C4);
    $('#svc3C-5').prop('checked', ca.svc3C5);
    if (ca.svcReward0) {
        $('#svcReward-0').removeClass('glyphicon-star-empty');
        $('#svcReward-0').addClass('glyphicon-star');
    }
    $('#svcReward-1').prop('checked', ca.svcReward1);
    $('#svcReward-2').prop('checked', ca.svcReward2);
    $('#svcReward-3').prop('checked', ca.svcReward3);
    $('#svcReward-4').prop('checked', ca.svcReward4);
    $('#svcReward-5').prop('checked', ca.svcReward5);
    if (ca.svcLoyal0) {
        $('#svcLoyal-0').removeClass('glyphicon-star-empty');
        $('#svcLoyal-0').addClass('glyphicon-star');
    }
    $('#svcLoyal-1').prop('checked', ca.svcLoyal1);
    $('#svcLoyal-2').prop('checked', ca.svcLoyal2);
    $('#svcLoyal-3').prop('checked', ca.svcLoyal3);
    $('#svcLoyal-4').prop('checked', ca.svcLoyal4);
    $('#svcLoyal-5').prop('checked', ca.svcLoyal5);

    if (ca.cmPostFlyer0) {
        $('#cmPostFlyer-0').removeClass('glyphicon-star-empty');
        $('#cmPostFlyer-0').addClass('glyphicon-star');
    }
    $('#cmPostFlyer-1').text(ca.cmPostFlyer1);
    $('#cmPostFlyer-2').text(ca.cmPostFlyer2);
    $('#cmPostFlyer-3').text(ca.cmPostFlyer3);
    $('#cmPostFlyer-4').text(ca.cmPostFlyer4);
    $('#cmPostFlyer-5').text(ca.cmPostFlyer5);
    $('#cmPostFlyer-6').text(ca.cmPostFlyer6);
    if (ca.cmHandFlyer0) {
        $('#cmHandFlyer-0').removeClass('glyphicon-star-empty');
        $('#cmHandFlyer-0').addClass('glyphicon-star');
    }
    $('#cmHandFlyer-1').text(ca.cmHandFlyer1);
    $('#cmHandFlyer-2').text(ca.cmHandFlyer2);
    $('#cmHandFlyer-3').text(ca.cmHandFlyer3);
    $('#cmHandFlyer-4').text(ca.cmHandFlyer4);
    $('#cmHandFlyer-5').text(ca.cmHandFlyer5);
    $('#cmHandFlyer-6').text(ca.cmHandFlyer6);
    if (ca.cmOutGp0) {
        $('#cmOutGp-0').removeClass('glyphicon-star-empty');
        $('#cmOutGp-0').addClass('glyphicon-star');
    }
    $('#cmOutGp-1').text(ca.cmOutGp1);
    $('#cmOutGp-2').text(ca.cmOutGp2);
    $('#cmOutGp-3').text(ca.cmOutGp3);
    $('#cmOutGp-4').text(ca.cmOutGp4);
    $('#cmOutGp-5').text(ca.cmOutGp5);
    $('#cmOutGp-6').text(ca.cmOutGp6);
    if (ca.cmCpBox0) {
        $('#cmCpBox-0').removeClass('glyphicon-star-empty');
        $('#cmCpBox-0').addClass('glyphicon-star');
    }
    $('#cmCpBox-1').text(ca.cmCpBox1);
    $('#cmCpBox-2').text(ca.cmCpBox2);
    $('#cmCpBox-3').text(ca.cmCpBox3);
    $('#cmCpBox-4').text(ca.cmCpBox4);
    $('#cmCpBox-5').text(ca.cmCpBox5);
    $('#cmCpBox-6').text(ca.cmCpBox6);
    if (ca.cmOutGot0) {
        $('#cmOutGot-0').removeClass('glyphicon-star-empty');
        $('#cmOutGot-0').addClass('glyphicon-star');
    }
    $('#cmOutGot-1').text(ca.cmOutGot1);
    $('#cmOutGot-2').text(ca.cmOutGot2);
    $('#cmOutGot-3').text(ca.cmOutGot3);
    $('#cmOutGot-4').text(ca.cmOutGot4);
    $('#cmOutGot-5').text(ca.cmOutGot5);
    $('#cmOutGot-6').text(ca.cmOutGot6);
    if (ca.cmInGot0) {
        $('#cmInGot-0').removeClass('glyphicon-star-empty');
        $('#cmInGot-0').addClass('glyphicon-star');
    }
    $('#cmInGot-1').text(ca.cmInGot1);
    $('#cmInGot-2').text(ca.cmInGot2);
    $('#cmInGot-3').text(ca.cmInGot3);
    $('#cmInGot-4').text(ca.cmInGot4);
    $('#cmInGot-5').text(ca.cmInGot5);
    $('#cmInGot-6').text(ca.cmInGot6);
    if (ca.cmBlogGot0) {
        $('#cmBlogGot-0').removeClass('glyphicon-star-empty');
        $('#cmBlogGot-0').addClass('glyphicon-star');
    }
    $('#cmBlogGot-1').text(ca.cmBlogGot1);
    $('#cmBlogGot-2').text(ca.cmBlogGot2);
    $('#cmBlogGot-3').text(ca.cmBlogGot3);
    $('#cmBlogGot-4').text(ca.cmBlogGot4);
    $('#cmBlogGot-5').text(ca.cmBlogGot5);
    $('#cmBlogGot-6').text(ca.cmBlogGot6);
    if (ca.cmBagGot0) {
        $('#cmBagGot-0').removeClass('glyphicon-star-empty');
        $('#cmBagGot-0').addClass('glyphicon-star');
    }
    $('#cmBagGot-1').text(ca.cmBagGot1);
    $('#cmBagGot-2').text(ca.cmBagGot2);
    $('#cmBagGot-3').text(ca.cmBagGot3);
    $('#cmBagGot-4').text(ca.cmBagGot4);
    $('#cmBagGot-5').text(ca.cmBagGot5);
    $('#cmBagGot-6').text(ca.cmBagGot6);
    if (ca.cmTotalGot0) {
        $('#cmTotalGot-0').removeClass('glyphicon-star-empty');
        $('#cmTotalGot-0').addClass('glyphicon-star');
    }
    $('#cmTotalGot-1').text(ca.cmTotalGot1);
    $('#cmTotalGot-2').text(ca.cmTotalGot2);
    $('#cmTotalGot-3').text(ca.cmTotalGot3);
    $('#cmTotalGot-4').text(ca.cmTotalGot4);
    $('#cmTotalGot-5').text(ca.cmTotalGot5);
    $('#cmTotalGot-6').text(ca.cmTotalGot6);
    if (ca.cmCallIn0) {
        $('#cmCallIn-0').removeClass('glyphicon-star-empty');
        $('#cmCallIn-0').addClass('glyphicon-star');
    }
    $('#cmCallIn-1').text(ca.cmCallIn1);
    $('#cmCallIn-2').text(ca.cmCallIn2);
    $('#cmCallIn-3').text(ca.cmCallIn3);
    $('#cmCallIn-4').text(ca.cmCallIn4);
    $('#cmCallIn-5').text(ca.cmCallIn5);
    $('#cmCallIn-6').text(ca.cmCallIn6);
    if (ca.cmOutGotCall0) {
        $('#cmOutGotCall-0').removeClass('glyphicon-star-empty');
        $('#cmOutGotCall-0').addClass('glyphicon-star');
    }
    $('#cmOutGotCall-1').text(ca.cmOutGotCall1);
    $('#cmOutGotCall-2').text(ca.cmOutGotCall2);
    $('#cmOutGotCall-3').text(ca.cmOutGotCall3);
    $('#cmOutGotCall-4').text(ca.cmOutGotCall4);
    $('#cmOutGotCall-5').text(ca.cmOutGotCall5);
    $('#cmOutGotCall-6').text(ca.cmOutGotCall6);
    if (ca.cmInGotCall0) {
        $('#cmInGotCall-0').removeClass('glyphicon-star-empty');
        $('#cmInGotCall-0').addClass('glyphicon-star');
    }
    $('#cmInGotCall-1').text(ca.cmInGotCall1);
    $('#cmInGotCall-2').text(ca.cmInGotCall2);
    $('#cmInGotCall-3').text(ca.cmInGotCall3);
    $('#cmInGotCall-4').text(ca.cmInGotCall4);
    $('#cmInGotCall-5').text(ca.cmInGotCall5);
    $('#cmInGotCall-6').text(ca.cmInGotCall6);
    if (ca.cmBlogGotCall0) {
        $('#cmBlogGotCall-0').removeClass('glyphicon-star-empty');
        $('#cmBlogGotCall-0').addClass('glyphicon-star');
    }
    $('#cmBlogGotCall-1').text(ca.cmBlogGotCall1);
    $('#cmBlogGotCall-2').text(ca.cmBlogGotCall2);
    $('#cmBlogGotCall-3').text(ca.cmBlogGotCall3);
    $('#cmBlogGotCall-4').text(ca.cmBlogGotCall4);
    $('#cmBlogGotCall-5').text(ca.cmBlogGotCall5);
    $('#cmBlogGotCall-6').text(ca.cmBlogGotCall6);
    if (ca.cmBagGotCall0) {
        $('#cmBagGotCall-0').removeClass('glyphicon-star-empty');
        $('#cmBagGotCall-0').addClass('glyphicon-star');
    }
    $('#cmBagGotCall-1').text(ca.cmBagGotCall1);
    $('#cmBagGotCall-2').text(ca.cmBagGotCall2);
    $('#cmBagGotCall-3').text(ca.cmBagGotCall3);
    $('#cmBagGotCall-4').text(ca.cmBagGotCall4);
    $('#cmBagGotCall-5').text(ca.cmBagGotCall5);
    $('#cmBagGotCall-6').text(ca.cmBagGotCall6);
    if (ca.cmOwnRefs0) {
        $('#cmOwnRefs-0').removeClass('glyphicon-star-empty');
        $('#cmOwnRefs-0').addClass('glyphicon-star');
    }
    $('#cmOwnRefs-1').text(ca.cmOwnRefs1);
    $('#cmOwnRefs-2').text(ca.cmOwnRefs2);
    $('#cmOwnRefs-3').text(ca.cmOwnRefs3);
    $('#cmOwnRefs-4').text(ca.cmOwnRefs4);
    $('#cmOwnRefs-5').text(ca.cmOwnRefs5);
    $('#cmOwnRefs-6').text(ca.cmOwnRefs6);
    if (ca.cmOtherRefs0) {
        $('#cmOtherRefs-0').removeClass('glyphicon-star-empty');
        $('#cmOtherRefs-0').addClass('glyphicon-star');
    }
    $('#cmOtherRefs-1').text(ca.cmOtherRefs1);
    $('#cmOtherRefs-2').text(ca.cmOtherRefs2);
    $('#cmOtherRefs-3').text(ca.cmOtherRefs3);
    $('#cmOtherRefs-4').text(ca.cmOtherRefs4);
    $('#cmOtherRefs-5').text(ca.cmOtherRefs5);
    $('#cmOtherRefs-6').text(ca.cmOtherRefs6);
    if (ca.cmNewspaper0) {
        $('#cmNewspaper-0').removeClass('glyphicon-star-empty');
        $('#cmNewspaper-0').addClass('glyphicon-star');
    }
    $('#cmNewspaper-1').text(ca.cmNewspaper1);
    $('#cmNewspaper-2').text(ca.cmNewspaper2);
    $('#cmNewspaper-3').text(ca.cmNewspaper3);
    $('#cmNewspaper-4').text(ca.cmNewspaper4);
    $('#cmNewspaper-5').text(ca.cmNewspaper5);
    $('#cmNewspaper-6').text(ca.cmNewspaper6);
    if (ca.cmTv0) {
        $('#cmTv-0').removeClass('glyphicon-star-empty');
        $('#cmTv-0').addClass('glyphicon-star');
    }
    $('#cmTv-1').text(ca.cmTv1);
    $('#cmTv-2').text(ca.cmTv2);
    $('#cmTv-3').text(ca.cmTv3);
    $('#cmTv-4').text(ca.cmTv4);
    $('#cmTv-5').text(ca.cmTv5);
    $('#cmTv-6').text(ca.cmTv6);
    if (ca.cmInternet0) {
        $('#cmInternet-0').removeClass('glyphicon-star-empty');
        $('#cmInternet-0').addClass('glyphicon-star');
    }
    $('#cmInternet-1').text(ca.cmInternet1);
    $('#cmInternet-2').text(ca.cmInternet2);
    $('#cmInternet-3').text(ca.cmInternet3);
    $('#cmInternet-4').text(ca.cmInternet4);
    $('#cmInternet-5').text(ca.cmInternet5);
    $('#cmInternet-6').text(ca.cmInternet6);
    if (ca.cmSign0) {
        $('#cmSign-0').removeClass('glyphicon-star-empty');
        $('#cmSign-0').addClass('glyphicon-star');
    }
    $('#cmSign-1').text(ca.cmSign1);
    $('#cmSign-2').text(ca.cmSign2);
    $('#cmSign-3').text(ca.cmSign3);
    $('#cmSign-4').text(ca.cmSign4);
    $('#cmSign-5').text(ca.cmSign5);
    $('#cmSign-6').text(ca.cmSign6);
    if (ca.cmMate0) {
        $('#cmMate-0').removeClass('glyphicon-star-empty');
        $('#cmMate-0').addClass('glyphicon-star');
    }
    $('#cmMate-1').text(ca.cmMate1);
    $('#cmMate-2').text(ca.cmMate2);
    $('#cmMate-3').text(ca.cmMate3);
    $('#cmMate-4').text(ca.cmMate4);
    $('#cmMate-5').text(ca.cmMate5);
    $('#cmMate-6').text(ca.cmMate6);
    if (ca.cmOthers0) {
        $('#cmOthers-0').removeClass('glyphicon-star-empty');
        $('#cmOthers-0').addClass('glyphicon-star');
    }
    $('#cmOthers-1').text(ca.cmOthers1);
    $('#cmOthers-2').text(ca.cmOthers2);
    $('#cmOthers-3').text(ca.cmOthers3);
    $('#cmOthers-4').text(ca.cmOthers4);
    $('#cmOthers-5').text(ca.cmOthers5);
    $('#cmOthers-6').text(ca.cmOthers6);
    if (ca.cmMailAgpIn0) {
        $('#cmMailAgpIn-0').removeClass('glyphicon-star-empty');
        $('#cmMailAgpIn-0').addClass('glyphicon-star');
    }
    $('#cmMailAgpIn-1').text(ca.cmMailAgpIn1);
    $('#cmMailAgpIn-2').text(ca.cmMailAgpIn2);
    $('#cmMailAgpIn-3').text(ca.cmMailAgpIn3);
    $('#cmMailAgpIn-4').text(ca.cmMailAgpIn4_);
    $('#cmMailAgpIn-5').text(ca.cmMailAgpIn5);
    $('#cmMailAgpIn-6').text(ca.cmMailAgpIn6);
    if (ca.cmPostFlyerAgpIn0) {
        $('#cmPostFlyerAgpIn-0').removeClass('glyphicon-star-empty');
        $('#cmPostFlyerAgpIn-0').addClass('glyphicon-star');
    }
    $('#cmPostFlyerAgpIn-1').text(ca.cmPostFlyerAgpIn1);
    $('#cmPostFlyerAgpIn-2').text(ca.cmPostFlyerAgpIn2);
    $('#cmPostFlyerAgpIn-3').text(ca.cmPostFlyerAgpIn3);
    $('#cmPostFlyerAgpIn-4').text(ca.cmPostFlyerAgpIn4);
    $('#cmPostFlyerAgpIn-5').text(ca.cmPostFlyerAgpIn5);
    $('#cmPostFlyerAgpIn-6').text(ca.cmPostFlyerAgpIn6);
    if (ca.cmHandFlyerAgpIn0) {
        $('#cmHandFlyerAgpIn-0').removeClass('glyphicon-star-empty');
        $('#cmHandFlyerAgpIn-0').addClass('glyphicon-star');
    }
    $('#cmHandFlyerAgpIn-1').text(ca.cmHandFlyerAgpIn1);
    $('#cmHandFlyerAgpIn-2').text(ca.cmHandFlyerAgpIn2);
    $('#cmHandFlyerAgpIn-3').text(ca.cmHandFlyerAgpIn3);
    $('#cmHandFlyerAgpIn-4').text(ca.cmHandFlyerAgpIn4);
    $('#cmHandFlyerAgpIn-5').text(ca.cmHandFlyerAgpIn5);
    $('#cmHandFlyerAgpIn-6').text(ca.cmHandFlyerAgpIn6);
    if (ca.cmCpAgpIn0) {
        $('#cmCpAgpIn-0').removeClass('glyphicon-star-empty');
        $('#cmCpAgpIn-0').addClass('glyphicon-star');
    }
    $('#cmCpAgpIn-1').text(ca.cmCpAgpIn1);
    $('#cmCpAgpIn-2').text(ca.cmCpAgpIn2);
    $('#cmCpAgpIn-3').text(ca.cmCpAgpIn3);
    $('#cmCpAgpIn-4').text(ca.cmCpAgpIn4);
    $('#cmCpAgpIn-5').text(ca.cmCpAgpIn5);
    $('#cmCpAgpIn-6').text(ca.cmCpAgpIn6);
    if (ca.cmOutAgpOut0) {
        $('#cmOutAgpOut-0').removeClass('glyphicon-star-empty');
        $('#cmOutAgpOut-0').addClass('glyphicon-star');
    }
    $('#cmOutAgpOut-1').text(ca.cmOutAgpOut1);
    $('#cmOutAgpOut-2').text(ca.cmOutAgpOut2);
    $('#cmOutAgpOut-3').text(ca.cmOutAgpOut3);
    $('#cmOutAgpOut-4').text(ca.cmOutAgpOut4);
    $('#cmOutAgpOut-5').text(ca.cmOutAgpOut5);
    $('#cmOutAgpOut-6').text(ca.cmOutAgpOut6);
    if (ca.cmInAgpOut0) {
        $('#cmInAgpOut-0').removeClass('glyphicon-star-empty');
        $('#cmInAgpOut-0').addClass('glyphicon-star');
    }
    $('#cmInAgpOut-1').text(ca.cmInAgpOut1);
    $('#cmInAgpOut-2').text(ca.cmInAgpOut2);
    $('#cmInAgpOut-3').text(ca.cmInAgpOut3);
    $('#cmInAgpOut-4').text(ca.cmInAgpOut4);
    $('#cmInAgpOut-5').text(ca.cmInAgpOut5);
    $('#cmInAgpOut-6').text(ca.cmInAgpOut6);
    if (ca.cmBlogAgpOut0) {
        $('#cmBlogAgpOut-0').removeClass('glyphicon-star-empty');
        $('#cmBlogAgpOut-0').addClass('glyphicon-star');
    }
    $('#cmBlogAgpOut-1').text(ca.cmBlogAgpOut1);
    $('#cmBlogAgpOut-2').text(ca.cmBlogAgpOut2);
    $('#cmBlogAgpOut-3').text(ca.cmBlogAgpOut3);
    $('#cmBlogAgpOut-4').text(ca.cmBlogAgpOut4);
    $('#cmBlogAgpOut-5').text(ca.cmBlogAgpOut5);
    $('#cmBlogAgpOut-6').text(ca.cmBlogAgpOut6);
    if (ca.cmBagAgpOut0) {
        $('#cmBagAgpOut-0').removeClass('glyphicon-star-empty');
        $('#cmBagAgpOut-0').addClass('glyphicon-star');
    }
    $('#cmBagAgpOut-1').text(ca.cmBagAgpOut1);
    $('#cmBagAgpOut-2').text(ca.cmBagAgpOut2);
    $('#cmBagAgpOut-3').text(ca.cmBagAgpOut3);
    $('#cmBagAgpOut-4').text(ca.cmBagAgpOut4);
    $('#cmBagAgpOut-5').text(ca.cmBagAgpOut5);
    $('#cmBagAgpOut-6').text(ca.cmBagAgpOut6);
    if (ca.cmApoTotal0) {
        $('#cmApoTotal-0').removeClass('glyphicon-star-empty');
        $('#cmApoTotal-0').addClass('glyphicon-star');
    }
    $('#cmApoTotal-1').text(ca.cmApoTotal1);
    $('#cmApoTotal-2').text(ca.cmApoTotal2);
    $('#cmApoTotal-3').text(ca.cmApoTotal3);
    $('#cmApoTotal-4').text(ca.cmApoTotal4);
    $('#cmApoTotal-5').text(ca.cmApoTotal5);
    $('#cmApoTotal-6').text(ca.cmApoTotal6);
    if (ca.cmInApptRatio0) {
        $('#cmInApptRatio-0').removeClass('glyphicon-star-empty');
        $('#cmInApptRatio-0').addClass('glyphicon-star');
    }
    $('#cmInApptRatio-1').text(ca.cmInApptRatio1);
    $('#cmInApptRatio-2').text(ca.cmInApptRatio2);
    $('#cmInApptRatio-3').text(ca.cmInApptRatio3);
    $('#cmInApptRatio-4').text(ca.cmInApptRatio4);
    $('#cmInApptRatio-5').text(ca.cmInApptRatio5);
    $('#cmInApptRatio-6').text(ca.cmInApptRatio6);
    if (ca.cmOutApptRatio0) {
        $('#cmOutApptRatio-0').removeClass('glyphicon-star-empty');
        $('#cmOutApptRatio-0').addClass('glyphicon-star');
    }
    $('#cmOutApptRatio-1').text(ca.cmOutApptRatio1);
    $('#cmOutApptRatio-2').text(ca.cmOutApptRatio2);
    $('#cmOutApptRatio-3').text(ca.cmOutApptRatio3);
    $('#cmOutApptRatio-4').text(ca.cmOutApptRatio4);
    $('#cmOutApptRatio-5').text(ca.cmOutApptRatio5);
    $('#cmOutApptRatio-6').text(ca.cmOutApptRatio6);
    if (ca.cmPostPerApo0) {
        $('#cmPostPerApo-0').removeClass('glyphicon-star-empty');
        $('#cmPostPerApo-0').addClass('glyphicon-star');
    }
    $('#cmPostPerApo-6').text(ca.cmPostPerApo6);
    if (ca.cmHandPerApo0) {
        $('#cmHandPerApo-0').removeClass('glyphicon-star-empty');
        $('#cmHandPerApo-0').addClass('glyphicon-star');
    }
    $('#cmHandPerApo-6').text(ca.cmHandPerApo6);
    if (ca.cmOutPerApo0) {
        $('#cmOutPerApo-0').removeClass('glyphicon-star-empty');
        $('#cmOutPerApo-0').addClass('glyphicon-star');
    }
    $('#cmOutPerApo-6').text(ca.cmOutPerApo6);
    if (ca.cmBrAgpRatio0) {
        $('#cmBrAgpRatio-0').removeClass('glyphicon-star-empty');
        $('#cmBrAgpRatio-0').addClass('glyphicon-star');
    }
    $('#cmBrAgpRatio-1').text(ca.cmBrAgpRatio1);
    $('#cmBrAgpRatio-2').text(ca.cmBrAgpRatio2);
    $('#cmBrAgpRatio-3').text(ca.cmBrAgpRatio3);
    $('#cmBrAgpRatio-4').text(ca.cmBrAgpRatio4);
    $('#cmBrAgpRatio-5').text(ca.cmBrAgpRatio5);
    $('#cmBrAgpRatio-6').text(ca.cmBrAgpRatio6);
    if (ca.cmFaSum0) {
        $('#cmFaSum-0').removeClass('glyphicon-star-empty');
        $('#cmFaSum-0').addClass('glyphicon-star');
    }
    $('#cmFaSum-1').text(ca.cmFaSum1);
    $('#cmFaSum-2').text(ca.cmFaSum2);
    $('#cmFaSum-3').text(ca.cmFaSum3);
    $('#cmFaSum-4').text(ca.cmFaSum4);
    $('#cmFaSum-5').text(ca.cmFaSum5);
    $('#cmFaSum-6').text(ca.cmFaSum6);
    if (ca.cmShowRatio0) {
        $('#cmShowRatio-0').removeClass('glyphicon-star-empty');
        $('#cmShowRatio-0').addClass('glyphicon-star');
    }
    $('#cmShowRatio-1').text(ca.cmShowRatio1);
    $('#cmShowRatio-2').text(ca.cmShowRatio2);
    $('#cmShowRatio-3').text(ca.cmShowRatio3);
    $('#cmShowRatio-4').text(ca.cmShowRatio4);
    $('#cmShowRatio-5').text(ca.cmShowRatio5);
    $('#cmShowRatio-6').text(ca.cmShowRatio6);
    if (ca.cmTraining0) {
        $('#cmTraining-0').removeClass('glyphicon-star-empty');
        $('#cmTraining-0').addClass('glyphicon-star');
    }
    $('#cmTraining-1').prop('checked', ca.cmTraining1);
    $('#cmTraining-2').prop('checked', ca.cmTraining2);
    $('#cmTraining-3').prop('checked', ca.cmTraining3);
    $('#cmTraining-4').prop('checked', ca.cmTraining4);
    $('#cmTraining-5').prop('checked', ca.cmTraining5);
    if (ca.cmGot3_0) {
        $('#cmGot3-0').removeClass('glyphicon-star-empty');
        $('#cmGot3-0').addClass('glyphicon-star');
    }
    $('#cmGot3-1').prop('checked', ca.cmGot3_1);
    $('#cmGot3-2').prop('checked', ca.cmGot3_2);
    $('#cmGot3-3').prop('checked', ca.cmGot3_3);
    $('#cmGot3-4').prop('checked', ca.cmGot3_4);
    $('#cmGot3-5').prop('checked', ca.cmGot3_5);
    if (ca.cmInvitation0) {
        $('#cmInvitation-0').removeClass('glyphicon-star-empty');
        $('#cmInvitation-0').addClass('glyphicon-star');
    }
    $('#cmInvitation-1').prop('checked', ca.cmInvitation1);
    $('#cmInvitation-2').prop('checked', ca.cmInvitation2);
    $('#cmInvitation-3').prop('checked', ca.cmInvitation3);
    $('#cmInvitation-4').prop('checked', ca.cmInvitation4);
    $('#cmInvitation-5').prop('checked', ca.cmInvitation5);

    if (ca.salesAch0) {
        $('#salesAch-0').removeClass('glyphicon-star-empty');
        $('#salesAch-0').addClass('glyphicon-star');
    }
    $('#salesAch-1').text(ca.salesAch1);
    $('#salesAch-2').text(ca.salesAch2);
    $('#salesAch-3').text(ca.salesAch3);
    $('#salesAch-4').text(ca.salesAch4);
    $('#salesAch-5').text(ca.salesAch5);
    $('#salesAch-5').text(ca.salesAch6);
    if (ca.salesMonthly0) {
        $('#salesMonthly-0').removeClass('glyphicon-star-empty');
        $('#salesMonthly-0').addClass('glyphicon-star');
    }
    $('#salesMonthly-1').text(ca.salesMonthly1);
    $('#salesMonthly-2').text(ca.salesMonthly2);
    $('#salesMonthly-3').text(ca.salesMonthly3);
    $('#salesMonthly-4').text(ca.salesMonthly4);
    $('#salesMonthly-5').text(ca.salesMonthly5);
    $('#salesMonthly-6').text(ca.salesMonthly6);
    if (ca.salesAllPrepay0) {
        $('#salesAllPrepay-0').removeClass('glyphicon-star-empty');
        $('#salesAllPrepay-0').addClass('glyphicon-star');
    }
    $('#salesAllPrepay-1').text(ca.salesAllPrepay1);
    $('#salesAllPrepay-2').text(ca.salesAllPrepay2);
    $('#salesAllPrepay-3').text(ca.salesAllPrepay3);
    $('#salesAllPrepay-4').text(ca.salesAllPrepay4);
    $('#salesAllPrepay-5').text(ca.salesAllPrepay5);
    $('#salesAllPrepay-6').text(ca.salesAllPrepay6);
    if (ca.salesTotal0) {
        $('#salesTotal-0').removeClass('glyphicon-star-empty');
        $('#salesTotal-0').addClass('glyphicon-star');
    }
    $('#salesTotal-1').text(ca.salesTotal1);
    $('#salesTotal-2').text(ca.salesTotal2);
    $('#salesTotal-3').text(ca.salesTotal3);
    $('#salesTotal-4').text(ca.salesTotal4);
    $('#salesTotal-5').text(ca.salesTotal5);
    $('#salesTotal-6').text(ca.salesTotal6);
    if (ca.salesRatio0) {
        $('#salesRatio-0').removeClass('glyphicon-star-empty');
        $('#salesRatio-0').addClass('glyphicon-star');
    }
    $('#salesRatio-1').text(ca.salesRatio1);
    $('#salesRatio-2').text(ca.salesRatio2);
    $('#salesRatio-3').text(ca.salesRatio3);
    $('#salesRatio-4').text(ca.salesRatio4);
    $('#salesRatio-5').text(ca.salesRatio5);
    $('#salesRatio-6').text(ca.salesRatio6);
    if (ca.salesAchAppRatio0) {
        $('#salesAchAppRatio-0').removeClass('glyphicon-star-empty');
        $('#salesAchAppRatio-0').addClass('glyphicon-star');
    }
    $('#salesAchAppRatio-1').text(ca.salesAchAppRatio1);
    $('#salesAchAppRatio-2').text(ca.salesAchAppRatio2);
    $('#salesAchAppRatio-3').text(ca.salesAchAppRatio3);
    $('#salesAchAppRatio-4').text(ca.salesAchAppRatio4);
    $('#salesAchAppRatio-5').text(ca.salesAchAppRatio5);
    $('#salesAchAppRatio-6').text(ca.salesAchAppRatio6);
    if (ca.salesFaReview0) {
        $('#salesFaReview-0').removeClass('glyphicon-star-empty');
        $('#salesFaReview-0').addClass('glyphicon-star');
    }
    $('#salesFaReview-1').prop('checked', ca.salesFaReview1);
    $('#salesFaReview-2').prop('checked', ca.salesFaReview2);
    $('#salesFaReview-3').prop('checked', ca.salesFaReview3);
    $('#salesFaReview-4').prop('checked', ca.salesFaReview4);
    $('#salesFaReview-5').prop('checked', ca.salesFaReview5);
    if (ca.salesPriceReview0) {
        $('#salesPriceReview-0').removeClass('glyphicon-star-empty');
        $('#salesPriceReview-0').addClass('glyphicon-star');
    }
    $('#salesPriceReview-1').prop('checked', ca.salesPriceReview1);
    $('#salesPriceReview-2').prop('checked', ca.salesPriceReview2);
    $('#salesPriceReview-3').prop('checked', ca.salesPriceReview3);
    $('#salesPriceReview-4').prop('checked', ca.salesPriceReview4);
    $('#salesPriceReview-5').prop('checked', ca.salesPriceReview5);
    if (ca.salesAck0) {
        $('#salesAck-0').removeClass('glyphicon-star-empty');
        $('#salesAck-0').addClass('glyphicon-star');
    }
    $('#salesAck-1').prop('checked', ca.salesAck1);
    $('#salesAck-2').prop('checked', ca.salesAck2);
    $('#salesAck-3').prop('checked', ca.salesAck3);
    $('#salesAck-4').prop('checked', ca.salesAck4);
    $('#salesAck-5').prop('checked', ca.salesAck5);
    if (ca.salesTarget0) {
        $('#salesTarget-0').removeClass('glyphicon-star-empty');
        $('#salesTarget-0').addClass('glyphicon-star');
    }
    $('#salesTarget-1').prop('checked', ca.salesTarget1);
    $('#salesTarget-2').prop('checked', ca.salesTarget2);
    $('#salesTarget-3').prop('checked', ca.salesTarget3);
    $('#salesTarget-4').prop('checked', ca.salesTarget4);
    $('#salesTarget-5').prop('checked', ca.salesTarget5);
    if (ca.salesMotivation0) {
        $('#salesMotivation-0').removeClass('glyphicon-star-empty');
        $('#salesMotivation-0').addClass('glyphicon-star');
    }
    $('#salesMotivation-1').prop('checked', ca.salesMotivation1);
    $('#salesMotivation-2').prop('checked', ca.salesMotivation2);
    $('#salesMotivation-3').prop('checked', ca.salesMotivation3);
    $('#salesMotivation-4').prop('checked', ca.salesMotivation4);
    $('#salesMotivation-5').prop('checked', ca.salesMotivation5);
    if (ca.salesObstacle0) {
        $('#salesObstacle-0').removeClass('glyphicon-star-empty');
        $('#salesObstacle-0').addClass('glyphicon-star');
    }
    $('#salesObstacle-1').prop('checked', ca.salesObstacle1);
    $('#salesObstacle-2').prop('checked', ca.salesObstacle2);
    $('#salesObstacle-3').prop('checked', ca.salesObstacle3);
    $('#salesObstacle-4').prop('checked', ca.salesObstacle4);
    $('#salesObstacle-5').prop('checked', ca.salesObstacle5);

    if (ca.mgmtMeeting0) {
        $('#mgmtMeeting-0').removeClass('glyphicon-star-empty');
        $('#mgmtMeeting-0').addClass('glyphicon-star');
    }
    $('#mgmtMeeting-1').prop('checked', ca.mgmtMeeting1);
    $('#mgmtMeeting-2').prop('checked', ca.mgmtMeeting2);
    $('#mgmtMeeting-3').prop('checked', ca.mgmtMeeting3);
    $('#mgmtMeeting-4').prop('checked', ca.mgmtMeeting4);
    $('#mgmtMeeting-5').prop('checked', ca.mgmtMeeting5);
    if (ca.mgmtCa0) {
        $('#mgmtCa-0').removeClass('glyphicon-star-empty');
        $('#mgmtCa-0').addClass('glyphicon-star');
    }
    $('#mgmtCa-1').prop('checked', ca.mgmtCa1);
    $('#mgmtCa-2').prop('checked', ca.mgmtCa2);
    $('#mgmtCa-3').prop('checked', ca.mgmtCa3);
    $('#mgmtCa-4').prop('checked', ca.mgmtCa4);
    $('#mgmtCa-5').prop('checked', ca.mgmtCa5);
    if (ca.mgmtGp0) {
        $('#mgmtGp-0').removeClass('glyphicon-star-empty');
        $('#mgmtGp-0').addClass('glyphicon-star');
    }
    $('#mgmtGp-1').prop('checked', ca.mgmtGp1);
    $('#mgmtGp-2').prop('checked', ca.mgmtGp2);
    $('#mgmtGp-3').prop('checked', ca.mgmtGp3);
    $('#mgmtGp-4').prop('checked', ca.mgmtGp4);
    $('#mgmtGp-5').prop('checked', ca.mgmtGp5);
    if (ca.mgmtLearn0) {
        $('#mgmtLearn-0').removeClass('glyphicon-star-empty');
        $('#mgmtLearn-0').addClass('glyphicon-star');
    }
    $('#mgmtLearn-1').prop('checked', ca.mgmtLearn1);
    $('#mgmtLearn-2').prop('checked', ca.mgmtLearn2);
    $('#mgmtLearn-3').prop('checked', ca.mgmtLearn3);
    $('#mgmtLearn-4').prop('checked', ca.mgmtLearn4);
    $('#mgmtLearn-5').prop('checked', ca.mgmtLearn5);
    if (ca.mgmtSheet0) {
        $('#mgmtSheet-0').removeClass('glyphicon-star-empty');
        $('#mgmtSheet-0').addClass('glyphicon-star');
    }
    $('#mgmtSheet-1').prop('checked', ca.mgmtSheet1);
    $('#mgmtSheet-2').prop('checked', ca.mgmtSheet2);
    $('#mgmtSheet-3').prop('checked', ca.mgmtSheet3);
    $('#mgmtSheet-4').prop('checked', ca.mgmtSheet4);
    $('#mgmtSheet-5').prop('checked', ca.mgmtSheet5);
    if (ca.mgmtPolicy0) {
        $('#mgmtPolicy-0').removeClass('glyphicon-star-empty');
        $('#mgmtPolicy-0').addClass('glyphicon-star');
    }
    $('#mgmtPolicy-1').prop('checked', ca.mgmtPolicy1);
    $('#mgmtPolicy-2').prop('checked', ca.mgmtPolicy2);
    $('#mgmtPolicy-3').prop('checked', ca.mgmtPolicy3);
    $('#mgmtPolicy-4').prop('checked', ca.mgmtPolicy4);
    $('#mgmtPolicy-5').prop('checked', ca.mgmtPolicy5);
    if (ca.mgmtCompiantSales0) {
        $('#mgmtCompiantSales-0').removeClass('glyphicon-star-empty');
        $('#mgmtCompiantSales-0').addClass('glyphicon-star');
    }
    $('#mgmtCompiantSales-1').prop('checked', ca.mgmtCompiantSales1);
    $('#mgmtCompiantSales-2').prop('checked', ca.mgmtCompiantSales2);
    $('#mgmtCompiantSales-3').prop('checked', ca.mgmtCompiantSales3);
    $('#mgmtCompiantSales-4').prop('checked', ca.mgmtCompiantSales4);
    $('#mgmtCompiantSales-5').prop('checked', ca.mgmtCompiantSales5);
    if (ca.mgmtCompiantMethod0) {
        $('#mgmtCompiantMethod-0').removeClass('glyphicon-star-empty');
        $('#mgmtCompiantMethod-0').addClass('glyphicon-star');
    }
    $('#mgmtCompiantMethod-1').prop('checked', ca.mgmtCompiantMethod1);
    $('#mgmtCompiantMethod-2').prop('checked', ca.mgmtCompiantMethod2);
    $('#mgmtCompiantMethod-3').prop('checked', ca.mgmtCompiantMethod3);
    $('#mgmtCompiantMethod-4').prop('checked', ca.mgmtCompiantMethod4);
    $('#mgmtCompiantMethod-5').prop('checked', ca.mgmtCompiantMethod5);
    if (ca.mgmtCompiantProduct0) {
        $('#mgmtCompiantProduct-0').removeClass('glyphicon-star-empty');
        $('#mgmtCompiantProduct-0').addClass('glyphicon-star');
    }
    $('#mgmtCompiantProduct-1').prop('checked', ca.mgmtCompiantProduct1);
    $('#mgmtCompiantProduct-2').prop('checked', ca.mgmtCompiantProduct2);
    $('#mgmtCompiantProduct-3').prop('checked', ca.mgmtCompiantProduct3);
    $('#mgmtCompiantProduct-4').prop('checked', ca.mgmtCompiantProduct4);
    $('#mgmtCompiantProduct-5').prop('checked', ca.mgmtCompiantProduct5);
    if (ca.mgmtCompiantAd0) {
        $('#mgmtCompiantAd-0').removeClass('glyphicon-star-empty');
        $('#mgmtCompiantAd-0').addClass('glyphicon-star');
    }
    $('#mgmtCompiantAd-1').prop('checked', ca.mgmtCompiantAd1);
    $('#mgmtCompiantAd-2').prop('checked', ca.mgmtCompiantAd2);
    $('#mgmtCompiantAd-3').prop('checked', ca.mgmtCompiantAd3);
    $('#mgmtCompiantAd-4').prop('checked', ca.mgmtCompiantAd4);
    $('#mgmtCompiantAd-5').prop('checked', ca.mgmtCompiantAd5);
    if (ca.mgmtCompiantTraining0) {
        $('#mgmtCompiantTraining-0').removeClass('glyphicon-star-empty');
        $('#mgmtCompiantTraining-0').addClass('glyphicon-star');
    }
    $('#mgmtTraining-1').prop('checked', ca.mgmtCompiantTraining1);
    $('#mgmtTraining-2').prop('checked', ca.mgmtCompiantTraining2);
    $('#mgmtTraining-3').prop('checked', ca.mgmtCompiantTraining3);
    $('#mgmtTraining-4').prop('checked', ca.mgmtCompiantTraining4);
    $('#mgmtTraining-5').prop('checked', ca.mgmtCompiantTraining5);
    if (ca.mgmtCompiantReport0) {
        $('#mgmtCompiantReport-0').removeClass('glyphicon-star-empty');
        $('#mgmtCompiantReport-0').addClass('glyphicon-star');
    }
    $('#mgmtReport-1').prop('checked', ca.mgmtCompiantReport1);
    $('#mgmtReport-2').prop('checked', ca.mgmtCompiantReport2);
    $('#mgmtReport-3').prop('checked', ca.mgmtCompiantReport3);
    $('#mgmtReport-4').prop('checked', ca.mgmtCompiantReport4);
    $('#mgmtReport-5').prop('checked', ca.mgmtCompiantReport5);
    if (ca.mgmtPlan0) {
        $('#mgmtPlan-0').removeClass('glyphicon-star-empty');
        $('#mgmtPlan-0').addClass('glyphicon-star');
    }
    $('#mgmtPlan-1').prop('checked', ca.mgmtPlan1);
    $('#mgmtPlan-2').prop('checked', ca.mgmtPlan2);
    $('#mgmtPlan-3').prop('checked', ca.mgmtPlan3);
    $('#mgmtPlan-4').prop('checked', ca.mgmtPlan4);
    $('#mgmtPlan-5').prop('checked', ca.mgmtPlan5);
    if (ca.mgmtMaintain0) {
        $('#mgmtMaintain-0').removeClass('glyphicon-star-empty');
        $('#mgmtMaintain-0').addClass('glyphicon-star');
    }
    $('#mgmtMaintain-1').prop('checked', ca.mgmtMaintain1);
    $('#mgmtMaintain-2').prop('checked', ca.mgmtMaintain2);
    $('#mgmtMaintain-3').prop('checked', ca.mgmtMaintain3);
    $('#mgmtMaintain-4').prop('checked', ca.mgmtMaintain4);
    $('#mgmtMaintain-5').prop('checked', ca.mgmtMaintain5);
    if (ca.mgmtFace2Face0) {
        $('#mgmtFace2Face-0').removeClass('glyphicon-star-empty');
        $('#mgmtFace2Face-0').addClass('glyphicon-star');
    }
    $('#mgmtFace2Face-1').prop('checked', ca.mgmtFace2Face1);
    $('#mgmtFace2Face-2').prop('checked', ca.mgmtFace2Face2);
    $('#mgmtFace2Face-3').prop('checked', ca.mgmtFace2Face3);
    $('#mgmtFace2Face-4').prop('checked', ca.mgmtFace2Face4);
    $('#mgmtFace2Face-5').prop('checked', ca.mgmtFace2Face5);

    $('#clubSalesRatio').text(ca.clubSalesRatio);
    $('#clubAchAppRatio').text(ca.clubAchAppRatio);
    $('#clubAch-1').text(ca.clubAch1);
    $('#clubAch-2').text(ca.clubAch2);
    $('#clubAch-3').text(ca.clubAch3);
    $('#clubAch-4').text(ca.clubAch4);
    $('#clubAch-5').text(ca.clubAch5);
    $('#clubAch-6').text(ca.clubAch6);
    $('#clubMm-1').text(ca.clubMm1);
    $('#clubMm-2').text(ca.clubMm2);
    $('#clubMm-3').text(ca.clubMm3);
    $('#clubMm-4').text(ca.clubMm4);
    $('#clubMm-5').text(ca.clubMm5);
    $('#clubMm-6').text(ca.clubMm6);
    $('#clubApp-1').text(ca.clubApp1);
    $('#clubApp-2').text(ca.clubApp2);
    $('#clubApp-3').text(ca.clubApp3);
    $('#clubApp-4').text(ca.clubApp4);
    $('#clubApp-5').text(ca.clubApp5);
    $('#clubApp-6').text(ca.clubApp6);
    $('#clubNs-1').text(ca.clubNs1);
    $('#clubNs-2').text(ca.clubNs2);
    $('#clubNs-3').text(ca.clubNs3);
    $('#clubNs-4').text(ca.clubNs4);
    $('#clubNs-5').text(ca.clubNs5);
    $('#clubNs-6').text(ca.clubNs6);
    $('#clubLx-1').text(ca.clubLx1);
    $('#clubLx-2').text(ca.clubLx2);
    $('#clubLx-3').text(ca.clubLx3);
    $('#clubLx-4').text(ca.clubLx4);
    $('#clubLx-5').text(ca.clubLx5);
    $('#clubLx-6').text(ca.clubLx6);

    $('#staff1SalesRatio').text(ca.staff1SalesRatio);
    $('#staff1AchAppRatio').text(ca.staff1AchAppRatio);
    $('#staff1Ach-1').text(ca.staff1Ach1);
    $('#staff1Ach-2').text(ca.staff1Ach2);
    $('#staff1Ach-3').text(ca.staff1Ach3);
    $('#staff1Ach-4').text(ca.staff1Ach4);
    $('#staff1Ach-5').text(ca.staff1Ach5);
    $('#staff1Ach-6').text(ca.staff1Ach6);
    $('#staff1Mm-1').text(ca.staff1Mm1);
    $('#staff1Mm-2').text(ca.staff1Mm2);
    $('#staff1Mm-3').text(ca.staff1Mm3);
    $('#staff1Mm-4').text(ca.staff1Mm4);
    $('#staff1Mm-5').text(ca.staff1Mm5);
    $('#staff1Mm-6').text(ca.staff1Mm6);
    $('#staff1App-1').text(ca.staff1App1);
    $('#staff1App-2').text(ca.staff1App2);
    $('#staff1App-3').text(ca.staff1App3);
    $('#staff1App-4').text(ca.staff1App4);
    $('#staff1App-5').text(ca.staff1App5);
    $('#staff1App-6').text(ca.staff1App6);
    $('#staff1Ns-1').text(ca.staff1Ns1);
    $('#staff1Ns-2').text(ca.staff1Ns2);
    $('#staff1Ns-3').text(ca.staff1Ns3);
    $('#staff1Ns-4').text(ca.staff1Ns4);
    $('#staff1Ns-5').text(ca.staff1Ns5);
    $('#staff1Ns-6').text(ca.staff1Ns6);
    $('#staff1Lx-1').text(ca.staff1Lx1);
    $('#staff1Lx-2').text(ca.staff1Lx2);
    $('#staff1Lx-3').text(ca.staff1Lx3);
    $('#staff1Lx-4').text(ca.staff1Lx4);
    $('#staff1Lx-5').text(ca.staff1Lx5);
    $('#staff1Lx-6').text(ca.staff1Lx6);

    $('#staff2SalesRatio').text(ca.staff2SalesRatio);
    $('#staff2AchAppRatio').text(ca.staff2AchAppRatio);
    $('#staff2Ach-1').text(ca.staff2Ach1);
    $('#staff2Ach-2').text(ca.staff2Ach2);
    $('#staff2Ach-3').text(ca.staff2Ach3);
    $('#staff2Ach-4').text(ca.staff2Ach4);
    $('#staff2Ach-5').text(ca.staff2Ach5);
    $('#staff2Ach-6').text(ca.staff2Ach6);
    $('#staff2Mm-1').text(ca.staff2Mm1);
    $('#staff2Mm-2').text(ca.staff2Mm2);
    $('#staff2Mm-3').text(ca.staff2Mm3);
    $('#staff2Mm-4').text(ca.staff2Mm4);
    $('#staff2Mm-5').text(ca.staff2Mm5);
    $('#staff2Mm-6').text(ca.staff2Mm6);
    $('#staff2App-1').text(ca.staff2App1);
    $('#staff2App-2').text(ca.staff2App2);
    $('#staff2App-3').text(ca.staff2App3);
    $('#staff2App-4').text(ca.staff2App4);
    $('#staff2App-5').text(ca.staff2App5);
    $('#staff2App-6').text(ca.staff2App6);
    $('#staff2Ns-1').text(ca.staff2Ns1);
    $('#staff2Ns-2').text(ca.staff2Ns2);
    $('#staff2Ns-3').text(ca.staff2Ns3);
    $('#staff2Ns-4').text(ca.staff2Ns4);
    $('#staff2Ns-5').text(ca.staff2Ns5);
    $('#staff2Ns-6').text(ca.staff2Ns6);
    $('#staff2Lx-1').text(ca.staff2Lx1);
    $('#staff2Lx-2').text(ca.staff2Lx2);
    $('#staff2Lx-3').text(ca.staff2Lx3);
    $('#staff2Lx-4').text(ca.staff2Lx4);
    $('#staff2Lx-5').text(ca.staff2Lx5);
    $('#staff2Lx-6').text(ca.staff2Lx6);

    $('#staff3SalesRatio').text(ca.staff3SalesRatio);
    $('#staff3AchAppRatio').text(staff3AchAppRatio);
    $('#staff3Ach-1').text(ca.staff3Ach1);
    $('#staff3Ach-2').text(ca.staff3Ach2);
    $('#staff3Ach-3').text(ca.staff3Ach3);
    $('#staff3Ach-4').text(ca.staff3Ach4);
    $('#staff3Ach-5').text(ca.staff3Ach5);
    $('#staff3Ach-6').text(ca.staff3Ach6);
    $('#staff3Mm-1').text(ca.staff3Mm1);
    $('#staff3Mm-2').text(ca.staff3Mm2);
    $('#staff3Mm-3').text(ca.staff3Mm3);
    $('#staff3Mm-4').text(ca.staff3Mm4);
    $('#staff3Mm-5').text(ca.staff3Mm5);
    $('#staff3Mm-6').text(ca.staff3Mm6);
    $('#staff3App-1').text(ca.staff3App1);
    $('#staff3App-2').text(ca.staff3App2);
    $('#staff3App-3').text(ca.staff3App3);
    $('#staff3App-4').text(ca.staff3App4);
    $('#staff3App-5').text(ca.staff3App5);
    $('#staff3App-6').text(ca.staff3App6);
    $('#staff3Ns-1').text(ca.staff3Ns1);
    $('#staff3Ns-2').text(ca.staff3Ns2);
    $('#staff3Ns-3').text(ca.staff3Ns3);
    $('#staff3Ns-4').text(ca.staff3Ns4);
    $('#staff3Ns-5').text(ca.staff3Ns5);
    $('#staff3Ns-6').text(ca.staff3Ns6);
    $('#staff3Lx-1').text(ca.staff3Lx1);
    $('#staff3Lx-2').text(ca.staff3Lx2);
    $('#staff3Lx-3').text(ca.staff3Lx3);
    $('#staff3Lx-4').text(ca.staff3Lx4);
    $('#staff3Lx-5').text(ca.staff3Lx5);
    $('#staff3Lx-6').text(ca.staff3Lx6);

    $('#staff4SalesRatio').text(ca.staff4SalesRatio);
    $('#staff4AchAppRatio').text(ca.staff4AchAppRatio);
    $('#staff4Ach-1').text(ca.staff4Ach1);
    $('#staff4Ach-2').text(ca.staff4Ach2);
    $('#staff4Ach-3').text(ca.staff4Ach3);
    $('#staff4Ach-4').text(ca.staff4Ach4);
    $('#staff4Ach-5').text(ca.staff4Ach5);
    $('#staff4Ach-6').text(ca.staff4Ach6);
    $('#staff4Mm-1').text(ca.staff4Mm1);
    $('#staff4Mm-2').text(ca.staff4Mm2);
    $('#staff4Mm-3').text(ca.staff4Mm3);
    $('#staff4Mm-4').text(ca.staff4Mm4);
    $('#staff4Mm-5').text(ca.staff4Mm5);
    $('#staff4Mm-6').text(ca.staff4Mm6);
    $('#staff4App-1').text(ca.staff4App1);
    $('#staff4App-2').text(ca.staff4App2);
    $('#staff4App-3').text(ca.staff4App3);
    $('#staff4App-4').text(ca.staff4App4);
    $('#staff4App-5').text(ca.staff4App5);
    $('#staff4App-6').text(ca.staff4App6);
    $('#staff4Ns-1').text(ca.staff4Ns1);
    $('#staff4Ns-2').text(ca.staff4Ns2);
    $('#staff4Ns-3').text(ca.staff4Ns3);
    $('#staff4Ns-4').text(ca.staff4Ns4);
    $('#staff4Ns-5').text(ca.staff4Ns5);
    $('#staff4Ns-6').text(ca.staff4Ns6);
    $('#staff4Lx-1').text(ca.staff4Lx1);
    $('#staff4Lx-2').text(ca.staff4Lx2);
    $('#staff4Lx-3').text(ca.staff4Lx3);
    $('#staff4Lx-4').text(ca.staff4Lx4);
    $('#staff4Lx-5').text(ca.staff4Lx5);
    $('#staff4Lx-6').text(ca.staff4Lx6);

    $('#staff5SalesRatio').text(ca.staff5SalesRatio);
    $('#staff5AchAppRatio').text(ca.staff5AchAppRatio);
    $('#staff5Ach-1').text(ca.staff5Ach1);
    $('#staff5Ach-2').text(ca.staff5Ach2);
    $('#staff5Ach-3').text(ca.staff5Ach3);
    $('#staff5Ach-4').text(ca.staff5Ach4);
    $('#staff5Ach-5').text(ca.staff5Ach5);
    $('#staff5Ach-6').text(ca.staff5Ach6);
    $('#staff5Mm-1').text(ca.staff5Mm1);
    $('#staff5Mm-2').text(ca.staff5Mm2);
    $('#staff5Mm-3').text(ca.staff5Mm3);
    $('#staff5Mm-4').text(ca.staff5Mm4);
    $('#staff5Mm-5').text(ca.staff5Mm5);
    $('#staff5Mm-6').text(ca.staff5Mm6);
    $('#staff5App-1').text(ca.staff5App1);
    $('#staff5App-2').text(ca.staff5App2);
    $('#staff5App-3').text(ca.staff5App3);
    $('#staff5App-4').text(ca.staff5App4);
    $('#staff5App-5').text(ca.staff5App5);
    $('#staff5App-6').text(ca.staff5App6);
    $('#staff5Ns-1').text(ca.staff5Ns1);
    $('#staff5Ns-2').text(ca.staff5Ns2);
    $('#staff5Ns-3').text(ca.staff5Ns3);
    $('#staff5Ns-4').text(ca.staff5Ns4);
    $('#staff5Ns-5').text(ca.staff5Ns5);
    $('#staff5Ns-6').text(ca.staff5Ns6);
    $('#staff5Lx-1').text(ca.staff5Lx1);
    $('#staff5Lx-2').text(ca.staff5Lx2);
    $('#staff5Lx-3').text(ca.staff5Lx3);
    $('#staff5Lx-4').text(ca.staff5Lx4);
    $('#staff5Lx-5').text(ca.staff5Lx5);
    $('#staff5Lx-6').text(ca.staff5Lx6);

    $('#staff6SalesRatio').text(ca.staff6SalesRatio);
    $('#staff6AchAppRatio').text(ca.staff6AchAppRatio);
    $('#staff6Ach-1').text(ca.staff6Ach1);
    $('#staff6Ach-2').text(ca.staff6Ach2);
    $('#staff6Ach-3').text(ca.staff6Ach3);
    $('#staff6Ach-4').text(ca.staff6Ach4);
    $('#staff6Ach-5').text(ca.staff6Ach5);
    $('#staff6Ach-6').text(ca.staff6Ach6);
    $('#staff6Mm-1').text(ca.staff6Mm1);
    $('#staff6Mm-2').text(ca.staff6Mm2);
    $('#staff6Mm-3').text(ca.staff6Mm3);
    $('#staff6Mm-4').text(ca.staff6Mm4);
    $('#staff6Mm-5').text(ca.staff6Mm5);
    $('#staff6Mm-6').text(ca.staff6Mm6);
    $('#staff6App-1').text(ca.staff6App1);
    $('#staff6App-2').text(ca.staff6App2);
    $('#staff6App-3').text(ca.staff6App3);
    $('#staff6App-4').text(ca.staff6App4);
    $('#staff6App-5').text(ca.staff6App5);
    $('#staff6App-6').text(ca.staff6App6);
    $('#staff6Ns-1').text(ca.staff6Ns1);
    $('#staff6Ns-2').text(ca.staff6Ns2);
    $('#staff6Ns-3').text(ca.staff6Ns3);
    $('#staff6Ns-4').text(ca.staff6Ns4);
    $('#staff6Ns-5').text(ca.staff6Ns5);
    $('#staff6Ns-6').text(ca.staff6Ns6);
    $('#staff6Lx-1').text(ca.staff6Lx1);
    $('#staff6Lx-2').text(ca.staff6Lx2);
    $('#staff6Lx-3').text(ca.staff6Lx3);
    $('#staff6Lx-4').text(ca.staff6Lx4);
    $('#staff6Lx-5').text(ca.staff6Lx5);
    $('#staff6Lx-6').text(ca.staff6Lx6);

    $('#thisPlanL').text(ca.thisPlan);
    $('#nextPlanL').text(ca.nextPlan);

    $('td').each(function() {
        if (0 == +$(this).text()) {
            $(this).text('');
        }
    });
}

// save CA
$("#btnSave").click(function() {
    $('#btnSave').prop("disabled", true);

    runFormula();

    var ca = {};

    ca.id = window.caId;

    ca.clubId = +$('#userId').text();
    ca.caYear = thisYear;
    ca.caMonth = thisMonth;

    ca.goalsTm = +$('#goalsTm').text();
    ca.goalsLastTm = +$('#goalsLastTm').text();
    ca.goalsLastActive = +$('#goalsLastActive').text();
    ca.goalsLastShowRatio = parseFloat($('#goalsLastShowRatio').text());
    ca.goalsLastSalesRatio = parseFloat($('#goalsLastSalesRatio').text());
    ca.goalsExitsRatio = parseFloat($('#goalsExitsRatio').text());
    ca.goalsNewSales = +$('#goalsNewSales').text();
    ca.goalsAppoints = +$('#goalsAppoints').text();

    ca.svcTm0 = $('#svcTm-0').hasClass('glyphicon-star');
    ca.svcTm1 = +$('#svcTm-1').text();
    ca.svcTm2 = +$('#svcTm-2').text();
    ca.svcTm3 = +$('#svcTm-3').text();
    ca.svcTm4 = +$('#svcTm-4').text();
    ca.svcTm5 = +$('#svcTm-5').text();
    ca.svcTm6 = +$('#svcTm-6').text();
    ca.svcShift0 = $('#svcShift-0').hasClass('glyphicon-star');
    ca.svcShift1 = +$('#svcShift-1').text();
    ca.svcShift2 = +$('#svcShift-2').text();
    ca.svcShift3 = +$('#svcShift-3').text();
    ca.svcShift4 = +$('#svcShift-4').text();
    ca.svcShift5 = +$('#svcShift-5').text();
    ca.svcShift6 = +$('#svcShift-6').text();
    ca.svcHold0 = $('#svcHold-0').hasClass('glyphicon-star');
    ca.svcHold1 = +$('#svcHold-1').text();
    ca.svcHold2 = +$('#svcHold-2').text();
    ca.svcHold3 = +$('#svcHold-3').text();
    ca.svcHold4 = +$('#svcHold-4').text();
    ca.svcHold5 = +$('#svcHold-5').text();
    ca.svcHold6 = +$('#svcHold-6').text();
    ca.svcActive0 = $('#svcActive-0').hasClass('glyphicon-star');
    ca.svcActive1 = +$('#svcActive-1').text();
    ca.svcActive2 = +$('#svcActive-2').text();
    ca.svcActive3 = +$('#svcActive-3').text();
    ca.svcActive4 = +$('#svcActive-4').text();
    ca.svcActive5 = +$('#svcActive-5').text();
    ca.svcActive6 = +$('#svcActive-6').text();
    ca.svcHoldRatio0 = parseFloat($('#svcHoldRatio-0').hasClass('glyphicon-star'));
    ca.svcHoldRatio1 = parseFloat($('#svcHoldRatio-1').text());
    ca.svcHoldRatio2 = parseFloat($('#svcHoldRatio-2').text());
    ca.svcHoldRatio3 = parseFloat($('#svcHoldRatio-3').text());
    ca.svcHoldRatio4 = parseFloat($('#svcHoldRatio-4').text());
    ca.svcHoldRatio5 = parseFloat($('#svcHoldRatio-5').text());
    ca.svcHoldRatio6 = parseFloat($('#svcHoldRatio-6').text());
    ca.svcTotalWo0 = $('#svcTotalWo-0').hasClass('glyphicon-star');
    ca.svcTotalWo1 = +$('#svcTotalWo-1').text();
    ca.svcTotalWo2 = +$('#svcTotalWo-2').text();
    ca.svcTotalWo3 = +$('#svcTotalWo-3').text();
    ca.svcTotalWo4 = +$('#svcTotalWo-4').text();
    ca.svcTotalWo5 = +$('#svcTotalWo-5').text();
    ca.svcTotalWo6 = +$('#svcTotalWo-6').text();
    ca.svcAvgWo0 = $('#svcAvgWo-0').hasClass('glyphicon-star');
    ca.svcAvgWo1 = parseFloat($('#svcAvgWo-1').text());
    ca.svcAvgWo2 = parseFloat($('#svcAvgWo-2').text());
    ca.svcAvgWo3 = parseFloat($('#svcAvgWo-3').text());
    ca.svcAvgWo4 = parseFloat($('#svcAvgWo-4').text());
    ca.svcAvgWo5 = parseFloat($('#svcAvgWo-5').text());
    ca.svcAvgWo6 = parseFloat($('#svcAvgWo-6').text());
    ca.svcMaxWo0 = $('#svcMaxWo-0').hasClass('glyphicon-star');
    ca.svcMaxWo1 = +$('#svcMaxWo-1').text();
    ca.svcMaxWo2 = +$('#svcMaxWo-2').text();
    ca.svcMaxWo3 = +$('#svcMaxWo-3').text();
    ca.svcMaxWo4 = +$('#svcMaxWo-4').text();
    ca.svcMaxWo5 = +$('#svcMaxWo-5').text();
    ca.svcMaxWo6 = +$('#svcMaxWo-6').text();
    ca.svcExits0 = $('#svcExits-0').hasClass('glyphicon-star');
    ca.svcExits1 = +$('#svcExits-1').text();
    ca.svcExits2 = +$('#svcExits-2').text();
    ca.svcExits3 = +$('#svcExits-3').text();
    ca.svcExits4 = +$('#svcExits-4').text();
    ca.svcExits5 = +$('#svcExits-5').text();
    ca.svcExits6 = +$('#svcExits-6').text();
    ca.svcExitsRatio0 = $('#svcExitsRatio-0').hasClass('glyphicon-star');
    ca.svcExitsRatio1 = parseFloat($('#svcExitsRatio-1').text());
    ca.svcExitsRatio2 = parseFloat($('#svcExitsRatio-2').text());
    ca.svcExitsRatio3 = parseFloat($('#svcExitsRatio-3').text());
    ca.svcExitsRatio4 = parseFloat($('#svcExitsRatio-4').text());
    ca.svcExitsRatio5 = parseFloat($('#svcExitsRatio-5').text());
    ca.svcExitsRatio6 = parseFloat($('#svcExitsRatio-6').text());
    ca.svcMeasure0 = $('#svcMeasure-0').hasClass('glyphicon-star');
    ca.svcMeasure1 = +$('#svcMeasure-1').text();
    ca.svcMeasure2 = +$('#svcMeasure-2').text();
    ca.svcMeasure3 = +$('#svcMeasure-3').text();
    ca.svcMeasure4 = +$('#svcMeasure-4').text();
    ca.svcMeasure5 = +$('#svcMeasure-5').text();
    ca.svcMeasure6 = +$('#svcMeasure-6').text();
    ca.svcMeasureRatio0 = $('#svcMeasureRatio-0').hasClass('glyphicon-star');
    ca.svcMeasureRatio1 = parseFloat($('#svcMeasureRatio-1').text());
    ca.svcMeasureRatio2 = parseFloat($('#svcMeasureRatio-2').text());
    ca.svcMeasureRatio3 = parseFloat($('#svcMeasureRatio-3').text());
    ca.svcMeasureRatio4 = parseFloat($('#svcMeasureRatio-4').text());
    ca.svcMeasureRatio5 = parseFloat($('#svcMeasureRatio-5').text());
    ca.svcMeasureRatio6 = parseFloat($('#svcMeasureRatio-6').text());
    ca.svc12_0 = $('#svc12-0').hasClass('glyphicon-star');
    ca.svc12_5 = +$('#svc12-5').text();
    ca.svc12_6 = +$('#svc12-6').text();
    ca.svc8to11_0 = $('#svc8to11-0').hasClass('glyphicon-star');
    ca.svc8to11_5 = +$('#svc8to11-5').text();
    ca.svc8to11_6 = +$('#svc8to11-6').text();
    ca.svc4to7_0 = $('#svc4to7-0').hasClass('glyphicon-star');
    ca.svc4to7_5 = +$('#svc4to7-5').text();
    ca.svc4to7_6 = +$('#svc4to7-6').text();
    ca.svc1to3_0 = $('#svc1to3-0').hasClass('glyphicon-star');
    ca.svc1to3_5 = +$('#svc1to3-5').text();
    ca.svc1to3_6 = +$('#svc1to3-6').text();
    ca.svc0_0 = $('#svc0-0').hasClass('glyphicon-star');
    ca.svc0_5 = +$('#svc0-5').text();
    ca.svc0_6 = +$('#svc0-6').text();
    ca.svc3More0 = $('#svc3More-0').hasClass('glyphicon-star');
    ca.svc3More1 = $('#svc3More-1').is(':checked');
    ca.svc3More2 = $('#svc3More-2').is(':checked');
    ca.svc3More3 = $('#svc3More-3').is(':checked');
    ca.svc3More4 = $('#svc3More-4').is(':checked');
    ca.svc3More5 = $('#svc3More-5').is(':checked');
    ca.svcInactive0 = $('#svcInactive-0').hasClass('glyphicon-star');
    ca.svcInactive1 = $('#svcInactive-1').is(':checked');
    ca.svcInactive2 = $('#svcInactive-2').is(':checked');
    ca.svcInactive3 = $('#svcInactive-3').is(':checked');
    ca.svcInactive4 = $('#svcInactive-4').is(':checked');
    ca.svcInactive5 = $('#svcInactive-5').is(':checked');
    ca.svcFwoReview0 = $('#svcFwoReview-0').hasClass('glyphicon-star');
    ca.svcFwoReview1 = $('#svcFwoReview-1').is(':checked');
    ca.svcFwoReview2 = $('#svcFwoReview-2').is(':checked');
    ca.svcFwoReview3 = $('#svcFwoReview-3').is(':checked');
    ca.svcFwoReview4 = $('#svcFwoReview-4').is(':checked');
    ca.svcFwoReview5 = $('#svcFwoReview-5').is(':checked');
    ca.svcInterview0 = $('#svcInterview-0').hasClass('glyphicon-star');
    ca.svcInterview1 = $('#svcInterview-1').is(':checked');
    ca.svcInterview2 = $('#svcInterview-2').is(':checked');
    ca.svcInterview3 = $('#svcInterview-3').is(':checked');
    ca.svcInterview4 = $('#svcInterview-4').is(':checked');
    ca.svcInterview5 = $('#svcInterview-5').is(':checked');
    ca.svcThanks0 = $('#svcThanks-0').hasClass('glyphicon-star');
    ca.svcThanks1 = $('#svcThanks-1').is(':checked');
    ca.svcThanks2 = $('#svcThanks-2').is(':checked');
    ca.svcThanks3 = $('#svcThanks-3').is(':checked');
    ca.svcThanks4 = $('#svcThanks-4').is(':checked');
    ca.svcThanks5 = $('#svcThanks-5').is(':checked');
    ca.svc3C0 = $('#svc3C-0').hasClass('glyphicon-star');
    ca.svc3C1 = $('#svc3C-1').is(':checked');
    ca.svc3C2 = $('#svc3C-2').is(':checked');
    ca.svc3C3 = $('#svc3C-3').is(':checked');
    ca.svc3C4 = $('#svc3C-4').is(':checked');
    ca.svc3C5 = $('#svc3C-5').is(':checked');
    ca.svcReward0 = $('#svcReward-0').hasClass('glyphicon-star');
    ca.svcReward1 = $('#svcReward-1').is(':checked');
    ca.svcReward2 = $('#svcReward-2').is(':checked');
    ca.svcReward3 = $('#svcReward-3').is(':checked');
    ca.svcReward4 = $('#svcReward-4').is(':checked');
    ca.svcReward5 = $('#svcReward-5').is(':checked');
    ca.svcLoyal0 = $('#svcLoyal-0').hasClass('glyphicon-star');
    ca.svcLoyal1 = $('#svcLoyal-1').is(':checked');
    ca.svcLoyal2 = $('#svcLoyal-2').is(':checked');
    ca.svcLoyal3 = $('#svcLoyal-3').is(':checked');
    ca.svcLoyal4 = $('#svcLoyal-4').is(':checked');
    ca.svcLoyal5 = $('#svcLoyal-5').is(':checked');

    ca.cmPostFlyer0 = $('#cmPostFlyer-0').hasClass('glyphicon-star');
    ca.cmPostFlyer1 = +$('#cmPostFlyer-1').text();
    ca.cmPostFlyer2 = +$('#cmPostFlyer-2').text();
    ca.cmPostFlyer3 = +$('#cmPostFlyer-3').text();
    ca.cmPostFlyer4 = +$('#cmPostFlyer-4').text();
    ca.cmPostFlyer5 = +$('#cmPostFlyer-5').text();
    ca.cmPostFlyer6 = +$('#cmPostFlyer-6').text();
    ca.cmHandFlyer0 = $('#cmHandFlyer-0').hasClass('glyphicon-star');
    ca.cmHandFlyer1 = +$('#cmHandFlyer-1').text();
    ca.cmHandFlyer2 = +$('#cmHandFlyer-2').text();
    ca.cmHandFlyer3 = +$('#cmHandFlyer-3').text();
    ca.cmHandFlyer4 = +$('#cmHandFlyer-4').text();
    ca.cmHandFlyer5 = +$('#cmHandFlyer-5').text();
    ca.cmHandFlyer6 = +$('#cmHandFlyer-6').text();
    ca.cmOutGp0 = $('#cmOutGp-0').hasClass('glyphicon-star');
    ca.cmOutGp1 = +$('#cmOutGp-1').text();
    ca.cmOutGp2 = +$('#cmOutGp-2').text();
    ca.cmOutGp3 = +$('#cmOutGp-3').text();
    ca.cmOutGp4 = +$('#cmOutGp-4').text();
    ca.cmOutGp5 = +$('#cmOutGp-5').text();
    ca.cmOutGp6 = +$('#cmOutGp-6').text();
    ca.cmCpBox0 = $('#cmCpBox-0').hasClass('glyphicon-star');
    ca.cmCpBox1 = +$('#cmCpBox-1').text();
    ca.cmCpBox2 = +$('#cmCpBox-2').text();
    ca.cmCpBox3 = +$('#cmCpBox-3').text();
    ca.cmCpBox4 = +$('#cmCpBox-4').text();
    ca.cmCpBox5 = +$('#cmCpBox-5').text();
    ca.cmCpBox6 = +$('#cmCpBox-6').text();
    ca.cmOutGot0 = $('#cmOutGot-0').hasClass('glyphicon-star');
    ca.cmOutGot1 = +$('#cmOutGot-1').text();
    ca.cmOutGot2 = +$('#cmOutGot-2').text();
    ca.cmOutGot3 = +$('#cmOutGot-3').text();
    ca.cmOutGot4 = +$('#cmOutGot-4').text();
    ca.cmOutGot5 = +$('#cmOutGot-5').text();
    ca.cmOutGot6 = +$('#cmOutGot-6').text();
    ca.cmInGot0 = $('#cmInGot-0').hasClass('glyphicon-star');
    ca.cmInGot1 = +$('#cmInGot-1').text();
    ca.cmInGot2 = +$('#cmInGot-2').text();
    ca.cmInGot3 = +$('#cmInGot-3').text();
    ca.cmInGot4 = +$('#cmInGot-4').text();
    ca.cmInGot5 = +$('#cmInGot-5').text();
    ca.cmInGot6 = +$('#cmInGot-6').text();
    ca.cmBlogGot0 = $('#cmBlogGot-0').hasClass('glyphicon-star');
    ca.cmBlogGot1 = +$('#cmBlogGot-1').text();
    ca.cmBlogGot2 = +$('#cmBlogGot-2').text();
    ca.cmBlogGot3 = +$('#cmBlogGot-3').text();
    ca.cmBlogGot4 = +$('#cmBlogGot-4').text();
    ca.cmBlogGot5 = +$('#cmBlogGot-5').text();
    ca.cmBlogGot6 = +$('#cmBlogGot-6').text();
    ca.cmBagGot0 = $('#cmBagGot-0').hasClass('glyphicon-star');
    ca.cmBagGot1 = +$('#cmBagGot-1').text();
    ca.cmBagGot2 = +$('#cmBagGot-2').text();
    ca.cmBagGot3 = +$('#cmBagGot-3').text();
    ca.cmBagGot4 = +$('#cmBagGot-4').text();
    ca.cmBagGot5 = +$('#cmBagGot-5').text();
    ca.cmBagGot6 = +$('#cmBagGot-6').text();
    ca.cmTotalGot0 = $('#cmTotalGot-0').hasClass('glyphicon-star');
    ca.cmTotalGot1 = +$('#cmTotalGot-1').text();
    ca.cmTotalGot2 = +$('#cmTotalGot-2').text();
    ca.cmTotalGot3 = +$('#cmTotalGot-3').text();
    ca.cmTotalGot4 = +$('#cmTotalGot-4').text();
    ca.cmTotalGot5 = +$('#cmTotalGot-5').text();
    ca.cmTotalGot6 = +$('#cmTotalGot-6').text();
    ca.cmCallIn0 = $('#cmCallIn-0').hasClass('glyphicon-star');
    ca.cmCallIn1 = +$('#cmCallIn-1').text();
    ca.cmCallIn2 = +$('#cmCallIn-2').text();
    ca.cmCallIn3 = +$('#cmCallIn-3').text();
    ca.cmCallIn4 = +$('#cmCallIn-4').text();
    ca.cmCallIn5 = +$('#cmCallIn-5').text();
    ca.cmCallIn6 = +$('#cmCallIn-6').text();
    ca.cmOutGotCall0 = $('#cmOutGotCall-0').hasClass('glyphicon-star');
    ca.cmOutGotCall1 = +$('#cmOutGotCall-1').text();
    ca.cmOutGotCall2 = +$('#cmOutGotCall-2').text();
    ca.cmOutGotCall3 = +$('#cmOutGotCall-3').text();
    ca.cmOutGotCall4 = +$('#cmOutGotCall-4').text();
    ca.cmOutGotCall5 = +$('#cmOutGotCall-5').text();
    ca.cmOutGotCall6 = +$('#cmOutGotCall-6').text();
    ca.cmInGotCall0 = $('#cmInGotCall-0').hasClass('glyphicon-star');
    ca.cmInGotCall1 = +$('#cmInGotCall-1').text();
    ca.cmInGotCall2 = +$('#cmInGotCall-2').text();
    ca.cmInGotCall3 = +$('#cmInGotCall-3').text();
    ca.cmInGotCall4 = +$('#cmInGotCall-4').text();
    ca.cmInGotCall5 = +$('#cmInGotCall-5').text();
    ca.cmInGotCall6 = +$('#cmInGotCall-6').text();
    ca.cmBlogGotCall0 = $('#cmBlogGotCall-0').hasClass('glyphicon-star');
    ca.cmBlogGotCall1 = +$('#cmBlogGotCall-1').text();
    ca.cmBlogGotCall2 = +$('#cmBlogGotCall-2').text();
    ca.cmBlogGotCall3 = +$('#cmBlogGotCall-3').text();
    ca.cmBlogGotCall4 = +$('#cmBlogGotCall-4').text();
    ca.cmBlogGotCall5 = +$('#cmBlogGotCall-5').text();
    ca.cmBlogGotCall6 = +$('#cmBlogGotCall-6').text();
    ca.cmBagGotCall0 = $('#cmBagGotCall-0').hasClass('glyphicon-star');
    ca.cmBagGotCall1 = +$('#cmBagGotCall-1').text();
    ca.cmBagGotCall2 = +$('#cmBagGotCall-2').text();
    ca.cmBagGotCall3 = +$('#cmBagGotCall-3').text();
    ca.cmBagGotCall4 = +$('#cmBagGotCall-4').text();
    ca.cmBagGotCall5 = +$('#cmBagGotCall-5').text();
    ca.cmBagGotCall6 = +$('#cmBagGotCall-6').text();
    ca.cmOwnRefs0 = $('#cmOwnRefs-0').hasClass('glyphicon-star');
    ca.cmOwnRefs1 = +$('#cmOwnRefs-1').text();
    ca.cmOwnRefs2 = +$('#cmOwnRefs-2').text();
    ca.cmOwnRefs3 = +$('#cmOwnRefs-3').text();
    ca.cmOwnRefs4 = +$('#cmOwnRefs-4').text();
    ca.cmOwnRefs5 = +$('#cmOwnRefs-5').text();
    ca.cmOwnRefs6 = +$('#cmOwnRefs-6').text();
    ca.cmOtherRefs0 = $('#cmOtherRefs-0').hasClass('glyphicon-star');
    ca.cmOtherRefs1 = +$('#cmOtherRefs-1').text();
    ca.cmOtherRefs2 = +$('#cmOtherRefs-2').text();
    ca.cmOtherRefs3 = +$('#cmOtherRefs-3').text();
    ca.cmOtherRefs4 = +$('#cmOtherRefs-4').text();
    ca.cmOtherRefs5 = +$('#cmOtherRefs-5').text();
    ca.cmOtherRefs6 = +$('#cmOtherRefs-6').text();
    ca.cmNewspaper0 = $('#cmNewspaper-0').hasClass('glyphicon-star');
    ca.cmNewspaper1 = +$('#cmNewspaper-1').text();
    ca.cmNewspaper2 = +$('#cmNewspaper-2').text();
    ca.cmNewspaper3 = +$('#cmNewspaper-3').text();
    ca.cmNewspaper4 = +$('#cmNewspaper-4').text();
    ca.cmNewspaper5 = +$('#cmNewspaper-5').text();
    ca.cmNewspaper6 = +$('#cmNewspaper-6').text();
    ca.cmTv0 = $('#cmTv-0').hasClass('glyphicon-star');
    ca.cmTv1 = +$('#cmTv-1').text();
    ca.cmTv2 = +$('#cmTv-2').text();
    ca.cmTv3 = +$('#cmTv-3').text();
    ca.cmTv4 = +$('#cmTv-4').text();
    ca.cmTv5 = +$('#cmTv-5').text();
    ca.cmTv6 = +$('#cmTv-6').text();
    ca.cmInternet0 = $('#cmInternet-0').hasClass('glyphicon-star');
    ca.cmInternet1 = +$('#cmInternet-1').text();
    ca.cmInternet2 = +$('#cmInternet-2').text();
    ca.cmInternet3 = +$('#cmInternet-3').text();
    ca.cmInternet4 = +$('#cmInternet-4').text();
    ca.cmInternet5 = +$('#cmInternet-5').text();
    ca.cmInternet6 = +$('#cmInternet-6').text();
    ca.cmSign0 = $('#cmSign-0').hasClass('glyphicon-star');
    ca.cmSign1 = +$('#cmSign-1').text();
    ca.cmSign2 = +$('#cmSign-2').text();
    ca.cmSign3 = +$('#cmSign-3').text();
    ca.cmSign4 = +$('#cmSign-4').text();
    ca.cmSign5 = +$('#cmSign-5').text();
    ca.cmSign6 = +$('#cmSign-6').text();
    ca.cmMate0 = $('#cmMate-0').hasClass('glyphicon-star');
    ca.cmMate1 = +$('#cmMate-1').text();
    ca.cmMate2 = +$('#cmMate-2').text();
    ca.cmMate3 = +$('#cmMate-3').text();
    ca.cmMate4 = +$('#cmMate-4').text();
    ca.cmMate5 = +$('#cmMate-5').text();
    ca.cmMate6 = +$('#cmMate-6').text();
    ca.cmOthers0 = $('#cmOthers-0').hasClass('glyphicon-star');
    ca.cmOthers1 = +$('#cmOthers-1').text();
    ca.cmOthers2 = +$('#cmOthers-2').text();
    ca.cmOthers3 = +$('#cmOthers-3').text();
    ca.cmOthers4 = +$('#cmOthers-4').text();
    ca.cmOthers5 = +$('#cmOthers-5').text();
    ca.cmOthers6 = +$('#cmOthers-6').text();
    ca.cmMailAgpIn0 = $('#cmMailAgpIn-0').hasClass('glyphicon-star');
    ca.cmMailAgpIn1 = +$('#cmMailAgpIn-1').text();
    ca.cmMailAgpIn2 = +$('#cmMailAgpIn-2').text();
    ca.cmMailAgpIn3 = +$('#cmMailAgpIn-3').text();
    ca.cmMailAgpIn4 = +$('#cmMailAgpIn-4').text();
    ca.cmMailAgpIn5 = +$('#cmMailAgpIn-5').text();
    ca.cmMailAgpIn6 = +$('#cmMailAgpIn-6').text();
    ca.cmPostFlyerAgpIn0 = $('#cmPostFlyerAgpIn-0').hasClass('glyphicon-star');
    ca.cmPostFlyerAgpIn1 = +$('#cmPostFlyerAgpIn-1').text();
    ca.cmPostFlyerAgpIn2 = +$('#cmPostFlyerAgpIn-2').text();
    ca.cmPostFlyerAgpIn3 = +$('#cmPostFlyerAgpIn-3').text();
    ca.cmPostFlyerAgpIn4 = +$('#cmPostFlyerAgpIn-4').text();
    ca.cmPostFlyerAgpIn5 = +$('#cmPostFlyerAgpIn-5').text();
    ca.cmPostFlyerAgpIn6 = +$('#cmPostFlyerAgpIn-6').text();
    ca.cmHandFlyerAgpIn0 = $('#cmHandFlyerAgpIn-0').hasClass('glyphicon-star');
    ca.cmHandFlyerAgpIn1 = +$('#cmHandFlyerAgpIn-1').text();
    ca.cmHandFlyerAgpIn2 = +$('#cmHandFlyerAgpIn-2').text();
    ca.cmHandFlyerAgpIn3 = +$('#cmHandFlyerAgpIn-3').text();
    ca.cmHandFlyerAgpIn4 = +$('#cmHandFlyerAgpIn-4').text();
    ca.cmHandFlyerAgpIn5 = +$('#cmHandFlyerAgpIn-5').text();
    ca.cmHandFlyerAgpIn6 = +$('#cmHandFlyerAgpIn-6').text();
    ca.cmCpAgpIn0 = $('#cmCpAgpIn-0').hasClass('glyphicon-star');
    ca.cmCpAgpIn1 = +$('#cmCpAgpIn-1').text();
    ca.cmCpAgpIn2 = +$('#cmCpAgpIn-2').text();
    ca.cmCpAgpIn3 = +$('#cmCpAgpIn-3').text();
    ca.cmCpAgpIn4 = +$('#cmCpAgpIn-4').text();
    ca.cmCpAgpIn5 = +$('#cmCpAgpIn-5').text();
    ca.cmCpAgpIn6 = +$('#cmCpAgpIn-6').text();
    ca.cmOutAgpOut0 = $('#cmOutAgpOut-0').hasClass('glyphicon-star');
    ca.cmOutAgpOut1 = +$('#cmOutAgpOut-1').text();
    ca.cmOutAgpOut2 = +$('#cmOutAgpOut-2').text();
    ca.cmOutAgpOut3 = +$('#cmOutAgpOut-3').text();
    ca.cmOutAgpOut4 = +$('#cmOutAgpOut-4').text();
    ca.cmOutAgpOut5 = +$('#cmOutAgpOut-5').text();
    ca.cmOutAgpOut6 = +$('#cmOutAgpOut-6').text();
    ca.cmInAgpOut0 = $('#cmInAgpOut-0').hasClass('glyphicon-star');
    ca.cmInAgpOut1 = +$('#cmInAgpOut-1').text();
    ca.cmInAgpOut2 = +$('#cmInAgpOut-2').text();
    ca.cmInAgpOut3 = +$('#cmInAgpOut-3').text();
    ca.cmInAgpOut4 = +$('#cmInAgpOut-4').text();
    ca.cmInAgpOut5 = +$('#cmInAgpOut-5').text();
    ca.cmInAgpOut6 = +$('#cmInAgpOut-6').text();
    ca.cmBlogAgpOut0 = $('#cmBlogAgpOut-0').hasClass('glyphicon-star');
    ca.cmBlogAgpOut1 = +$('#cmBlogAgpOut-1').text();
    ca.cmBlogAgpOut2 = +$('#cmBlogAgpOut-2').text();
    ca.cmBlogAgpOut3 = +$('#cmBlogAgpOut-3').text();
    ca.cmBlogAgpOut4 = +$('#cmBlogAgpOut-4').text();
    ca.cmBlogAgpOut5 = +$('#cmBlogAgpOut-5').text();
    ca.cmBlogAgpOut6 = +$('#cmBlogAgpOut-6').text();
    ca.cmBagAgpOut0 = $('#cmBagAgpOut-0').hasClass('glyphicon-star');
    ca.cmBagAgpOut1 = +$('#cmBagAgpOut-1').text();
    ca.cmBagAgpOut2 = +$('#cmBagAgpOut-2').text();
    ca.cmBagAgpOut3 = +$('#cmBagAgpOut-3').text();
    ca.cmBagAgpOut4 = +$('#cmBagAgpOut-4').text();
    ca.cmBagAgpOut5 = +$('#cmBagAgpOut-5').text();
    ca.cmBagAgpOut6 = +$('#cmBagAgpOut-6').text();
    ca.cmApoTotal0 = $('#cmApoTotal-0').hasClass('glyphicon-star');
    ca.cmApoTotal1 = +$('#cmApoTotal-1').text();
    ca.cmApoTotal2 = +$('#cmApoTotal-2').text();
    ca.cmApoTotal3 = +$('#cmApoTotal-3').text();
    ca.cmApoTotal4 = +$('#cmApoTotal-4').text();
    ca.cmApoTotal5 = +$('#cmApoTotal-5').text();
    ca.cmApoTotal6 = +$('#cmApoTotal-6').text();
    ca.cmInApptRatio0 = $('#cmInApptRatio-0').hasClass('glyphicon-star');
    ca.cmInApptRatio1 = parseFloat($('#cmInApptRatio-1').text());
    ca.cmInApptRatio2 = parseFloat($('#cmInApptRatio-2').text());
    ca.cmInApptRatio3 = parseFloat($('#cmInApptRatio-3').text());
    ca.cmInApptRatio4 = parseFloat($('#cmInApptRatio-4').text());
    ca.cmInApptRatio5 = parseFloat($('#cmInApptRatio-5').text());
    ca.cmInApptRatio6 = parseFloat($('#cmInApptRatio-6').text());
    ca.cmOutApptRatio0 = $('#cmOutApptRatio-0').hasClass('glyphicon-star');
    ca.cmOutApptRatio1 = parseFloat($('#cmOutApptRatio-1').text());
    ca.cmOutApptRatio2 = parseFloat($('#cmOutApptRatio-2').text());
    ca.cmOutApptRatio3 = parseFloat($('#cmOutApptRatio-3').text());
    ca.cmOutApptRatio4 = parseFloat($('#cmOutApptRatio-4').text());
    ca.cmOutApptRatio5 = parseFloat($('#cmOutApptRatio-5').text());
    ca.cmOutApptRatio6 = parseFloat($('#cmOutApptRatio-6').text());
    ca.cmPostPerApo0 = $('#cmPostPerApo-0').hasClass('glyphicon-star');
    ca.cmPostPerApo6 = +$('#cmPostPerApo-6').text();
    ca.cmHandPerApo0 = $('#cmHandPerApo-0').hasClass('glyphicon-star');
    ca.cmHandPerApo6 = +$('#cmHandPerApo-6').text();
    ca.cmOutPerApo0 = $('#cmOutPerApo-0').hasClass('glyphicon-star');
    ca.cmOutPerApo6 = parseFloat($('#cmOutPerApo-6').text());
    ca.cmBrAgpRatio0 = $('#cmBrAgpRatio-0').hasClass('glyphicon-star');
    ca.cmBrAgpRatio1 = parseFloat($('#cmBrAgpRatio-1').text());
    ca.cmBrAgpRatio2 = parseFloat($('#cmBrAgpRatio-2').text());
    ca.cmBrAgpRatio3 = parseFloat($('#cmBrAgpRatio-3').text());
    ca.cmBrAgpRatio4 = parseFloat($('#cmBrAgpRatio-4').text());
    ca.cmBrAgpRatio5 = parseFloat($('#cmBrAgpRatio-5').text());
    ca.cmBrAgpRatio6 = parseFloat($('#cmBrAgpRatio-6').text());
    ca.cmFaSum0 = $('#cmFaSum-0').hasClass('glyphicon-star');
    ca.cmFaSum1 = +$('#cmFaSum-1').text();
    ca.cmFaSum2 = +$('#cmFaSum-2').text();
    ca.cmFaSum3 = +$('#cmFaSum-3').text();
    ca.cmFaSum4 = +$('#cmFaSum-4').text();
    ca.cmFaSum5 = +$('#cmFaSum-5').text();
    ca.cmFaSum6 = +$('#cmFaSum-6').text();
    ca.cmShowRatio0 = $('#cmShowRatio-0').hasClass('glyphicon-star');
    ca.cmShowRatio1 = parseFloat($('#cmShowRatio-1').text());
    ca.cmShowRatio2 = parseFloat($('#cmShowRatio-2').text());
    ca.cmShowRatio3 = parseFloat($('#cmShowRatio-3').text());
    ca.cmShowRatio4 = parseFloat($('#cmShowRatio-4').text());
    ca.cmShowRatio5 = parseFloat($('#cmShowRatio-5').text());
    ca.cmShowRatio6 = parseFloat($('#cmShowRatio-6').text());
    ca.cmTraining0 = $('#cmTraining-0').hasClass('glyphicon-star');
    ca.cmTraining1 = $('#cmTraining-1').is(':checked');
    ca.cmTraining2 = $('#cmTraining-2').is(':checked');
    ca.cmTraining3 = $('#cmTraining-3').is(':checked');
    ca.cmTraining4 = $('#cmTraining-4').is(':checked');
    ca.cmTraining5 = $('#cmTraining-5').is(':checked');
    ca.cmGot3_0 = $('#cmGot3-0').hasClass('glyphicon-star');
    ca.cmGot3_1 = $('#cmGot3-1').is(':checked');
    ca.cmGot3_2 = $('#cmGot3-2').is(':checked');
    ca.cmGot3_3 = $('#cmGot3-3').is(':checked');
    ca.cmGot3_4 = $('#cmGot3-4').is(':checked');
    ca.cmGot3_5 = $('#cmGot3-5').is(':checked');
    ca.cmInvitation0 = $('#cmInvitation-0').hasClass('glyphicon-star');
    ca.cmInvitation1 = $('#cmInvitation-1').is(':checked');
    ca.cmInvitation2 = $('#cmInvitation-2').is(':checked');
    ca.cmInvitation3 = $('#cmInvitation-3').is(':checked');
    ca.cmInvitation4 = $('#cmInvitation-4').is(':checked');
    ca.cmInvitation5 = $('#cmInvitation-5').is(':checked');

    ca.salesAch0 = $('#salesAch-0').hasClass('glyphicon-star');
    ca.salesAch1 = +$('#salesAch-1').text();
    ca.salesAch2 = +$('#salesAch-2').text();
    ca.salesAch3 = +$('#salesAch-3').text();
    ca.salesAch4 = +$('#salesAch-4').text();
    ca.salesAch5 = +$('#salesAch-5').text();
    ca.salesAch6 = +$('#salesAch-5').text();
    ca.salesMonthly0 = $('#salesMonthly-0').hasClass('glyphicon-star');
    ca.salesMonthly1 = +$('#salesMonthly-1').text();
    ca.salesMonthly2 = +$('#salesMonthly-2').text();
    ca.salesMonthly3 = +$('#salesMonthly-3').text();
    ca.salesMonthly4 = +$('#salesMonthly-4').text();
    ca.salesMonthly5 = +$('#salesMonthly-5').text();
    ca.salesMonthly6 = +$('#salesMonthly-6').text();
    ca.salesAllPrepay0 = $('#salesAllPrepay-0').hasClass('glyphicon-star');
    ca.salesAllPrepay1 = +$('#salesAllPrepay-1').text();
    ca.salesAllPrepay2 = +$('#salesAllPrepay-2').text();
    ca.salesAllPrepay3 = +$('#salesAllPrepay-3').text();
    ca.salesAllPrepay4 = +$('#salesAllPrepay-4').text();
    ca.salesAllPrepay5 = +$('#salesAllPrepay-5').text();
    ca.salesAllPrepay6 = +$('#salesAllPrepay-6').text();
    ca.salesTotal0 = $('#salesTotal-0').hasClass('glyphicon-star');
    ca.salesTotal1 = +$('#salesTotal-1').text();
    ca.salesTotal2 = +$('#salesTotal-2').text();
    ca.salesTotal3 = +$('#salesTotal-3').text();
    ca.salesTotal4 = +$('#salesTotal-4').text();
    ca.salesTotal5 = +$('#salesTotal-5').text();
    ca.salesTotal6 = +$('#salesTotal-6').text();
    ca.salesRatio0 = $('#salesRatio-0').hasClass('glyphicon-star');
    ca.salesRatio1 = parseFloat($('#salesRatio-1').text());
    ca.salesRatio2 = parseFloat($('#salesRatio-2').text());
    ca.salesRatio3 = parseFloat($('#salesRatio-3').text());
    ca.salesRatio4 = parseFloat($('#salesRatio-4').text());
    ca.salesRatio5 = parseFloat($('#salesRatio-5').text());
    ca.salesRatio6 = parseFloat($('#salesRatio-6').text());
    ca.salesAchAppRatio0 = $('#salesAchAppRatio-0').hasClass('glyphicon-star');
    ca.salesAchAppRatio1 = parseFloat($('#salesAchAppRatio-1').text());
    ca.salesAchAppRatio2 = parseFloat($('#salesAchAppRatio-2').text());
    ca.salesAchAppRatio3 = parseFloat($('#salesAchAppRatio-3').text());
    ca.salesAchAppRatio4 = parseFloat($('#salesAchAppRatio-4').text());
    ca.salesAchAppRatio5 = parseFloat($('#salesAchAppRatio-5').text());
    ca.salesAchAppRatio6 = parseFloat($('#salesAchAppRatio-6').text());
    ca.salesFaReview0 = $('#salesFaReview-0').hasClass('glyphicon-star');
    ca.salesFaReview1 = $('#salesFaReview-1').is(':checked');
    ca.salesFaReview2 = $('#salesFaReview-2').is(':checked');
    ca.salesFaReview3 = $('#salesFaReview-3').is(':checked');
    ca.salesFaReview4 = $('#salesFaReview-4').is(':checked');
    ca.salesFaReview5 = $('#salesFaReview-5').is(':checked');
    ca.salesPriceReview0 = $('#salesPriceReview-0').hasClass('glyphicon-star');
    ca.salesPriceReview1 = $('#salesPriceReview-1').is(':checked');
    ca.salesPriceReview2 = $('#salesPriceReview-2').is(':checked');
    ca.salesPriceReview3 = $('#salesPriceReview-3').is(':checked');
    ca.salesPriceReview4 = $('#salesPriceReview-4').is(':checked');
    ca.salesPriceReview5 = $('#salesPriceReview-5').is(':checked');
    ca.salesAck0 = $('#salesAck-0').hasClass('glyphicon-star');
    ca.salesAck1 = $('#salesAck-1').is(':checked');
    ca.salesAck2 = $('#salesAck-2').is(':checked');
    ca.salesAck3 = $('#salesAck-3').is(':checked');
    ca.salesAck4 = $('#salesAck-4').is(':checked');
    ca.salesAck5 = $('#salesAck-5').is(':checked');
    ca.salesTarget0 = $('#salesTarget-0').hasClass('glyphicon-star');
    ca.salesTarget1 = $('#salesTarget-1').is(':checked');
    ca.salesTarget2 = $('#salesTarget-2').is(':checked');
    ca.salesTarget3 = $('#salesTarget-3').is(':checked');
    ca.salesTarget4 = $('#salesTarget-4').is(':checked');
    ca.salesTarget5 = $('#salesTarget-5').is(':checked');
    ca.salesMotivation0 = $('#salesMotivation-0').hasClass('glyphicon-star');
    ca.salesMotivation1 = $('#salesMotivation-1').is(':checked');
    ca.salesMotivation2 = $('#salesMotivation-2').is(':checked');
    ca.salesMotivation3 = $('#salesMotivation-3').is(':checked');
    ca.salesMotivation4 = $('#salesMotivation-4').is(':checked');
    ca.salesMotivation5 = $('#salesMotivation-5').is(':checked');
    ca.salesObstacle0 = $('#salesObstacle-0').hasClass('glyphicon-star');
    ca.salesObstacle1 = $('#salesObstacle-1').is(':checked');
    ca.salesObstacle2 = $('#salesObstacle-2').is(':checked');
    ca.salesObstacle3 = $('#salesObstacle-3').is(':checked');
    ca.salesObstacle4 = $('#salesObstacle-4').is(':checked');
    ca.salesObstacle5 = $('#salesObstacle-5').is(':checked');

    ca.mgmtMeeting0 = $('#mgmtMeeting-0').hasClass('glyphicon-star');
    ca.mgmtMeeting1 = $('#mgmtMeeting-1').is(':checked');
    ca.mgmtMeeting2 = $('#mgmtMeeting-2').is(':checked');
    ca.mgmtMeeting3 = $('#mgmtMeeting-3').is(':checked');
    ca.mgmtMeeting4 = $('#mgmtMeeting-4').is(':checked');
    ca.mgmtMeeting5 = $('#mgmtMeeting-5').is(':checked');
    ca.mgmtCa0 = $('#mgmtCa-0').hasClass('glyphicon-star');
    ca.mgmtCa1 = $('#mgmtCa-1').is(':checked');
    ca.mgmtCa2 = $('#mgmtCa-2').is(':checked');
    ca.mgmtCa3 = $('#mgmtCa-3').is(':checked');
    ca.mgmtCa4 = $('#mgmtCa-4').is(':checked');
    ca.mgmtCa5 = $('#mgmtCa-5').is(':checked');
    ca.mgmtGp0 = $('#mgmtGp-0').hasClass('glyphicon-star');
    ca.mgmtGp1 = $('#mgmtGp-1').is(':checked');
    ca.mgmtGp2 = $('#mgmtGp-2').is(':checked');
    ca.mgmtGp3 = $('#mgmtGp-3').is(':checked');
    ca.mgmtGp4 = $('#mgmtGp-4').is(':checked');
    ca.mgmtGp5 = $('#mgmtGp-5').is(':checked');
    ca.mgmtLearn0 = $('#mgmtLearn-0').hasClass('glyphicon-star');
    ca.mgmtLearn1 = $('#mgmtLearn-1').is(':checked');
    ca.mgmtLearn2 = $('#mgmtLearn-2').is(':checked');
    ca.mgmtLearn3 = $('#mgmtLearn-3').is(':checked');
    ca.mgmtLearn4 = $('#mgmtLearn-4').is(':checked');
    ca.mgmtLearn5 = $('#mgmtLearn-5').is(':checked');
    ca.mgmtSheet0 = $('#mgmtSheet-0').hasClass('glyphicon-star');
    ca.mgmtSheet1 = $('#mgmtSheet-1').is(':checked');
    ca.mgmtSheet2 = $('#mgmtSheet-2').is(':checked');
    ca.mgmtSheet3 = $('#mgmtSheet-3').is(':checked');
    ca.mgmtSheet4 = $('#mgmtSheet-4').is(':checked');
    ca.mgmtSheet5 = $('#mgmtSheet-5').is(':checked');
    ca.mgmtPolicy0 = $('#mgmtPolicy-0').hasClass('glyphicon-star');
    ca.mgmtPolicy1 = $('#mgmtPolicy-1').is(':checked');
    ca.mgmtPolicy2 = $('#mgmtPolicy-2').is(':checked');
    ca.mgmtPolicy3 = $('#mgmtPolicy-3').is(':checked');
    ca.mgmtPolicy4 = $('#mgmtPolicy-4').is(':checked');
    ca.mgmtPolicy5 = $('#mgmtPolicy-5').is(':checked');
    ca.mgmtCompiantSales0 = $('#mgmtCompiantSales-0').hasClass('glyphicon-star');
    ca.mgmtCompiantSales1 = $('#mgmtCompiantSales-1').is(':checked');
    ca.mgmtCompiantSales2 = $('#mgmtCompiantSales-2').is(':checked');
    ca.mgmtCompiantSales3 = $('#mgmtCompiantSales-3').is(':checked');
    ca.mgmtCompiantSales4 = $('#mgmtCompiantSales-4').is(':checked');
    ca.mgmtCompiantSales5 = $('#mgmtCompiantSales-5').is(':checked');
    ca.mgmtCompiantMethod0 = $('#mgmtCompiantMethod-0').hasClass('glyphicon-star');
    ca.mgmtCompiantMethod1 = $('#mgmtCompiantMethod-1').is(':checked');
    ca.mgmtCompiantMethod2 = $('#mgmtCompiantMethod-2').is(':checked');
    ca.mgmtCompiantMethod3 = $('#mgmtCompiantMethod-3').is(':checked');
    ca.mgmtCompiantMethod4 = $('#mgmtCompiantMethod-4').is(':checked');
    ca.mgmtCompiantMethod5 = $('#mgmtCompiantMethod-5').is(':checked');
    ca.mgmtCompiantProduct0 = $('#mgmtCompiantProduct-0').hasClass('glyphicon-star');
    ca.mgmtCompiantProduct1 = $('#mgmtCompiantProduct-1').is(':checked');
    ca.mgmtCompiantProduct2 = $('#mgmtCompiantProduct-2').is(':checked');
    ca.mgmtCompiantProduct3 = $('#mgmtCompiantProduct-3').is(':checked');
    ca.mgmtCompiantProduct4 = $('#mgmtCompiantProduct-4').is(':checked');
    ca.mgmtCompiantProduct5 = $('#mgmtCompiantProduct-5').is(':checked');
    ca.mgmtCompiantAd0 = $('#mgmtCompiantAd-0').hasClass('glyphicon-star');
    ca.mgmtCompiantAd1 = $('#mgmtCompiantAd-1').is(':checked');
    ca.mgmtCompiantAd2 = $('#mgmtCompiantAd-2').is(':checked');
    ca.mgmtCompiantAd3 = $('#mgmtCompiantAd-3').is(':checked');
    ca.mgmtCompiantAd4 = $('#mgmtCompiantAd-4').is(':checked');
    ca.mgmtCompiantAd5 = $('#mgmtCompiantAd-5').is(':checked');
    ca.mgmtTraining0 = $('#mgmtTraining-0').hasClass('glyphicon-star');
    ca.mgmtTraining1 = $('#mgmtTraining-1').is(':checked');
    ca.mgmtTraining2 = $('#mgmtTraining-2').is(':checked');
    ca.mgmtTraining3 = $('#mgmtTraining-3').is(':checked');
    ca.mgmtTraining4 = $('#mgmtTraining-4').is(':checked');
    ca.mgmtTraining5 = $('#mgmtTraining-5').is(':checked');
    ca.mgmtReport0 = $('#mgmtReport-0').hasClass('glyphicon-star');
    ca.mgmtReport1 = $('#mgmtReport-1').is(':checked');
    ca.mgmtReport2 = $('#mgmtReport-2').is(':checked');
    ca.mgmtReport3 = $('#mgmtReport-3').is(':checked');
    ca.mgmtReport4 = $('#mgmtReport-4').is(':checked');
    ca.mgmtReport5 = $('#mgmtReport-5').is(':checked');
    ca.mgmtPlan0 = $('#mgmtPlan-0').hasClass('glyphicon-star');
    ca.mgmtPlan1 = $('#mgmtPlan-1').is(':checked');
    ca.mgmtPlan2 = $('#mgmtPlan-2').is(':checked');
    ca.mgmtPlan3 = $('#mgmtPlan-3').is(':checked');
    ca.mgmtPlan4 = $('#mgmtPlan-4').is(':checked');
    ca.mgmtPlan5 = $('#mgmtPlan-5').is(':checked');
    ca.mgmtMaintain0 = $('#mgmtMaintain-0').hasClass('glyphicon-star');
    ca.mgmtMaintain1 = $('#mgmtMaintain-1').is(':checked');
    ca.mgmtMaintain2 = $('#mgmtMaintain-2').is(':checked');
    ca.mgmtMaintain3 = $('#mgmtMaintain-3').is(':checked');
    ca.mgmtMaintain4 = $('#mgmtMaintain-4').is(':checked');
    ca.mgmtMaintain5 = $('#mgmtMaintain-5').is(':checked');
    ca.mgmtFace2Face0 = $('#mgmtFace2Face-0').hasClass('glyphicon-star');
    ca.mgmtFace2Face1 = $('#mgmtFace2Face-1').is(':checked');
    ca.mgmtFace2Face2 = $('#mgmtFace2Face-2').is(':checked');
    ca.mgmtFace2Face3 = $('#mgmtFace2Face-3').is(':checked');
    ca.mgmtFace2Face4 = $('#mgmtFace2Face-4').is(':checked');
    ca.mgmtFace2Face5 = $('#mgmtFace2Face-5').is(':checked');

    ca.clubSalesRatio = parseFloat($('#clubSalesRatio').text());
    ca.clubAchAppRatio = parseFloat($('#clubAchAppRatio').text());
    ca.clubAch1 = +$('#clubAch-1').text();
    ca.clubAch2 = +$('#clubAch-2').text();
    ca.clubAch3 = +$('#clubAch-3').text();
    ca.clubAch4 = +$('#clubAch-4').text();
    ca.clubAch5 = +$('#clubAch-5').text();
    ca.clubAch6 = +$('#clubAch-6').text();
    ca.clubMm1 = +$('#clubMm-1').text();
    ca.clubMm2 = +$('#clubMm-2').text();
    ca.clubMm3 = +$('#clubMm-3').text();
    ca.clubMm4 = +$('#clubMm-4').text();
    ca.clubMm5 = +$('#clubMm-5').text();
    ca.clubMm6 = +$('#clubMm-6').text();
    ca.clubApp1 = +$('#clubApp-1').text();
    ca.clubApp2 = +$('#clubApp-2').text();
    ca.clubApp3 = +$('#clubApp-3').text();
    ca.clubApp4 = +$('#clubApp-4').text();
    ca.clubApp5 = +$('#clubApp-5').text();
    ca.clubApp6 = +$('#clubApp-6').text();
    ca.clubNs1 = +$('#clubNs-1').text();
    ca.clubNs2 = +$('#clubNs-2').text();
    ca.clubNs3 = +$('#clubNs-3').text();
    ca.clubNs4 = +$('#clubNs-4').text();
    ca.clubNs5 = +$('#clubNs-5').text();
    ca.clubNs6 = +$('#clubNs-6').text();
    ca.clubLx1 = +$('#clubLx-1').text();
    ca.clubLx2 = +$('#clubLx-2').text();
    ca.clubLx3 = +$('#clubLx-3').text();
    ca.clubLx4 = +$('#clubLx-4').text();
    ca.clubLx5 = +$('#clubLx-5').text();
    ca.clubLx6 = +$('#clubLx-6').text();

    ca.staff1SalesRatio = parseFloat($('#staff1SalesRatio').text());
    ca.staff1AchAppRatio = parseFloat($('#staff1AchAppRatio').text());
    ca.staff1Ach1 = +$('#staff1Ach-1').text();
    ca.staff1Ach2 = +$('#staff1Ach-2').text();
    ca.staff1Ach3 = +$('#staff1Ach-3').text();
    ca.staff1Ach4 = +$('#staff1Ach-4').text();
    ca.staff1Ach5 = +$('#staff1Ach-5').text();
    ca.staff1Ach6 = +$('#staff1Ach-6').text();
    ca.staff1Mm1 = +$('#staff1Mm-1').text();
    ca.staff1Mm2 = +$('#staff1Mm-2').text();
    ca.staff1Mm3 = +$('#staff1Mm-3').text();
    ca.staff1Mm4 = +$('#staff1Mm-4').text();
    ca.staff1Mm5 = +$('#staff1Mm-5').text();
    ca.staff1Mm6 = +$('#staff1Mm-6').text();
    ca.staff1App1 = +$('#staff1App-1').text();
    ca.staff1App2 = +$('#staff1App-2').text();
    ca.staff1App3 = +$('#staff1App-3').text();
    ca.staff1App4 = +$('#staff1App-4').text();
    ca.staff1App5 = +$('#staff1App-5').text();
    ca.staff1App6 = +$('#staff1App-6').text();
    ca.staff1Ns1 = +$('#staff1Ns-1').text();
    ca.staff1Ns2 = +$('#staff1Ns-2').text();
    ca.staff1Ns3 = +$('#staff1Ns-3').text();
    ca.staff1Ns4 = +$('#staff1Ns-4').text();
    ca.staff1Ns5 = +$('#staff1Ns-5').text();
    ca.staff1Ns6 = +$('#staff1Ns-6').text();
    ca.staff1Lx1 = +$('#staff1Lx-1').text();
    ca.staff1Lx2 = +$('#staff1Lx-2').text();
    ca.staff1Lx3 = +$('#staff1Lx-3').text();
    ca.staff1Lx4 = +$('#staff1Lx-4').text();
    ca.staff1Lx5 = +$('#staff1Lx-5').text();
    ca.staff1Lx6 = +$('#staff1Lx-6').text();

    ca.staff2SalesRatio = parseFloat($('#staff2SalesRatio').text());
    ca.staff2AchAppRatio = parseFloat($('#staff2AchAppRatio').text());
    ca.staff2Ach1 = +$('#staff2Ach-1').text();
    ca.staff2Ach2 = +$('#staff2Ach-2').text();
    ca.staff2Ach3 = +$('#staff2Ach-3').text();
    ca.staff2Ach4 = +$('#staff2Ach-4').text();
    ca.staff2Ach5 = +$('#staff2Ach-5').text();
    ca.staff2Ach6 = +$('#staff2Ach-6').text();
    ca.staff2Mm1 = +$('#staff2Mm-1').text();
    ca.staff2Mm2 = +$('#staff2Mm-2').text();
    ca.staff2Mm3 = +$('#staff2Mm-3').text();
    ca.staff2Mm4 = +$('#staff2Mm-4').text();
    ca.staff2Mm5 = +$('#staff2Mm-5').text();
    ca.staff2Mm6 = +$('#staff2Mm-6').text();
    ca.staff2App1 = +$('#staff2App-1').text();
    ca.staff2App2 = +$('#staff2App-2').text();
    ca.staff2App3 = +$('#staff2App-3').text();
    ca.staff2App4 = +$('#staff2App-4').text();
    ca.staff2App5 = +$('#staff2App-5').text();
    ca.staff2App6 = +$('#staff2App-6').text();
    ca.staff2Ns1 = +$('#staff2Ns-1').text();
    ca.staff2Ns2 = +$('#staff2Ns-2').text();
    ca.staff2Ns3 = +$('#staff2Ns-3').text();
    ca.staff2Ns4 = +$('#staff2Ns-4').text();
    ca.staff2Ns5 = +$('#staff2Ns-5').text();
    ca.staff2Ns6 = +$('#staff2Ns-6').text();
    ca.staff2Lx1 = +$('#staff2Lx-1').text();
    ca.staff2Lx2 = +$('#staff2Lx-2').text();
    ca.staff2Lx3 = +$('#staff2Lx-3').text();
    ca.staff2Lx4 = +$('#staff2Lx-4').text();
    ca.staff2Lx5 = +$('#staff2Lx-5').text();
    ca.staff2Lx6 = +$('#staff2Lx-6').text();

    ca.staff3SalesRatio = parseFloat($('#staff3SalesRatio').text());
    ca.staff3AchAppRatio = parseFloat($('#staff3AchAppRatio').text());
    ca.staff3Ach1 = +$('#staff3Ach-1').text();
    ca.staff3Ach2 = +$('#staff3Ach-2').text();
    ca.staff3Ach3 = +$('#staff3Ach-3').text();
    ca.staff3Ach4 = +$('#staff3Ach-4').text();
    ca.staff3Ach5 = +$('#staff3Ach-5').text();
    ca.staff3Ach6 = +$('#staff3Ach-6').text();
    ca.staff3Mm1 = +$('#staff3Mm-1').text();
    ca.staff3Mm2 = +$('#staff3Mm-2').text();
    ca.staff3Mm3 = +$('#staff3Mm-3').text();
    ca.staff3Mm4 = +$('#staff3Mm-4').text();
    ca.staff3Mm5 = +$('#staff3Mm-5').text();
    ca.staff3Mm6 = +$('#staff3Mm-6').text();
    ca.staff3App1 = +$('#staff3App-1').text();
    ca.staff3App2 = +$('#staff3App-2').text();
    ca.staff3App3 = +$('#staff3App-3').text();
    ca.staff3App4 = +$('#staff3App-4').text();
    ca.staff3App5 = +$('#staff3App-5').text();
    ca.staff3App6 = +$('#staff3App-6').text();
    ca.staff3Ns1 = +$('#staff3Ns-1').text();
    ca.staff3Ns2 = +$('#staff3Ns-2').text();
    ca.staff3Ns3 = +$('#staff3Ns-3').text();
    ca.staff3Ns4 = +$('#staff3Ns-4').text();
    ca.staff3Ns5 = +$('#staff3Ns-5').text();
    ca.staff3Ns6 = +$('#staff3Ns-6').text();
    ca.staff3Lx1 = +$('#staff3Lx-1').text();
    ca.staff3Lx2 = +$('#staff3Lx-2').text();
    ca.staff3Lx3 = +$('#staff3Lx-3').text();
    ca.staff3Lx4 = +$('#staff3Lx-4').text();
    ca.staff3Lx5 = +$('#staff3Lx-5').text();
    ca.staff3Lx6 = +$('#staff3Lx-6').text();

    ca.staff4SalesRatio = parseFloat($('#staff4SalesRatio').text());
    ca.staff4AchAppRatio = parseFloat($('#staff4AchAppRatio').text());
    ca.staff4Ach1 = +$('#staff4Ach-1').text();
    ca.staff4Ach2 = +$('#staff4Ach-2').text();
    ca.staff4Ach3 = +$('#staff4Ach-3').text();
    ca.staff4Ach4 = +$('#staff4Ach-4').text();
    ca.staff4Ach5 = +$('#staff4Ach-5').text();
    ca.staff4Ach6 = +$('#staff4Ach-6').text();
    ca.staff4Mm1 = +$('#staff4Mm-1').text();
    ca.staff4Mm2 = +$('#staff4Mm-2').text();
    ca.staff4Mm3 = +$('#staff4Mm-3').text();
    ca.staff4Mm4 = +$('#staff4Mm-4').text();
    ca.staff4Mm5 = +$('#staff4Mm-5').text();
    ca.staff4Mm6 = +$('#staff4Mm-6').text();
    ca.staff4App1 = +$('#staff4App-1').text();
    ca.staff4App2 = +$('#staff4App-2').text();
    ca.staff4App3 = +$('#staff4App-3').text();
    ca.staff4App4 = +$('#staff4App-4').text();
    ca.staff4App5 = +$('#staff4App-5').text();
    ca.staff4App6 = +$('#staff4App-6').text();
    ca.staff4Ns1 = +$('#staff4Ns-1').text();
    ca.staff4Ns2 = +$('#staff4Ns-2').text();
    ca.staff4Ns3 = +$('#staff4Ns-3').text();
    ca.staff4Ns4 = +$('#staff4Ns-4').text();
    ca.staff4Ns5 = +$('#staff4Ns-5').text();
    ca.staff4Ns6 = +$('#staff4Ns-6').text();
    ca.staff4Lx1 = +$('#staff4Lx-1').text();
    ca.staff4Lx2 = +$('#staff4Lx-2').text();
    ca.staff4Lx3 = +$('#staff4Lx-3').text();
    ca.staff4Lx4 = +$('#staff4Lx-4').text();
    ca.staff4Lx5 = +$('#staff4Lx-5').text();
    ca.staff4Lx6 = +$('#staff4Lx-6').text();

    ca.staff5SalesRatio = parseFloat($('#staff5SalesRatio').text());
    ca.staff5AchAppRatio = parseFloat($('#staff5AchAppRatio').text());
    ca.staff5Ach1 = +$('#staff5Ach-1').text();
    ca.staff5Ach2 = +$('#staff5Ach-2').text();
    ca.staff5Ach3 = +$('#staff5Ach-3').text();
    ca.staff5Ach4 = +$('#staff5Ach-4').text();
    ca.staff5Ach5 = +$('#staff5Ach-5').text();
    ca.staff5Ach6 = +$('#staff5Ach-6').text();
    ca.staff5Mm1 = +$('#staff5Mm-1').text();
    ca.staff5Mm2 = +$('#staff5Mm-2').text();
    ca.staff5Mm3 = +$('#staff5Mm-3').text();
    ca.staff5Mm4 = +$('#staff5Mm-4').text();
    ca.staff5Mm5 = +$('#staff5Mm-5').text();
    ca.staff5Mm6 = +$('#staff5Mm-6').text();
    ca.staff5App1 = +$('#staff5App-1').text();
    ca.staff5App2 = +$('#staff5App-2').text();
    ca.staff5App3 = +$('#staff5App-3').text();
    ca.staff5App4 = +$('#staff5App-4').text();
    ca.staff5App5 = +$('#staff5App-5').text();
    ca.staff5App6 = +$('#staff5App-6').text();
    ca.staff5Ns1 = +$('#staff5Ns-1').text();
    ca.staff5Ns2 = +$('#staff5Ns-2').text();
    ca.staff5Ns3 = +$('#staff5Ns-3').text();
    ca.staff5Ns4 = +$('#staff5Ns-4').text();
    ca.staff5Ns5 = +$('#staff5Ns-5').text();
    ca.staff5Ns6 = +$('#staff5Ns-6').text();
    ca.staff5Lx1 = +$('#staff5Lx-1').text();
    ca.staff5Lx2 = +$('#staff5Lx-2').text();
    ca.staff5Lx3 = +$('#staff5Lx-3').text();
    ca.staff5Lx4 = +$('#staff5Lx-4').text();
    ca.staff5Lx5 = +$('#staff5Lx-5').text();
    ca.staff5Lx6 = +$('#staff5Lx-6').text();

    ca.staff6SalesRatio = parseFloat($('#staff6SalesRatio').text());
    ca.staff6AchAppRatio = parseFloat($('#staff6AchAppRatio').text());
    ca.staff6Ach1 = +$('#staff6Ach-1').text();
    ca.staff6Ach2 = +$('#staff6Ach-2').text();
    ca.staff6Ach3 = +$('#staff6Ach-3').text();
    ca.staff6Ach4 = +$('#staff6Ach-4').text();
    ca.staff6Ach5 = +$('#staff6Ach-5').text();
    ca.staff6Ach6 = +$('#staff6Ach-6').text();
    ca.staff6Mm1 = +$('#staff6Mm-1').text();
    ca.staff6Mm2 = +$('#staff6Mm-2').text();
    ca.staff6Mm3 = +$('#staff6Mm-3').text();
    ca.staff6Mm4 = +$('#staff6Mm-4').text();
    ca.staff6Mm5 = +$('#staff6Mm-5').text();
    ca.staff6Mm6 = +$('#staff6Mm-6').text();
    ca.staff6App1 = +$('#staff6App-1').text();
    ca.staff6App2 = +$('#staff6App-2').text();
    ca.staff6App3 = +$('#staff6App-3').text();
    ca.staff6App4 = +$('#staff6App-4').text();
    ca.staff6App5 = +$('#staff6App-5').text();
    ca.staff6App6 = +$('#staff6App-6').text();
    ca.staff6Ns1 = +$('#staff6Ns-1').text();
    ca.staff6Ns2 = +$('#staff6Ns-2').text();
    ca.staff6Ns3 = +$('#staff6Ns-3').text();
    ca.staff6Ns4 = +$('#staff6Ns-4').text();
    ca.staff6Ns5 = +$('#staff6Ns-5').text();
    ca.staff6Ns6 = +$('#staff6Ns-6').text();
    ca.staff6Lx1 = +$('#staff6Lx-1').text();
    ca.staff6Lx2 = +$('#staff6Lx-2').text();
    ca.staff6Lx3 = +$('#staff6Lx-3').text();
    ca.staff6Lx4 = +$('#staff6Lx-4').text();
    ca.staff6Lx5 = +$('#staff6Lx-5').text();
    ca.staff6Lx6 = +$('#staff6Lx-6').text();

    ca.thisPlan = $('#thisPlan').text();
    ca.nextPlan = $('#nextPlan').text();

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "/rest/CA",
        'data': JSON.stringify(ca),
        'dataType': 'json'
    }).done(function() {
        alert("Save successfully.");
    }).fail(function() {
        alert("oops! Save failed, please try again.");
    });

    $('td').each(function() {
        if (0 == +$(this).text()) {
            $(this).text('');
        }
    });

    $('#btnSave').prop("disabled", false);
});

function runFormula() {
    var goalsTm = +$('#goalsTm').text();
    var goalsExitsRatio = parseFloat($('#goalsExitsRatio').text());
    if (isNaN(goalsExitsRatio)) {
        goalsExitsRatio = 0;
    }
    var goalsLastTm = +$('#goalsLastTm').text();
    $('#goalsNewSales').text(goalsTm*(1+goalsExitsRatio)-goalsLastTm);
    var goalsLastShowRatio = parseFloat($('#goalsLastShowRatio').text());
    if (isNaN(goalsLastShowRatio)) {
        goalsLastShowRatio = 0;
    }
    var goalsLastSalesRatio = parseFloat($('#goalsLastSalesRatio').text());
    if (isNaN(goalsLastSalesRatio)) {
        goalsLastSalesRatio = 0;
    }
    if (goalsLastShowRatio != 0 && goalsLastSalesRatio != 0) {
        $('#goalsAppoints').text((goalsTm*(1+goalsExitsRatio)-goalsLastTm)/goalsLastShowRatio/goalsLastSalesRatio);
    }

    var svcTm1 = (+$('#svcTm-1').text());
    if (svcTm1 != 0) {
        var svcHold1 = (+$('#svcHold-1').text());
        $('svcHoldRatio-1').text(Math.round(svcHold1*100 / svcTm1) + '%');
    }
    var svcTm2 = (+$('#svcTm-2').text());
    if (svcTm2 != 0) {
        var svcHold2 = (+$('#svcHold-2').text());
        $('svcHoldRatio-2').text(Math.round(svcHold2*100 / svcTm2) + '%');
    }
    var svcTm3 = (+$('#svcTm-3').text());
    if (svcTm3 != 0) {
        var svcHold3 = (+$('#svcHold-3').text());
        $('svcHoldRatio-3').text(Math.round(svcHold3*100 / svcTm3) + '%');
    }
    var svcTm4 = (+$('#svcTm-4').text());
    if (svcTm4 != 0) {
        var svcHold4 = (+$('#svcHold-4').text());
        $('svcHoldRatio-4').text(Math.round(svcHold4*100 / svcTm4) + '%');
    }
    var svcTm5 = (+$('#svcTm-5').text());
    if (svcTm5 != 0) {
        var svcHold5 = (+$('#svcHold-5').text());
        $('svcHoldRatio-5').text(Math.round(svcHold5*100 / svcTm5) + '%');
    }
    var svcTm6 = (+$('#svcTm-1').text());
    if (svcTm6 != 0) {
        var svcHold6 = (+$('#svcHold-6').text());
        $('svcHoldRatio-6').text(Math.round(svcHold6*100 / svcTm6) + '%');
    }

    var svcTotalWo1 = (+$('#svcTotalWo-1').text());
    var svcTotalWo2 = (+$('#svcTotalWo-2').text());
    var svcTotalWo3 = (+$('#svcTotalWo-3').text());
    var svcTotalWo4 = (+$('#svcTotalWo-4').text());
    var svcTotalWo5 = (+$('#svcTotalWo-5').text());
    $('#svcTotalWo-6').text(svcTotalWo1+svcTotalWo2+svcTotalWo3+svcTotalWo4+svcTotalWo5);

    var svcActive1 = (+$('#svcTotalWo-1').text());
    if (svcActive1 != 0) {
        $('#svcAvgWo-1').text((svcTotalWo1/svcActive1).toFixed(1));
    }
    var svcActive2 = (+$('#svcTotalWo-2').text());
    if (svcActive2 != 0) {
        $('#svcAvgWo-2').text((svcTotalWo2/svcActive2).toFixed(1));
    }
    var svcActive3 = (+$('#svcTotalWo-3').text());
    if (svcActive3 != 0) {
        $('#svcAvgWo-3').text((svcTotalWo3/svcActive3).toFixed(1));
    }
    var svcActive4 = (+$('#svcTotalWo-4').text());
    if (svcActive4 != 0) {
        $('#svcAvgWo-4').text((svcTotalWo4/svcActive4).toFixed(1));
    }
    var svcActive5 = (+$('#svcTotalWo-5').text());
    if (svcActive5 != 0) {
        $('#svcAvgWo-5').text((svcTotalWo5/svcActive5).toFixed(1));
    }
    var svcActive6 = (+$('#svcTotalWo-6').text());
    if (svcActive6 != 0) {
        $('#svcAvgWo-6').text((svcTotalWo6/svcActive6/4).toFixed(1));
        $('#svcMaxWo-6').text(Math.round(Math.max(svcMaxWo1, svcMaxWo2, svcMaxWo3, svcMaxWo4, svcMaxWo5)/svcActive6));
    }

    var svcExits1 = (+$('#svcExits-1').text());
    var svcExits2 = (+$('#svcExits-2').text());
    var svcExits3 = (+$('#svcExits-3').text());
    var svcExits4 = (+$('#svcExits-4').text());
    var svcExits5 = (+$('#svcExits-5').text());
    $('#svcExits-6').text(svcExits1+svcExits2+svcExits3+svcExits4+svcExits5);
    if (svcTm1 != 0) {
        $('#svcExitsRatio-1').text((svcExits1*100 / svcTm1).toFixed(1) + '%');
    }
    if (svcTm2 != 0) {
        $('#svcExitsRatio-2').text((svcExits2*100 / svcTm2).toFixed(1) + '%');
    }
    if (svcTm3 != 0) {
        $('#svcExitsRatio-3').text((svcExits3*100 / svcTm3).toFixed(1) + '%');
    }
    if (svcTm4 != 0) {
        $('#svcExitsRatio-4').text((svcExits4*100 / svcTm4).toFixed(1) + '%');
    }
    if (svcTm5 != 0) {
        $('#svcExitsRatio-5').text((svcExits5*100 / svcTm5).toFixed(1) + '%');
    }
    if (svcTm6 != 0) {
        $('#svcExitsRatio-6').text((svcExits6*100 / svcTm6).toFixed(1) + '%');
    }

    var svcMeasure1 = (+$('#svcMeasure-1').text());
    var svcMeasure2 = (+$('#svcMeasure-2').text());
    var svcMeasure3 = (+$('#svcMeasure-3').text());
    var svcMeasure4 = (+$('#svcMeasure-4').text());
    var svcMeasure5 = (+$('#svcMeasure-5').text());
    $('#svcMeasure-6').text(svcMeasure1+svcMeasure2+svcMeasure3+svcMeasure4+svcMeasure5);
    if (svcActive1 != 0) {
        $('#svcMeasureRatio-1').text(Math.round(svcMeasure1*100 / svcActive1) + '%');
    }
    if (svcActive2 != 0) {
        $('#svcMeasureRatio-2').text(Math.round(svcMeasure2*100 / svcActive2) + '%');
    }
    if (svcActive3 != 0) {
        $('#svcMeasureRatio-3').text(Math.round(svcMeasure3*100 / svcActive3) + '%');
    }
    if (svcActive4 != 0) {
        $('#svcMeasureRatio-4').text(Math.round(svcMeasure4*100 / svcActive4) + '%');
    }
    if (svcActive5 != 0) {
        $('#svcMeasureRatio-5').text(Math.round(svcMeasure5*100 / svcActive5) + '%');
    }
    if (svcActive6 != 0) {
        $('#svcMeasureRatio-6').text(Math.round(svcMeasure6*100 / svcActive6) + '%');
        $('#svc12-6').text(Math.round((+$('#svc12-5').text()) / svcActive6));
        $('#svc8to11-6').text(Math.round((+$('#svc8to11-5').text()) / svcActive6));
        $('#svc4to7-6').text(Math.round((+$('#svc4to7-5').text()) / svcActive6));
        $('#svc1to3-6').text(Math.round((+$('#svc1to3-5').text()) / svcActive6));
        $('#svc0-6').text(Math.round((+$('#svc0-5').text()) / svcActive6));
    }

    $('#cmPostFlyer-6').text((+$('#cmPostFlyer-1').text())+(+$('#cmPostFlyer-2').text())+(+$('#cmPostFlyer-3').text())+(+$('#cmPostFlyer-4').text())+(+$('#cmPostFlyer-5').text()));
    $('#cmHandFlyer-6').text((+$('#cmHandFlyer-1').text())+(+$('#cmHandFlyer-2').text())+(+$('#cmHandFlyer-3').text())+(+$('#cmHandFlyer-4').text())+(+$('#cmHandFlyer-5').text()));
    $('#cmOutGp-6').text((+$('#cmOutGp-1').text())+(+$('#cmOutGp-2').text())+(+$('#cmOutGp-3').text())+(+$('#cmOutGp-4').text())+(+$('#cmOutGp-5').text()));
    $('#cmCpBox-6').text((+$('#cmCpBox-1').text())+(+$('#cmCpBox-2').text())+(+$('#cmCpBox-3').text())+(+$('#cmCpBox-4').text())+(+$('#cmCpBox-5').text()));
    $('#cmOutGot-6').text((+$('#cmOutGot-1').text())+(+$('#cmOutGot-2').text())+(+$('#cmOutGot-3').text())+(+$('#cmOutGot-4').text())+(+$('#cmOutGot-5').text()));
    $('#cmInGot-6').text((+$('#cmInGot-1').text())+(+$('#cmInGot-2').text())+(+$('#cmInGot-3').text())+(+$('#cmInGot-4').text())+(+$('#cmInGot-5').text()));
    $('#cmBlogGot-6').text((+$('#cmBlogGot-1').text())+(+$('#cmBlogGot-2').text())+(+$('#cmBlogGot-3').text())+(+$('#cmBlogGot-4').text())+(+$('#cmBlogGot-5').text()));
    $('#cmBagGot-6').text((+$('#cmBagGot-1').text())+(+$('#cmBagGot-2').text())+(+$('#cmBagGot-3').text())+(+$('#cmBagGot-4').text())+(+$('#cmBagGot-5').text()));
    $('#cmTotalGot-1').text((+$('#cmOutGot-1').text())+(+$('#cmInGot-1').text())+(+$('#cmBlogGot-1').text())+(+$('#cmBagGot-1').text()));
    $('#cmTotalGot-2').text((+$('#cmOutGot-2').text())+(+$('#cmInGot-2').text())+(+$('#cmBlogGot-2').text())+(+$('#cmBagGot-2').text()));
    $('#cmTotalGot-3').text((+$('#cmOutGot-3').text())+(+$('#cmInGot-3').text())+(+$('#cmBlogGot-3').text())+(+$('#cmBagGot-3').text()));
    $('#cmTotalGot-4').text((+$('#cmOutGot-4').text())+(+$('#cmInGot-4').text())+(+$('#cmBlogGot-4').text())+(+$('#cmBagGot-4').text()));
    $('#cmTotalGot-5').text((+$('#cmOutGot-5').text())+(+$('#cmInGot-5').text())+(+$('#cmBlogGot-5').text())+(+$('#cmBagGot-5').text()));
    $('#cmTotalGot-6').text((+$('#cmTotalGot-1').text())+(+$('#cmTotalGot-2').text())+(+$('#cmTotalGot-3').text())+(+$('#cmTotalGot-4').text())+(+$('#cmTotalGot-5').text()));
    var cmCallIn1 = +$('#cmCallIn-1');
    var cmCallIn2 = +$('#cmCallIn-2');
    var cmCallIn3 = +$('#cmCallIn-3');
    var cmCallIn4 = +$('#cmCallIn-4');
    var cmCallIn5 = +$('#cmCallIn-5');
    var cmCallIn6 = cmCallIn1+cmCallIn2+cmCallIn3+cmCallIn4+cmCallIn5;
    $('#cmCallIn-6').text(cmCallIn6);
    $('#cmOutGotCall-6').text((+$('#cmOutGotCall-1').text())+(+$('#cmOutGotCall-2').text())+(+$('#cmOutGotCall-3').text())+(+$('#cmOutGotCall-4').text())+(+$('#cmOutGotCall-5').text()));
    $('#cmInGotCall-6').text((+$('#cmInGotCall-1').text())+(+$('#cmInGotCall-2').text())+(+$('#cmInGotCall-3').text())+(+$('#cmInGotCall-4').text())+(+$('#cmInGotCall-5').text()));
    $('#cmBlogGotCall-6').text((+$('#cmBlogGotCall-1').text())+(+$('#cmBlogGotCall-2').text())+(+$('#cmBlogGotCall-3').text())+(+$('#cmBlogGotCall-4').text())+(+$('#cmBlogGotCall-5').text()));
    $('#cmBagGotCall-6').text((+$('#cmBagGotCall-1').text())+(+$('#cmBagGotCall-2').text())+(+$('#cmBagGotCall-3').text())+(+$('#cmBagGotCall-4').text())+(+$('#cmBagGotCall-5').text()));

    var cmOwnRefs1 = +$('#cmOwnRefs-1').text();
    var cmOwnRefs2 = +$('#cmOwnRefs-2').text();
    var cmOwnRefs3 = +$('#cmOwnRefs-3').text();
    var cmOwnRefs4 = +$('#cmOwnRefs-4').text();
    var cmOwnRefs5 = +$('#cmOwnRefs-5').text();
    var cmOwnRefs6 = cmOwnRefs1+cmOwnRefs2+cmOwnRefs3+cmOwnRefs4+cmOwnRefs5;
    var cmOtherRefs1 = +$('#cmOtherRefs-1').text();
    var cmOtherRefs2 = +$('#cmOtherRefs-2').text();
    var cmOtherRefs3 = +$('#cmOtherRefs-3').text();
    var cmOtherRefs4 = +$('#cmOtherRefs-4').text();
    var cmOtherRefs5 = +$('#cmOtherRefs-5').text();
    var cmOtherRefs6 = cmOtherRefs1+cmOtherRefs2+cmOtherRefs3+cmOtherRefs4+cmOtherRefs5;
    var cmNewspaper1 = +$('#cmNewspaper-1').text();
    var cmNewspaper2 = +$('#cmNewspaper-2').text();
    var cmNewspaper3 = +$('#cmNewspaper-3').text();
    var cmNewspaper4 = +$('#cmNewspaper-4').text();
    var cmNewspaper5 = +$('#cmNewspaper-5').text();
    var cmNewspaper6 = cmNewspaper1+cmNewspaper2+cmNewspaper3+cmNewspaper4+cmNewspaper5;
    var cmTv1 = +$('#cmTv-1').text();
    var cmTv2 = +$('#cmTv-2').text();
    var cmTv3 = +$('#cmTv-3').text();
    var cmTv4 = +$('#cmTv-4').text();
    var cmTv5 = +$('#cmTv-5').text();
    var cmTv6 = cmTv1+cmTv2+cmTv3+cmTv4+cmTv5;
    var cmInternet1 = +$('#cmInternet-1').text();
    var cmInternet2 = +$('#cmInternet-2').text();
    var cmInternet3 = +$('#cmInternet-3').text();
    var cmInternet4 = +$('#cmInternet-4').text();
    var cmInternet5 = +$('#cmInternet-5').text();
    var cmInternet6 = cmInternet1+cmInternet2+cmInternet3+cmInternet4+cmInternet5
    var cmSign1 = +$('#cmSign-1').text();
    var cmSign2 = +$('#cmSign-2').text();
    var cmSign3 = +$('#cmSign-3').text();
    var cmSign4 = +$('#cmSign-4').text();
    var cmSign5 = +$('#cmSign-5').text();
    var cmSign6 = cmSign1+cmSign2+cmSign3+cmSign4+cmSign5;
    var cmMate1 = +$('#cmMate-1').text();
    var cmMate2 = +$('#cmMate-2').text();
    var cmMate3 = +$('#cmMate-3').text();
    var cmMate4 = +$('#cmMate-4').text();
    var cmMate5 = +$('#cmMate-5').text();
    var cmMate6 = cmMate1+cmMate2+cmMate3+cmMate4+cmMate5;
    var cmOthers1 = +$('#cmOthers-1').text();
    var cmOthers2 = +$('#cmOthers-2').text();
    var cmOthers3 = +$('#cmOthers-3').text();
    var cmOthers4 = +$('#cmOthers-4').text();
    var cmOthers5 = +$('#cmOthers-5').text();
    var cmOthers6 = cmOthers1+cmOthers2+cmOthers3+cmOthers4+cmOthers5;
    var cmMailAgpIn1 = +$('#cmMailAgpIn-1').text();
    var cmMailAgpIn2 = +$('#cmMailAgpIn-2').text();
    var cmMailAgpIn3 = +$('#cmMailAgpIn-3').text();
    var cmMailAgpIn4 = +$('#cmMailAgpIn-4').text();
    var cmMailAgpIn5 = +$('#cmMailAgpIn-5').text();
    var cmMailAgpIn6 = cmMailAgpIn1+cmMailAgpIn2+cmMailAgpIn3+cmMailAgpIn4+cmMailAgpIn5;
    var cmPostFlyerAgpIn1 = +$('#cmPostFlyerAgpIn-1').text();
    var cmPostFlyerAgpIn2 = +$('#cmPostFlyerAgpIn-2').text();
    var cmPostFlyerAgpIn3 = +$('#cmPostFlyerAgpIn-3').text();
    var cmPostFlyerAgpIn4 = +$('#cmPostFlyerAgpIn-4').text();
    var cmPostFlyerAgpIn5 = +$('#cmPostFlyerAgpIn-5').text();
    var cmPostFlyerAgpIn6 = cmPostFlyerAgpIn1+cmPostFlyerAgpIn2+cmPostFlyerAgpIn3+cmPostFlyerAgpIn4+cmPostFlyerAgpIn5;
    var cmHandFlyerAgpIn1 = +$('#cmHandFlyerAgpIn-1').text();
    var cmHandFlyerAgpIn2 = +$('#cmHandFlyerAgpIn-2').text();
    var cmHandFlyerAgpIn3 = +$('#cmHandFlyerAgpIn-3').text();
    var cmHandFlyerAgpIn4 = +$('#cmHandFlyerAgpIn-4').text();
    var cmHandFlyerAgpIn5 = +$('#cmHandFlyerAgpIn-5').text();
    var cmHandFlyerAgpIn6 = cmHandFlyerAgpIn1+cmHandFlyerAgpIn2+cmHandFlyerAgpIn3+cmHandFlyerAgpIn4+cmHandFlyerAgpIn5;
    var cmCpAgpIn1 = +$('#cmCpAgpIn-1').text();
    var cmCpAgpIn2 = +$('#cmCpAgpIn-2').text();
    var cmCpAgpIn3 = +$('#cmCpAgpIn-3').text();
    var cmCpAgpIn4 = +$('#cmCpAgpIn-4').text();
    var cmCpAgpIn5 = +$('#cmCpAgpIn-5').text();
    var cmCpAgpIn6 = cmCpAgpIn1+cmCpAgpIn2+cmCpAgpIn3+cmCpAgpIn4+cmCpAgpIn5;
    var cmOutAgpOut1 = +$('#cmOutAgpOut-1').text();
    var cmOutAgpOut2 = +$('#cmOutAgpOut-2').text();
    var cmOutAgpOut3 = +$('#cmOutAgpOut-3').text();
    var cmOutAgpOut4 = +$('#cmOutAgpOut-4').text();
    var cmOutAgpOut5 = +$('#cmOutAgpOut-5').text();
    var cmOutAgpOut6 = cmOutAgpOut1+cmOutAgpOut2+cmOutAgpOut3+cmOutAgpOut4+cmOutAgpOut5;
    var cmInAgpOut1 = +$('#cmInAgpOut-1').text();
    var cmInAgpOut2 = +$('#cmInAgpOut-2').text();
    var cmInAgpOut3 = +$('#cmInAgpOut-3').text();
    var cmInAgpOut4 = +$('#cmInAgpOut-4').text();
    var cmInAgpOut5 = +$('#cmInAgpOut-5').text();
    var cmInAgpOut6 = cmInAgpOut1+cmInAgpOut2+cmInAgpOut3+cmInAgpOut4+cmInAgpOut5;
    var cmBlogAgpOut1 = +$('#cmBlogAgpOut-1').text();
    var cmBlogAgpOut2 = +$('#cmBlogAgpOut-2').text();
    var cmBlogAgpOut3 = +$('#cmBlogAgpOut-3').text();
    var cmBlogAgpOut4 = +$('#cmBlogAgpOut-4').text();
    var cmBlogAgpOut5 = +$('#cmBlogAgpOut-5').text();
    var cmBlogAgpOut6 = cmBlogAgpOut1+cmBlogAgpOut2+cmBlogAgpOut3+cmBlogAgpOut4+cmBlogAgpOut5;
    var cmBagAgpOut1 = +$('#cmBagAgpOut-1').text();
    var cmBagAgpOut2 = +$('#cmBagAgpOut-2').text();
    var cmBagAgpOut3 = +$('#cmBagAgpOut-3').text();
    var cmBagAgpOut4 = +$('#cmBagAgpOut-4').text();
    var cmBagAgpOut5 = +$('#cmBagAgpOut-5').text();
    var cmBagAgpOut6 = cmBagAgpOut1+cmBagAgpOut2+cmBagAgpOut3+cmBagAgpOut4+cmBagAgpOut5;
    $('#cmOwnRefs-6').text(cmOwnRefs6);
    $('#cmOtherRefs-6').text(cmOtherRefs6);
    $('#cmNewspaper-6').text(cmNewspaper6);
    $('#cmTv-6').text(cmTv6);
    $('#cmInternet-6').text(cmInternet6);
    $('#cmSign-6').text(cmSign6);
    $('#cmMate-6').text(cmMate6);
    $('#cmOthers-6').text(cmOthers6);
    $('#cmMailAgpIn-6').text(cmMailAgpIn6);
    $('#cmPostFlyerAgpIn-6').text(cmPostFlyerAgpIn6);
    $('#cmHandFlyerAgpIn-6').text(cmHandFlyerAgpIn6);
    $('#cmCpAgpIn-6').text(cmCpAgpIn6);
    $('#cmOutAgpOut-6').text(cmOutAgpOut6);
    $('#cmInAgpOut-6').text(cmInAgpOut6);
    $('#cmBlogAgpOut-6').text(cmBlogAgpOut6);
    $('#cmBagAgpOut-6').text(cmBagAgpOut6);

    var cmInApoTotal1 = cmOwnRefs1+cmOtherRefs1+cmNewspaper1+cmTv1+cmInternet1+cmSign1+cmMate1+cmOthers1+cmMailAgpIn1
            +cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1;
    var cmInApoTotal2 = cmOwnRefs2+cmOtherRefs2+cmNewspaper2+cmTv2+cmInternet2+cmSign2+cmMate2+cmOthers2+cmMailAgpIn2
            +cmPostFlyerAgpIn2+cmHandFlyerAgpIn2+cmCpAgpIn2;
    var cmInApoTotal3 = cmOwnRefs3+cmOtherRefs3+cmNewspaper3+cmTv3+cmInternet3+cmSign3+cmMate3+cmOthers3+cmMailAgpIn3
            +cmPostFlyerAgpIn3+cmHandFlyerAgpIn3+cmCpAgpIn3;
    var cmInApoTotal4 = cmOwnRefs4+cmOtherRefs4+cmNewspaper4+cmTv4+cmInternet4+cmSign4+cmMate4+cmOthers4+cmMailAgpIn4
            +cmPostFlyerAgpIn4+cmHandFlyerAgpIn4+cmCpAgpIn4;
    var cmInApoTotal5 = cmOwnRefs5+cmOtherRefs5+cmNewspaper5+cmTv5+cmInternet5+cmSign5+cmMate5+cmOthers5+cmMailAgpIn5
            +cmPostFlyerAgpIn5+cmHandFlyerAgpIn5+cmCpAgpIn5;
    var cmInApoTotal6 = cmOwnRefs6+cmOtherRefs6+cmNewspaper6+cmTv6+cmInternet6+cmSign6+cmMate6+cmOthers6+cmMailAgpIn6
            +cmPostFlyerAgpIn6+cmHandFlyerAgpIn6+cmCpAgpIn6;
    var ApgOutTotal1 = cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1;
    var ApgOutTotal2 = cmOutAgpOut2+cmInAgpOut2+cmBlogAgpOut2+cmBagAgpOut2;
    var ApgOutTotal3 = cmOutAgpOut3+cmInAgpOut3+cmBlogAgpOut3+cmBagAgpOut3;
    var ApgOutTotal4 = cmOutAgpOut4+cmInAgpOut4+cmBlogAgpOut4+cmBagAgpOut4;
    var ApgOutTotal5 = cmOutAgpOut5+cmInAgpOut5+cmBlogAgpOut5+cmBagAgpOut5;
    var ApgOutTotal6 = cmOutAgpOut6+cmInAgpOut6+cmBlogAgpOut6+cmBagAgpOut6;
    var agpTotal1 = cmInApoTotal1+ApgOutTotal1;
    var agpTotal2 = cmInApoTotal2+ApgOutTotal2;
    var agpTotal3 = cmInApoTotal3+ApgOutTotal3;
    var agpTotal4 = cmInApoTotal4+ApgOutTotal4;
    var agpTotal5 = cmInApoTotal5+ApgOutTotal5;
    var agpTotal6 = cmOwnRefs6+cmOtherRefs6+cmNewspaper6+cmTv6+cmInternet6+cmSign6+cmMate6+cmOthers6+cmMailAgpIn6
        +cmPostFlyerAgpIn6+cmHandFlyerAgpIn6+cmCpAgpIn6+cmOutAgpOut6+cmInAgpOut6+cmBlogAgpOut6+cmBagAgpOut6;
    $('#cmApoTotal-1').text(agpTotal1);
    $('#cmApoTotal-2').text(agpTotal2);
    $('#cmApoTotal-3').text(agpTotal3);
    $('#cmApoTotal-4').text(agpTotal4);
    $('#cmApoTotal-5').text(agpTotal5);
    $('#cmApoTotal-6').text(agpTotal6);
    if (cmCallIn1 != 0) {
        $('#cmInApptRatio-1').text(Math.round(cmInApoTotal1*100/cmCallIn1) + '%');
    }
    if (cmCallIn2 != 0) {
        $('#cmInApptRatio-2').text(Math.round(cmInApoTotal2*100/cmCallIn2) + '%');
    }
    if (cmCallIn3 != 0) {
        $('#cmInApptRatio-3').text(Math.round(cmInApoTotal3*100/cmCallIn3) + '%');
    }
    if (cmCallIn4 != 0) {
        $('#cmInApptRatio-4').text(Math.round(cmInApoTotal4*100/cmCallIn4) + '%');
    }
    if (cmCallIn5 != 0) {
        $('#cmInApptRatio-5').text(Math.round(cmInApoTotal5*100/cmCallIn5) + '%');
    }
    if (cmCallIn6 != 0) {
        $('#cmInApptRatio-6').text(Math.round(cmInApoTotal6*100/cmCallIn6) + '%');
    }
    var cmGotCallTotal1 = (+$('#cmOutGotCall-1').text())+(+$('#cmInGotCall-1').text())+(+$('#cmBlogGotCall-1').text())+(+$('#cmBagGotCall-1').text());
    $('#cmOutApptRatio-1').text(Math.round(cmGotCallTotal1*100/ApgOutTotal1) + '%');
    var cmGotCallTotal2 = (+$('#cmOutGotCall-2').text())+(+$('#cmInGotCall-2').text())+(+$('#cmBlogGotCall-2').text())+(+$('#cmBagGotCall-2').text());
    $('#cmOutApptRatio-2').text(Math.round(cmGotCallTotal2*100/ApgOutTotal2) + '%');
    var cmGotCallTotal3 = (+$('#cmOutGotCall-3').text())+(+$('#cmInGotCall-3').text())+(+$('#cmBlogGotCall-3').text())+(+$('#cmBagGotCall-3').text());
    $('#cmOutApptRatio-3').text(Math.round(cmGotCallTotal3*100/ApgOutTotal3) + '%');
    var cmGotCallTotal4 = (+$('#cmOutGotCall-4').text())+(+$('#cmInGotCall-4').text())+(+$('#cmBlogGotCall-4').text())+(+$('#cmBagGotCall-4').text());
    $('#cmOutApptRatio-4').text(Math.round(cmGotCallTotal4*100/ApgOutTotal4) + '%');
    var cmGotCallTotal5 = (+$('#cmOutGotCall-5').text())+(+$('#cmInGotCall-5').text())+(+$('#cmBlogGotCall-5').text())+(+$('#cmBagGotCall-5').text());
    $('#cmOutApptRatio-5').text(Math.round(cmGotCallTotal5*100/ApgOutTotal5) + '%');
    var cmGotCallTotal6 = (+$('#cmOutGotCall-6').text())+(+$('#cmInGotCall-6').text())+(+$('#cmBlogGotCall-6').text())+(+$('#cmBagGotCall-6').text());
    $('#cmOutApptRatio-6').text(Math.round(cmGotCallTotal6*100/ApgOutTotal6) + '%');

    $('#cmPostPerApo-6').text((+$('#cmPostFlyer-6').text())/(+$('#cmPostFlyerAgpIn-6').text()));
    $('#cmHandPerApo-6').text((+$('#cmHandFlyer-6').text())/(+$('#cmHandFlyerAgpIn-6').text()));
    $('#cmOutPerApo-6').text((+$('#cmOutGp-6').text())/(+$('#cmOutAgpOut-6').text()));
    $('#cmBrAgpRatio-1').text(Math.round((cmOwnRefs1+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal1)) + '%');
    $('#cmBrAgpRatio-2').text(Math.round((cmOwnRefs2+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal2)) + '%');
    $('#cmBrAgpRatio-3').text(Math.round((cmOwnRefs3+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal3)) + '%');
    $('#cmBrAgpRatio-4').text(Math.round((cmOwnRefs4+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal4)) + '%');
    $('#cmBrAgpRatio-5').text(Math.round((cmOwnRefs5+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal5)) + '%');
    $('#cmBrAgpRatio-6').text(Math.round((cmOwnRefs6+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal6)) + '%');
    $('#cmFaSum-6').text((+$('#cmFaSum-1').text())+(+$('#cmFaSum-2').text())+(+$('#cmFaSum-3').text())+(+$('#cmFaSum-4').text())+(+$('#cmFaSum-5').text()));
    $('#cmShowRatio-1').text(Math.round((+$('#cmFaSum-1').text())*100/agpTotal1) + '%');
    $('#cmShowRatio-2').text(Math.round((+$('#cmFaSum-2').text())*100/agpTotal2) + '%');
    $('#cmShowRatio-3').text(Math.round((+$('#cmFaSum-3').text())*100/agpTotal3) + '%');
    $('#cmShowRatio-4').text(Math.round((+$('#cmFaSum-4').text())*100/agpTotal4) + '%');
    $('#cmShowRatio-5').text(Math.round((+$('#cmFaSum-5').text())*100/agpTotal5) + '%');
    $('#cmShowRatio-6').text(Math.round((+$('#cmFaSum-6').text())*100/agpTotal6) + '%');

    $('#salesAch-6').text((+$('#salesAch-1').text())+(+$('#salesAch-2').text())+(+$('#salesAch-3').text())+(+$('#salesAch-4').text())+(+$('#salesAch-5').text()));
    $('#salesMonthly-6').text((+$('#salesMonthly-1').text())+(+$('#salesMonthly-2').text())+(+$('#salesMonthly-3').text())+(+$('#salesMonthly-4').text())+(+$('#salesMonthly-5').text()));
    $('#salesAllPrepay-6').text((+$('#salesAllPrepay-1').text())+(+$('#salesAllPrepay-2').text())+(+$('#salesAllPrepay-3').text())+(+$('#salesAllPrepay-4').text())+(+$('#salesAllPrepay-5').text()));
    var salesTotal1 = (+$('#salesAch-1').text()) + (+$('#salesMonthly-1').text()) + (+$('#salesAllPrepay-1').text());
    var salesTotal2 = (+$('#salesAch-2').text()) + (+$('#salesMonthly-2').text()) + (+$('#salesAllPrepay-2').text());
    var salesTotal3 = (+$('#salesAch-3').text()) + (+$('#salesMonthly-3').text()) + (+$('#salesAllPrepay-3').text());
    var salesTotal4 = (+$('#salesAch-4').text()) + (+$('#salesMonthly-4').text()) + (+$('#salesAllPrepay-4').text());
    var salesTotal5 = (+$('#salesAch-5').text()) + (+$('#salesMonthly-5').text()) + (+$('#salesAllPrepay-5').text());
    var salesTotal6 = (+$('#salesAch-6').text()) + (+$('#salesMonthly-6').text()) + (+$('#salesAllPrepay-6').text());
    $('#salesTotal-1').text(salesTotal1);
    $('#salesTotal-2').text(salesTotal2);
    $('#salesTotal-3').text(salesTotal3);
    $('#salesTotal-4').text(salesTotal4);
    $('#salesTotal-5').text(salesTotal5);
    $('#salesTotal-6').text(salesTotal6);
    $('#salesRatio-1').text(Math.round(salesTotal1*100/(+$('#cmFaSum-1').text())) + '%');
    $('#salesRatio-2').text(Math.round(salesTotal2*100/(+$('#cmFaSum-2').text())) + '%');
    $('#salesRatio-3').text(Math.round(salesTotal3*100/(+$('#cmFaSum-3').text())) + '%');
    $('#salesRatio-4').text(Math.round(salesTotal4*100/(+$('#cmFaSum-4').text())) + '%');
    $('#salesRatio-5').text(Math.round(salesTotal5*100/(+$('#cmFaSum-5').text())) + '%');
    $('#salesRatio-6').text(Math.round(salesTotal6*100/(+$('#cmFaSum-6').text())) + '%');
    $('#salesAchAppRatio-1').text(Math.round(((+$('#salesAch-1').text()) + (+$('#salesAllPrepay-1').text()))*100/salesTotal1) + '%');
    $('#salesAchAppRatio-2').text(Math.round(((+$('#salesAch-2').text()) + (+$('#salesAllPrepay-2').text()))*100/salesTotal2) + '%');
    $('#salesAchAppRatio-3').text(Math.round(((+$('#salesAch-3').text()) + (+$('#salesAllPrepay-3').text()))*100/salesTotal3) + '%');
    $('#salesAchAppRatio-4').text(Math.round(((+$('#salesAch-4').text()) + (+$('#salesAllPrepay-4').text()))*100/salesTotal4) + '%');
    $('#salesAchAppRatio-5').text(Math.round(((+$('#salesAch-5').text()) + (+$('#salesAllPrepay-5').text()))*100/salesTotal5) + '%');
    $('#salesAchAppRatio-6').text(Math.round(((+$('#salesAch-6').text()) + (+$('#salesAllPrepay-6').text()))*100/salesTotal6) + '%');

    var staff1AchTotal = (+$('#staff1Ach-1').text())+(+$('#staff1Ach-2').text())+(+$('#staff1Ach-3').text())+(+$('#staff1Ach-4').text())+(+$('#staff1Ach-5').text());
    var staff2AchTotal = (+$('#staff2Ach-1').text())+(+$('#staff2Ach-2').text())+(+$('#staff2Ach-3').text())+(+$('#staff2Ach-4').text())+(+$('#staff2Ach-5').text());
    var staff3AchTotal = (+$('#staff3Ach-1').text())+(+$('#staff3Ach-2').text())+(+$('#staff3Ach-3').text())+(+$('#staff3Ach-4').text())+(+$('#staff3Ach-5').text());
    var staff4AchTotal = (+$('#staff4Ach-1').text())+(+$('#staff4Ach-2').text())+(+$('#staff4Ach-3').text())+(+$('#staff4Ach-4').text())+(+$('#staff4Ach-5').text());
    var staff5AchTotal = (+$('#staff5Ach-1').text())+(+$('#staff5Ach-2').text())+(+$('#staff5Ach-3').text())+(+$('#staff5Ach-4').text())+(+$('#staff5Ach-5').text());
    var staff6AchTotal = (+$('#staff6Ach-1').text())+(+$('#staff6Ach-2').text())+(+$('#staff6Ach-3').text())+(+$('#staff6Ach-4').text())+(+$('#staff6Ach-5').text());
    var staff1MmTotal = (+$('#staff1Mm-1').text())+(+$('#staff1Mm-2').text())+(+$('#staff1Mm-3').text())+(+$('#staff1Mm-4').text())+(+$('#staff1Mm-5').text());
    var staff2MmTotal = (+$('#staff2Mm-1').text())+(+$('#staff2Mm-2').text())+(+$('#staff2Mm-3').text())+(+$('#staff2Mm-4').text())+(+$('#staff2Mm-5').text());
    var staff3MmTotal = (+$('#staff3Mm-1').text())+(+$('#staff3Mm-2').text())+(+$('#staff3Mm-3').text())+(+$('#staff3Mm-4').text())+(+$('#staff3Mm-5').text());
    var staff4MmTotal = (+$('#staff4Mm-1').text())+(+$('#staff4Mm-2').text())+(+$('#staff4Mm-3').text())+(+$('#staff4Mm-4').text())+(+$('#staff4Mm-5').text());
    var staff5MmTotal = (+$('#staff5Mm-1').text())+(+$('#staff5Mm-2').text())+(+$('#staff5Mm-3').text())+(+$('#staff5Mm-4').text())+(+$('#staff5Mm-5').text());
    var staff6MmTotal = (+$('#staff6Mm-1').text())+(+$('#staff6Mm-2').text())+(+$('#staff6Mm-3').text())+(+$('#staff6Mm-4').text())+(+$('#staff6Mm-5').text());
    var staff1AppTotal = (+$('#staff1App-1').text())+(+$('#staff1App-2').text())+(+$('#staff1App-3').text())+(+$('#staff1App-4').text())+(+$('#staff1App-5').text());
    var staff2AppTotal = (+$('#staff2App-1').text())+(+$('#staff2App-2').text())+(+$('#staff2App-3').text())+(+$('#staff2App-4').text())+(+$('#staff2App-5').text());
    var staff3AppTotal = (+$('#staff3App-1').text())+(+$('#staff3App-2').text())+(+$('#staff3App-3').text())+(+$('#staff3App-4').text())+(+$('#staff3App-5').text());
    var staff4AppTotal = (+$('#staff4App-1').text())+(+$('#staff4App-2').text())+(+$('#staff4App-3').text())+(+$('#staff4App-4').text())+(+$('#staff4App-5').text());
    var staff5AppTotal = (+$('#staff5App-1').text())+(+$('#staff5App-2').text())+(+$('#staff5App-3').text())+(+$('#staff5App-4').text())+(+$('#staff5App-5').text());
    var staff6AppTotal = (+$('#staff6App-1').text())+(+$('#staff6App-2').text())+(+$('#staff6App-3').text())+(+$('#staff6App-4').text())+(+$('#staff6App-5').text());
    var staff1NsTotal = (+$('#staff1Ns-1').text())+(+$('#staff1Ns-2').text())+(+$('#staff1Ns-3').text())+(+$('#staff1Ns-4').text())+(+$('#staff1Ns-5').text());
    var staff2NsTotal = (+$('#staff2Ns-1').text())+(+$('#staff2Ns-2').text())+(+$('#staff2Ns-3').text())+(+$('#staff2Ns-4').text())+(+$('#staff2Ns-5').text());
    var staff3NsTotal = (+$('#staff3Ns-1').text())+(+$('#staff3Ns-2').text())+(+$('#staff3Ns-3').text())+(+$('#staff3Ns-4').text())+(+$('#staff3Ns-5').text());
    var staff4NsTotal = (+$('#staff4Ns-1').text())+(+$('#staff4Ns-2').text())+(+$('#staff4Ns-3').text())+(+$('#staff4Ns-4').text())+(+$('#staff4Ns-5').text());
    var staff5NsTotal = (+$('#staff5Ns-1').text())+(+$('#staff5Ns-2').text())+(+$('#staff5Ns-3').text())+(+$('#staff5Ns-4').text())+(+$('#staff5Ns-5').text());
    var staff6NsTotal = (+$('#staff6Ns-1').text())+(+$('#staff6Ns-2').text())+(+$('#staff6Ns-3').text())+(+$('#staff6Ns-4').text())+(+$('#staff6Ns-5').text());
    var staff1LxTotal = (+$('#staff1Lx-1').text())+(+$('#staff1Lx-2').text())+(+$('#staff1Lx-3').text())+(+$('#staff1Lx-4').text())+(+$('#staff1Lx-5').text());
    var staff2LxTotal = (+$('#staff2Lx-1').text())+(+$('#staff2Lx-2').text())+(+$('#staff2Lx-3').text())+(+$('#staff2Lx-4').text())+(+$('#staff2Lx-5').text());
    var staff3LxTotal = (+$('#staff3Lx-1').text())+(+$('#staff3Lx-2').text())+(+$('#staff3Lx-3').text())+(+$('#staff3Lx-4').text())+(+$('#staff3Lx-5').text());
    var staff4LxTotal = (+$('#staff4Lx-1').text())+(+$('#staff4Lx-2').text())+(+$('#staff4Lx-3').text())+(+$('#staff4Lx-4').text())+(+$('#staff4Lx-5').text());
    var staff5LxTotal = (+$('#staff5Lx-1').text())+(+$('#staff5Lx-2').text())+(+$('#staff5Lx-3').text())+(+$('#staff5Lx-4').text())+(+$('#staff5Lx-5').text());
    var staff6LxTotal = (+$('#staff6Lx-1').text())+(+$('#staff6Lx-2').text())+(+$('#staff6Lx-3').text())+(+$('#staff6Lx-4').text())+(+$('#staff6Lx-5').text());
    $('#staff1Ach-6').text(staff1AchTotal);
    $('#staff2Ach-6').text(staff2AchTotal);
    $('#staff3Ach-6').text(staff3AchTotal);
    $('#staff4Ach-6').text(staff4AchTotal);
    $('#staff5Ach-6').text(staff5AchTotal);
    $('#staff6Ach-6').text(staff6AchTotal);
    var ach1Total = (+$('#staff1Ach-1').text())+(+$('#staff2Ach-1').text())+(+$('#staff3Ach-1').text())+(+$('#staff4Ach-1').text())+(+$('#staff5Ach-1').text())+(+$('#staff6Ach-1').text());
    $('#clubAch-1').text(ach1Total);
    var ach2Total = (+$('#staff1Ach-2').text())+(+$('#staff2Ach-2').text())+(+$('#staff3Ach-2').text())+(+$('#staff4Ach-2').text())+(+$('#staff5Ach-2').text())+(+$('#staff6Ach-2').text());
    $('#clubAch-2').text(ach2Total);
    var ach3Total = (+$('#staff1Ach-3').text())+(+$('#staff2Ach-3').text())+(+$('#staff3Ach-3').text())+(+$('#staff4Ach-3').text())+(+$('#staff5Ach-3').text())+(+$('#staff6Ach-3').text());
    $('#clubAch-3').text(ach3Total);
    var ach4Total = (+$('#staff1Ach-4').text())+(+$('#staff2Ach-4').text())+(+$('#staff3Ach-4').text())+(+$('#staff4Ach-4').text())+(+$('#staff5Ach-4').text())+(+$('#staff6Ach-4').text());
    $('#clubAch-4').text(ach4Total);
    var ach5Total = (+$('#staff1Ach-5').text())+(+$('#staff2Ach-5').text())+(+$('#staff3Ach-5').text())+(+$('#staff4Ach-5').text())+(+$('#staff5Ach-5').text())+(+$('#staff6Ach-5').text());
    $('#clubAch-5').text(ach5Total);
    $('#clubAch-6').text(ach1Total+ach2Total+ach3Total+ach4Total+ach5Total);
    $('#staff1Mm-6').text(staff1MmTotal);
    $('#staff2Mm-6').text(staff2MmTotal);
    $('#staff3Mm-6').text(staff3MmTotal);
    $('#staff4Mm-6').text(staff4MmTotal);
    $('#staff5Mm-6').text(staff5MmTotal);
    $('#staff6Mm-6').text(staff6MmTotal);
    var mm1Total = (+$('#staff1Mm-1').text())+(+$('#staff2Mm-1').text())+(+$('#staff3Mm-1').text())+(+$('#staff4Mm-1').text())+(+$('#staff5Mm-1').text())+(+$('#staff6Mm-1').text());
    $('#clubMm-1').text(mm1Total);
    var mm2Total = (+$('#staff1Mm-2').text())+(+$('#staff2Mm-2').text())+(+$('#staff3Mm-2').text())+(+$('#staff4Mm-2').text())+(+$('#staff5Mm-2').text())+(+$('#staff6Mm-2').text());
    $('#clubMm-2').text(mm2Total);
    var mm3Total = (+$('#staff1Mm-3').text())+(+$('#staff2Mm-3').text())+(+$('#staff3Mm-3').text())+(+$('#staff4Mm-3').text())+(+$('#staff5Mm-3').text())+(+$('#staff6Mm-3').text());
    $('#clubMm-3').text(mm3Total);
    var mm4Total = (+$('#staff1Mm-4').text())+(+$('#staff2Mm-4').text())+(+$('#staff3Mm-4').text())+(+$('#staff4Mm-4').text())+(+$('#staff5Mm-4').text())+(+$('#staff6Mm-4').text());
    $('#clubMm-4').text(mm4Total);
    var mm5Total = (+$('#staff1Mm-5').text())+(+$('#staff2Mm-5').text())+(+$('#staff3Mm-5').text())+(+$('#staff4Mm-5').text())+(+$('#staff5Mm-5').text())+(+$('#staff6Mm-5').text());
    $('#clubMm-5').text(mm5Total);
    $('#clubMm-6').text(mm1Total+mm2Total+mm3Total+mm4Total+mm5Total);
    $('#staff1App-6').text(staff1AppTotal);
    $('#staff2App-6').text(staff2AppTotal);
    $('#staff3App-6').text(staff3AppTotal);
    $('#staff4App-6').text(staff4AppTotal);
    $('#staff5App-6').text(staff5AppTotal);
    $('#staff6App-6').text(staff6AppTotal);
    var app1Total = (+$('#staff1App-1').text())+(+$('#staff2App-1').text())+(+$('#staff3App-1').text())+(+$('#staff4App-1').text())+(+$('#staff5App-1').text())+(+$('#staff6App-1').text());
    $('#clubApp-1').text(app1Total);
    var app2Total = (+$('#staff1App-2').text())+(+$('#staff2App-2').text())+(+$('#staff3App-2').text())+(+$('#staff4App-2').text())+(+$('#staff5App-2').text())+(+$('#staff6App-2').text());
    $('#clubApp-2').text(app2Total);
    var app3Total = (+$('#staff1App-3').text())+(+$('#staff2App-3').text())+(+$('#staff3App-3').text())+(+$('#staff4App-3').text())+(+$('#staff5App-3').text())+(+$('#staff6App-3').text());
    $('#clubApp-3').text(app3Total);
    var app4Total = (+$('#staff1App-4').text())+(+$('#staff2App-4').text())+(+$('#staff3App-4').text())+(+$('#staff4App-4').text())+(+$('#staff5App-4').text())+(+$('#staff6App-4').text());
    $('#clubApp-4').text(app4Total);
    var app5Total = (+$('#staff1App-5').text())+(+$('#staff2App-5').text())+(+$('#staff3App-5').text())+(+$('#staff4App-5').text())+(+$('#staff5App-5').text())+(+$('#staff6App-5').text());
    $('#clubApp-5').text(app5Total);
    $('#clubApp-6').text(app1Total+app2Total+app3Total+app4Total+app5Total);
    $('#staff1Ns-6').text(staff1NsTotal);
    $('#staff2Ns-6').text(staff2NsTotal);
    $('#staff3Ns-6').text(staff3NsTotal);
    $('#staff4Ns-6').text(staff4NsTotal);
    $('#staff5Ns-6').text(staff5NsTotal);
    $('#staff6Ns-6').text(staff6NsTotal);
    var ns1Total = (+$('#staff1Ns-1').text())+(+$('#staff2Ns-1').text())+(+$('#staff3Ns-1').text())+(+$('#staff4Ns-1').text())+(+$('#staff5Ns-1').text())+(+$('#staff6Ns-1').text());
    $('#clubNs-1').text(ns1Total);
    var ns2Total = (+$('#staff1Ns-2').text())+(+$('#staff2Ns-2').text())+(+$('#staff3Ns-2').text())+(+$('#staff4Ns-2').text())+(+$('#staff5Ns-2').text())+(+$('#staff6Ns-2').text());
    $('#clubNs-2').text(ns2Total);
    var ns3Total = (+$('#staff1Ns-3').text())+(+$('#staff2Ns-3').text())+(+$('#staff3Ns-3').text())+(+$('#staff4Ns-3').text())+(+$('#staff5Ns-3').text())+(+$('#staff6Ns-3').text());
    $('#clubNs-3').text(ns3Total);
    var ns4Total = (+$('#staff1Ns-4').text())+(+$('#staff2Ns-4').text())+(+$('#staff3Ns-4').text())+(+$('#staff4Ns-4').text())+(+$('#staff5Ns-4').text())+(+$('#staff6Ns-4').text());
    $('#clubNs-4').text(ns4Total);
    var ns5Total = (+$('#staff1Ns-5').text())+(+$('#staff2Ns-5').text())+(+$('#staff3Ns-5').text())+(+$('#staff4Ns-5').text())+(+$('#staff5Ns-5').text())+(+$('#staff6Ns-5').text());
    $('#clubNs-5').text(ns5Total);
    $('#clubNs-6').text(ns1Total+ns2Total+ns3Total+ns4Total+ns5Total);
    $('#staff1Lx-6').text(staff1LxTotal);
    $('#staff2Lx-6').text(staff2LxTotal);
    $('#staff3Lx-6').text(staff3LxTotal);
    $('#staff4Lx-6').text(staff4LxTotal);
    $('#staff5Lx-6').text(staff5LxTotal);
    $('#staff6Lx-6').text(staff6LxTotal);
    var lx1Total = (+$('#staff1Lx-1').text())+(+$('#staff2Lx-1').text())+(+$('#staff3Lx-1').text())+(+$('#staff4Lx-1').text())+(+$('#staff5Lx-1').text())+(+$('#staff6Lx-1').text());
    $('#clubLx-1').text(lx1Total);
    var lx2Total = (+$('#staff1Lx-2').text())+(+$('#staff2Lx-2').text())+(+$('#staff3Lx-2').text())+(+$('#staff4Lx-2').text())+(+$('#staff5Lx-2').text())+(+$('#staff6Lx-2').text());
    $('#clubLx-2').text(lx2Total);
    var lx3Total = (+$('#staff1Lx-3').text())+(+$('#staff2Lx-3').text())+(+$('#staff3Lx-3').text())+(+$('#staff4Lx-3').text())+(+$('#staff5Lx-3').text())+(+$('#staff6Lx-3').text());
    $('#clubLx-3').text(lx3Total);
    var lx4Total = (+$('#staff1Lx-4').text())+(+$('#staff2Lx-4').text())+(+$('#staff3Lx-4').text())+(+$('#staff4Lx-4').text())+(+$('#staff5Lx-4').text())+(+$('#staff6Lx-4').text());
    $('#clubLx-4').text(lx4Total);
    var lx5Total = (+$('#staff1Lx-5').text())+(+$('#staff2Lx-5').text())+(+$('#staff3Lx-5').text())+(+$('#staff4Lx-5').text())+(+$('#staff5Lx-5').text())+(+$('#staff6Lx-5').text());
    $('#clubLx-5').text(lx5Total);
    $('#clubLx-6').text(lx1Total+lx2Total+lx3Total+lx4Total+lx5Total);

    var ama = (+$('#clubAch-6').text()) + (+$('#clubAch-6').text()) + (+$('#clubAch-6').text());
    if (ama != 0) {
        var aa = (+$('#clubAch-6').text()) + (+$('#clubAch-6').text());
        $('#clubAchAppRatio').text(Math.round(aa*100/ama) + '%');
    }
    var aman = ama+(+$('#clubNs-6').text());
    if (aman != 0) {
        $('#clubSalesRatio').text(Math.round(ama*100/aman) + '%');
    }
}

});
