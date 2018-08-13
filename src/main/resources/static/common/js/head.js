function departmentClick(){
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
            var userNameOptions = {
                selector: '[name=createUser]',
                optionField: {value: 'id', text: 'userName'},
                width: '50%',
                blank: true,
                url: api.util.getUrl('apimanager/user/list')
            }
            var createSelect = api.ui.chosenSelect(userNameOptions);
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
            var userNameOptions = {
                selector: '[name=createUser]',
                optionField: {value: 'id', text: 'userName'},
                width: '50%',
                blank: true,
                url: api.util.getUrl('apimanager/user/list')
            }
            var createSelect = api.ui.chosenSelect(userNameOptions);
            api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                api.ui.editTable(actionTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function actionTestClick() {
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/actionTest.html'),
        async: false,
        loaded: function () {
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
    $('#depart').empty();
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
    $('#depart').empty();
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
    $('#depart').empty();
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
function tableManageClick() {
    $('#depart').empty();
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/sysdb/table.html'),
        async: false,
        loaded: function () {
            api.util.loadScript(api.util.getUrl("html/sysdb/js/table.js") ,function () {
                var dbNameSelectOptions = {
                    selector: '[name=dbName]',
                    width: '60%',
                    blank: false,
                    async: false,
                    optionField: {value: 'dbName', text: 'dbName'},
                    url: api.util.getUrl('apimanager/dbconfig/list')
                }
                api.ui.chosenSelect(dbNameSelectOptions);
                api.ui.editTable(tableListTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function batchTestClick() {
    $('#depart').empty();
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
            var userNameOptions = {
                selector: '[name=createUser]',
                optionField: {value: 'id', text: 'userName'},
                width: '50%',
                blank: true,
                url: api.util.getUrl('apimanager/user/list')
            }
            var createSelect = api.ui.chosenSelect(userNameOptions);
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

function groupTestClick() {
    $('#depart').empty();
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/groupTest.html'),
        async: false,
        loaded: function () {
            var groupOptions = {
                selector: '[name=groupId]',
                optionField: {value: 'id', text: 'groupName'},
                width: '50%',
                blank: false,
                url: api.util.getUrl('apimanager/testgroup/list')
            };
            api.ui.chosenSelect(groupOptions);

            api.util.loadScript(api.util.getUrl("html/action/js/groupTest.js") ,function () {
                api.ui.editTable(groupTestTableOptions);
            });
        }
    }
    api.ui.load(conf);
}

function objectDescClick() {
    $('#depart').empty();
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/objectFieldDesc.html'),
        async: false,
        loaded: function () {
            api.util.loadScript(api.util.getUrl("html/action/js/objectFieldDesc.js") ,function () {
                api.ui.editTable(objectFieldDescTableOptions);
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
    $('#depart').empty();
    var userConf = {
        container: '#container',
        url: api.util.getUrl('html/user/userInfo.html'),
        async: false,
        preLoad: function (content) {
            $('#depart').parent('.container-fluid').css('display','none');
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
                            $(".emailCompletion .input").mailAutoComplete();
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
                var flag = false,params = {};
                params['id'] = $('input[name=id]').val();
                params['userName'] = $('input[name=userName]').val();
                params['account'] = $('input[name=account]').val();
                //邮箱格式验证
                if($('.input').val()){
                    var pattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
                    if(!pattern.test($('.input').val())){
                        var emailDialog = {
                            container: 'body',
                            content: '请填写正确的邮箱格式',
                            iTitle: true,
                            title: '邮箱验证',
                        }
                        api.ui.dialog(emailDialog).open();
                        flag = true;
                    }else{
                        params['email'] = $('input[name=email]').val();
                    }
                }
                //表单非空校验
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
                if(flag){
                    return;
                }
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
            })
        }
    }
    api.ui.load(userConf);
}

function changePass() {
    $('#depart').empty();
    var userConf = {
        container: '#container',
        url: api.util.getUrl('html/user/changePassword.html'),
        async: false,
        preLoad: function (content) {
        },
        loaded: function () {
            $('#userInfoForm').css('margin-top','100px');
            $.ajax({
                url: api.util.getUrl('/apimanager/user/findBySession'),
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (result) {
                    $('input[name=id]').val(result.data.id);
                }
            })
            //cancel
            $('#userCancelBtn').on('click',function () {
                window.location.href = 'index.html';
            })
            //save
            $('#userSaveBtn').on('click',function () {
                var flag = false, params = {};
                params['id'] = $('input[name=id]').val();
                params['password'] = $('input[name=password]').val();
                var $passwordInput = $('input[name=password]');
                if(!$.trim($passwordInput.val())){
                    $passwordInput.css('border-color','red');
                    $passwordInput.on('blur',function () {
                        if($.trim($passwordInput.val())){
                            $passwordInput.css('border-color','');
                        }
                    })
                    return;
                }

                $.ajax({
                    url: api.util.getUrl('/apimanager/user/changePassword'),
                    data: params,
                    dataType: 'json',
                    async: false,
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
            })
        }
    }
    api.ui.load(userConf);
}

function loggerClick() {
    $('#depart').empty();
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/logger/logger.html'),
        async: false,
        loaded: function () {
            api.util.loadScript(api.util.getUrl("html/logger/js/logger.js"), function () {
                api.ui.chosenSelect(moduleCodeSelect);
                api.ui.chosenSelect(operateTypeSelect);
                api.ui.chosenSelect(operateUserSelect);
                api.ui.editTable(loggerTableOptions);
            });
            $('input[name=entityId]').on('blur', function () {
                var regPos = /^[0-9]*$/; // 浮点数
                if(!regPos.test($('input[name=entityId]').val())){
                    var options = {
                        content: '请输入业务编号！'
                    };
                    api.ui.dialog(options).open();
                    $('input[name=entityId]').val('');
                    $('input[name=entityId]').css('border-color','red');
                }else {
                    $('input[name=entityId]').css('border-color','');
                }
            });
        }
    }
    api.ui.load(conf);
}

function actionCountClick() {
    $('#depart').empty();
    // 基于准备好的dom，初始化echarts实例
    var containerConf = {
        container: '#container',
        url: 'html/action/actionCount.html',
        loaded: function () {
            $('#depart').parent('.container-fluid').css('display','none');
            $.ajax({
                url: api.util.getUrl('apimanager/action/countGroupByProject'),
                type: 'get',
                dataType: 'json',
                success: function (result) {
                    var datas = result.data;
                    var xData = [],series = [];
                    if(datas){
                        $.each(datas, function (index, data) {
                            xData.push(data.projectName);
                            series.push(data.actionCount);
                        })
                    }
                    console.log(xData);
                    console.log(series);
                    var myChart = echarts.init(document.getElementById('actionNum'));
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '项目接口数量统计',
                            x: '38%',
                            textStyle:{
                                fontSize: 25
                            }
                        },
                        legend: {
                            y: '6%',
                            x: '880px',
                            data: ['数量'],
                            color: 'rgb(42,170,227)',
                            textStyle:{
                                fontSize: 18
                            }
                        },
                        grid: {
                            x: '8%',
                            width:'80%',
                            top: '20%',
                            containLabel: true
                        },
                        xAxis: {
                            data: xData,
                            axisLabel:{
                                interval: 0,
                                rotate: -30
                            }
                        },
                        yAxis: {},
                        series: [{
                            name: '数量',
                            type: 'bar',
                            data: series,
                            label: {
                                show: true,
                                position: 'top',
                                color: 'black'
                            },
                            itemStyle: {
                                color: 'rgb(42,170,227)'
                            }
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })
        }
    }
    api.ui.load(containerConf);
}