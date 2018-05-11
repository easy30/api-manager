function departmentClick(){
    $('#depart').parent('.container-fluid').css('display','');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/department/department.html'),
        async: false,
        preLoad: function (content) {
            $("#depart").empty();
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
        },
        loaded: function () {
            api.util.loadScript(api.util.getUrl('html/department/js/department.js'), function () {
                api.ui.editTable(departmentOptions);
            });
        }
    }
    api.ui.load(conf);
}
function projectClick(){
    $('#depart').parent('.container-fluid').css('display','');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/project/project.html'),
        async: false,
        preLoad: function (content) {
            $("#depart").empty();
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
        },
        loaded: function () {
            var options = {
                selector: '[name=depId]',
                optionField: {value: 'id', text: 'depName'},
                width: '70%',
                url: api.util.getUrl('apimanager/department/list')
            }
            api.ui.chosenSelect(options);
            api.util.loadScript(api.util.getUrl("html/project/js/project.js"), function () {
                api.ui.editTable(projectTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function projectClick1(){
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/project/project.html'),
        async: false,
        preLoad: function () {
            var depId = $("select[name=depId]").val();
            $("#depart").empty();
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
            return depId;
        },
        loaded: function (param) {
            var options = {
                selector: '[name=depId]',
                optionField: {value: 'id', text: 'depName'},
                width: '70%',
                async: false,
                url: api.util.getUrl('/apimanager/department/list')
            }
            api.ui.chosenSelect(options).val(param);
            api.util.loadScript(api.util.getUrl("html/project/js/project.js"), function () {
                api.ui.editTable(projectTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function moduleClick(){
    $('#depart').parent('.container-fluid').css('display','');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/module/module.html'),
        async: false,
        preLoad: function (content) {
            $("#depart").empty();
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
        },
        loaded: function () {
            var projectOptions = {
                selector: '[name=projectId]',
                optionField: {value: 'id', text: 'projectName'},
                width: '70%',
                url: api.util.getUrl('apimanager/project/list')
            };
            var projectSelect = api.ui.chosenSelect(projectOptions);
            api.util.loadScript(api.util.getUrl("html/module/js/module.js"), function () {
                api.ui.editTable(moduleTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function moduleClick1(){
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/module/module.html'),
        async: false,
        preLoad: function () {
            var depId = $("select[name=depId]").val();
            var projectId = $("select[name=projectId]").val();
            var param = {};
            param['depId'] = depId;
            param['projectId'] = projectId;
            $("#depart").empty();
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
            return param;
        },
        loaded: function (param) {
            var depOptions = {
                selector: '[name=depId]',
                optionField: {value: 'id', text: 'depName'},
                width: '70%',
                async: false,
                url: api.util.getUrl('apimanager/department/list'),
                change: function (e) {
                    var params = {};
                    params['depId'] = e.target.value;
                    var projectOptions = {
                        selector: '[name=projectId]',
                        optionField: {value: 'id', text: 'projectName'},
                        width: '70%',
                        async: false,
                        params: params,
                        url: api.util.getUrl('apimanager/project/list')
                    };
                    var projectSelect = api.ui.chosenSelect(projectOptions);
                    projectSelect.val(param.projectId);
                }
            };

            var depSelect = api.ui.chosenSelect(depOptions);
            depSelect.val(param.depId);
            depSelect.doChange();
            api.util.loadScript(api.util.getUrl("html/module/js/module.js"), function () {
                api.ui.editTable(moduleTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function actionClick(){
    $('#depart').parent('.container-fluid').css('display','');
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
            var moduleOptions = {
                selector: '[name=moduleId]',
                optionField: {value: 'id', text: 'moduleName'},
                width: '70%',
                url: api.util.getUrl('apimanager/module/list')
            };
            var moduleSelect = api.ui.chosenSelect(moduleOptions);
            api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                api.ui.editTable(actionTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function actionClick1(){
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/action.html'),
        content: "",
        async: false,
        preLoad: function () {
            var param = {};
            param['depId'] = $("select[name=depId]:first").val();
            param['projectId'] = $("select[name=projectId]:first").val();
            param['parentId'] = $("select[name=moduleId]:first").val();
            $("#depart").empty();
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"departmentClick()\">Department</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"moduleClick1()\">Module</a></li>");
            $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
            return param;
        },
        loaded: function (param) {
            var depId = param.depId, projectId = param.projectId, parentId = param.parentId;
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
}
function actionTestClick() {
    $('#depart').parent('.container-fluid').css('display','none');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/actionTest.html'),
        async: false,
        loaded: function () {
            $('#testPage').css('margin-top','90px');
            var testHeadParam, testRequestParam;
            api.util.loadScript(api.util.getUrl('html/action/js/actionTest.js'), function () {
                envOptions.change = function (event) {
                    var value = event.target.value;
                    domainEditSelect._empty();
                    if(value && $.trim(value) != ''){
                        domainEditSelect._load({envId: value})
                    } else {
                        domainEditSelect._load({});
                    }
                };
                var envSelect = api.ui.chosenSelect(envOptions);
                var domainEditSelect = api.ui.editSelect(domainEditOptions);
                var requestTypeSelect = api.ui.chosenSelect(requestTypeOptions);
                testHeadParam = api.ui.param(testHeadOptions);
                testRequestParam = api.ui.param(testRequestOptions);
            });
            $('#sendRequest').on('click', function () {
                var domainName = $('[name=testDomainId]').val(), requestType = $('[name=testRequestType]').val(), requestUrl = $('[name=testRequestUrl]').val();
                if(!domainName || $.trim(domainName) == ''){
                    var options = {
                        content: '服务地址不能为空！'
                    };
                    api.ui.dialog(options).open();
                    return;
                }
                if(!requestUrl || $.trim(requestUrl) == ''){
                    var options = {
                        content: '接口地址不能为空！'
                    };
                    api.ui.dialog(options).open();
                    return;
                }
                var progress = api.ui.progress({});
                var requestMockTemplate = api.util.buildMockTemplate(testRequestParam.toData());
                var headMockTemplate = api.util.buildMockTemplate(testHeadParam.toData());
                var requestData = Mock.mock(requestMockTemplate), headData = Mock.mock(headMockTemplate);
                var requestDataStr = JSON.stringify(requestData), headDataStr = JSON.stringify(headData);
                $('#requestJson').JSONView(requestDataStr);
                $('#requestJsonArea').val(requestDataStr);

                var requestBody = {};
                requestBody['domainName'] = domainName;
                requestBody['requestType'] = requestType;
                requestBody['requestUrl'] = requestUrl;
                requestBody['requestHeadData'] = headDataStr;
                requestBody['requestData'] = requestDataStr;
                $.ajax({
                    url: api.util.getUrl('apimanager/tester/send'),
                    type: 'POST',
                    contentType: 'application/json;charset=UTF-8', //解决415问题
                    data: JSON.stringify(requestBody),//解决400问题
                    dataType: 'json',
                    success: function (result) {
                        progress._hide();
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
    }
    api.ui.load(conf);
}

function domainClick() {
    $('#depart').parent('.container-fluid').css('display','none');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/domain/domain.html'),
        async: false,
        preLoad: function () {
            var param = {};
            param['envId'] = $("select[name=envId]").val();
            return param;
        },
        loaded: function (param) {
            var envOptions = {
                selector: '[name=envId]',
                optionField: {value: 'id', text: 'envName'},
                width: '50%',
                url: api.util.getUrl('apimanager/env/list')
            };
            var envSelect = api.ui.chosenSelect(envOptions);
            api.util.loadScript(api.util.getUrl("html/domain/js/domain.js") ,function () {
                api.ui.editTable(domainTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function envClick() {
    $('#depart').parent('.container-fluid').css('display','none');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/env/env.html'),
        async: false,
        loaded: function (param) {
            api.util.loadScript(api.util.getUrl("html/env/js/env.js") ,function () {
                api.ui.editTable(envTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function actionLoginClick() {
    $('#depart').parent('.container-fluid').css('display','none');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/actionlogin/actionLogin.html'),
        async: false,
        loaded: function () {
            api.util.loadScript(api.util.getUrl("html/actionlogin/js/actionLogin.js") ,function () {
                var actionLoginDomainSelectOptions = {
                    selector: '[name=domainId]',
                    optionField: {value: 'id', text: 'domainName'},
                    width: '60%',
                    async: false,
                    url: api.util.getUrl('/apimanager/domain/list')
                }
                api.ui.chosenSelect(actionLoginDomainSelectOptions);
                api.ui.editTable(actionLoginTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function batchTestClick() {
    $('#depart').parent('.container-fluid').css('display','none');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/actionBatchTest.html'),
        async: false,
        loaded: function () {
            var envOptions = {
                selector: '[name=envId]',
                optionField: {value: 'id', text: 'envName'},
                width: '50%',
                blank: false,
                url: api.util.getUrl('apimanager/env/list')
            };
            var envSelect = api.ui.chosenSelect(envOptions);
            var moduleOptions = {
                selector: '[name=moduleId]',
                optionField: {value: 'id', text: 'moduleName'},
                width: '70%',
                url: api.util.getUrl('apimanager/module/list')
            };
            var moduleSelect = api.ui.chosenSelect(moduleOptions);
            api.util.loadScript(api.util.getUrl("html/action/js/actionBatchTest.js") ,function () {
                api.ui.editTable(batchTestActionTableOptions);
            });

            $('#batchSend').on('click', function () {
                var buttons = $('#selectedActionArea').find('button');
                if(!buttons || buttons.length == 0){
                    var options = {
                        content: '请选择要测试的接口！'
                    };
                    api.ui.dialog(options).open();
                    return;
                }
                var progress = api.ui.progress({});
                $('#resultList').empty();
                $('#resultContent').empty();
                var envId = $('select[name=envId]').val(), actionIds = '', data = {};
                data['envId'] = envId;
                $.each(buttons, function (index, btn) {
                    var $this = $(btn), actionId = $this.attr('actionId'), requestUrl = $this.attr('requestUrl');
                    data['actionId'] = actionId;
                    data['requestUrl'] = requestUrl;
                    $.ajax({
                        url: api.util.getUrl('apimanager/tester/sendByActionId'),
                        type: 'GET',
                        data: data,
                        dataType: 'json',
                        success: function (result) {
                            var resultItem = $('<a style="margin: 2px 2px; cursor: pointer; display: block; font-size: 14px; font-weight: bold;"></a>');
                            if(result['code'] == 0){
                                if(result['data']['code'] == 0){
                                    resultItem.css('color', 'green');
                                } else {
                                    resultItem.css('color', 'red');
                                }
                            } else {
                                resultItem.css('color', 'red');
                            }
                            resultItem.hover(function () {
                                resultItem.css('background-color', '#d1ecf1');
                            }, function () {
                                resultItem.css('background-color', '');
                            })
                            resultItem.text(result.data.requestUrl);
                            resultItem.on('click', function () {
                                $('#resultContent').empty();
                                $('#resultContent').append('<p><span style="color: #17a2b8">服务地址：</span><br/>' + result.data['wholeUrl'] + '</p>');
                                $('#resultContent').append('<p><span style="color: #17a2b8">请求头参数：</span><br/>' + result.data['requestHeadData'] + '</p>');
                                $('#resultContent').append('<p><span style="color: #17a2b8">请求参数：</span><br/>' + result.data['requestData'] + '</p>');
                                var resultContent = $('<p><span style="color: #17a2b8">返回结果：</span><br/><span id="resultContent"></span></p>');
                                resultContent.find('#resultContent').text(result.data['result']);
                                $('#resultContent').append(resultContent);
                            });
                            $('#resultList').append(resultItem);
                            progress._hide();
                        }
                    });
                })
            });
            $('#clearSelectedList').on('click', function () {
                $('#selectedActionArea').empty();
            });
            $('#clearResultList').on('click', function () {
                $('#resultList').empty();
                $('#resultContent').empty();
            });
        }
    }
    api.ui.load(conf);
}

function logOut() {
    $.ajax({
        url: api.util.getUrl('apimanager/user/loginOut'),
        type: 'get',
        dataType: 'json',
        success: function (result) {
            window.location.href = '/login.html';
        }
    });
}

function userInfo() {
    var userConf = {
        container: '#container',
        url: api.util.getUrl('html/user/userInfo.html'),
        async: false,
        preLoad: function (content) {
            $("#depart").css('display','none');
        },
        loaded: function () {
            $('#userInfoForm').css('margin-top','100px');
            $.ajax({
                url: api.util.getUrl('/apimanager/user/findBySession'),
                type: 'GET',
                dataType: 'json',
                success: function (result) {
                    if(result.code == '0') {
                        var data = result.data;
                        api.util.loadScript(api.util.getUrl('html/user/js/userInfo.js'),function () {
                            var userForm = api.ui.form(userInfoOptions);
                            userForm.giveVal(data);
                        })
                    }else {
                        var dialConf = {
                            container: 'body',
                            content: result.msg,
                            iTitle: true,
                            title: '提示',
                        }
                        api.ui.dialog(dialConf).open();
                    }
                }
            })
            //cancel
            $('#userCancelBtn').on('click',function () {
                window.location.href = 'index.html';
            })
            //save
            $('#userSaveBtn').on('click',function () {
                var params = {};
                params['id'] = $('input[name=id]').val();
                params['userName'] = $('input[name=userName]').val();
                params['account'] = $('input[name=account]').val();
                params['email'] = $('input[name=email]').val();
                //表单非空校验
                var flag = false;
                $.each(params, function (name,value) {
                    if(!$.trim(value)){
                        if (name=='userName') {
                            var dialConf = {
                                container: 'body',
                                content: '中文名称不能为空',
                                iTitle: true,
                                title: '提示',
                            }
                            api.ui.dialog(dialConf).open();
                            flag = true;
                        }
                        return false;
                    }
                })
                if (!flag) {
                    $.ajax({
                        url: api.util.getUrl('/apimanager/user/update'),
                        data: params,
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == '0') {
                                $.ajax({
                                    url: api.util.getUrl('apimanager/user/loginOut'),
                                    type: 'get',
                                    dataType: 'json',
                                    success: function (result) {
                                        window.location.href = '/login.html';
                                    }
                                })
                            } else {
                                var dialConf = {
                                    container: 'body',
                                    content: result.msg,
                                    iTitle: true,
                                    title: '提示',
                                }
                                api.ui.dialog(dialConf).open();
                            }
                        }
                    })
                }
            })
        }
    }
    api.ui.load(userConf);
}