;(function ($, window, document, undefined) {
    var modalHtml = '<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">\n' +
        '  <div class="modal-dialog">\n' +
        '    <div class="modal-content">\n' +
        '      <div class="modal-header">\n' +
        '        <p class="modal-title" id="myModalLabel" style="color: #ab1e1e"></p>\n' +
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
    var modalTitle = '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;系统提示';

    var dialog = function (options) {
        var options = this.options = $.extend({}, dialog.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build();
    };

    dialog.prototype = {
        _build: function () {
            var dialog = this, options = this.options, jq = this.jq, buttons = options.buttons, $modalHtml = $(modalHtml), modalId = 'modal-' + api.util.generateId();
            this.modalId = modalId;

            var modal = jq.append($modalHtml.clone().attr('id', modalId)).find('#' + modalId);
            modal.find('.modal-body').empty().append(this.options.content)
            var myModalLabel = jq.find('#' + modalId).find('#myModalLabel');
            if(options.iTitle){
                myModalLabel.append(modalTitle);
            } else {
                myModalLabel.append(options.title);
            }
            if(options.width) {
                jq.find('#' + modalId).find('.modal-content').css('width', options.width);
            }

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
                        button.fn && button.fn(modal.find('.modal-body'));
                    });
                    jq.find('#' + modalId).find('.modal-footer').append($btn);
                })
            }

            //获取modal的宽度
            var modalWidth = jq.find('#' + modalId).find('.modal-content').css('width');
            //计算偏移量
            var left = "-" + (parseFloat(modalWidth.replace('%', '')) - 100) / 2.5;
            //modal居中
            jq.find('#' + modalId).css({"margin-left": left + '%'});

            jq.find('#' + modalId).on('hidden.bs.modal', function () {
                this.remove();
            });
            return this;
        },
        open: function () {
            var jq = this.jq, options = this.options;
            jq.find('#' + this.modalId).modal('show');
            options.opened && options.opened(jq.find('#' + this.modalId).find('.modal-body'));
            return this;
        },
        close: function () {
            this.jq.find('#' + this.modalId).modal('hide');
            return this;
        }
    };
    dialog.defaults = {
        container: 'body',
        content: '系统提示',
        iTitle: true,
        title: '',
        opened: undefined
    };

    api.ui.dialog = function (options) {
        return new dialog(options);
    }
})(jQuery, window, document);