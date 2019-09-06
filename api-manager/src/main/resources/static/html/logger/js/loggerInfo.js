var loggerInfoFormOptions = {
    container: '#loggerInfoForm'
}
var moduleCodeSelectOptions = {
    selector: 'select[name=moduleCode]',
    width: '90%',
    params: {metaId: 5},
    cache: true,
    async: false,
    blank: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
}
var operateTypeSelectOptions = {
    selector: 'select[name=operateType]',
    width: '75%',
    params: {metaId: 6},
    cache: true,
    async: false,
    blank: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
}
var operateUserSelectOptions = {
    selector: 'select[name=operateUser]',
    width: '90%',
    async: false,
    blank: false,
    optionField: {value: 'id', text: 'userName'},
    url: api.util.getUrl('apimanager/user/list')
}