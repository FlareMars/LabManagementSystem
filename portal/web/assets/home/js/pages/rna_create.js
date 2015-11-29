(function() {
    app_inits.push(function() {

        $("#reference_species").select2();
        $("#reference_version").select2();

        init_server_variables();
    });

    function convert_files(files) {
        var data = files.data ? files.data : files;

        var result = [];
        for(var i=0;i<data.length;i++) {
            var f = data[i];

            result.push({
                name : f.id, //f.name,
                path: f.path + f.name,
                fileType: f.fileType.id,
                source: 1,
                userId: f.userId,
                size: f.size
            });
        }
        return result;
    }

    function init_server_variables() {
        $app.blockUI({message: '参数初始化中，请稍后...'});
        $app.ajax({
            url: $app.absolute('rest/file/list'),
            data: {
                type: '1',
                path: '/参考序列/大豆/ensembl23/'
            },
            method: 'post'
        }).done(function(r) {
            var option = {
                references : []
            };

            option.references.push({
                name: 'ensembl23',
                data: convert_files(r)
            });

            $app.ajax({
                url: $app.absolute('rest/file/list'),
                data: {
                    type: '1',
                    path: '/参考序列/大豆/smalltest/'
                },
                method: 'post'
            }).done(function(r1) {
                option.references.push({
                    name: 'Small Scale Test',
                    data: convert_files(r1)
                });

                init_view(option);
                $app.unblockUI();
            }).fail(function() {
                $app.showMessage('参数没有正确加载，请重新刷新页面.');
            });
        }).fail(function() {
            $app.showMessage('参数没有正确加载，请重新刷新页面.');
        })
    }

    function init_view(option) {
        var view = new RNAView(option);

        init_wizard(view);
        init_actions(view);
    }

    function init_actions(view) {
        /*$("#group_switcher").find("[data-toggle='tab']").click(function() {
            var tab = $(this).attr('data-tab');

            if(tab == 'group') {
                $("#action_create_group").show();
            } else {
                $("#action_create_group").hide();
            }

            if(tab == 'difference') {
                $("#action_create_difference").show();
            } else {
                $("#action_create_difference").hide();
            }
        });
        $("#action_create_difference").hide();*/

        $("#action_submit").click(function() {
            console.log(view.model)
            var obj = view.model.convertForSave();

            console.log(obj);
            $app.blockUI({message: '保存中，请稍后'});
            $app.ajax({
                url: $app.absolute('/rest/project/analyses/create'),
                data: JSON.stringify(obj),
                method: 'POST',
                contentType: "application/json",
                processData: false
            }).done(function(r) {
                $app.unblockUI();

                window.location.href = $app.absolute("/project/list");
            }).fail(function() {
                $app.unblockUI();
            });
        });
    }

    function init_wizard(view) {
        var handleTitle = function(tab, navigation, index) {
            var total = navigation.find('li').length;
            var current = index + 1;
            // set wizard title
            $('.step-title', $('#wizard')).text('Step ' + (index + 1) + ' of ' + total);
            // set done steps
            jQuery('li', $('#wizard')).removeClass("done");
            var li_list = navigation.find('li');
            for (var i = 0; i < index; i++) {
                jQuery(li_list[i]).addClass("done");
            }

            if (current == 1) {
                $('#form_wizard_1').find('.button-previous').hide();
            } else {
                $('#form_wizard_1').find('.button-previous').show();
            }

            if (current >= total) {
                $('#wizard').find('.button-next').hide();
                $('#wizard').find('.button-submit').show();
            } else {
                $('#wizard').find('.button-next').show();
                $('#wizard').find('.button-submit').hide();
            }
            Metronic.scrollTo($('.page-title'));
        };

        $('#wizard').bootstrapWizard({
            'nextSelector': '.button-next',
            'previousSelector': '.button-previous',
            onTabClick: function (tab, navigation, index, clickedIndex) {
                if(clickedIndex>index+1)
                    return false;
                if(clickedIndex>index && !view.validate(index)) {
                    return false;
                }

                handleTitle(tab, navigation, clickedIndex);
                return true;
            },
            onInit: function(tab, navigation, index) {
                handleTitle(tab, navigation, index)
            },
            onNext: function (tab, navigation, index) {
                if(!view.validate(index-1))
                    return false;
                handleTitle(tab, navigation, index);
            },
            onPrevious: function (tab, navigation, index) {
                handleTitle(tab, navigation, index);
            },
            onTabShow: function (tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                var $percent = (current / total) * 100;
                $('#wizard').find('.progress-bar').css({
                    width: $percent + '%'
                });
                view.initStepView(index);
            }
        });
    }
})(jQuery);