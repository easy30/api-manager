var actionLoginTableOptions = {
    container:'#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '服务地址', width: '20%'},
        {text: '认证接口', width: '20%'},
        {text: '请求类型', width: '10%'},
        {text: '操作', width: '15%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'domainId', type: 'select', inputDesc: '服务地址', required: false, options:{
                optionField: {value: 'id', text: 'domainName'},
                async: false,
                url: api.util.getUrl('apimanager/domain/list')
            }},
        {name: 'requestUrl', type: 'input', inputDesc: '认证接口', required: false},
        {name: 'requestType', type: 'select', inputDesc: '请求类型', required: false, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 2},
                cache: true,
                async: false,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/actionlogin/update')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/actionlogin/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加'
        }
    ],
    url: api.util.getUrl('apimanager/actionlogin/findPage')
};
var actionLoginDomainSelectOptions = {
    selector: '[name=domainId]',
    optionField: {value: 'id', text: 'domainName'},
    width: '60%',
    async: false,
    url: api.util.getUrl('/apimanager/domain/list')
}
