(function($) {
    'use strict'

    app_inits.push(function() {
        pager = new Pager({
            pageSize: 5,
            currentPage: 0,
            totalRecords: 0,
            element: "#pager",
            pageChanged: function(page) {
                refresh(page);
            }
        });
        refresh(0);
    });

    var pager;

    function refresh(page) {
        var url = $app.absolute("/rest/project/list?page="+page+"&d="+new Date());
        console.log(url)
        $app.ajax({
            url: url
        }).done(function(r) {
            console.log(r);

            if(r.data)
                r = r.data;

            pager.update({
                page: r.number,
                count: r.totalElements
            });

            updateList(r);
        });
    }

    function updateList(data) {
        //var analysisTemplate = $("#template-analysis").html();
        var projectTemplate = $("#template-project").html();
        var list = $("#project-list");

        list.html('');

        for(var i=0;i<data.content.length;i++) {
            var project = data.content[i];

            var statusText = project.status == 1 ? '有效' : project.status == 0 ? '删除' : '未知';

            var projectHtml = projectTemplate.replace('{title}', project.title)
                .replace('{projectId}', project.id)
                .replace('{status}', statusText)
                .replace('{createTime}', project.updateTimeStr)
                .replace('{updateTime}', project.updateTimeStr)
                .replace('{species}', getDescription(project.species, Constants.species))
                .replace('{sequencingMachines}', getDescription(project.sequencingMachines, Constants.sequencingMachines))
                .replace('{processingMethod}', getDescription(project.processingMethod, Constants.processingMethod))
                .replace('{speciesParts}', getDescription(project.speciesParts, Constants.speciesParts));

            var $project = $(projectHtml);
            $project.appendTo(list);

            var $analyses = $project.find('[data-action="analyses"]');

            $analyses.html('分析流程加载中...');

            $app.ajax({
                url: $app.absolute("/rest/project/analyses"),
                data: {projectId: project.id},
                tag: {index:i, element:$analyses}
            }).done(function(r, tag) {
                tag.element.html('');

                for(var j=0;j< r.data.length; j++) {
                    var analysis = r.data[j];

                    var statusText = '<span class="label label-md label-danger">未知</span>';
                    var reportLink = '<span class="label label-md label-info">暂时不能查看</span>';
                    var resultLink = '<span class="label label-md label-info">暂时不能查看</span>';
                    var link = '<a href="'+ $app.absolute("/project/detail?analysesId="+analysis.id) +'">'+analysis.title+'</a>';

                    if(analysis.status == 'WAIT') {
                        statusText = '<span class="label label-md label-info">等待中</span>';
                    } else if(analysis.status == 'PROGRESS') {
                        statusText = '<span class="label label-md label-primary">进行中</span>';
                    } else if(analysis.status == 'FINISH') {
                        statusText = '<span class="label label-md label-success">已完成</span>';

                        reportLink = '<a href="'+ $app.absolute("/report/read?analysesId="+analysis.id) +'" class="btn default btn-xs red-stripe">点击查看</a>';
                        resultLink = '<a href="http://report.geneapps.cn/'+ analysis.id +'.zip" class="btn default btn-xs red-stripe">点击查看</a>';
                    } else if(analysis.status == 'FAIL') {
                        statusText = '<span class="label label-md label-danger">失败</span>';
                    }

                    var row = "<tr>"
                        +"<td>" + link + "</td>"
                        +"<td>" + analysis.updateTimeStr + "</td>"
                        +"<td>" + statusText + "</td>"
                        +"<td>" + reportLink + "</td>"
                        +"<td>" + resultLink + "</td>"
                        +"</tr>";

                    var $row = $(row);
                    $row.appendTo(tag.element);
                }
            });
        }

        $analyses.find("[data-action='detail']").click(function() {
            var id = $(this).attr('data-project-id');

            window.location.href = $app.absolute("/project/detail?id="+id);
        })
    }

    function updateTable(list) {
        var analysisTemplate = $("#template-analysis").html();
        var table = $("#list_body");

        table.html('');

        for(var i=0;i<list.content.length;i++) {
            var project = list.content[i];

            var statusText = project.status == 1 ? '有效' : project.status == 0 ? '删除' : '未知';

            var row = "<tr>"
                +"<td>" + (i+1) + "</td>"
                +"<td><span data-action='detail' data-project-id='" + project.id + "'>" + project.title + "</span></td>"
                +"<td>" + getDescription(project.species, Constants.species) + "</td>"
                +"<td>" + getDescription(project.sequencingMachines, Constants.sequencingMachines) + "</td>"
                +"<td>" + getDescription(project.processingMethod, Constants.processingMethod) + "</td>"
                +"<td>" + getDescription(project.speciesParts, Constants.speciesParts) + "</td>"
                +"<td>" + statusText + "</td>"
                +"<td>" + project.updateTimeStr + "</td>"
                +"</tr>"
                +"<tr><td></td><td colspan='7'><div class='row' data-action='analyses'></div></td></tr>"

            var $row = $(row);
            $row.appendTo(table);

            var $analyses = $row.find('[data-action="analyses"]');

            $analyses.html('分析流程加载中...');

            $app.ajax({
                url: $app.absolute("/rest/project/analyses"),
                data: {projectId: project.id},
                tag: {index:i, element:$analyses}
            }).done(function(r, tag) {
                console.log(r);
                tag.element.html('');

                for(var j=0;j< r.data.length; j++) {
                    var analysis = r.data[j];

                    var statusText = '<span class="label label-md label-danger">未知</span>';
                    var reportLink = '<span class="label label-md label-info">暂时不能查看</span>';
                    var resultLink = '<span class="label label-md label-info">暂时不能查看</span>';
                    var link = '<a href="'+ $app.absolute("/project/detail?analysesId="+analysis.id) +'" class="btn default btn-xs red-stripe">点击查看</a>';

                    if(analysis.status == 'WAIT') {
                        statusText = '<span class="label label-md label-info">等待中</span>';
                    } else if(analysis.status == 'PROGRESS') {
                        statusText = '<span class="label label-md label-primary">进行中</span>';
                    } else if(analysis.status == 'FINISH') {
                        statusText = '<span class="label label-md label-success">已完成</span>';

                        reportLink = '<a href="'+ $app.absolute("/report/read?analysesId="+analysis.id) +'" class="btn default btn-xs red-stripe">点击查看</a>';
                        resultLink = '<a href="http://report.geneapps.cn/'+ analysis.id +'.zip" class="btn default btn-xs red-stripe">点击查看</a>';
                    } else if(analysis.status == 'FAIL') {
                        statusText = '<span class="label label-md label-danger">失败</span>';
                    }

                    var analysisHtml = analysisTemplate.replace("{name}", analysis.title)
                        .replace("{createTime}", analysis.updateTimeStr)
                        .replace("{status}", statusText)
                        .replace("{report}", reportLink)
                        .replace("{result}", resultLink)
                        .replace("{link}", link);
                    var $analysis = $(analysisHtml);
                    $analysis.appendTo(tag.element);
                }
            });
        }

        table.find("[data-action='detail']").click(function() {
            var id = $(this).attr('data-project-id');

            window.location.href = $app.absolute("/project/detail?id="+id);
        })
    }

    function getDescription(value, dataSource) {
        for(var i=0;i<dataSource.length;i++) {
            if(dataSource[i].value == value)
                return dataSource[i].name;
        }
        return "";
    }
})(jQuery);