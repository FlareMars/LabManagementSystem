(function($) {
    app_inits.push(function() {
        getDescription($("#species"), Constants.species);
        getDescription($("#sequencingMachines"), Constants.sequencingMachines);
        getDescription($("#processingMethod"), Constants.processingMethod);
        getDescription($("#speciesParts"), Constants.speciesParts);

        updateLogMessages();
    });

    function getDescription(element, dataSource) {
        var value = element.html();

        for(var i=0;i<dataSource.length;i++) {
            if(dataSource[i].value == value) {
                element.html(dataSource[i].name);
            }
        }
    }

    function updateLogMessages() {
        $app.ajax({
            url: $app.absolute('rest/project/analyses/logMessages')
        }).done(function(r) {
            var messages = r.messages;
            $("[data-log-id]").each(function() {
                var id = "m"+$(this).attr('data-log-id');

                if(messages[id])
                    $(this).html(messages[id]);
                else
                    $(this).html("["+id+"]未知错误");
            })
        });
    }
})(jQuery);