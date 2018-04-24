var domainTableOptions = {
    container: '#editTable',
    headers: [
        {text: '域名编号', width: '15%'},
        {text: '所属环境', width: '20%'},
        {text: '域名地址', width: '30%'},
        {text: '序号', width: '10%'},
        {text: '操作', width: '25%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '域名编号', required: false},
        {
            name: 'envId', type: 'select', inputDesc: '所属环境', required: true, options: {
                optionField: {value: 'id', text: 'envName'},
                url: api.util.getUrl('apimanager/env/list')
            }
        },
        {name: 'domainName', type: 'input', inputDesc: '域名地址', required: true},
        {name: 'sortCode', type: 'input', inputDesc: '序号', required: false}
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