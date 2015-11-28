(function($) {
    'use strict'

    app_inits.push(function() {
        load();
    });

    function load() {
        getDescription($("#species"), Constants.species);
        getDescription($("#sequencingMachines"), Constants.sequencingMachines);
        getDescription($("#processingMethod"), Constants.processingMethod);
        getDescription($("#speciesParts"), Constants.speciesParts);
        var projectId = $("#project-id").val();

        var $analyses = $('#analyses');

        $analyses.html('分析流程加载中...');

        $app.ajax({
            url: $app.absolute("/rest/project/analyses"),
            data: {projectId: projectId},
            tag: {element:$analyses}
        }).done(function(r, tag) {
            console.log(r);
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

    function getDescription(element, dataSource) {
        var value = element.html();

        for(var i=0;i<dataSource.length;i++) {
            if(dataSource[i].value == value) {
                element.html(dataSource[i].name);
            }
        }
    }
})(jQuery);