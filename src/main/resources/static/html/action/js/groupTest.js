var groupTestTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '5%'},
        {text: '分组名称', width: '20%'},
        {text: '操作', width: '10%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'groupName', type: 'input', inputDesc: '分组名称', required: true}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/testgroup/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/testgroup/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/testgroup/delete')},
        {type: 'enter', text: '详细', fn: function (param) {
                var parentId = param['id'];
                alert(parentId);
            }
        }
    ],
    headBtn: [
        {
            type: 'add', text: '添加'
        }
    ],
    url: api.util.getUrl('apimanager/testgroup/findPage')
}