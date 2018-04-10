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
                var actionInfoFormObject;
                var headParam;
                var requestParam;
                var responseParam;
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/actionTab.html'),
                    content: "",
                    async: false,
                    preLoad: function () {},
                    loaded: function () {
                        $('#headButton button:last').css('display', 'none');
                        var actionTabConf = {
                            container: '#tabs',
                            tabs: [{
                                title: '基本信息',
                                width: '10%',
                                href: api.util.getUrl('html/action/actionInfo1.html'),
                                loaded: function () {
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data:{id: param.id},
                                        dataType: "json",
                                        contentType: 'json',
                                        success: function(result){
                                            var data = result['data'];
                                            var moduleOptions = {
                                                selector: '[name=moduleId]',
                                                optionField: {value: 'id', text: 'moduleName'},
                                                width: '60%',
                                                url: api.util.getUrl('/apimanager/module/list')
                                            };
                                            api.ui.chosenSelect(moduleOptions);
                                            if(data){
                                                var actionInfoFormOptions={
                                                    container:'#actionInfoForm'
                                                };
                                                actionInfoFormObject =  api.ui.form(actionInfoFormOptions);
                                                actionInfoFormObject.giveVal({
                                                    id: data['id'],
                                                    requestUrl: data['requestUrl'],
                                                    actionName:data['actionName'],
                                                    moduleId:data['moduleId'],
                                                    requestType:data['requestType'],
                                                    status:data['status'],
                                                    actionDesc: data['actionDesc']
                                                });
                                                actionInfoFormObject.disable();
                                            }
                                        }
                                    });
                                }
                            }, {
                                title: '接口参数',
                                width: '10%',
                                lazy:false,
                                href: api.util.getUrl('html/action/actionParam.html'),
                                loaded: function () {
                                    $.ajax({
                                        type: 'get',
                                        url: api.util.getUrl('/apimanager/action/findById'),
                                        data:{id: param.id},
                                        dataType: "json",
                                        async: false,
                                        contentType: 'json',
                                        success: function(result){
                                            var data = result['data'];
                                            if(data){
                                                api.util.loadScript(api.util.getUrl('html/action/js/actionParam.js'), function () {
                                                    headOptions.data=JSON.parse(data['requestHeadDefinition']);
                                                    requestOptions.data=JSON.parse(data['requestDefinition']);
                                                    responseOptions.data=JSON.parse(data['responseDefinition']);
                                                    headParam = api.ui.param(headOptions);
                                                    requestParam = api.ui.param(requestOptions);
                                                    responseParam = api.ui.param(responseOptions);
                                                    headParam.disable();
                                                    requestParam.disable();
                                                    responseParam.disable();
                                                });
                                            }
                                        }
                                    });
                                }
                            }]
                        };
                        api.ui.tabs(actionTabConf);
                    }
                };
                api.ui.load(conf);
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
                    var headStr = '';
                    for(var i = 0;i<headArr.length;i++){
                        if(i==headArr.length-1){
                            headStr= headStr+JSON.stringify(headArr[i])+']';
                            continue;
                        }
                        if(i==0){
                            headStr = '['+JSON.stringify(headArr[i])+',';
                        }else{
                            headStr = headStr+JSON.stringify(headArr[i])+',';
                        }
                    }
                    var requestHeadJson = {'requestHeadDefinition': headStr};

                    var requestArr = requestParam.toData();
                    var requestStr = '';
                    for(var i = 0;i<requestArr.length;i++){
                        if(i==requestArr.length-1){
                            requestStr= requestStr+JSON.stringify(requestArr[i])+']';
                            continue;
                        }
                        if(i==0){
                            requestStr = '['+JSON.stringify(requestArr[i])+',';
                        }else{
                            requestStr = requestStr+JSON.stringify(requestArr[i])+',';
                        }
                    }
                    var requestJson = {'requestDefinition': requestStr};

                    var responseArr = responseParam.toData();
                    var responseStr = '';
                    for(var i = 0;i<responseArr.length;i++){
                        if(i==responseArr.length-1){
                            responseStr= responseStr+JSON.stringify(responseArr[i])+']';
                            continue;
                        }
                        if(i==0){
                            responseStr = '['+JSON.stringify(responseArr[i])+',';
                        }else {
                            responseStr = responseStr+JSON.stringify(responseArr[i])+',';
                        }
                    }
                    var responseJson = {'responseDefinition': responseStr};

                    var paramData = $.extend({},actionInfoFormObject.toJson(),requestHeadJson,requestJson,responseJson);
                    $.ajax({
                        type: 'post',
                        url: api.util.getUrl('apimanager/action/update'),
                        dataType: 'json',
                        data: JSON.stringify(paramData),
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            //不跳转到列表
                            actionInfoFormObject.disable();
                            headParam.disable();
                            requestParam.disable();
                            responseParam.disable();
                            $('#headButton button:first').css('display', '');
                            $('#headButton button:last').css('display', 'none');
                        }
                    });
                });
            }
        },
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/action/delete')}
    ],
    headBtn: [
        {
            type: 'add-jump', text: '添加', icon: 'glyphicon glyphicon-plus', fn: function (row) {
                var actionInfoFormObject;
                var headParam;
                var requestParam;
                var responseParam;
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
                                href: api.util.getUrl('html/action/actionInfo1.html'),
                                loaded: function () {
                                    var moduleOptions = {
                                        selector: '[name=moduleId]',
                                        optionField: {value: 'id', text: 'moduleName'},
                                        width: '60%',
                                        url: api.util.getUrl('/apimanager/module/list')
                                    };
                                    api.ui.chosenSelect(moduleOptions);
                                }
                            }, {
                                title: '接口参数',
                                width: '10%',
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
                    }
                };
                api.ui.load(conf);
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
                    $.ajax({
                        type: 'post',
                        url: api.util.getUrl('apimanager/action/add'),
                        dataType: 'json',
                        data : JSON.stringify(actionInfoFormObject.toJson()),
                        contentType : 'application/json;charset=utf-8',
                        success: function (data) {
                            //不跳转到列表
                            actionInfoFormObject.disable();
                            $('#headButton button:first').css('display', '');
                            $('#headButton button:last').css('display', 'none');
                            //跳转到action列表
                        //     var conf = {
                        //         container: '#container',
                        //         url: api.util.getUrl('html/action/action.html'),
                        //         content: "",
                        //         async: false,
                        //         preLoad: function () {
                        //             $("#depart").empty();
                        //             $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                        //             $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                        //             $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                        //             $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                        //         },
                        //         loaded: function () {
                        //             var depOptions = {
                        //                 selector: '[name=depId]',
                        //                 optionField: {value: 'id', text: 'depName'},
                        //                 width: '70%',
                        //                 url: api.util.getUrl('apimanager/department/list'),
                        //                 change: function (e, p) {
                        //                     projectSelect.clear();
                        //                     var params = {};
                        //                     params['depId']=e.target.value;
                        //                     projectSelect.load(params);
                        //                 }
                        //             };
                        //             var projectOptions = {
                        //                 selector: '[name=projectId]',
                        //                 optionField: {value: 'id', text: 'projectName'},
                        //                 width: '70%',
                        //                 url: api.util.getUrl('apimanager/project/list'),
                        //                 change: function (e, p) {
                        //                     moduleSelect.clear();
                        //                     var params = {};
                        //                     params['projectId']=e.target.value;
                        //                     moduleSelect.load(params);
                        //                 }
                        //             };
                        //             var moduleOptions = {
                        //                 selector: '[name=moduleId]',
                        //                 optionField: {value: 'id', text: 'moduleName'},
                        //                 width: '70%',
                        //                 url: api.util.getUrl('apimanager/module/list')
                        //             };
                        //             var projectSelect = api.ui.chosenSelect(projectOptions);
                        //             var moduleSelect = api.ui.chosenSelect(moduleOptions);
                        //             api.ui.chosenSelect(depOptions);
                        //             api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                        //                 api.ui.editTable(actionTableOptions);
                        //             });
                        //         }
                        //     }
                        //     api.ui.load(conf);
                            // var conf = {
                            //     container: '#container',
                            //     url: api.util.getUrl('html/action/action.html'),
                            //     content: "",
                            //     async: false,
                            //     preLoad: function () {
                            //         $("#depart").empty();
                            //         $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
                            //         $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                            //         $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
                            //         $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                            //     },
                            //     loaded: function () {
                            //         var depOptions = {
                            //             selector: '[name=depId]',
                            //             optionField: {value: 'id', text: 'depName'},
                            //             width: '70%',
                            //             url: api.util.getUrl('apimanager/department/list'),
                            //             change: function (e, p) {
                            //                 projectSelect.clear();
                            //                 var params = {};
                            //                 params['depId']=e.target.value;
                            //                 projectSelect.load(params);
                            //             }
                            //         };
                            //         var projectOptions = {
                            //             selector: '[name=projectId]',
                            //             optionField: {value: 'id', text: 'projectName'},
                            //             width: '70%',
                            //             url: api.util.getUrl('apimanager/project/list'),
                            //             change: function (e, p) {
                            //                 moduleSelect.clear();
                            //                 var params = {};
                            //                 params['projectId']=e.target.value;
                            //                 moduleSelect.load(params);
                            //             }
                            //         };
                            //         var moduleOptions = {
                            //             selector: '[name=moduleId]',
                            //             optionField: {value: 'id', text: 'moduleName'},
                            //             width: '70%',
                            //             url: api.util.getUrl('apimanager/module/list')
                            //         };
                            //         var projectSelect = api.ui.chosenSelect(projectOptions);
                            //         var moduleSelect = api.ui.chosenSelect(moduleOptions);
                            //         api.ui.chosenSelect(depOptions);
                            //         api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                            //             api.ui.editTable(actionTableOptions);
                            //         });
                            //     }
                            // }
                            // api.ui.load(conf);
                        }
                    });
                });
            }
        }
    ],
    url: api.util.getUrl('apimanager/action/findPage')
};
