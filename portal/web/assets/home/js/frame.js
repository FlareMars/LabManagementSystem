(function($) {
    'use strict'

    app_inits.push(function(){
    	init_logout_action();
    });

    $app.ajaxHandlers = [global_error_handler,
        $app.error_ajax_handler,
        $app.regular_ajax_handler];

    $app.initFileUpload = function(params) {
        init_file_upload(params);
    };

    function init_logout_action() {
        $("#action_logout").click(function(){
            $app.showConfirm({
                msg:'您确定要注销吗？',
                confirmCallback: function() {
                    window.location.href = $app.absolute("/acc/logout");
                }
            });
        });
    }

    function global_error_handler(process) {
        if(process.result.errorCode == 'acc_require_login') {
            $app.showMessage('您停留的时间太长，请重新登录', function() {
                    window.location.href = $app.urls.login + "?returnUrl=" + window.location.href;
                }
            );
            process.cancel = true;
        } else if(process.result.errorCode == 'acc_access_denied') {
            $app.showMessage('您的权限无法执行此操作，请联系系统管理员');
        }
    }

    function init_file_upload(params) {
        var $e = $("#"+params.fileElementId);
        $e.val("");
        $e.on('change', function() {
            upload_file(params);
        });
    }

    function upload_file(params) {
        $app.blockUI();

        var url = params.url ? params.url : "/rest/file/create";

        var data = params.dataCallback ? params.dataCallback() : params.data;

        $.ajaxFileUpload({
            url: $app.absolute(url, data),
            fileElementId: params.fileElementId,
            dataType: 'json',
            success: function (data, status) {
                $app.unblockUI();

                if(params.callback)
                    params.callback(data);
                /*else {
                    $("#" + params.imageElementId).attr("src", data.imageUrl);
                    $("#" + params.valueElementId).val(data.resourceId);
                }*/

                init_file_upload(params);
            },
            error: function (data, status, e) {
                $app.unblockUI();
                $app.showMessage("上传失败");
                init_file_upload(params);
            }
        });
    }
})(jQuery);