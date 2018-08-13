var typeSelectOption = {
    selector: '[name=requestType]',
    width: '95%',
    params: {metaId: 2},
    cache: true,
    async: false,
    blank: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var statusSelectOption = {
    selector: '[name=status]',
    width: '95%',
    params: {metaId: 3},
    cache: true,
    async: false,
    blank: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var actionLevelSelectOption = {
    selector: '[name=actionLevel]',
    width: '90%',
    params: {metaId: 7},
    cache: true,
    async: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var moduleOptions = {
    selector: '[name=moduleId]',
    optionField: {value: 'id', text: 'moduleName'},
    width: '90%',
    async: false,
    url: api.util.getUrl('/apimanager/module/list')
};
var actionInfoFormOptions={
    container:'#actionInfoForm'
};
var domainSelectOptions = {
    selector: '[name=domainId]',
    optionField: {value: 'id', text: 'domainName'},
    width: '88.5%',
    async: false,
    params: {envId: 1},
    url: api.util.getUrl('/apimanager/domain/list')
}