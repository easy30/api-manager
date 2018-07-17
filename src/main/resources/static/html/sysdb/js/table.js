var tableListTableOptions = {
    container: '#editTable',
    headers: [
        {text: '表名称', width: '20%'},
        {text: '表注释', width: '50%'},
        {text: '操作', width: '5%'}
    ],
    form: '#form',
    fields: [
        {name: 'tableName', type: 'input', inputDesc: '表名称', required: false},
        {name: 'tableComment', type: 'input', inputDesc: '表注释', required: false}
    ],
    rowButtons: [
        {type: 'makeObjectDesc', text: '生成对象备注', icon: 'glyphicon glyphicon-edit', fn: function (param) {
                var dbName = $('select[name=dbName]').val();
                $.ajax({
                    url: api.util.getUrl('apimanager/sysdb/makeObjectDesc'),
                    type: 'get',
                    data: {'tableName': param.tableName, 'dbName': dbName},
                    dataType: 'json',
                    success: function (result) {
                        if(result.code == 0){
                            var options = {
                                content: '处理成功！'
                            };
                            api.ui.dialog(options).open();
                        } else {
                            var options = {
                                content: result['msg']
                            };
                            api.ui.dialog(options).open();
                        }
                    }
                });
            }
        }
    ],
    url: api.util.getUrl('apimanager/sysdb/findTables')
}