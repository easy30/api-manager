var objectFieldDescTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '5%'},
        {text: '名称', width: '20%'},
        {text: '创建人', width: '10%'},
        {text: '修改人', width: '10%'},
        {text: '操作', width: '10%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '编号', required: false},
        {name: 'classWholeName', type: 'input', inputDesc: '名称', required: false},
        {name: 'createUserName', type: 'input', inputDesc: '创建人', required: false, readOnly: true},
        {name: 'updateUserName', type: 'input', inputDesc: '修改人', required: false, readOnly: true}
    ],
    rowButtons: [
        {type: 'openUpdate', text: '编辑', icon: 'glyphicon glyphicon-edit', fn: function (param) {
                var dialogOptions = {
                    container: 'body',
                    content: '',
                    iTitle: false,
                    title: '编辑备注对象',
                    width: '160%',
                    formCheck: true,
                    buttons:[
                        {
                            type: 'cancel', text: '取消'
                        },{
                            type: 'sure', text: '保存', fn: function () {
                                //非空校验
                                var objectFieldDescInfoForm = $('#objectFieldDescInfoForm');
                                var i = 0;
                                objectFieldDescInfoForm.find('input[name=classWholeName],textarea[name=fieldDescValue]').each(function(){
                                    var value = $.trim($(this).val());
                                    if(!value){
                                        i = 1;
                                        $(this).css('border-color','red');
                                        $(this).on('blur',function () {
                                            if($.trim($(this).val())){
                                                $(this).css('border-color','');
                                            }
                                        })
                                        return true;
                                    }
                                });

                                if(i == 1){
                                    return;
                                }

                                var options = {
                                    container: objectFieldDescInfoForm
                                };
                                var formData = api.ui.form(options).toJson();
                                $.ajax({
                                    url: api.util.getUrl('apimanager/object/field/update'),
                                    type: 'POST',
                                    data: formData,
                                    dataType: 'json',
                                    success: function (result) {
                                        addDialog.close();
                                        $('#form').find('button').trigger('click');
                                    }
                                });
                            }
                        }
                    ],
                    opened: function (modalBody) {
                        var options = {
                            container: modalBody,
                            url: api.util.getUrl('html/action/objectFieldDescInfo.html'),
                            async: false,
                            loaded: function () {
                                $.ajax({
                                    url: api.util.getUrl('apimanager/object/field/findById'),
                                    type: 'GET',
                                    data: {id: param.id},
                                    dataType: 'json',
                                    success: function (result) {
                                        var data = result['data'];
                                        var fieldDescValueObj = JSON.parse(data['fieldDescValue']);
                                        var fieldDescValueFormat = JSON.stringify(fieldDescValueObj, null, 4);
                                        $('#objectFieldDescInfoForm').find('textarea[name=fieldDescValue]').val(fieldDescValueFormat);
                                        $('#objectFieldDescInfoForm').find('input[name=classWholeName]').val(data['classWholeName']);
                                        $('#objectFieldDescInfoForm').find('input[name=id]').val(data['id']);
                                    }
                                });
                            }
                        };
                        api.ui.load(options);
                    }
                };
                var addDialog = api.ui.dialog(dialogOptions).open();
            }
        },
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/object/field/delete')}
    ],
    headBtn: [
        {
            type: 'openAdd', text: '添加', icon: 'glyphicon glyphicon-plus', fn: function (param) {
                var dialogOptions = {
                    container: 'body',
                    content: '',
                    iTitle: false,
                    title: '添加备注对象',
                    width: '160%',
                    formCheck: true,
                    buttons:[
                        {
                            type: 'cancel', text: '取消'
                        },{
                            type: 'sure', text: '保存', fn: function () {
                                //非空校验
                                var objectFieldDescInfoForm = $('#objectFieldDescInfoForm');
                                var i = 0;
                                objectFieldDescInfoForm.find('input[name=classWholeName],textarea[name=fieldDescValue]').each(function(){
                                    var value = $.trim($(this).val());
                                    if(!value){
                                        i = 1;
                                        $(this).css('border-color','red');
                                        $(this).on('blur',function () {
                                            if($.trim($(this).val())){
                                                $(this).css('border-color','');
                                            }
                                        })
                                        return true;
                                    }
                                });

                                if(i == 1){
                                    return;
                                }

                                var options = {
                                    container: objectFieldDescInfoForm
                                };
                                var formData = api.ui.form(options).toJson();
                                $.ajax({
                                    url: api.util.getUrl('apimanager/object/field/add'),
                                    type: 'POST',
                                    data: formData,
                                    dataType: 'json',
                                    success: function (result) {
                                        addDialog.close();
                                        $('#form').find('button').trigger('click');
                                    }
                                });
                            }
                        }
                    ],
                    opened: function (modalBody) {
                        var options = {
                            container: modalBody,
                            url: api.util.getUrl('html/action/objectFieldDescInfo.html'),
                            async: true,
                            preLoad: undefined,
                            loaded: function () {
                            }
                        };
                        api.ui.load(options);
                    }
                };
                var addDialog = api.ui.dialog(dialogOptions).open();
            }
        }
    ],
    url: api.util.getUrl('apimanager/object/field/findPage')
}