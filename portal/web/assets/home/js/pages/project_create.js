(function($) {
    'use strict'

    var initSelection = function($s, data) {
        $s.html('');

        var html = "";
        for(var i=0;i<data.length;i++) {
            html += "<option value='"+data[i].value+"'>"+data[i].name+"</option>";
        }

        $s.html(html);

        $s.select2();
    };

    var submitForm = function() {
        var project = {
            title : $("#project_title").val(),
            species : $("#selection_species").val(),
            sequencingMachines : $("#selection_sequencingMachines").val(),
            processingMethod : $("#selection_processingMethod").val(),
            speciesParts : $("#selection_speciesParts").val()
        };

        if(!project.title) {
            $app.showMessage("请输入项目名称");
            return;
        }

        $app.blockUI({message: '保存中，请稍后'});
        $app.ajax({
            url: $app.absolute('/rest/project/create'),
            data: JSON.stringify({
                title: project.title,
                project: project
            }),
            method: 'POST',
            contentType: "application/json",
            processData: false
        }).done(function(r) {
            $app.unblockUI();

            window.location.href = $app.absolute("/project/list");
        }).fail(function() {
            $app.unblockUI();
        });
    };

    app_inits.push(function() {
        initSelection($("#selection_species"), Constants.species);
        initSelection($("#selection_sequencingMachines"), Constants.sequencingMachines);
        initSelection($("#selection_processingMethod"), Constants.processingMethod);
        initSelection($("#selection_speciesParts"), Constants.speciesParts);

        $("#action_submit").click(function() {
            submitForm();
        })
    });
})(jQuery);