var moduleTableOptions = {
    container: '#editTable',
    headers: [
        {text: '模块编号', width: '15%'},
        {text: '模块名称', width: '15%'},
        {text: '所属项目', width: '15%'},
        {text: '模块简述', width: '30%'},
        {text: '操作', width: '25%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '模块编号', required: false},
        {name: 'moduleName', type: 'input', inputDesc: '模块名称', required: true},
        {name: 'projectId', type:'select', inputDesc: '所属项目', required: true, options:{
                optionField: {value: 'id', text: 'projectName'},
                url: api.util.getUrl('apimanager/project/list')
            }},
        {name: 'moduleDesc', type: 'input', inputDesc: '模块描述', required: false}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/module/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/module/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/module/delete')},
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
                        optionField: {value: 'id', text: 'depName'},
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
                        width: '70%',
                        optionField: {value: 'id', text: 'projectName'},
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
                        width: '70%',
                        optionField: {value: 'id', text: 'moduleName'},
                        url: api.util.getUrl('apimanager/module/list')
                    };
                    var moduleSelect = api.ui.chosenSelect(moduleOptions);
                    var projectSelect = api.ui.chosenSelect(projectOptions);
                    var depSelect = api.ui.chosenSelect(depOptions);
                    depSelect.val(depId);
                    depSelect.doChange();
                    api.util.loadScript(api.util.getUrl('html/action/js/action.js') ,function () {
                        api.ui.editTable(actionTableOptions);
                    });
                }
            }
            api.ui.load(conf);
        }}
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
