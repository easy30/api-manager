var typeSelectOption = {
    selector: '[name=requestType]',
    width: '95%',
    params: {metaId: 2},
    cache: true,
    blank: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var statusSelectOption = {
    selector: '[name=status]',
    width: '95%',
    params: {metaId: 3},
    cache: true,
    blank: false,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var moduleOptions = {
    selector: '[name=moduleId]',
    optionField: {value: 'id', text: 'moduleName'},
    width: '90%',
    url: api.util.getUrl('/apimanager/module/list')
};
var actionInfoFormOptions={
    container:'#actionInfoForm'
};
var domainSelectOptions = {
    selector: '[name=domainId]',
    optionField: {value: 'id', text: 'domainName'},
    width: '88.5%',
    url: api.util.getUrl('/apimanager/domain/list')
}