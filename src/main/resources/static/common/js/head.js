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
