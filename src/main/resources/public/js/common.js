function showAlert(alertId, alertClass, msg) {
    var $alertDiv = $(alertId)
    $alertDiv.removeClass('hide');
    $alertDiv.addClass(alertClass);
    $alertDiv.text(msg);
    setTimeout(function() {
        $alertDiv.addClass('hide');
        $alertDiv.removeClass(alertClass);
    }, 5000);
}

var toggleEditable = function() {
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
}

var forceNumber = function(editable) {
    editable.keydown(function(e) {
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

;(function($) {
    $.QueryString = (function(a) {
        if (a === "") return {};
        var b = {};
        for (var i = 0; i < a.length; ++i)
        {
            var p=a[i].split('=');
            if (p.length != 2) continue;
            b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
        }
        return b;
    })(window.location.search.substr(1).split('&'));
})(jQuery);

;(function() {
    // init user ID / club ID
    $.get("/rest/whoami", function(userId) {
        $('#userId').html('<i class="fa fa-user"></i> '+userId+' <span class="caret"></span>');
        $('body').attr('userId', userId);
    }).fail(function(data) {
        showAlert("alert-danger", "Cannot find club info. Please refresh and retry.");
    });

    $('#btnChangePassword').click(function() {
        $('#dlgPassword').modal({});
    });

    $('#btnSave2').click(function() {
        var passwd1 = $('#passwd1').val(), passwd2 = $('#passwd2').val();
        var hasError = false;
        if ( !passwd1 || !passwd2 ) {
            showAlert("#alertPasswd", "alert-danger", "All fields must be filled.");
            return;
        } else if (passwd1 !== passwd2) {
            showAlert("#alertPasswd", "alert-danger", "Two passwords not match.");
            return;
        }

        $(this).prop("disabled", true);

        $.post("/rest/changePassword", {
            clubId: $('body').attr('userId'), password: passwd2
        }).done(function() {
            showAlert("#alertPasswd", "alert-success", "Save successfully.");
            $('#dlgPassword').modal({show: false});
        }).fail(function() {
            showAlert("#alertPasswd", "alert-danger", "Save Fail. Please refresh and retry.");
        }).always(function() {
            $(this).prop("disabled", false);
        });
    });
})();
