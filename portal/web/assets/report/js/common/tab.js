var tab={
    $tabs:null,//存放所有tab的jquery对象
    $nextTab:null,//当前tab（内容显示）的下一个相邻tab对象
    size:0, //tab个数
    isLastTab:false,//true表示当前是最后一个tab显示。

    /**
     * 
     * @param {Object} tabSelector  tab的选择器 如'a[data-toggle="tab"]'
     * @param {Object} lastTabShowCallback  当最后一个tab的内容显示时，调用的回调函数
     * @param {Object} nonLastTabShowCallback  当非最后一个tab的内容显示时，调用的回调函数
     */
    init:function(tabSelector,lastTabShowCallback,nonLastTabShowCallback){
        tab.$tabs=$(tabSelector);
        tab.size=tab.$tabs.length;
        tab.isLastTab=false;
        
        //获取下一个tab对象
        if(tab.size===0){
            return;
        }else if(tab.size===1){
            //如果只有一个tab对象，下一个tab对象就为那唯一的对象
            tab.$nextTab=tab.$tabs.eq(0);
        }else{
            //如果有多个tab对象时，取第二个tab对象为下一个tab对象
             tab.$nextTab=tab.$tabs.eq(1);
        }

        //添加tab显示事件
        tab.$tabs.off().on("shown.bs.tab",function(e){
            var $currentTab=$(e.target);
            var currentTabIndex= tab.$tabs.index($currentTab);
            var nextTabIndex = 0;
            
            if(currentTabIndex===tab.size-1){
                //当前是最后一个tab 显示
                tab.isLastTab=true;
                nextTabIndex=currentTabIndex;
                if(typeof lastTabShowCallback=="function"){
                    lastTabShowCallback();
                }
            }else{
                tab.isLastTab=false;
                nextTabIndex = currentTabIndex+1;
                if(typeof nonLastTabShowCallback=="function"){
                    nonLastTabShowCallback();
                }
            }
           
            tab.$nextTab=tab.$tabs.eq(nextTabIndex);
        });
    },
    
    showNextTab:function(){
        tab.$nextTab.tab("show");
    }
}
