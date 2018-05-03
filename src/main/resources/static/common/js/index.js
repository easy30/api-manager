var headConf = {
    container: '#head',
    url: 'head.html',
    loaded: function () {
        api.util.loadScript(api.util.getUrl('common/js/head.js'), function () {
            var config = {
                container: '#actionDownMenu',
                title: '接口管理',
                items: [
                    {
                        name: '接口列表', fn: function () {
                            actionClick();
                        }
                    },{
                        name: '接口测试', fn: function () {
                            actionTestClick();
                        }
                    },{
                        name: '批量测试', fn: function () {
                            batchTestClick();
                        }
                    }
                ]
            }
            api.ui.downMenu(config);
        });
    }
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