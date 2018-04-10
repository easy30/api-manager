var actionTabConf = {
    container: '#actionInfoForm',
    tabs: [{
        title: '基本信息',
        width: '10%',
        href: 'html/action/actionInfo1.html',
        loaded: function () {
            $.ajax({
                type: 'get',
                url: '/apimanager/action/findById',
                data:{id: $('input[name=id]').val()},
                dataType: "json",
                contentType: 'json',
                success: function(result){
                    var actionInfoFormObject;
                    var data = result['data'];
                    var moduleOptions = {
                        selector: '[name=moduleId]',
                        optionField: {value: 'id', text: 'moduleName'},
                        width: '60%',
                        url: '/apimanager/module/list'
                    };
                    api.ui.chosenSelect(moduleOptions);
                    if(data){
                        var actionInfoFormOptions={
                            container:'#actionInfoForm'
                        };
                        actionInfoFormObject =  api.ui.form(actionInfoFormOptions);
                        actionInfoFormObject.giveVal({
                            id: data['id'],
                            requestUrl: data['requestUrl'],
                            actionName:data['actionName'],
                            moduleId:data['moduleId'],
                            requestType:data['requestType'],
                            status:data['status'],
                            actionDesc: data['actionDesc']
                        });
                        actionInfoFormObject.disable();
                    }
                }
            });
        }
    }, {
        title: '接口参数',
        width: '10%',
        href: 'html/action/actionParam.html',
        loaded: function () {
            $.ajax({
                type: 'get',
                url: '/apimanager/action/findById',
                data:{id: $('input[name=id]').val()},
                dataType: "json",
                contentType: 'json',
                success: function(result){
                    var data = result['data'];
                    if(data){
                        api.util.loadScript('html/action/js/actionParam.js', function () {
                            headOptions.data=JSON.parse(data['requestHeadDefinition']);
                            requestOptions.data=JSON.parse(data['requestDefinition']);
                            responseOptions.data=JSON.parse(data['responseDefinition']);
                            var headParam = api.ui.param(headOptions);
                            var requestParam = api.ui.param(requestOptions);
                            var responseParam = api.ui.param(responseOptions);
                            headParam.disable();
                            requestParam.disable();
                            responseParam.disable();
                        });
                    }
                }
            });
        }
    }]
};


