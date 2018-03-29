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

    function modal(options) {
        var options = this.options = $.extend({}, modal.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build();
    };

    modal.prototype = {
        _build: function () {
            var modal = this, options = this.options, jq = this.jq, buttons = options.buttons;
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
                        modal.hidden();
                        button.fn && button.fn();
                    });
                    jq.find('.modal-footer').append($btn);
                })
            }
            jq.find('#myModal').on('hidden.bs.modal', function () {
                this.remove();
            });
        },
        show: function () {
            this.jq.find('#myModal').modal('show');
        },
        hidden: function () {
            this.jq.find('#myModal').modal('hide');
        }
    };
    modal.defaults = {
        container: 'body',
        content: '系统提示'
    };

    api.ui.modal = function (options) {
        return new modal(options);
    }

})(jQuery, window, document);