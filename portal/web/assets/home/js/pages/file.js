(function($) {

    var FOLDER_INDICATOR = '<td><i class="fa fa-folder"></i></td>';
    var FILE_INDICATOR = '<td><i class="fa fa-file-o"></i></td>';
    var DELETE_ACTION = '<td><a href="javascript:void(0)" data-action="delete" data-path="{0}" class="btn btn-danger btn-xs"><i class="fa fa-times"></i> 删除</a></td>';
    var DATA_NAME_FOLDER = "fileView_folderCreation";
    var HTML_FOLDER_CREATION = '\r\n<div class="btn-group">\r\n'
    + '<input class="form-control" placeholder="目录名称" type="text" style="width:98px;" />\r\n'
    + '</div>\r\n'
    + '<a class="btn btn-xs btn-success" data-action="confirmCreation">\r\n'
    + '<i class="fa fa-check"></i>\r\n'
    + '</a>\r\n'
    + '<a class="btn btn-xs btn-default" data-action="cancelCreation">\r\n'
    + '<i class="fa fa-times"></i>\r\n'
    + '</a>\r\n';

    app_inits.push(function() {
        var fl = new FileList({
            list: "#file_list",
            breadcrumb: "#breadcrumb",
            uploadFileElement: "upload_file",
            actionPanelElement: "#action_panel"
        });
        fl.refreshList();

        $("#action_test").click(function() {
            var dlg = new FileDialog();

            dlg.open();
        });
    });
})(jQuery)