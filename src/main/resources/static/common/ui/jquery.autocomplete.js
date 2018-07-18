;(function ($) {
    var autocomplete = function (options) {
        var options = this.options = $.extend({}, autocomplete.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        jq.css('width', options.width);
        if (options.url) {
            this._load();
        } else {
            this._data(options.data);
        }
    };
    autocomplete.prototype = {
        _load: function () {
            var that = this, jq = this.jq, options = this.options, url = options.url
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (result) {
                    var data = result.data;
                    that._data(data);
                }
            });
            return this;
        },
        _data: function (dataValues) {
            var that = this, jq = this.jq, options = this.options;
            function split(val) {
                return val.split(/,\s*/);
            }

            function extractLast(term) {
                return split(term).pop();
            }

            jq.bind("keydown", function (event) {
                    if (event.keyCode === $.ui.keyCode.TAB && jq.data("ui-autocomplete").menu.active) {
                        event.preventDefault();
                    }
                })
                .autocomplete({
                    minLength: 0,
                    appendTo: options.appendTo,
                    source: function (request, response) {
                        // 回到 autocomplete，但是提取最后的条目
                        response($.ui.autocomplete.filter(dataValues, extractLast(request.term)));
                    },
                    focus: function () {
                        // 防止在获得焦点时插入值
                        return false;
                    },
                    select: function (event, ui) {
                        var terms = split(this.value);
                        // 移除当前输入
                        terms.pop();
                        // 添加被选项
                        terms.push(ui.item.value);
                        // 添加占位符，在结尾添加逗号+空格
                        terms.push("");
                        this.value = terms.join(", ");
                        return false;
                    }
                });
            return this;
        }
    };
    autocomplete.defaults = {
        container: '',
        appendTo: 'body',
        url: '',
        data: '',
        width: '',
        multiItem: true
    };
    api.ui.autocomplete = function (option) {
        return new autocomplete(option);
    }
})(jQuery);