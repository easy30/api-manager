var testHeadOptions = {
    container: '#testRequestHead',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '15%'},
        {text: '含义', width: '20%'},
        {text: '类型', width: '20%'},
        {text: '默认值', width: '20%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {name: 'desc', type: 'input'},
        {
            name: 'type', type: 'select', options: {
                width: '100%',
                height: '100%',
                blank: false,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('apimanager/meta/findMeta'),
                params: {metaId: 1}
            }
        },
        {name: 'defaultVal', type: 'input'}
    ],
    footBtn: [
        {
            type: 'add', text: '添加参数', fn: function (row) {
            }
        }
    ]
};

var testRequestOptions = {
    container: '#testRequestParam',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '15%'},
        {text: '含义', width: '20%'},
        {text: '类型', width: '20%'},
        {text: '规则', width: '20%'},
        {text: '默认值', width: '20%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {name: 'desc', type: 'input'},
        {
            name: 'type', type: 'select', options: {
                width: '100%',
                height: '100%',
                blank: false,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('apimanager/meta/findMeta'),
                params: {metaId: 1}
            }
        },
        {name: 'rule', type: 'input'},
        {name: 'defaultVal', type: 'input'}
    ],
    footBtn: [
        {
            type: 'add', text: '添加参数', fn: function (row) {
            }
        }
    ]
};

var requestTypeOptions = {
    selector: '[name=testRequestType]',
    width: '70%',
    params: {metaId: 2},
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};