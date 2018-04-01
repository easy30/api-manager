(function ($) {
    var itemD = $('<li class="nav-item"><a class="nav-link" data-toggle="tab"></a></li>'),
        paneD = $('<div class="tab-pane fade" style="padding:15px;">'),
        closeD = $('<button type="button" class="close" aria-hidden="true" style="padding-right:3px;" onmouseover="this.style.opacity=0.2" onmouseout="this.style.opacity=0">&times;</button>');

    function tabs(conf) {
        this.confs = {};
        conf = $.extend({}, tabs.defaults, conf);

        this.nav = $('<ul class="nav nav-tabs"/>');
        this.content = $('<div class="tab-content"/>');

        if ('string' == typeof conf.container) {
            conf.container = $(conf.container);
        }
        conf.container.append(this.nav).append(this.content);

        if (conf.tabs) {
            var i = 0, len = conf.tabs.length;
            for (i; i < len; i++) {
                conf.tabs[i].index = i;
                conf.tabs[i].activeIndex = conf.activeIndex;
                this.add(conf.tabs[i]);
            }
        }
    };

    tabs.prototype = {
        add: function (tabConf) {
            tabConf = $.extend({}, tabDefaults, tabConf);
            var title = tabConf.title, that = this;
            this.confs[title] = $.extend({}, this.confs[title], tabConf);
            // 已经存在的tab，在调用添加的时候直接open
            if (this.nav.find('a:contains(' + title + ')').length) {
                this.open(title);
                return;
            }

            var id = api.util.generateId(), item = itemD.clone(), tabs = this, a = item.find('>a').attr('href', '#' + id).text(title), pane = paneD.clone().attr('id', id);
            if (tabConf.closable) {
                a.css('padding-right', '16px');
                item.prepend(closeD.clone().click(function () {
                    tabs.close(title);
                }));
            }

            // 创建tab和panel
            this.nav.append(item);
            this.content.append(pane);
            tabConf.panel = pane;
            this.confs[title] = tabConf;

            item.click(function (event) {
                // 防止冒泡
                event.stopPropagation();
                that.load(tabConf);
            });
            //判断是否需要延迟加载标签
            if (tabConf.lazy) {
                //活动tab需要加载内容
                if (tabConf.activeIndex == tabConf.index) {
                    item.trigger("click");
                }
            } else {
                item.trigger("click");
            }

            if (tabConf.disabled) {
                this.disable(title);
            }
        },
        close: function (title) {
            var a = this.nav.find('a:contains(' + title + ')'), id = a.attr('href'), p = a.parent();
            if (a.parent().is('.active')) {
                var next = p.next(), showTitle = next.length ? next.find('a').text() : p.prev().find('a').text();
                if (showTitle) this.show(showTitle);
            }
            this.content.find('>div' + id).remove();
            p.remove();
        },
        show: function (title) {
            this.nav.find('a:contains(' + title + ')').tab('show');
        },
        hide: function (title) {
            this.nav.find('a:contains(' + title + ')').parent().css('display', 'none');
        },
        display: function (title) {
            this.nav.find('a:contains(' + title + ')').parent().css('display', '');
        },
        contains: function (title) {
            return this.nav.find('a:contains(' + title + ')').length;
        },
        open: function (title, reload) {
            if (reload) {
                this.confs[title].panel.loaded = false;
            }
            this.load(this.confs[title]);
        },
        load: function (conf) {
            var title = conf.title, href = conf.href, pane = conf.panel, content = conf.content;
            this.show(title);
            //已经load内容的不再加载，只展示内容
            if (!pane.loaded) {
                api.ui.load({
                    container: pane,
                    url: href,
                    content: content,
                    loaded: function (pane) {
                        conf.loaded && conf.loaded(pane);
                    }
                });
                pane.loaded = true;
            }
        },
        tab: function (title) {
            var jq = this.content.find('>div' + this.nav.find('a:contains(' + title + ')').attr('href'));
            jq.conf = this.confs[title];
            return jq;
        },
        activeTab: function () {
            var a = this.nav.find('>li.active a'), jq = this.content.find('>div' + a.attr('href'));
            jq.conf = this.confs[a.text()];
            return jq;
        },
        destroy: function () {
            this.nav.remove();
            this.content.remove();
        },
        disable: function (title) {
            this.nav.find('a:contains(' + title + ')').parent().addClass('disabled').on('click.disabled', function () {
                return false;
            });
        },
        enable: function (title) {
            this.nav.find('a:contains(' + title + ')').parent().removeClass('disabled').unbind('click.disabled');
        }
    };

    var tabDefaults = {
        title: '',
        content: '',
        href: '',
        lazy: true,
        loaded: '',
        error: '',
        disabled: false,
        closable: false
    };

    tabs.defaults = {
        container: '',
        activeIndex: 0,
        tabs: []
    };

    api.ui.tabs = function (conf) {
        return new tabs(conf);
    };
})(jQuery);