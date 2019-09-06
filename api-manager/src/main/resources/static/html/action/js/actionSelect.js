var actionSelectTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '接口名称', width: '30%'},
        {text: '所属模块', width: '30%'},
        {text: '创建人', width: '20%'},
        {text: '操作', width: '10%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'actionName', type: 'input', inputDesc: '接口名称', required: true},
        {name: 'moduleId', type:'select', inputDesc: '所属模块', required: true, options:{
                optionField: {value: 'id', text: 'moduleName'},
                async: false,
                url: api.util.getUrl('apimanager/module/list')
            }
        },
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'select', text: '选择', icon: 'glyphicon glyphicon-check', fn: function (param) {
                var actionId = param['id'];
                $.ajax({
                    type: 'get',
                    url: api.util.getUrl('/apimanager/groupaction/add'),
                    data: {'actionId': actionId, 'groupId': $('input[name=groupId]').val()},
                    dataType: "json",
                    success: function (result) {
                        var status = result.code;
                        if(status == '0'){
                            var errorOptions = {
                                content: "选择处理成功"
                            }
                            api.ui.dialog(errorOptions).open();
                        } else {
                            var successOptions = {
                                content: "选择处理失败！"
                            }
                            api.ui.dialog(successOptions).open();
                        }
                    }
                });
            }
        }
    ],
    url: api.util.getUrl('apimanager/action/findPage')
}