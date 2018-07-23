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
    'common/ui/jquery.downMenu.js',
    'common/ui/jquery.autocomplete.js'
    ], function () {
    var headConf = {
        container: '#head',
        url: 'head.html',
        loaded: function () {
            api.util.loadScript(api.util.getUrl('common/js/head.js'), function () {
                $.ajax({
                    url: api.util.getUrl('/apimanager/user/findBySession'),
                    type: 'GET',
                    dataType: 'json',
                    success: function (result) {
                        $('#userName').text(result.data.userName);
                    }
                })
            });
        }
    }
    api.ui.load(headConf);

    var menuNavConf = {
        container: '#menu-nav',
        url: 'menu-nav.html',
        loaded: function () {
            $('.menu-list .inactive').click(function () {
                if ($(this).siblings('ul').css('display') != 'none') {
                    $(this).addClass('inactives');
                    $(this).siblings('ul').slideUp(100);
                } else {
                    $(this).removeClass('inactives');
                    $(this).siblings('ul').slideDown(100).children('li');
                }
            })
        }
    }
    api.ui.load(menuNavConf);

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
