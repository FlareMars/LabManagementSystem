$(function() {
	
	/** 页面中class=auto-height部分高度自适应 */
	var autofix = function() {
		var height = $(window).height();
		var fixHeight = $("header").outerHeight()
				+ $(".fix-navbar").outerHeight() + $("footer").outerHeight();
		$(".auto-height").height(height - fixHeight);
	};
	autofix();
	$(window).resize(function() {
		autofix();
	});
	
	var ajaxSubmitSuccess = function(target){
		$("#global-ajax-success-text").html(target.attr("success-msg")||'操作成功!');
		$("#global-ajax-success").fadeIn();
		window.setTimeout(function(){$("#global-ajax-success").fadeOut()},2000);
	};
	
	$.ajaxSuccess = function(){
		$("#global-ajax-success").fadeIn();
		window.setTimeout(function(){$("#global-ajax-success").fadeOut()},2000);
	};
	/*** 
	 * framework level 
	 * @author ren-wei.wang
	 */
	var rCRLF = /\r?\n/g,
		rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
		rsubmittable = /^(?:input|select|textarea|keygen)/i,
		manipulation_rcheckableType = /^(?:checkbox|radio)$/i;
	jQuery.fn.extend({
		valiSubmit: function(conf) {
			var inputs = [];
			var paramsAry = this.map(function(){
				// Can add propHook for "elements" to filter or add form elements
				var elements = jQuery.prop( this, "elements" );
				return elements ? jQuery.makeArray( elements ) : this;
			})
			.filter(function(){
				var type = this.type;
				var isInput = this.name && !jQuery( this ).is( ":disabled" ) &&
					rsubmittable.test( this.nodeName ) && !rsubmitterTypes.test( type );
				// Use .is(":disabled") so that fieldset[disabled] works
				if(isInput){
					inputs.push(this);
				}
				return  isInput && ( this.checked || !manipulation_rcheckableType.test( type ) );
			})
			.map(function( i, elem ){
				var val = jQuery( this ).val();
				return val == null ?
					null :
					jQuery.isArray( val ) ?
						jQuery.map( val, function( val ){
							return { name: elem.name, value: val.replace( rCRLF, "\r\n" ) };
						}) :
						{ name: elem.name, value: val.replace( rCRLF, "\r\n" ) };
			}).get();
			
			var params = {};
			$.each(paramsAry,function(){
				params[this.name] = this.value;
			});
			
			var hasError = false;
			$.each(inputs,function(){
				if(this.required === true){
					var self = $(this);
					var grp = self.parent('.input-group');
					var _error_msg = $(this).attr('vali_error_msg')||'输入错误';
					if(!params[this.name]){
						if(grp) grp.addClass('has-error');
						self.popover({'title':'失败','content':_error_msg}).popover("show");
						hasError = true;
					}else{
						if(grp) grp.removeClass('has-error');
						self.popover("hide");
					}
				}
			});
			
			if(!hasError){
				var form = $(this);
				if(form.attr("ajax") || form.hasClass("ajax")){
					if(conf.onBegin){
						conf.onBegin();
					}
					$.ajax({
						url:form.attr("action"),
						data:paramsAry,
						dataType:conf.dataType||'html',
						type:form.attr('method')||'post',
						success:function(r){
							if(conf.onSuccess){
								conf.onSuccess(r);
							}
							if(!form.hasClass("noMention")){
								ajaxSubmitSuccess(form);
							}
						},
						error:conf.onFail||function(){},
						complete:conf.onComplete||function(){}
					});
				}else{
					form.submit();
				}
			}
		}
	});
	
	var js_form = function(form,conf){
		var dataType = form.attr('dataType')||"html";
		form.valiSubmit({
			dataType:dataType,
			onBegin:function(){},
			onSuccess:function(r){
				if(conf.success){
					conf.success(r);
				}
			},
			onFail:function(t){
				if(conf.fail){
					conf.fail(t);
				}
			},
			onComplete:function(){
				if(conf.callback){
					conf.callback();
				}
			}
		});
	};
	
	if(!$.swjs){
		$.swjs={};
	}
	
	if(!$.swjs.getFileName){
		$.swjs.getFileName = function(name){
			return name.replace(/(\.tgz)|(\.tar\.gz)|(\.gz)$/,'');
		};
	}
	
	//模态窗口【class标记为form-modal】时，尝试将form中的值设置为默认的
	$('body').delegate(".form-destory-modal",'show.bs.modal',function(e){
		var href = e.relatedTarget;
		var that = $(this);
		if(typeof(href)!='str'){
			href = $(href).attr("href");
		}
		if('true' == that.attr('shown')){
			if(that.attr('hisload') != href){
				$.ajax({
					url:href,
					success:function(r){
						that.find('.modal-content').html(r);
					}
				});
			}
		}
		that.attr('shown','true')
		that.attr('hisload',href);
	});
	
	/**
	 * _dt jquery表达式中加载页面
	 */
	var doLoad = function(self,_dt,callback){
		var url = self.attr('href');
		var paramDataObj = {};
		$.each(self.get(0).attributes,function(){
			if(/^params-.*$/.test(this.name)){
				paramDataObj[this.name.substr(7)] = this.value;
			}
		});
		$.ajax({
			url:url,
			type:self.data('method')||'get',
			data:self.attr('data-params') || paramDataObj,
			success:function(r){
				var evt_success = $.Event('ajax-success',{'data-target':_dt});
				self.focus().trigger(evt_success);
				if(callback){
					callback;
				}
				$(_dt).html(r);
				autoLoadRender();
			},
			fail:function(){
				var evt_fail = $.Event('ajax-fail',{'data-target':_dt,'url':url});
				var popover = self.popover({'title':'失败',
					'content':_error_msg});
				popover.popover("show");
				self.focus().trigger(evt_fail);
				window.setTimeout(function(){popover.popover('hide');},2000)
			}
		});
	};
	
	$('body').delegate("a.ajax,button.ajax",'click',function(e){
		var self = $(this),
			_dt = self.attr('data-target'),
			_error_msg = self.attr('error_msg')||'系统错误，请联系管理员';
		if(_dt){
			if('true'==self.attr('history'))
			{
				$.history.load(self.attr('href'),function(){
					doLoad(self,_dt);
				});
			}else{
				doLoad(self,_dt);
			}
			e.preventDefault();
		}
	});
	
	/** 表达submit */
	$("body").delegate("button[type=submit]","click",function(e){
		var button = $(this),
			button_form = button.parents("form");
		if(button_form){
			var _dt = button_form.attr('data-target');
			var _error_msg = button_form.attr('error_msg')||'系统错误，请联系管理员';
			var parent_model = button_form.parents(".modal");
			var conf = {
				fail:function(){
					var evt_fail = $.Event({'data-target':_dt,'url':url});
					self.focus().trigger(evt_fail);
					
					var popover = button.popover({'title':'失败',
						'content':_error_msg});
					popover.popover("show");
					window.setTimeout(function(){popover.popover('hide');},2000)
				},
				success:function(r){
					if(parent_model.size()>0){
						parent_model.modal('hide');
						if(parent_model.hasClass('form-destory-modal')){
							parent_model.remove();
							$(".modal-backdrop").remove();
						}
					}
					var evt_success = $.Event('ajax-success',{'data-target':_dt});
					button.focus().trigger(evt_success);
					if(_dt){
						$(_dt).html(r);
					}
				}
			}
			js_form(button_form,conf);
			e.preventDefault();
		}
	});
	
	
	/**
	 * 按钮和tabs绑定，即：按钮点击，自动选择下一个tab
	 */
	$("body").delegate("button[data-toggle=tab]","click",function(e){
		var self = $(this),
			_dt = self.attr('data-target');
		if(_dt){
			var tabs = $(_dt);
			if(tabs){
				var idx = tabs.find("li.active").index(),
					tabcount = tabs.find("li").size();
				var nextTabIdx = idx+1;
				if(nextTabIdx<tabcount){
					tabs.find("li:eq("+nextTabIdx+") a").tab('show');
				}
			}
		}
	});
	
	/**
	 * 事件组件
	 */
	$("body").delegate("[data-toggle=event]","click",function(e){
		var self = $(this),
			_dt = self.attr('data-target') || "body",
			eventName = self.attr('data-event')||'delegate.event';
		var eventObj = {};
		$.each(this.attributes,function(){
			if(/^event-.*$/.test(this.name)){
				eventObj[this.name.substr(6)] = this.value;
			}
		});
		var event = $.Event(eventName,eventObj);
		$(_dt).focus().trigger(event);
		return "true" !== self.attr("prevent-default");
	});
	
	var autoLoadRender = function(){
		$(".autoLoad").each(function(){
			if($(this).attr("href") && !$(this).attr("auto-loaded")){
				$(this).load($(this).attr("href"));
				$(this).attr('auto-loaded',"true");
			}
		});
	};
	
	/** 按钮点击，移掉按钮所在的行 */
	$("body").delegate("[data-dismiss=row]","click",function(){
		var row = $(this).parents("tr");
		row.remove();
		var table = row.parents('table');
		var deleteEvent = $.Event('row.delete');
		table.focus().trigger(deleteEvent);
		return false;
	});
	
	/** Bootstrap button dropdown条目点击后显示标签显示被选中条目值 */
       $("body").delegate("div.btn-group>ul.dropdown-menu>li>a","click",function(e){
           var $this=$(this);
           var $labelObj = this.$labelObj;
           var $btnGroupObj = this.$btnGroupObj;
           var isSingleBtnDropdown=this.isSingleBtnDropdown;
           
           if(typeof $labelObj ==="undefined"){
               $btnGroupObj=$this.parent().parent().parent();
               var $btnList=$btnGroupObj.find(".btn");
               var btnListLength=$btnList.length;
               switch(btnListLength){
                   case 1:
                     //Single button dropdowns
                     isSingleBtnDropdown = true;
                     $labelObj = $btnList;
                     break;
                   case 2:
                    //Split button dropdowns
                    isSingleBtnDropdown =false;
                    $labelObj =$btnList.eq(0);
                    break;
               }
               this.$labelObj=$labelObj;
               this.$btnGroupObj=$btnGroupObj;
               this.isSingleBtnDropdown=isSingleBtnDropdown;
           }
           
           //获取选择值
           var value=$this.data("value");
           if(value){
        	   $labelObj.data("value",value);
           }
           
         //更新显示标签值
         var labelValue = $this.html();
         if(isSingleBtnDropdown===true){
             var displayValue = labelValue+" <span class='caret'></span>";
             $labelObj.html(displayValue);
         }else if(isSingleBtnDropdown==false){
             $labelObj.html(labelValue);
         }
         
       $btnGroupObj.removeClass("open");
       return false;
    });
	
	var emptyFunc = function(){};
	$.fn.extend({
		//tab事件代理
		'initTabEventProxy':function(cnf){
			var conf = cnf || {};
			var onfirst = conf.onfirst||emptyFunc,
				onlast = conf.onlast||emptyFunc,
				onmiddle = conf.onmiddle||emptyFunc;
			$(this).on('shown.bs.tab',function(e){
				var tab = $(e.target).parent(),
				tabs = tab.parent();
				var idx = tab.index(),
					tabcount = tabs.find("li").size();
	
				if(idx==0){
					onfirst();
				}else if(idx+1<tabcount){
					onmiddle();
				}else{
					onlast();
				}
			});
		}
	});
	
	autoLoadRender();
});