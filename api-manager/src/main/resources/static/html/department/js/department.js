var departmentOptions = {
    container:'#editTable',
    headers: [
        {text: '部门编号', width: '15%'},
        {text: '部门名称', width: '20%'},
        {text: '部门简述', width: '20%'},
        {text: '创建人', width: '10%'},
        {text: '修改人', width: '10%'},
        {text: '操作', width: '25%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '部门编号', required: false},
        {name: 'depName', type: 'input', inputDesc: '部门名称', required: true},
        {name: 'depDesc', type: 'input', inputDesc: '部门描述', required: false},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'enter', text: '进入', fn: function (param) {
                var parentId = param['id'];
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/project/project.html'),
                    preLoad: function (content) {
                        $("#depart").append("<li class=\"breadcrumb-item\"><a href=\"javasript:void(0)\" onclick=\"projectClick1()\">Project</a></li>");
                    },
                    loaded: function () {
                        var options = {
                            selector: '[name=depId]',
                            optionField: {value: 'id', text: 'depName'},
                            width: '70%',
                            async: false,
                            url: api.util.getUrl('apimanager/department/list')
                        }
                        api.ui.chosenSelect(options).val(parentId);
                        api.util.loadScript(api.util.getUrl("html/project/js/project.js"), function () {
                            api.ui.editTable(projectTableOptions);
                        });
                    }
                }
                api.ui.load(conf);
            }},
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/department/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/department/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/department/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加'
        }
    ],
    url: api.util.getUrl('apimanager/department/findPage')
};
