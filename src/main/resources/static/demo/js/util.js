Api.ns('api.browser');
api.browser.isIe = window.navigator.appName == 'Microsoft Internet Explorer';
api.browser.isHtml5 = api.browser.isIe ? (/MSIE (\d\d?)/.exec(window.navigator.appVersion)[1] > 8) : true;
api.link = document.createElement('link');
api.link.setAttribute('rel', 'stylesheet');
api.util = {
    scriptsCatche: {
        linkDom: api.link,
        scriptDom: document.createElement('script'),
        headDom: document.head || document.getElementsByTagName('head')[0]
    },
    addEvent: function (elem, e, fn) {
        if (e == 'load' && !api.browser.isHtml5) {
            elem.onreadystatechange = function () {
                if ('complete' == elem.readyState || 'loaded' == elem.readyState) {
                    fn();
                }
            };
        } else {
            if (elem.attachEvent) {
                elem.attachEvent("on" + e, fn);
            } else if (elem.addEventListener) {
                elem.addEventListener(e, fn, false);
            }
        }
    },
    loadScript: function (url, fn) {
        fn = fn || $.noop;
        if (!this.scriptsCatche[url]) {
            var script = this.scriptsCatche.scriptDom.cloneNode();
            script.setAttribute('src', url);
            this.addEvent(script, 'load', fn);
            this.scriptsCatche.headDom.appendChild(script);
            this.scriptsCatche[url] = true;
        } else {
            fn && fn();
        }
    },
    loadScripts: function (urls, fn) {
        var i = 0, len = urls.length, url;
        if (len < 1) return;

        function load() {
            if (i == len) {
                fn && fn();
                return;
            }
            url = urls[i++];
            api.util.loadScript(url, load);
        }

        load();
    }
};
api.link = undefined;