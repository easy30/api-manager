var testGroupTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '分组名称', width: '40%'},
        {text: '创建人', width: '15%'},
        {text: '修改人', width: '10%'},
        {text: '操作', width: '25%'}
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
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/groupAction.html'),
                    async: false,
                    loaded: function () {
                        $('input[name=groupId]').val(parentId);
                        api.util.loadScript(api.util.getUrl("html/action/js/groupAction.js") ,function () {
                            api.ui.editTable(testGroupActionTableOptions);
                        });
                    }
                }
                api.ui.load(conf);

                $('#form').find('.btn-back').on('click', function () {
                    var conf = {
                        container: '#container',
                        url: api.util.getUrl('html/action/testGroup.html'),
                        async: false,
                        loaded: function () {
                            api.util.loadScript(api.util.getUrl("html/action/js/testGroup.js") ,function () {
                                api.ui.editTable(testGroupTableOptions);
                            });
                        }
                    }
                    api.ui.load(conf);
                });
            }
        },
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/testgroup/update')},
        {type: 'save', text: '保存', url: api.util.getUrl('apimanager/testgroup/add')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/testgroup/delete')}
    ],
    headBtn: [
        {
            type: 'add', text: '添加分组'
        }
    ],
    url: api.util.getUrl('apimanager/testgroup/findPage')
}