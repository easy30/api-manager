var envTableOptions = {
    container:'#editTable',
    headers: [
        {text: '环境编号', width: '15%'},
        {text: '环境名称', width: '20%'},
        {text: '序号', width: '10%'},
        {text: '创建人', width: '7%'},
        {text: '修改人', width: '7%'},
        {text: '操作', width: '25%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '环境编号', required: false},
        {name: 'envName', type: 'input', inputDesc: '环境名称', required: true},
        {name: 'sortCode', type: 'input', inputDesc: '序号', required: false},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/env/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/env/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/env/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加'
        }
    ],
    url: api.util.getUrl('apimanager/env/findPage')
};
