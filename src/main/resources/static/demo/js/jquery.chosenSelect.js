;(function ($, window, document, undefined) {
    function chosenSelect(options) {
        var options = this.options = $.extend({}, chosenSelect.defaults, options);
        var $selector = $('<select class="form-control" style="font-size: 14px;"></select>')
            .attr('name', options.selectName)
            .css('width', options.width)
            .css('height', options.height);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        jq.append($selector);
        if (options.data) {
            this.data(options.data);
        } else {
            this.load();
        }
    };

    chosenSelect.prototype = {
        data: function (data) {
            var jq = this.jq, options = this.options, $selector = jq.find('select');
            $.each(data, function (index, value) {
                $selector.append($('<option></option>').attr('value', value[options.optionField.value]).text(value[options.optionField.text]));
            });
            return this;
        },
        val: function (value) {
            var $select = this.jq.find('select');
            if (!value) {
                return $select.val();
            } else {
                $select.find('option').each(function () {
                    var $option = $(this);
                    if ($option.val() == value) {
                        $option.attr('selected', true);
                        return false;
                    }
                });
            }
            return this;
        },
        disable: function () {
            this.jq.find('select').attr('disabled', true);
            return this;
        },
        enable: function () {
            this.jq.find('select').attr('disabled', false);
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
        container: '',
        url: '',
        width: '80%',
        height: '35px'
    };

    api.ui.chosenSelect = function (options) {
        return new chosenSelect(options);
    };
})(jQuery, window, document);