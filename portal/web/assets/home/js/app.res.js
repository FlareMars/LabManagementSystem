(function ($) {
    'use strict'
    var $app = window.$app;

    $app.resources = {
        btn_confirm: "确定",
        btn_cancel: "取消",
        btn_tipsConfirm: "[取消][确定]",
        label_systemInfo: "系统消息",
        label_confirm: "确认",
        label_progress: "处理中",
        label_inprogress: "系统正在处理中，请稍后...",
        msg_opSuccess: "操作成功",
        msg_systemError: "系统错误",
        msg_maxLength: '字段超长',
        msg_no_file_found: '未发现上传文件'
    };
    
    $app.onInitUrls = function(urls) {
        urls.home = $app.contextRootUrl + "home";
        urls.login = $app.contextRootUrl + "acc/login";
        urls.logout = $app.contextRootUrl + "acc/logout";
    }

    $app.errors = {
        system_error: $app.resources.msg_systemError,
        no_file_found: $app.resources.msg_no_file_found
    };
})(jQuery);