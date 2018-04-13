var headConf = {
    container: '#head',
    url: 'head.html'
}
api.ui.load(headConf);

var containerConf = {
    container: '#container',
    url: 'html/department/department.html',
    loaded: function () {
        api.util.loadScript(api.util.getUrl('html/department/js/department.js'), function () {
            api.ui.editTable(departmentOptions);
        });
    }
}
api.ui.load(containerConf);