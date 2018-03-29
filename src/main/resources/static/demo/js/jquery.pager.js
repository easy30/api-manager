;(function ($, window, document, undefined) {
    var firstBtn = $('<button class="btn btn-info btn-sm btn-first" style="margin-left: 5px; height: 28px;"><span class="glyphicon glyphicon-step-backward"></span>首页</button>');
    var prevBtn = $('<button class="btn btn-info btn-sm btn-prev" style="margin-left: 5px; height: 28px;"><span class="glyphicon glyphicon-chevron-left"></span>上页</button>');
    var nextBtn = $('<button class="btn btn-info btn-sm btn-next" style="margin-left: 5px; height: 28px;">下页<span class="glyphicon glyphicon-chevron-right"></span></button>');
    var lastBtn = $('<button class="btn btn-info btn-sm btn-last" style="margin-left: 5px; height: 28px;">末页<span class="glyphicon glyphicon-step-forward"></span></button>');
    var pageDesc = $('<span class="pageDesc" style="color: #2a6496;"></span>');

    function pager(options) {
        var options = this.options = $.extend({}, pager.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build(options);
    };

    pager.prototype = {
        _build: function (pageParams) {
            var jq = this.jq, options = this.options, pageInfo = options.pageInfo;
            pageInfo = pageInfo.replace('_CURRENT_', pageParams.pageIndex);
            pageInfo = pageInfo.replace('_PAGE_', pageParams.totalPage);
            pageInfo = pageInfo.replace('_SIZE_', pageParams.pageSize);
            pageInfo = pageInfo.replace('_TOTAL_', pageParams.totalRecord);
            jq.append(pageDesc.html(pageInfo)).append(firstBtn).append(prevBtn).append(nextBtn).append(lastBtn);
            jq.find('.btn-first').on('click', function () {
                options.currentIndex = 1;
                options.onPageChange();
            });
            jq.find('.btn-prev').on('click', function () {
                var currentIndex = options.currentIndex;
                if (currentIndex == 1) {
                    options.currentIndex = 1;
                } else {
                    options.currentIndex = currentIndex - 1;
                }
                options.onPageChange();
            });
            jq.find('.btn-next').on('click', function () {
                var currentIndex = options.currentIndex;
                if (currentIndex >= options.totalPage) {
                    options.currentIndex = options.totalPage;
                } else {
                    options.currentIndex = currentIndex + 1;
                }
                options.onPageChange();
            });
            jq.find('.btn-last').on('click', function () {
                options.currentIndex = options.totalPage;
                options.onPageChange(options.totalPage);
            });
            return this;
        },
        _refresh: function () {
            var jq = this.jq, options = this.options, pageInfo = options.pageInfo;
            pageInfo = pageInfo.replace('_CURRENT_', options.currentIndex);
            pageInfo = pageInfo.replace('_PAGE_', options.totalPage);
            pageInfo = pageInfo.replace('_SIZE_', options.pageSize);
            pageInfo = pageInfo.replace('_TOTAL_', options.totalRecord);
            jq.find('.pageDesc').html(pageInfo);
        }
    };

    pager.defaults = {
        pageInfo: '&nbsp;[第_CURRENT_页]&nbsp;&nbsp;[共_PAGE_页]&nbsp;&nbsp;[每页_SIZE_条]&nbsp;&nbsp;[共_TOTAL_条]',
        pageIndex: 1,
        currentIndex: 1,
        totalPage: 1,
        totalRecord: 1,
        pageSize: 10
    };

    api.ui.pager = function (options) {
        return new pager(options);
    };
})(jQuery, window, document);