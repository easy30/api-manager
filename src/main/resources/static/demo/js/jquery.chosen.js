;(function ($, window, document, undefined) {
    function chosenSelect(options) {
        var options = this.options = $.extend({}, chosenSelect.defaults, options);
        var jq = this.jq = ('string' == typeof options.selector) ? $(options.selector) : options.selector;
        jq.css('width', options.width).css('height', options.height);
        if (options.data) {
            this.data(options.data);
        } else {
            this.load();
        }
    };

    chosenSelect.prototype = {
        data: function (data) {
            var jq = this.jq, options = this.options;
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
                        return false;
                    }
                });
            }
        },
        disable: function () {
            this.jq.attr('disabled', true);
            return this;
        },
        enable: function () {
            this.jq.attr('disabled', false);
            return this;
        },
        load: function () {
            var chosenSelect = this;
            $.ajax({
                url: this.options.url,
                type: 'GET',
                async: false,
                data: {},
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
        height: '34px'
    };

    api.ui.chosenSelect = function (options) {
        return new chosenSelect(options);
    };
})(jQuery, window, document);