var testGroupActionTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '接口名称', width: '40%'},
        {text: '创建人', width: '15%'},
        {text: '修改人', width: '15%'},
        {text: '操作', width: '20%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'actionName', type: 'input', inputDesc: '接口名称', required: true},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/groupaction/delete')}
    ],
    headBtn: [
        {
            type: 'select', text: '添加接口', fn: function (params) {

            }
        }
    ],
    url: api.util.getUrl('apimanager/groupaction/findPage')
}