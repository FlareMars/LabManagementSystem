(function() {
    var view = null,
        RULES_URL = "rest/pipeline/params",
        PIPELINE_ID = "5cf19d07f86045c3a2a4e92600c94ac7",
        HTML_GROUP_CREATION = '\r\n<div class="btn-group">\r\n'
            + '<input class="form-control" placeholder="转录组名称" type="text" style="width:98px;" />\r\n'
            + '</div>\r\n'
            + '<a class="btn btn-xs btn-success" data-action="confirmCreation">\r\n'
            + '<i class="fa fa-check"></i>\r\n'
            + '</a>\r\n'
            + '<a class="btn btn-xs btn-default" data-action="cancelCreation">\r\n'
            + '<i class="fa fa-times"></i>\r\n'
            + '</a>\r\n',
        FLOW_STEPS = {
            sequence: {
                pipeId : '0842db24bdcb470d9c856adec7f7d488',
                flowId: 'de0fdc5870da4eaeb7374170a309bfe4',
                name: 'Mapping',
                display: '参考序列比对分析',
                referenceFileTypes: ['a6158158a3b24026bb6ec4ca641426a8', "238b342c3906406395cea5d7e780c56f","dacf521db9a845cab05c3bb880066ce8","41fdd5c266584be78f74ab8d6bf0d3ad","cf54c157014f448c8f60b0f02d3ca7bc"],
                convertParams: function(root, model) {
                    var params = {
                        taskStep: '3',
                        taskName: 'Mapping',
                        //reference: "Gmax_275_Wm82.a2.v1.fa",
                        //anno_gff: "Glycine_max.V1.0.23.gff3.clean",
                        //anno_gtf: "Glycine_max.V1.0.23.new.gtf",
                        samples_fastq: []
                    };

                    params.reference = model.getFileByType('a6158158a3b24026bb6ec4ca641426a8').name;
                    params.anno_gff = model.getFileByType('dacf521db9a845cab05c3bb880066ce8').name;
                    params.anno_gtf = model.getFileByType('41fdd5c266584be78f74ab8d6bf0d3ad').name;

                    for(var i=0;i<model.groups.length;i++) {
                        var group = model.groups[i];
                        var left = "", right = "";

                        for(var j=0;j<group.files.length;j++) {
                            var file = group.files[j];
                            if(file.direction == 'left') {
                                left = file.name;
                                root.inFiles.push(file);
                            }
                            else if(file.direction == 'right') {
                                right = file.name;
                                root.inFiles.push(file);
                            }
                        }

                        if(left || right) {
                            params.samples_fastq.push({
                                sample_name: group.name,
                                left: left,
                                right: right
                            });
                        }
                    }

                    return params;
                }
            },
            /*expression: {
                pipeId : 'fd5d4e555a9346efbd2581d02e2aac55',
                flowId: '6f65c83fea9247cebfd65565f91b4c4a',
                name: 'GeneExp',
                display: '基因表达水平分析',
                convertParams: function(root, model) {
                    return {
                        taskStep: '4',
                        taskName: 'GeneExp',
                        anno_gtf: "Glycine_max.V1.0.23.gtf"
                    };
                }
            },
            reqQuality: {
                pipeId: '8c4634ac2e2740239d886907044ceb48',
                flowId: 'c0d69731b46b42d892c63fdefd44ebea',
                name: 'AdvancedQC',
                diaplay: 'RNA-req整体质量评估',
                convertParams: function(root, model) {
                    return {
                        taskStep: '5',
                        taskName: 'AdvancedQC',
                        anno_gff: "Glycine_max.V1.0.23.gff3.clean",
                        anno_gtf: "Glycine_max.V1.0.23.gtf"
                    };
                }
            },*/
            difference: {
                pipeId: '7fa5260ded4f4e9c939c6e98d5ffa3ad',
                flowId: '2351f3bf7c1c40198f27ef233eda302c',
                name: 'DiffExprAnalysis',
                display: '基因差异表达分析',
                referenceFileTypes: ["dacf521db9a845cab05c3bb880066ce8","41fdd5c266584be78f74ab8d6bf0d3ad","1f42aad018b14157aa8b996d724c2552"],
                convertParams: function(root, model) {
                    var params = {
                        taskStep: '6',
                        taskName: 'DiffExprAnalysis',
                        //anno_gff: "Glycine_max.V1.0.23.gff3.clean",
                        //anno_gtf: "Glycine_max.V1.0.23.new.gtf",
                        vs: []
                    };

                    params.anno_gff = model.getFileByType('dacf521db9a845cab05c3bb880066ce8').name;
                    params.anno_gtf = model.getFileByType('41fdd5c266584be78f74ab8d6bf0d3ad').name;

                    for(var i=0;i<model.groups.length;i++) {
                        var group = model.groups[i];
                        var bam = "";
                        for(var j=0;j<group.files.length;j++) {
                            var file = group.files[j];
                            if(file.direction == 'bam') {
                                bam = file.name;
                                root.inFiles.push(file);
                            }
                        }

                        if(bam) {
                            if(!params.samples_bam)
                                params.samples_bam = [];
                            params.samples_bam.push({
                                sample_name: group.name,
                                bam: bam
                            });
                        }
                    }

                    for(var i=0;i<model.compareGroups.length;i++) {
                        var group = model.compareGroups[i];

                        if(group.group1 && group.group2) {
                            params.vs.push({
                                name: group.name,
                                a: [group.group1.name],
                                b: [group.group2.name]
                            });
                        }
                    }

                    return params;
                }
            },
            go : {
                pipeId: 'a443250a413a4a2ab8c698c6d752ac07',
                flowId: '72467cd795874450adcbca1e206def44',
                name: 'GO',
                display: 'GO分析',
                referenceFileTypes: ["41fdd5c266584be78f74ab8d6bf0d3ad","009a9347d5244821934f0b068beb9c2f"],
                convertParams: function(root, model) {
                    var params =  {
                        taskStep: '7',
                        taskName: 'DEG_GO',
                        anno_gtf: model.getFileByType('41fdd5c266584be78f74ab8d6bf0d3ad').name,
                        anno_go: model.getFileByType('009a9347d5244821934f0b068beb9c2f').name
                        //degslist:"RING_GITvsHA.DEG"
                    };

                    if(!model.steps.difference) {
                        model.appendFile(root, model.diffFile);
                        params.degslist = model.diffFile.name;
                    }
                    else
                        params.degslist = null;

                    return params;
                }
            },
            kegg: {
                pipeId: 'd25123f7125047e6a2439a14e9ab6028',
                flowId: '8bbd877091604850bff39b68367f16e1',
                name: 'KEGG',
                display: 'KEGG分析',
                referenceFileTypes: ["009a9347d5244821934f0b068beb9c2f", '9799164d3a4743979862b60e9ab0b59f'],
                convertParams: function(root, model) {
                    var params = {
                        taskStep: '8',
                        taskName: 'DE_KEGG',
                        anno_pro: model.getFileByType('9799164d3a4743979862b60e9ab0b59f').name,
                        species: "gmx"
                        //degslist:"RING_GITvsHA.DEG"
                    };

                    if(!model.steps.difference) {
                        //root.inFiles.push(model.diffFile);
                        model.appendFile(root, model.diffFile);
                        params.degslist = model.diffFile.name;
                    }
                    else
                        params.degslist = null;

                    return params;
                }
            }
        }/*,
        REFERENCE_FILES = [{
                "name": "Glycine_max.V1.0.23.dna.genome.fa", // 暂时固定(品种：Glycine_max，版本ensembl_23)
                "path": "/Glycine_max/ensembl23/Glycine_max.V1.0.23.dna.genome.fa",
                "fileType": "238b342c3906406395cea5d7e780c56f",
                "source": 1, // 暂时固定
                "userId":null, // 暂时固定
                "size": 0
            },
            {
                "name": "Glycine_max.V1.0.23.gtf", // 暂时固定(品种：Glycine_max，版本ensembl_23)
                "path": "/Glycine_max/ensembl23/Glycine_max.V1.0.23.gtf",
                "fileType": "41fdd5c266584be78f74ab8d6bf0d3ad",
                "source": 1, // 暂时固定
                "userId":null, // 暂时固定
                "size": 0
            },
            {
                "name": "Glycine_max.V1.0.23.gff3.clean", // 暂时固定(品种：Glycine_max，版本ensembl_23)
                "path": "/Glycine_max/ensembl23/Glycine_max.V1.0.23.gff3.clean",
                "fileType": "dacf521db9a845cab05c3bb880066ce8",
                "source": 1, // 暂时固定
                "userId":null, // 暂时固定
                "size": 0
            },
            {
                "name": "ensembl_go.txt", // 暂时固定(品种：Glycine_max，版本ensembl_23)
                "path": "/Glycine_max/ensembl23/ensembl_go.txt",
                "fileType": "009a9347d5244821934f0b068beb9c2f",
                "source": 1, // 暂时固定
                "userId":null, // 暂时固定
                "size": 0
            },
            {
                "name": "Glycine_max.V1.0.23.pep.all.fa", // 暂时固定(品种：Glycine_max，版本ensembl_23)
                "path": "/Glycine_max/ensembl23/Glycine_max.V1.0.23.pep.all.fa",
                "fileType": "a6158158a3b24026bb6ec4ca641426a8",
                "source": 1, // 暂时固定
                "userId":null, // 暂时固定
                "size": 0
            }]*/;

    var RNAModel = function() {
        RNAModel.prototype.init.apply(this, arguments);
    };

    RNAModel.prototype = {
        init: function(option) {
            this.projectInfo = $.extend({
                /*title: "",
                description: "",
                species: 1,
                speciesParts: 1,
                sequencingMachines: 1,
                processingMethod: 1*/
                id: $("#project_id").val()
            }, option?option.projectInfo:{});

            this.steps = $.extend({
                //quality: true,
                filter: true,
                sequence: true,
                expression: true,
                reqQuality: true,
                difference: true,
                go: true,
                network: true,
                kegg: true
            }, option?option.steps:{});

            this.groups = option && option.groups ? option.groups : [];
            this.compareGroups = option && option.compareGroups ? option.compareGroups : [];
            this.title = "";

            //this.files = option && option.files ? option.files : [];
        },

        isGroupDefined: function(name) {
            for(var i=0;i<this.groups.length;i++) {
                if(this.groups[i].name == name)
                    return true;
            }
            return false;
        },

        getGroup: function(name) {
            for(var i=0;i<this.groups.length;i++) {
                if(this.groups[i].name == name)
                    return this.groups[i];
            }
            return undefined;
        },

        isCompareGroupDefined: function(name) {
            for(var i=0;i<this.compareGroups.length;i++) {
                if(this.compareGroups[i].name == name)
                    return true;
            }
            return false;
        },

        addNewGroup: function(name) {
            if(this.isGroupDefined(name))
                return;

            var group = {
                name: name,
                files: []
            };

            this.groups.push(group);

            return group;
        },

        removeGroup: function(name) {
            var index = -1;
            for(var i=0;i<this.groups.length;i++) {
                if(this.groups[i].name == name)
                    index = i;
            }

            if(index>=0)
                this.groups.splice(index, 1);
        },

        addNewCompareGroup: function(name) {
            if(this.isCompareGroupDefined(name))
                return;

            var group = {
                name: name,
                files: []
            };

            this.compareGroups.push(group);

            return group;
        },

        removeCompareGroup: function(name) {
            var index = -1;
            for(var i=0;i<this.compareGroups.length;i++) {
                if(this.compareGroups[i].name == name)
                    index = i;
            }

            if(index>=0)
                this.compareGroups.splice(index, 1);
        },

        convertForSave: function() {
            var obj = {
                title: this.analysesTitle?this.analysesTitle:this.projectInfo.title,
                project: {
                    id: this.projectInfo.id,
                    title: this.projectInfo.title,
                    species: this.projectInfo.species,
                    speciesParts: this.projectInfo.speciesParts,
                    sequencingMachines: this.projectInfo.sequencingMachines,
                    processingMethod: this.projectInfo.processingMethod
                },
                inFiles: [],
                pipeline: {
                    id: "5cf19d07f86045c3a2a4e92600c94ac7",
                    pipeFlows: []
                }
            };

            /*for(var i=0;i<REFERENCE_FILES.length;i++)
                obj.inFiles.push(REFERENCE_FILES[i]);*/

            this._resolveReferenceFiles(obj);

            for(var name in this.steps) {
                if(this.steps[name] && FLOW_STEPS[name]) {
                    var step = FLOW_STEPS[name];

                    var stepData = {
                        id : step.flowId,
                        pipeId : step.pipeId,
                        params : step.convertParams(obj, this)
                    };

                    obj.pipeline.pipeFlows.push(stepData);
                }
            }

            return obj;
        },

        _resolveReferenceFiles: function(obj) {
            var types = [];
            for(var name in this.steps) {
                if(this.steps[name] && FLOW_STEPS[name]) {
                    var step = FLOW_STEPS[name];

                    for(var i=0;i<step.referenceFileTypes.length;i++) {
                        var type = step.referenceFileTypes[i];

                        var found = false;
                        for(var j=0;j<types.length;j++) {
                            if(types[j] == type) {
                                found = true;
                                break;
                            }
                        }
                        if(!found)
                            types.push(type);
                    }
                }
            }

            console.log(this.reference);
            for(var i=0;i<this.reference.data.length;i++) {
                var file = this.reference.data[i];

                for(var j=0;j<types.length;j++) {
                    if(file.fileType == types[j])
                        obj.inFiles.push(file);
                }
            }
        },

        getFileByType: function(type) {
            for(var i=0;i<this.reference.data.length;i++) {
                var file = this.reference.data[i];
                if(file.fileType == type)
                    return file;
            }
            return undefined;
        },

        appendFile: function(root, file) {
            if(!root.inFiles)
                root.inFiles = [];

            for(var i=0;i<root.inFiles.length;i++) {
                if(root.inFiles[i].name == file.name)
                    return;
            }

            root.inFiles.push(file);
        }
    };

    var initSelection = function($s, data) {
        $s.html('');

        var html = "";
        for(var i=0;i<data.length;i++) {
            html += "<option value='"+data[i].value+"'>"+data[i].name+"</option>";
        }

        $s.html(html);

        $s.select2();
    }

    var RNAView = window.RNAView = function() {
        RNAView.prototype._init.apply(this, arguments);
    };

    RNAView.prototype = {
        _init: function(option) {
            console.log(option)
            this.model = new RNAModel(option?option.model:undefined);
            this.model.references = option.references;

            this._initElements();
            this._initTemplates();

            //this.parts = [new BasicInfoStep(this), new FlowInfoStep(this), new DataFileStep(this), new SettingStep(this)];
            this.parts = [new FlowInfoStep(this), new DataFileStep(this), new SettingStep(this)];

            for(var i=0;i<this.parts.length;i++) {
                this.parts[i].syncToView();
            }
        },

        init: function() {
            var deferred = $.Deferred();

            return deferred.promise();
        },

        validate: function(step) {
            if(step == 0) {
                if(!this.model.analysesTitle) {
                    $app.showMessage("请输入分析流程名称");
                    return false;
                }

                var reg = /^[0-9a-zA-Z_]*$/g;

                if(!reg.exec(this.model.analysesTitle)) {
                    $app.showMessage("分析流程名称只能由数字、英文字母和下划线组成");
                    return false;
                }
            }
            if(step == 1) {
                if(this.model.steps.sequence && this.model.groups.length==0) {
                    $app.showMessage("请添加至少一个转录组");
                    return false;
                }
                if(!this.model.reference) {
                    $app.showMessage("请选择参考基因组版本");
                    return false;
                }
            }
            return true;
        },

        initStepView: function(step) {
            var step = this.parts[step];

            if(step && step.initStepView)
                step.initStepView();
        },

        _validateGroup: function(group) {

        },

        _initElements: function() {
            this.elements = {
                title: $("#project_title"),
                description: $("#project_description"),
                species: $("#selection_species"),
                sequencingMachines: $("#selection_sequencingMachines"),
                processingMethod: $("#selection_processingMethod"),
                speciesParts: $("#selection_speciesParts"),
                flowSteps: $("[data-step]"),
                groupSwitcher: $("#group_switcher"),
                createGroupAction: $("#action_create_group"),
                createDifferenceAction: $("#action_create_difference"),
                createCompareGroupAction: $("#action_create_compare_group"),
                groupContainer: $("#group_container"),
                compareGroupContainer: $("#compare_group_container"),
                referenceSelection: $("#reference_version"),
                analysesTitle: $("#analyses_title"),
                diffFilesTable: $("#difference_files")
            };
        },

        _initTemplates: function() {
            this.templates = {
                group: $("#template_group").html()
            };
        }
    };

    var BasicInfoStep = function() {
        BasicInfoStep.prototype.init.apply(this, arguments);
    };

    BasicInfoStep.prototype = {
        init: function(view) {
            this.view = view;
            this.model = view.model;
            this.elements = view.elements;

            initSelection(this.elements.species, Constants.species);
            initSelection(this.elements.sequencingMachines, Constants.sequencingMachines);
            initSelection(this.elements.processingMethod, Constants.processingMethod);
            initSelection(this.elements.speciesParts, Constants.speciesParts);

            this._bindEvents();
        },

        syncToModel: function() {
            this.model.projectInfo.title = this.elements.title.val();
            this.model.projectInfo.description = this.elements.description.val();
            this.model.projectInfo.species = this.elements.species.val();
            this.model.projectInfo.sequencingMachines = this.elements.sequencingMachines.val();
            this.model.projectInfo.processingMethod = this.elements.processingMethod.val();
            this.model.projectInfo.speciesParts = this.elements.speciesParts.val();
        },

        syncToView: function() {
            this.elements.title.val(this.model.projectInfo.title);
            this.elements.description.val(this.model.projectInfo.description);
            this.elements.species.val(this.model.projectInfo.species);
            this.elements.species.select2();
            this.elements.sequencingMachines.val(this.model.projectInfo.sequencingMachines);
            this.elements.sequencingMachines.select2();
            this.elements.processingMethod.val(this.model.projectInfo.processingMethod);
            this.elements.processingMethod.select2();
            this.elements.speciesParts.val(this.model.projectInfo.speciesParts);
            this.elements.speciesParts.select2();
        },

        _bindEvents: function() {
            var self = this;
            this.elements.title.on('change', function() {
                self.syncToModel();
            });
            this.elements.description.on('change', function() {
                self.syncToModel();
            });
            this.elements.species.on('change', function() {
                self.syncToModel();
            });
            this.elements.sequencingMachines.on('change', function() {
                self.syncToModel();
            });
            this.elements.processingMethod.on('change', function() {
                self.syncToModel();
            });
            this.elements.speciesParts.on('change', function() {
                self.syncToModel();
            });
        }
    };

    var FlowInfoStep = function() {
        FlowInfoStep.prototype.init.apply(this, arguments);
    };

    FlowInfoStep.prototype = {
        init: function(view) {
            this.view = view;
            this.model = view.model;
            this.elements = view.elements;

            this._bindEvents();
        },
        syncToModel: function() {
            var self = this;
            this.elements.flowSteps.each(function() {
                var step = $(this).attr('data-step');
                eval("self.model.steps."+step+"="+this.checked);
            });
            this.model.analysesTitle = this.elements.analysesTitle.val();
        },
        syncToView: function() {
            var self = this;
            this.elements.flowSteps.each(function() {
                var step = $(this).attr('data-step');
                eval("this.checked = self.model.steps."+step);
            });
            this.elements.analysesTitle.val(this.model.analysesTitle);
        },
        _bindEvents: function() {
            var self = this;
            this.elements.flowSteps.click(function() {
                var step = $(this).attr('data-step');
                eval("self.model.steps."+step+"="+this.checked);
            });
            this.elements.analysesTitle.on('change', function() {
                self.syncToModel();
            });
        }
    };

    var Colors = [{
        color: "blue"
    }, {
        color: "red"
    }, {
        color: "purple"
    }, {
        color: "yellow"
    }];

    var DataFileStep = function() {
        DataFileStep.prototype.init.apply(this, arguments);
    };

    DataFileStep.prototype = {
        init: function(view) {
            this.view = view;
            this.model = view.model;
            this.elements = view.elements;
            this.groups = [];
            this.series = [];

            this.groupType = 'direction';

            this._initGroupCreation(this.elements.createGroupAction);
            this._initDiffFileUpload(this.elements.createDifferenceAction);
            this._initReferenceSelection();
        },
        syncToModel: function() {
        },
        syncToView: function() {
        },
        initStepView: function() {
            this.view.elements.groupSwitcher.find("[data-tab='difference']").hide();
            this.view.elements.groupSwitcher.find("[data-tab='group']").hide();

            this.view.elements.groupSwitcher.find("[data-tab='group']").tab('show');

            if( !(this.model.steps.filter || this.model.steps.sequence || this.model.steps.difference) ) {
                if (this.model.steps.go || this.model.steps.kegg) {
                    this.view.elements.groupSwitcher.find("[data-tab='difference']").show();
                    this.view.elements.groupSwitcher.find("[data-tab='difference']").tab('show');
                }
            }

            if( this.model.steps.filter || this.model.steps.sequence || this.model.steps.difference) {
                this.view.elements.groupSwitcher.find("[data-tab='group']").show();
                this.view.elements.groupSwitcher.find("[data-tab='group']").tab('show');

                if(!this.model.steps.filter && !this.model.steps.sequence)
                    this.groupType = 'bam';
            }

            this._initElements();
        },

        _initElements: function() {
            var self = this;
            this.view.elements.groupSwitcher.find("[data-toggle='tab']").click(function() {
                var tab = $(this).attr('data-tab');

                if(tab == 'group') {
                    self.elements.createGroupAction.show();
                } else {
                    self.elements.createGroupAction.hide();
                }

                if(tab == 'difference') {
                    self.elements.createDifferenceAction.show();
                } else {
                    self.elements.createDifferenceAction.hide();
                }
            });

            if( this.model.steps.filter || this.model.steps.sequence || this.model.steps.difference) {
                self.elements.createGroupAction.show();
                self.elements.createDifferenceAction.hide();
            } else {
                self.elements.createGroupAction.hide();
                self.elements.createDifferenceAction.show();
            }
        },
        _bindEvents: function() {

        },
        _createGroup: function(name, element, inputArea) {
            if(!name)
                return;

            var error=undefined;
            var reg = /^[0-9a-zA-Z_]*$/g;

            //名称不能为中文
            if(!error && !reg.exec(name)){
                error = "分析流程名称只能由数字、英文字母和下划线组成";
            }

            if(!error && this.model.isGroupDefined(name)) {
                error = "转录组名称已经存在，请重新输入";
            }

            if(error) {
                $app.showMessage(error);
                return;
            }

            var group = this.model.addNewGroup(name);

            this.groups.push(new GroupView({
                view: this,
                group: group,
                container: $("#group_container")
            }));

            inputArea.hide();
            $(element).show();
        },

        _removeGroup: function(groupView) {
            var index = -1;
            var name = "";
            for(var i=0;i<this.groups.length;i++) {
                if(this.groups[i] == groupView) {
                    index = i;
                    name = this.groups[i].group.name;
                }
            }

            if(index>=0) {
                this.groups.splice(index, 1);
                this.model.removeGroup(name);
            }
        },

        _initGroupCreation: function(element) {
            var dataName = "groupInput";
            var self = this;
            var inputArea = $(element).data(dataName);
            if (!inputArea) {
                inputArea = $(HTML_GROUP_CREATION);
                $(element).data(dataName, inputArea);
                inputArea.insertAfter($(element));
                var $name = $(element).next("div").children("input");
                var $confirm = $(element).next("div").next("a");
                var $cancel = $confirm.next("a");

                $confirm.click(function () {
                    self._createGroup.apply(self, [$name.val(), element, inputArea]);
                });
                $cancel.click(function () {
                    inputArea.hide();
                    $(element).show();
                });
                inputArea.hide();
            }
            $(element).click(function () {
                var $name = $(element).next("div").children("input");
                $name.val("");
                $(this).hide();
                inputArea.show();
            })
        },

        _initDiffFileUpload: function(element) {
            var self = this;
            element.on('click', function() {
                var dlg = new FileDialog({
                    onSelect: function(file) {
                        dlg.close();

                        var fileObj = {
                            name: file.name,
                            path: file.fullPath,
                            fileType: '65715b57c96942a0a154f028405dd51f',
                            source: file.businessType,
                            userId: file.userId,
                            size: file.size
                        };

                        self.model.diffFile = fileObj;

                        var type = file.businessType == 0 ? "我的文件" : file.businessType == 1 ? "公共数据库" : file.businessType == 2 ? "项目文件" : "未知";
                        var html = $("#rna-diff-file-template").html()
                            .replace("{file-name}", file.name)
                            .replace("{file-size}", file.sizeLabel)
                            .replace("{file-source}", type)
                            .replace("{file-path}", file.fullPath);

                        var $row = $(html);
                        $row.hide().appendTo(self.elements.diffFilesTable).slideDown(function() {
                            $row.on('click', '[data-action="delete"]', function() {
                                var $this = $(this);
                                $app.showConfirm({
                                    msg: "您确定要删除这个文件吗？",
                                    confirmCallback: function() {
                                        self.model.diffFile = null;
                                        $row.remove();
                                    }
                                })
                            });
                        });
                    }
                });

                dlg.open();
            });
        },

        _initReferenceSelection: function() {
            var html = "<option value=''>请选择版本</option>";
            for(var i=0;i<this.model.references.length;i++) {
                var ref = this.model.references[i];
                html += "<option value='"+ref.name+"'>"+ref.name+"</option>";
            }

            this.elements.referenceSelection.html(html);
            this.elements.referenceSelection.select2();

            var self = this;
            this.elements.referenceSelection.on('change', function() {
                var name = $(this).val();

                if(!name)
                    self.model.reference = null;
                else {
                    for (var i = 0; i < self.model.references.length; i++) {
                        var ref = self.model.references[i];
                        if (ref.name == name) {
                            self.model.reference = ref;
                        }
                    }
                }
            });
        },

        getSeries: function(name) {
            if(!this.series || this.series.length==0)
                return undefined;

            for(var i=0;i<this.series.length;i++) {
                if(this.series[i].name == name)
                    return this.series[i];
            }

            return undefined;
        },

        createSeries: function(callback) {
            var self = this;
            var content = $("#rna-create-series-template").html();
            $app.showDialog('create_series_dialog', {
                content: content,
                width: 340,
                height: 160,
                title: '请输入分组名称',
                confirmCallback: function($d) {
                    var input = $d.find("[data-action='seriesName']");

                    var name = input.val();

                    if(!name) {
                        $app.showMessage("请输入分组名称");
                        return;
                    }
                    if(self.getSeries(name)) {
                        $app.showMessage("分组已经存在");
                        return;
                    }

                    var series = {
                        name : name
                    };

                    var index = self.series.length;
                    while(index >= Colors.length)
                        index -= Colors.length;

                    series.color = Colors[index].color;

                    self.series.push(series);
                    $app.closeDialog('create_series_dialog');

                    if(callback)
                        callback(series);

                    for(var i=0;i<self.groups.length;i++)
                        self.groups[i].updateSeries();
                }
            });
        }
    };

    var SettingStep = function() {
        SettingStep.prototype.init.apply(this, arguments);
    };

    SettingStep.prototype = {
        init: function(view) {
            this.view = view;
            this.model = view.model;
            this.elements = view.elements;
            this.compareGroups = [];

            this._initCompareGroupCreation(this.elements.createCompareGroupAction);
        },
        syncToModel: function() {
        },
        syncToView: function() {
        },
        _bindEvents: function() {
        },
        initStepView: function() {
            var self = this;
            var active = false;
            $("[data-setting-step]").each(function() {
                var step = $(this).attr("data-setting-step");
                var visible = true;
                eval("visible = self.model.steps."+step);
                if(!visible)
                    $(this).hide();
                else {
                    $(this).show();

                    if(!active) {
                        $(this).tab('show');
                        active = true;
                    }
                }
            });
        },
        _createCompareGroup: function(name, element, inputArea) {
            if(!name)
                return;

            var error=undefined;

            if(this.model.compareGroups.length>=1) {
                error = "目前只支持添加一个比对组";
            }

            if(!error && this.model.isCompareGroupDefined(name)) {
                error = "比对组名称已经存在，请重新输入";
            }

            if(error) {
                $app.showMessage(error);
                return;
            }

            var compareGroup = this.model.addNewCompareGroup(name);

            this.compareGroups.push(new CompareGroupView({
                view: this,
                model: this.model,
                compareGroup: compareGroup,
                container: $("#compare_group_container")
            }));

            inputArea.hide();
            $(element).show();
        },

        _removeCompareGroup: function(groupView) {
            var index = -1;
            var name = "";
            for(var i=0;i<this.compareGroups.length;i++) {
                if(this.compareGroups[i] == groupView) {
                    index = i;
                    name = this.compareGroups[i].compareGroup.name;
                }
            }

            if(index>=0) {
                this.compareGroups.splice(index, 1);
                this.model.removeCompareGroup(name);
            }
        },

        _initCompareGroupCreation: function(element) {
            var dataName = "groupInput";
            var self = this;
            var inputArea = $(element).data(dataName);
            if (!inputArea) {
                inputArea = $(HTML_GROUP_CREATION);
                $(element).data(dataName, inputArea);
                inputArea.insertAfter($(element));
                var $name = $(element).next("div").children("input");
                var $confirm = $(element).next("div").next("a");
                var $cancel = $confirm.next("a");

                $confirm.click(function () {
                    self._createCompareGroup.apply(self, [$name.val(), element, inputArea]);
                });
                $cancel.click(function () {
                    inputArea.hide();
                    $(element).show();
                });
                inputArea.hide();
            }
            $(element).click(function () {
                var $name = $(element).next("div").children("input");
                $name.val("");
                $(this).hide();
                inputArea.show();
            })
        }
    };

    var GroupView = function() {
        GroupView.prototype.init.apply(this, arguments);
    };

    GroupView.prototype = {
        init: function(option) {
            this.view = option.view;
            this.group = option.group;
            this.container = option.container;
            var self = this;
            this.fileDialog =  new FileDialog({
                onSelect: function(file) {
                    self._addFile.call(self, file);
                    self.fileDialog.close();
                }
            });

            this._initView();
        },

        _initView: function() {
            var html = $("#rna-group-template").html()
                .replace("{name}", this.group.name);

            this.$element = $(html);
            var self = this;
            this.$element.hide().appendTo(this.container).slideDown(function() {
                self.$table = self.$element.find("[data-action='files']");
                self.$series = self.$element.find("[data-action='series']");

                // remove group view
                self.$element.on('click', '[data-action="remove"]', function() {
                    $app.showConfirm({
                        msg: "您确定要删除这个转录组吗？",
                        confirmCallback: function() {
                            self.view._removeGroup(self);
                            self.$element.slideUp(function() {
                                self.$element.remove();
                            });
                        }
                    })
                });

                self.$element.on('click', '[data-action="selectFile"]', function() {
                    self.fileDialog.open();
                });

                self.updateSeries();
            });
        },

        updateSeries: function() {
            var name = this.series ? this.series.name : "未分组";
            var color = this.series ? this.series.color : "btn-default";

            var html = '<a class="btn btn-circle ' + color + '" href="javascript:;" data-toggle="dropdown">'
                +'<i class="fa fa-tag"></i> ' + name + ' <i class="fa fa-angle-down"></i>'
                +'</a>';

            html += '<ul class="dropdown-menu">';

            for(var i=0;i<this.view.series.length;i++) {
                var s = this.view.series[i];

                html += '<li><a href="javascript:;" data-action="select-series" data-series="' + s.name + '"><i class="fa fa-tag font-' + s.color + '"></i> ' + s.name + '</a></li>';
            }

            html += '<li><a href="javascript:;" data-action="create-series"><i class="fa fa-plus"></i> 新建分组</a></li>';
            html += '</ul>';

            var self = this;
            this.$series.html("");

            var $html = $(html);
            $html.appendTo(this.$series);
            $html.data('group', self);

            $html.on('click', '[data-action="create-series"]', function() {
                self.view.createSeries(function(series) {
                    self.series = series;
                    self.group.series = series.name;
                });
            });

            $html.on('click', '[data-action="select-series"]', function() {
                self.series = self.view.getSeries($(this).attr('data-series'));

                self.updateSeries();
            });
        },

        _addFile: function(file) {
            for(var i=0;i<this.group.files.length;i++) {
                if(this.group.files[i].path == file.path) {
                    $app.showMessage("文件已经存在");
                    return;
                }
            }

            var fileObj = {
                id: file.id,
                name: file.name,
                path: file.fullPath,
                fileType: 'cf54c157014f448c8f60b0f02d3ca7bc',
                source: file.businessType,
                userId: file.userId,
                size: file.size,
                direction: 'left'
            };

            this.group.files.push(fileObj);

            var type = file.businessType == 0 ? "我的文件" : file.businessType == 1 ? "公共数据库" : file.businessType == 2 ? "项目文件" : "未知";
            var html = $("#rna-group-file-template").html()
                .replace("{file-name}", file.name)
                .replace("{file-size}", file.sizeLabel)
                .replace("{file-source}", type)
                .replace("{file-path}", file.fullPath)
                .replace("{file-id}", file.id);

            var $row = $(html);
            var self = this;
            $row.hide().appendTo(this.$table).slideDown(function() {
                var selection = $row.find("[data-action='direction']");

                var options = [];
                if(self.view.model.steps.sequence)
                    options = [{
                        name: '正向', value: '1'
                    }, {
                        name: '反向', value: '2'
                    }];
                else
                    options = [{name:'BAM', value:'3'}];

                var html = '';
                for(var i=0;i<options.length;i++)
                    html += '<option value="'+options[i].value+'">'+options[i].name+'</option>';

                selection.html(html);

                selection.select2({
                    templateResult: function(state) {
                        console.log(state)
                        if(!state.id) return state.text;
                        if(state.id == '1') {
                            return $("<span><i class='fa fa-arrow-left'></i> " + state.text + "</span>");
                        } else if(state.id == '2') {
                            return $("<span><i class='fa fa-arrow-right'></i> " + state.text + "</span>");
                        } else {
                            return $("<span>" + state.text + "</span>");
                        }
                    }
                }).on('change', function() {
                    console.log(fileObj.direction);

                    var val = $(this).val();

                    if(val == '1')
                        fileObj.direction = 'left';
                    else if(val == '2')
                        fileObj.direction = 'right';
                    else if(val == '3')
                        fileObj.direction = 'bam';
                });

                $row.on('click', '[data-action="delete"]', function() {
                    var $this = $(this);
                    $app.showConfirm({
                        msg: "您确定要删除这个文件吗？",
                        confirmCallback: function() {
                            var id = $this.attr('data-file-id');
                            var index = -1;

                            for(var i=0;i<self.group.files.length;i++) {
                                if(self.group.files[i].id == id) {
                                    index = i;
                                    break;
                                }
                            }
                            if(index>=0) {
                                self.group.files.splice(index, 1);
                                $row.remove();
                            }
                        }
                    })
                });
            });
        }
    };


    var CompareGroupView = function() {
        CompareGroupView.prototype.init.apply(this, arguments);
    };

    CompareGroupView.prototype = {
        init: function (option) {
            this.view = option.view;
            this.model = option.model;
            this.compareGroup = option.compareGroup;
            this.container = option.container;

            this._initView();
        },

        updateGroups: function() {
            var self = this;

            this.$groups.each(function() {
                var $group = $(this);
                var color = $group.attr("data-color");
                var number = $group.attr("data-group-action");
                var group = number == 'group1' ? self.group1 : self.group2;
                var name = group ? group.name : "请选择转录组";
                var html = '<a class="btn btn-circle ' + color + '" href="javascript:;" data-toggle="dropdown">'
                    +'<i class="fa fa-tag"></i> ' + name + ' <i class="fa fa-angle-down"></i>'
                    +'</a>';

                html += '<ul class="dropdown-menu">';

                for(var i=0;i<self.model.groups.length;i++) {
                    var g = self.model.groups[i];

                    html += '<li><a href="javascript:;" data-action="select-group" data-group="' + g.name + '"><i class="fa fa-tag"></i> ' + g.name + '</a></li>';
                }

                html += '</ul>';

                $group.html("");

                var $html = $(html);
                $html.appendTo($group);
                $html.data('group', group);

                $html.on('click', '[data-action="select-group"]', function() {
                    var filesHtml = "";
                    var gname = $(this).attr("data-group");
                    var ga = $group.attr("data-group-action");
                    var g = self.model.getGroup(gname);
                    var $content = self.container.find("[data-group='"+ga+"']").find("[data-action='content']");

                    for(var i=0;i<g.files.length;i++) {
                        var file = g.files[i];

                        var direction = file.direction == 'left' ? 'left' : 'right';

                        filesHtml += "<h4>"
                            +"<i class='fa fa-arrow-"+direction+"'></i> "+file.name+"</h4>";
                    }

                    var cls = number == 'group1' ? 'danger' : 'info';

                    if(number == 'group1') {
                        self.compareGroup.group1 = g;
                    } else {
                        self.compareGroup.group2 = g;
                    }

                    var content = $("#rna-compare-group-content-template").html()
                        .replace("{name}", g.name)
                        .replace("{noteClass}", cls)
                        .replace("{files}", filesHtml);

                    $content.html(content);
                });
            });
        },

        _initView: function () {
            var html = $("#rna-compare-group-template").html()
                .replace("{name}", this.compareGroup.name);

            this.$element = $(html);
            var self = this;
            this.$element.hide().appendTo(this.container).slideDown(function () {
                self.$groups = self.$element.find("[data-action='groups']");

                // remove group view
                self.$element.on('click', '[data-action="remove"]', function() {
                    $app.showConfirm({
                        msg: "您确定要删除这个比对组吗？",
                        confirmCallback: function() {
                            self.view._removeCompareGroup(self);
                            self.$element.slideUp(function() {
                                self.$element.remove();
                            });
                        }
                    })
                });

                self.updateGroups.apply(self);
            });
        }
    }
})(jQuery);