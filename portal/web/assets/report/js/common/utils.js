var utils ={
    ajax:function(url,successCB){
        url=window.basePath+url;
        var option = {
            url : url,
            type : "POST",
            success : function(ret) {
                if(typeof successCB==="function"){
                    successCB(ret);
                }
            }
        };

        $.ajax(option);    
    },
    
    showErrorInfor:function($erroField,errorInfor,isAddCloseBtn){
        if($erroField===undefined || $erroField.size()===0){
        	return;
        }
        
        var $parent=$erroField.parent(),
               $errorTip=$parent.find("div.alert");
        
        if($errorTip.size()>0){
        	$errorTip.find(".error-infor").html(errorInfor);
        	return;
        }
        
        var  errorTipTemplate='<div class="alert alert-error" role="alert"><span class="error-infor">{error-infor}</span></div>',
    	       errorTipWithCloseBtnTemplate='<div class="alert alert-error alert-dismissible" role="alert">'
    	    	                                                               +'<button type="button" class="close" data-dismiss="alert">'
    	    	                                                                     +'<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>'
    	    	                                                               +'</button>'
    	    	                                                               +'<span class="error-infor">{error-infor}</span></div>',
    	     errorTipStr=errorTipTemplate;
        
        if(isAddCloseBtn==true){
        	errorTipStr=errorTipWithCloseBtnTemplate;
        }
        
        errorTipStr=errorTipStr.replace("{error-infor}",errorInfor);
        
        $erroField.addClass("error-field");
        $erroField.parent().append(errorTipStr);
    },
    
    hideErrorInfor:function($erroField){
    	if($erroField===undefined || $erroField.size()===0){
    		return;
    	}
    	 $erroField.removeClass("error-field");
    	 
     	var $errorTipList=$erroField.parent().find("div.alert");
     	if($errorTipList.size()>0){
     		$errorTipList.eq(0).remove();
     	}
    },
    
    /**
     * 
     * @param $targetObj
     * @param content
     * @param location "top","right","bottom"和"left"
     */
    showPopErrorInfor:function($targetObj,content,location){
    	if($targetObj===undefined || $targetObj.size()===0){
    		return;
    	}
    	$targetObj.addClass("error-field");
    	
    	var template='<div class="popover popover-error"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    	
    	$targetObj.popover({
     		"trigger":"manual",
     		 'placement': location,
     		 "html":true,
     		 "title":'',
     		'content':content,
     		'template':template
     	}).popover("show");
    	
    },
    
    hidePopErrorInfor:function($targetObj){
    	if($targetObj===undefined || $targetObj.size()===0){
    		return;
    	}
    	
    	$targetObj.removeClass("error-field");
    	$targetObj.popover("destroy");
    }
}
