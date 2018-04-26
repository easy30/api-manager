;(function ($, window, document, undefined) {
    var modalHtml = '<div class="modal" tabindex="-1" id="loadingModal" data-backdrop="static" data-keyboard="false">\n' +
        '    <div style="width: 200px; height: 20px; z-index: 20000; position: absolute; text-align: center; left: 50%; top: 50%; margin-left: -100px; margin-top: -10px">\n' +
        '        <div class="progress" style="margin-bottom: 5px;">\n' +
        '            <div class="progress-bar progress-bar-striped progress-bar-animated" style="width: 100%;"></div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>';

    var progress = function (options) {
        var options = this.options = $.extend({}, progress.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build();
    }

    progress.prototype = {
        _build: function () {
            var options = this.options, jq = this.jq, $modalHtml = $(modalHtml), modalId = 'modal-' + api.util.generateId();
            this.modalId = modalId;
            jq.append($modalHtml.clone().attr('id', modalId));
            this._show();
            return this;
        },
        _show: function () {
            var jq = this.jq, modalId = this.modalId;
            jq.find('#' + modalId).modal('show');
            return this;
        },
        _hide: function () {
            var modal = this.jq.find('#' + this.modalId);
            modal.modal('hide');
            modal.remove();
            return this;
        }
    };

    progress.defaults = {
        container: 'body'
    }

    api.ui.progress = function (options) {
        return new progress(options);
    }
})(jQuery, window, document);