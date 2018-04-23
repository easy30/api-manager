var actionTableOptions = {
    container: '#editTable',
    headers: [
        {text: '接口编号', width: '10%'},
        {text: '接口名称', width: '10%'},
        {text: '所属模块', width: '15%'},
        {text: '请求类型', width: '8%'},
        {text: '状态', width: '7%'},
        {text: '操作', width: '20%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '接口编号', required: false},
        {name: 'actionName', type: 'input', inputDesc: '接口名称', required: true},
        {name: 'moduleId', type:'select', inputDesc: '所属模块', required: true, options:{
                optionField: {value: 'id', text: 'moduleName'},
                url: api.util.getUrl('apimanager/module/list')
            }
        },
        {name: 'requestType', type:'select', inputDesc: '请求类型', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 2},
                cache: true,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }
        },
        {name: 'status', type:'select', inputDesc: '状态', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 3},
                cache: true,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }
        }
    ],
    rowButtons: [
        {type: 'more', text: '更多', icon: 'glyphicon glyphicon-option-horizontal', fn: function (param) {
                $('#depart').parent('.container').css('display', 'none');
                var actionInfoFormObject, headParam, requestParam, responseParam, domainSelect;
                var parentId = $('select[name=moduleId]').val();
                var depId = $('select[name=depId]').val();
                var projectId = $('select[name=projectId]').val();
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/actionTab.html'),
                    async: false,
                    loaded: function () {
                        var $cancelSave = $('#cancelSave'),$editChange = $('#editChange');
                        var $cancelButton = $('#cancelButton'),$editCancel = $('#editCancel'),$editButton = $('#editButton'),$saveButton = $('#saveButton');
                        $cancelSave.css('display','none');
                        var actionTabConf = {
                            container: '#tabs',
                            tabs: [{
                                title: '基本信息',
                                width: '10%',
                                href: api.util.getUrl('html/action/actionInfo.html'),
                                loaded: function () {
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data: {id: param.id},
                                        dataType: "json",
                                        contentType: 'json',
                                        success: function (result) {
                                            var data = result['data'];
                                            api.util.loadScript(api.util.getUrl('html/action/js/actionInfo.js'), function () {
                                                api.ui.chosenSelect(typeSelectOption);
                                                api.ui.chosenSelect(statusSelectOption);
                                                api.ui.chosenSelect(moduleOptions);
                                                api.ui.chosenSelect(domainSelectOptions);
                                                actionInfoFormObject = api.ui.form(actionInfoFormOptions);
                                                actionInfoFormObject.giveVal({
                                                    id: data['id'],
                                                    requestUrl: data['requestUrl'],
                                                    actionName: data['actionName'],
                                                    moduleId: data['moduleId'],
                                                    requestType: data['requestType'],
                                                    status: data['status'],
                                                    actionDesc: data['actionDesc'],
                                                    domainId: data['domainId']
                                                });
                                                actionInfoFormObject.disable();
                                            });
                                        }
                                    });
                                }
                            }, {
                                title: '接口参数',
                                width: '10%',
                                lazy: false,
                                href: api.util.getUrl('html/action/actionParam.html'),
                                loaded: function () {
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data: {id: param.id},
                                        dataType: "json",
                                        async: false,
                                        contentType: 'json',
                                        success: function (result) {
                                            var data = result['data'];
                                            api.util.loadScript(api.util.getUrl('html/action/js/actionParam.js'), function () {
                                                headParam = api.ui.param(headOptions);
                                                requestParam = api.ui.param(requestOptions);
                                                responseParam = api.ui.param(responseOptions);
                                                if (data && data['requestHeadDefinition']) {
                                                    var rowData = JSON.parse(data['requestHeadDefinition']);
                                                    $.each(rowData, function (index, data) {
                                                        headParam._showRow(data);
                                                    })
                                                }
                                                if (data && data['requestDefinition']) {
                                                    var rowData = JSON.parse(data['requestDefinition']);
                                                    $.each(rowData, function (index, data) {
                                                        requestParam._showRow(data);
                                                    })
                                                }
                                                if (data && data['responseDefinition']) {
                                                    var rowData = JSON.parse(data['responseDefinition']);
                                                    $.each(rowData, function (index, data) {
                                                        responseParam._showRow(data);
                                                    })
                                                }
                                                var $importBtn = $('#responseParam').find('#importBtn');
                                                $importBtn.on('click',function () {
                                                    var dialogOptions = {
                                                        container: 'body',
                                                        content: '<textarea class="col-12 form-control" name="responseJson" style="height: 300px;"></textarea>',
                                                        iTitle: false,
                                                        title: '响应参数',
                                                        width: '150%',
                                                        buttons:[
                                                            {
                                                                type: 'close', text: '关闭', fn: function () {}
                                                            },{
                                                                type: 'sure', text: '导入', fn: function () {
                                                                    $.ajax({
                                                                        url: api.util.getUrl('apimanager/params/convertJsonToRows'),
                                                                        type: 'post',
                                                                        data : $('textarea[name=responseJson]').val(),
                                                                        contentType : 'application/json;charset=utf-8',
                                                                        dataType: 'json',
                                                                        success: function (result) {
                                                                            var data = result.data;
                                                                            $.each(JSON.parse(data), function (index, rowData) {
                                                                                responseParam._showRow(rowData);
                                                                            })
                                                                            $('#responseModal').map(function () {
                                                                                if (!$(this).is(":hidden")){
                                                                                    $(this).modal('hide');
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        ]
                                                    };
                                                    api.ui.dialog(dialogOptions).open();
                                                })
                                                headParam.disable();
                                                requestParam.disable();
                                                responseParam.disable();
                                            });

                                        }
                                    });

                                }
                            }, {
                                title: '接口测试',
                                width: '10%',
                                lazy: false,
                                href: api.util.getUrl('html/action/actionTest.html'),
                                loaded: function () {
                                    $('#depart').parent('.container').css('display','none');
                                    $('#actionPage').css('margin-top','90px');
                                    $('#testPage').css('margin-top','0px');
                                    var testHeadParam, testRequestParam;
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data: {id: param.id},
                                        dataType: "json",
                                        async: false,
                                        contentType: 'json',
                                        success: function (result) {
                                            var data = result['data'];
                                            api.util.loadScript(api.util.getUrl('html/action/js/actionTest.js'), function () {
                                                var requestTypeSelect = api.ui.chosenSelect(requestTypeOptions);
                                                testHeadParam = api.ui.param(testHeadOptions);
                                                testRequestParam = api.ui.param(testRequestOptions);
                                                if (data) {
                                                    requestTypeSelect.val(data['requestType']);
                                                    $('[name=testRequestUrl]').val(data['requestUrl']);
                                                    $.ajax({
                                                        url: api.util.getUrl('apimanager/domain/findById'),
                                                        type: 'GET',
                                                        data: {id: data['domainId']},
                                                        dataType: 'json',
                                                        success: function (result) {
                                                            var resultData = result.data;
                                                            var domainEditSelect = api.ui.editSelect(domainEditOptions);
                                                            domainEditSelect._load({domainCode: resultData.domainCode});
                                                            domainEditSelect._val(resultData.id);
                                                        }
                                                    })
                                                    if (data['requestHeadDefinition']) {
                                                        var rowData = JSON.parse(data['requestHeadDefinition']);
                                                        $.each(rowData, function (index, data) {
                                                            testHeadParam._showRow(data);
                                                        })
                                                    }
                                                    if (data['requestDefinition']) {
                                                        var rowData = JSON.parse(data['requestDefinition']);
                                                        $.each(rowData, function (index, data) {
                                                            testRequestParam._showRow(data);
                                                        })
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    $('#sendRequest').on('click', function () {
                                        var requestMockTemplate = api.util.buildMockTemplate(testRequestParam.toData());
                                        var headMockTemplate = api.util.buildMockTemplate(testHeadParam.toData());
                                        var requestData = Mock.mock(requestMockTemplate), headData = Mock.mock(headMockTemplate);
                                        var requestDataStr = JSON.stringify(requestData), headDataStr = JSON.stringify(headData);
                                        $('#requestJson').JSONView(requestDataStr);
                                        $('#requestJsonArea').val(requestDataStr);

                                        var requestBody = {};
                                        requestBody['domainName'] = $('[name=testDomainId]').val();
                                        requestBody['requestType'] = $('[name=testRequestType]').val();
                                        requestBody['requestUrl'] = $('[name=testRequestUrl]').val();
                                        requestBody['requestHeadData'] = headDataStr;
                                        requestBody['requestData'] = requestDataStr;
                                        $.ajax({
                                            url: api.util.getUrl('apimanager/tester/send'),
                                            type: 'POST',
                                            contentType: 'application/json;charset=UTF-8', //解决415问题
                                            data: JSON.stringify(requestBody),//解决400问题
                                            dataType: 'json',
                                            success: function (result) {
                                                var code = result.code;
                                                if(code == '-1'){
                                                    $('#responseJsonArea').val(JSON.stringify(result));
                                                    $('#responseJson').JSONView(result);
                                                } else {
                                                    var data = result.data, dataStr = JSON.stringify(data)
                                                    $('#responseJsonArea').val(dataStr);
                                                    $('#responseJson').JSONView(dataStr);
                                                }
                                                $('#requestJsonFormatLink')[0].scrollIntoView();
                                            }
                                        });
                                    });
                                    $('#requestJsonFormatLink').on('click', function () {
                                        $('#requestJson').css('display', '');
                                        $('#requestJsonArea').css('display', 'none');
                                    });
                                    $('#requestJsonRowLink').on('click', function () {
                                        $('#requestJson').css('display', 'none');
                                        $('#requestJsonArea').css('display', '');
                                    });
                                    $('#responseJsonFormatLink').on('click', function () {
                                        $('#responseJson').css('display', '');
                                        $('#responseJsonArea').css('display', 'none');
                                    });
                                    $('#responseJsonRowLink').on('click', function () {
                                        $('#responseJson').css('display', 'none');
                                        $('#responseJsonArea').css('display', '');
                                    });
                                }
                            }]
                        }
                        var actionTabConfObject = api.ui.tabs(actionTabConf);
                        //切换
                        $editButton.on('click', function () {
                            actionInfoFormObject.enable();
                            headParam.enable();
                            requestParam.enable();
                            responseParam.enable();
                            $editChange.css('display','none');
                            $cancelSave.css('display','');
                            var activeTabTitle = actionTabConfObject.activeTabTitle();
                            if(activeTabTitle == '接口测试'){
                                actionTabConfObject.show('基本信息');
                            }
                            actionTabConfObject.hide('接口测试');
                        });
                        //取消保存
                        $cancelButton.mousedown(function () {
                            actionInfoFormObject.reset();
                            actionInfoFormObject.disable();
                            actionTabConfObject.display('接口测试');
                            $.ajax({
                                type: 'get',
                                url: api.util.getUrl('/apimanager/action/findById'),
                                data: {id: param.id},
                                dataType: "json",
                                async: false,
                                contentType: 'json',
                                success: function (result) {
                                    var data = result['data'];
                                    api.util.loadScript(api.util.getUrl('html/action/js/actionParam.js'), function () {
                                        headParam._empty();
                                        requestParam._empty();
                                        responseParam._empty();
                                        if (data && data['requestHeadDefinition']) {
                                            var rowData = JSON.parse(data['requestHeadDefinition']);
                                            $.each(rowData, function (index, data) {
                                                headParam._showRow(data);
                                            })
                                        }
                                        if (data && data['requestDefinition']) {
                                            var rowData = JSON.parse(data['requestDefinition']);
                                            $.each(rowData, function (index, data) {
                                                requestParam._showRow(data);
                                            })
                                        }
                                        if (data && data['responseDefinition']) {
                                            var rowData = JSON.parse(data['responseDefinition']);
                                            $.each(rowData, function (index, data) {
                                                responseParam._showRow(data);
                                            })
                                        }
                                        headParam.disable();
                                        requestParam.disable();
                                        responseParam.disable();
                                    });
                                }
                            });
                            $editChange.css('display', '');
                            $cancelSave.css('display', 'none');
                        });
                        //退出查看页面
                        $editCancel.mousedown(function () {
                            $('#depart').parent('.container').css('display', '');
                            //跳转到action列表
                            var conf = {
                                container: '#container',
                                url: api.util.getUrl('html/action/action.html'),
                                async: false,
                                preLoad: function () {
                                    $("#depart").empty();
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                                },
                                loaded: function () {
                                    var depOptions = {
                                        selector: '[name=depId]',
                                        optionField: {value: 'id', text: 'depName'},
                                        width: '70%',
                                        url: api.util.getUrl('apimanager/department/list'),
                                        change: function (e, p) {
                                            projectSelect.clear();
                                            var params = {};
                                            params['depId']=e.target.value;
                                            projectSelect.load(params);
                                            projectSelect.val(projectId);
                                            projectSelect.doChange();
                                        }
                                    };
                                    var projectOptions = {
                                        selector: '[name=projectId]',
                                        optionField: {value: 'id', text: 'projectName'},
                                        width: '70%',
                                        url: api.util.getUrl('apimanager/project/list'),
                                        change: function (e, p) {
                                            moduleSelect.clear();
                                            var params = {};
                                            params['projectId']=e.target.value;
                                            moduleSelect.load(params);
                                            moduleSelect.val(parentId);
                                        }
                                    };
                                    var moduleOptions = {
                                        selector: '[name=moduleId]',
                                        optionField: {value: 'id', text: 'moduleName'},
                                        width: '70%',
                                        url: api.util.getUrl('apimanager/module/list')
                                    };
                                    var projectSelect = api.ui.chosenSelect(projectOptions);
                                    var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                    moduleSelect.val(parentId);
                                    var depSelect = api.ui.chosenSelect(depOptions);
                                    depSelect.val(depId);
                                    depSelect.doChange();
                                    api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                                        api.ui.editTable(actionTableOptions);
                                    });
                                }
                            }
                            api.ui.load(conf);
                        });
                        //保存
                        $saveButton.on('click', function () {
                            //表单非空校验
                            var i = 0;
                            $('#actionInfoForm').find('input,select').each(function(){
                                var value = $.trim($(this).val());
                                if(!value){
                                    i = 1;
                                    $(this).css('border-color','red');
                                    $(this).on('blur',function () {
                                        if($.trim($(this).val())){
                                            $(this).css('border-color','');
                                        }
                                    })
                                    return true;
                                }
                            });
                            if(i == 1){
                                actionTabConfObject.show('基本信息');
                                var option = {content: '请完善接口基本信息'};
                                api.ui.dialog(option).open();
                                return;
                            }
                            if(headParam._checkEmpty() || requestParam._checkEmpty() || responseParam._checkEmpty()||
                                headParam.defaultFlag==1||requestParam.defaultFlag==1||responseParam.defaultFlag==1){
                                var option = {content: '请完善接口参数信息'};
                                api.ui.dialog(option).open();
                                return;
                            }
                            var headArr = headParam.toData();
                            var requestArr = requestParam.toData();
                            var responseArr = responseParam.toData();
                            var requestData = actionInfoFormObject.toJson();
                            requestData['id'] = param.id;
                            requestData['requestHeadDefinition'] = JSON.stringify(headArr);
                            requestData['requestDefinition'] = JSON.stringify(requestArr);
                            requestData['responseDefinition'] = JSON.stringify(responseArr);
                            $.ajax({
                                url: api.util.getUrl('apimanager/action/update'),
                                type: 'post',
                                contentType: 'application/json;charset=UTF-8',
                                data: JSON.stringify(requestData),
                                dataType: 'json',
                                success: function (data) {
                                    if(data.code==-1){
                                        var option={content: '保存失败'};
                                        api.ui.dialog(option).open();
                                        return;
                                    }
                                    $('#depart').parent('.container').css('display','');
                                    //跳转到action列表
                                    var conf = {
                                        container: '#container',
                                        url: api.util.getUrl('html/action/action.html'),
                                        async: false,
                                        preLoad: function () {
                                            $("#depart").empty();
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                                        },
                                        loaded: function () {
                                            var depOptions = {
                                                selector: '[name=depId]',
                                                optionField: {value: 'id', text: 'depName'},
                                                width: '70%',
                                                url: api.util.getUrl('apimanager/department/list'),
                                                change: function (e, p) {
                                                    projectSelect.clear();
                                                    var params = {};
                                                    params['depId']=e.target.value;
                                                    projectSelect.load(params);
                                                    projectSelect.val(projectId);
                                                    projectSelect.doChange();
                                                }
                                            };
                                            var projectOptions = {
                                                selector: '[name=projectId]',
                                                optionField: {value: 'id', text: 'projectName'},
                                                width: '70%',
                                                url: api.util.getUrl('apimanager/project/list'),
                                                change: function (e, p) {
                                                    moduleSelect.clear();
                                                    var params = {};
                                                    params['projectId']=e.target.value;
                                                    moduleSelect.load(params);
                                                    moduleSelect.val(parentId);
                                                }
                                            };
                                            var moduleOptions = {
                                                selector: '[name=moduleId]',
                                                optionField: {value: 'id', text: 'moduleName'},
                                                width: '70%',
                                                url: api.util.getUrl('apimanager/module/list')
                                            };
                                            var projectSelect = api.ui.chosenSelect(projectOptions);
                                            var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                            var depSelect = api.ui.chosenSelect(depOptions);
                                            depSelect.val(depId);
                                            depSelect.doChange();
                                            api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                                                api.ui.editTable(actionTableOptions);
                                            });
                                        }
                                    }
                                    api.ui.load(conf);
                                }
                            });
                        });
                    }
                };
                api.ui.load(conf);
            }
        },
        {type: 'copy', text: '复制', icon: 'glyphicon glyphicon-share', fn: function (param) {
                $('#depart').parent('.container').css('display','none');
                var parentId = $('select[name=moduleId]').val();
                var depId = $('select[name=depId]').val();
                var projectId = $('select[name=projectId]').val();
                var actionInfoFormObject, headParam, requestParam, responseParam;
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/actionTab.html'),
                    content: "",
                    async: false,
                    preLoad: function () {},
                    loaded: function () {
                        var $editChange = $('#editChange');
                        var $cancelButton = $('#cancelButton'),$saveButton = $('#saveButton');
                        $editChange.css('display','none');
                        $('#actionPage').css('margin-top','90px');
                        var actionTabConf = {
                            container: '#tabs',
                            tabs: [{
                                title: '基本信息',
                                width: '10%',
                                href: api.util.getUrl('html/action/actionInfo.html'),
                                loaded: function () {
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data: {id: param.id},
                                        dataType: "json",
                                        contentType: 'json',
                                        success: function (result) {
                                            var data = result['data'];
                                            api.util.loadScript(api.util.getUrl('html/action/js/actionInfo.js'), function () {
                                                api.ui.chosenSelect(typeSelectOption);
                                                api.ui.chosenSelect(statusSelectOption);
                                                api.ui.chosenSelect(moduleOptions);
                                                api.ui.chosenSelect(domainSelectOptions);
                                                actionInfoFormObject = api.ui.form(actionInfoFormOptions);
                                                actionInfoFormObject.giveVal({
                                                    actionName: data['actionName'],
                                                    moduleId: data['moduleId'],
                                                    requestType: data['requestType'],
                                                    status: data['status'],
                                                    actionDesc: data['actionDesc'],
                                                    domainId: data['domainId']
                                                });
                                            });
                                        }
                                    });
                                }
                            }, {
                                title: '接口参数',
                                width: '10%',
                                lazy: false,
                                href: api.util.getUrl('html/action/actionParam.html'),
                                loaded: function () {
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data: {id: param.id},
                                        dataType: "json",
                                        async: false,
                                        contentType: 'json',
                                        success: function (result) {
                                            var data = result['data'];
                                            api.util.loadScript(api.util.getUrl('html/action/js/actionParam.js'), function () {
                                                headParam = api.ui.param(headOptions);
                                                requestParam = api.ui.param(requestOptions);
                                                responseParam = api.ui.param(responseOptions);
                                                if (data && data['requestHeadDefinition']) {
                                                    var rowData = JSON.parse(data['requestHeadDefinition']);
                                                    $.each(rowData, function (index, data) {
                                                        headParam._showRow(data);
                                                    })
                                                }
                                                if (data && data['requestDefinition']) {
                                                    var rowData = JSON.parse(data['requestDefinition']);
                                                    $.each(rowData, function (index, data) {
                                                        requestParam._showRow(data);
                                                    })
                                                }
                                                if (data && data['responseDefinition']) {
                                                    var rowData = JSON.parse(data['responseDefinition']);
                                                    $.each(rowData, function (index, data) {
                                                        responseParam._showRow(data);
                                                    })
                                                }
                                                var $importBtn = $('#responseParam').find('#importBtn');
                                                $importBtn.on('click',function () {
                                                    var dialogOptions = {
                                                        container: 'body',
                                                        content: '<textarea class="col-12 form-control" name="responseJson" style="height: 300px;"></textarea>',
                                                        iTitle: false,
                                                        title: '响应参数',
                                                        width: '150%',
                                                        buttons:[
                                                            {
                                                                type: 'close', text: '关闭', fn: function () {}
                                                            },{
                                                                type: 'sure', text: '导入', fn: function () {
                                                                    $.ajax({
                                                                        url: api.util.getUrl('apimanager/params/convertJsonToRows'),
                                                                        type: 'post',
                                                                        data : $('textarea[name=responseJson]').val(),
                                                                        contentType : 'application/json;charset=utf-8',
                                                                        dataType: 'json',
                                                                        success: function (result) {
                                                                            var data = result.data;
                                                                            $.each(JSON.parse(data), function (index, rowData) {
                                                                                responseParam._showRow(rowData);
                                                                            })
                                                                            $('#responseModal').map(function () {
                                                                                if (!$(this).is(":hidden")){
                                                                                    $(this).modal('hide');
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        ]
                                                    };
                                                    api.ui.dialog(dialogOptions).open();
                                                })
                                            });
                                        }
                                    });
                                }
                            }]
                        };
                        var actionTabConfObject = api.ui.tabs(actionTabConf);
                        //退出添加
                        $cancelButton.mousedown(function () {
                            $('#depart').parent('.container').css('display', '');
                            //跳转到action列表
                            var conf = {
                                container: '#container',
                                url: api.util.getUrl('html/action/action.html'),
                                content: "",
                                async: false,
                                preLoad: function () {
                                    $("#depart").empty();
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                                    $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                                },
                                loaded: function (param) {
                                    var depOptions = {
                                        selector: '[name=depId]',
                                        optionField: {value: 'id', text: 'depName'},
                                        width: '70%',
                                        url: api.util.getUrl('apimanager/department/list'),
                                        change: function (e) {
                                            projectSelect.clear();
                                            var params = {};
                                            params['depId'] = e.target.value;
                                            projectSelect.load(params);
                                            projectSelect.val(projectId);
                                            projectSelect.doChange();
                                        }
                                    };
                                    var projectOptions = {
                                        selector: '[name=projectId]',
                                        optionField: {value: 'id', text: 'projectName'},
                                        width: '70%',
                                        url: api.util.getUrl('apimanager/project/list'),
                                        change: function (e) {
                                            moduleSelect.clear();
                                            var params = {};
                                            params['projectId'] = e.target.value;
                                            moduleSelect.load(params);
                                            moduleSelect.val(parentId);
                                        }
                                    };
                                    var moduleOptions = {
                                        selector: '[name=moduleId]',
                                        optionField: {value: 'id', text: 'moduleName'},
                                        width: '70%',
                                        url: api.util.getUrl('apimanager/module/list')
                                    };
                                    var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                    var projectSelect = api.ui.chosenSelect(projectOptions);
                                    var depSelect = api.ui.chosenSelect(depOptions);
                                    depSelect.val(depId);
                                    depSelect.doChange();
                                    api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                                        api.ui.editTable(actionTableOptions);
                                    });
                                }
                            }
                            api.ui.load(conf);
                        });
                        //保存
                        $('#headButton button:last').on('click', function () {
                            //表单非空校验
                            var i = 0;
                            $('#actionInfoForm').find('input,select').each(function(){
                                var value = $.trim($(this).val());
                                if(!value){
                                    i = 1;
                                    $(this).css('border-color','red');
                                    $(this).on('blur',function () {
                                        if($.trim($(this).val())){
                                            $(this).css('border-color','');
                                        }
                                    })
                                    return true;
                                }

                            });
                            if(i == 1){
                                var option={content: '请完善接口基本信息'};
                                api.ui.dialog(option).open();
                                actionTabConfObject.show('基本信息');
                                return;
                            }
                            if(headParam._checkEmpty() || requestParam._checkEmpty() || responseParam._checkEmpty()
                                || headParam.defaultFlag == 1 || requestParam.defaultFlag == 1 || responseParam.defaultFlag == 1){
                                var option = {content: '请完善接口参数信息'};
                                api.ui.dialog(option).open();
                                return;
                            }
                            var headArr = headParam.toData();
                            var requestArr = requestParam.toData();
                            var responseArr = responseParam.toData();
                            var requestData = actionInfoFormObject.toJson();
                            requestData['requestHeadDefinition'] = JSON.stringify(headArr);
                            requestData['requestDefinition'] = JSON.stringify(requestArr);
                            requestData['responseDefinition'] = JSON.stringify(responseArr);
                            $.ajax({
                                type: 'post',
                                url: api.util.getUrl('apimanager/action/add'),
                                dataType: 'json',
                                data : JSON.stringify(requestData),
                                contentType : 'application/json;charset=utf-8',
                                success: function (data) {
                                    if(data.code == -1){
                                        var option={content: '保存失败'};
                                        api.ui.dialog(option).open();

                                        return;
                                    }
                                    $('#depart').parent('.container').css('display','');
                                    //跳转到action列表
                                    var conf = {
                                        container: '#container',
                                        url: api.util.getUrl('html/action/action.html'),
                                        async: false,
                                        preLoad: function () {
                                            $("#depart").empty();
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                                            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                                        },
                                        loaded: function () {
                                            var depOptions = {
                                                selector: '[name=depId]',
                                                optionField: {value: 'id', text: 'depName'},
                                                width: '70%',
                                                url: api.util.getUrl('apimanager/department/list'),
                                                change: function (e, p) {
                                                    projectSelect.clear();
                                                    var params = {};
                                                    params['depId']=e.target.value;
                                                    projectSelect.load(params);
                                                    projectSelect.val(projectId);
                                                    projectSelect.doChange();
                                                }
                                            };
                                            var projectOptions = {
                                                selector: '[name=projectId]',
                                                optionField: {value: 'id', text: 'projectName'},
                                                width: '70%',
                                                url: api.util.getUrl('apimanager/project/list'),
                                                change: function (e, p) {
                                                    moduleSelect.clear();
                                                    var params = {};
                                                    params['projectId']=e.target.value;
                                                    moduleSelect.load(params);
                                                    moduleSelect.val(parentId);
                                                }
                                            };
                                            var moduleOptions = {
                                                selector: '[name=moduleId]',
                                                optionField: {value: 'id', text: 'moduleName'},
                                                width: '70%',
                                                url: api.util.getUrl('apimanager/module/list')
                                            };
                                            var projectSelect = api.ui.chosenSelect(projectOptions);
                                            var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                            var depSelect = api.ui.chosenSelect(depOptions);
                                            depSelect.val(depId);
                                            depSelect.doChange();
                                            api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                                                api.ui.editTable(actionTableOptions);
                                            });
                                        }
                                    }
                                    api.ui.load(conf);
                                }
                            });
                        });
                    }
                };
                api.ui.load(conf);
        }
        },
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/action/delete')}
],
headBtn: [
    {
        type: 'add-jump', text: '添加', icon: 'glyphicon glyphicon-plus', fn: function () {
            $('#depart').parent('.container').css('display','none');
            var parentId = $('select[name=moduleId]').val();
            var depId = $('select[name=depId]').val();
            var projectId = $('select[name=projectId]').val();
            var actionInfoFormObject, headParam, requestParam, responseParam;
            var conf = {
                container: '#container',
                url: api.util.getUrl('html/action/actionTab.html'),
                content: "",
                async: false,
                preLoad: function () {},
                loaded: function () {
                    var $editChange = $('#editChange');
                    var $cancelButton = $('#cancelButton'),$saveButton = $('#saveButton');
                    $editChange.css('display','none');
                    $('#actionPage').css('margin-top','90px');

                    var actionTabConf = {
                        container: '#tabs',
                        tabs: [{
                            title: '基本信息',
                            width: '10%',
                            href: api.util.getUrl('html/action/actionInfo.html'),
                            loaded: function () {
                                api.util.loadScript(api.util.getUrl('html/action/js/actionInfo.js'), function () {
                                    api.ui.chosenSelect(domainSelectOptions);
                                    api.ui.chosenSelect(typeSelectOption);
                                    var statusSelect = api.ui.chosenSelect(statusSelectOption);
                                    api.ui.chosenSelect(moduleOptions);
                                    actionInfoFormObject = api.ui.form(actionInfoFormOptions);
                                    actionInfoFormObject.giveVal({
                                        moduleId: parentId,
                                        status: 1,
                                        requestType: 1
                                    });

                                });
                            }
                        }, {
                            title: '接口参数',
                            width: '10%',
                            lazy: false,
                            href: api.util.getUrl('html/action/actionParam.html'),
                            loaded: function () {
                                api.util.loadScript(api.util.getUrl('html/action/js/actionParam.js'), function () {
                                    headParam = api.ui.param(headOptions);
                                    requestParam = api.ui.param(requestOptions);
                                    responseParam = api.ui.param(responseOptions);
                                    var $importBtn = $('#responseParam').find('#importBtn');
                                    $importBtn.on('click',function () {
                                        var dialogOptions = {
                                            container: 'body',
                                            content: '<textarea class="col-12 form-control" name="responseJson" style="height: 300px;"></textarea>',
                                            iTitle: false,
                                            title: '响应参数',
                                            width: '150%',
                                            buttons:[
                                                {
                                                    type: 'close', text: '关闭', fn: function () {}
                                                },{
                                                    type: 'sure', text: '导入', fn: function () {
                                                        $.ajax({
                                                            url: api.util.getUrl('apimanager/params/convertJsonToRows'),
                                                            type: 'post',
                                                            data : $('textarea[name=responseJson]').val(),
                                                            contentType : 'application/json;charset=utf-8',
                                                            dataType: 'json',
                                                            success: function (result) {
                                                                var data = result.data;
                                                                $.each(JSON.parse(data), function (index, rowData) {
                                                                    responseParam._showRow(rowData);
                                                                })
                                                                $('#responseModal').map(function () {
                                                                    if (!$(this).is(":hidden")){
                                                                        $(this).modal('hide');
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                }
                                            ]
                                        };
                                        api.ui.dialog(dialogOptions).open();
                                    })
                                });
                            }
                        }]
                    };
                    var actionTabConfObject = api.ui.tabs(actionTabConf);
                    //退出添加
                    $cancelButton.mousedown(function () {
                        $('#depart').parent('.container').css('display', '');
                        //跳转到action列表
                        var conf = {
                            container: '#container',
                            url: api.util.getUrl('html/action/action.html'),
                            content: "",
                            async: false,
                            preLoad: function () {
                                $("#depart").empty();
                                $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                                $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                                $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                                $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                            },
                            loaded: function (param) {
                                var depOptions = {
                                    selector: '[name=depId]',
                                    optionField: {value: 'id', text: 'depName'},
                                    width: '70%',
                                    url: api.util.getUrl('apimanager/department/list'),
                                    change: function (e) {
                                        projectSelect.clear();
                                        var params = {};
                                        params['depId'] = e.target.value;
                                        projectSelect.load(params);
                                        projectSelect.val(projectId);
                                        projectSelect.doChange();
                                    }
                                };
                                var projectOptions = {
                                    selector: '[name=projectId]',
                                    optionField: {value: 'id', text: 'projectName'},
                                    width: '70%',
                                    url: api.util.getUrl('apimanager/project/list'),
                                    change: function (e) {
                                        moduleSelect.clear();
                                        var params = {};
                                        params['projectId'] = e.target.value;
                                        moduleSelect.load(params);
                                        moduleSelect.val(parentId);
                                    }
                                };
                                var moduleOptions = {
                                    selector: '[name=moduleId]',
                                    optionField: {value: 'id', text: 'moduleName'},
                                    width: '70%',
                                    url: api.util.getUrl('apimanager/module/list')
                                };
                                var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                var projectSelect = api.ui.chosenSelect(projectOptions);
                                var depSelect = api.ui.chosenSelect(depOptions);
                                depSelect.val(depId);
                                depSelect.doChange();
                                api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                                    api.ui.editTable(actionTableOptions);
                                });
                            }
                        }
                        api.ui.load(conf);
                    });
                    //保存
                    $('#headButton button:last').on('click', function () {
                        //表单非空校验
                        var i = 0;
                        $('#actionInfoForm').find('input,select').each(function(){
                            var value = $.trim($(this).val());
                            if(!value){
                                i = 1;
                                $(this).css('border-color','red');
                                $(this).on('blur',function () {
                                    if($.trim($(this).val())){
                                        $(this).css('border-color','');
                                    }
                                })
                                return true;
                            }

                        });
                        if(i == 1){
                            var option={content: '请完善接口基本信息'};
                            api.ui.dialog(option).open();
                            actionTabConfObject.show('基本信息');
                            return;
                        }
                        if (headParam._checkEmpty() || requestParam._checkEmpty() || responseParam._checkEmpty()
                            || headParam.defaultFlag == 1 || requestParam.defaultFlag == 1 || responseParam.defaultFlag == 1) {
                            var option={content: '请完善接口参数信息'};
                            api.ui.dialog(option).open();
                            return;
                        }
                        var headArr = headParam.toData();
                        var requestArr = requestParam.toData();
                        var responseArr = responseParam.toData();
                        var requestData = actionInfoFormObject.toJson();
                        requestData['requestHeadDefinition'] = JSON.stringify(headArr);
                        requestData['requestDefinition'] = JSON.stringify(requestArr);
                        requestData['responseDefinition'] = JSON.stringify(responseArr);
                        $.ajax({
                            type: 'post',
                            url: api.util.getUrl('apimanager/action/add'),
                            dataType: 'json',
                            data : JSON.stringify(requestData),
                            contentType : 'application/json;charset=utf-8',
                            success: function (data) {
                                if (data.code == -1) {
                                    var option={content: '保存失败'};
                                    api.ui.dialog(option).open();

                                    return;
                                }
                                $('#depart').parent('.container').css('display','');
                                //跳转到action列表
                                var conf = {
                                    container: '#container',
                                    url: api.util.getUrl('html/action/action.html'),
                                    async: false,
                                    preLoad: function () {
                                        $("#depart").empty();
                                        $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                                        $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                                        $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                                        $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                                    },
                                    loaded: function () {
                                        var depOptions = {
                                            selector: '[name=depId]',
                                            optionField: {value: 'id', text: 'depName'},
                                            width: '70%',
                                            url: api.util.getUrl('apimanager/department/list'),
                                            change: function (e, p) {
                                                projectSelect.clear();
                                                var params = {};
                                                params['depId']=e.target.value;
                                                projectSelect.load(params);
                                                projectSelect.val(projectId);
                                                projectSelect.doChange();
                                            }
                                        };
                                        var projectOptions = {
                                            selector: '[name=projectId]',
                                            optionField: {value: 'id', text: 'projectName'},
                                            width: '70%',
                                            url: api.util.getUrl('apimanager/project/list'),
                                            change: function (e, p) {
                                                moduleSelect.clear();
                                                var params = {};
                                                params['projectId']=e.target.value;
                                                moduleSelect.load(params);
                                                moduleSelect.val(parentId);
                                            }
                                        };
                                        var moduleOptions = {
                                            selector: '[name=moduleId]',
                                            optionField: {value: 'id', text: 'moduleName'},
                                            width: '70%',
                                            url: api.util.getUrl('apimanager/module/list')
                                        };
                                        var projectSelect = api.ui.chosenSelect(projectOptions);
                                        var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                        var depSelect = api.ui.chosenSelect(depOptions);
                                        depSelect.val(depId);
                                        depSelect.doChange();
                                        api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                                            api.ui.editTable(actionTableOptions);
                                        });
                                    }
                                }
                                api.ui.load(conf);
                            }
                        });
                    });
                }
            };
            api.ui.load(conf);
        }
    }
],
    url: api.util.getUrl('apimanager/action/findPage')
};
