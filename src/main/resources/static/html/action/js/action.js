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
                preLoad: function () {

                },
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
                            $('input[name=actionName]').val(data['actionName']);
                            $('input[name=actionName]').attr('disabled','disabled');
                            $('input[name=actionDesc]').val(data['actionDesc']);
                            $('input[name=actionDesc]').attr('disabled','disabled');
                            $('select[name=requestType]').val(data['requestType']);
                            $('select[name=requestType]').attr('disabled','disabled');
                        }
                    })
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
                        alert('之前！')
                    },
                    loaded: function () {

                    }
                }
                api.ui.load(conf);
            }
        }
    ],
    url: '/apimanager/action/findPage'
};
