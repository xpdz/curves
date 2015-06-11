$(document).ready(function() {
  $.getScript('js/common.js', function() {
    // init user ID / club ID
    $.get("/rest/whoami", function(userId) {
        $('#userId').html('<i class="fa fa-user"></i> '+userId+' <span class="caret"></span>');
        var clubId = userId;
        if ($.QueryString.clubId && $.QueryString.clubId != clubId) {
          clubId = $.QueryString.clubId;
          $('ul[data-curves="club"]').hide();
          $('ul[data-curves="mgmt"]').show();
        }

        var today = new Date();
        var thisYear = today.getFullYear();
        var thisMonth = today.getMonth();
        var currentYear = thisYear;
        var currentMonth = thisMonth;

        // init date picker
        $('#monthPicker').val(thisYear+"-"+(thisMonth+1));
        $('.input-group.date').datepicker({
            minViewMode: 1,
            autoclose: true,
            orientation: "top",
            format: "yyyy-mm",
            language: "zh-TW",
            todayHighlight: true
        });
        $('.input-group.date').datepicker().on('changeDate', function(ev) {
            currentYear = ev.date.getFullYear();
            currentMonth = ev.date.getMonth();
            getCA();
        });

        var tmpDate = new Date(today.getFullYear(), today.getMonth()-1, 1);
        var yEnd = tmpDate.getFullYear();
        var mEnd = tmpDate.getMonth();
        tmpDate.setMonth(mEnd - 5);
        var yStart = tmpDate.getFullYear();
        var mStart = tmpDate.getMonth();

        // init date picker
        $('#x1Date').val(yStart + '-' + (mStart+1));
        $('#x1Date').datepicker({
            minViewMode: 1,
            autoclose: true,
            orientation: "top",
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
            orientation: "top",
            format: "yyyy-mm",
            language: "zh-TW",
            todayHighlight: true
        });
        $('#x2Date').datepicker().on('changeDate', function(ev) {
            yEnd = ev.date.getFullYear();
            mEnd = ev.date.getMonth();
        });

        $('#btnExportThis').click(function() {
          window.location = "/rest/CA/export?yStart=" + currentYear + "&yEnd=" + currentYear + "&mStart=" + currentMonth + "&mEnd=" + currentMonth;
        });
        $('#btnExportMulti').click(function() {
          $('#dlgExport').modal({});
        });
        $('#btnExportMonths').click(function() {
          $('#dlgExport').modal('hide');
          window.location = "/rest/CA/export?yStart=" + yStart + "&yEnd=" + yEnd + "&mStart=" + mStart + "&mEnd=" + mEnd;
        });

        function clearNaN() {
            $('td').each(function() {
                var valueX = $(this).text();
                if (valueX === 'undefined' || valueX === 'NaN' || valueX === 'NaN%' || valueX === 'Infinity%') {
                    $(this).text('');
                }
            });
        }

        function clearZero() {
            $('[contenteditable]').each(function() {
                var valueX = $(this).text();
                if (valueX === '0' || valueX === '0.0') {
                    $(this).text('');
                }
            });
        }

        $.getJSON("/rest/clubs/"+clubId, function(club) {
          $('#clubName').text(club.name);
          $('#owner').text(club.owner);
          $('#mgr').text(club.mgr);
        }).fail(function() {
          showAlert("#alertMain", "alert-danger", "Cannot find club info. Please refresh and retry.");
        });

        getCA();
        function getCA() {
            $.getJSON("/rest/CA/lastMonth", {caYear: currentYear, caMonth: (currentMonth-1)}, function(data) {
                if (data['svcTm6']) {
                    $('#goalsLastTm').text(data['svcTm6']);
                    $('#goalsLastActive').text(data['svcActive6']);
                    $('#goalsLastShowRatio').text(data['cmShowRatio6']);
                    $('#goalsLastSalesRatio').text(data['salesRatio6']);
                    $('#thisPlan').val(data['nextPlan']);
                }
            }).fail(function() {
                showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
            });

            $.getJSON("/rest/CA", {caYear: currentYear, caMonth: currentMonth}, function(ca) {
                fillSheet(ca);
            }).fail(function() {
                showAlert("#alertMain", "alert-danger", "Cannot load data. Please refresh and retry.");
            });

            if (currentYear === thisYear && (currentMonth === thisMonth || (currentMonth === (thisMonth-1) && today.getDate() < 4))) {
                $('#btnSave').prop("disabled", false);
                $('[contenteditable="false"]').prop('contenteditable', true);
            } else {
                $('#btnSave').prop("disabled", true);
                $('[contenteditable="true"]').prop('contenteditable', false);
            }
        }

        $('.toggleV').click(function() {
            if (currentYear != thisYear || currentMonth != thisMonth) {
                return;
            }
            $(this).text(($(this).text().length == 0) ? 'v' : '');
        });

        $('[contenteditable="true"]').focusout(function() {
            runFormula();
        }).keydown(function(e) {
            var cellId = $(this).attr('id');
            if (cellId === 'staff1' || cellId === 'staff2' || cellId === 'staff3' || cellId === 'staff4' || cellId === 'staff5' || cellId === 'staff6') {
                return;
            }
            // keyCode: 8-BackSpace,9-Tab,13-Enter,46-Delete,109-KP-subtract,110-KP_Decimal,189-minus,190-period colon,35-40:Home-End-ArrowKey,48-57:0-9,96-105:KP-0-9
            if (e.which == 8 || e.which == 9 || e.which == 13 || e.which == 46 || e.which == 109 || e.which == 110 || e.which == 189 || e.which == 190
                || (e.which >= 35 && e.which <= 40) || (e.which >= 48 && e.which <= 57) || (e.which >= 96 && e.which <= 105)) {
                switch (e.which) {
                    case 13: // enter
                    case 39: // right
                        $(this).closest('td').next().find('div').focus();
                        break;
                    case 37: // left
                        $(this).closest('td').prev().find('div').focus();
                        break;
                    case 38: // up
                        var thisId = $(this).attr('id').split('-');
                        $(this).closest('tr').prev().find('[id$='+thisId[1]+']').focus();
                        break;
                    case 40: // down
                        var thisId = $(this).attr('id').split('-');
                        $(this).closest('tr').next().find('[id$='+thisId[1]+']').focus();
                        break;
                    default: return; // exit this handler for other keys
                }
            }
            e.preventDefault(); // prevent the default action (scroll / move caret)
        });

        function fillSheet(ca) {
            $('#goalsTm').text(ca.goalsTm);
            $('#goalsExitsRatio').text((ca.goalsExitsRatio*100).toFixed(1)+'%');
            $('#goalsNewSales').text(ca.goalsNewSales);
            $('#salesTotalX').text(ca.goalsNewSales);
            $('#goalsAppoints').text(ca.goalsAppoints);
            $('#apoTotalX').text(ca.goalsAppoints);

            $('#svcTm-1').text(ca.svcTm1);
            $('#svcTm-2').text(ca.svcTm2);
            $('#svcTm-3').text(ca.svcTm3);
            $('#svcTm-4').text(ca.svcTm4);
            $('#svcTm-5').text(ca.svcTm5);
            $('#svcTm-6').text(ca.svcTm6);
            $('#svcShiftOut-1').text(ca.svcShiftOut1);
            $('#svcShiftOut-2').text(ca.svcShiftOut2);
            $('#svcShiftOut-3').text(ca.svcShiftOut3);
            $('#svcShiftOut-4').text(ca.svcShiftOut4);
            $('#svcShiftOut-5').text(ca.svcShiftOut5);
            $('#svcShiftOut-6').text(ca.svcShiftOut6);
            $('#svcShiftIn-1').text(ca.svcShiftIn1);
            $('#svcShiftIn-2').text(ca.svcShiftIn2);
            $('#svcShiftIn-3').text(ca.svcShiftIn3);
            $('#svcShiftIn-4').text(ca.svcShiftIn4);
            $('#svcShiftIn-5').text(ca.svcShiftIn5);
            $('#svcShiftIn-6').text(ca.svcShiftIn6);
            $('#svcHold-1').text(ca.svcHold1);
            $('#svcHold-2').text(ca.svcHold2);
            $('#svcHold-3').text(ca.svcHold3);
            $('#svcHold-4').text(ca.svcHold4);
            $('#svcHold-5').text(ca.svcHold5);
            $('#svcHold-6').text(ca.svcHold6);
            $('#svcActive-1').text(ca.svcActive1);
            $('#svcActive-2').text(ca.svcActive2);
            $('#svcActive-3').text(ca.svcActive3);
            $('#svcActive-4').text(ca.svcActive4);
            $('#svcActive-5').text(ca.svcActive5);
            $('#svcActive-6').text(ca.svcActive6);
            if (ca.svcHoldRatio6 < 0.12) {
                $('#svcHoldRatio-0').css('color', 'red');
            }
            $('#svcHoldRatio-1').text((ca.svcHoldRatio1*100).toFixed(1)+'%');
            $('#svcHoldRatio-2').text((ca.svcHoldRatio2*100).toFixed(1)+'%');
            $('#svcHoldRatio-3').text((ca.svcHoldRatio3*100).toFixed(1)+'%');
            $('#svcHoldRatio-4').text((ca.svcHoldRatio4*100).toFixed(1)+'%');
            $('#svcHoldRatio-5').text((ca.svcHoldRatio5*100).toFixed(1)+'%');
            $('#svcHoldRatio-6').text((ca.svcHoldRatio6*100).toFixed(1)+'%');
            if (ca.svcTotalWo6 > (8 * ca.svcActive6)) {
                $('#svcTotalWo-0').css('color', 'red');
            }
            $('#svcTotalWo-1').text(ca.svcTotalWo1);
            $('#svcTotalWo-2').text(ca.svcTotalWo2);
            $('#svcTotalWo-3').text(ca.svcTotalWo3);
            $('#svcTotalWo-4').text(ca.svcTotalWo4);
            $('#svcTotalWo-5').text(ca.svcTotalWo5);
            $('#svcTotalWo-6').text(ca.svcTotalWo6);
            if (ca.svcAvgWo6 > 2.0) {
                $('#svcAvgWo-0').css('color', 'red');
            }
            $('#svcAvgWo-1').text(ca.svcAvgWo1.toFixed(1));
            $('#svcAvgWo-2').text(ca.svcAvgWo2.toFixed(1));
            $('#svcAvgWo-3').text(ca.svcAvgWo3.toFixed(1));
            $('#svcAvgWo-4').text(ca.svcAvgWo4.toFixed(1));
            $('#svcAvgWo-5').text(ca.svcAvgWo5.toFixed(1));
            $('#svcAvgWo-6').text(ca.svcAvgWo6.toFixed(1));
            $('#svcMaxWo-1').text(ca.svcMaxWo1);
            $('#svcMaxWo-2').text(ca.svcMaxWo2);
            $('#svcMaxWo-3').text(ca.svcMaxWo3);
            $('#svcMaxWo-4').text(ca.svcMaxWo4);
            $('#svcMaxWo-5').text(ca.svcMaxWo5);
            $('#svcMaxWo-6').text((ca.svcMaxWo6*100).toFixed(0)+'%');
            $('#svcExits-1').text(ca.svcExits1);
            $('#svcExits-2').text(ca.svcExits2);
            $('#svcExits-3').text(ca.svcExits3);
            $('#svcExits-4').text(ca.svcExits4);
            $('#svcExits-5').text(ca.svcExits5);
            $('#svcExits-6').text(ca.svcExits6);
            if (ca.svcExitsRatio6 < 0.04) {
                $('#svcExitsRatio-0').css('color', 'red');
            }
            $('#svcExitsRatio-1').text((ca.svcExitsRatio1*100).toFixed(1)+'%');
            $('#svcExitsRatio-2').text((ca.svcExitsRatio2*100).toFixed(1)+'%');
            $('#svcExitsRatio-3').text((ca.svcExitsRatio3*100).toFixed(1)+'%');
            $('#svcExitsRatio-4').text((ca.svcExitsRatio4*100).toFixed(1)+'%');
            $('#svcExitsRatio-5').text((ca.svcExitsRatio5*100).toFixed(1)+'%');
            $('#svcExitsRatio-6').text((ca.svcExitsRatio6*100).toFixed(1)+'%');
            $('#svcMeasure-1').text(ca.svcMeasure1);
            $('#svcMeasure-2').text(ca.svcMeasure2);
            $('#svcMeasure-3').text(ca.svcMeasure3);
            $('#svcMeasure-4').text(ca.svcMeasure4);
            $('#svcMeasure-5').text(ca.svcMeasure5);
            $('#svcMeasure-6').text(ca.svcMeasure6);
            if (ca.svcMeasureRatio6 > (ca.svcActive6*0.6)) {
                $('#svcMeasureRatio-0').css('color', 'red');
            }
            $('#svcMeasureRatio-1').text((ca.svcMeasureRatio1*100).toFixed(0)+'%');
            $('#svcMeasureRatio-2').text((ca.svcMeasureRatio2*100).toFixed(0)+'%');
            $('#svcMeasureRatio-3').text((ca.svcMeasureRatio3*100).toFixed(0)+'%');
            $('#svcMeasureRatio-4').text((ca.svcMeasureRatio4*100).toFixed(0)+'%');
            $('#svcMeasureRatio-5').text((ca.svcMeasureRatio5*100).toFixed(0)+'%');
            $('#svcMeasureRatio-6').text((ca.svcMeasureRatio6*100).toFixed(0)+'%');
            if (ca.svc12_6 > 0.3) {
                $('#svc12-0').css('color', 'red');
            }
            $('#svc12-5').text(ca.svc12_5);
            $('#svc12-6').text((ca.svc12_6*100).toFixed(0)+'%');
            if (ca.svc8to11_6 > 0.25) {
                $('#svc8to11-0').css('color', 'red');
            }
            $('#svc8to11-5').text(ca.svc8to11_5);
            $('#svc8to11-6').text((ca.svc8to11_6*100).toFixed(0)+'%');
            $('#svc4to7-5').text(ca.svc4to7_5);
            $('#svc4to7-6').text((ca.svc4to7_6*100).toFixed(0)+'%');
            $('#svc1to3-5').text(ca.svc1to3_5);
            $('#svc1to3-6').text((ca.svc1to3_6*100).toFixed(0)+'%');
            $('#svc0-5').text(ca.svc0_5);
            $('#svc0-6').text((ca.svc0_6*100).toFixed(0)+'%');
            $('#svc3More-1').text(ca.svc3More1);
            $('#svc3More-2').text(ca.svc3More2);
            $('#svc3More-3').text(ca.svc3More3);
            $('#svc3More-4').text(ca.svc3More4);
            $('#svc3More-5').text(ca.svc3More5);
            $('#svcInactive-1').text(ca.svcInactive1);
            $('#svcInactive-2').text(ca.svcInactive2);
            $('#svcInactive-3').text(ca.svcInactive3);
            $('#svcInactive-4').text(ca.svcInactive4);
            $('#svcInactive-5').text(ca.svcInactive5);
            $('#svcFwoReview-1').text(ca.svcFwoReview1);
            $('#svcFwoReview-2').text(ca.svcFwoReview2);
            $('#svcFwoReview-3').text(ca.svcFwoReview3);
            $('#svcFwoReview-4').text(ca.svcFwoReview4);
            $('#svcFwoReview-5').text(ca.svcFwoReview5);
            $('#svcInterview-1').text(ca.svc3More1);
            $('#svcInterview-2').text(ca.svcInterview2);
            $('#svcInterview-3').text(ca.svcInterview3);
            $('#svcInterview-4').text(ca.svcInterview4);
            $('#svcInterview-5').text(ca.svcInterview5);
            $('#svcThanks-1').text(ca.svcThanks1);
            $('#svcThanks-2').text(ca.svcThanks2);
            $('#svcThanks-3').text(ca.svcThanks3);
            $('#svcThanks-4').text(ca.svcThanks4);
            $('#svcThanks-5').text(ca.svcThanks5);
            $('#svc3C-1').text(ca.svc3C1);
            $('#svc3C-2').text(ca.svc3C2);
            $('#svc3C-3').text(ca.svc3C3);
            $('#svc3C-4').text(ca.svc3C4);
            $('#svc3C-5').text(ca.svc3C5);
            $('#svcReward-1').text(ca.svcReward1);
            $('#svcReward-2').text(ca.svcReward2);
            $('#svcReward-3').text(ca.svcReward3);
            $('#svcReward-4').text(ca.svcReward4);
            $('#svcReward-5').text(ca.svcReward5);
            $('#svcLoyal-1').text(ca.svcLoyal1);
            $('#svcLoyal-2').text(ca.svcLoyal2);
            $('#svcLoyal-3').text(ca.svcLoyal3);
            $('#svcLoyal-4').text(ca.svcLoyal4);
            $('#svcLoyal-5').text(ca.svcLoyal5);

            $('#cmPostFlyer-1').text(ca.cmPostFlyer1);
            $('#cmPostFlyer-2').text(ca.cmPostFlyer2);
            $('#cmPostFlyer-3').text(ca.cmPostFlyer3);
            $('#cmPostFlyer-4').text(ca.cmPostFlyer4);
            $('#cmPostFlyer-5').text(ca.cmPostFlyer5);
            $('#cmPostFlyer-6').text(ca.cmPostFlyer6);
            $('#cmHandFlyer-1').text(ca.cmHandFlyer1);
            $('#cmHandFlyer-2').text(ca.cmHandFlyer2);
            $('#cmHandFlyer-3').text(ca.cmHandFlyer3);
            $('#cmHandFlyer-4').text(ca.cmHandFlyer4);
            $('#cmHandFlyer-5').text(ca.cmHandFlyer5);
            $('#cmHandFlyer-6').text(ca.cmHandFlyer6);
            $('#cmHandFlyerHours-1').text(ca.cmHandFlyerHours1.toFixed(1));
            $('#cmHandFlyerHours-2').text(ca.cmHandFlyerHours2.toFixed(1));
            $('#cmHandFlyerHours-3').text(ca.cmHandFlyerHours3.toFixed(1));
            $('#cmHandFlyerHours-4').text(ca.cmHandFlyerHours4.toFixed(1));
            $('#cmHandFlyerHours-5').text(ca.cmHandFlyerHours5.toFixed(1));
            $('#cmHandFlyerHours-6').text(ca.cmHandFlyerHours6.toFixed(1));
            $('#cmOutGpHours-1').text(ca.cmOutGpHours1.toFixed(1));
            $('#cmOutGpHours-2').text(ca.cmOutGpHours2.toFixed(1));
            $('#cmOutGpHours-3').text(ca.cmOutGpHours3.toFixed(1));
            $('#cmOutGpHours-4').text(ca.cmOutGpHours4.toFixed(1));
            $('#cmOutGpHours-5').text(ca.cmOutGpHours5.toFixed(1));
            $('#cmOutGpHours-6').text(ca.cmOutGpHours6.toFixed(1));
            $('#cmOutGp-1').text(ca.cmOutGp1);
            $('#cmOutGp-2').text(ca.cmOutGp2);
            $('#cmOutGp-3').text(ca.cmOutGp3);
            $('#cmOutGp-4').text(ca.cmOutGp4);
            $('#cmOutGp-5').text(ca.cmOutGp5);
            $('#cmOutGp-6').text(ca.cmOutGp6);
            $('#cmCpBox-1').text(ca.cmCpBox1);
            $('#cmCpBox-2').text(ca.cmCpBox2);
            $('#cmCpBox-3').text(ca.cmCpBox3);
            $('#cmCpBox-4').text(ca.cmCpBox4);
            $('#cmCpBox-5').text(ca.cmCpBox5);
            $('#cmCpBox-6').text(ca.cmCpBox6);
            $('#cmOutGot-1').text(ca.cmOutGot1);
            $('#cmOutGot-2').text(ca.cmOutGot2);
            $('#cmOutGot-3').text(ca.cmOutGot3);
            $('#cmOutGot-4').text(ca.cmOutGot4);
            $('#cmOutGot-5').text(ca.cmOutGot5);
            $('#cmOutGot-6').text(ca.cmOutGot6);
            $('#cmInGot-1').text(ca.cmInGot1);
            $('#cmInGot-2').text(ca.cmInGot2);
            $('#cmInGot-3').text(ca.cmInGot3);
            $('#cmInGot-4').text(ca.cmInGot4);
            $('#cmInGot-5').text(ca.cmInGot5);
            $('#cmInGot-6').text(ca.cmInGot6);
            $('#cmBlogGot-1').text(ca.cmBlogGot1);
            $('#cmBlogGot-2').text(ca.cmBlogGot2);
            $('#cmBlogGot-3').text(ca.cmBlogGot3);
            $('#cmBlogGot-4').text(ca.cmBlogGot4);
            $('#cmBlogGot-5').text(ca.cmBlogGot5);
            $('#cmBlogGot-6').text(ca.cmBlogGot6);
            $('#cmBagGot-1').text(ca.cmBagGot1);
            $('#cmBagGot-2').text(ca.cmBagGot2);
            $('#cmBagGot-3').text(ca.cmBagGot3);
            $('#cmBagGot-4').text(ca.cmBagGot4);
            $('#cmBagGot-5').text(ca.cmBagGot5);
            $('#cmBagGot-6').text(ca.cmBagGot6);
            $('#cmTotalGot-1').text(ca.cmTotalGot1);
            $('#cmTotalGot-2').text(ca.cmTotalGot2);
            $('#cmTotalGot-3').text(ca.cmTotalGot3);
            $('#cmTotalGot-4').text(ca.cmTotalGot4);
            $('#cmTotalGot-5').text(ca.cmTotalGot5);
            $('#cmTotalGot-6').text(ca.cmTotalGot6);
            $('#cmCallIn-1').text(ca.cmCallIn1);
            $('#cmCallIn-2').text(ca.cmCallIn2);
            $('#cmCallIn-3').text(ca.cmCallIn3);
            $('#cmCallIn-4').text(ca.cmCallIn4);
            $('#cmCallIn-5').text(ca.cmCallIn5);
            $('#cmCallIn-6').text(ca.cmCallIn6);
            $('#cmOutGotCall-1').text(ca.cmOutGotCall1);
            $('#cmOutGotCall-2').text(ca.cmOutGotCall2);
            $('#cmOutGotCall-3').text(ca.cmOutGotCall3);
            $('#cmOutGotCall-4').text(ca.cmOutGotCall4);
            $('#cmOutGotCall-5').text(ca.cmOutGotCall5);
            $('#cmOutGotCall-6').text(ca.cmOutGotCall6);
            $('#cmInGotCall-1').text(ca.cmInGotCall1);
            $('#cmInGotCall-2').text(ca.cmInGotCall2);
            $('#cmInGotCall-3').text(ca.cmInGotCall3);
            $('#cmInGotCall-4').text(ca.cmInGotCall4);
            $('#cmInGotCall-5').text(ca.cmInGotCall5);
            $('#cmInGotCall-6').text(ca.cmInGotCall6);
            $('#cmBlogGotCall-1').text(ca.cmBlogGotCall1);
            $('#cmBlogGotCall-2').text(ca.cmBlogGotCall2);
            $('#cmBlogGotCall-3').text(ca.cmBlogGotCall3);
            $('#cmBlogGotCall-4').text(ca.cmBlogGotCall4);
            $('#cmBlogGotCall-5').text(ca.cmBlogGotCall5);
            $('#cmBlogGotCall-6').text(ca.cmBlogGotCall6);
            $('#cmBagGotCall-1').text(ca.cmBagGotCall1);
            $('#cmBagGotCall-2').text(ca.cmBagGotCall2);
            $('#cmBagGotCall-3').text(ca.cmBagGotCall3);
            $('#cmBagGotCall-4').text(ca.cmBagGotCall4);
            $('#cmBagGotCall-5').text(ca.cmBagGotCall5);
            $('#cmBagGotCall-6').text(ca.cmBagGotCall6);
            $('#cmOwnRefs-1').text(ca.cmOwnRefs1);
            $('#cmOwnRefs-2').text(ca.cmOwnRefs2);
            $('#cmOwnRefs-3').text(ca.cmOwnRefs3);
            $('#cmOwnRefs-4').text(ca.cmOwnRefs4);
            $('#cmOwnRefs-5').text(ca.cmOwnRefs5);
            $('#cmOwnRefs-6').text(ca.cmOwnRefs6);
            $('#cmOtherRefs-1').text(ca.cmOtherRefs1);
            $('#cmOtherRefs-2').text(ca.cmOtherRefs2);
            $('#cmOtherRefs-3').text(ca.cmOtherRefs3);
            $('#cmOtherRefs-4').text(ca.cmOtherRefs4);
            $('#cmOtherRefs-5').text(ca.cmOtherRefs5);
            $('#cmOtherRefs-6').text(ca.cmOtherRefs6);
            $('#cmNewspaper-1').text(ca.cmNewspaper1);
            $('#cmNewspaper-2').text(ca.cmNewspaper2);
            $('#cmNewspaper-3').text(ca.cmNewspaper3);
            $('#cmNewspaper-4').text(ca.cmNewspaper4);
            $('#cmNewspaper-5').text(ca.cmNewspaper5);
            $('#cmNewspaper-6').text(ca.cmNewspaper6);
            $('#cmTv-1').text(ca.cmTv1);
            $('#cmTv-2').text(ca.cmTv2);
            $('#cmTv-3').text(ca.cmTv3);
            $('#cmTv-4').text(ca.cmTv4);
            $('#cmTv-5').text(ca.cmTv5);
            $('#cmTv-6').text(ca.cmTv6);
            $('#cmInternet-1').text(ca.cmInternet1);
            $('#cmInternet-2').text(ca.cmInternet2);
            $('#cmInternet-3').text(ca.cmInternet3);
            $('#cmInternet-4').text(ca.cmInternet4);
            $('#cmInternet-5').text(ca.cmInternet5);
            $('#cmInternet-6').text(ca.cmInternet6);
            $('#cmSign-1').text(ca.cmSign1);
            $('#cmSign-2').text(ca.cmSign2);
            $('#cmSign-3').text(ca.cmSign3);
            $('#cmSign-4').text(ca.cmSign4);
            $('#cmSign-5').text(ca.cmSign5);
            $('#cmSign-6').text(ca.cmSign6);
            $('#cmMate-1').text(ca.cmMate1);
            $('#cmMate-2').text(ca.cmMate2);
            $('#cmMate-3').text(ca.cmMate3);
            $('#cmMate-4').text(ca.cmMate4);
            $('#cmMate-5').text(ca.cmMate5);
            $('#cmMate-6').text(ca.cmMate6);
            $('#cmOthers-1').text(ca.cmOthers1);
            $('#cmOthers-2').text(ca.cmOthers2);
            $('#cmOthers-3').text(ca.cmOthers3);
            $('#cmOthers-4').text(ca.cmOthers4);
            $('#cmOthers-5').text(ca.cmOthers5);
            $('#cmOthers-6').text(ca.cmOthers6);
            $('#cmMailAgpIn-1').text(ca.cmMailAgpIn1);
            $('#cmMailAgpIn-2').text(ca.cmMailAgpIn2);
            $('#cmMailAgpIn-3').text(ca.cmMailAgpIn3);
            $('#cmMailAgpIn-4').text(ca.cmMailAgpIn4_);
            $('#cmMailAgpIn-5').text(ca.cmMailAgpIn5);
            $('#cmMailAgpIn-6').text(ca.cmMailAgpIn6);
            $('#cmPostFlyerAgpIn-1').text(ca.cmPostFlyerAgpIn1);
            $('#cmPostFlyerAgpIn-2').text(ca.cmPostFlyerAgpIn2);
            $('#cmPostFlyerAgpIn-3').text(ca.cmPostFlyerAgpIn3);
            $('#cmPostFlyerAgpIn-4').text(ca.cmPostFlyerAgpIn4);
            $('#cmPostFlyerAgpIn-5').text(ca.cmPostFlyerAgpIn5);
            $('#cmPostFlyerAgpIn-6').text(ca.cmPostFlyerAgpIn6);
            $('#cmHandFlyerAgpIn-1').text(ca.cmHandFlyerAgpIn1);
            $('#cmHandFlyerAgpIn-2').text(ca.cmHandFlyerAgpIn2);
            $('#cmHandFlyerAgpIn-3').text(ca.cmHandFlyerAgpIn3);
            $('#cmHandFlyerAgpIn-4').text(ca.cmHandFlyerAgpIn4);
            $('#cmHandFlyerAgpIn-5').text(ca.cmHandFlyerAgpIn5);
            $('#cmHandFlyerAgpIn-6').text(ca.cmHandFlyerAgpIn6);
            $('#cmCpAgpIn-1').text(ca.cmCpAgpIn1);
            $('#cmCpAgpIn-2').text(ca.cmCpAgpIn2);
            $('#cmCpAgpIn-3').text(ca.cmCpAgpIn3);
            $('#cmCpAgpIn-4').text(ca.cmCpAgpIn4);
            $('#cmCpAgpIn-5').text(ca.cmCpAgpIn5);
            $('#cmCpAgpIn-6').text(ca.cmCpAgpIn6);
            $('#cmOutAgpOut-1').text(ca.cmOutAgpOut1);
            $('#cmOutAgpOut-2').text(ca.cmOutAgpOut2);
            $('#cmOutAgpOut-3').text(ca.cmOutAgpOut3);
            $('#cmOutAgpOut-4').text(ca.cmOutAgpOut4);
            $('#cmOutAgpOut-5').text(ca.cmOutAgpOut5);
            $('#cmOutAgpOut-6').text(ca.cmOutAgpOut6);
            $('#cmInAgpOut-1').text(ca.cmInAgpOut1);
            $('#cmInAgpOut-2').text(ca.cmInAgpOut2);
            $('#cmInAgpOut-3').text(ca.cmInAgpOut3);
            $('#cmInAgpOut-4').text(ca.cmInAgpOut4);
            $('#cmInAgpOut-5').text(ca.cmInAgpOut5);
            $('#cmInAgpOut-6').text(ca.cmInAgpOut6);
            $('#cmBlogAgpOut-1').text(ca.cmBlogAgpOut1);
            $('#cmBlogAgpOut-2').text(ca.cmBlogAgpOut2);
            $('#cmBlogAgpOut-3').text(ca.cmBlogAgpOut3);
            $('#cmBlogAgpOut-4').text(ca.cmBlogAgpOut4);
            $('#cmBlogAgpOut-5').text(ca.cmBlogAgpOut5);
            $('#cmBlogAgpOut-6').text(ca.cmBlogAgpOut6);
            $('#cmBagAgpOut-1').text(ca.cmBagAgpOut1);
            $('#cmBagAgpOut-2').text(ca.cmBagAgpOut2);
            $('#cmBagAgpOut-3').text(ca.cmBagAgpOut3);
            $('#cmBagAgpOut-4').text(ca.cmBagAgpOut4);
            $('#cmBagAgpOut-5').text(ca.cmBagAgpOut5);
            $('#cmBagAgpOut-6').text(ca.cmBagAgpOut6);
            $('#cmApoTotal-1').text(ca.cmApoTotal1);
            $('#cmApoTotal-2').text(ca.cmApoTotal2);
            $('#cmApoTotal-3').text(ca.cmApoTotal3);
            $('#cmApoTotal-4').text(ca.cmApoTotal4);
            $('#cmApoTotal-5').text(ca.cmApoTotal5);
            $('#cmApoTotal-6').text(ca.cmApoTotal6);
            if (ca.cmInApptRatio6 > 1) {
                $('#cmInApptRatio-0').css('color', 'red');
            }
            $('#cmInApptRatio-1').text((ca.cmInApptRatio1*100).toFixed(0)+'%');
            $('#cmInApptRatio-2').text((ca.cmInApptRatio2*100).toFixed(0)+'%');
            $('#cmInApptRatio-3').text((ca.cmInApptRatio3*100).toFixed(0)+'%');
            $('#cmInApptRatio-4').text((ca.cmInApptRatio4*100).toFixed(0)+'%');
            $('#cmInApptRatio-5').text((ca.cmInApptRatio5*100).toFixed(0)+'%');
            $('#cmInApptRatio-6').text((ca.cmInApptRatio6*100).toFixed(0)+'%');
            $('#cmOutApptRatio-1').text((ca.cmOutApptRatio1*100).toFixed(0)+'%');
            $('#cmOutApptRatio-2').text((ca.cmOutApptRatio2*100).toFixed(0)+'%');
            $('#cmOutApptRatio-3').text((ca.cmOutApptRatio3*100).toFixed(0)+'%');
            $('#cmOutApptRatio-4').text((ca.cmOutApptRatio4*100).toFixed(0)+'%');
            $('#cmOutApptRatio-5').text((ca.cmOutApptRatio5*100).toFixed(0)+'%');
            $('#cmOutApptRatio-6').text((ca.cmOutApptRatio6*100).toFixed(0)+'%');
            $('#cmPostPerApo-6').text(ca.cmPostPerApo6);
    //        $('#cmHandPerApo-6').text(ca.cmHandPerApo6);
            $('#cmHandHoursPerApo-6').text(ca.cmHandHoursPerApo6.toFixed(1));
            $('#cmOutGpHoursPerApo-6').text(ca.cmOutGpHoursPerApo6.toFixed(1));
    //        $('#cmOutGpPerApo-6').text(ca.cmOutGpPerApo6);
            if (ca.cmBrAgpRatio6 > 0.75) {
                $('#cmBrAgpRatio-0').css('color', 'red');
            }
            $('#cmBrAgpRatio-1').text((ca.cmBrAgpRatio1*100).toFixed(0)+'%');
            $('#cmBrAgpRatio-2').text((ca.cmBrAgpRatio2*100).toFixed(0)+'%');
            $('#cmBrAgpRatio-3').text((ca.cmBrAgpRatio3*100).toFixed(0)+'%');
            $('#cmBrAgpRatio-4').text((ca.cmBrAgpRatio4*100).toFixed(0)+'%');
            $('#cmBrAgpRatio-5').text((ca.cmBrAgpRatio5*100).toFixed(0)+'%');
            $('#cmBrAgpRatio-6').text((ca.cmBrAgpRatio6*100).toFixed(0)+'%');
            $('#cmFaSum-1').text(ca.cmFaSum1);
            $('#cmFaSum-2').text(ca.cmFaSum2);
            $('#cmFaSum-3').text(ca.cmFaSum3);
            $('#cmFaSum-4').text(ca.cmFaSum4);
            $('#cmFaSum-5').text(ca.cmFaSum5);
            $('#cmFaSum-6').text(ca.cmFaSum6);
            if (ca.cmShowRatio6 > 0.8) {
                $('#cmShowRatio-0').css('color', 'red');
            }
            $('#cmShowRatio-1').text((ca.cmShowRatio1*100).toFixed(0)+'%');
            $('#cmShowRatio-2').text((ca.cmShowRatio2*100).toFixed(0)+'%');
            $('#cmShowRatio-3').text((ca.cmShowRatio3*100).toFixed(0)+'%');
            $('#cmShowRatio-4').text((ca.cmShowRatio4*100).toFixed(0)+'%');
            $('#cmShowRatio-5').text((ca.cmShowRatio5*100).toFixed(0)+'%');
            $('#cmShowRatio-6').text((ca.cmShowRatio6*100).toFixed(0)+'%');
            $('#cmTraining-1').text(ca.cmTraining1);
            $('#cmTraining-2').text(ca.cmTraining2);
            $('#cmTraining-3').text(ca.cmTraining3);
            $('#cmTraining-4').text(ca.cmTraining4);
            $('#cmTraining-5').text(ca.cmTraining5);
            $('#cmGot3-1').text(ca.cmGot3_1);
            $('#cmGot3-2').text(ca.cmGot3_2);
            $('#cmGot3-3').text(ca.cmGot3_3);
            $('#cmGot3-4').text(ca.cmGot3_4);
            $('#cmGot3-5').text(ca.cmGot3_5);
            $('#cmInvitation-1').text(ca.cmInvitation1);
            $('#cmInvitation-2').text(ca.cmInvitation2);
            $('#cmInvitation-3').text(ca.cmInvitation3);
            $('#cmInvitation-4').text(ca.cmInvitation4);
            $('#cmInvitation-5').text(ca.cmInvitation5);

            $('#salesAch-1').text(ca.salesAch1);
            $('#salesAch-2').text(ca.salesAch2);
            $('#salesAch-3').text(ca.salesAch3);
            $('#salesAch-4').text(ca.salesAch4);
            $('#salesAch-5').text(ca.salesAch5);
            $('#salesAch-6').text(ca.salesAch6);
            $('#salesMonthly-1').text(ca.salesMonthly1);
            $('#salesMonthly-2').text(ca.salesMonthly2);
            $('#salesMonthly-3').text(ca.salesMonthly3);
            $('#salesMonthly-4').text(ca.salesMonthly4);
            $('#salesMonthly-5').text(ca.salesMonthly5);
            $('#salesMonthly-6').text(ca.salesMonthly6);
            $('#salesAllPrepay-1').text(ca.salesAllPrepay1);
            $('#salesAllPrepay-2').text(ca.salesAllPrepay2);
            $('#salesAllPrepay-3').text(ca.salesAllPrepay3);
            $('#salesAllPrepay-4').text(ca.salesAllPrepay4);
            $('#salesAllPrepay-5').text(ca.salesAllPrepay5);
            $('#salesAllPrepay-6').text(ca.salesAllPrepay6);
            $('#salesTotal-1').text(ca.salesTotal1);
            $('#salesTotal-2').text(ca.salesTotal2);
            $('#salesTotal-3').text(ca.salesTotal3);
            $('#salesTotal-4').text(ca.salesTotal4);
            $('#salesTotal-5').text(ca.salesTotal5);
            $('#salesTotal-6').text(ca.salesTotal6);
            if (ca.salesRatio6 > 0.85) {
                $('#salesRatio-0').css('color', 'red');
            }
            $('#salesRatio-1').text((ca.salesRatio1*100).toFixed(0)+'%');
            $('#salesRatio-2').text((ca.salesRatio2*100).toFixed(0)+'%');
            $('#salesRatio-3').text((ca.salesRatio3*100).toFixed(0)+'%');
            $('#salesRatio-4').text((ca.salesRatio4*100).toFixed(0)+'%');
            $('#salesRatio-5').text((ca.salesRatio5*100).toFixed(0)+'%');
            $('#salesRatio-6').text((ca.salesRatio6*100).toFixed(0)+'%');
            if (ca.salesAchAppRatio6 > 0.9) {
                $('#salesAchAppRatio-0').css('color', 'red');
            }
            $('#salesAchAppRatio-1').text((ca.salesAchAppRatio1*100).toFixed(0)+'%');
            $('#salesAchAppRatio-2').text((ca.salesAchAppRatio2*100).toFixed(0)+'%');
            $('#salesAchAppRatio-3').text((ca.salesAchAppRatio3*100).toFixed(0)+'%');
            $('#salesAchAppRatio-4').text((ca.salesAchAppRatio4*100).toFixed(0)+'%');
            $('#salesAchAppRatio-5').text((ca.salesAchAppRatio5*100).toFixed(0)+'%');
            $('#salesAchAppRatio-6').text((ca.salesAchAppRatio6*100).toFixed(0)+'%');
            $('#salesFaReview-1').text(ca.salesFaReview1);
            $('#salesFaReview-2').text(ca.salesFaReview2);
            $('#salesFaReview-3').text(ca.salesFaReview3);
            $('#salesFaReview-4').text(ca.salesFaReview4);
            $('#salesFaReview-5').text(ca.salesFaReview5);
            $('#salesPriceReview-1').text(ca.salesPriceReview1);
            $('#salesPriceReview-2').text(ca.salesPriceReview2);
            $('#salesPriceReview-3').text(ca.salesPriceReview3);
            $('#salesPriceReview-4').text(ca.salesPriceReview4);
            $('#salesPriceReview-5').text(ca.salesPriceReview5);
            $('#salesAck-1').text(ca.salesAck1);
            $('#salesAck-2').text(ca.salesAck2);
            $('#salesAck-3').text(ca.salesAck3);
            $('#salesAck-4').text(ca.salesAck4);
            $('#salesAck-5').text(ca.salesAck5);
            $('#salesTarget-1').text(ca.salesTarget1);
            $('#salesTarget-2').text(ca.salesTarget2);
            $('#salesTarget-3').text(ca.salesTarget3);
            $('#salesTarget-4').text(ca.salesTarget4);
            $('#salesTarget-5').text(ca.salesTarget5);
            $('#salesMotivation-1').text(ca.salesMotivation1);
            $('#salesMotivation-2').text(ca.salesMotivation2);
            $('#salesMotivation-3').text(ca.salesMotivation3);
            $('#salesMotivation-4').text(ca.salesMotivation4);
            $('#salesMotivation-5').text(ca.salesMotivation5);
            $('#salesObstacle-1').text(ca.salesObstacle1);
            $('#salesObstacle-2').text(ca.salesObstacle2);
            $('#salesObstacle-3').text(ca.salesObstacle3);
            $('#salesObstacle-4').text(ca.salesObstacle4);
            $('#salesObstacle-5').text(ca.salesObstacle5);

            $('#mgmtMeeting-1').text(ca.mgmtMeeting1);
            $('#mgmtMeeting-2').text(ca.mgmtMeeting2);
            $('#mgmtMeeting-3').text(ca.mgmtMeeting3);
            $('#mgmtMeeting-4').text(ca.mgmtMeeting4);
            $('#mgmtMeeting-5').text(ca.mgmtMeeting5);
            $('#mgmtCa-1').text(ca.mgmtCa1);
            $('#mgmtCa-2').text(ca.mgmtCa2);
            $('#mgmtCa-3').text(ca.mgmtCa3);
            $('#mgmtCa-4').text(ca.mgmtCa4);
            $('#mgmtCa-5').text(ca.mgmtCa5);
            $('#mgmtGp-1').text(ca.mgmtGp1);
            $('#mgmtGp-2').text(ca.mgmtGp2);
            $('#mgmtGp-3').text(ca.mgmtGp3);
            $('#mgmtGp-4').text(ca.mgmtGp4);
            $('#mgmtGp-5').text(ca.mgmtGp5);
            $('#mgmtLearn-1').text(ca.mgmtLearn1);
            $('#mgmtLearn-2').text(ca.mgmtLearn2);
            $('#mgmtLearn-3').text(ca.mgmtLearn3);
            $('#mgmtLearn-4').text(ca.mgmtLearn4);
            $('#mgmtLearn-5').text(ca.mgmtLearn5);
            $('#mgmtSheet-1').text(ca.mgmtSheet1);
            $('#mgmtSheet-2').text(ca.mgmtSheet2);
            $('#mgmtSheet-3').text(ca.mgmtSheet3);
            $('#mgmtSheet-4').text(ca.mgmtSheet4);
            $('#mgmtSheet-5').text(ca.mgmtSheet5);
            $('#mgmtPolicy-1').text(ca.mgmtPolicy1);
            $('#mgmtPolicy-2').text(ca.mgmtPolicy2);
            $('#mgmtPolicy-3').text(ca.mgmtPolicy3);
            $('#mgmtPolicy-4').text(ca.mgmtPolicy4);
            $('#mgmtPolicy-5').text(ca.mgmtPolicy5);
            $('#mgmtCompiantSales-1').text(ca.mgmtCompiantSales1);
            $('#mgmtCompiantSales-2').text(ca.mgmtCompiantSales2);
            $('#mgmtCompiantSales-3').text(ca.mgmtCompiantSales3);
            $('#mgmtCompiantSales-4').text(ca.mgmtCompiantSales4);
            $('#mgmtCompiantSales-5').text(ca.mgmtCompiantSales5);
            $('#mgmtCompiantMethod-1').text(ca.mgmtCompiantMethod1);
            $('#mgmtCompiantMethod-2').text(ca.mgmtCompiantMethod2);
            $('#mgmtCompiantMethod-3').text(ca.mgmtCompiantMethod3);
            $('#mgmtCompiantMethod-4').text(ca.mgmtCompiantMethod4);
            $('#mgmtCompiantMethod-5').text(ca.mgmtCompiantMethod5);
            $('#mgmtCompiantProduct-1').text(ca.mgmtCompiantProduct1);
            $('#mgmtCompiantProduct-2').text(ca.mgmtCompiantProduct2);
            $('#mgmtCompiantProduct-3').text(ca.mgmtCompiantProduct3);
            $('#mgmtCompiantProduct-4').text(ca.mgmtCompiantProduct4);
            $('#mgmtCompiantProduct-5').text(ca.mgmtCompiantProduct5);
            $('#mgmtCompiantAd-1').text(ca.mgmtCompiantAd1);
            $('#mgmtCompiantAd-2').text(ca.mgmtCompiantAd2);
            $('#mgmtCompiantAd-3').text(ca.mgmtCompiantAd3);
            $('#mgmtCompiantAd-4').text(ca.mgmtCompiantAd4);
            $('#mgmtCompiantAd-5').text(ca.mgmtCompiantAd5);
            $('#mgmtTraining-1').text(ca.mgmtCompiantTraining1);
            $('#mgmtTraining-2').text(ca.mgmtCompiantTraining2);
            $('#mgmtTraining-3').text(ca.mgmtCompiantTraining3);
            $('#mgmtTraining-4').text(ca.mgmtCompiantTraining4);
            $('#mgmtTraining-5').text(ca.mgmtCompiantTraining5);
            $('#mgmtReport-1').text(ca.mgmtCompiantReport1);
            $('#mgmtReport-2').text(ca.mgmtCompiantReport2);
            $('#mgmtReport-3').text(ca.mgmtCompiantReport3);
            $('#mgmtReport-4').text(ca.mgmtCompiantReport4);
            $('#mgmtReport-5').text(ca.mgmtCompiantReport5);
            $('#mgmtPlan-1').text(ca.mgmtPlan1);
            $('#mgmtPlan-2').text(ca.mgmtPlan2);
            $('#mgmtPlan-3').text(ca.mgmtPlan3);
            $('#mgmtPlan-4').text(ca.mgmtPlan4);
            $('#mgmtPlan-5').text(ca.mgmtPlan5);
            $('#mgmtMaintain-1').text(ca.mgmtMaintain1);
            $('#mgmtMaintain-2').text(ca.mgmtMaintain2);
            $('#mgmtMaintain-3').text(ca.mgmtMaintain3);
            $('#mgmtMaintain-4').text(ca.mgmtMaintain4);
            $('#mgmtMaintain-5').text(ca.mgmtMaintain5);
            $('#mgmtFace2Face-1').text(ca.mgmtFace2Face1);
            $('#mgmtFace2Face-2').text(ca.mgmtFace2Face2);
            $('#mgmtFace2Face-3').text(ca.mgmtFace2Face3);
            $('#mgmtFace2Face-4').text(ca.mgmtFace2Face4);
            $('#mgmtFace2Face-5').text(ca.mgmtFace2Face5);

            $('#clubSalesRatio').text((ca.clubSalesRatio*100).toFixed(0)+'%');
            $('#clubAchAppRatio').text((ca.clubAchAppRatio*100).toFixed(0)+'%');
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

            $('#staff1').text(ca.staff1Name);
            $('#staff1SalesRatio').text((ca.staff1SalesRatio*100).toFixed(0)+'%');
            $('#staff1AchAppRatio').text((ca.staff1AchAppRatio*100).toFixed(0)+'%');
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

            $('#staff2').text(ca.staff2Name);
            $('#staff2SalesRatio').text((ca.staff2SalesRatio*100).toFixed(0)+'%');
            $('#staff2AchAppRatio').text((ca.staff2AchAppRatio*100).toFixed(0)+'%');
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

            $('#staff3').text(ca.staff3Name);
            $('#staff3SalesRatio').text((ca.staff3SalesRatio*100).toFixed(0)+'%');
            $('#staff3AchAppRatio').text((staff3AchAppRatio*100).toFixed(0)+'%');
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

            $('#staff4').text(ca.staff4Name);
            $('#staff4SalesRatio').text((ca.staff4SalesRatio*100).toFixed(0)+'%');
            $('#staff4AchAppRatio').text((ca.staff4AchAppRatio*100).toFixed(0)+'%');
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

            $('#staff5').text(ca.staff5Name);
            $('#staff5SalesRatio').text((ca.staff5SalesRatio*100).toFixed(0)+'%');
            $('#staff5AchAppRatio').text((ca.staff5AchAppRatio*100).toFixed(0)+'%');
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

            $('#staff6').text(ca.staff6Name);
            $('#staff6SalesRatio').text((ca.staff6SalesRatio*100).toFixed(0)+'%');
            $('#staff6AchAppRatio').text((ca.staff6AchAppRatio*100).toFixed(0)+'%');
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

            $('#nextPlan').val(ca.nextPlan);

            clearZero();
        }

        // save CA
        $("#btnSave").click(function() {
            $(this).prop("disabled", true);

            runFormula();

            var ca = {};
            ca.clubId = +clubId;
            ca.caYear = thisYear;
            ca.caMonth = thisMonth;

            ca.goalsTm = +$('#goalsTm').text();
            ca.goalsLastTm = +$('#goalsLastTm').text();
            ca.goalsLastActive = +$('#goalsLastActive').text();
            ca.goalsLastShowRatio = parseFloat($('#goalsLastShowRatio').text())/100;
            ca.goalsLastSalesRatio = parseFloat($('#goalsLastSalesRatio').text())/100;
            ca.goalsExitsRatio = parseFloat($('#goalsExitsRatio').text())/100;
            ca.goalsNewSales = +$('#goalsNewSales').text();
            ca.goalsAppoints = +$('#goalsAppoints').text();

            ca.svcTm1 = +$('#svcTm-1').text();
            ca.svcTm2 = +$('#svcTm-2').text();
            ca.svcTm3 = +$('#svcTm-3').text();
            ca.svcTm4 = +$('#svcTm-4').text();
            ca.svcTm5 = +$('#svcTm-5').text();
            ca.svcTm6 = +$('#svcTm-6').text();
            ca.svcShiftOut1 = +$('#svcShiftOut-1').text();
            ca.svcShiftOut2 = +$('#svcShiftOut-2').text();
            ca.svcShiftOut3 = +$('#svcShiftOut-3').text();
            ca.svcShiftOut4 = +$('#svcShiftOut-4').text();
            ca.svcShiftOut5 = +$('#svcShiftOut-5').text();
            ca.svcShiftOut6 = +$('#svcShiftOut-6').text();
            ca.svcShiftIn1 = +$('#svcShiftIn-1').text();
            ca.svcShiftIn2 = +$('#svcShiftIn-2').text();
            ca.svcShiftIn3 = +$('#svcShiftIn-3').text();
            ca.svcShiftIn4 = +$('#svcShiftIn-4').text();
            ca.svcShiftIn5 = +$('#svcShiftIn-5').text();
            ca.svcShiftIn6 = +$('#svcShiftIn-6').text();
            ca.svcHold1 = +$('#svcHold-1').text();
            ca.svcHold2 = +$('#svcHold-2').text();
            ca.svcHold3 = +$('#svcHold-3').text();
            ca.svcHold4 = +$('#svcHold-4').text();
            ca.svcHold5 = +$('#svcHold-5').text();
            ca.svcHold6 = +$('#svcHold-6').text();
            ca.svcActive1 = +$('#svcActive-1').text();
            ca.svcActive2 = +$('#svcActive-2').text();
            ca.svcActive3 = +$('#svcActive-3').text();
            ca.svcActive4 = +$('#svcActive-4').text();
            ca.svcActive5 = +$('#svcActive-5').text();
            ca.svcActive6 = +$('#svcActive-6').text();
            ca.svcHoldRatio1 = parseFloat($('#svcHoldRatio-1').text())/100;
            ca.svcHoldRatio2 = parseFloat($('#svcHoldRatio-2').text())/100;
            ca.svcHoldRatio3 = parseFloat($('#svcHoldRatio-3').text())/100;
            ca.svcHoldRatio4 = parseFloat($('#svcHoldRatio-4').text())/100;
            ca.svcHoldRatio5 = parseFloat($('#svcHoldRatio-5').text())/100;
            ca.svcHoldRatio6 = parseFloat($('#svcHoldRatio-6').text())/100;
            ca.svcTotalWo1 = +$('#svcTotalWo-1').text();
            ca.svcTotalWo2 = +$('#svcTotalWo-2').text();
            ca.svcTotalWo3 = +$('#svcTotalWo-3').text();
            ca.svcTotalWo4 = +$('#svcTotalWo-4').text();
            ca.svcTotalWo5 = +$('#svcTotalWo-5').text();
            ca.svcTotalWo6 = +$('#svcTotalWo-6').text();
            ca.svcAvgWo1 = parseFloat($('#svcAvgWo-1').text());
            ca.svcAvgWo2 = parseFloat($('#svcAvgWo-2').text());
            ca.svcAvgWo3 = parseFloat($('#svcAvgWo-3').text());
            ca.svcAvgWo4 = parseFloat($('#svcAvgWo-4').text());
            ca.svcAvgWo5 = parseFloat($('#svcAvgWo-5').text());
            ca.svcAvgWo6 = parseFloat($('#svcAvgWo-6').text());
            ca.svcMaxWo1 = +$('#svcMaxWo-1').text();
            ca.svcMaxWo2 = +$('#svcMaxWo-2').text();
            ca.svcMaxWo3 = +$('#svcMaxWo-3').text();
            ca.svcMaxWo4 = +$('#svcMaxWo-4').text();
            ca.svcMaxWo5 = +$('#svcMaxWo-5').text();
            ca.svcMaxWo6 = parseFloat($('#svcMaxWo-6').text());
            ca.svcExits1 = +$('#svcExits-1').text();
            ca.svcExits2 = +$('#svcExits-2').text();
            ca.svcExits3 = +$('#svcExits-3').text();
            ca.svcExits4 = +$('#svcExits-4').text();
            ca.svcExits5 = +$('#svcExits-5').text();
            ca.svcExits6 = +$('#svcExits-6').text();
            ca.svcExitsRatio1 = parseFloat($('#svcExitsRatio-1').text())/100;
            ca.svcExitsRatio2 = parseFloat($('#svcExitsRatio-2').text())/100;
            ca.svcExitsRatio3 = parseFloat($('#svcExitsRatio-3').text())/100;
            ca.svcExitsRatio4 = parseFloat($('#svcExitsRatio-4').text())/100;
            ca.svcExitsRatio5 = parseFloat($('#svcExitsRatio-5').text())/100;
            ca.svcExitsRatio6 = parseFloat($('#svcExitsRatio-6').text())/100;
            ca.svcMeasure1 = +$('#svcMeasure-1').text();
            ca.svcMeasure2 = +$('#svcMeasure-2').text();
            ca.svcMeasure3 = +$('#svcMeasure-3').text();
            ca.svcMeasure4 = +$('#svcMeasure-4').text();
            ca.svcMeasure5 = +$('#svcMeasure-5').text();
            ca.svcMeasure6 = +$('#svcMeasure-6').text();
            ca.svcMeasureRatio1 = parseFloat($('#svcMeasureRatio-1').text())/100;
            ca.svcMeasureRatio2 = parseFloat($('#svcMeasureRatio-2').text())/100;
            ca.svcMeasureRatio3 = parseFloat($('#svcMeasureRatio-3').text())/100;
            ca.svcMeasureRatio4 = parseFloat($('#svcMeasureRatio-4').text())/100;
            ca.svcMeasureRatio5 = parseFloat($('#svcMeasureRatio-5').text())/100;
            ca.svcMeasureRatio6 = parseFloat($('#svcMeasureRatio-6').text())/100;
            ca.svc12_5 = +$('#svc12-5').text();
            ca.svc12_6 = parseFloat($('#svc12-6').text())/100;
            ca.svc8to11_5 = +$('#svc8to11-5').text();
            ca.svc8to11_6 = parseFloat($('#svc8to11-6').text())/100;
            ca.svc4to7_5 = +$('#svc4to7-5').text();
            ca.svc4to7_6 = parseFloat($('#svc4to7-6').text())/100;
            ca.svc1to3_5 = +$('#svc1to3-5').text();
            ca.svc1to3_6 = parseFloat($('#svc1to3-6').text())/100;
            ca.svc0_5 = +$('#svc0-5').text();
            ca.svc0_6 = parseFloat($('#svc0-6').text())/100;
            ca.svc3More1 = $('#svc3More-1').text();
            ca.svc3More2 = $('#svc3More-2').text();
            ca.svc3More3 = $('#svc3More-3').text();
            ca.svc3More4 = $('#svc3More-4').text();
            ca.svc3More5 = $('#svc3More-5').text();
            ca.svcInactive1 = $('#svcInactive-1').text();
            ca.svcInactive2 = $('#svcInactive-2').text();
            ca.svcInactive3 = $('#svcInactive-3').text();
            ca.svcInactive4 = $('#svcInactive-4').text();
            ca.svcInactive5 = $('#svcInactive-5').text();
            ca.svcFwoReview1 = $('#svcFwoReview-1').text();
            ca.svcFwoReview2 = $('#svcFwoReview-2').text();
            ca.svcFwoReview3 = $('#svcFwoReview-3').text();
            ca.svcFwoReview4 = $('#svcFwoReview-4').text();
            ca.svcFwoReview5 = $('#svcFwoReview-5').text();
            ca.svcInterview1 = $('#svcInterview-1').text();
            ca.svcInterview2 = $('#svcInterview-2').text();
            ca.svcInterview3 = $('#svcInterview-3').text();
            ca.svcInterview4 = $('#svcInterview-4').text();
            ca.svcInterview5 = $('#svcInterview-5').text();
            ca.svcThanks1 = $('#svcThanks-1').text();
            ca.svcThanks2 = $('#svcThanks-2').text();
            ca.svcThanks3 = $('#svcThanks-3').text();
            ca.svcThanks4 = $('#svcThanks-4').text();
            ca.svcThanks5 = $('#svcThanks-5').text();
            ca.svc3C1 = $('#svc3C-1').text();
            ca.svc3C2 = $('#svc3C-2').text();
            ca.svc3C3 = $('#svc3C-3').text();
            ca.svc3C4 = $('#svc3C-4').text();
            ca.svc3C5 = $('#svc3C-5').text();
            ca.svcReward1 = $('#svcReward-1').text();
            ca.svcReward2 = $('#svcReward-2').text();
            ca.svcReward3 = $('#svcReward-3').text();
            ca.svcReward4 = $('#svcReward-4').text();
            ca.svcReward5 = $('#svcReward-5').text();
            ca.svcLoyal1 = $('#svcLoyal-1').text();
            ca.svcLoyal2 = $('#svcLoyal-2').text();
            ca.svcLoyal3 = $('#svcLoyal-3').text();
            ca.svcLoyal4 = $('#svcLoyal-4').text();
            ca.svcLoyal5 = $('#svcLoyal-5').text();

            ca.cmPostFlyer1 = +$('#cmPostFlyer-1').text();
            ca.cmPostFlyer2 = +$('#cmPostFlyer-2').text();
            ca.cmPostFlyer3 = +$('#cmPostFlyer-3').text();
            ca.cmPostFlyer4 = +$('#cmPostFlyer-4').text();
            ca.cmPostFlyer5 = +$('#cmPostFlyer-5').text();
            ca.cmPostFlyer6 = +$('#cmPostFlyer-6').text();
            ca.cmHandFlyer1 = +$('#cmHandFlyer-1').text();
            ca.cmHandFlyer2 = +$('#cmHandFlyer-2').text();
            ca.cmHandFlyer3 = +$('#cmHandFlyer-3').text();
            ca.cmHandFlyer4 = +$('#cmHandFlyer-4').text();
            ca.cmHandFlyer5 = +$('#cmHandFlyer-5').text();
            ca.cmHandFlyer6 = +$('#cmHandFlyer-6').text();
            ca.cmHandFlyerHours1 = +$('#cmHandFlyerHours-1').text();
            ca.cmHandFlyerHours2 = +$('#cmHandFlyerHours-2').text();
            ca.cmHandFlyerHours3 = +$('#cmHandFlyerHours-3').text();
            ca.cmHandFlyerHours4 = +$('#cmHandFlyerHours-4').text();
            ca.cmHandFlyerHours5 = +$('#cmHandFlyerHours-5').text();
            ca.cmHandFlyerHours6 = +$('#cmHandFlyerHours-6').text();
            ca.cmOutGpHours1 = +$('#cmOutGpHours-1').text();
            ca.cmOutGpHours2 = +$('#cmOutGpHours-2').text();
            ca.cmOutGpHours3 = +$('#cmOutGpHours-3').text();
            ca.cmOutGpHours4 = +$('#cmOutGpHours-4').text();
            ca.cmOutGpHours5 = +$('#cmOutGpHours-5').text();
            ca.cmOutGpHours6 = +$('#cmOutGpHours-6').text();
            ca.cmOutGp1 = +$('#cmOutGp-1').text();
            ca.cmOutGp2 = +$('#cmOutGp-2').text();
            ca.cmOutGp3 = +$('#cmOutGp-3').text();
            ca.cmOutGp4 = +$('#cmOutGp-4').text();
            ca.cmOutGp5 = +$('#cmOutGp-5').text();
            ca.cmOutGp6 = +$('#cmOutGp-6').text();
            ca.cmCpBox1 = +$('#cmCpBox-1').text();
            ca.cmCpBox2 = +$('#cmCpBox-2').text();
            ca.cmCpBox3 = +$('#cmCpBox-3').text();
            ca.cmCpBox4 = +$('#cmCpBox-4').text();
            ca.cmCpBox5 = +$('#cmCpBox-5').text();
            ca.cmCpBox6 = +$('#cmCpBox-6').text();
            ca.cmOutGot1 = +$('#cmOutGot-1').text();
            ca.cmOutGot2 = +$('#cmOutGot-2').text();
            ca.cmOutGot3 = +$('#cmOutGot-3').text();
            ca.cmOutGot4 = +$('#cmOutGot-4').text();
            ca.cmOutGot5 = +$('#cmOutGot-5').text();
            ca.cmOutGot6 = +$('#cmOutGot-6').text();
            ca.cmInGot1 = +$('#cmInGot-1').text();
            ca.cmInGot2 = +$('#cmInGot-2').text();
            ca.cmInGot3 = +$('#cmInGot-3').text();
            ca.cmInGot4 = +$('#cmInGot-4').text();
            ca.cmInGot5 = +$('#cmInGot-5').text();
            ca.cmInGot6 = +$('#cmInGot-6').text();
            ca.cmBlogGot1 = +$('#cmBlogGot-1').text();
            ca.cmBlogGot2 = +$('#cmBlogGot-2').text();
            ca.cmBlogGot3 = +$('#cmBlogGot-3').text();
            ca.cmBlogGot4 = +$('#cmBlogGot-4').text();
            ca.cmBlogGot5 = +$('#cmBlogGot-5').text();
            ca.cmBlogGot6 = +$('#cmBlogGot-6').text();
            ca.cmBagGot1 = +$('#cmBagGot-1').text();
            ca.cmBagGot2 = +$('#cmBagGot-2').text();
            ca.cmBagGot3 = +$('#cmBagGot-3').text();
            ca.cmBagGot4 = +$('#cmBagGot-4').text();
            ca.cmBagGot5 = +$('#cmBagGot-5').text();
            ca.cmBagGot6 = +$('#cmBagGot-6').text();
            ca.cmTotalGot1 = +$('#cmTotalGot-1').text();
            ca.cmTotalGot2 = +$('#cmTotalGot-2').text();
            ca.cmTotalGot3 = +$('#cmTotalGot-3').text();
            ca.cmTotalGot4 = +$('#cmTotalGot-4').text();
            ca.cmTotalGot5 = +$('#cmTotalGot-5').text();
            ca.cmTotalGot6 = +$('#cmTotalGot-6').text();
            ca.cmCallIn1 = +$('#cmCallIn-1').text();
            ca.cmCallIn2 = +$('#cmCallIn-2').text();
            ca.cmCallIn3 = +$('#cmCallIn-3').text();
            ca.cmCallIn4 = +$('#cmCallIn-4').text();
            ca.cmCallIn5 = +$('#cmCallIn-5').text();
            ca.cmCallIn6 = +$('#cmCallIn-6').text();
            ca.cmOutGotCall1 = +$('#cmOutGotCall-1').text();
            ca.cmOutGotCall2 = +$('#cmOutGotCall-2').text();
            ca.cmOutGotCall3 = +$('#cmOutGotCall-3').text();
            ca.cmOutGotCall4 = +$('#cmOutGotCall-4').text();
            ca.cmOutGotCall5 = +$('#cmOutGotCall-5').text();
            ca.cmOutGotCall6 = +$('#cmOutGotCall-6').text();
            ca.cmInGotCall1 = +$('#cmInGotCall-1').text();
            ca.cmInGotCall2 = +$('#cmInGotCall-2').text();
            ca.cmInGotCall3 = +$('#cmInGotCall-3').text();
            ca.cmInGotCall4 = +$('#cmInGotCall-4').text();
            ca.cmInGotCall5 = +$('#cmInGotCall-5').text();
            ca.cmInGotCall6 = +$('#cmInGotCall-6').text();
            ca.cmBlogGotCall1 = +$('#cmBlogGotCall-1').text();
            ca.cmBlogGotCall2 = +$('#cmBlogGotCall-2').text();
            ca.cmBlogGotCall3 = +$('#cmBlogGotCall-3').text();
            ca.cmBlogGotCall4 = +$('#cmBlogGotCall-4').text();
            ca.cmBlogGotCall5 = +$('#cmBlogGotCall-5').text();
            ca.cmBlogGotCall6 = +$('#cmBlogGotCall-6').text();
            ca.cmBagGotCall1 = +$('#cmBagGotCall-1').text();
            ca.cmBagGotCall2 = +$('#cmBagGotCall-2').text();
            ca.cmBagGotCall3 = +$('#cmBagGotCall-3').text();
            ca.cmBagGotCall4 = +$('#cmBagGotCall-4').text();
            ca.cmBagGotCall5 = +$('#cmBagGotCall-5').text();
            ca.cmBagGotCall6 = +$('#cmBagGotCall-6').text();
            ca.cmOwnRefs1 = +$('#cmOwnRefs-1').text();
            ca.cmOwnRefs2 = +$('#cmOwnRefs-2').text();
            ca.cmOwnRefs3 = +$('#cmOwnRefs-3').text();
            ca.cmOwnRefs4 = +$('#cmOwnRefs-4').text();
            ca.cmOwnRefs5 = +$('#cmOwnRefs-5').text();
            ca.cmOwnRefs6 = +$('#cmOwnRefs-6').text();
            ca.cmOtherRefs1 = +$('#cmOtherRefs-1').text();
            ca.cmOtherRefs2 = +$('#cmOtherRefs-2').text();
            ca.cmOtherRefs3 = +$('#cmOtherRefs-3').text();
            ca.cmOtherRefs4 = +$('#cmOtherRefs-4').text();
            ca.cmOtherRefs5 = +$('#cmOtherRefs-5').text();
            ca.cmOtherRefs6 = +$('#cmOtherRefs-6').text();
            ca.cmNewspaper1 = +$('#cmNewspaper-1').text();
            ca.cmNewspaper2 = +$('#cmNewspaper-2').text();
            ca.cmNewspaper3 = +$('#cmNewspaper-3').text();
            ca.cmNewspaper4 = +$('#cmNewspaper-4').text();
            ca.cmNewspaper5 = +$('#cmNewspaper-5').text();
            ca.cmNewspaper6 = +$('#cmNewspaper-6').text();
            ca.cmTv1 = +$('#cmTv-1').text();
            ca.cmTv2 = +$('#cmTv-2').text();
            ca.cmTv3 = +$('#cmTv-3').text();
            ca.cmTv4 = +$('#cmTv-4').text();
            ca.cmTv5 = +$('#cmTv-5').text();
            ca.cmTv6 = +$('#cmTv-6').text();
            ca.cmInternet1 = +$('#cmInternet-1').text();
            ca.cmInternet2 = +$('#cmInternet-2').text();
            ca.cmInternet3 = +$('#cmInternet-3').text();
            ca.cmInternet4 = +$('#cmInternet-4').text();
            ca.cmInternet5 = +$('#cmInternet-5').text();
            ca.cmInternet6 = +$('#cmInternet-6').text();
            ca.cmSign1 = +$('#cmSign-1').text();
            ca.cmSign2 = +$('#cmSign-2').text();
            ca.cmSign3 = +$('#cmSign-3').text();
            ca.cmSign4 = +$('#cmSign-4').text();
            ca.cmSign5 = +$('#cmSign-5').text();
            ca.cmSign6 = +$('#cmSign-6').text();
            ca.cmMate1 = +$('#cmMate-1').text();
            ca.cmMate2 = +$('#cmMate-2').text();
            ca.cmMate3 = +$('#cmMate-3').text();
            ca.cmMate4 = +$('#cmMate-4').text();
            ca.cmMate5 = +$('#cmMate-5').text();
            ca.cmMate6 = +$('#cmMate-6').text();
            ca.cmOthers1 = +$('#cmOthers-1').text();
            ca.cmOthers2 = +$('#cmOthers-2').text();
            ca.cmOthers3 = +$('#cmOthers-3').text();
            ca.cmOthers4 = +$('#cmOthers-4').text();
            ca.cmOthers5 = +$('#cmOthers-5').text();
            ca.cmOthers6 = +$('#cmOthers-6').text();
            ca.cmMailAgpIn1 = +$('#cmMailAgpIn-1').text();
            ca.cmMailAgpIn2 = +$('#cmMailAgpIn-2').text();
            ca.cmMailAgpIn3 = +$('#cmMailAgpIn-3').text();
            ca.cmMailAgpIn4 = +$('#cmMailAgpIn-4').text();
            ca.cmMailAgpIn5 = +$('#cmMailAgpIn-5').text();
            ca.cmMailAgpIn6 = +$('#cmMailAgpIn-6').text();
            ca.cmPostFlyerAgpIn1 = +$('#cmPostFlyerAgpIn-1').text();
            ca.cmPostFlyerAgpIn2 = +$('#cmPostFlyerAgpIn-2').text();
            ca.cmPostFlyerAgpIn3 = +$('#cmPostFlyerAgpIn-3').text();
            ca.cmPostFlyerAgpIn4 = +$('#cmPostFlyerAgpIn-4').text();
            ca.cmPostFlyerAgpIn5 = +$('#cmPostFlyerAgpIn-5').text();
            ca.cmPostFlyerAgpIn6 = +$('#cmPostFlyerAgpIn-6').text();
            ca.cmHandFlyerAgpIn1 = +$('#cmHandFlyerAgpIn-1').text();
            ca.cmHandFlyerAgpIn2 = +$('#cmHandFlyerAgpIn-2').text();
            ca.cmHandFlyerAgpIn3 = +$('#cmHandFlyerAgpIn-3').text();
            ca.cmHandFlyerAgpIn4 = +$('#cmHandFlyerAgpIn-4').text();
            ca.cmHandFlyerAgpIn5 = +$('#cmHandFlyerAgpIn-5').text();
            ca.cmHandFlyerAgpIn6 = +$('#cmHandFlyerAgpIn-6').text();
            ca.cmCpAgpIn1 = +$('#cmCpAgpIn-1').text();
            ca.cmCpAgpIn2 = +$('#cmCpAgpIn-2').text();
            ca.cmCpAgpIn3 = +$('#cmCpAgpIn-3').text();
            ca.cmCpAgpIn4 = +$('#cmCpAgpIn-4').text();
            ca.cmCpAgpIn5 = +$('#cmCpAgpIn-5').text();
            ca.cmCpAgpIn6 = +$('#cmCpAgpIn-6').text();
            ca.cmOutAgpOut1 = +$('#cmOutAgpOut-1').text();
            ca.cmOutAgpOut2 = +$('#cmOutAgpOut-2').text();
            ca.cmOutAgpOut3 = +$('#cmOutAgpOut-3').text();
            ca.cmOutAgpOut4 = +$('#cmOutAgpOut-4').text();
            ca.cmOutAgpOut5 = +$('#cmOutAgpOut-5').text();
            ca.cmOutAgpOut6 = +$('#cmOutAgpOut-6').text();
            ca.cmInAgpOut1 = +$('#cmInAgpOut-1').text();
            ca.cmInAgpOut2 = +$('#cmInAgpOut-2').text();
            ca.cmInAgpOut3 = +$('#cmInAgpOut-3').text();
            ca.cmInAgpOut4 = +$('#cmInAgpOut-4').text();
            ca.cmInAgpOut5 = +$('#cmInAgpOut-5').text();
            ca.cmInAgpOut6 = +$('#cmInAgpOut-6').text();
            ca.cmBlogAgpOut1 = +$('#cmBlogAgpOut-1').text();
            ca.cmBlogAgpOut2 = +$('#cmBlogAgpOut-2').text();
            ca.cmBlogAgpOut3 = +$('#cmBlogAgpOut-3').text();
            ca.cmBlogAgpOut4 = +$('#cmBlogAgpOut-4').text();
            ca.cmBlogAgpOut5 = +$('#cmBlogAgpOut-5').text();
            ca.cmBlogAgpOut6 = +$('#cmBlogAgpOut-6').text();
            ca.cmBagAgpOut1 = +$('#cmBagAgpOut-1').text();
            ca.cmBagAgpOut2 = +$('#cmBagAgpOut-2').text();
            ca.cmBagAgpOut3 = +$('#cmBagAgpOut-3').text();
            ca.cmBagAgpOut4 = +$('#cmBagAgpOut-4').text();
            ca.cmBagAgpOut5 = +$('#cmBagAgpOut-5').text();
            ca.cmBagAgpOut6 = +$('#cmBagAgpOut-6').text();
            ca.cmApoTotal1 = +$('#cmApoTotal-1').text();
            ca.cmApoTotal2 = +$('#cmApoTotal-2').text();
            ca.cmApoTotal3 = +$('#cmApoTotal-3').text();
            ca.cmApoTotal4 = +$('#cmApoTotal-4').text();
            ca.cmApoTotal5 = +$('#cmApoTotal-5').text();
            ca.cmApoTotal6 = +$('#cmApoTotal-6').text();
            ca.cmInApptRatio1 = parseFloat($('#cmInApptRatio-1').text())/100;
            ca.cmInApptRatio2 = parseFloat($('#cmInApptRatio-2').text())/100;
            ca.cmInApptRatio3 = parseFloat($('#cmInApptRatio-3').text())/100;
            ca.cmInApptRatio4 = parseFloat($('#cmInApptRatio-4').text())/100;
            ca.cmInApptRatio5 = parseFloat($('#cmInApptRatio-5').text())/100;
            ca.cmInApptRatio6 = parseFloat($('#cmInApptRatio-6').text())/100;
            ca.cmOutApptRatio1 = parseFloat($('#cmOutApptRatio-1').text())/100;
            ca.cmOutApptRatio2 = parseFloat($('#cmOutApptRatio-2').text())/100;
            ca.cmOutApptRatio3 = parseFloat($('#cmOutApptRatio-3').text())/100;
            ca.cmOutApptRatio4 = parseFloat($('#cmOutApptRatio-4').text())/100;
            ca.cmOutApptRatio5 = parseFloat($('#cmOutApptRatio-5').text())/100;
            ca.cmOutApptRatio6 = parseFloat($('#cmOutApptRatio-6').text())/100;
            ca.cmPostPerApo6 = +$('#cmPostPerApo-6').text();
    //        ca.cmHandPerApo6 = +$('#cmHandPerApo-6').text();
            ca.cmHandHoursPerApo6 = +$('#cmHandHoursPerApo-6').text();
            ca.cmOutGpHoursPerApo6 = parseFloat($('#cmOutGpHoursPerApo-6').text());
    //        ca.cmOutGpPerApo6 = +$('#cmOutGpPerApo-6').text();
            ca.cmBrAgpRatio1 = parseFloat($('#cmBrAgpRatio-1').text())/100;
            ca.cmBrAgpRatio2 = parseFloat($('#cmBrAgpRatio-2').text())/100;
            ca.cmBrAgpRatio3 = parseFloat($('#cmBrAgpRatio-3').text())/100;
            ca.cmBrAgpRatio4 = parseFloat($('#cmBrAgpRatio-4').text())/100;
            ca.cmBrAgpRatio5 = parseFloat($('#cmBrAgpRatio-5').text())/100;
            ca.cmBrAgpRatio6 = parseFloat($('#cmBrAgpRatio-6').text())/100;
            ca.cmFaSum1 = +$('#cmFaSum-1').text();
            ca.cmFaSum2 = +$('#cmFaSum-2').text();
            ca.cmFaSum3 = +$('#cmFaSum-3').text();
            ca.cmFaSum4 = +$('#cmFaSum-4').text();
            ca.cmFaSum5 = +$('#cmFaSum-5').text();
            ca.cmFaSum6 = +$('#cmFaSum-6').text();
            ca.cmShowRatio1 = parseFloat($('#cmShowRatio-1').text())/100;
            ca.cmShowRatio2 = parseFloat($('#cmShowRatio-2').text())/100;
            ca.cmShowRatio3 = parseFloat($('#cmShowRatio-3').text())/100;
            ca.cmShowRatio4 = parseFloat($('#cmShowRatio-4').text())/100;
            ca.cmShowRatio5 = parseFloat($('#cmShowRatio-5').text())/100;
            ca.cmShowRatio6 = parseFloat($('#cmShowRatio-6').text())/100;
            ca.cmTraining1 = $('#cmTraining-1').text();
            ca.cmTraining2 = $('#cmTraining-2').text();
            ca.cmTraining3 = $('#cmTraining-3').text();
            ca.cmTraining4 = $('#cmTraining-4').text();
            ca.cmTraining5 = $('#cmTraining-5').text();
            ca.cmGot3_1 = $('#cmGot3-1').text();
            ca.cmGot3_2 = $('#cmGot3-2').text();
            ca.cmGot3_3 = $('#cmGot3-3').text();
            ca.cmGot3_4 = $('#cmGot3-4').text();
            ca.cmGot3_5 = $('#cmGot3-5').text();
            ca.cmInvitation1 = $('#cmInvitation-1').text();
            ca.cmInvitation2 = $('#cmInvitation-2').text();
            ca.cmInvitation3 = $('#cmInvitation-3').text();
            ca.cmInvitation4 = $('#cmInvitation-4').text();
            ca.cmInvitation5 = $('#cmInvitation-5').text();

            ca.salesAch1 = +$('#salesAch-1').text();
            ca.salesAch2 = +$('#salesAch-2').text();
            ca.salesAch3 = +$('#salesAch-3').text();
            ca.salesAch4 = +$('#salesAch-4').text();
            ca.salesAch5 = +$('#salesAch-5').text();
            ca.salesAch6 = +$('#salesAch-5').text();
            ca.salesMonthly1 = +$('#salesMonthly-1').text();
            ca.salesMonthly2 = +$('#salesMonthly-2').text();
            ca.salesMonthly3 = +$('#salesMonthly-3').text();
            ca.salesMonthly4 = +$('#salesMonthly-4').text();
            ca.salesMonthly5 = +$('#salesMonthly-5').text();
            ca.salesMonthly6 = +$('#salesMonthly-6').text();
            ca.salesAllPrepay1 = +$('#salesAllPrepay-1').text();
            ca.salesAllPrepay2 = +$('#salesAllPrepay-2').text();
            ca.salesAllPrepay3 = +$('#salesAllPrepay-3').text();
            ca.salesAllPrepay4 = +$('#salesAllPrepay-4').text();
            ca.salesAllPrepay5 = +$('#salesAllPrepay-5').text();
            ca.salesAllPrepay6 = +$('#salesAllPrepay-6').text();
            ca.salesTotal1 = +$('#salesTotal-1').text();
            ca.salesTotal2 = +$('#salesTotal-2').text();
            ca.salesTotal3 = +$('#salesTotal-3').text();
            ca.salesTotal4 = +$('#salesTotal-4').text();
            ca.salesTotal5 = +$('#salesTotal-5').text();
            ca.salesTotal6 = +$('#salesTotal-6').text();
            ca.salesRatio1 = parseFloat($('#salesRatio-1').text())/100;
            ca.salesRatio2 = parseFloat($('#salesRatio-2').text())/100;
            ca.salesRatio3 = parseFloat($('#salesRatio-3').text())/100;
            ca.salesRatio4 = parseFloat($('#salesRatio-4').text())/100;
            ca.salesRatio5 = parseFloat($('#salesRatio-5').text())/100;
            ca.salesRatio6 = parseFloat($('#salesRatio-6').text())/100;
            ca.salesAchAppRatio1 = parseFloat($('#salesAchAppRatio-1').text())/100;
            ca.salesAchAppRatio2 = parseFloat($('#salesAchAppRatio-2').text())/100;
            ca.salesAchAppRatio3 = parseFloat($('#salesAchAppRatio-3').text())/100;
            ca.salesAchAppRatio4 = parseFloat($('#salesAchAppRatio-4').text())/100;
            ca.salesAchAppRatio5 = parseFloat($('#salesAchAppRatio-5').text())/100;
            ca.salesAchAppRatio6 = parseFloat($('#salesAchAppRatio-6').text())/100;
            ca.salesFaReview1 = $('#salesFaReview-1').text();
            ca.salesFaReview2 = $('#salesFaReview-2').text();
            ca.salesFaReview3 = $('#salesFaReview-3').text();
            ca.salesFaReview4 = $('#salesFaReview-4').text();
            ca.salesFaReview5 = $('#salesFaReview-5').text();
            ca.salesPriceReview1 = $('#salesPriceReview-1').text();
            ca.salesPriceReview2 = $('#salesPriceReview-2').text();
            ca.salesPriceReview3 = $('#salesPriceReview-3').text();
            ca.salesPriceReview4 = $('#salesPriceReview-4').text();
            ca.salesPriceReview5 = $('#salesPriceReview-5').text();
            ca.salesAck1 = $('#salesAck-1').text();
            ca.salesAck2 = $('#salesAck-2').text();
            ca.salesAck3 = $('#salesAck-3').text();
            ca.salesAck4 = $('#salesAck-4').text();
            ca.salesAck5 = $('#salesAck-5').text();
            ca.salesTarget1 = $('#salesTarget-1').text();
            ca.salesTarget2 = $('#salesTarget-2').text();
            ca.salesTarget3 = $('#salesTarget-3').text();
            ca.salesTarget4 = $('#salesTarget-4').text();
            ca.salesTarget5 = $('#salesTarget-5').text();
            ca.salesMotivation1 = $('#salesMotivation-1').text();
            ca.salesMotivation2 = $('#salesMotivation-2').text();
            ca.salesMotivation3 = $('#salesMotivation-3').text();
            ca.salesMotivation4 = $('#salesMotivation-4').text();
            ca.salesMotivation5 = $('#salesMotivation-5').text();
            ca.salesObstacle1 = $('#salesObstacle-1').text();
            ca.salesObstacle2 = $('#salesObstacle-2').text();
            ca.salesObstacle3 = $('#salesObstacle-3').text();
            ca.salesObstacle4 = $('#salesObstacle-4').text();
            ca.salesObstacle5 = $('#salesObstacle-5').text();

            ca.mgmtMeeting1 = $('#mgmtMeeting-1').text();
            ca.mgmtMeeting2 = $('#mgmtMeeting-2').text();
            ca.mgmtMeeting3 = $('#mgmtMeeting-3').text();
            ca.mgmtMeeting4 = $('#mgmtMeeting-4').text();
            ca.mgmtMeeting5 = $('#mgmtMeeting-5').text();
            ca.mgmtCa1 = $('#mgmtCa-1').text();
            ca.mgmtCa2 = $('#mgmtCa-2').text();
            ca.mgmtCa3 = $('#mgmtCa-3').text();
            ca.mgmtCa4 = $('#mgmtCa-4').text();
            ca.mgmtCa5 = $('#mgmtCa-5').text();
            ca.mgmtGp1 = $('#mgmtGp-1').text();
            ca.mgmtGp2 = $('#mgmtGp-2').text();
            ca.mgmtGp3 = $('#mgmtGp-3').text();
            ca.mgmtGp4 = $('#mgmtGp-4').text();
            ca.mgmtGp5 = $('#mgmtGp-5').text();
            ca.mgmtLearn1 = $('#mgmtLearn-1').text();
            ca.mgmtLearn2 = $('#mgmtLearn-2').text();
            ca.mgmtLearn3 = $('#mgmtLearn-3').text();
            ca.mgmtLearn4 = $('#mgmtLearn-4').text();
            ca.mgmtLearn5 = $('#mgmtLearn-5').text();
            ca.mgmtSheet1 = $('#mgmtSheet-1').text();
            ca.mgmtSheet2 = $('#mgmtSheet-2').text();
            ca.mgmtSheet3 = $('#mgmtSheet-3').text();
            ca.mgmtSheet4 = $('#mgmtSheet-4').text();
            ca.mgmtSheet5 = $('#mgmtSheet-5').text();
            ca.mgmtPolicy1 = $('#mgmtPolicy-1').text();
            ca.mgmtPolicy2 = $('#mgmtPolicy-2').text();
            ca.mgmtPolicy3 = $('#mgmtPolicy-3').text();
            ca.mgmtPolicy4 = $('#mgmtPolicy-4').text();
            ca.mgmtPolicy5 = $('#mgmtPolicy-5').text();
            ca.mgmtCompiantSales1 = $('#mgmtCompiantSales-1').text();
            ca.mgmtCompiantSales2 = $('#mgmtCompiantSales-2').text();
            ca.mgmtCompiantSales3 = $('#mgmtCompiantSales-3').text();
            ca.mgmtCompiantSales4 = $('#mgmtCompiantSales-4').text();
            ca.mgmtCompiantSales5 = $('#mgmtCompiantSales-5').text();
            ca.mgmtCompiantMethod1 = $('#mgmtCompiantMethod-1').text();
            ca.mgmtCompiantMethod2 = $('#mgmtCompiantMethod-2').text();
            ca.mgmtCompiantMethod3 = $('#mgmtCompiantMethod-3').text();
            ca.mgmtCompiantMethod4 = $('#mgmtCompiantMethod-4').text();
            ca.mgmtCompiantMethod5 = $('#mgmtCompiantMethod-5').text();
            ca.mgmtCompiantProduct1 = $('#mgmtCompiantProduct-1').text();
            ca.mgmtCompiantProduct2 = $('#mgmtCompiantProduct-2').text();
            ca.mgmtCompiantProduct3 = $('#mgmtCompiantProduct-3').text();
            ca.mgmtCompiantProduct4 = $('#mgmtCompiantProduct-4').text();
            ca.mgmtCompiantProduct5 = $('#mgmtCompiantProduct-5').text();
            ca.mgmtCompiantAd1 = $('#mgmtCompiantAd-1').text();
            ca.mgmtCompiantAd2 = $('#mgmtCompiantAd-2').text();
            ca.mgmtCompiantAd3 = $('#mgmtCompiantAd-3').text();
            ca.mgmtCompiantAd4 = $('#mgmtCompiantAd-4').text();
            ca.mgmtCompiantAd5 = $('#mgmtCompiantAd-5').text();
            ca.mgmtTraining1 = $('#mgmtTraining-1').text();
            ca.mgmtTraining2 = $('#mgmtTraining-2').text();
            ca.mgmtTraining3 = $('#mgmtTraining-3').text();
            ca.mgmtTraining4 = $('#mgmtTraining-4').text();
            ca.mgmtTraining5 = $('#mgmtTraining-5').text();
            ca.mgmtReport1 = $('#mgmtReport-1').text();
            ca.mgmtReport2 = $('#mgmtReport-2').text();
            ca.mgmtReport3 = $('#mgmtReport-3').text();
            ca.mgmtReport4 = $('#mgmtReport-4').text();
            ca.mgmtReport5 = $('#mgmtReport-5').text();
            ca.mgmtPlan1 = $('#mgmtPlan-1').text();
            ca.mgmtPlan2 = $('#mgmtPlan-2').text();
            ca.mgmtPlan3 = $('#mgmtPlan-3').text();
            ca.mgmtPlan4 = $('#mgmtPlan-4').text();
            ca.mgmtPlan5 = $('#mgmtPlan-5').text();
            ca.mgmtMaintain1 = $('#mgmtMaintain-1').text();
            ca.mgmtMaintain2 = $('#mgmtMaintain-2').text();
            ca.mgmtMaintain3 = $('#mgmtMaintain-3').text();
            ca.mgmtMaintain4 = $('#mgmtMaintain-4').text();
            ca.mgmtMaintain5 = $('#mgmtMaintain-5').text();
            ca.mgmtFace2Face1 = $('#mgmtFace2Face-1').text();
            ca.mgmtFace2Face2 = $('#mgmtFace2Face-2').text();
            ca.mgmtFace2Face3 = $('#mgmtFace2Face-3').text();
            ca.mgmtFace2Face4 = $('#mgmtFace2Face-4').text();
            ca.mgmtFace2Face5 = $('#mgmtFace2Face-5').text();

            ca.clubSalesRatio = parseFloat($('#clubSalesRatio').text())/100;
            ca.clubAchAppRatio = parseFloat($('#clubAchAppRatio').text())/100;
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

            ca.staff1Name = $('#staff1').text();
            ca.staff1SalesRatio = parseFloat($('#staff1SalesRatio').text())/100;
            ca.staff1AchAppRatio = parseFloat($('#staff1AchAppRatio').text())/100;
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

            ca.staff2Name = $('#staff2').text();
            ca.staff2SalesRatio = parseFloat($('#staff2SalesRatio').text())/100;
            ca.staff2AchAppRatio = parseFloat($('#staff2AchAppRatio').text())/100;
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

            ca.staff3Name = $('#staff3').text();
            ca.staff3SalesRatio = parseFloat($('#staff3SalesRatio').text())/100;
            ca.staff3AchAppRatio = parseFloat($('#staff3AchAppRatio').text())/100;
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

            ca.staff4Name = $('#staff4').text();
            ca.staff4SalesRatio = parseFloat($('#staff4SalesRatio').text())/100;
            ca.staff4AchAppRatio = parseFloat($('#staff4AchAppRatio').text())/100;
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

            ca.staff5Name = $('#staff5').text();
            ca.staff5SalesRatio = parseFloat($('#staff5SalesRatio').text())/100;
            ca.staff5AchAppRatio = parseFloat($('#staff5AchAppRatio').text())/100;
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

            ca.staff6Name = $('#staff6').text();
            ca.staff6SalesRatio = parseFloat($('#staff6SalesRatio').text())/100;
            ca.staff6AchAppRatio = parseFloat($('#staff6AchAppRatio').text())/100;
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

            ca.thisPlan = $('#thisPlan').val();
            ca.nextPlan = $('#nextPlan').val();

            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json; charset=UTF-8'
                },
                'type': 'POST',
                'url': "/rest/CA",
                'data': JSON.stringify(ca),
                'dataType': 'json'
            }).done(function(data) {
                showAlert("#alertMain", "alert-success", "Save successfully.");
            }).fail(function() {
                showAlert("#alertMain", "alert-danger", "Save Fail. Please refresh and retry.");
            });

            $('#btnSave').prop("disabled", false);
        });

        function runFormula() {
            var goalsTm = +$('#goalsTm').text();
            var goalsExitsRatio = parseFloat($('#goalsExitsRatio').text())/100;
            var goalsLastTm = +$('#goalsLastTm').text();
            $('#goalsNewSales').text((goalsTm*(1+goalsExitsRatio)-goalsLastTm).toFixed(0));
            $('#salesTotalX').text($('#goalsNewSales').text());
            var goalsLastShowRatio = parseFloat($('#goalsLastShowRatio').text())/100;
            var goalsLastSalesRatio = parseFloat($('#goalsLastSalesRatio').text())/100;
            if (goalsLastShowRatio != 0 && goalsLastSalesRatio != 0) {
                var goalsNewSales = +$('#goalsNewSales').text();
                $('#goalsAppoints').text((goalsNewSales/goalsLastShowRatio/goalsLastSalesRatio).toFixed(0));
                $('#apoTotalX').text($('#goalsAppoints').text());
            }

            var svcTm1 = (+$('#svcTm-1').text());
            if (svcTm1 != 0) {
                var svcHold1 = (+$('#svcHold-1').text());
                $('#svcHoldRatio-1').text((svcHold1*100 / svcTm1).toFixed(1) + '%');
            }
            var svcTm2 = (+$('#svcTm-2').text());
            if (svcTm2 != 0) {
                var svcHold2 = (+$('#svcHold-2').text());
                $('#svcHoldRatio-2').text((svcHold2*100 / svcTm2).toFixed(1) + '%');
            }
            var svcTm3 = (+$('#svcTm-3').text());
            if (svcTm3 != 0) {
                var svcHold3 = (+$('#svcHold-3').text());
                $('#svcHoldRatio-3').text((svcHold3*100 / svcTm3).toFixed(1) + '%');
            }
            var svcTm4 = (+$('#svcTm-4').text());
            if (svcTm4 != 0) {
                var svcHold4 = (+$('#svcHold-4').text());
                $('#svcHoldRatio-4').text((svcHold4*100 / svcTm4).toFixed(1) + '%');
            }
            var svcTm5 = (+$('#svcTm-5').text());
            if (svcTm5 != 0) {
                var svcHold5 = (+$('#svcHold-5').text());
                $('#svcHoldRatio-5').text((svcHold5*100 / svcTm5).toFixed(1) + '%');
            }
            var svcTm6 = (+$('#svcTm-1').text());
            if (svcTm6 != 0) {
                var svcHold6 = (+$('#svcHold-6').text());
                var holds = (svcHold6*100 / svcTm6);
                $('#svcHoldRatio-6').text(holds.toFixed(1) + '%');
                if (holds < 0.12) {
                    $('#svcHoldRatio-0').css('color', 'red');
                }
            }

            var svcTotalWo1 = (+$('#svcTotalWo-1').text());
            var svcTotalWo2 = (+$('#svcTotalWo-2').text());
            var svcTotalWo3 = (+$('#svcTotalWo-3').text());
            var svcTotalWo4 = (+$('#svcTotalWo-4').text());
            var svcTotalWo5 = (+$('#svcTotalWo-5').text());
            var svcTotalWo6 = svcTotalWo1+svcTotalWo2+svcTotalWo3+svcTotalWo4+svcTotalWo5;
            $('#svcTotalWo-6').text(svcTotalWo6);

            var svcActive1 = (+$('#svcActive-1').text());
            if (svcActive1 != 0) {
                $('#svcAvgWo-1').text((svcTotalWo1/svcActive1).toFixed(1));
            }
            var svcActive2 = (+$('#svcActive-2').text());
            if (svcActive2 != 0) {
                $('#svcAvgWo-2').text((svcTotalWo2/svcActive2).toFixed(1));
            }
            var svcActive3 = (+$('#svcActive-3').text());
            if (svcActive3 != 0) {
                $('#svcAvgWo-3').text((svcTotalWo3/svcActive3).toFixed(1));
            }
            var svcActive4 = (+$('#svcActive-4').text());
            if (svcActive4 != 0) {
                $('#svcAvgWo-4').text((svcTotalWo4/svcActive4).toFixed(1));
            }
            var svcActive5 = (+$('#svcActive-5').text());
            if (svcActive5 != 0) {
                $('#svcAvgWo-5').text((svcTotalWo5/svcActive5).toFixed(1));
            }
            var svcActive6 = (+$('#svcActive-6').text());
            if (svcActive6 != 0) {
                var max6 = Math.max(+$('#svcMaxWo-1').text(), +$('#svcMaxWo-2').text(), +$('#svcMaxWo-3').text(), +$('#svcMaxWo-4').text(), +$('#svcMaxWo-5').text());
                var avgWo = svcTotalWo6/svcActive6/4;
                $('#svcAvgWo-6').text(avgWo.toFixed(1));
                if (avgWo > 2) {
                    $('#svcAvgWo-0').css('color', 'red');
                }
                $('#svcMaxWo-6').text((max6*100/svcActive6).toFixed(0)+'%');
            }
            if (svcTotalWo6 > (8*svcActive6)) {
                $('#svcTotalWo-0').css('color', 'red');
            }

            var svcExits1 = (+$('#svcExits-1').text());
            var svcExits2 = (+$('#svcExits-2').text());
            var svcExits3 = (+$('#svcExits-3').text());
            var svcExits4 = (+$('#svcExits-4').text());
            var svcExits5 = (+$('#svcExits-5').text());
            var svcExits6 = svcExits1+svcExits2+svcExits3+svcExits4+svcExits5;
            $('#svcExits-6').text(svcExits6);
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
                var exitsRatios = svcExits6*100 / svcTm6;
                if (exitsRatios < 0.04) {
                    $('#svcExitsRatio-0').css('color', 'red');
                }
                $('#svcExitsRatio-6').text(exitsRatios.toFixed(1) + '%');
            }

            var svcMeasure1 = (+$('#svcMeasure-1').text());
            var svcMeasure2 = (+$('#svcMeasure-2').text());
            var svcMeasure3 = (+$('#svcMeasure-3').text());
            var svcMeasure4 = (+$('#svcMeasure-4').text());
            var svcMeasure5 = (+$('#svcMeasure-5').text());
            var svcMeasure6 = svcMeasure1+svcMeasure2+svcMeasure3+svcMeasure4+svcMeasure5;
            $('#svcMeasure-6').text(svcMeasure6);
            if (svcActive1 != 0) {
                $('#svcMeasureRatio-1').text((svcMeasure1*100 / svcActive1).toFixed(0) + '%');
            }
            if (svcActive2 != 0) {
                $('#svcMeasureRatio-2').text((svcMeasure2*100 / svcActive2).toFixed(0) + '%');
            }
            if (svcActive3 != 0) {
                $('#svcMeasureRatio-3').text((svcMeasure3*100 / svcActive3).toFixed(0) + '%');
            }
            if (svcActive4 != 0) {
                $('#svcMeasureRatio-4').text((svcMeasure4*100 / svcActive4).toFixed(0) + '%');
            }
            if (svcActive5 != 0) {
                $('#svcMeasureRatio-5').text((svcMeasure5*100 / svcActive5).toFixed(0) + '%');
            }
            if (svcActive6 != 0) {
                var msr = svcMeasure6*100 / svcActive6;
                if (msr > (svcActive6*0.6)) {
                    $('#svcMeasureRatio-0').css('color', 'red');
                }
                $('#svcMeasureRatio-6').text(msr.toFixed(0) + '%');
                var svc12 = (+$('#svc12-5').text())*100 / svcActive6;
                if (svc12 > 0.3) {
                    $('#svc12-0').css('color', 'red');
                }
                $('#svc12-6').text(svc12.toFixed(0) + '%');
                var svc8to11 = (+$('#svc8to11-5').text())*100 / svcActive6;
                if (svc8to11 > 0.25) {
                    $('#svc8to11-0').css('color', 'red');
                }
                $('#svc8to11-6').text(svc8to11.toFixed(0) + '%');
                $('#svc4to7-6').text(((+$('#svc4to7-5').text())*100 / svcActive6).toFixed(0) + '%');
                $('#svc1to3-6').text(((+$('#svc1to3-5').text())*100 / svcActive6).toFixed(0) + '%');
                $('#svc0-6').text(((+$('#svc0-5').text())*100 / svcActive6).toFixed(0) + '%');
            }

            $('#cmPostFlyer-6').text((+$('#cmPostFlyer-1').text())+(+$('#cmPostFlyer-2').text())+(+$('#cmPostFlyer-3').text())+(+$('#cmPostFlyer-4').text())+(+$('#cmPostFlyer-5').text()));
            $('#cmHandFlyer-6').text((+$('#cmHandFlyer-1').text())+(+$('#cmHandFlyer-2').text())+(+$('#cmHandFlyer-3').text())+(+$('#cmHandFlyer-4').text())+(+$('#cmHandFlyer-5').text()));
            $('#cmHandFlyerHours-6').text((+$('#cmHandFlyerHours-1').text())+(+$('#cmHandFlyerHours-2').text())+(+$('#cmHandFlyerHours-3').text())+(+$('#cmHandFlyerHours-4').text())+(+$('#cmHandFlyerHours-5').text()));
            $('#cmOutGpHours-6').text((+$('#cmOutGpHours-1').text())+(+$('#cmOutGpHours-2').text())+(+$('#cmOutGpHours-3').text())+(+$('#cmOutGpHours-4').text())+(+$('#cmOutGpHours-5').text()));
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
            var cmCallIn1 = +$('#cmCallIn-1').text();
            var cmCallIn2 = +$('#cmCallIn-2').text();
            var cmCallIn3 = +$('#cmCallIn-3').text();
            var cmCallIn4 = +$('#cmCallIn-4').text();
            var cmCallIn5 = +$('#cmCallIn-5').text();
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
                $('#cmInApptRatio-1').text((cmInApoTotal1*100/cmCallIn1).toFixed(0) + '%');
            }
            if (cmCallIn2 != 0) {
                $('#cmInApptRatio-2').text((cmInApoTotal2*100/cmCallIn2).toFixed(0) + '%');
            }
            if (cmCallIn3 != 0) {
                $('#cmInApptRatio-3').text((cmInApoTotal3*100/cmCallIn3).toFixed(0) + '%');
            }
            if (cmCallIn4 != 0) {
                $('#cmInApptRatio-4').text((cmInApoTotal4*100/cmCallIn4).toFixed(0) + '%');
            }
            if (cmCallIn5 != 0) {
                $('#cmInApptRatio-5').text((cmInApoTotal5*100/cmCallIn5).toFixed(0) + '%');
            }
            if (cmCallIn6 != 0) {
                var apptRatio = cmInApoTotal6*100/cmCallIn6;
                if (apptRatio == 1) {
                    $('#cmInApptRatio-0').css('color', 'red');
                }
                $('#cmInApptRatio-6').text(apptRatio.toFixed(0) + '%');
            }
            var cmGotCallTotal1 = (+$('#cmOutGotCall-1').text())+(+$('#cmInGotCall-1').text())+(+$('#cmBlogGotCall-1').text())+(+$('#cmBagGotCall-1').text());
            $('#cmOutApptRatio-1').text((ApgOutTotal1*100/cmGotCallTotal1).toFixed(0) + '%');
            var cmGotCallTotal2 = (+$('#cmOutGotCall-2').text())+(+$('#cmInGotCall-2').text())+(+$('#cmBlogGotCall-2').text())+(+$('#cmBagGotCall-2').text());
            $('#cmOutApptRatio-2').text((ApgOutTotal2*100/cmGotCallTotal2).toFixed(0) + '%');
            var cmGotCallTotal3 = (+$('#cmOutGotCall-3').text())+(+$('#cmInGotCall-3').text())+(+$('#cmBlogGotCall-3').text())+(+$('#cmBagGotCall-3').text());
            $('#cmOutApptRatio-3').text((ApgOutTotal3*100/cmGotCallTotal3).toFixed(0) + '%');
            var cmGotCallTotal4 = (+$('#cmOutGotCall-4').text())+(+$('#cmInGotCall-4').text())+(+$('#cmBlogGotCall-4').text())+(+$('#cmBagGotCall-4').text());
            $('#cmOutApptRatio-4').text((ApgOutTotal4*100/cmGotCallTotal4).toFixed(0) + '%');
            var cmGotCallTotal5 = (+$('#cmOutGotCall-5').text())+(+$('#cmInGotCall-5').text())+(+$('#cmBlogGotCall-5').text())+(+$('#cmBagGotCall-5').text());
            $('#cmOutApptRatio-5').text((ApgOutTotal5*100/cmGotCallTotal5).toFixed(0) + '%');
            var cmGotCallTotal6 = (+$('#cmOutGotCall-6').text())+(+$('#cmInGotCall-6').text())+(+$('#cmBlogGotCall-6').text())+(+$('#cmBagGotCall-6').text());
            $('#cmOutApptRatio-6').text((ApgOutTotal6*100/cmGotCallTotal6).toFixed(0) + '%');

            $('#cmPostPerApo-6').text(((+$('#cmPostFlyer-6').text())/cmPostFlyerAgpIn6).toFixed(0));
            $('#cmHandPerApo-6').text(((+$('#cmHandFlyer-6').text())/cmHandFlyerAgpIn6).toFixed(0));
            $('#cmHandHoursPerApo-6').text(((+$('#cmHandFlyerHours-6').text())/cmHandFlyerAgpIn6).toFixed(1));
            $('#cmOutGpHoursPerApo-6').text(((+$('#cmOutGpHours-6').text())/(+$('#cmOutAgpOut-6').text())).toFixed(1));
            $('#cmOutGpPerApo-6').text(((+$('#cmOutGp-6').text())/(+$('#cmOutAgpOut-6').text())).toFixed(1));
            $('#cmBrAgpRatio-1').text(((cmOwnRefs1+cmMailAgpIn1+cmPostFlyerAgpIn1+cmHandFlyerAgpIn1+cmCpAgpIn1+cmOutAgpOut1+cmInAgpOut1+cmBlogAgpOut1+cmBagAgpOut1)*100/(agpTotal1)).toFixed(0) + '%');
            $('#cmBrAgpRatio-2').text(((cmOwnRefs2+cmMailAgpIn2+cmPostFlyerAgpIn2+cmHandFlyerAgpIn2+cmCpAgpIn2+cmOutAgpOut2+cmInAgpOut2+cmBlogAgpOut2+cmBagAgpOut2)*100/(agpTotal2)).toFixed(0) + '%');
            $('#cmBrAgpRatio-3').text(((cmOwnRefs3+cmMailAgpIn3+cmPostFlyerAgpIn3+cmHandFlyerAgpIn3+cmCpAgpIn3+cmOutAgpOut3+cmInAgpOut3+cmBlogAgpOut3+cmBagAgpOut3)*100/(agpTotal3)).toFixed(0) + '%');
            $('#cmBrAgpRatio-4').text(((cmOwnRefs4+cmMailAgpIn4+cmPostFlyerAgpIn4+cmHandFlyerAgpIn4+cmCpAgpIn4+cmOutAgpOut4+cmInAgpOut4+cmBlogAgpOut4+cmBagAgpOut4)*100/(agpTotal4)).toFixed(0) + '%');
            $('#cmBrAgpRatio-5').text(((cmOwnRefs5+cmMailAgpIn5+cmPostFlyerAgpIn5+cmHandFlyerAgpIn5+cmCpAgpIn5+cmOutAgpOut5+cmInAgpOut5+cmBlogAgpOut5+cmBagAgpOut5)*100/(agpTotal5)).toFixed(0) + '%');
            $('#cmBrAgpRatio-6').text(((cmOwnRefs6+cmMailAgpIn6+cmPostFlyerAgpIn6+cmHandFlyerAgpIn6+cmCpAgpIn6+cmOutAgpOut6+cmInAgpOut6+cmBlogAgpOut6+cmBagAgpOut6)*100/(agpTotal6)).toFixed(0) + '%');
            $('#cmFaSum-6').text((+$('#cmFaSum-1').text())+(+$('#cmFaSum-2').text())+(+$('#cmFaSum-3').text())+(+$('#cmFaSum-4').text())+(+$('#cmFaSum-5').text()));
            $('#cmShowRatio-1').text(((+$('#cmFaSum-1').text())*100/agpTotal1).toFixed(0) + '%');
            $('#cmShowRatio-2').text(((+$('#cmFaSum-2').text())*100/agpTotal2).toFixed(0) + '%');
            $('#cmShowRatio-3').text(((+$('#cmFaSum-3').text())*100/agpTotal3).toFixed(0) + '%');
            $('#cmShowRatio-4').text(((+$('#cmFaSum-4').text())*100/agpTotal4).toFixed(0) + '%');
            $('#cmShowRatio-5').text(((+$('#cmFaSum-5').text())*100/agpTotal5).toFixed(0) + '%');
            var showUpRate = (+$('#cmFaSum-6').text())*100/agpTotal6;
            if (showUpRate > 0.8) {
                $('#cmShowRatio-0').css('color', 'red');
            }
            $('#cmShowRatio-6').text(showUpRate.toFixed(0) + '%');

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
            $('#salesRatio-1').text((salesTotal1*100/(+$('#cmFaSum-1').text())).toFixed(0) + '%');
            $('#salesRatio-2').text((salesTotal2*100/(+$('#cmFaSum-2').text())).toFixed(0) + '%');
            $('#salesRatio-3').text((salesTotal3*100/(+$('#cmFaSum-3').text())).toFixed(0) + '%');
            $('#salesRatio-4').text((salesTotal4*100/(+$('#cmFaSum-4').text())).toFixed(0) + '%');
            $('#salesRatio-5').text((salesTotal5*100/(+$('#cmFaSum-5').text())).toFixed(0) + '%');
            var salesRate = salesTotal6*100/(+$('#cmFaSum-6').text());
            if (salesRate > 0.85) {
                $('#salesRatio-0').css('color', 'red');
            }
            $('#salesRatio-6').text(salesRate.toFixed(0) + '%');
            $('#salesAchAppRatio-1').text((((+$('#salesAch-1').text()) + (+$('#salesAllPrepay-1').text()))*100/salesTotal1).toFixed(0) + '%');
            $('#salesAchAppRatio-2').text((((+$('#salesAch-2').text()) + (+$('#salesAllPrepay-2').text()))*100/salesTotal2).toFixed(0) + '%');
            $('#salesAchAppRatio-3').text((((+$('#salesAch-3').text()) + (+$('#salesAllPrepay-3').text()))*100/salesTotal3).toFixed(0) + '%');
            $('#salesAchAppRatio-4').text((((+$('#salesAch-4').text()) + (+$('#salesAllPrepay-4').text()))*100/salesTotal4).toFixed(0) + '%');
            $('#salesAchAppRatio-5').text((((+$('#salesAch-5').text()) + (+$('#salesAllPrepay-5').text()))*100/salesTotal5).toFixed(0) + '%');
            var achAppRate = ((+$('#salesAch-6').text()) + (+$('#salesAllPrepay-6').text()))*100/salesTotal6;
            if (achAppRate > 0.9) {
                $('#salesAchAppRatio-0').css('color', 'red');
            }
            $('#salesAchAppRatio-6').text(achAppRate.toFixed(0) + '%');

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
            var staff1Sales = staff1AchTotal+staff1MmTotal+staff1AppTotal;
            $('#staff1SalesRatio').text((staff1Sales*100/(staff1Sales+staff1NsTotal)).toFixed(0)+'%');
            $('#staff1AchAppRatio').text((staff1AchTotal+staff1AppTotal*100/staff1Sales).toFixed(0)+'%');
            var staff2Sales = staff2AchTotal+staff2MmTotal+staff2AppTotal;
            $('#staff2SalesRatio').text((staff2Sales*100/(staff2Sales+staff2NsTotal)).toFixed(0)+'%');
            $('#staff2AchAppRatio').text((staff2AchTotal+staff2AppTotal*100/staff2Sales).toFixed(0)+'%');
            var staff3Sales = staff3AchTotal+staff3MmTotal+staff3AppTotal;
            $('#staff3SalesRatio').text((staff3Sales*100/(staff3Sales+staff3NsTotal)).toFixed(0)+'%');
            $('#staff3AchAppRatio').text((staff3AchTotal+staff3AppTotal*100/staff3Sales).toFixed(0)+'%');
            var staff4Sales = staff1AchTotal+staff4MmTotal+staff4AppTotal;
            $('#staff4SalesRatio').text((staff4Sales*100/(staff4Sales+staff4NsTotal)).toFixed(0)+'%');
            $('#staff4AchAppRatio').text((staff4AchTotal+staff4AppTotal*100/staff4Sales).toFixed(0)+'%');
            var staff5Sales = staff5AchTotal+staff5MmTotal+staff5AppTotal;
            $('#staff5SalesRatio').text((staff5Sales*100/(staff5Sales+staff5NsTotal)).toFixed(0)+'%');
            $('#staff5AchAppRatio').text((staff5AchTotal+staff5AppTotal*100/staff5Sales).toFixed(0)+'%');
            var staff6Sales = staff6AchTotal+staff6MmTotal+staff6AppTotal;
            $('#staff6SalesRatio').text((staff6Sales*100/(staff6Sales+staff6NsTotal)).toFixed(0)+'%');
            $('#staff6AchAppRatio').text((staff6AchTotal+staff6AppTotal*100/staff6Sales).toFixed(0)+'%');
            $('#staff1Ach-6').text(staff1AchTotal);
            $('#staff2Ach-6').text(staff2AchTotal);
            $('#staff3Ach-6').text(staff3AchTotal);
            $('#staff4Ach-6').text(staff4AchTotal);
            $('#staff5Ach-6').text(staff5AchTotal);
            $('#staff6Ach-6').text(staff6AchTotal);
            var clubAch1 = (+$('#staff1Ach-1').text())+(+$('#staff2Ach-1').text())+(+$('#staff3Ach-1').text())+(+$('#staff4Ach-1').text())+(+$('#staff5Ach-1').text())+(+$('#staff6Ach-1').text());
            $('#clubAch-1').text(clubAch1);
            var clubAch2 = (+$('#staff1Ach-2').text())+(+$('#staff2Ach-2').text())+(+$('#staff3Ach-2').text())+(+$('#staff4Ach-2').text())+(+$('#staff5Ach-2').text())+(+$('#staff6Ach-2').text());
            $('#clubAch-2').text(clubAch2);
            var clubAch3 = (+$('#staff1Ach-3').text())+(+$('#staff2Ach-3').text())+(+$('#staff3Ach-3').text())+(+$('#staff4Ach-3').text())+(+$('#staff5Ach-3').text())+(+$('#staff6Ach-3').text());
            $('#clubAch-3').text(clubAch3);
            var clubAch4 = (+$('#staff1Ach-4').text())+(+$('#staff2Ach-4').text())+(+$('#staff3Ach-4').text())+(+$('#staff4Ach-4').text())+(+$('#staff5Ach-4').text())+(+$('#staff6Ach-4').text());
            $('#clubAch-4').text(clubAch4);
            var clubAch5 = (+$('#staff1Ach-5').text())+(+$('#staff2Ach-5').text())+(+$('#staff3Ach-5').text())+(+$('#staff4Ach-5').text())+(+$('#staff5Ach-5').text())+(+$('#staff6Ach-5').text());
            $('#clubAch-5').text(clubAch5);
            $('#clubAch-6').text(clubAch1+clubAch2+clubAch3+clubAch4+clubAch5);
            $('#staff1Mm-6').text(staff1MmTotal);
            $('#staff2Mm-6').text(staff2MmTotal);
            $('#staff3Mm-6').text(staff3MmTotal);
            $('#staff4Mm-6').text(staff4MmTotal);
            $('#staff5Mm-6').text(staff5MmTotal);
            $('#staff6Mm-6').text(staff6MmTotal);
            var clubMm1 = (+$('#staff1Mm-1').text())+(+$('#staff2Mm-1').text())+(+$('#staff3Mm-1').text())+(+$('#staff4Mm-1').text())+(+$('#staff5Mm-1').text())+(+$('#staff6Mm-1').text());
            $('#clubMm-1').text(clubMm1);
            var clubMm2 = (+$('#staff1Mm-2').text())+(+$('#staff2Mm-2').text())+(+$('#staff3Mm-2').text())+(+$('#staff4Mm-2').text())+(+$('#staff5Mm-2').text())+(+$('#staff6Mm-2').text());
            $('#clubMm-2').text(clubMm2);
            var clubMm3 = (+$('#staff1Mm-3').text())+(+$('#staff2Mm-3').text())+(+$('#staff3Mm-3').text())+(+$('#staff4Mm-3').text())+(+$('#staff5Mm-3').text())+(+$('#staff6Mm-3').text());
            $('#clubMm-3').text(clubMm3);
            var clubMm4 = (+$('#staff1Mm-4').text())+(+$('#staff2Mm-4').text())+(+$('#staff3Mm-4').text())+(+$('#staff4Mm-4').text())+(+$('#staff5Mm-4').text())+(+$('#staff6Mm-4').text());
            $('#clubMm-4').text(clubMm4);
            var clubMm5 = (+$('#staff1Mm-5').text())+(+$('#staff2Mm-5').text())+(+$('#staff3Mm-5').text())+(+$('#staff4Mm-5').text())+(+$('#staff5Mm-5').text())+(+$('#staff6Mm-5').text());
            $('#clubMm-5').text(clubMm5);
            $('#clubMm-6').text(clubMm1+clubMm2+clubMm3+clubMm4+clubMm5);
            $('#staff1App-6').text(staff1AppTotal);
            $('#staff2App-6').text(staff2AppTotal);
            $('#staff3App-6').text(staff3AppTotal);
            $('#staff4App-6').text(staff4AppTotal);
            $('#staff5App-6').text(staff5AppTotal);
            $('#staff6App-6').text(staff6AppTotal);
            var clubApp1 = (+$('#staff1App-1').text())+(+$('#staff2App-1').text())+(+$('#staff3App-1').text())+(+$('#staff4App-1').text())+(+$('#staff5App-1').text())+(+$('#staff6App-1').text());
            $('#clubApp-1').text(clubApp1);
            var clubApp2 = (+$('#staff1App-2').text())+(+$('#staff2App-2').text())+(+$('#staff3App-2').text())+(+$('#staff4App-2').text())+(+$('#staff5App-2').text())+(+$('#staff6App-2').text());
            $('#clubApp-2').text(clubApp2);
            var clubApp3 = (+$('#staff1App-3').text())+(+$('#staff2App-3').text())+(+$('#staff3App-3').text())+(+$('#staff4App-3').text())+(+$('#staff5App-3').text())+(+$('#staff6App-3').text());
            $('#clubApp-3').text(clubApp3);
            var clubApp4 = (+$('#staff1App-4').text())+(+$('#staff2App-4').text())+(+$('#staff3App-4').text())+(+$('#staff4App-4').text())+(+$('#staff5App-4').text())+(+$('#staff6App-4').text());
            $('#clubApp-4').text(clubApp4);
            var clubApp5 = (+$('#staff1App-5').text())+(+$('#staff2App-5').text())+(+$('#staff3App-5').text())+(+$('#staff4App-5').text())+(+$('#staff5App-5').text())+(+$('#staff6App-5').text());
            $('#clubApp-5').text(clubApp5);
            $('#clubApp-6').text(clubApp1+clubApp2+clubApp3+clubApp4+clubApp5);
            $('#staff1Ns-6').text(staff1NsTotal);
            $('#staff2Ns-6').text(staff2NsTotal);
            $('#staff3Ns-6').text(staff3NsTotal);
            $('#staff4Ns-6').text(staff4NsTotal);
            $('#staff5Ns-6').text(staff5NsTotal);
            $('#staff6Ns-6').text(staff6NsTotal);
            var clubNs1 = (+$('#staff1Ns-1').text())+(+$('#staff2Ns-1').text())+(+$('#staff3Ns-1').text())+(+$('#staff4Ns-1').text())+(+$('#staff5Ns-1').text())+(+$('#staff6Ns-1').text());
            $('#clubNs-1').text(clubNs1);
            var clubNs2 = (+$('#staff1Ns-2').text())+(+$('#staff2Ns-2').text())+(+$('#staff3Ns-2').text())+(+$('#staff4Ns-2').text())+(+$('#staff5Ns-2').text())+(+$('#staff6Ns-2').text());
            $('#clubNs-2').text(clubNs2);
            var clubNs3 = (+$('#staff1Ns-3').text())+(+$('#staff2Ns-3').text())+(+$('#staff3Ns-3').text())+(+$('#staff4Ns-3').text())+(+$('#staff5Ns-3').text())+(+$('#staff6Ns-3').text());
            $('#clubNs-3').text(clubNs3);
            var clubNs4 = (+$('#staff1Ns-4').text())+(+$('#staff2Ns-4').text())+(+$('#staff3Ns-4').text())+(+$('#staff4Ns-4').text())+(+$('#staff5Ns-4').text())+(+$('#staff6Ns-4').text());
            $('#clubNs-4').text(clubNs4);
            var clubNs5 = (+$('#staff1Ns-5').text())+(+$('#staff2Ns-5').text())+(+$('#staff3Ns-5').text())+(+$('#staff4Ns-5').text())+(+$('#staff5Ns-5').text())+(+$('#staff6Ns-5').text());
            $('#clubNs-5').text(clubNs5);
            $('#clubNs-6').text(clubNs1+clubNs2+clubNs3+clubNs4+clubNs5);
            $('#staff1Lx-6').text(staff1LxTotal);
            $('#staff2Lx-6').text(staff2LxTotal);
            $('#staff3Lx-6').text(staff3LxTotal);
            $('#staff4Lx-6').text(staff4LxTotal);
            $('#staff5Lx-6').text(staff5LxTotal);
            $('#staff6Lx-6').text(staff6LxTotal);
            var clubLx1 = (+$('#staff1Lx-1').text())+(+$('#staff2Lx-1').text())+(+$('#staff3Lx-1').text())+(+$('#staff4Lx-1').text())+(+$('#staff5Lx-1').text())+(+$('#staff6Lx-1').text());
            $('#clubLx-1').text(clubLx1);
            var clubLx2 = (+$('#staff1Lx-2').text())+(+$('#staff2Lx-2').text())+(+$('#staff3Lx-2').text())+(+$('#staff4Lx-2').text())+(+$('#staff5Lx-2').text())+(+$('#staff6Lx-2').text());
            $('#clubLx-2').text(clubLx2);
            var clubLx3 = (+$('#staff1Lx-3').text())+(+$('#staff2Lx-3').text())+(+$('#staff3Lx-3').text())+(+$('#staff4Lx-3').text())+(+$('#staff5Lx-3').text())+(+$('#staff6Lx-3').text());
            $('#clubLx-3').text(clubLx3);
            var clubLx4 = (+$('#staff1Lx-4').text())+(+$('#staff2Lx-4').text())+(+$('#staff3Lx-4').text())+(+$('#staff4Lx-4').text())+(+$('#staff5Lx-4').text())+(+$('#staff6Lx-4').text());
            $('#clubLx-4').text(clubLx4);
            var clubLx5 = (+$('#staff1Lx-5').text())+(+$('#staff2Lx-5').text())+(+$('#staff3Lx-5').text())+(+$('#staff4Lx-5').text())+(+$('#staff5Lx-5').text())+(+$('#staff6Lx-5').text());
            $('#clubLx-5').text(clubLx5);
            $('#clubLx-6').text(clubLx1+clubLx2+clubLx3+clubLx4+clubLx5);

            var ama = (+$('#clubAch-6').text()) + (+$('#clubMm-6').text()) + (+$('#clubApp-6').text());
            if (ama != 0) {
                var aa = (+$('#clubAch-6').text()) + (+$('#clubApp-6').text());
                $('#clubAchAppRatio').text((aa*100/ama).toFixed(0) + '%');
            }
            var aman = ama+(+$('#clubNs-6').text());
            if (aman != 0) {
                $('#clubSalesRatio').text((ama*100/aman).toFixed(0) + '%');
            }

            clearNaN();
        }
      });
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("ERROR STATUS: "+textStatus);
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });
});
