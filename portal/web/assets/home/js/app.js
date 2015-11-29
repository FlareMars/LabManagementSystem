(function($){
    'use strict'
    var $app = window.$app = {
            options: {
                contextRootUrl: '/'
            }
        },
        $msgBox = $("<div style='padding-left:10px;padding-top:20px;'><p></p></div>"),
        $confirmBox = $("<div style='padding-left:10px;padding-top:20px;'><p></p></div>"),
        $progress = $("<div style='text-align:center;padding-top:20px;'><p></p></div>"),
        $dialogs = {},
        ALERT_DEFAULTS = {
            alertClass: 'alert-danger'
        },
        dialog_initial_content = "<p><i class='fa fa-spinner fa-spin'></i>&nbsp;加载中...</p>";

    $app.init = function(options) {
        $.extend($app.options, options);
        init_urls();
        init_dialogs();
        ALERT_DEFAULTS.message = $app.resources.msg_systemError;

        if(window.app_inits) {
            for(var i=0;i<window.app_inits.length;i++)
                window.app_inits[i]();
        }
    }

    $app.absolute = function(relativeUrl, parameters) {
        var url = relativeUrl.charAt(0)=='/'?$app.contextRootUrl+relativeUrl.substr(1):$app.contextRootUrl+relativeUrl;
        if(!parameters)
            return url;

        var query = url.indexOf('?')>0?"&":"?";
        for(var name in parameters) {
            if(query.length>1)
                query+="&";
            query += name + "=" + encodeURIComponent(parameters[name]);
        }

        return url + query;
    }

    $app.inlineMessage = function (options) {
        var setting = $.extend({}, ALERT_DEFAULTS, options);
        if (!setting.placement && !setting.placementId) {
            $app.showMessage(options.message);
            return;
        }

        var $placement = setting.placement ? (setting.placement instanceof jQuery?setting.placement:$(setting.placement))
            : $("#" + setting.placementId);

        var title = setting.title ? "<strong>" + setting.title + "</strong> " : "";
        $placement.html("<div class=\"alert alert-danger\">"
            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>"
            + title + setting.message
            + "</div>");
    }

    $app.showMessage = function (option, c) {
        var msg = option && option.msg?option.msg : (typeof option == 'string'? option:"");
        var callback = option && option.callback ? option.callback:c;
        var autoClose = option && option.autoClose ? option.autoclose : true;
        $msgBox.find("p").html(msg);
        if (callback) {
            $msgBox.dialog('option', 'buttons', [{
                html: "<i class='fa fa-check'></i>&nbsp; " + $app.resources.btn_confirm,
                "class": "btn btn-primary",
                click: function () {
                    if(autoClose)
                        $msgBox.dialog('close');
                    callback();
                }
            }]);
        }
        $msgBox.dialog('open');
    }

    $app.closeMessage = function () {
        $msgBox.dialog('close');
    }

    $app.showConfirm = function (option) {
        var msg = option && option.msg ? option.msg : (typeof option == 'string' ? option : "");
        var title = option && option.title ? option.title : undefined;
        var buttons = option ? option.buttons : undefined;
        var autoClose = option && option.autoClose ? option.autoclose : true;

        $confirmBox.find("p").html(msg);

        if (title)
            $confirmBox.dialog('option', 'title', title);

        if (buttons)
            $confirmBox.dialog('option', 'buttons', buttons);
        else {
            $confirmBox.dialog('option', 'buttons', [{
                html: "<i class='fa fa-check'></i>&nbsp; " + $app.resources.btn_confirm,
                "class": "btn btn-primary",
                click: function () {
                    if(autoClose)
                        $(this).dialog("close");
                    if (option.confirmCallback)
                        option.confirmCallback();
                }
            }, {
                html: "<i class='fa fa-times'></i>&nbsp; " + $app.resources.btn_cancel,
                "class": "btn btn-default",
                click: function () {
                    if(autoClose)
                        $(this).dialog("close");
                    if (option.cancelCallback)
                        option.cancelCallback();
                }
            }]);
        }

        $confirmBox.dialog('open');
    };

    $app.closeConfirm = function () {
        $confirmBox.dialog('close');
    };

    $app.showDialog = function(nameOrDialog, option) {
        //if(!$dialogs[name])
        //    $dialogs[name] = create_dialog(option);

        var $d;//$dialogs[name];
        if(typeof nameOrDialog == "string") {
            $dialogs[nameOrDialog] = create_dialog(option);
            $d = $dialogs[nameOrDialog];
        }
        else
            $d = nameOrDialog;

        $d.dialog('open');

        if(option.url) {
            $d.html(dialog_initial_content);
            $d.data('dialog_loading', true);
            $app.ajax({
                url: option.url,
                data: option.data,
                dataType: 'html',
                success: function(result) {
                    $d.data('dialog_loading', false);
                    $d.html(result);

                    if(option.loaded)
                        option.loaded($d);
                }
            })
        } else if(option.content) {
            $d.html(option.content);
            if(option.loaded)
                option.loaded($d);
        }

        return $d;
    };

    $app.closeDialog = function(nameOrDialog) {
        if(typeof nameOrDialog == "string") {
            if($dialogs[nameOrDialog])
                $dialogs[nameOrDialog].dialog('close');
        }
        else
            nameOrDialog.dialog('close');
    };

    $app.showProgress = function(options) {
        var $content = $progress.find("p");
        if (typeof (options) == "string" || (options && options.content)) {
            var content = typeof (options) == "string" ? options : options.content;
            $content.html(content);
        } else
            $content.html($app.resources.label_inprogress);
        if (options && options.title)
            $progress.dialog('option', 'title', option.title);
        $progress.dialog('open');
    };

    $app.closeProgress = function() {
        $progress.dialog('close');
    };

    $app.ajax = function(options) {
        var content = options.progressContent || $app.resources.label_inprogress;

        if(!options.url) {
            alert('please provide url to perform a server ajax call');
            return;
        }
        var method = options.method?options.method:'get';
        var dataType = options.dataType?options.dataType:'json';
        var cache = options.cache?options.cache:false;
        var tag = options.tag ? options.tag : {};

        var ajaxOptions = {
            url: options.url,
            data: options.data,
            dataType: dataType,
            cache: cache,
            type: method
        };

        if(options.contentType)
            ajaxOptions.contentType = options.contentType;
        if(options.processData == false)
            ajaxOptions.processData = options.processData;

        return $.Deferred(function(deferred) {
            $.ajax(ajaxOptions).done(function(result) {
                if(handle_ajax_result(result,options))
                    deferred.resolve(result, tag);
                else
                    deferred.reject(result, tag);
            }).fail(function(request, e, t) {
                $app.showMessage($app.resources.msg_systemError + ":" + t);
                var callback = options.error||options.errorCallback;
                if(callback)
                    callback(e);
                deferred.reject(request, e, t);
            });
        }).promise();
    };

    $app.ajaxSubmit = function(option) {
        option = !option?true:option;
        var hint = option.hint ? option.hint : $app.resources.label_inprogress;
        var dataType = option.dataType?option.dataType:'json';
        var blockFunc,unblockFunc;
        if(!option.block || option.block=='blockUI') {
            blockFunc = function() {
                $app.blockUI({boxed:true,message:'系统处理中...'});
            };
            unblockFunc = $app.unblockUI;
        }
        else {
            blockFunc = function() {
                $app.showProgress(hint);
            };
            unblockFunc = $app.closeProgress;
        }
        if(blockFunc) blockFunc();

        var $f = option.form instanceof jQuery? option.form : $("#"+option.form);

        return $.Deferred(function(deferred) {
            $f.ajaxSubmit({
                type: "post",
                url: option.url,
                dataType: dataType,
                success: function (result) {
                    if(unblockFunc) unblockFunc();
                    if(handle_ajax_result(result,option))
                        deferred.resolve(result);
                    else
                        deferred.reject(result);
                },
                error: function (request, e, t) {
                    if(unblockFunc) unblockFunc();
                    $app.showMessage($app.resources.msg_systemError + ":" + t);
                    var callback = option.error||option.errorCallback;
                    if(callback)
                        callback(e);

                    deferred.reject(e);
                }
            })
        }).promise();
    }

    $app.htmlEncode = function(value) {
        return $('<div/>').text(value).html();
    }

    $app.htmlDecode = function(value) {
        return $('<div/>').html(value).text();
    }
    
    $app.regular_ajax_handler = function(process) {
        var option = process.option ? process.option : {};
        var msg = option && option.successMessage ? option.successMessage : $app.resources.msg_opSuccess;
        var callback = function() {
            if (option.relocateUrl) {
                window.location.href = option.relocateUrl;
                return;
            }
            if (option.reload) {
                window.location.reload();
            }
            var sc = option.success || option.successCallback;
            if (sc)
                sc(process.result);
        };

        if(option.successMsg) {
            $app.showMessage(msg, function () {
                $app.closeMessage();
                callback();
            });
        } else
        	callback();
    }

    $app.error_ajax_handler = function(process) {
        var option = process.option ? process.option : {};
        if(process.result.error) {
            $app.showMessage(process.result.error);
            process.cancel = true;
        }
        if(process.result.errorCode) {
            var error = $app.errors && $app.errors[process.result.errorCode] ? $app.errors[process.result.errorCode] : $app.resources.msg_systemError;
            $app.showMessage(error);
            process.cancel = true;
        }
        if(process.result.statusCode && process.result.statusCode < 0) {
            var error = process.result.message;
            if(!error)
                error = $app.errors && $app.errors[process.result.statusCode] ? $app.errors[process.result.statusCode] : $app.resources.msg_systemError;
            $app.showMessage(error);
            process.cancel = true;
        }
        var callback = option.error||option.errorCallback;
        if(callback)
            callback(process.result);
    }

    $app.blockUI = function (options) {
        var options = $.extend(true, {}, options);
        var html = '';
        if(options.boxed==undefined)
            options.boxed = true;
        if (options.iconOnly) {
            html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><img src="' + $app.getImageRootUrl() + 'loading-spinner-grey.gif" align=""></div>';
        } else if (options.textOnly) {
            html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><span>&nbsp;&nbsp;' + (options.message ? options.message : 'LOADING...') + '</span></div>';
        } else {
            html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><img src="' + $app.getImageRootUrl() + 'loading-spinner-grey.gif" align=""><span>&nbsp;&nbsp;' + (options.message ? options.message : 'LOADING...') + '</span></div>';
        }

        if (options.target) { // element blocking
            var el = options.target instanceof jQuery? options.target:$(options.target);
            if (el.height() <= ($(window).height())) {
                options.cenrerY = true;
            }
            el.block({
                message: html,
                baseZ: options.zIndex ? options.zIndex : 99999,
                centerY: options.cenrerY != undefined ? options.cenrerY : false,
                css: {
                    top: '10%',
                    border: '0',
                    padding: '0',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: options.overlayColor ? options.overlayColor : '#000',
                    opacity: options.boxed ? 0.05 : 0.1,
                    cursor: 'wait'
                }
            });
        } else { // page blocking
            $.blockUI({
                message: html,
                baseZ: options.zIndex ? options.zIndex : 99999,
                css: {
                    border: '0',
                    padding: '0',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: options.overlayColor ? options.overlayColor : '#000',
                    opacity: options.boxed ? 0.05 : 0.1,
                    cursor: 'wait'
                }
            });
        }
    }

    $app.unblockUI = function (target) {
        if (target) {
            var $target = target instanceof jQuery? target : $(target);
            $target.unblock({
                onUnblock: function () {
                    $target.css('position', '');
                    $target.css('zoom', '');
                }
            });
        } else {
            $.unblockUI();
        }
    }

    $app.getImageRootUrl = function() {
        return $app.contextRootUrl+"assets/images/";
    }

    $app.formatString = function() {
        if( arguments.length == 0 )
            return null;

        var str = arguments[0];
        for(var i=1;i<arguments.length;i++) {
            var re = new RegExp('\\{' + (i-1) + '\\}','gm');
            str = str.replace(re, arguments[i]);
        }
        return str;
    }

    $app.getElementData = function() {
        if(arguments.length==0)
            return {};
        var el;
        if(arguments.length==1)
            el = arguments[0] instanceof jQuery ? arguments[0] : $(arguments[0]);
        else {
            var container = arguments[0] instanceof jQuery? arguments[0] : $(arguments[0]);
            el = container.find(arguments[1]);
        }
        var data = {};
        for(var i=0;i<el[0].attributes.length;i++) {
            var name = el[0].attributes[i].name;
            if(name.length>5&&name.indexOf('data-')==0) {
                var attrName = convertNamingRule(name.substr(5));
                data[attrName] = el[0].attributes[i].value;
            }
        }
        return data;
    }

    function convertNamingRule(name) {
        var parts = name.split('-');
        var result = parts[0];
        for(var i=1;i<parts.length;i++)
            result+=firstUpper(parts[i]);
        return result;
    }

    function firstUpper(str) {
        if(!str || typeof(str)!='string')
            return undefined;
        if(str.length<=1) return str.toUpperCase();
        else {
            return str.substr(0,1).toUpperCase()+str.substr(1);
        }
    }

    var PAGER_DEFAULTS = {
        class: 'pagination',
        disabledClass: 'disabled',
        activeClass: 'active',
        showPrevious: true,
        showNext: true,
        showFirst: true,
        showLast: true,
        previousHint: '上一页',
        nextHint: '下一页',
        firstHint: '第一页',
        lastHint: '最后一页',
        previousContent:'<i class="fa fa-angle-left"></i>',
        nextContent:'<i class="fa fa-angle-right"></i>',
        firstContent:'<i class="fa fa-angle-double-left"></i>',
        lastContent:'<i class="fa fa-angle-double-right"></i>',
        summaryContent:'总记录条数：{0}条；分{1}页显示；这是第{2}页',
        currentPage:0,
        pageSize:10,
        totalRecords:0,
        maxPagers:5
    }

    var pager = window.Pager = function() {
        pager.prototype.init.apply(this, arguments);
    };

    pager.prototype = {
        init: function(option) {
            this.option = $.extend({}, PAGER_DEFAULTS, option);
            this.pageSize = this.option.pageSize<0?0:this.option.pageSize;
            this.currentPage = this.option.currentPage<0?0:this.option.currentPage;
            this.totalRecords = this.option.totalRecords<0?0:this.option.totalRecords;

            var $element = $(this.option.element);
            if($element.is('ul'))
                this.$element = $element;
            else {
                this.$element = $("<ul></ul>");
                this.$element.appendTo($element);
            }
            this.$element.addClass(this.option.class);

            this._update();
        },

        update: function(o) {
            if(!o && (!o.page||!o.count))
                return;
            if(o.page || o.page == '0')
                this.currentPage = parseInt(o.page);
            if(o.count)
                this.totalRecords = parseInt(o.count);
            if(o.pageSize)
                this.pageSize = parseInt(o.pageSize);
            this._update();
        },

        _update: function() {
            var o = this.option;
            if(this.totalRecords<0) this.totalRecords=0;
            if(this.pageSize<0) this.pageSize = o.pageSize;
            this.pages = Math.ceil(this.totalRecords/this.pageSize);
            if(this.pages==0) this.pages = 1;
            if(this.currentPage<0) this.currentPage=0;
            if(this.currentPage>=this.pages) this.currentPage = this.pages-1;
            this.$element.html('');
            if(o.showFirst)
                this._createItem(0, o.firstContent, this.currentPage==0, false, o.firstHint);
            if(o.showPrevious)
                this._createItem(this.currentPage-1, o.previousContent, this.currentPage==0, false, o.previousHint);

            var pagers = this.pages> o.maxPagers? o.maxPagers:this.pages;
            var start = this.currentPage - Math.floor(pagers/2);
            if(start<0)
                start=0;
            if(start+pagers>this.pages)
                start -= (start+pagers)-this.pages;
            for(var i=start;i<start+pagers;i++)
                this._createItem(i,i+1,false,this.currentPage==i);

            if(o.showNext)
                this._createItem(this.currentPage+1, o.nextContent, this.currentPage>=(this.pages-1), false, o.nextHint);
            if(o.showLast)
                this._createItem(this.pages-1, o.lastContent, this.currentPage>=(this.pages-1), false, o.lastHint);

            if(o.summaryElement) {
                var el = $(o.summaryElement);
                var p = this.currentPage + 1;
                el.html($app.formatString(o.summaryContent, this.totalRecords, this.pages, p));
            }
        },

        _createItem: function(pageIndex, content, disabled, current, hint) {
            var wrapper = $("<li></li>"), link = $('<a href="javascript:void(0)"></a>');
            link.appendTo(wrapper);
            wrapper.appendTo(this.$element);
            if(disabled)
                wrapper.addClass(this.option.disabledClass);
            link.attr('page-index', pageIndex);
            if(current) {
                wrapper.addClass(this.option.activeClass);
                link.html(content+" <span class='sr-only'>(current)</span>");
            } else
                link.html(content);
            if(hint)
                link.attr('title', hint);

            var self = this;
            link.click(function() {
                var idx = $(this).attr('page-index');
                if(idx<0||idx>=self.pages||idx==self.currentPage)
                    return;
                if(self.option.pageChanged)
                    self.option.pageChanged(idx);
            });
        }
    }

    var ORDER_DEFAULTS = {
        activeTitle:'<a href="javascript:void(0)" class="order-link">{0}&nbsp;{1}</a>',
        title:'<a href="javascript:void(0)" class="order-link">{0}</a>',
        ascHtml:'<i class="fa fa-caret-up"></i>',
        descHtml:'<i class="fa fa-caret-down"></i>',
        asc:'a',
        desc:'d'
    };

    var order = window.OrderTable = function() {
        order.prototype.init.apply(this, arguments);
    };

    order.prototype = {
        init: function (option) {
            this.option = $.extend({}, ORDER_DEFAULTS, option);

            if(this.option.element) {
                this.$element = this.option.element instanceof jQuery ? this.option.element : $(this.option.element);
                this.update();
            }
        },

        update: function(option) {
            this.columns = [];
            if(option.element)
                this.$element = option.element instanceof jQuery?option.element:$(option.element);
            if(option.orderColumn)
                this.orderColumn = option.orderColumn;
            if(option.orderDirection)
                this.orderDirection = option.orderDirection;
            var self = this;
            this.$element.find('th[data-order]').each(function() {
                var $c = $(this);
                var content = $c.html();
                var name = $c.attr('data-order');
                var column = {
                    name: name,
                    content: content,
                    direction: self.orderColumn==name?self.orderDirection:self.option.asc
                };
                self.columns.push(column);

                if(self.orderColumn==name) {
                    var indicator = self.orderDirection=='d'?self.option.descHtml:self.option.ascHtml;
                    $c.html($app.formatString(self.option.activeTitle, content, indicator));
                } else {
                    $c.html($app.formatString(self.option.title, content));
                }
                $c.on('click', function() {
                    self._updateColumn.call(self, column);
                });
            });
        },

        _updateColumn: function(column){
            var direction = column.direction;
            if(this.orderColumn) {
                var previous = this._getColumn(this.orderColumn);
                if(this.orderColumn!=column.name)
                    this.$element.find("[data-order='"+previous.name+"']").html($app.formatString(this.option.title, previous.content));
                else
                    direction = this.orderDirection&&this.orderDirection==this.option.asc?this.option.desc:this.option.asc;
            }
            var indicator = direction=='d'?this.option.descHtml:this.option.ascHtml;
            this.$element.find("[data-order='"+column.name+"']").html($app.formatString(this.option.activeTitle, column.content, indicator));
            this.orderColumn = column.name;
            this.orderDirection = direction;

            this._orderChanged(this);
        },

        _reset: function() {
            for(var column in this.columns)
                column.$element.html($app.formatString(this.option.title, content));
        },

        _orderChanged: function(obj) {
            if(this.option.orderChanged)
                this.option.orderChanged(obj);
        },

        _getColumn: function(name) {
            for(var i=0;i<this.columns.length;i++) {
                var c = this.columns[i];
                if(c.name==name)
                    return c;
            }
            return undefined;
        }
    }

    $app.ajaxHandlers = [$app.error_ajax_handler, $app.regular_ajax_handler];

    $app.DEFAULT_DATEPICKER_OPTIONS = {
        language: 'zh-CN',
        format:'yyyy-mm-dd'
    };

    $app.createDialog = function(option) {
        return create_dialog(option)
    }

    function handle_ajax_result(result, option) {
        var process = {};
        process.option = option;
        process.result = result;
        process.cancel = false;

        for(var i=0;i<$app.ajaxHandlers.length;i++) {
            if(process.cancel)
                return false;
            var handler = $app.ajaxHandlers[i];
            handler(process);
        }
        return true;
    }

    function init_urls() {
        $app.contextRootUrl = $app.options.contextRootUrl;
        if(!$app.contextRootUrl || $app.contextRootUrl.lastIndexOf('/') != $app.contextRootUrl.length-1)
            $app.contextRootUrl += '/';
        if(!$app.urls)
        	$app.urls = {};
        if($app.onInitUrls)
        	$app.onInitUrls($app.urls);
    }

    function init_dialogs() {
        $msgBox.dialog({
            autoOpen: false,
            maxWidth: 600,
            resizable: false,
            modal: true,
            title: "<i class='fa fa-bell text-info'></i>&nbsp;&nbsp;"+$app.resources.label_systemInfo,
            buttons: [{
                html: "<i class='fa fa-check'></i>&nbsp; " + $app.resources.btn_confirm,
                "class": "btn btn-primary",
                click: function () {
                    $msgBox.dialog('close');
                }
            }]
        });

        $confirmBox.dialog({
            dialogClass: "no-close",
            autoOpen: false,
            width: 600,
            resizable: true,
            modal: true,
            title: "<i class='fa fa-question-circle text-info'></i>&nbsp;&nbsp;"+$app.resources.label_confirm
        });

        $progress.dialog({
            dialogClass: "no-close",
            autoOpen: false,
            maxWidth: 350,
            height:120,
            resizable: false,
            modal: true,
            title: "<i class='fa fa-spinner fa-spin'></i>&nbsp;&nbsp;"+$app.resources.label_progress
        });
    }

    function create_dialog(option) {
        var $dlg = $(dialog_initial_content);

        var dlgOption = {
            autoOpen: false,
            resizable: false,
            model: true,
            title: "<i class='fa fa-tasks text-info'></i>&nbsp;&nbsp;"
        };

        if(option.title)
            dlgOption.title += option.title;
        if(option.width) dlgOption.width = option.width;
        if(option.height) dlgOption.height = option.height;
        if(option.maxWidth) dlgOption.maxWidth = option.maxWidth;
        if(option.maxHeight) dlgOption.maxHeight = option.maxHeight;
        if(option.buttons)
            dlgOption.buttons = option.buttons;
        else
            dlgOption.buttons = [{
                html: "<i class='fa fa-check'></i>&nbsp; " + $app.resources.btn_confirm,
                "class": "btn btn-primary",
                click: function () {
                    if($dlg.data('dialog_loading'))
                        return;
                    if (option.confirmCallback)
                        option.confirmCallback($dlg);
                }
            }, {
                html: "<i class='fa fa-times'></i>&nbsp; " + $app.resources.btn_cancel,
                "class": "btn btn-default",
                click: function () {
                    if (option.cancelCallback)
                        option.cancelCallback($dlg);
                    else
                        $(this).dialog("close");
                }
            }];

        $dlg.dialog(dlgOption);
        return $dlg;
    }
})(jQuery);