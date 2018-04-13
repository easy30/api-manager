function departmentClick(){
    $('#depart').parent('.container').css('display','');
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
    $('#depart').parent('.container').css('display','');
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
    $('#depart').parent('.container').css('display','');
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
            var depOptions = {
                selector: '[name=depId]',
                optionField: {value: 'id', text: 'depName'},
                width: '70%',
                url: api.util.getUrl('apimanager/department/list'),
                change: function (e) {
                    projectSelect.clear();
                    var param = {};
                    param['depId'] = e.target.value;
                    projectSelect.load(param);
                }
            };
            api.ui.chosenSelect(depOptions);
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
                url: api.util.getUrl('apimanager/department/list'),
                change: function (e) {
                    projectSelect.clear();
                    var params = {};
                    params['depId'] = e.target.value;
                    projectSelect.load(params);
                    projectSelect.val(param.projectId);
                }
            };
            var projectOptions = {
                selector: '[name=projectId]',
                optionField: {value: 'id', text: 'projectName'},
                width: '70%',
                url: api.util.getUrl('apimanager/project/list')
            };
            var projectSelect = api.ui.chosenSelect(projectOptions);
            api.ui.chosenSelect(depOptions).val(param.depId);
            api.util.loadScript(api.util.getUrl("html/module/js/module.js"), function () {
                api.ui.editTable(moduleTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function actionClick(){
    $('#depart').parent('.container').css('display','');
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
                change: function (e) {
                    projectSelect.clear();
                    var params = {};
                    params['depId'] = e.target.value;
                    projectSelect.load(params);
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
            api.ui.chosenSelect(depOptions).val(depId);
            api.util.loadScript(api.util.getUrl("html/action/js/action.js") ,function () {
                api.ui.editTable(actionTableOptions);
            });
        }
    }
    api.ui.load(conf);
}
function actionTestClick() {
    $('#depart').parent('.container').css('display','none');
    var conf = {
        container: '#container',
        url: api.util.getUrl('html/action/actionTest.html'),
        async: false,
        loaded: function () {
            var testHeadParam, testRequestParam;
            api.util.loadScript(api.util.getUrl('html/action/js/actionTest.js'), function () {
                var requestTypeSelect = api.ui.chosenSelect(requestTypeOptions);
                testHeadParam = api.ui.param(testHeadOptions);
                testRequestParam = api.ui.param(testRequestOptions);
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