api.util.loadScripts([
    'plugins/mock/mock-min.js',
    'plugins/jsonview/js/jquery.jsonview.js',
    'common/js/jquery.md5.js',
    'common/ui/jquery.param.js',
    'common/ui/jquery.chosen.js',
    'common/ui/jquery.dialog.js',
    'common/ui/jquery.pager.js',
    'common/ui/jquery.form.js',
    'common/ui/jquery.tab.js',
    'common/ui/jquery.load.js',
    'common/ui/jquery.editTable.js',
    'common/ui/jquery.editSelect.js',
    'common/ui/jquery.progress.js',
    'common/ui/jquery.downMenu.js'
    ], function () {
    var headConf = {
        container: '#head',
        url: 'head.html',
        loaded: function () {
            api.util.loadScript(api.util.getUrl('common/js/head.js'), function () {
                var config = {
                    container: '#actionDownMenu',
                    title: '接口功能',
                    items: [
                        {
                            name: '接口管理', fn: function () {
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

                var config = {
                    container: '#extendModule',
                    title: '扩展功能',
                    items: [
                        {
                            name: '环境管理', fn: function () {
                                envClick();
                            }
                        },
                        {
                            name: '服务管理', fn: function () {
                                domainClick();
                            }
                        },{
                            name: '服务认证', fn: function () {
                                actionLoginClick();
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
})
