;(function ($, window, document, undefined) {
    var form = function (options) {
        var options = this.options = $.extend({}, form.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
    }

    form.prototype = {
        toJson: function () {
            var jq = this.jq, params = {}, els = jq.serializeArray();
            $.each(els, function (index, el) {
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
        giveVal: function (data) {
            this.oldData = data;
            this.jq.find('input,select').each(function () {
                var $this = $(this);
                $this.val(data[$this.attr('name')]);
            });
            return this;
        },
        reset: function () {
            var oldData = this.data;
            this.jq.find('input,select').each(function () {
                var $this = $(this);
                $this.val(oldData[$this.attr('name')]);
            });
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