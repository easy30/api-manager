var domainTableOptions = {
    container: '#editTable',
    headers: [
        {text: '服务编号', width: '15%'},
        {text: '所属环境', width: '10%'},
        {text: '服务地址', width: '30%'},
        {text: '序号', width: '10%'},
        {text: '创建人', width: '7%'},
        {text: '修改人', width: '7%'},
        {text: '操作', width: '25%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '服务编号', required: false},
        {
            name: 'envId', type: 'select', inputDesc: '所属环境', required: true, options: {
                optionField: {value: 'id', text: 'envName'},
                async: false,
                url: api.util.getUrl('apimanager/env/list')
            }
        },
        {name: 'domainName', type: 'input', inputDesc: '服务地址', required: true},
        {name: 'sortCode', type: 'input', inputDesc: '序号', required: false},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/domain/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/domain/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/domain/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加', fn: function (row) {
                row.find('select').val($('form select[name=envId]').val());
            }
        }
    ],
    url: api.util.getUrl('apimanager/domain/findPage')
};