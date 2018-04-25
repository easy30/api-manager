Api.ns('api.browser');
api.browser.isIe = window.navigator.appName == 'Microsoft Internet Explorer';
api.browser.isHtml5 = api.browser.isIe ? (/MSIE (\d\d?)/.exec(window.navigator.appVersion)[1] > 8) : true;
api.link = document.createElement('link');
api.link.setAttribute('rel', 'stylesheet');
api.util = {
    pathRootName: '/',
    scriptsCache: {
        linkDom: api.link,
        scriptDom: document.createElement('script'),
        headDom: document.head || document.getElementsByTagName('head')[0]
    },
    getUrl: function (url) {
        var separate = url.charAt(0);
        if(separate == '/'){
            return url;
        } else {
            return this.pathRootName + url;
        }
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
        if (!this.scriptsCache[url]) {
            var script = this.scriptsCache.scriptDom.cloneNode();
            script.setAttribute('src', url);
            this.addEvent(script, 'load', fn);
            this.scriptsCache.headDom.appendChild(script);
            this.scriptsCache[url] = true;
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
    },
    generateId: function () {
        return 'api-id' + this.generateNumber();
    },
    generateNumber: function () {
        return Math.round(Math.random() * 9999999999);
    },
    buildMockTemplate: function (array) {
        var mockObject = {};
        for (var j = 0, len = array.length; j < len; j++) {
            var element = array[j];
            var columnType = element['type'];
            if (columnType == '0' || columnType == '1' || columnType == '2' || columnType == '3'
                || columnType == '6' || columnType == '7' || columnType == '8') {
                var name = element['name'], rule = element['rule'], ruleDesc = '', value = element['defaultVal'];
                if(rule){
                    if(columnType == '0'){
                        value = Mock.mock(rule);
                        mockObject[name] = value;
                    } else {
                        if(rule.indexOf(':') > 0){
                            var ruleItem = rule.split(':');
                            var expression = ruleItem[0];
                            var itemValue = ruleItem[1];
                            if(columnType == '1' || columnType == '6'){
                                value = Number(itemValue);
                            } else {
                                value = itemValue;
                            }
                            ruleDesc = '|' + expression;
                            mockObject[name + ruleDesc] = value;
                        } else {
                            mockObject[name] = value;
                        }
                    }
                } else {
                    if(columnType == '1' || columnType == '6'){
                        value = Number(value);
                    }
                    mockObject[name] = value;
                }
            } else if (columnType == '4') {
                var name = element['name'], children = element['child'], columnObject = this.buildMockTemplate(children);
                mockObject[name] = columnObject;
            } else if (columnType == '9') {
                var name = element['name'], children = element['child'], innerArray = [];
                for (var i = 0, len = children.length; i < len; i++) {
                    var childElement = children[i], innerChildren = childElement['child'];
                    var innerColumnObject = this.buildMockTemplate(innerChildren);
                    innerArray[i] = innerColumnObject;
                }
                mockObject[name] = innerArray;
            }
        }
        return mockObject;
    }
};