(function($) {
    'use strict'

    app_inits.push(function() {
        loadProjects();
        loadAnalyses();

        $("#pager-previous").click(function() {
            if(currentPage>0)
                currentPage-=1;
            else
                return;

            loadProjects();
        });

        $("#pager-next").click(function() {
            if(currentPage<pages-1)
                currentPage+=1;
            else
                return;

            loadProjects();
        })
    });

    var currentPage = 0;
    var pages = 0;

    function loadProjects() {
        $app.blockUI({target: $("#project-container")});

        $app.ajax({
            url: $app.absolute("/rest/project/list?page="+currentPage)
        }).done(function(r) {
            $app.unblockUI($("#project-container"));

            if(r.data)
                r = r.data;

            console.log(r);

            currentPage = r.number;
            pages = r.totalPages;

            var list = $("#project-container");
            list.html('');
            var template = $("#template-project").html();
            //updateList(r);

            for(var i=0;i< r.content.length; i++) {
                var project = r.content[i];
                var html = template.replace("{title}", project.title)
                    .replace("{projectId}", project.id)
                    .replace("{createdTime}", project.updateTimeStr);

                $(html).appendTo(list);
            }
        });
    }

    function loadAnalyses() {
        $app.blockUI({target: $("#analyses-container")});

        $app.ajax({
            url: $app.absolute("/rest/project/recentlyAnalyses")
        }).done(function(r) {
            $app.unblockUI($("#analyses-container"));
            console.log(r);

            var list = $("#analyses-container");
            var template = $("#template-analyses").html();
            //updateList(r);

            for(var i=0;i< r.content.length; i++) {
                var analysis = r.content[i];
                var statusText = '<span class="label label-md label-danger">未知</span>';
                var reportLink = '';
                var resultLink = '';

                if(analysis.status == 'WAIT') {
                    statusText = '<span class="label label-md label-info">等待中</span>';
                } else if(analysis.status == 'PROGRESS') {
                    statusText = '<span class="label label-md label-primary">进行中</span>';
                } else if(analysis.status == 'FINISH') {
                    statusText = '<span class="label label-md label-success">已完成</span>';

                    reportLink = '<a href="'+ $app.absolute("/report/read?analysesId="+analysis.id) +'" class="btn default btn-xs red-stripe">查看报告</a>';
                    resultLink = '<a href="http://report.geneapps.cn/'+ analysis.id +'.zip" class="btn default btn-xs red-stripe">下载结果</a>';
                } else if(analysis.status == 'FAIL') {
                    statusText = '<span class="label label-md label-danger">失败</span>';
                }

                var html = template.replace("{title}", analysis.title)
                    .replace("{analysesId}", analysis.id)
                    .replace("{updateTime}", analysis.updateTimeStr)
                    .replace("{status}", statusText)
                    .replace("{report}", reportLink)
                    .replace("{result}", resultLink);;

                $(html).appendTo(list);
            }
        });
    }

    function updateList(data) {
        var list = $("#project_list");
        var template = $("#template-project").html();
        var analysisTemplate = $("#template-analysis").html();

        for(var i=0;i<data.content.length;i++) {
            var project = data.content[i];
            var html = template.replace("{title}", project.title)
                .replace("{name}", project.title)
                .replace("{createTime}", project.updateTimeStr);

            var $html = $(html);

            $html.appendTo(list);
            $("<br>").appendTo(list);

            var $analysesList = $html.find("[data-action='analyses']");
            console.log($analysesList)

            $analysesList.html("<div class='row'><div class='col-md-12 text-center'>加载中...</div></div>");

            var f1 = function() {
                console.log(i);
            }

            $app.ajax({
                url: $app.absolute("/rest/project/analyses"),
                data: {projectId: project.id},
                tag: {index:i, element:$analysesList}
            }).done(function(r, tag) {
                console.log(r);
                tag.element.html('');

                for(var j=0;j< r.data.length; j++) {
                    var analysis = r.data[j];

                    var statusText = '<span class="label label-md label-danger">未知</span>';
                    var reportLink = '<span class="label label-md label-info">暂时不能查看</span>';
                    var resultLink = '<span class="label label-md label-info">暂时不能查看</span>';

                    if(analysis.status == 'WAIT') {
                        statusText = '<span class="label label-md label-info">等待中</span>';
                    } else if(analysis.status == 'PROGRESS') {
                        statusText = '<span class="label label-md label-primary">进行中</span>';
                    } else if(analysis.status == 'FINISH') {
                        statusText = '<span class="label label-md label-success">已完成</span>';

                        reportLink = '<a href="'+ $app.absolute("/report/read?analysesId="+analysis.id) +'" class="btn default btn-xs red-stripe">点击查看</a>';
                        resultLink = '<a href="http://report.geneapps.cn/"'+ analysis.id +'.zip" class="btn default btn-xs red-stripe">点击查看</a>';
                    } else if(analysis.status == 'FAIL') {
                        statusText = '<span class="label label-md label-danger">失败</span>';
                    }

                    var analysisHtml = analysisTemplate.replace("{name}", analysis.title)
                        .replace("{createTime}", analysis.updateTimeStr)
                        .replace("{status}", statusText)
                        .replace("{report}", reportLink)
                        .replace("{result}", resultLink);
                    var $analysis = $(analysisHtml);
                    $analysis.appendTo(tag.element);
                }
            });
        }

        /*var table = $("#list_body");

        table.html('');

        for(var i=0;i<list.content.length;i++) {
            var project = list.content[i];
            var row = "<tr>"
                +"<td>" + (i+1) + "</td>"
                +"<td><a href='javascript:;' data-action='detail' data-project-id='" + project.id + "'>" + project.title + "</a></td>"
                +"<td>" + getDescription(project.species, Constants.species) + "</td>"
                +"<td>" + getDescription(project.sequencingMachines, Constants.sequencingMachines) + "</td>"
                +"<td>" + getDescription(project.processingMethod, Constants.processingMethod) + "</td>"
                +"<td>" + getDescription(project.speciesParts, Constants.speciesParts) + "</td>"
                +"<td>" + project.status + "</td>"
                +"<td>" + project.updateTimeStr + "</td>"
                +"</tr>";

            $(row).appendTo(table);
        }

        table.find("[data-action='detail']").click(function() {
            var id = $(this).attr('data-project-id');

            window.location.href = $app.absolute("/project/detail?id="+id);
        })*/
    }

    function getDescription(value, dataSource) {
        for(var i=0;i<dataSource.length;i++) {
            if(dataSource[i].value == value)
                return dataSource[i].name;
        }
        return "";
    }
})(jQuery);