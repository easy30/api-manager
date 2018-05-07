var actionLoginTableOptions = {
    container:'#editTable',
    headers: [
        {text: '编号', width: '10%'},
        {text: '服务地址', width: '20%'},
        {text: '认证接口', width: '20%'},
        {text: '请求类型', width: '10%'},
        {text: '操作', width: '20%'}
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
        {type: 'openUpdate', text: '编辑', icon: 'glyphicon glyphicon-edit', fn: function (param) {
                var dialogOptions = {
                    container: 'body',
                    content: '',
                    iTitle: false,
                    title: '编辑认证接口',
                    width: '140%',
                    formCheck: true,
                    buttons:[
                        {
                            type: 'cancel', text: '取消'
                        },{
                            type: 'sure', text: '保存', fn: function () {
                                //非空校验
                                var actionLoginInfoForm = $('#actionLoginInfoForm');
                                var i = 0;
                                actionLoginInfoForm.find('input[name!=id],select,textarea[name!=extParam]').each(function(){
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
                                    container: actionLoginInfoForm
                                };
                                var formData = api.ui.form(options).toJson();
                                $.ajax({
                                    url: api.util.getUrl('apimanager/actionlogin/update'),
                                    type: 'GET',
                                    data: formData,
                                    dataType: 'json',
                                    success: function (result) {
                                        editDialog.close();
                                        $('#form').find('button').trigger('click');
                                    }
                                });
                            }
                        }
                    ],
                    opened: function (modalBody) {
                        var options = {
                            container: modalBody,
                            url: api.util.getUrl('html/actionlogin/actionLoginInfo.html'),
                            async: true,
                            preLoad: undefined,
                            loaded: function () {
                                $.ajax({
                                    url: api.util.getUrl('apimanager/actionlogin/findById'),
                                    type: 'GET',
                                    data: {id: param.id},
                                    dataType: 'json',
                                    success: function (result) {
                                        var data = result['data'];
                                        api.ui.chosenSelect({
                                            selector: '#actionLoginInfoForm [name=domainId]',
                                            optionField: {value: 'id', text: 'domainName'},
                                            width: '90%',
                                            async: false,
                                            url: api.util.getUrl('/apimanager/domain/list')
                                        });

                                        var requestTypeSelectOption = {
                                            selector: '[name=requestType]',
                                            width: '90%',
                                            params: {metaId: 2},
                                            cache: true,
                                            async: false,
                                            blank: false,
                                            optionField: {value: 'k', text: 'v'},
                                            url: api.util.getUrl('apimanager/meta/findMeta')
                                        };
                                        api.ui.chosenSelect(requestTypeSelectOption);

                                        var options = {
                                            container: $('#actionLoginInfoForm')
                                        };
                                        api.ui.form(options).giveVal(data);
                                    }
                                });
                            }
                        };
                        api.ui.load(options);
                    }
                };
                var editDialog = api.ui.dialog(dialogOptions).open();
            }},
        {type: 'doTest', text: '调用', icon: 'glyphicon glyphicon-play', fn: function (param) {
                $.ajax({
                    url: api.util.getUrl('apimanager/actionlogin/findById'),
                    type: 'GET',
                    data: {id: param.id},
                    dataType: 'json',
                    success: function (result) {
                        var data = result['data'];
                        var requestBody = {
                            'requestType': data.requestType,
                            'requestUrl': data.requestUrl,
                            'requestData': data.accountParam,
                            'domainName': data.domainName
                        };
                        $.ajax({
                            url: api.util.getUrl('apimanager/tester/send'),
                            type: 'post',
                            contentType: 'application/json;charset=UTF-8', //解决415问题
                            data: JSON.stringify(requestBody),//解决400问题
                            dataType: 'json',
                            success: function (result) {
                                if(result.code == 0){
                                    var options = {
                                        content: '调用处理成功！'
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
                });
            }},
        {type: 'delete', text: '删除', url: api.util.getUrl('apimanager/actionlogin/delete')}
    ],
    headBtn: [
        {
            type: 'openAdd', text: '添加', icon: 'glyphicon glyphicon-plus', fn: function (param) {
                var dialogOptions = {
                    container: 'body',
                    content: '',
                    iTitle: false,
                    title: '添加认证接口',
                    width: '140%',
                    formCheck: true,
                    buttons:[
                        {
                            type: 'cancel', text: '取消'
                        },{
                            type: 'sure', text: '保存', fn: function () {
                                //非空校验
                                var actionLoginInfoForm = $('#actionLoginInfoForm');
                                var i = 0;
                                actionLoginInfoForm.find('input[name!=id],select,textarea[name!=extParam]').each(function(){
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
                                    container: actionLoginInfoForm
                                };
                                var formData = api.ui.form(options).toJson();
                                $.ajax({
                                    url: api.util.getUrl('apimanager/actionlogin/add'),
                                    type: 'GET',
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
                            url: api.util.getUrl('html/actionlogin/actionLoginInfo.html'),
                            async: true,
                            preLoad: undefined,
                            loaded: function () {
                                api.ui.chosenSelect({
                                    selector: '#actionLoginInfoForm [name=domainId]',
                                    optionField: {value: 'id', text: 'domainName'},
                                    width: '90%',
                                    async: false,
                                    url: api.util.getUrl('/apimanager/domain/list')
                                });

                                var requestTypeSelectOption = {
                                    selector: '[name=requestType]',
                                    width: '90%',
                                    params: {metaId: 2},
                                    cache: true,
                                    async: false,
                                    blank: false,
                                    optionField: {value: 'k', text: 'v'},
                                    url: api.util.getUrl('apimanager/meta/findMeta')
                                };
                                api.ui.chosenSelect(requestTypeSelectOption);
                            }
                        };
                        api.ui.load(options);
                    }
                };
                var addDialog = api.ui.dialog(dialogOptions).open();
            }
        }
    ],
    url: api.util.getUrl('apimanager/actionlogin/findPage')
};
