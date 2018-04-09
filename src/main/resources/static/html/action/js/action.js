var actionTableOptions = {
    container: '#editTable',
    headers: [
        {text: '接口编号', width: '10%'},
        {text: '接口名称', width: '10%'},
        {text: '所属模块', width: '15%'},
        {text: '请求类型', width: '10%'},
        {text: '状态', width: '10%'},
        {text: '操作', width: '15%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '接口编号', required: false},
        {name: 'actionName', type: 'input', inputDesc: '接口名称', required: true},
        {name: 'moduleId', type:'select', inputDesc: '所属模块', required: true, options:{
                optionField: {value: 'id', text: 'moduleName'},
                url: '/apimanager/module/list'
            }
        },
        {name: 'requestType', type:'select', inputDesc: '请求类型', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 2},
                url: '/apimanager/meta/findMeta'
            }
        },
        {name: 'status', type:'select', inputDesc: '状态', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 3},
                url: '/apimanager/meta/findMeta'
            }
        }
    ],
    rowButtons: [
        {type: 'enter', text: '查看', fn: function (param) {
            var conf = {
                container: '#container',
                url: 'html/action/actionTab.html',
                content: "",
                async: false,
                preLoad: function () {},
                loaded: function () {
                    $('#headButton button:last').css('display','none');
                    var conf = {
                        container: '#tabs',
                        tabs: [{
                            title: '基本信息',
                            width: '10%',
                            href: 'html/action/actionInfo1.html',
                            loaded: function () {
                                $.ajax({
                                    type: 'get',
                                    url: '/apimanager/action/findById',
                                    data:{id: param.id},
                                    dataType: "json",
                                    contentType: 'json',
                                    success: function(result){
                                        var data = result['data'];
                                        console.log(data);
                                        var actionInfoFormOptions={
                                            container:'#actionInfoForm'
                                        };
                                        var actionInfoFormObject =  api.ui.form(actionInfoFormOptions);
                                        console.log(actionInfoFormObject.toJson());
                                        actionInfoFormObject.giveVal({
                                            requestUrl: data['requestUrl'],
                                            actionName:data['actionName'],
                                            actionDesc: data['actionDesc']
                                        })
                                        actionInfoFormObject.disable();
                                    }
                                })
                            }
                        }, {
                            title: '接口参数',
                            width: '10%',
                            href: 'html/action/actionParam.html'
                        }]
                    };
                    var tabs = api.ui.tabs(conf);
                }
            }
            api.ui.load(conf);

            }
        },
        {type: 'delete', text: '删除', url: '/apimanager/action/delete'}
    ],
    headBtn: [
        {
            type: 'enter', text: '添加', fn: function (row) {
                var conf = {
                    container: '#container',
                    url: 'html/action/actionTab.html',
                    content: "",
                    async: false,
                    preLoad: function (content) {

                    },
                    loaded: function () {
                        $('#headButton button:first').css('display','none');

                    }
                }
                api.ui.load(conf);
            }
        }
    ],
    url: '/apimanager/action/findPage'
};
