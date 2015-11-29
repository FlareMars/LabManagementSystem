(function($) {

    var FOLDER_TEMPLATE = '<td><i class="fa fa-folder"></i>  {name}</td>',
        FILE_TEMPLATE = '<td><i class="fa fa-file-o"></i>  {name}</td>',
        DELETE_ACTION = '<td><a href="javascript:void(0)" data-action="delete" data-path="{0}" class="btn btn-danger btn-xs"><i class="fa fa-times"></i> 删除</a></td>',
        SELECT_ACTION = '<td><a href="javascript:void(0)" data-action="select" data-path="{0}" class="btn btn-success btn-xs"><i class="fa fa-check"></i> 选择</a></td>',
        DATA_NAME_FOLDER = "fileView_folderCreation",
        HTML_FOLDER_CREATION = '\r\n<div class="btn-group">\r\n'
    + '<input class="form-control" placeholder="目录名称" type="text" style="width:98px;" />\r\n'
    + '</div>\r\n'
    + '<a class="btn btn-xs btn-success" data-action="confirmCreation">\r\n'
    + '<i class="fa fa-check"></i>\r\n'
    + '</a>\r\n'
    + '<a class="btn btn-xs btn-default" data-action="cancelCreation">\r\n'
    + '<i class="fa fa-times"></i>\r\n'
    + '</a>\r\n';


    var TYPE_USER = 0,
        TYPE_PUBLISH = 1,
        TYPE_PROJECT = 2;

    var DEFAULTS = {
        url : "/rest/file/list",
        listContainer: "#file_list",
        breadcrumb: "#breadcrumb",
        createFolderElement: "#action_create_folder",
        showDelete: true,
        showSelect: false,
        type:TYPE_USER
    };

    var DIALOG_DEFAULTS = {
        url: "/file/selection"
    };

    var fileList = window.FileList = function() {
        fileList.prototype.init.apply(this, arguments);
    };

    fileList.prototype = {
        init: function(option) {
            this.option = $.extend({}, DEFAULTS, option);
            this.path = "/";
            this.list = this.option.listContainer instanceof jQuery ? this.option.listContainer : $(this.option.listContainer);
            if(!this.option.data)
                this.option.data = {};

            if(this.option.actionPanelElement)
                this.actionPanelElement = $(this.option.actionPanelElement);

            if(this.option.createFolderElement) {
                this.createFolderElement = $(this.option.createFolderElement);
                this._initFolderCreation(this.createFolderElement);
            }
            this._updateBreadcrumb();
            this._initTypeSwitcher();

            var self = this;
            if(this.option.uploadFileElement) {
                $app.initFileUpload({
                    fileElementId: this.option.uploadFileElement,
                    dataCallback: function() {
                        return {
                            path: self.path,
                            checkStorage: false,
                            replace: true
                        }
                    },
                    callback: function(result) {
                        console.log(result)
                    }
                })
            }

            this.setType(this.type);
        },

        refreshList: function() {
            this.list.html("");
            var self = this;
            $app.blockUI();
            $app.ajax({
                url:$app.absolute(self.option.url),
                data: $.extend({}, this.option.data, {path: this.path, type: this.type}),
                method:"POST"
            }).done(function(r) {
                $app.unblockUI();
                var list = self.list;

                if(r && r.length) {
                    for(var i=0;i< r.length; i++) {
                        var html = create_record(r[i], self.option.showDelete, self.option.showSelect);

                        var $html = $(html);

                        $html.data('file', r[i]);

                        $html.appendTo(list);
                    }
                }

                list.find("[data-action='folder']").click(function() {
                    self._onFolderClick.call(self, this);
                });
                if(self.option.showDelete) {
                    list.find("[data-action='delete']").click(function() {
                        self._delete.call(self, $(this).attr("data-path"));
                    });
                }
                if(self.option.showSelect) {
                    list.find("[data-action='select']").click(function() {
                        self._select.call(self, $(this));
                    });
                }

                self._updateBreadcrumb();
            }).fail(function() {
                $app.unblockUI();
            });
        },

        setType: function(type) {
            if(type == this.type)
                return;

            this.type = type;
            this.path = "/";

            if(this.actionPanelElement) {
                if(this.type == TYPE_USER)
                    this.actionPanelElement.show();
                else
                    this.actionPanelElement.hide();
            }

            this.option.showDelete = this.type == TYPE_USER;

            this._updateSwitcher();
            this.refreshList();
        },

        _initTypeSwitcher: function() {
            this.switcheres = $("[data-file-type]");

            var self = this;
            this.switcheres.click(function() {
                var type = $(this).attr('data-file-type');
                self.setType(type);
            });
        },

        _updateSwitcher: function() {
            if(!this.switcheres)
                return;

            var self = this;
            this.switcheres.each(function() {
                var $this = $(this);
                var type = $this.attr("data-file-type");
                var activeClass = $this.attr("data-active-class");
                var inactiveClass = $this.attr("data-inactive-class");

                if(type == self.type) {
                    $this.removeClass(inactiveClass);
                    $this.addClass(activeClass);
                } else {
                    $this.removeClass(activeClass);
                    $this.addClass(inactiveClass);
                }
            });
        },

        _updateBreadcrumb: function () {
            if (!this.breadcrumb) {
                var html = '<ol class="breadcrumb" style="padding-bottom:0;margin-bottom:5px;background-color: white"></ol>';
                this.breadcrumb = $(html);
                if(this.option.breadcrumb) {
                    var $b = this.option.breadcrumb instanceof jQuery ? this.option.breadcrumb : $(this.option.breadcrumb);
                    $b.append(this.breadcrumb);
                }
            }

            this.breadcrumb.html("");
            this.breadcrumb.append(this._initBreadcrumbItem("根目录", true, "/"));

            var path = "";
            var dirs = this.path.split('/');
            for(var i=0;i<dirs.length;i++) {
                var dir = dirs[i];
                if(!dir)
                    continue;

                path = path + "/" + dir;

                this.breadcrumb.append(this._initBreadcrumbItem(dir, i<dirs.length-1, path));
            }
        },

        _initBreadcrumbItem: function (name, link, path) {
            var self = this;
            var item = link ? $('<li><a href="javascript:void(0)"><i class="fa fa-folder txt-color-blue"></i> ' + name + '</a></li>')
                : ('<li>' + name + '</li>');
            link && item.click(function () {
                self.path = path;
                self.refreshList();
            });
            return item;
        },

        _onFolderClick: function(folder) {
            this.path = $(folder).attr("data-path");
            this.refreshList();
        },

        _createFolder: function(name, element, inputArea) {
            if(!name)
                return;

            var self = this;
            $app.ajax({
                url: $app.absolute("/rest/file/mkdir"),
                data: {path: self.path + "/" + name},
                method:"POST"
            }).done(function(r) {
                inputArea.hide();
                $(element).show();
                self.refreshList();
            });
        },

        _delete: function(path) {
            if(!path)
                return;

            var self = this;
            $app.showConfirm({
                msg: '您确定要删除"'+name+'"吗？',
                confirmCallback: function() {
                    $app.ajax({
                        url: $app.absolute("/rest/file/delete"),
                        data: {path : path}
                    }).done(function(r) {
                        self.refreshList();
                    })
                }
            })
        },

        _select: function($a) {
            var file = $a.parent().parent().data('file');

            if(this.option.onSelect)
                this.option.onSelect(file);
        },

        _initFolderCreation: function(element) {
            var self = this;
            var inputArea = $(element).data(DATA_NAME_FOLDER);
            if (!inputArea) {
                inputArea = $(HTML_FOLDER_CREATION);
                $(element).data(DATA_NAME_FOLDER, inputArea);
                inputArea.insertAfter($(element));
                var $name = $(element).next("div").children("input");
                var $confirm = $(element).next("div").next("a");
                var $cancel = $confirm.next("a");

                $confirm.click(function () {
                    self._createFolder.apply(self, [$name.val(), element, inputArea]);
                })
                $cancel.click(function () {
                    inputArea.hide();
                    $(element).show();
                })
                inputArea.hide();
            }
            $(element).click(function () {
                var $name = $(element).next("div").children("input");
                $name.val("");
                $(this).hide();
                inputArea.show();
            })
        }
    };

    var fileDialog = window.FileDialog = function() {
        fileDialog.prototype.init.apply(this, arguments);
    };

    fileDialog.prototype = {
        init: function(option) {
            this.option = $.extend({}, DIALOG_DEFAULTS, option);
            this.loaded = false;
            var self = this;
            this.dialogOption = {
                url: $app.absolute(this.option.url),
                width: 690,
                height: 450,
                title: '选择文件',
                loaded: function($d) {
                    if(!self.fileList) {
                        self.fileList = new FileList({
                            listContainer: $d.find("#file_list"),
                            breadcrumb: $d.find("#breadcrumb"),
                            showDelete: false,
                            showSelect: true,
                            onSelect: function(file) {
                                self.loaded = true;
                                if(self.option.onSelect)
                                    self.option.onSelect(file);
                            }
                        });
                    }

                    self.fileList.list = $d.find("#file_list");
                    self.fileList.refreshList();
                },
                confirmCallback: function($d) {
                    //$d.find(self.option.detailForm).submit();
                }
            };
            this.dialog = $app.createDialog(this.dialogOption);
        },

        open: function() {
            $app.showDialog(this.dialog,this.dialogOption);
        },

        close: function() {
            $app.closeDialog(this.dialog);
        }
    };

    function create_record(fileRecord, showDelete, showSelect) {
        var html = "<tr>";
        var path = fileRecord.path;
        if(path.lastIndexOf("/") != path.length-1)
            path += "/";
        path += fileRecord.name;

        if(fileRecord.type == 0) {
            var link = "<a href='javascript:void(0)' class='btn-link' data-action='folder' data-path='" +path+ "'>" + fileRecord.name + "</a>";
            html += FOLDER_TEMPLATE.replace("{name}", link);
            html += "<td></td>";
        }
        else {
            var name = fileRecord.name;
            html += FILE_TEMPLATE.replace("{name}", name);
            html += "<td>" + fileRecord.sizeLabel + "</td>";
        }

        html += "<td>" + fileRecord.updateTimeStr + "</td>"

        if(showDelete)
            html += $app.formatString(DELETE_ACTION, path);

        if(showSelect) {
            if(fileRecord.type != 0) {
                html += $app.formatString(SELECT_ACTION, path);
            } else {
                html += "<td></td>";
            }
        }

        html += "</tr>";
        return html;
    }
})(jQuery)