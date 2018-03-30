;(function ($, window, document, undefined) {
    var form = function (options) {
        var options = this.options = $.extend({}, form.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
    }

    form.prototype = {
        toJson: function () {
            var jq = this.jq, params = {}, inputs = jq.serializeArray();
            $.each(inputs, function () {
                if (params[this.name]) {
                    if (!params[this.name].push) {
                        params[this.name] = [params[this.name]];
                    }
                    params[this.name].push(this.value || '');
                } else {
                    params[this.name] = this.value || '';
                }
            });
            return params;
        },
        setVal: function (json) {
            return this;
        }
    }
    form.defaults = {
        container: ''
    };

    api.ui.form = function (options) {
        return new form(options);
    }

})(jQuery, window, document);