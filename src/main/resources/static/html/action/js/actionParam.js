var headOptions = {
    container: '#requestHead',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '20%'},
        {text: '类型', width: '15%'},
        {text: '备注', width: '30%'},
        {text: '样值', width: '30%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {
            name: 'type', type: 'select',
            options: {
                width: '100%',
                height: '100%',
                blank: false,
                async: false,
                optionField: {value: 'k', text: 'v'},
                selectedVal: 2,
                cache: true,
                url: api.util.getUrl('/apimanager/meta/findMeta'),
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
        },
        {
            type: 'clear', text: '清空参数', fn: function (row) {
            }
        }
    ]
};
var requestOptions = {
    container: '#requestParam',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '20%'},
        {text: '类型', width: '15%'},
        {text: '必填', width: '10%'},
        {text: '备注', width: '20%'},
        {text: 'Mock规则', width: '15%'},
        {text: '样值', width: '15%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {
            name: 'type', type: 'select',
            options: {
                width: '100%',
                height: '100%',
                blank: false,
                async: false,
                selectedVal: 2,
                cache: true,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('/apimanager/meta/findMeta'),
                params: {metaId: 1}
            }
        },
        {
            name: 'required', type: 'select', options: {
                width: '100%',
                height: '100%',
                blank: false,
                async: false,
                selectedVal: 1,
                cache: true,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('/apimanager/meta/findMeta'),
                params: {metaId: 4}
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
        },
        {
            type: 'import', text: '导入参数', fn: function (row) {
            }
        },
        {
            type: 'importDesc', text: '导入备注', className: 'importDescBtn', fn: function (row) {
            }
        },
        {
            type: 'clear', text: '清空参数', fn: function (row) {
            }
        }
    ]
};
var responseOptions = {
    container: '#responseParam',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '20%'},
        {text: '类型', width: '15%'},
        {text: '备注', width: '20%'},
        {text: 'Mock规则', width: '20%'},
        {text: '样值', width: '20%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {
            name: 'type', type: 'select', options: {
                width: '100%',
                height: '100%',
                blank: false,
                async: false,
                selectedVal: 2,
                cache: true,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('/apimanager/meta/findMeta'),
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
        },
        {
            type: 'import', text: '导入参数', fn: function (row) {
            }
        },
        {
            type: 'importDesc', text: '导入备注', className: 'importDescBtn', fn: function (row) {
            }
        },
        {
            type: 'clear', text: '清空参数', fn: function (row) {
            }
        }
    ]
};
var responseFailOptions = {
    container: '#responseFailParam',
    headers: [
        {text: '操作', width: '5%'},
        {text: '名称', width: '20%'},
        {text: '类型', width: '15%'},
        {text: '备注', width: '30%'},
        {text: '样值', width: '30%'}
    ],
    fields: [
        {name: 'name', type: 'input'},
        {
            name: 'type', type: 'select', options: {
                width: '100%',
                height: '100%',
                blank: false,
                async: false,
                selectedVal: 2,
                cache: true,
                optionField: {value: 'k', text: 'v'},
                url: api.util.getUrl('/apimanager/meta/findMeta'),
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
        },
        {
            type: 'import', text: '导入参数', fn: function (row) {
            }
        },
        {
            type: 'clear', text: '清空参数', fn: function (row) {
            }
        }
    ]
};
