(function($) {
    'use strict'

    var pager;

    app_inits.push(function() {
        pager = new Pager({
            pageSize: 10,
            currentPage: 0,
            totalRecords: 0,
            element: "#pager",
            pageChanged: function(page) {
                refresh(page);
            }
        });
        refresh(0);
    });

    function refresh(page) {
        var url = $app.absolute("/rest/project/allanalyses?page="+page+"&d="+new Date());
        console.log(url)
        $app.ajax({
            url: url
        }).done(function(r) {
            console.log(r);

            if(r.data)
                r = r.data;

            updateList(r);

            pager.update({
                page: r.number,
                count: r.totalElements
            });
        });
    }

    function updateList(data) {
        //var analysisTemplate = $("#template-analysis").html();
        var list = $("#analyses-list");

        list.html('');

        for(var j=0;j< data.content.length; j++) {
            var analysis = data.content[j];

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
            $row.appendTo(list);
        }

        /*list.find("[data-action='detail']").click(function() {
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