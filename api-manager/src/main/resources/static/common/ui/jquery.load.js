(function ($) {
    function load(conf) {
        this.conf = conf = $.extend({}, load.defaults, conf);
        var jq = this.jq = ('string' == typeof conf.container) ? $(conf.container) : conf.container;
        if (conf.url) {
            this._open(conf.url);
        } else if (conf.content) {
            this._data(conf.content);
        }
    }

    load.prototype = {
        _open: function (url) {
            this._load(url);
        },
        _load: function (url) {
            var jq = this.jq, that = this, conf = this.conf, type = "GET";
            $.ajax({
                url: url,
                async: conf.async,
                type: type,
                dataType: "html",
                complete: function (jqXHR, status) {
                    var responseText = jqXHR.responseText
                    if (responseText) {
                        that._data(responseText);
                    }
                }
            });
        },
        _data: function (content) {
            var jq = this.jq, conf = this.conf;
            var params = conf.preLoad && conf.preLoad();
            jq.html(content);
            conf.loaded && conf.loaded(params);
        }
    };

    load.defaults = {
        container: '',
        url: undefined,
        content: "",
        async: true,
        preLoad: undefined,
        loaded: undefined
    };

    api.ui.load = function (conf) {
        return new load(conf);
    };
})(jQuery);