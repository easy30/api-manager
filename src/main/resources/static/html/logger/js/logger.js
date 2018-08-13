var loggerTableOptions = {
    container: "#LoggerEditTable",
    headers: [
        {text: '编号', width: '10%'},
        {text: '业务编号', width: '10%'},
        {text: '日志模块', width: '10%'},
        {text: '操作类型', width: '10%'},
        {text: '操作人', width: '15%'},
        {text: '操作时间', width: '20%'},
        {text: '操作', width: '10%'}
    ],
    form: '#loggerForm',
    fields: [
        {name: 'id', type:'input', inputDesc:'编号',required:false},
        {name: 'entityId', type:'input', inputDesc:'业务ID',required:false},
        {name: 'moduleCode', type:'select', inputDesc: '日志模块', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 5},
                cache: true,
                async: false,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }},
        {name: 'operateType', type:'select', inputDesc: '操作类型', required: true, options:{
                optionField: {value: 'k', text: 'v'},
                params:{metaId: 6},
                cache: true,
                async: false,
                url: api.util.getUrl('apimanager/meta/findMeta')
            }},
        {name: 'operateUser', type:'select', inputDesc: '操作人', required: true, options:{
                selector: 'select[name=operateUser]',
                width: '90%',
                async: false,
                blank: false,
                optionField: {value: 'id', text: 'userName'},
                url: api.util.getUrl('apimanager/user/list')
            }},
        {name: 'operateTimeFormat', type:'input', inputDesc: '操作时间', required: false}
    ],
    rowButtons: [
        {type: 'enter', text: '查看', fn: function (param) {
            var moduleCodeSearch = $('select[name=moduleCode]').val(),
                operateTypeSearch = $('select[name=operateType]').val(),
                operateUserSearch = $('select[name=operateUser]').val();
            var loggerInfoConf = {
                container: '#container',
                url: api.util.getUrl('html/logger/loggerInfo.html'),
                async: false,
                loaded: function () {
                    api.util.loadScript(api.util.getUrl('html/logger/js/loggerInfo.js'),function () {
                        api.ui.chosenSelect(moduleCodeSelectOptions);
                        api.ui.chosenSelect(operateTypeSelectOptions);
                        api.ui.chosenSelect(operateUserSelectOptions);
                        $.ajax({
                            url: api.util.getUrl('apimanager/operatelog/findById'),
                            type: 'GET',
                            data: {id: param.id},
                            dataType: 'json',
                            success: function (result) {
                                if(result.code == '0') {
                                    var loggerForm = api.ui.form(loggerInfoFormOptions);
                                    loggerForm.giveVal(result.data);
                                    var date = new Date(result.data.operateTime);
                                    var dateVal = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
                                    $('input[name=operateTime]').val(dateVal);
                                    loggerForm.disable();
                                }
                            }
                        })
                    })
                    $('#returnLogger').on('click', function () {
                        $('#depart').parent('.container-fluid').css('display','none');
                        var conf = {
                            container: '#container',
                            url: api.util.getUrl('html/logger/logger.html'),
                            async: false,
                            loaded: function () {
                                api.util.loadScript(api.util.getUrl("html/logger/js/logger.js"), function () {
                                    api.ui.chosenSelect(moduleCodeSelect).val(moduleCodeSearch);
                                    api.ui.chosenSelect(operateUserSelect).val(operateUserSearch);
                                    api.ui.chosenSelect(operateTypeSelect).val(operateTypeSearch);
                                    api.ui.editTable(loggerTableOptions);
                                });
                            }
                        }
                        api.ui.load(conf);
                    })
                }
            }
            api.ui.load(loggerInfoConf);
        }}
    ],
    url: api.util.getUrl('apimanager/operatelog/findPage')
};

var moduleCodeSelect = {
    selector: 'select[name=moduleCode]',
    width: '50%',
    params: {metaId: 5},
    cache: true,
    async: false,
    blank: true,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
}

var operateUserSelect = {
    selector: 'select[name=operateUser]',
    width: '50%',
    async: false,
    blank: true,
    optionField: {value: 'id', text: 'userName'},
    url: api.util.getUrl('apimanager/user/list')
}

var operateTypeSelect = {
    selector: 'select[name=operateType]',
    width: '50%',
    params: {metaId: 6},
    cache: true,
    async: false,
    blank: true,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
}