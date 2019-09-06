var moduleTableOptions = {
    container: '#editTable',
    headers: [
        {text: '模块编号', width: '8%'},
        {text: '模块名称', width: '10%'},
        {text: '所属项目', width: '25%'},
        {text: '模块简述', width: '20%'},
        {text: '创建人', width: '7%'},
        {text: '修改人', width: '7%'},
        {text: '操作', width: '23%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '模块编号', required: false},
        {name: 'moduleName', type: 'input', inputDesc: '模块名称', required: true},
        {name: 'projectId', type:'select', inputDesc: '所属项目', required: true, options:{
                optionField: {value: 'id', text: 'projectName'},
                async: false,
                url: api.util.getUrl('apimanager/project/list')
            }},
        {name: 'moduleDesc', type: 'input', inputDesc: '模块描述', required: false},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'enter', text: '进入', fn: function (param) {
                var parentId = param.id, projectId = param.projectId;
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/action.html'),
                    async: false,
                    preLoad: function () {
                        var depId = $("select[name=depId]").val();
                        $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"actionClick1()\">Action</a></li>");
                        return depId;
                    },
                    loaded: function (depId) {
                        var depOptions = {
                            selector: '[name=depId]',
                            width: '60%',
                            async: false,
                            optionField: {value: 'id', text: 'depName'},
                            url: api.util.getUrl('apimanager/department/list'),
                            change: function (e) {
                                var params = {};
                                params['depId'] = e.target.value;
                                var projectOptions = {
                                    selector: '[name=projectId]',
                                    width: '70%',
                                    async: false,
                                    params: params,
                                    optionField: {value: 'id', text: 'projectName'},
                                    url: api.util.getUrl('apimanager/project/list'),
                                    change: function (e) {
                                        var params = {};
                                        params['projectId'] = e.target.value;
                                        var moduleOptions = {
                                            selector: '[name=moduleId]',
                                            width: '70%',
                                            params: params,
                                            async: false,
                                            optionField: {value: 'id', text: 'moduleName'},
                                            url: api.util.getUrl('apimanager/module/list')
                                        };
                                        var moduleSelect = api.ui.chosenSelect(moduleOptions);
                                        moduleSelect.val(parentId);
                                    }
                                };
                                var projectSelect = api.ui.chosenSelect(projectOptions);
                                projectSelect.val(projectId);
                                projectSelect.doChange();
                            }
                        };

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
                        api.util.loadScript(api.util.getUrl('html/action/js/action.js') ,function () {
                            api.ui.editTable(actionTableOptions);
                        });
                    }
                }
                api.ui.load(conf);
            }},
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/module/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/module/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/module/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加', fn: function (row) {
                row.find('select').val($('form select[name=projectId]').val());
            }
        }
    ],
    url: api.util.getUrl('apimanager/module/findPage')
};
