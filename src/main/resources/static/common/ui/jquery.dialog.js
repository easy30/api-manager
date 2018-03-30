;(function ($, window, document, undefined) {
    var modalHtml = '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">\n' +
        '  <div class="modal-dialog">\n' +
        '    <div class="modal-content">\n' +
        '      <div class="modal-header">\n' +
        '        <p class="modal-title" id="myModalLabel" style="color: #ab1e1e"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;系统提示</p>\n' +
        '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
        '          <span aria-hidden="true">&times;</span>\n' +
        '        </button>\n' +
        '      </div>\n' +
        '      <div class="modal-body">\n' +
        '        系统消息\n' +
        '      </div>\n' +
        '      <div class="modal-footer"></div>\n' +
        '    </div>\n' +
        '  </div>\n' +
        '</div>';

    var dialog = function (options) {
        var options = this.options = $.extend({}, dialog.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build();
    };

    dialog.prototype = {
        _build: function () {
            var dialog = this, options = this.options, jq = this.jq, buttons = options.buttons;
            jq.append(modalHtml).find('.modal-body').text(this.options.content);
            if (buttons) {
                $.each(buttons, function (index, button) {
                    var btnType = button.type;
                    if (btnType == 'cancel') {
                        var $btn = $('<button type="button" class="btn btn-secondary"></button>').text(button.text);
                    } else if (btnType == 'sure') {
                        var $btn = $('<button type="button" class="btn btn-primary"></button>').text(button.text);
                    } else {
                        var $btn = $('<button type="button" class="btn btn-primary"></button>').text(button.text);
                    }
                    $btn.on('click', function () {
                        dialog.close();
                        button.fn && button.fn();
                    });
                    jq.find('.modal-footer').append($btn);
                })
            }
            jq.find('#myModal').on('hidden.bs.modal', function () {
                this.remove();
            });
            return this;
        },
        open: function () {
            this.jq.find('#myModal').modal('show');
            return this;
        },
        close: function () {
            this.jq.find('#myModal').modal('hide');
            return this;
        }
    };
    dialog.defaults = {
        container: 'body',
        content: '系统提示'
    };

    api.ui.dialog = function (options) {
        return new dialog(options);
    }
})(jQuery, window, document);