var rna={
		init:function(){
			this.reset();
			
			rnaUploadDataTab.init(this.tipDialogTemplate);
			rnaSeqFileTab.init();
			rnaAssembleTab.init();
			rnaCompaTab.init();
			rnaOrderTab.init(rnaAssembleTab.assemPanelTemplate);
			rnaFileTypeDrowdownHelper.init();
			rnaSeqFileDropDownHelper.init();
			rnaUtils.init();
		},
		
		reset:function(){
			rnaGroupStatus.reset();
			rnaCompaStatus.reset();
			rnaSeqFileStatus.reset();
			rnaSeqFileDropDownHelper.reset();
		}
};

/**负责[数据文件]标签**/
var rnaUploadDataTab={
		uploadDataPanelTemplate:null,
		groupNameFieldTemplate:null,
		$dataBody:null,
		$groupNameDialog:null,
		$groupNameDialogOkBtn:null,
		$groupNameDialogNameField:null,
		panelIdPrefix:"upload-data-",
		tbodyIdSuffix:"-upload-data-tbody",
		
		init:function(){
			var _this=this;
			this.uploadDataPanelTemplate=$("#upload-data-panel-template").html();
			this.groupNameFieldTemplate=$("#group-name-field-template").html();
			this.$dataBody=$("#rna-data-dyna-body");
			this.$groupNameDialog=$("#rna-group-name-dialog");
			this.$groupNameDialogOkBtn=$("#rna-group-name-dialog-ok-btn");
			this.$groupNameDialogNameField=$("#rna-group-name");
			
			$("#rna-add-group-btn").on("click",function(e){
				//清空对话框文本域内容
				_this.$groupNameDialogNameField.val("");
				utils.hideErrorInfor(_this.$groupNameDialogNameField);
				_this.$groupNameDialog.modal("show");
				
				setTimeout(function(){
					_this.$groupNameDialogNameField[0].focus();
				},500);
			   
			});
			
			this.$groupNameDialogNameField.on("keydown",function(e){
				  var code = (e.keyCode ? e.keyCode : e.which);
				   if(code ==13){ // entyer key code
					   _this.groupNameDialogOkBtnEventHandler(e);
					   e.preventDefault();
				   }
			});
			
			$("#rna-group-name-dialog-ok-btn").on("click",function(e){
				_this.groupNameDialogOkBtnEventHandler(e);
			});
		},
		
		groupNameDialogOkBtnEventHandler:function(e){
				var time=rnaUtils.getTime(),
	            key=rnaGroupStatus.groupKeyPrefix+time,
	            groupName=this.$groupNameDialogNameField.val();
			
				
			if(!rnaGroupStatus.isAvailableGroupName(groupName)){
				utils.showErrorInfor(this.$groupNameDialogNameField, "该转录组名称已经存在。请重新输入。");
				return;
			}else if(groupName.replace(" ","")===""){
				utils.showErrorInfor(this.$groupNameDialogNameField, "转录组名称不能为空。");
				return;
			}else{
				utils.hideErrorInfor(this.$groupNameDialogNameField);
			}
	
		      this.insertGroupPanel(e,key,groupName);
		      rnaOrderTab.insertUploadDataPanel(e,key,groupName);
		      
		      rnaGroupStatus.addGroup(key, groupName);
		      
		      this.$groupNameDialog.modal("hide");
	    },
	    
		tableFileSelectedEventHandler:function(e,$panelObj,$tbody,$addFileBtn,groupKey,dropdownMenu){
            var  rowClass="row"+rnaUtils.getTime(),
                    fileNumber=0,
                    isAllowSampleAnalysis=false,
                    defaultSelectedFileType=rnaFileTypeDrowdownHelper.EMPTY_VALUE,
                    returnResult=this.insertFile(e,$panelObj,$tbody,rowClass,groupKey,$addFileBtn,dropdownMenu);
		
		     fileNumber=returnResult.rowSize;
		     isAllowSampleAnalysis=returnResult.isAllowSampleAnalysis;
		     defaultSelectedFileType=returnResult.defaultSelectedFileType;
		     
			//TODO
			//向【参考序列比对】标签页相应转录组面板插入一个文件（只有当插入第3个文件时，插入操作才会执行）
			//rnaCompaTab.insertFileToGroupBlock(groupKey,e,fileNumber,rowClass,defaultSelectedFileType);
			
		    //向【订单确认】标签页【数据文件】相应转录组面板插入一个文件
			rnaOrderTab.insertFileToDataPanel(e,rowClass,groupKey,isAllowSampleAnalysis,defaultSelectedFileType);
			
			//TODO
			//向【订单确认】标签页相应转录组面板插入一个文件（只有当插入第3个文件时，插入操作才会执行）
			//rnaOrderTab.insertFileToGroupBlock(groupKey,e,fileNumber,rowClass,defaultSelectedFileType);			
		},
		
		deleteGroupBtnEventHandler:function(e,groupKey){
			var _this=this;
	   	   if(rnaGroupStatus.isCompa(groupKey)===true){
	  		        rnaUtils.openTipDialog("转录组删除","该转录组参与了【样本间比对分析】，请删除相应的比对组后，再尝试删除。");
	  	   }else{
	 				rnaUtils.openTipDialog("删除转录组","确定要删除该转录组吗？",function(){
	 					_this.removeGroupPanel(e, groupKey);
	 					rnaOrderTab.removeGroupPanelFromDataPanel(e, groupKey);
	 					
	 					rnaGroupStatus.deleteGroup(groupKey);
					});
	  	   }
		},	    
		
		deleteFileLinkEventHandler:function(e,groupKey,rowClass,$rowObj,$panelObj,$tbody,$addFileBtn,dropdownMenu){
		   	   if(rnaGroupStatus.isCompa(groupKey)===true){
        		   rnaUtils.openTipDialog("文件删除","该文件所在转录组参与了【样本间比对分析】，请删除相应的比对组后，再尝试删除。");
        	   }else{
        		   //更新下拉框们状态 
        		   dropdownMenu.refreshAfterRemovingMenu($rowObj.find("ul.dropdown-menu"));
        		   
        		   //删除文件
        		  var isAllowSampleAnalysis= this.deleteFile($rowObj,groupKey,$panelObj,$tbody,$addFileBtn);
        		   rnaOrderTab.deleteFileFromDataPanel(rowClass,groupKey,isAllowSampleAnalysis);
        		   rnaOrderTab.deleteFileFromAssemPanel(rowClass,groupKey,isAllowSampleAnalysis);
        	   }			
		},
		
		clickFileTypeItemEventHandler:function(e,groupKey,$this,$panelObj,value){
			var rowClass=$this.data("menuParentIdOrClass"),
			       isAllowSampleAnalysis=false;
			
			//更新rnaGroupStatus
			rnaGroupStatus.updateFileTypeForGroupFile(groupKey, rowClass, value);
			isAllowSampleAnalysis=rnaGroupStatus.isAllowSampleAnalysis(groupKey);
			
			//控制【数据文件】错误提示信息显示
			rnaUtils.showOrHideErrorTipInfor($panelObj, !isAllowSampleAnalysis);

			rnaAssembleTab.updateFileType(rowClass,value);
			rnaCompaTab.updateFileType(rowClass, value);
			rnaOrderTab.updateFileTypeForDataPanel(rowClass, value,groupKey,isAllowSampleAnalysis);
			rnaOrderTab.updateFileTypeForAssembPanel(rowClass, value);
	
			rnaOrderTab.updateFileTypeForCompaPanel(rowClass, value);
		},
		
		insertGroupPanel:function(e,key,groupName){
			var  _this = this,
			        panelid=this.panelIdPrefix+key,
			        panelColId=key+"-panel",
			        tbodyId=key+this.tbodyIdSuffix,
			        $tbody=null,
			        $panelObj=null,
			        $addFileBtn=null,
			        dropdownMenu=null;
			
			$panelObj=rnaUtils.dealFilePanelTemplate(this.uploadDataPanelTemplate,key, groupName,panelid,panelColId,tbodyId,this.$dataBody);
			
			$addFileBtn=$panelObj.find("button.add-file-btn");
			
			$tbody=$panelObj.find("#"+tbodyId);
			
			dropdownMenu=new DropdownMenu(rnaFileTypeDrowdownHelper.getFileTypeDropdownItems(),
					  																  $tbody,
					  																  rnaFileTypeDrowdownHelper.EMPTY_LABEL,
					  																  rnaFileTypeDrowdownHelper.EMPTY_VALUE,
					  																  rnaFileTypeDrowdownHelper.EMPTY_ICON_CLASS);
			//为该面板下的所有文件类型下拉框注册事件
			dropdownMenu.registerItemClickEventByDelegate(function(e,$this,value){
				_this.clickFileTypeItemEventHandler(e,key,$this,$panelObj,value);
			});
			
			$tbody.on("file.selected",function(e){
				_this.tableFileSelectedEventHandler(e, $panelObj,$(this),$addFileBtn,key,dropdownMenu);
			});
			
			$panelObj.find("button.close").on("click",function(e){
		               _this.deleteGroupBtnEventHandler(e,key);
			});
		},
		
		removeGroupPanel:function(e,groupKey){
			var $targetPanel=this.$dataBody.find("."+groupKey);
			$targetPanel.slideUp(function(){
				$targetPanel.remove();
			});
		},
		
		insertFile:function(e,$panelObj,$tbody,rowClass,key,$addFileBtn,dropdownMenu){
           var _this=this,
                  rowSize=0,
                  fileName=e.filename,
                  source=e.resoucename,
                  fileSize=e.showfilesize,
                  actualSize = e.filesize,
                  directory=e.directory,
                  isAllowSampleAnalysis=false,
                  defaultSelectedFileType="",
                  $rowObj=null;
           
           //向【数据文件】标签页插入一个文件

           var dataRow='<tr class='+rowClass+'>'
           							+'<td class="file-type-drowdown"></td>'
        	   						+'<td>'+fileName+'</td>'
        	   						+'<td>'+fileSize+'</td>'
        	   						+'<td>'+source+'</td>'
        	   						+'<td><a class="del-row" >删除</a></td>'
        	   						+'</tr>';

           $rowObj=$(dataRow).appendTo($tbody);
           
           //渲染文件类型下拉框
           defaultSelectedFileType=rnaGroupStatus.getDefaultSelectedFileType(key);
           dropdownMenu.assemDropdown(rowClass,$rowObj.find("td.file-type-drowdown"),defaultSelectedFileType);
           
           $rowObj.find("a.del-row").on("click",function(e){
        	   _this.deleteFileLinkEventHandler(e,key,rowClass,$rowObj,$panelObj,$tbody, $addFileBtn,dropdownMenu);
        	   return false;
           });
           
           rnaGroupStatus.insertFileToGroup(key,fileName,defaultSelectedFileType,source,fileSize,actualSize,rowClass,directory);
           
           rowSize=$tbody.children().size();
           
           
           if(rowSize>0){
        	   isAllowSampleAnalysis=rnaGroupStatus.isAllowSampleAnalysis(key);
        	   rnaUtils.showOrHideErrorTipInfor($panelObj, !isAllowSampleAnalysis,false);
           }
           
           if(rowSize===2){
        	   $addFileBtn.attr("disabled","disabled");
           }
           
           return {
        	   rowSize:rowSize,
        	   isAllowSampleAnalysis:isAllowSampleAnalysis,
        	   defaultSelectedFileType:defaultSelectedFileType
           };
		},
		
		deleteFile:function($rowObj,groupKey,$panelObj,$tbody,$addFileBtn){
			var rowClass=$rowObj.attr("class"),
			       rowSize = 0,
			       isAllowSampleAnalysis=false;
			
			$rowObj.remove();
			
			rnaGroupStatus.deleteFileFromGroup(groupKey,rowClass);
			isAllowSampleAnalysis=rnaGroupStatus.isAllowSampleAnalysis(groupKey);
			
			rowSize=$tbody.children().size();
			if(rowSize===1){
				$addFileBtn.removeAttr("disabled");
			}
			
			rnaUtils.showOrHideErrorTipInfor($panelObj, !isAllowSampleAnalysis);
			
			return isAllowSampleAnalysis;
		}
};

/**负责[序列文件]标签**/
var rnaSeqFileTab={
		$tbody:null,
		$addBtn:null,
		init:function(){
			var _this=this;
			this.$tbody=$("#seq-file-tbody");
			this.$addBtn=$("#rna-add-seq-file-btn");
			this.$tbody.on("file.selected",function(e){
				_this.insertFileToSeqFileTable(e);
			});
		},
		
		insertFileToSeqFileTable:function(e){
	           var _this =this,
	                  rowClass="row"+rnaUtils.getTime(),
	                  directory = e.directory,
	                  filename=e.filename,
	                  source=e.resoucename,
	                  fileSize=e.showfilesize,
	                  actualSize = e.filesize,
	                  dataRow='<tr class='+rowClass+'>'
										+'<td><span class="glyphicon glyphicon-align-justify"></span>Reference Sequence</td>'
										+'<td>'+filename+'</td>'
										+'<td>'+source+'</td>'
										+'<td>'+fileSize+'</td>'
										+'<td><a class="del-row" >删除</a></td>'
										+'</tr>',
					$rowObj=null;
	           $rowObj=$(dataRow).appendTo(this.$tbody);
	          
	           //注册删除事件函数 TODO
	           $rowObj.find("a.del-row").on("click",function(e){
	        	   _this.deleteSeqFile(e,$rowObj,rowClass);
	        	   return false;
	           });
	           
	           //向订单页面插入一条记录
	           rnaOrderTab.insertFileToSeqFilePanel(e, rowClass,rnaFileTypeDrowdownHelper.SEQ_FILE);
	           
	           //向seqFiles json对象中插入一条记录
	           rnaSeqFileStatus.addSeqFile(rowClass,directory,filename,fileSize,actualSize, source);
	           
	           rnaSeqFileDropDownHelper.setDeafultSelectedValueForAllDropDown();
	           
	           this.$addBtn.attr("disabled","disabled");
		},
		
		deleteSeqFile:function(e,$deleteRow,rowClass){
			if($deleteRow==undefined || $deleteRow.size()===0){
				return false;
			}
			
			//判断该序列文件是否被转录组使用
			if(rnaSeqFileStatus.isBindGroup(rowClass)){
				rnaUtils.openTipDialog("序列文件删除","该序列文件被相应转录组使用。请删除相应的转录组后，再尝试删除。");
				return false;
			}
			
			//删除行
			$deleteRow.remove();
			
			//从订单页面删除相应记录
			rnaOrderTab.deleteFileFromSeqFilePanel(rowClass);
			
			//从seqFiles中删除相应记录
			rnaSeqFileStatus.deleteSeqFile(rowClass);
			
			  this.$addBtn.removeAttr("disabled");
			
			return false;
		}
		
};

/**负责[转录组拼接]标签**/
var rnaAssembleTab={
		$contentBody:null,
		$openDialogBtn:null,
		$creationDialog:null,
/*		$asseTypeCheckboxs:null,*/
		$ssrCheckbox:null,
		$cdsCheckbox:null,
		$avaiArea:null,
		$selectArea:null,
		groupAssemblePanelTemplate:null,
		groupBlockClassSuffix:"-block",
		dropAndDrag:null,
		TITLE:"转录组拼接",
		MIN_BLOCK_NUM:1,
		MAX_BLOCK_NUM:1000,
       init:function(){
    	   var _this=this;
    	   this.$contentBody=$("#rna-group-assem-body");
    	   this.$openDialogBtn=$("#rna-open-group-assemble-dialog-btn");
    	   this.$creationDialog=$("#rna-group-assemble-dialog");
    	   //this.$asseTypeCheckboxs=$("#rna-group-assemble-dialog input[type='checkbox']");
    	   this.$ssrCheckbox=$("#rna-group-assemble-dialog input[data-type='ssr']");
    	   this.$cdsCheckbox=$("#rna-group-assemble-dialog input[data-type='cds']");
    	   this.$avaiArea=$("#ga-avai-group-area");
    	   this.$selectArea=$("#ga-selected-area");
    	   this.groupAssemblePanelTemplate=$("#group-assem-panel-template").html();
    	   this.dropAndDrag=new DropAndDrag(this.MIN_BLOCK_NUM,
																    			   this.MAX_BLOCK_NUM,
																	               this.$avaiArea,
																	               this.$selectArea,
																	               this.groupBlockClassSuffix);
    	   
    	   this.$openDialogBtn.on("click",function(e){
    		   _this.openCreationDialog(e);
    	   });
    	   
    	   $("#rna-create-group-assemble-btn").on("click",function(e){
    		   _this.createGroupAssemble(e);
    	   });
    	   
    	   this.$contentBody.on("click","input.assembly-type",function(e){
    		    var $this=$(this),
    		            type=$this.data("type"),
    		            isChecked=$(this).is(':checked');
    		    rnaOrderTab.updateAssembTypeForAssemPanel(type, isChecked);
    		    
    		    _this.updateAssembleTypeForDataModel(e, type, isChecked);
    	   });
       },
       
       openCreationDialog:function(e){
    	   
    	   //重置拼接类型复选框
    	   this.$ssrCheckbox.attr("checked","checked");
    	   this.$cdsCheckbox.attr("checked","checked");
    	   
    	   //重置选中区域
    	   this.$selectArea.empty();
    	   
    	   //填充可用转录组
    	   this.dropAndDrag.fillAvaiArea();
    	   
			//打开对话框
			this.$creationDialog.modal("toggle");
       },
       
       createGroupAssemble:function(e){
    	   //判断是否选中至少一个转录组
    	   var _this=this,
    	          selectedBlockNum=this.$selectArea.find("div.compa-block").size();
    	   
    	   if(selectedBlockNum<this.MIN_BLOCK_NUM || selectedBlockNum>this.MAX_BLOCK_NUM){
    		   utils.showPopErrorInfor(this.$selectArea, "请选择一个转录组", "right");
    		   return false;
    	   }else{
    		   utils.hidePopErrorInfor(this.$selectArea);
    	   }
    	   
    	   //获取操作类型
    	   var isSSR=this.$ssrCheckbox.is(':checked'),
    	          isCDS=this.$cdsCheckbox.is(':checked');
    	   
    	   //在【样本间分析】插入一条记录
    	   var buildResult=rnaUtils.buildAsssembleAndCompaGroupInnerHtml(this.$selectArea.find("div.compa-block")),
    	          blockHtml=buildResult.blockHtml,
    	          groupAry=buildResult.groupKeyArray,
    	          panelHtml=this.groupAssemblePanelTemplate.replace("{title}",this.TITLE)
    	   																						 .replace("{block-area}",blockHtml)
    	   																						 .replace("{cds-checked-status}",isCDS?"checked='checked'":"")
    	   																						 .replace("{ssr-checked-status}",isSSR?"checked='checked'":""),
    	   		 $panel=$(panelHtml);
    	   
		      	   //向seqFiles添加groupAssemble字段
		  		   rnaSeqFileStatus.addGroupAssemble(isCDS, isSSR, groupAry);
	  		   
		          //初始化序列文件下拉框
		          //rnaSeqFileDropDownHelper.batchRenderDropdown(groupAry, $panel);    	   
    	   
    	         //显示面板 
		    	 $panel.hide().prependTo(this.$contentBody).slideDown("slow",function(){		  		   
		    	   $panel.css("overflow","visible");
				});
    	   		
    	   //在【订单确认】插入一条记录
		   panelHtml=panelHtml.replace("{close-btn-style}","style='display:none;'")
		   											.replace(/{disabled-status}/g,"disabled='disabled'");
		   rnaOrderTab.insertAssemPanel(panelHtml);
		    	 
		   //为删除按钮注册事件函数
    	 $panel.find("button.close").on("click",function(e){
    		    if(rnaSeqFileStatus.isBindGroup(rnaSeqFileStatus.GROUP_ASSEMBLE_KEY)){
    		    	rnaUtils.openTipDialog("删除样本间分析","该样本间分析被其他转录组选用为序列文件。请解除相应关联后，再尝试删除。");
    		    	return false;
    		    }
    		    
				rnaUtils.openTipDialog("删除样本间分析","确定要删除该样本间分析吗？",function(e){
					_this.deleteGroupAssem(e,groupAry);
				});
				return false;
			});		    	 
    	 
			//更新转录组的comparisonPartner
			rnaGroupStatus.updateGroupsRelation(groupAry);
			
			rnaSeqFileDropDownHelper.setDeafultSelectedValueForAllDropDown();
    	 
    	   //禁用添加按钮
    	 this.$openDialogBtn.attr("disabled","disabled");
    	   
		 //关闭对话框
		this.$creationDialog.modal("toggle");
       },
       
       deleteGroupAssem:function(e,groupAry){
    	   var _this=this,
    	         $targetPanel=$("div.group-assem-panel");
			$targetPanel.slideUp(function(){
				  _this.$openDialogBtn.removeAttr("disabled");
				  $targetPanel.remove();
				  rnaOrderTab.removePanelFromAssemPanel();
			});
			
			rnaSeqFileStatus.deleteSeqFile(rnaSeqFileStatus.GROUP_ASSEMBLE_KEY);
			
			//解除groupKeyArray数组记录的转录组间的关联关系
			rnaGroupStatus.unbindGroupRelation(groupAry);
			
			//视情况解除转录组与序列文件关联关系
			rnaSeqFileStatus.batchUnbindSeqFileAndGroup(groupAry);
       },
       
		updateFileType:function(rowClass,fileType){
			rnaUtils.updateGroupBlockFileType(this.$contentBody, rowClass, fileType);
		},
		
		updateAssembleTypeForDataModel:function(e,assembleType,isChecked){
			var groupAssembleObj=rnaSeqFileStatus.getSeqFile(rnaSeqFileStatus.GROUP_ASSEMBLE_KEY);
			if(groupAssembleObj==undefined || groupAssembleObj.isAssemble==undefined){
				return;
			}
			switch(assembleType){
			   case "cds":
				   groupAssembleObj.isCDS=isChecked;
				   break;
			   case "ssr":
				   groupAssembleObj.isSSR=isChecked;
				   break;
			}
		}
};

/**负责[参考序列比对]标签**/
var rnaCompaTab={
		RNA_DIALOG_TITLE:"【SNP开发】比对组创建",
		GENE_DIALOG_TITLE:"【差异表达分析】比对组创建",
		RNA_DIALOG_DESC:"(两两对比)",
		GENE_DIALOG_DESC:"(两两对比)",
		title:"参考比对组创建",
		RNA_MIN_GROUP_BLOCK_NUMBER:2,
		RNA_MAX_GROUP_BLOCK_NUMBER:2,
		GENE_MIN_GROUP_BLOCK_NUMBER:2,
		GENE_MAX_GROUP_BLOCK_NUMBER:2,	
		currentCreationType:-1, //rnaCompaStatus.RNA_TYPE表示当前正在创建【SNP开发】比对组。rnaCompaStatus.GENE_TYPE 表示当前正在创建【转基因表达】比对组
		groupBlockClassSuffix:"-block",
		panelTemplate:null,
		compaGroupClassPrefix:"compa-",
		$compaBody:null,
		$rnaCompaBody:null,
		$geneCompaBody:null,
		$compaGroupCreationDialog:null,
		$compaDialogTitle:null,
		$compaDialogDesc:null,
		$dialogNameText:null,
		$avaiGroupArea:null,
		$comapGroupArea:null,
		dropAndDrag:null,
		rnaDropAndDrag:null,
		geneDropAndDrag:null,
		
		init:function(){
		   var _this=this;
	       this.panelTemplate=$("#compa-group-template").html();
	       this.$compaBody=$("#rna-compa-body");
	       this.$rnaCompaBody=$("#rna-compa-rna-body");
	       this.$geneCompaBody=$("#rna-compa-gene-body");
	       this.$compaGroupCreationDialog=$("#rna-seq-compa-dialog");
	       this.$compaDialogTitle=$("#rna-seq-compa-dialog h4#modal-title");
	       this.$compaDialogDesc=$("#rna-seq-compa-dialog span#dialog-desc");
	       this.$dialogNameText=$("#compa-name");
	       this.$avaiGroupArea=$("#avai-group-area");
	       this.$comapGroupArea=$("#compa-group-area");
	       this.currentCreationType=rnaCompaStatus.RNA_TYPE;
	       this.rnaDropAndDrag=new DropAndDrag(this.RNA_MIN_GROUP_BLOCK_NUMBER,
	    		                                                                         this.RNA_MAX_GROUP_BLOCK_NUMBER,
	    		                                                                         this.$avaiGroupArea,
	    		                                                                         this.$comapGroupArea,
	    		                                                                         this.groupBlockClassSuffix);
	       
	       this.geneDropAndDrag=new DropAndDrag(this.GENE_MIN_GROUP_BLOCK_NUMBER,
																			               this.GENE_MAX_GROUP_BLOCK_NUMBER,
																			               this.$avaiGroupArea,
																			               this.$comapGroupArea,
																			               this.groupBlockClassSuffix);
	       $("div.rna-compa-tabs-left a#rna-tab").on("click",function(e){
	    	   _this.currentCreationType=rnaCompaStatus.RNA_TYPE;
	       })
	       
	       $("div.rna-compa-tabs-left a#gene-tab").on("click",function(e){
	    	   _this.currentCreationType=rnaCompaStatus.GENE_TYPE;
	       })
	       
		   //打开转录组添加出对话框，准备为【SNP开发】创建比对组
	       $("#rna-open-compa-rna-group-btn").on("click",function(e){
	    	   _this.dropAndDrag=_this.rnaDropAndDrag;
	    	   _this.openCompaGroupCreationDialog(e,_this.RNA_DIALOG_TITLE,
	    			   																	_this.RNA_DIALOG_DESC);
	       });
	       
		   //打开转录组添加出对话框，准备为【转基因表达】创建比对组
	       $("#rna-open-compa-gene-group-btn").on("click",function(e){
	    	   _this.dropAndDrag=_this.geneDropAndDrag;
	    	   _this.openCompaGroupCreationDialog(e,_this.GENE_DIALOG_TITLE,
	    			   																	_this.GENE_DIALOG_DESC);
	       });
	       
	       this.$compaGroupCreationDialog.on("keydown",function(e){
	    	   var code = (e.keyCode ? e.keyCode : e.which);
	    	   if(code ==13){ // entyer key code
	    		   _this.addCompaGroup(e);
	    		   e.preventDefault();
	    	   }
	       });
	       
	       //添加比对组按钮事件
	       $("#rna-add-compa-group-btn").on("click",function(e){
	    	    _this.addCompaGroup(e);
	       });
		},
		
		openCompaGroupCreationDialog:function(e,dialogTitle,dialogDesc){
			//重置对话框内容
			var $dialogNameText=this.$dialogNameText,
			       $comapGroupArea=this.$comapGroupArea;
			
			$dialogNameText.val("");
			 utils.hideErrorInfor($dialogNameText);
			 
			 $comapGroupArea.empty();
			 utils.hidePopErrorInfor($comapGroupArea);
			 
			 this.$avaiGroupArea.empty();
			 
			 //向【可用组】区域插入所有可能转录组
			 this.dropAndDrag.fillAvaiArea();
			 	
			 this.$compaDialogTitle.html(dialogTitle);
			 this.$compaDialogDesc.html(dialogDesc);
			
			//打开对话框
			this.$compaGroupCreationDialog.modal("toggle");
			
			setTimeout(function(){
				$dialogNameText[0].focus();
			},500);
		},
		
		addCompaGroup:function(e){
			var _this=this,
			      $comapGroupArea=this.$comapGroupArea,
			       $compaGroupList=$comapGroupArea.find("div.compa-block"),
			       $dialogNameText=this.$dialogNameText,
			       compaBlockNum=$compaGroupList.size(),
			       name=$dialogNameText.val(),
			       currentType=this.currentCreationType,
			       minBlockNum=0,
			       maxBlockNum=0,
			       groupKey=null,
			       groupKeyArray=null,
			       $groupBlock=null,
			       panelClass=null,
			       blockHtml="",
			       compaGroupHtml="",
			       $compaGroup=null,
			       $compaBody=null;
			
			//获取当前比对组类型
			switch(currentType){
			case rnaCompaStatus.RNA_TYPE:
				minBlockNum=this.RNA_MIN_GROUP_BLOCK_NUMBER;
				maxBlockNum=this.RNA_MAX_GROUP_BLOCK_NUMBER;
				$compaBody=this.$rnaCompaBody;
				break;
			case rnaCompaStatus.GENE_TYPE:
				minBlockNum=this.GENE_MIN_GROUP_BLOCK_NUMBER;
				maxBlockNum=this.GENE_MAX_GROUP_BLOCK_NUMBER;
				$compaBody=this.$geneCompaBody;
				break;
			}
			
			//判断比对组名称是否为空
			if(name.replace(" ","")===""){
				utils.showErrorInfor($dialogNameText, "请输入比对组名称。");
				return;
			}
			
			//判断比对组名称是否可用
			if(!rnaCompaStatus.isAvailableCompaName(name, currentType)){
				utils.showErrorInfor($dialogNameText, "比对组名称：<strong>"+name+"</strong>。已经存在，请重新输入。");
				return;				
			}
			
			utils.hideErrorInfor($dialogNameText);
			
			//判断参与比对的转录组块个数是否满足条件
			if(compaBlockNum<minBlockNum || compaBlockNum>maxBlockNum){
				if(minBlockNum===maxBlockNum){
					//rnaUtils.openTipDialog(this.title,"该比对组需要<strong>"+minBlockNum+"</strong>个转录组参与比对。请重新选择。");
					utils.showPopErrorInfor($comapGroupArea, "该比对组需要<strong>"+minBlockNum+"</strong>个转录组参与比对。请重新选择。", "right");
				}else{
					//rnaUtils.openTipDialog(this.title,"该比对组需要<strong>"+minBlockNum+"</strong>到<strong>"+maxBlockNum+"</strong>个转录组参与比对。请重新选择。");
					utils.showPopErrorInfor($comapGroupArea, "该比对组需要<strong>"+minBlockNum+"</strong>到<strong>"+maxBlockNum+"</strong>个转录组参与比对。请重新选择。", "right");
				}
				return;							
			}
			
			utils.hidePopErrorInfor($comapGroupArea);
			
			//构建所有选中转录组块的展示HTML
			var buildResult=rnaUtils.buildAsssembleAndCompaGroupInnerHtml($compaGroupList);
			groupKeyArray=buildResult.groupKeyArray;
			blockHtml=buildResult.blockHtml;
			
			//判断参与比对的转录组组合是否可用
			if(!rnaCompaStatus.isAvaliableGroupBlockList(groupKeyArray, currentType)){
				rnaUtils.openTipDialog(this.title,"已经有相同配置的转录组组合。请重新选择比对组。");
				return;
			}
			
			//生成比对组面板
			panelClass=this.compaGroupClassPrefix+rnaUtils.getTime();
			compaGroupHtml=this.panelTemplate.replace(/{compa-group-class}/g,panelClass)
																								 .replace("{title}",name)
																								 .replace("{block-area}",blockHtml);			
			$compaGroup=$(compaGroupHtml);
			
	          //初始化序列文件下拉框
	          rnaSeqFileDropDownHelper.batchRenderDropdown(groupKeyArray, $compaGroup);    
	          
	          //显示比对面板
			$compaGroup.hide().prependTo($compaBody).slideDown("slow",function(){
				$compaGroup.css("overflow","visible");
			});
			
			//向【订单确认】页面插入比对组面板
			rnaOrderTab.insertCompaPanel(compaGroupHtml, currentType,groupKeyArray);
			
			//为删除按钮注册事件函数
			$compaGroup.find("button.close").on("click",function(e){
				rnaUtils.openTipDialog("删除比对组","确定要删除该比对组吗？",function(){
					_this.deleteCompaGroup(e,panelClass,name,groupKeyArray);
					rnaOrderTab.deleteCompaGroup(panelClass);
				});
				return false;
			});
			
			//记录当前比对组配置
			rnaCompaStatus.addCompaRecord(name, groupKeyArray, currentType);
						
			//更新转录组的comparisonPartner
			rnaGroupStatus.updateGroupsRelation(groupKeyArray);
			
			//关闭对话框
			this.$compaGroupCreationDialog.modal("toggle");
		},
		
		deleteCompaGroup:function(e,groupClass,compaName,groupKeyArray){
			var $compaGroup=this.$compaBody.find("."+groupClass);
			if($compaGroup.size()==0){
				return;
			}
			
			//解除groupKeyArray数组记录的转录组间的关联关系
			rnaGroupStatus.unbindGroupRelation(groupKeyArray);
			 
			 //去除该比对组记录在rnaCompaStatus的配置信息
			 rnaCompaStatus.deleteCompaRecord(compaName, this.currentCreationType);
			 
			 
			//视情况解除转录组与序列文件关联关系
				rnaSeqFileStatus.batchUnbindSeqFileAndGroup(groupKeyArray);
			 
			 //将该比对组面板从【参考序列比对】标签页中删除
			 $compaGroup.slideUp(function(){
				 $compaGroup.remove();
			 });
		},
		
		insertFileToGroupBlock:function(groupKey,rowObj,fileNumber,rowClass,fileType){
			if(fileNumber!=3){
				return;
			}
	    	rnaUtils.insertFileToGroupBlock(groupKey,rowObj,this.$compaBody,rowClass,fileType);
		},
		
		toggleGroupBlockTipInfo:function(groupKey,isShow){
			      rnaUtils.toggleGroupBlockTipInfo(groupKey,isShow,this.$compaBody);
		},
		
		updateFileType:function(rowClass,fileType){
			rnaUtils.updateGroupBlockFileType(this.$compaBody, rowClass, fileType);
		}
};

/**负责[订单确认]标签**/
var rnaOrderTab={
		dataPanelTemplate:null,
		assemPanelTemplate:null,
		
		$dataBody:null,
		$seqFileTbody:null,
		$assemBody:null,
		$compaBody:null,
		$rnaCompaBody:null,
		$geneCompaBody:null,
		
		dataFileTbodyIdSuffix:"-order-data-tbody",
		assemFileTbodyIdSuffix:"-order-assem-tbody",
		
		dataPanelIdPrefix:"order-data-pa-id-",
		assemPanelIdPrefix:"order-asse-pa-id-",
		
		init:function(assemPanelTemplate){
			if(typeof assemPanelTemplate ==="string"){
				this.assemPanelTemplate=assemPanelTemplate;
			}else{
				this.assemPanelTemplate=$("#group-assemble-panel-template").html();
			}
			
			this.dataPanelTemplate=$("#order-upload-data-panel-template").html();
			this.$dataBody=$("#rna-order-data-file-tab");
			this.$seqFileTbody=$("#rna-order-seq-file-tbody");
			this.$assemBody=$("#rna-order-assem-panel div.panel-body");
			this.$compaBody=$("#rna-order-compa-body");
			this.$rnaCompaBody=$("#rna-order-compa-rna-body");
			this.$geneCompaBody=$("#rna-order-compa-gene-body");
		},
		
		insertUploadDataPanel:function(e,key,groupName){
			var _this =this,
			        id=this.dataPanelIdPrefix+key,
				    panelColId=key+"-order-data-panel",
	                tbodyId=key+this.dataFileTbodyIdSuffix,
	                $panelObj=null;	
	
	       $panelObj=rnaUtils.dealFilePanelTemplate(this.dataPanelTemplate,key,groupName,id,panelColId,tbodyId,this.$dataBody);
		},
		
		insertAssemPanel:function(panelHtml){
			this.$assemBody.append(panelHtml);
		},
		
		insertCompaPanel:function(compaGroupHtml,type,groupKeyArray){
			compaGroupHtml=compaGroupHtml.replace("{close-btn-style}","style='display:none;'");
			var $compaBody=null;
			switch(type){
			case rnaCompaStatus.RNA_TYPE:
				$compaBody=this.$rnaCompaBody;
				break;
			case rnaCompaStatus.GENE_TYPE:
				$compaBody=this.$geneCompaBody;
				break;
			}
			if($compaBody && $compaBody.size()>0){
				$compaBody.prepend(compaGroupHtml);
				var isShowErrorTip=false,
				       defaultSeqFileKey=null,
				       defaultSeqFile=null,
				       defaultSeqFileName=null;
				if(rnaSeqFileStatus.getSeqFileCount()===0){
					isShowErrorTip=true;
				}else{
					isShowErrorTip=false;
				    defaultSeqFileKey=rnaSeqFileDropDownHelper.getDefaultSeqFileKey();
				    defaultSeqFile=rnaSeqFileStatus.getSeqFile(defaultSeqFileKey);
				    if(defaultSeqFile){
				    	defaultSeqFileName=defaultSeqFile.name;
				    }
				}
				$.each(groupKeyArray,function(index,groupKey){
					rnaSeqFileDropDownHelper.updateSeqFileForOrderTab(groupKey,defaultSeqFileName,isShowErrorTip);
				});
				
			}
		},
		
		
		insertFileToSeqFilePanel:function(rowObj,rowClass,fileType){
			rnaUtils.insertReadRowToTable(rowObj,rowClass,this.$seqFileTbody,fileType);
		},
		
		insertFileToDataPanel:function(rowObj,rowClass,groupKey,isAllowSampleAnalysis,fileType){
			var $panel=$("#"+this.dataPanelIdPrefix+groupKey),
			     $tbody=$("#"+groupKey+this.dataFileTbodyIdSuffix),
			     rowSize=0;
			
			rnaUtils.insertReadRowToTable(rowObj,rowClass,$tbody,fileType);
			rowSize=$tbody.children().size();
			if(rowSize===0){
				rnaUtils.showOrHideErrorTipInfor($panel, false,true);
			}else{
				rnaUtils.showOrHideErrorTipInfor($panel, !isAllowSampleAnalysis);
			}
		
		},
		
		insertFileToGroupBlock:function(groupKey,rowObj,fileNumber,rowClass,fileType){
			if(fileNumber!=3){
				return;
			}
	    	rnaUtils.insertFileToGroupBlock(groupKey,rowObj,this.$compaBody,rowClass,fileType);
		},
		
		updateFileTypeForDataPanel:function(rowClass,fileType,groupKey,isAllowSampleAnalysis){
			var $panelObj=$("#"+this.dataPanelIdPrefix+groupKey),
			       $targetTd=this.$dataBody.find("tr."+rowClass+" td.type-col"),
		           newFileTypeStr=rnaFileTypeDrowdownHelper.assessFileType(fileType);
		
		       $targetTd.html(newFileTypeStr);
		       
		       rnaUtils.showOrHideErrorTipInfor($panelObj, !isAllowSampleAnalysis);
		},
		
		updateFileTypeForAssembPanel:function(rowClass,fileType){
			rnaUtils.updateGroupBlockFileType(this.$assemBody, rowClass, fileType);
		},
		
		updateAssembTypeForAssemPanel:function(assembType,isChecked){
			var $assembCheckbox=null;
			switch(assembType){
			case "cds":
				$assembCheckbox=this.$assemBody.find("input[data-type='cds']");
				break;
			case "ssr":
				$assembCheckbox=this.$assemBody.find("input[data-type='ssr']");
				break;
			}
			
			if($assembCheckbox && $assembCheckbox.size()==1){
				$assembCheckbox.attr("checked",isChecked);
			}
		},
		
		updateFileTypeForCompaPanel:function(rowClass,fileType){
			rnaUtils.updateGroupBlockFileType(this.$compaBody, rowClass, fileType);
		},
		
		deleteFileFromDataPanel:function(targetRowClass,groupKey,isAllowSampleAnalysis){
			this.$dataBody.find("."+targetRowClass).remove();
			
			var $panelObj=$("#"+this.dataPanelIdPrefix+groupKey);
			rnaUtils.showOrHideErrorTipInfor($panelObj, !isAllowSampleAnalysis);
		},
		
		deleteFileFromSeqFilePanel:function(targetRowClass){
			this.$seqFileTbody.find("."+targetRowClass).remove();
		},
		
		deleteFileFromAssemPanel:function(targetRowClass,groupKey,isAllowSampleAnalysis){
			var  $panel=$("#"+this.assemPanelIdPrefix+groupKey),
	        		$targetRow=$panel.find("tr."+targetRowClass),
	        		$tbody=$targetRow.parent();
     
		     $targetRow.remove();
		     
		     rnaUtils.updateTypeCheckboxAndErrorTipInforStatus(this.assemPanelIdPrefix, groupKey, isAllowSampleAnalysis);
		     
		},
		
		deleteCompaGroup:function(compaGroupClass){
			this.$compaBody.find("."+compaGroupClass).remove();
		},
		
		removeGroupPanelFromDataPanel:function(e,groupKey){
			this.$dataBody.find("."+groupKey).remove();
		},
		
		removePanelFromAssemPanel:function(){
		    this.$assemBody.empty();
		},
		
		toggleGroupBlockTipInfo:function(groupKey,isShow){
		      rnaUtils.toggleGroupBlockTipInfo(groupKey,isShow,this.$compaBody);
	   }
};

var rnaUtils={
		tipDialogTemplate:null,
		$tipDialog:null,
		$tipDialogTitle:null,
		$tipDialogContent:null,
		$tipDialogFooter:null,
		$tipOkBtn:null,
		fileInfoContentTemplate:null,
		init:function(){
			this.fileInfoContentTemplate=$("#compa-block-file-info-content-template").html();
		},
		
		getTime:function(){
			return (new Date()).getTime();
		},
		
		dealFilePanelTemplate:function(template,key,groupName,panelId,panelColId,tbodyId,$dataBody){
			var panelHtml=template.replace(/{key}/g,key)
			                              .replace("{id}",panelId)
										  .replace(/{panel-col-id}/g,panelColId)
										  .replace(/{group-name}/g,groupName)
										   .replace(/{href-panel-col-id}/g,"#"+panelColId)
										  .replace(/{tbody-id}/g,tbodyId),
						 $panel=$(panelHtml);
			
			$panel.hide().prependTo($dataBody).slideDown(function(){
				$panel.css("overflow","visible");
		    });
		    return $panel;
		},
		
		insertReadRowToTable:function(rowObj,rowClass,$tbody,fileType){
			var rowContent='<tr class='+rowClass+'>'
			                             +'<td class="type-col">'+rnaFileTypeDrowdownHelper.assessFileType(fileType)+'</td>'
			                             +'<td>'+rowObj.filename+'</td>'
				                         +'<td>'+rowObj.showfilesize+'</td>'
				                         +'<td>'+rowObj.resoucename+'</td>'
				                         +'</tr>';
			$tbody.append(rowContent);
		},
		
		insertFileToGroupBlock:function(groupKey,rowObj,$compaBody,rowClass,fileType){
			var _this=this,
			        groupBlockClass="."+groupKey+rnaCompaTab.groupBlockClassSuffix,
			        fileTypeIconClass=rnaFileTypeDrowdownHelper.getIconClassByFileType(fileType),
			        $groupBlock=null,
			        fileInfoContentStr=null,
			        fileInfoStr=null;
			
			$compaBody.find(groupBlockClass).each(function(index,ele){
				$groupBlock=$(ele);
				//插入一个文件名
				fileInfoContentStr=_this.fileInfoContentTemplate.replace("{icon-class}",fileTypeIconClass?fileTypeIconClass:rnaFileTypeDrowdownHelper.EMPTY_ICON_CLASS)
																														 .replace("{file-name}",rowObj.filename);
				fileInfoStr=_this.fileInfoTemplate.replace("{row-class}",rowClass)
																							 .replace("{file-info-content}",fileInfoContentStr);
				
				$groupBlock.find(".file-info-area").append(fileInfoStr);
				//隐藏提示信息
				$groupBlock.find(".tip-info").hide();
			})
		},
		/**
		 * 
		 * @param title
		 * @param content
		 * @param okCB  回调函数。当返回值为true时，不关闭当前对话框。返回false或什么都没有返回，关闭当前对话框。
		 */
		openTipDialog:function(title,content,okCB){
			if(this.$tipDialog==null){
				this.$tipDialog=$("#tip-dialog");
				this.$tipDialogTitle=this.$tipDialog.find("h4.modal-title");
				this.$tipDialogContent=this.$tipDialog.find("div.modal-body");
				this.$tipDialogFooter=this.$tipDialog.find("div.modal-footer");
				this.$tipOkBtn=this.$tipDialog.find("#rna-tip-ok-btn");
			}
			var _this=this;
			if(title){
				this.$tipDialogTitle.html(title);
			}
			
			if(content){
				this.$tipDialogContent.html(content);
			}
			
			if(typeof okCB ==="function"){
				this.$tipDialogFooter.show();
				
				this.$tipOkBtn.off().on("click",function(e){
					if(!okCB(e,_this.$tipDialog)){
						_this.$tipDialog.modal('hide');
					}
					_this.$tipOkBtn.off();
				});
			}else{
				this.$tipDialogFooter.hide();
			}
			
			this.$tipDialog.modal('show');
		},
		
		toggleGroupBlockTipInfo:function(groupKey,isShow,$contanier){
			var groupBlockClass=groupKey+rnaCompaTab.groupBlockClassSuffix,
			      $groupBlockList=$contanier.find("."+groupBlockClass),
			      $tipInfoList =null;
			
			if($groupBlockList.size()===0){
				return;
			}
			
			$tipInfoList =$groupBlockList.find("div.tip-info");
			if(isShow){
				$tipInfoList.show();
			}else{
				$tipInfoList.hide();
			}
		},
		
		updateGroupBlockFileType:function($blockParent,rowClass,fileType){
			var _this=this,
					$targetDivList=$blockParent.find("div."+rowClass),
					$targetDiv=null,
					fileName="",
					newFileContentStr="",
					iconClass=rnaFileTypeDrowdownHelper.getIconClassByFileType(fileType) ;
		
			$targetDivList.each(function(index,ele){
				$targetDiv=$(ele);
				fileName=$targetDiv.text();
				newFileContentStr=_this.fileInfoContentTemplate.replace("{icon-class}",iconClass)
					                                                                                     					.replace("{file-name}",fileName);
				$targetDiv.html(newFileContentStr);
			});			
		},
		
		updateAssemType:function($checkboxList,triggerType,checkedStatus,$assembleCheckbox,$cdsCheckbox,$ssrCheckbox){
		       if($assembleCheckbox===undefined){
		    	     $assembleCheckbox=$checkboxList.eq(0),
		       		  $cdsCheckbox=$checkboxList.eq(1),
		              $ssrCheckbox=$checkboxList.eq(2);		    	   
		       }
		       
		       if(triggerType==="assemble"){
					if(checkedStatus===false){
						$cdsCheckbox.removeAttr("checked");
						$ssrCheckbox.removeAttr("checked");
					}
		       }else if(triggerType==="cds" || triggerType==="ssr"){
					if(checkedStatus===true){
						$assembleCheckbox.attr("checked","checked");
					}
		       }		
		},
		
		showOrHideErrorTipInfor:function($panelObj,isShowErrorTip,isShowNoramlTip){
/*			var $errorTip=$panelObj.find("div.tip-info.error-block"),
			        $normalTip=$panelObj.find("div.tip-info.normal-block");
			
			if(isShowNoramlTip==true){
				$errorTip.hide();
				$normalTip.show();
			}else if(isShowErrorTip==true ){
				$normalTip.hide();
				$errorTip.show();
			}else if(isShowErrorTip==false){
				$errorTip.hide();
				$normalTip.hide();
			}*/
			
			var $normalTip=$panelObj.find("div.tip-info.normal-block");
			if(isShowErrorTip==true){
				$normalTip.show();
			}else if(isShowErrorTip==false){
				$normalTip.hide();
			}
		},
		
		updateTypeCheckboxAndErrorTipInforStatus:function(assemPanelIdPrefix,groupKey,isAllowSampleAnalysis){
			var  $panel=$("#"+assemPanelIdPrefix+groupKey),
			        $checkBoxList=$panel.find("div.checkbox-c input"),
			        $tipInfo=$panel.find("div.tip-info");
			
				if(isAllowSampleAnalysis==true){
				    $checkBoxList.removeAttr("disabled").attr("checked","checked");
					$tipInfo.hide();
				}else{
					$checkBoxList.removeAttr("checked").attr("disabled","disabled");
					$tipInfo.show();
				}
		},
		
		buildAsssembleAndCompaGroupInnerHtml:function($groups){
			if($groups==undefined || $groups.size()==0){
				return "";
			}
			
			var $groupBlock=null,
			        blockHtml="",
			        groupKey="",
			        groupSize=$groups.size(),
			        groupKeyArray=[];
			$groups.each(function(index,ele){
				$groupBlock=$(ele);
				groupKey=$groupBlock.data("key");
				
				//生成转录组块HTML代码。 每行只放2个转录组块
				index++;
				if(index%2!=0 && index!=groupSize){
					blockHtml+='<div class="block-row">'+$groupBlock[0].outerHTML;
				}else if(index%2!=0 && index==groupSize){
					//最后一个转录组的位置索引是奇数
					blockHtml+='<div class="block-row">'+$groupBlock[0].outerHTML+'<div class="clear"></div></div>';
				}else{
					blockHtml+=$groupBlock[0].outerHTML+'<div class="clear"></div></div>';
				}
				groupKeyArray.push(groupKey);
			});			
			
			return {
				"blockHtml":blockHtml,
				"groupKeyArray":groupKeyArray
			};
		}
};

var rnaGroupStatus={
		groupKeyPrefix:"group",
		groupNamePrefix:"转录组",
		groups:{
			
		},
		/**
		 * groupNames:{
		 *    group-name-1: true,
		 *    group-name-2: true,
		 *     ....
		 * }
		 */
		groupNames:{
			
		},
		KEY_FIELD:"key",
		NAME_FIELD:"name",
		IS_ASSEMBLE_FIELD:"isAssemble",
		IS_CDS_FIELD:"isCDS",
		IS_SSR_FIELD:"isSSR",
		IS_THREE_FILES_FIELD:"isThreeFiles",
		COMPA_PARTNER_FIELD:"compaPartner",
		DATA_FILES_FIELD:"dataFiles",
	
		addGroup:function(groupKey,groupName,fileName,rowClass){
			if(groupKey===undefined || groupKey===null){
				return;
			}
			
			var dataFiles={
					fileSize:0
			       };
/*			       groupName=groupKey.replace(this.groupKeyPrefix,this.groupNamePrefix);*/
			
			if(fileName && rowClass){
				dataFiles.rowClass={
						name:fileName,
						fileType:rnaFileTypeDrowdownHelper.EMPTY_VALUE
				};
			}
			
			this.groupNames[groupName]=true;
			
			return this.groups[groupKey]={
					key:groupKey,
					name:groupName,
					isAssemble:true,
					isCDS:true,
					isSSR:true, 
					compaPartner:[],
					dataFiles:dataFiles
			};
		},
		
		deleteGroup:function(groupKey){
			var group=this.getGroup(groupKey),
			       groupName=group.name;
			
			delete this.groups[groupKey];
			delete this.groupNames[groupName];
		},
		
		updateGroupAttr:function(groupKey,attrName,value){
			var group=this.getGroup(groupKey);
			group[attrName]=value;
		},
	
		insertFileToGroup:function(groupKey,fileName,fileType,source,fileSize,actualSize,rowClass,directory){
			var group=this.getGroup(groupKey),
			       dataFiles=group[this.DATA_FILES_FIELD];
			
			if(fileName && fileType && rowClass){
				dataFiles[rowClass]={
						name:fileName,
						fileType:fileType,
						source:source,
						actualSize:actualSize,
						fileSize:fileSize,
						directory:directory
				};
				dataFiles.fileSize++;
			}
		},
		
		deleteFileFromGroup:function(groupKey,rowClass){
			var group=this.getGroup(groupKey),
			       dataFiles = group[this.DATA_FILES_FIELD],
			       filterDataFiles=null,
			       isRemoved=false;
			
			if(dataFiles.fileSize>0 && rowClass){
				delete dataFiles[rowClass];
				dataFiles.fileSize--;
			}
		},
		
		updateFileTypeForGroupFile:function(groupKey,rowClass,newFileType){
			var group=this.getGroup(groupKey);
		    group[this.DATA_FILES_FIELD][rowClass]["fileType"]=newFileType;
		},
		
		//将转录组间相应的groupKey存放到各自的compaPartner数组字段中
		updateGroupsRelation:function(groupKeyAry){
			//更新转录组的comparisonPartner
			var length=groupKeyAry.length,
			       groupKey=null,
			       parentKey=null;
			for(var i=0;i<length;i++){
				groupKey=groupKeyAry[i];
				for(var j=0;j<length;j++){
					//i=j说明groupKeyArray[j]得到的parentKey与groupKey是同一个group key
					if(i==j){
						continue;
					}
					parentKey=groupKeyAry[j];
					rnaGroupStatus.insertCompaPartner(groupKey,parentKey);
				}
			}
		},
		
		//解除groupKeyArray数组记录的转录组间的关联关系
	    unbindGroupRelation:function(groupKeyArray){
	    	if(!(groupKeyArray instanceof Array) || groupKeyArray.length==0){
	    		return;
	    	}
	    	
	    	var groupKeyArrayLength=groupKeyArray.length,
	    	       groupKey=null,
	    	       parentKey=null;
			for(var i=0;i<groupKeyArrayLength;i++){
				groupKey=groupKeyArray[i];
				for(var j=0;j<groupKeyArrayLength;j++){
					//i=j说明groupKeyArray[j]得到的parentKey与groupKey是同一个group key
					if(i===j){
						continue;
					}
					parentKey=groupKeyArray[j];
					rnaGroupStatus.deletePartnerKey(groupKey,parentKey);
				}
			}
	    },
		
		
		deletePartnerKey:function(groupKey,partnerKey){
			var group=this.groups[groupKey];
			if(group===undefined){
				return;
			}
			
			var partnerKeyArray = group[this.COMPA_PARTNER_FIELD];
			if( partnerKeyArray.length==0){
				return ;
			}
			var tempArray=[],
			        ele=null;
			for(var i=0;i<partnerKeyArray.length;i++){
				    ele=partnerKeyArray[i];
				    if(ele===partnerKey){
				    	//当第一次遇到元素与partnerKey匹配时，将parnerKey置空，保证后面与未置空前的parnerKey匹配的元素不被删除。因为有可能group1与group2有两种比对情况，只是比对类型不一样而已。
				    	partnerKey=null;
				    }else{
				    	tempArray.push(ele);
				    }
			}
			
			group[this.COMPA_PARTNER_FIELD]=tempArray;
		},
		
		insertCompaPartner:function(groupKey,partnerKey){
			var group=this.getGroup(groupKey);
			group[this.COMPA_PARTNER_FIELD].push(partnerKey);
		},
		
		getGroup:function(groupKey){
			var group=this.groups[groupKey];
			if(group===undefined){
				group=this.addGroup(groupKey);
			}
			return group;
		},
		
		/**
		 * 返回true可进行【样本间分析】，反之不行。
		 * 
		 * @param groupKey
		 * @returns {Boolean}
		 */
		isAllowSampleAnalysis:function(groupKey,groupJson){
			var hasForwardReadFile=false,
			       hasReverseReadFile=false,
			       hasEmptyValue=false,
			       fileJson=null,
			       group=null,
			       dataFiles=null;
			
			if(typeof groupJson ==="object"){
				group=groupJson;
			}else{
				group=this.getGroup(groupKey);
			}
			
			dataFiles=group[this.DATA_FILES_FIELD];
			
			if(dataFiles.fileSize<2){
				return false;
			}
			
			for(var key in dataFiles){
				if(key=="fileSize"){
					continue;
				}
				
				fileJson=dataFiles[key];
				switch(fileJson.fileType){
				   case rnaFileTypeDrowdownHelper.FORWARD_READ:
					   hasForwardReadFile=true;
					   break;
				   case rnaFileTypeDrowdownHelper.REVERSE_READ:
					   hasReverseReadFile=true;
					   break;
				   case rnaFileTypeDrowdownHelper.EMPTY_VALUE:
					   hasEmptyValue=true;
					   break;
				}
				
				if(hasEmptyValue){
					break;
				}
			}
			
			return !hasEmptyValue && hasForwardReadFile && hasReverseReadFile;
		},
		
		/**
		 * 返回true，需要上传Reference Sequence文件
		 * 如果指定转录组不能进行【样本间分析】返回false
		 * @param groupKey
		 * @returns {Boolean}
		 */
		isUploadReferenceSeqFile:function(groupKey){
			var group=this.groups[groupKey],
			       isAllowSampleAnalysis=this.isAllowSampleAnalysis(groupKey,group);
			
			if(isAllowSampleAnalysis==false){
				return false;
			}
			
			//转录组拼接，CDS预测，SSR开发 都没有选中时，需要上传Reference Sequence文件
			return !(group[this.IS_ASSEMBLE_FIELD] || group[this.IS_CDS_FIELD] || group[this.IS_SSR_FIELD]);
		},
		
		
		
		//返回true，指定组参与了 序列比对和样本间分析
		isCompa:function(groupKey){
			var group=this.groups[groupKey];
			
			if(group===undefined){
				return false;
			}
			
			var compaPartnerArry=group[this.COMPA_PARTNER_FIELD];
			
			return  compaPartnerArry instanceof Array && compaPartnerArry.length>0;
		},
		
		isAvailableGroupName:function(groupName){
			groupName=groupName.replace(" ","");
			return !this.groupNames[groupName];
		},
		
		/**
		 * 当向指定转录组中插入新文件时，获取这个文件默认可用的文件类型。返回值为rnaFileTypeDrowdownHelper的FORWARD_READ，REVERSE_READ，REFER_SEQ属性
		 * 文件类型选用优先级：Forward Read > Reverse Read > Reference Sequence
		 * @param groupKey
		 * @returns {String}
		 */
		getDefaultSelectedFileType:function(groupKey,groupJson){
			var nextFileType;
			var hasForwardReadFile=false,
		       	  hasReverseReadFile=false,
		          hasEmptyValue=false,
		          fileJson=null,
		          group=null,
		          dataFiles=null;
		
		if(typeof groupJson ==="object"){
			group=groupJson;
		}else{
			group=this.getGroup(groupKey);
		}
		
		dataFiles=group[this.DATA_FILES_FIELD];
		
		if(dataFiles.fileSize===0){
			return rnaFileTypeDrowdownHelper.FORWARD_READ;
		}
		
		
		for(var key in dataFiles){
			if(key=="fileSize"){
				continue;
			}
			
			fileJson=dataFiles[key];
			switch(fileJson.fileType){
			   case rnaFileTypeDrowdownHelper.FORWARD_READ:
				   hasForwardReadFile=true;
				   break;
			   case rnaFileTypeDrowdownHelper.REVERSE_READ:
				   hasReverseReadFile=true;
				   break;
			   case rnaFileTypeDrowdownHelper.EMPTY_VALUE:
				   hasEmptyValue=true;
				   break;
			}	
		}	
		
			if(hasEmptyValue || !hasForwardReadFile){
				nextFileType=rnaFileTypeDrowdownHelper.FORWARD_READ;
			}else if(hasForwardReadFile && !hasReverseReadFile){
				nextFileType=rnaFileTypeDrowdownHelper.REVERSE_READ;
			}
			
			return nextFileType;
		},
		
		setSeqFile:function(groupKey,seqFileKey){
			var group=this.getGroup(groupKey);
			group.seqFileKey=seqFileKey;
		},
		
		reset:function(){
			this.groups={};
			this.groupNames={};
		}
};

var rnaCompaStatus={
		RNA_TYPE:0, //SNP开发
		GENE_TYPE:1,
		/*
		 * 记录SNP开发比对组的名称，及相应比对组块下转录组块的groupKey
		 * key-value说明:
		 * key:为比对组的名称
		 * value: 为相应比对组块下转录组块groupKey组成的数组
		 */
		rnaCompa:{},
		/*
		 * 记录转基因表达比对组的名称，及相应比对组块下转录组块的groupKey
		 * key-value说明:
		 * key:为比对组的名称
		 * value: 为相应比对组块下转录组块groupKey组成的数组
		 *       geneCompa:{
                     compa-name-1:["group-key-1","group-key-2"],
                          .....
                 }
		 * 
		 */		
		geneCompa:{},
		
		addCompaRecord:function(compaName,groupBlockArray,type){
			    if(compaName==undefined){
			    	return;
			    }
			    
			    if(!(groupBlockArray instanceof Array)){
			        return;	
			    }
			
			   switch(type){
			    case this.RNA_TYPE:
			    	this.rnaCompa[compaName]=groupBlockArray;
				   break;
			    case this.GENE_TYPE:
			    	this.geneCompa[compaName]=groupBlockArray;
			      break;
			   }			
		},
		
		deleteCompaRecord:function(compaName,type){
			   switch(type){
			    case this.RNA_TYPE:
			    	delete this.rnaCompa[compaName];
				   break;
			    case this.GENE_TYPE:
			    	delete this.geneCompa[compaName];
			      break;
			   }				
		},
		
		/**
		 * 判断指定的比对组名称是否可用. 返回true是可用的。
		 * @param name 
		 * @param type rnaCompaStatus.RNA_TYPE表示RNA开发。rnaCompaStatus.GENE_TYPE表示转基因开发
		 * @returns {Boolean}
		 */
		isAvailableCompaName:function(name,type){
			var isAvaliable=true;
		   switch(type){
		    case this.RNA_TYPE:
		    	isAvaliable=!(this.rnaCompa[name] instanceof Array);
			   break;
		    case this.GENE_TYPE:
		    	isAvaliable=!(this.geneCompa[name] instanceof Array);
		      break;
		   }
		   
		   return isAvaliable;
		},
		
		/**
		 * 返回true表明指定的转录组块组合可用，反之已经存在不可用。
		 * @param groupBlockArray
		 * @param type rnaCompaStatus.RNA_TYPE表示RNA开发。rnaCompaStatus.GENE_TYPE表示转基因开发
		 * @returns {Boolean}
		 */
		isAvaliableGroupBlockList:function(checkedGroupBlockArray,type){
			var isAvaliable=true,
			       compaStatusJson=null,
			       checkedGroupBlockArrayStr=null,
			       currentGroupBlockArray=null;
			
			if(!(checkedGroupBlockArray instanceof Array) || checkedGroupBlockArray.length===0){
				return true;
			}
			       
			   switch(type){
			    case this.RNA_TYPE:
			    	compaStatusJson=this.rnaCompa;
				   break;
			    case this.GENE_TYPE:
			    	compaStatusJson=this.geneCompa;
			      break;
			   }
			   
			   checkedGroupBlockArrayStr=checkedGroupBlockArray.sort().toString();
			   
			   for(var key in compaStatusJson){
				   currentGroupBlockArray=compaStatusJson[key];
				   if(checkedGroupBlockArrayStr===currentGroupBlockArray.sort().toString()){
					   isAvaliable=false;
					   break;
				   }
			   }
			   
			return isAvaliable;
		},
		
		reset:function(){
			this.rnaCompa={};
			this.geneCompa={};
		}
		
};

var rnaSeqFileStatus={
		GROUP_ASSEMBLE_KEY:"groupAssemble",
		count:0,
		seqFiles:{},
		
		addSeqFile:function(key,directory,name,fileSize,actualSize,source){
			if(key==undefined || key==null){
				return;
			}
			
			this.seqFiles[key]={
					"fileSize":fileSize,
					"actualSize":actualSize,
					"fileType":"seq",
					"directory":directory,
					"name":name,
					"source":source,
					"bindGroupKeyAry":[]
			};
			
			this.count++;
		},
		
		addGroupAssemble:function(isCDS,isSSR,groupAry){
			this.seqFiles[this.GROUP_ASSEMBLE_KEY]={
					"name":"样本间分析",
					"isAssemble":true,
					"isCDS":isCDS?isCDS:false,
					"isSSR":isSSR?isSSR:false,
					"groupAry":(groupAry instanceof Array)?groupAry:[],
					"bindGroupKeyAry":[]
			};
			this.count++;
		},
		
		deleteSeqFile:function(key){
			delete this.seqFiles[key];
			this.count--;
		},
		
		getSeqFile:function(key){
			return this.seqFiles[key];
		},
		
		getSeqFileCount:function(){
			return this.count;
		},
		
		isBindGroup:function(key){
			var seqFile=this.getSeqFile(key);
			if(seqFile==undefined){
				console.log("不存在key为:【"+key+"】的序列文件");
				return false;
			}
			
			var bindGroupKeyAry=seqFile.bindGroupKeyAry;
			
			return bindGroupKeyAry instanceof Array && bindGroupKeyAry.length>0;
		},
		
		bindGroup:function(key,groupKey){
			var seqFile=this.getSeqFile(key),
			       group=rnaGroupStatus.getGroup(groupKey),
			       groupBindSeqFileKey=group.seqFileKey;
			
			if(seqFile==undefined){
				console.log("不存在key为:【"+key+"】的序列文件");
				return;
			}
			
			seqFile.bindGroupKeyAry.push(groupKey);
			rnaGroupStatus.setSeqFile(groupKey, key);
		},
		
		unbindGroup:function(key,groupKey){
			var seqFile=this.getSeqFile(key);
			if(seqFile==undefined){
				console.log("不存在key为:【"+key+"】的序列文件");
				return;
			}
			
			var bindGroupKeyAry = seqFile.bindGroupKeyAry,
			       length=bindGroupKeyAry.length;
			if( length==0){
				return ;
			}
			
			var tempArray=[],
			        ele=null;
			for(var i=0;i<length;i++){
				    ele=bindGroupKeyAry[i];
				    if(ele===groupKey){
				    	//当第一次遇到元素与groupKey匹配时，将groupKey置空，保证后面与未置空前的groupKey匹配的元素不被删除。因为一个序列文件，可能被多个转录组使用。
				    	groupKey=null;
				    }else{
				    	tempArray.push(ele);
				    }
			}
			
			seqFile.bindGroupKeyAry=tempArray;
		},
		
		batchUnbindSeqFileAndGroup:function(groupKeyAry){
			if(!(groupKeyAry instanceof Array) ||  groupKeyAry.length==0){
				return;
			}
			
			var _this=this;
			$.each(groupKeyAry,function(index,ele){
				_this.unbindSeqFileAndGroup(ele);
			});
		},
		
		unbindSeqFileAndGroup:function(groupKey){
			var group=rnaGroupStatus.getGroup(groupKey),
			       seqFileKey=null,
			       seqFile=null;
			if(!(typeof group=="object")){
				return ;
			}
			
			seqFileKey=group.seqFileKey;
			if(seqFileKey==""){
				return;
			}
			
			//解除序列文件与转录组块的关联
			rnaSeqFileStatus.unbindGroup(seqFileKey, groupKey);
			
			//当转录组没有参加任何【样本间分析】或【样本比对分析】时，解除其与序列文件的关联
			if(rnaGroupStatus.isCompa(groupKey)==false){
				//解除转录组与序列文件的关联
				rnaGroupStatus.setSeqFile(groupKey, "");
			}
	
		},
		
		reset:function(){
			this.seqFiles={};
			this.count=0;
		}
		
};

var rnaFileTypeDrowdownHelper={
		EMPTY_VALUE:-1,
		EMPTY_LABEL:"请选择一个文件类型",
		EMPTY_ICON_CLASS:"glyphicon-question-sign",
		FORWARD_READ:"left",
		REVERSE_READ:"right",
		SEQ_FILE:"seq",
		fileTypeDropdwonItems:null, //数组类型
		fileTypeTemplate:null,
		avilableItemObj:null, //记录文件类型下拉框下的所有可用选项
		allFileType:null, //记录所有文件类型。注意在文件类型下拉框中可见的文件类型在avilableItemObj配置
		init:function(){
			var _this=this;
			this.fileTypeTemplate=$("#file-type-template").html();
			this.avilableItemObj={
					left:{
						value:_this.FORWARD_READ,
						label:"Forward Read",
						iconClass:"glyphicon-arrow-right"
					},
					right:{
						value:_this.REVERSE_READ,
						label:"Reverse Read",
						iconClass:"glyphicon-arrow-left"				
					}		
				};
			
			this.allFileType={
					seq:{
						value:_this.SEQ_FILE,
						label:"Reference Sequence",
						iconClass:"glyphicon-align-justify"		
					}
			};
			this.allFileType=$.extend({},this.avilableItemObj,this.allFileType);
		},

		getFileTypeDropdownItems:function(){
			if(this.fileTypeDropdwonItems===null){
				var tempAry=[],
				       avilableItemObj=this.avilableItemObj;
				for(var key in avilableItemObj){
					tempAry.push(avilableItemObj[key]);
				}
				this.fileTypeDropdwonItems=tempAry;
			}
			
			return this.fileTypeDropdwonItems;
		},
		
		getIconClassByFileType:function(fileType){
			if(fileType===this.EMPTY_VALUE){
				return this.EMPTY_ICON_CLASS;
			}
			
			return this.avilableItemObj[fileType]["iconClass"];
		},
		
		assessFileType:function(fileType){
			var dangerStyle="",
			       label="",
			       iconClass="";
			
			if(fileType===undefined || fileType===this.EMPTY_VALUE){
				label=this.EMPTY_LABEL;
				iconClass=this.EMPTY_ICON_CLASS;
				dangerStyle="rna-file-type-danger";
			}else{
				var itemJson=this.allFileType[fileType];
				label=itemJson.label;
				iconClass=itemJson.iconClass;
			}
			
			return this.fileTypeTemplate.replace("{danger-style}",dangerStyle)
																		  .replace("{icon-class}",iconClass)	
																		  .replace("{label}",label);
		}
};

var rnaSeqFileDropDownHelper={
		itemTemplate:'<li {style}><a href="#" data-value="{value}" >{label}</a></li>',
		orderSeqFileTemplate:'<div class="rna-file-type  icon-text" ><span class="glyphicon glyphicon-align-justify"></span>{name}</div> ',
		seqFileCount:0,//记录当前下拉框可用选项个数
		dropdownItemStr:"", //记录下拉框可用选项的html代码结构
		$seqFileEffectTabs:null,
		$orderTab:null,
		
		init:function(){
			var _this=this,
			       $rnaContent=$("div.rna-content");
			
			this.$orderTab=$("#rna-order-compa-panel");
			this.$seqFileEffectTabs=$("div.seq-file-effect-tab");
			
			$rnaContent.on("click","div.seq-file-add-area",function(e){
				_this.addSeqFileBtnClickEventHandler(e, this);
			});
			
			$rnaContent.on("click","div.seq-file-dropdown-area button.dropdown-toggle",function(e){
				_this.expandDropDown(e, this);
			});
			
			$rnaContent.on("click","div.seq-file-dropdown-area span.remove-seq-file-btn",function(e){
				_this.removeSeqFileDropDown(e,this);
			});
			
			//下拉框选项点击事件处理
			$rnaContent.on("click","div.seq-file-dropdown-area ul.dropdown-menu li a ",function(e){
				_this.selectOneItem(e, this);
			});
			
		},
		
		batchRenderDropdown:function(groupKeyAry,$panelContainer){
			if(!(groupKeyAry instanceof Array) || groupKeyAry.length==0){
				return ;
			}
			
			var _this=this,
			      $dropDownAreas=$panelContainer.find("div.seq-file-dropdown-area"),
			      $errorTips=$panelContainer.find("div.error-block");
			
			$dropDownAreas.show();
			
			if(rnaSeqFileStatus.getSeqFileCount()>0){
				$errorTips.hide();
				$dropDownAreas.find("button").removeClass("btn-danger");
				$.each(groupKeyAry,function(index,ele){
					_this.renderDropdown(ele,$panelContainer);
				});
			}else{
				$errorTips.show();
			}
		},
		
		renderDropdown:function(groupKey,$panelContainer){
			var  _this=this,
			        $dropdownGroup =$panelContainer.find("div."+groupKey+"-block div.dropdown-group"),
			        group=rnaGroupStatus.getGroup(groupKey),
			        groupBindSeqFileKey=group.seqFileKey,
			        selectedSeqFile=rnaSeqFileStatus.getSeqFile(groupBindSeqFileKey),
			        selectedSeqFileKey="";
			
			//获取groupKey对应的group使用的序列文件key
			if(selectedSeqFile && selectedSeqFile.name){
				//转录组块已经在其他地方使用且选择了序列文件
				selectedSeqFileKey=groupBindSeqFileKey;
				//令下拉框可见
				this.setDropdownAreVisible($panelContainer,groupKey);
				
				setTimeout(function(){
					//更新订单页面相应转录组块的序列文件标识(延时处理目的：等相应面板被插入订单标签页后，再更改序列文件标识)
					_this.updateSeqFileForOrderTab(groupKey, selectedSeqFile.name);
				},1000);

			}else{
				//转录组块第一次添加序列文件
				selectedSeqFileKey=_this.getDefaultSeqFileKey();
				//令添加序列文件按钮可见
				this.setAddSeqFileBtnVisible($panelContainer, groupKey, null);
			}
			
			//构建下拉框条目
			this.fillDropDown($dropdownGroup, selectedSeqFileKey);
			
			//更新相应group和seqFile状态 
			rnaSeqFileStatus.bindGroup(selectedSeqFileKey, groupKey);
		},
		
		setAddSeqFileBtnVisible:function($panelContainer,groupKey,$addSeqFileBtn){
			if($addSeqFileBtn==null || $addSeqFileBtn==undefined || $addSeqFileBtn.size()==0){
				$addSeqFileBtn=$panelContainer.find("div[data-key='"+groupKey+"'] div.seq-file-add-area");
			}
			$addSeqFileBtn.css("display","block");
		},
		
		setDropdownAreVisible:function($panelContainer,groupKey){
			var $dropdownArea=$panelContainer.find("div[data-key='"+groupKey+"'] div.seq-file-dropdown-area");
			$dropdownArea.css("display","block");
		},
		
		addSeqFileBtnClickEventHandler:function(e,eventObj){
			if(rnaSeqFileStatus.getSeqFileCount()==0){
				rnaUtils.openTipDialog("序列文件选择","您还没有添加序列文件。请到【数据文件】下的【序列文件】标签页添加或者在【样本间分析】标签页下创建转录组拼接。");
				return;
			}
			
			var _this=this,
			       $this=$(eventObj),
			       groupKey=$this.data("groupKey"),
			       defaultSeqFileKey=this.getDefaultSeqFileKey(),
			       defaultSeqFileName=rnaSeqFileStatus.getSeqFile(defaultSeqFileKey).name,
		           $dropdownGroup=null,
		           $dropdownArea=null,
		           $addBtn=null,
		           $labelBtn=null,
		           $dropdownAreas=this.getDropdownAreasJqueryObjByGroupKey(groupKey),
		           currentDropdownItemCount=0,
			       latestSeqFilesCount=rnaSeqFileStatus.getSeqFileCount();
			
			//更新所有同groupKey的转录组块的UI变化
			$dropdownAreas.each(function(index,ele){
				$dropdownArea=$(ele);
				$addBtn=_this.getAddSeqFileBtn($dropdownArea);
				$labelBtn=_this.getLabelBtn($dropdownArea);
				
				//检查是否需要重建下拉框选项
				$dropdownGroup=$dropdownArea.find("div.dropdown-group");
				currentDropdownItemCount=$dropdownGroup.data("childrenSize");
				if(currentDropdownItemCount!=latestSeqFilesCount){
					_this.fillDropDown($dropdownGroup, defaultSeqFileKey);
				}
				
				$labelBtn.data("value",defaultSeqFileKey);
				$labelBtn.html(defaultSeqFileName);
				
				$addBtn.hide();
				$dropdownArea.show();
			});
			
			//更新相应group和seqFile状态 
			rnaSeqFileStatus.bindGroup(defaultSeqFileKey, groupKey);
			
			//更新订单页面序列文件标识
			this.updateSeqFileForOrderTab(groupKey, defaultSeqFileName);
		},
		
		removeSeqFileDropDown:function(e,eventObj){
			var  _this=this,
			       $this=$(eventObj),
			        groupKey=$this.data("groupKey"),
			       $labelBtn=eventObj.$labelBtn,
		           $dropdownArea=null,
		           $addBtn=null,
		           $dropdownAreas=this.getDropdownAreasJqueryObjByGroupKey(groupKey),
		           seqFileKey=null;
			
			if(typeof $labelBtn=="undefined"){
				$labelBtn=$this.parent().find("button.rna-label-btn");
				eventObj.$labelBtn=$labelBtn;
			}
						
			//更新所有同groupKey的转录组块的UI变化
			$dropdownAreas.each(function(index,ele){
				$dropdownArea=$(ele);
				$addBtn=_this.getAddSeqFileBtn($dropdownArea);
								
				$addBtn.show();
				$dropdownArea.hide();
			});
			
			//去除订单页面序列文件标识
			this.updateSeqFileForOrderTab(groupKey, null);
			
			seqFileKey=$labelBtn.data("value");
			
			//解除序列文件与转录组块的关联
			rnaSeqFileStatus.unbindGroup(seqFileKey, groupKey);
			
			//解除转录组与序列文件的关联
			rnaGroupStatus.setSeqFile(groupKey, "");
		},
		
		fillDropDown:function($dropdownGroup,selectedValue){
			var seqFile=null,
			        label="",
			        dropdownGroupObj=$dropdownGroup[0],
			        //$dropdownMenu=dropdownGroupObj.$dropdownMenu,
			        //$labelBtn=dropdownGroupObj.$labelBtn,
			        latestSeqFilesCount=rnaSeqFileStatus.getSeqFileCount(),
			        dropdowItemDomObjCount=$dropdownGroup.data("childrenSize"); //当前下拉框可选项的个数
			
			var selectedLabel = "";
			if(rnaSeqFileStatus.getSeqFile(selectedValue)){
				selectedLabel=rnaSeqFileStatus.getSeqFile(selectedValue).name;
			}
			
			
			//如果是第一次访问，则通过jquery获取相应对象，然后再存放在dropdownGroupObj中，下次使用时，可直接使用
			/*if(typeof $dropdownMenu ==="undefined"){
				$dropdownMenu=$dropdownGroup.find("ul.dropdown-menu");
				$labelBtn=$dropdownGroup.find("button.rna-label-btn");
				dropdownGroupObj.$dropdownMenu=$dropdownMenu;
				dropdownGroupObj.$labelBtn=$labelBtn;
			}*/
			
			//如果序列文件有变化，根据最新的rnaSeqFileStatus.seqFiles重新构建序列文件下拉框条目
			if(this.seqFileCount!=latestSeqFilesCount){
				this.dropdownItemStr="";
				
				//遍历所有序列文件
				for(var key in rnaSeqFileStatus.seqFiles){
					seqFile=rnaSeqFileStatus.getSeqFile(key);
					label =seqFile.name;
					
					//确定选中项
					if(selectedValue==key){
						this.dropdownItemStr+=this.itemTemplate.replace("{style}","checked='checked'")
																					.replace("{value}",key)
																					.replace("{label}",label);					
					}else{
						this.dropdownItemStr+=this.itemTemplate.replace("{style}","")
																					.replace("{value}",key)
																					.replace("{label}",label);					
					}
				}				
				
				$dropdownGroup.data("listSize",latestSeqFilesCount);
			}
			
			//如果当前下拉框的选项过时，则重新构建。
			/*if(dropdowItemDomObjCount!=latestSeqFilesCount){
				$dropdownMenu.html(this.dropdownItemStr);
				$dropdownGroup.data("childrenSize",latestSeqFilesCount);
				
				//记录当前选中项
				$labelBtn.data("value",selectedValue);
				$labelBtn.html(selectedLabel);
			}*/
			
		},
		
		selectOneItem:function(e,eventObj){
			var _this=this,
			       $this=$(eventObj),
	               $ulMenu=$this.parent().parent(),			
			        groupKey=$ulMenu.data("groupKey"),
			        $labelBtn=$ulMenu.parent().find("button.rna-label-btn"),
			        $dropdownArea=null,
			        $dropdownAreas=this.getDropdownAreasJqueryObjByGroupKey(groupKey),
			        oldSelectedValue=$labelBtn.data("value"),
			        newSelectedValue=$this.data("value"),
			        newSelectedLabel=$this.text();
			
			if(oldSelectedValue==newSelectedValue){
				return false;
			}
			
			//更新所有同groupKey的转录组块的UI变化
			$dropdownAreas.each(function(index,ele){
				$dropdownArea=$(ele);
				$labelBtn=_this.getLabelBtn($dropdownArea);
				
				$labelBtn.html(newSelectedLabel);
				$labelBtn.data("value",newSelectedValue);
				
				//将groupKey 从旧序列文件的bindGroupKeyAry中移除
				rnaSeqFileStatus.unbindGroup(oldSelectedValue, groupKey);
							
				//将groupKey添加到新选定seqFile的bindGroupKeyAry数组
				rnaSeqFileStatus.bindGroup(newSelectedValue, groupKey);
			});
			
			//更新订单页面相应转录组块的序列文件信息
			this.updateSeqFileForOrderTab(groupKey, newSelectedLabel);
		},
		
		expandDropDown:function(e,eventObj){
			var _this=this,
			       $this=$(eventObj),
			       $dropdownGroup=eventObj.$dropdownGroup,
			       $lableBtn=eventObj.$lableBtn,
			       groupKey=$this.data("groupKey"),
			       selectedValue="-1",
			       latestSeqFileCount=rnaSeqFileStatus.getSeqFileCount(),
			       currentDropDownSeqFileCount=0;
			
			if(typeof $dropdownGroup=="undefined"){
				$dropdownGroup=$this.parent();
				$lableBtn=_this.getLabelBtn($dropdownGroup);
				
				eventObj.$dropdownGroup=$dropdownGroup;
				eventObj.$lableBtn=$lableBtn;
			}
			
			currentDropDownSeqFileCount=$dropdownGroup.data("listSize");
			
			if(latestSeqFileCount==0){
				rnaUtils.openTipDialog("序列文件选择","您还没有添加序列文件。请到【数据文件】下的【参考基因组】标签页添加或者在【样本间分析】标签页下添加");
				return;
			}
			
			//判断是否重构下拉框选项
			if(currentDropDownSeqFileCount!=latestSeqFileCount){
				selectedValue=$lableBtn.data("value");
				if(selectedValue=="-1"){
					selectedValue=this.getDefaultSeqFileKey();
				}
				this.fillDropDown($dropdownGroup, selectedValue);
			}
		},
		
		updateSeqFileForOrderTab:function(groupKey,seqFileName,isShowErrorTip){
			var $tragetPanel=this.$orderTab.find("div[data-key='"+groupKey+"']"),
			        $orderSeqFileDropdownAreas=$tragetPanel.find("div.seq-file-dropdown-area"),
			        $errorTip=$tragetPanel.find("div.error-block"),
			        orderSeqFileStr=null;
			        
			$errorTip.hide();
			
			if(isShowErrorTip==true){
				$orderSeqFileDropdownAreas.hide();
				$errorTip.show();
			}else if( seqFileName==null || seqFileName==""){
				 $orderSeqFileDropdownAreas.hide();
			}else{
				orderSeqFileStr=orderSeqFileStr=this.orderSeqFileTemplate.replace("{name}",seqFileName);
				$orderSeqFileDropdownAreas.html(orderSeqFileStr).show();
			}
		},
		
		getDropdownAreasJqueryObjByGroupKey:function(groupKey){
			return this.$seqFileEffectTabs.find("div.block-row div[data-key='"+groupKey+"'] div.seq-file-dropdown-area");
		},
		
		getAddSeqFileBtn:function($dropdownArea){
			return $dropdownArea.parent().find("div.seq-file-add-area");
		},
		
		getLabelBtn:function($dropdownArea){
			return $dropdownArea.find("button.rna-label-btn");
		},
		
		getDefaultSeqFileKey:function(){
			var deafultSeqFileKey=rnaSeqFileStatus.GROUP_ASSEMBLE_KEY,
			       deafultSeqFile=rnaSeqFileStatus.getSeqFile(deafultSeqFileKey);
			
			if(typeof deafultSeqFile !="object"){
				for(var key in rnaSeqFileStatus.seqFiles){
					deafultSeqFileKey=key;
					break;
				}
			}
			
			return deafultSeqFileKey;
		},
		
		setDeafultSelectedValueForAllDropDown:function(){
			if(rnaSeqFileStatus.getSeqFileCount()>1){
				return ;
			}
			
			var $labelBtns=this.$seqFileEffectTabs.find("div.block-row div.compa-block button.rna-label-btn"),
		           $expandBtns=this.$seqFileEffectTabs.find("div.block-row div.compa-block button.dropdown-toggle"),
		           $errorTips=this.$seqFileEffectTabs.find("div.block-row div.compa-block div.error-block"),
		           $expandBtn=null,
		           $dropdownGroup=null,
		           defaultSeqFileKey=this.getDefaultSeqFileKey(),
			       defaultSeqFile=rnaSeqFileStatus.getSeqFile(defaultSeqFileKey),
			       defaultSeqFileName=defaultSeqFile?defaultSeqFile.name:null,
		           groupKey=null,
		           group=null,
		           _this=this;
			
			$labelBtns.removeClass("btn-danger");
			$expandBtns.removeClass("btn-danger");
			$errorTips.hide();
			
			$expandBtns.each(function(index,ele){
				$expandBtn=$(ele);
				$dropdownGroup=$expandBtn.parent();
				groupKey=$expandBtn.data("groupKey");
				
				_this.fillDropDown($dropdownGroup, defaultSeqFileKey);
				
				_this.updateSeqFileForOrderTab(groupKey, defaultSeqFileName, false);
				
				rnaSeqFileStatus.bindGroup(defaultSeqFileKey, groupKey);
			});
		},
		
		reset:function(){
			this.seqFileCount=0;
		}
};

function DropAndDrag(minNum,maxNum,$avaiArea,$selecteArea,groupBlockClassSuffix){
    if(this instanceof DropAndDrag){
    	var _this=this;
    	this.minNum=minNum;
    	this.maxNum=maxNum;
    	this.$avaiArea=$avaiArea;
    	this.$selecteArea=$selecteArea;
    	this.groupBlockClassSuffix=groupBlockClassSuffix;
    	this.preSeletectBlockNum=0;
    	this.dragDropObj=null;
    	
    	if(this.fileInfoContentTemplate==null){
    		this.fileInfoContentTemplate=$("#compa-block-file-info-content-template").html();
    	}
    	
    	if(this.fileInfoTemplate==null){
    		 this.fileInfoTemplate=$("#compa-block-file-info-template").html();
    	}
    	
    	if(this.compaBlockTempate==null){
    		 this.compaBlockTempate=$("#compa-block-template").html();
    	}
    	
	    this.dragDropOpt={
	        cursor: 'move', // selector 的鼠标手势 
	        sortBoxs: 'div.selected-area', //拖动排序项父容器 
	        replace: false, //2个sortBox之间拖动替换 
	        items: '> *', //拖动排序项选择器 
	        selector: '', //拖动排序项用于拖动的子元素的选择器，为空时等于item 
	        zIndex: 1000,
	       stopCB:function($currentItem){
	    	   _this.afterDragAndDrop($currentItem);
	       } //拖拽结束后调用。$currentItem为被拖拽元素的jquery对象
	    };
    }else{
    	return new DropAndDrag(minNum,maxNum,$avaiArea,$selecteArea);
    }
};

DropAndDrag.prototype={
		constructor:DropAndDrag,
	    fileInfoContentTemplate:null,
	    fileInfoTemplate:null,
	    compaBlockTempate:null,
	    
		fillAvaiArea:function(){
			var groups=rnaGroupStatus.groups,
			       group=null,
			       dataFiles=null,
			       fileInfoListStr="",
			       fileInfoStr="",
			       fileInfoContentStr="",
			       fileJson=null,
			       iconClass="",
			       groupBlcoksStr="";
			
			 for(var groupKey in groups){
				 fileInfoListStr="";
				 group=groups[groupKey];

				 //只有能够进行【样本间分析】的转录组可进行跨组比对
				 if(!rnaGroupStatus.isAllowSampleAnalysis("", group)){
					 continue;
				 }
					 
				 dataFiles=group[rnaGroupStatus.DATA_FILES_FIELD];
				 
				 //渲染转录组块列表
				 for(var key in dataFiles){
					 if(key==="fileSize"){
						 continue;
					 }
					 
					 fileJson=dataFiles[key];
					 iconClass=rnaFileTypeDrowdownHelper.getIconClassByFileType(fileJson.fileType);
					 
					 fileInfoContentStr=this.fileInfoContentTemplate.replace("{icon-class}",iconClass)
					 																						  .replace("{file-name}",fileJson.name);
					 
					 fileInfoStr=this.fileInfoTemplate.replace("{row-class}",key)
					 															.replace("{file-info-content}",fileInfoContentStr);
					 fileInfoListStr+=fileInfoStr;
				 }
				 
				 groupBlcoksStr+=this.compaBlockTempate.replace("{block-inden-class}",groupKey+this.groupBlockClassSuffix)
				 																				.replace(/{group-key}/g,groupKey)
				 																			   .replace("{group-name}",group[rnaGroupStatus.NAME_FIELD])
				 																			   .replace("{file-info-list}",fileInfoListStr); 					
				 
				 this.$avaiArea.html(groupBlcoksStr);
				//激活拖动功能
				 this.dragDropObj=this.$avaiArea.sortDrag(this.dragDropOpt);
			 }			
		},
		
		afterDragAndDrop:function($currentItem){
			if(!$currentItem || $currentItem.size()===0){
				return;
			}
			
			var parent=$currentItem.parent()[0],
			        currentSeletectBlockNum=this.$selecteArea.find("div.compa-block").size();
			
			
			switch(parent){
			 case this.$avaiArea[0]:
				   //当前转录组块被拖动到【可用组】
				   if( this.preSeletectBlockNum===this.maxNum && currentSeletectBlockNum===(this.maxNum-1)){
					   /*
					    * 满足下面条件，恢复可用组的转录组块的拖拽功能。
					    * 1：前一次操作时，【选中组】区域下的转录组块个数刚好达到允许的最大转录组块个数
					    * 2：当前操作下，【选中组】区域下的转录组块个数等于maxNum-1
					    * 
					    * 避免频繁的拖拽功能激活，提升性能
					    */
					   var $availBlockList=this.$avaiArea.find("div.compa-block");
					   
					   this.dragDropObj.disableDragFeature($currentItem);
					   this.dragDropObj.enableDragFeature(this.dragDropOpt,this.$avaiArea,$availBlockList,null);
					   $availBlockList.removeClass("compa-block-disabled");
				   }
				   break;
			 case this.$selecteArea[0]:
				   //当前转录组块被拖动到【选中组】
			       if(currentSeletectBlockNum===this.maxNum){
			    	   //如果选中组刚好达到转录组块上限个数maxNum。可用组的转录组块需显示禁用样式，同时禁用拖拽功能
			    	   var $availBlockList=this.$avaiArea.find("div.compa-block");
			    	   
			    	   this.dragDropObj.disableDragFeature($availBlockList);
			    	   $availBlockList.addClass("compa-block-disabled");
			       }
				   break;
			}
			  this.preSeletectBlockNum=currentSeletectBlockNum;			
		}
};


/*
 * 		     dropdownItems= [
                     {label:"Forward Read",value:"right",iconClass:"glyphicon-arrow-right",relatedMenuId:-1},
                     {label:"Reverse Read",value:"left",iconClass:"glyphicon-arrow-left",relatedMenuId:-1},
                     {label:"Reference Sequence",value:"seq",iconClass:"glyphicon-align-justify",relatedMenuId:-1}
                  ]
 */
function DropdownMenu(dropdownItems,$dropdownParent,emptyItemLabel,emptyItemValue,emptyItemIconClass){
	if(this instanceof DropdownMenu){
		//获得dropdownItems副本
		this.dropdownItems=JSON.parse(JSON.stringify(dropdownItems));
		
		this.$dropdownParent=$dropdownParent;
		
		if(emptyItemLabel){
			this.EMPTY_LABEL=emptyItemLabel;
		}
		
		if(emptyItemValue){
			this.EMPTY_VALUE=emptyItemValue;
		}
		
		//生成默认空item
		this.defaultItemTemplate=this.itemTemplate.replace("{style}","")
																						  .replace("{value}",this.EMPTY_VALUE)
																						  .replace("{index}","")
		  																				  .replace("{label}",this.EMPTY_LABEL);		
		if(emptyItemIconClass){
			this.EMPTY_ICON_CLASS=emptyItemIconClass;
			this.defaultItemTemplate=this.defaultItemTemplate.replace("{icon-span}",this.iconSpanTemplate.replace("{icon-class}",emptyItemIconClass));
		}else{
			this.defaultItemTemplate=this.defaultItemTemplate.replace("{icon-span}","");
		}

		
		if(DropdownMenu.prototype.dropdownTemplate===null){
			DropdownMenu.prototype.dropdownTemplate=$("#file-type-dropdown-template").html();
		}
	}else{
		return new DropdownMenu(dropdownItems,emptyItemLabel,emptyItemValue);
	}
};

DropdownMenu.prototype={
		constructor:DropdownMenu,
		dropdownTemplate:null,
		defaultItemTemplate:'',
		itemTemplate:'<li {style}><a href="#" data-value="{value}" data-index="{index}" data-menu-parent-id-or-class="{id-or-class}">{icon-span}{label}</a></li>',
		iconSpanTemplate:'<span class="glyphicon {icon-class}" ></span>',
		EMPTY_VALUE:-1,
		EMPTY_LABEL:"请选择一个值",
		
		assemDropdown:function(menuParentClassOrId,$parent,defaultSelectedValue){
			var dropdownStr=this.dropdownTemplate.replace("{selected-value}",this.EMPTY_VALUE)
		        									 								      .replace("{icon-span}",this.EMPTY_ICON_CLASS ? this.iconSpanTemplate.replace("{icon-class}",this.EMPTY_ICON_CLASS):"")
		        									 								      .replace("{selected-item-label}",this.EMPTY_LABEL)
		        									 								      .replace("{ul-menu-id}","menu-"+(new Date()).getTime()),
		            idOrClass=menuParentClassOrId?menuParentClassOrId:"",
		        	itemListStr=this.defaultItemTemplate.replace("{id-or-class}",idOrClass),
		        	itemStr="",
		        	selectedValue=this.EMPTY_VALUE,
		        	selectedIconClass=this.EMPTY_ICON_CLASS?this.EMPTY_ICON_CLASS:"",
		        	selectedItemLabel=this.EMPTY_LABEL,
		        	selectedItemIndex=-1,
		        	menuId="menu-"+(new Date()).getTime(),
		        	style="",
		        	_this=this;
			
			
			//生成下拉选项
			$.each(this.dropdownItems,function(index,item){
				if(item.relatedMenuId===undefined || item.relatedMenuId===_this.EMPTY_VALUE){
					style="";
				}else{
					style='style="display:none;"';
				}
				
				if(item.value===defaultSelectedValue){
					selectedValue=defaultSelectedValue;
					selectedIconClass=item.iconClass;
					selectedItemLabel=item.label;
					selectedItemIndex=index;
				}
				
				itemStr=_this.itemTemplate.replace("{style}",style)
																   .replace("{value}",item.value)
																   .replace("{index}",index)
																   .replace("{id-or-class}",idOrClass)
																   .replace("{label}",item.label);			
				if(item.iconClass){
					itemStr=itemStr.replace("{icon-span}",_this.iconSpanTemplate.replace("{icon-class}",item.iconClass));
				}else{
					itemStr=itemStr.replace("{icon-span}","");
				}
				itemListStr+=itemStr;
								
			})
			
			dropdownStr=this.dropdownTemplate.replace("{selected-value}",selectedValue)
		        									 								      .replace("{icon-span}",selectedIconClass ? this.iconSpanTemplate.replace("{icon-class}",selectedIconClass):"")
		        									 								      .replace("{selected-item-label}",selectedItemLabel)
		        									 								      .replace("{ul-menu-id}",menuId)
		        									 								      .replace("{item-list}",itemListStr);
			
			//刷新同组其他Menu的选项
			if($parent && $parent.size()>0 && defaultSelectedValue){
				   var $dropdown=$(dropdownStr);
				   $parent.append($dropdown);
				   
				   //去除下拉框显示标签的红色提示信息
				   $dropdown.find("button").removeClass("btn-danger").addClass("btn-default");
				   
			       _this.updateItemSelectedStatus(selectedItemIndex, -1, menuId);
			       _this.refreshMenuItems();
			       $dropdown.find("ul.dropdown-menu").data("oldSelectedIndex",selectedItemIndex);				
			}
			
			return dropdownStr;
		},
		
		registerItemClickEventByDelegate:function(customEventHandler){
			var _this=this;
			this.$dropdownParent.on("click","ul.dropdown-menu li a",function(e){
			       var $this=$(this),
			              $currentMenu=$this.parent().parent(),
			              $btnGroup=$currentMenu.parent();
			              $btnList=$btnGroup.find(".btn"),
			              currentMenuId=$currentMenu.attr("id"),
			              releaseItemIndex=$currentMenu.data("oldSelectedIndex"),
			              newItemIndex=$this.data("index"),
			              value=$this.data("value");
			       
			       
			       if(value!==_this.EMPTY_VALUE){
			    	 //如果被选中项是“请选择一个值”，则显示红色样式
			    	   $btnList.removeClass("btn-danger").addClass("btn-default");
			       }else{
			    	   //如果被选中项不是“请选择一个值”，则显示默认样式
			    	   $btnList.removeClass("btn-default").addClass("btn-danger");
			       }
			       
			       //更新其他关联下拉框的选项，保证不同下拉框的选项不重复出现
			       _this.updateItemSelectedStatus(newItemIndex, releaseItemIndex, currentMenuId);
			       _this.refreshMenuItems();
			       
			       $currentMenu.data("oldSelectedIndex",newItemIndex);
			       
			       //调用自定义回调函数
			       if(typeof customEventHandler==="function"){
			    	   customEventHandler(e,$this,value);
			       }
			});
		},
		/**
		 * 更新数组dropdownItems相应item对应json的currentMenuId字段值
		 * @param newItemIndex  当前被选中item 相应json对象在数组 dropdownItems中的索引值
		 * @param releaseItemIndex 之前被选中item 相应json对象在数组 dropdownItems中的索引值
		 * @param currentMenuId  当前操作下拉框的ID值 (其是ul标签，class为"dropdown-menu")
		 */
		updateItemSelectedStatus:function(newItemIndex,releaseItemIndex,currentMenuId){
			if(releaseItemIndex===newItemIndex){
				return;
			}
			
			
			if(typeof releaseItemIndex==="number" && releaseItemIndex>-1){
				this.dropdownItems[releaseItemIndex].relatedMenuId=this.EMPTY_VALUE;
			}
			
			if(typeof newItemIndex==="number" && newItemIndex>-1){
				this.dropdownItems[newItemIndex].relatedMenuId=currentMenuId;
			}
		},
		
		refreshMenuItems:function(){
			var _this=this,
			       $menu=null,
			       menuId=null,
			       $menuList=this.$dropdownParent.find("ul.dropdown-menu");
			
			$menuList.each(function(index,item){
				$menu=$(item);
				menuId=$menu.attr("id");
				_this.refreshItemsForMenu($menu);
			});
		},
		
		refreshItemsForMenu:function($menu){
			var $itemList=$menu.find("li"),
			       $item=null;
			        itemJson=null,
			        relatedMenuId=-1,
			        currentMenuId=$menu.attr("id");
			        dropdownItems=this.dropdownItems;
			        
			for(var i=0;i<dropdownItems.length;i++){
				 itemJson=dropdownItems[i];
				 relatedMenuId=itemJson.relatedMenuId;
				 
				 $item=$itemList.eq(i+1);
				 if(relatedMenuId===undefined || relatedMenuId===this.EMPTY_VALUE){
					 $item.show();
				 }else{
					 $item.hide();
				 }
			}
		},
		
		refreshAfterRemovingMenu:function($removedMenu){
			var releaseItemIndex=$removedMenu.data("oldSelectedIndex");
			
			this.updateItemSelectedStatus(-1, releaseItemIndex, null);
			this.refreshMenuItems();
		}
		
};


