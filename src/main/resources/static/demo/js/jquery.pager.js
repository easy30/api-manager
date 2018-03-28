;(function ($, window, document, undefined) {
    var firstBtn = $('<button class="btn btn-info btn-sm btn-first">首页</button>');
    var prevBtn = $('<button class="btn btn-info btn-sm btn-prev">上一页</button>');
    var nextBtn = $('<button class="btn btn-info btn-sm btn-next">下一页</button>');
    var lastBtn = $('<button class="btn btn-info btn-sm btn-last">首页</button>');
    function pager(options) {
        var options = this.options = $.extend({}, pager.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this.build();
    };
    pager.prototype = {
        build: function (pageParams) {
            var jq = this.jq, options = this.options, pageInfo = options.pageInfo;
            jq.append(firstBtn).append(prevBtn).append(nextBtn).append(lastBtn);
            jq.find('button').on('click', function () {
                
            });
        }
    };
    pager.defaults = {};

    api.ui.pager = function (options) {
        return new pager(options);
    };
})(jQuery, window, document);