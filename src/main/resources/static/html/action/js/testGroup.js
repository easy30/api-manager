var testGroupTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '分组名称', width: '40%'},
        {text: '创建人', width: '10%'},
        {text: '修改人', width: '10%'},
        {text: '操作', width: '30%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'groupName', type: 'input', inputDesc: '分组名称', required: true},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'enter', text: '进入', fn: function (param) {
                var parentId = param['id'];
                alert(parentId);
            }
        },
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/testgroup/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/testgroup/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/testgroup/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加'
        }
    ],
    url: api.util.getUrl('apimanager/testgroup/findPage')
}