;(function ($, window, document, undefined) {
    var editSelect = function (options) {
        var options = this.options = $.extend({}, editSelect.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        jq.addClass('dropdown');
        this._build();
        if(options.url){
            this._load(options.param);
        } else {
            this._data(options.content);
        }
    };

    editSelect.prototype = {
        _build: function () {
            var jq = this.jq, options = this.options;
            var $input = $('<input type="text" class="form-control" data-toggle="dropdown">');
            var $dropdownMenu = $('<div class="dropdown-menu" style="margin-top: 0px;"></div>');
            $input.css('width', options.width).attr('name', options.selectName);
            $dropdownMenu.css('width', options.width);
            this.input = $input, this.dropdownMenu = $dropdownMenu;
            jq.append($input).append($dropdownMenu);
        },
        _data: function (data) {
            var editSelect = this, jq = this.jq, options = this.options;
            if(data) {
                var dropdownItem = jq.find('.dropdown-menu');
                $.each(data, function (index, value) {
                    var $dropdownItem = $('<a class="dropdown-item" href="#"></a>');
                    $dropdownItem.text(value[options.selectValueField]);
                    $dropdownItem.attr('optionCode', value[options.selectCodeField]);
                    $dropdownItem.on('click', function () {
                        var $this = $(this), param = {};
                        param['oldVal'] = editSelect.input.val();
                        editSelect.input.val($this.text());
                        param['newVal'] = $this.text();
                        options.change && options.change(param);
                    });
                    editSelect.dropdownMenu.append($dropdownItem);
                })
            }
        },
        _val: function (value) {
            var editSelect = this, jq = this.jq;
            if(value){
                $.each(jq.find('.dropdown-item'), function (index, item) {
                    var $item = $(item);
                    if($item.attr('optionCode') == value){
                        editSelect.input.val($item.text());
                        return false;
                    }
                });
            } else {
                return editSelect.input.val();
            }
        },
        _load: function (param) {
            var editSelect = this, jq = this.jq, options = this.options;
            $.ajax({
                url: options.url,
                type: 'GET',
                data: param,
                async: false,
                dataType: 'json',
                success: function (result) {
                    var data = result.data;
                    editSelect._data(data);
                }
            });
        }
    }
    editSelect.defaults = {
        container: '',
        selectName: '',
        selectCodeField: '',
        selectValueField: '',
        content: {},
        param: {},
        url: '',
        width: '',
        change: undefined
    }
    api.ui.editSelect = function (options) {
        return new editSelect(options);
    }
})(jQuery, window, document);