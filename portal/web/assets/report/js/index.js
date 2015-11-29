var index={
    init:function(){
        var _this=this;
        $(function() {
             _this.initPage();
        });
    },
    
    initPage:function(){
        $('.gallery-content').unslider({dots: true});
        $('.user-fb-gallery').unslider({dots: true});
    }
}

index.init();
