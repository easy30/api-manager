var typeSelectOption = {
    selector: '[name=requestType]',
    width: '100%',
    blank: false,
    params: {metaId: 2},
    cache: true,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var statusSelectOption = {
    selector: '[name=status]',
    width: '100%',
    blank: false,
    params: {metaId: 3},
    cache: true,
    optionField: {value: 'k', text: 'v'},
    url: api.util.getUrl('apimanager/meta/findMeta')
};
var moduleOptions = {
    selector: '[name=moduleId]',
    optionField: {value: 'id', text: 'moduleName'},
    width: '100%',
    url: api.util.getUrl('/apimanager/module/list')
};
var actionInfoFormOptions={
    container:'#actionInfoForm'
};