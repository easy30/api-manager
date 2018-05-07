var batchTestActionTableOptions = {
    container: '#editTable',
    headers: [
        {text: '编号', width: '5%'},
        {text: '接口地址', width: '20%'},
        {text: '所属模块', width: '15%'},
        {text: '请求类型', width: '15%'},
        {text: '状态', width: '10%'},
        {text: '操作', width: '10%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '接口编号', required: false},
        {name: 'requestUrl', type: 'input', inputDesc: '接口地址', required: true},
        {
            name: 'moduleId', type: 'select', inputDesc: '所属模块', required: true, options: {
                optionField: {value: 'id', text: 'moduleName'},
                url: api.util.getUrl('apimanager/module/list')
            }
        },
        {
            name: 'requestType', type: 'select', inputDesc: '请求类型', required: true, options: {
                optionField: {value: 'k', text: 'v'},
                params: {metaId: 2},
                cache: true,
                async: false,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }
        },
        {
            name: 'status', type: 'select', inputDesc: '状态', required: true, options: {
                optionField: {value: 'k', text: 'v'},
                params: {metaId: 3},
                cache: true,
                async: false,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }
        }
    ],
    rowButtons: [
        {type: 'choose', text: '选择', icon: 'glyphicon glyphicon-ok', fn: function (params) {
                var $selectAction = $('<button type="button" class="btn btn-success btn-sm" style="margin-left: 10px; margin-top: 10px; display: block;"></button>');
                $selectAction.text(params.requestUrl);
                $selectAction.attr('actionId', params.id);
                $selectAction.attr('requestUrl', params.requestUrl);
                $selectAction.on('dblclick', function () {
                    $(this).remove();
                });
                $('#selectedActionArea').append($selectAction);
            }}
    ],
    headBtn: [
        {
            type: 'allSelect', text: '全选', icon: 'glyphicon glyphicon-ok-circle', fn: function (row) {
                $('#selectedActionArea').empty();
                $('#editTable').find('.td-button').find('button').trigger('click');
            }
        }
    ],
    url: api.util.getUrl('apimanager/action/findPage')
}