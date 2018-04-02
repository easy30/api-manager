;(function ($, window, document, undefined) {
    var chosenSelect = function (options) {
        var options = this.options = $.extend({}, chosenSelect.defaults, options);
        var jq = this.jq = ('string' == typeof options.selector) ? $(options.selector) : options.selector;
        jq.css('width', options.width).css('height', options.height);
        if (options.data) {
            this.data(options.data);
        } else {
            this.load(options.params);
        }
        // 添加change事件
        options.change && jq.on('change', function (event) {
            options.change(event);
        })
    };

    chosenSelect.prototype = {
        data: function (data) {
            var jq = this.jq, options = this.options;
            jq.append($('<option></option>'));
            $.each(data, function (index, value) {
                jq.append($('<option></option>').attr('value', value[options.optionField.value]).text(value[options.optionField.text]));
            });
            return this;
        },
        val: function (value) {
            var jq = this.jq;
            if (!value) {
                return jq.val();
            } else {
                jq.find('option').each(function () {
                    var $option = $(this);
                    if ($option.val() == value) {
                        $option.attr('selected', true);
                        jq.trigger('change');
                        return false;
                    }
                });
            }
        },
        clear: function () {
            this.jq.empty();
        },
        disable: function () {
            this.jq.attr('disabled', true);
            return this;
        },
        enable: function () {
            this.jq.attr('disabled', false);
            return this;
        },
        load: function (param) {
            var chosenSelect = this;
            $.ajax({
                url: this.options.url,
                type: 'GET',
                async: false,
                data: param,
                dataType: 'json',
                success: function (result) {
                    chosenSelect.data(result.data);
                }
            });
            return this;
        }
    };

    chosenSelect.defaults = {
        selector: '',
        url: '',
        width: '80%',
        height: '34px',
        params: {},
        change: function (event) {

        }
    };

    api.ui.chosenSelect = function (options) {
        return new chosenSelect(options);
    };
})(jQuery, window, document);