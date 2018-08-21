var testGroupActionTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '接口名称', width: '40%'},
        {text: '序号', width: '10%'},
        {text: '创建人', width: '10%'},
        {text: '修改人', width: '10%'},
        {text: '操作', width: '20%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'actionName', type: 'input', inputDesc: '接口名称', required: true, readOnly: true},
        {name: 'sortCode', type: 'input', inputDesc: '接口名称', required: false},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: api.util.getUrl('apimanager/groupaction/update')},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/groupaction/delete')}
    ],
    headBtn: [
        {
            type: 'select', text: '添加接口', icon: 'glyphicon glyphicon-plus', fn: function (params) {
                var groupId = $('input[name=groupId]').val();
                var conf = {
                    container: '#container',
                    url: api.util.getUrl('html/action/actionSelect.html'),
                    async: false,
                    loaded: function () {
                        api.util.loadScript(api.util.getUrl("html/action/js/actionSelect.js") ,function () {
                            $('input[name=groupId]').val(groupId);
                            api.ui.editTable(actionSelectTableOptions);
                        });

                        $('#form').find('.btn-back').on('click', function () {
                            var conf = {
                                container: '#container',
                                url: api.util.getUrl('html/action/groupAction.html'),
                                async: false,
                                loaded: function () {
                                    $('input[name=groupId]').val(groupId);
                                    api.util.loadScript(api.util.getUrl("html/action/js/groupAction.js") ,function () {
                                        api.ui.editTable(testGroupActionTableOptions);
                                    });

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
                            }
                            api.ui.load(conf);
                        });
                    }
                }
                api.ui.load(conf);
            }
        }
    ],
    url: api.util.getUrl('apimanager/groupaction/findPage')
}