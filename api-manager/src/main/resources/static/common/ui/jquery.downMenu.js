;(function ($, window, document, undefined) {
    var downMenu = function (config) {
        var options = this.options = $.extend({}, downMenu.defaults, config);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build();
    };

    downMenu.prototype = {
        _build: function () {
            var jq = this.jq, options = this.options;
            jq.css('height', options.height);
            var ul = $('<ul></ul>'), mul = ul.clone(), mli = $('<li class="main"></li>'), li = $('<li></li>'), a = $('<a></a>');

            var len = options.items.length;
            $.each(options.items, function (index, item) {
                var liItem = li.clone();
                liItem.append(item.name);
                liItem.on('click', function () {
                    item.fn && item.fn();
                });
                ul.append('<hr style="margin: 0px 0px">');
                ul.append(liItem);
            })
            var div = $('<div></div>');
            div.append(ul);
            mli.append(a.clone().append(options.title)).append(div);
            mul.append(mli);
            jq.append(mul);

            jq.find('.main').hover(function () {
                $(this).find("ul").toggle();
            })
        }
    };

    downMenu.defaults = {
        height: '10px'
    };

    api.ui.downMenu = function (config) {
        return new downMenu(config);
    };

})(jQuery, window, document);