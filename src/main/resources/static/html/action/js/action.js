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
                url: '/apimanager/module/list'
            }
        },
        {name: 'requestType', type:'select', inputDesc: '请求类型', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 2},
                url: '/apimanager/meta/findMeta'
            }
        },
        {name: 'status', type:'select', inputDesc: '状态', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 3},
                url: '/apimanager/meta/findMeta'
            }
        }
    ],
    rowButtons: [
        {type: 'view', text: '查看', icon: 'glyphicon glyphicon-eye-open', fn: function (param) {
                var conf = {
                    container: '#container',
                    url: 'html/action/actionTab.html',
                    content: "",
                    async: false,
                    preLoad: function () {},
                    loaded: function () {
                        $('#headButton button:last').css('display','none');
                        $('input[name=id]').val(param.id);
                        api.util.loadScript('html/action/js/actionTab.js', function () {
                            api.ui.tabs(actionTabConf);
                        });
                    }
                };
                api.ui.load(conf);
                var actionInfoFormOptions={
                    container:'#tabs'
                };
                var actionInfoFormObject =  api.ui.form(actionInfoFormOptions);
                $('#headButton button:first').on('click', function () {
                    actionInfoFormObject.enable();
                    $('#headButton button:first').css('display','none');
                    $('#headButton button:last').css('display','');
                });
                //保存
                $('#headButton button:last').on('click', function () {
                    $.ajax({
                        type: 'post',
                        url: 'apimanager/action/update',
                        dataType: 'json',
                        data : JSON.stringify(actionInfoFormObject.toJson()),
                        contentType : 'application/json;charset=utf-8',
                        success: function (data) {
                            //不跳转到列表
                            actionInfoFormObject.disable();
                            $('#headButton button:first').css('display','');
                            $('#headButton button:last').css('display','none');
                        }
                    });
                });
            }
        },
        {type: 'delete', text: '删除', url: '/apimanager/action/delete'}
    ],
    headBtn: [
        {
            type: 'add-jump', text: '添加', icon: 'glyphicon glyphicon-plus', fn: function (row) {
                var conf = {
                    container: '#container',
                    url: 'html/action/actionTab.html',
                    content: "",
                    async: false,
                    preLoad: function () {},
                    loaded: function () {
                        $('#headButton button:first').css('display','none');
                        api.util.loadScript('html/action/js/actionTab.js', function () {
                            api.ui.tabs(actionTabConf);
                        });
                    }
                };
                api.ui.load(conf);
                var actionInfoFormOptions={
                    container:'#tabs'
                };
                var actionInfoFormObject =  api.ui.form(actionInfoFormOptions);
                $('#headButton button:first').on('click', function () {
                    actionInfoFormObject.enable();
                    $('#headButton button:first').css('display','none');
                    $('#headButton button:last').css('display','');
                });
                //保存
                $('#headButton button:last').on('click', function () {
                    $.ajax({
                        type: 'post',
                        url: '/apimanager/action/add',
                        dataType: 'json',
                        data : JSON.stringify(actionInfoFormObject.toJson()),
                        contentType : 'application/json;charset=utf-8',
                        success: function (data) {
                            //跳转到action列表
                            var conf = {
                                container: '#container',
                                url: 'html/action/action.html',
                                content: "",
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
                                        url: '/apimanager/department/list',
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
                                        url: '/apimanager/project/list',
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
                                        url: '/apimanager/module/list'
                                    };
                                    var projectSelect = api.ui.chosenSelect(projectOptions);
                                    var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                    api.ui.chosenSelect(depOptions);
                                    api.util.loadScript("html/action/js/action.js" ,function () {
                                        api.ui.editTable(actionTableOptions);
                                    });
                                }
                            }
                            api.ui.load(conf);
                        }
                    });
                });
            }
        }
    ],
    url: '/apimanager/action/findPage'
};
