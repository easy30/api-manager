var actionTableOptions = {
    container: '#editTable',
    headers: [
        {text: '接口编号', width: '10%'},
        {text: '接口名称', width: '10%'},
        {text: '所属模块', width: '15%'},
        {text: '请求类型', width: '10%'},
        {text: '状态', width: '10%'},
        {text: '操作', width: '15%'}
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
                url: api.util.getUrl('apimanager/meta/findMeta')
            }
        },
        {name: 'status', type:'select', inputDesc: '状态', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 3},
                url: api.util.getUrl('apimanager/meta/findMeta')
            }
        }
    ],
    rowButtons: [
        {type: 'view', text: '查看', icon: 'glyphicon glyphicon-eye-open', fn: function (param) {
                $('#depart').parent('.container').css('display', 'none');
                var actionInfoFormObject, headParam, requestParam, responseParam;
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/actionTab.html'),
                    async: false,
                    loaded: function () {
                        $('#headButton button:last').css('display', 'none');
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
                                                actionInfoFormObject = api.ui.form(actionInfoFormOptions);
                                                actionInfoFormObject.giveVal({
                                                    id: data['id'],
                                                    requestUrl: data['requestUrl'],
                                                    actionName: data['actionName'],
                                                    moduleId: data['moduleId'],
                                                    requestType: data['requestType'],
                                                    status: data['status'],
                                                    actionDesc: data['actionDesc']
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
                                                var data = result.data, dataStr = JSON.stringify(data);
                                                $('#responseJsonArea').val(dataStr);
                                                $('#responseJson').JSONView(dataStr);
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
                        api.ui.tabs(actionTabConf);
                        //切换
                        $('#headButton button:first').on('click', function () {
                            actionInfoFormObject.enable();
                            headParam.enable();
                            requestParam.enable();
                            responseParam.enable();
                            $('#headButton button:first').css('display', 'none');
                            $('#headButton button:last').css('display', '');
                        });
                        //保存
                        $('#headButton button:last').on('click', function () {
                            var headArr = headParam.toData();
                            var requestArr = requestParam.toData();
                            var responseArr = responseParam.toData();
                            var requestData = actionInfoFormObject.toJson();
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
                                            api.ui.chosenSelect(depOptions);
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
        type: 'add-jump', text: '添加', icon: 'glyphicon glyphicon-plus', fn: function (row) {
            $('#depart').parent('.container').css('display','none');
            var actionInfoFormObject, headParam, requestParam, responseParam;
            var conf = {
                container: '#container',
                url: api.util.getUrl('html/action/actionTab.html'),
                content: "",
                async: false,
                preLoad: function () {},
                loaded: function () {
                    $('#headButton button:first').css('display','none');
                    var actionTabConf = {
                        container: '#tabs',
                        tabs: [{
                            title: '基本信息',
                            width: '10%',
                            href: api.util.getUrl('html/action/actionInfo.html'),
                            loaded: function () {
                                api.util.loadScript(api.util.getUrl('html/action/js/actionInfo.js'), function () {
                                    api.ui.chosenSelect(typeSelectOption);
                                    api.ui.chosenSelect(statusSelectOption);
                                    api.ui.chosenSelect(moduleOptions);
                                    actionInfoFormObject = api.ui.form(actionInfoFormOptions);
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
                                });
                            }
                        }]
                    };
                    api.ui.tabs(actionTabConf);

                    //切换
                    $('#headButton button:first').on('click', function () {
                        actionInfoFormObject.enable();
                        headParam.enable();
                        requestParam.enable();
                        responseParam.enable();
                        $('#headButton button:first').css('display','none');
                        $('#headButton button:last').css('display','');
                    });
                    //保存
                    $('#headButton button:last').on('click', function () {
                        //表单非空校验
                        var i=0;
                        $('#actionInfoForm').find('input,select').each(function(){
                            var value = $.trim($(this).val());
                            if(!value){
                                i=1;
                                var option={content: '存在空值'};
                                api.ui.dialog(option).open();
                                return false;
                            }
                        });
                        if(i==1){
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
                                        api.ui.chosenSelect(depOptions);
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
