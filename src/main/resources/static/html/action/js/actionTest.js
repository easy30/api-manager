var testHeadOptions = {
    container: '#testRequestHead',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '15%'},
        {text: '类型', width: '20%'},
        {text: '备注', width: '20%'},
        {text: '值', width: '20%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {
            name: 'type', type: 'select', options: {
                width: '100%',
                height: '100%',
                selectedVal: 2,
                blank: false,
                cache: true,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('apimanager/meta/findMeta'),
                params: {metaId: 1}
            }
        },
        {name: 'desc', type: 'input'},
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
        {text: '类型', width: '20%'},
        {text: '备注', width: '20%'},
        {text: 'Mock规则', width: '20%'},
        {text: '值', width: '20%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {
            name: 'type', type: 'select', options: {
                width: '100%',
                height: '100%',
                blank: false,
                selectedVal: 2,
                cache: true,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('apimanager/meta/findMeta'),
                params: {metaId: 1}
            }
        },
        {name: 'desc', type: 'input'},
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

var envOptions = {
    selector: '[name=testEnv]',
    width: '30%',
    optionField: {value: 'id', text: 'envName'},
    url: api.util.getUrl('apimanager/env/list')
};

var requestTypeOptions = {
    selector: '[name=testRequestType]',
    width: '20%',
    params: {metaId: 2},
    blank: false,
    cache: true,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};

var domainEditOptions = {
    container: '#testDomainEdit',
    selectName: 'testDomainId',
    selectCodeField: 'id',
    selectValueField: 'domainName',
    width: '78%',
    url: api.util.getUrl('apimanager/domain/list'),
    change: function (param) {
    }
}