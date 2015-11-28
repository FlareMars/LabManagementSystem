$(function(){
        var  $groupItems=$("#left-navigation-panel a.list-group-item");
        $("#left-navigation-panel").delegate("a.list-group-item","click",function(){
            var $this = $(this);
            $groupItems.removeClass("active");
            $this.addClass("active");
        });   
});
