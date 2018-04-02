;(function ($, window, document, undefined) {
    var param = function (options) {
        var options = this.options = $.extend({}, param.defaults, options);
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        this._build();
    };

    param.prototype = {
        _build: function () {
            var that = this, jq = this.jq, conf = this.options, fields = conf.fields, headers = conf.headers, footBtn = conf.footBtn;
            var $table = $('<table class="table table-bordered table-sm" style="font-size: 15px;"></table>');
            var $tHead = $('<thead></thead>');
            var $tBody = $('<tbody></tbody>');
            var $tFoot = $('<tfoot></tfoot>');

            if (headers) {
                var $tr = $('<tr style="background-color: #e3e3e3"></tr>');
                $.each(headers, function (index, header) {
                    $tr.append($('<th style="padding-left: 10px; height: 10px;"></th>').css('width', header.width).text(header.text));
                });
                $tHead.append($tr);
            }

            $tFoot.append($('<tr></tr>').append($('<td></td>').attr('colspan', headers.length)));
            if (footBtn) {
                $.each(footBtn, function (index, footBtn) {
                    var type = footBtn.type;
                    if (type == 'add') {
                        var $addBtn = $('<button class="btn btn-success btn-sm" type="button"><span class="glyphicon glyphicon-plus"></span></button>').append('&nbsp;' + footBtn.text);
                        $addBtn.on('click', function () {
                            that._addRow();
                            footBtn.fn && footBtn.fn();
                        });
                        $tFoot.find('td').append($addBtn);
                    }
                })
            }

            jq.append($table.css('width', conf.width).append($tHead).append($tBody).append($tFoot));
            return this;
        },
        _addRow: function () {
            var that = this, jq = this.jq, conf = this.options, fields = conf.fields, $tr = $('<tr></tr>');
            var $operate = $('<td class="td-item" style="padding-left: 10px;"></td>');
            var $addLink = $('<a class="glyphicon glyphicon-plus" href="#" style="text-decoration: none; margin-top: 10px; color: #1e7e34; display: ;"></a>');
            var $removeLink = $('<a class="glyphicon glyphicon-remove" href="#" style="text-decoration: none; margin-left: 15px; margin-top: 10px; color: #ab1e1e"></a>');
            $removeLink.on('click', function () {
                $tr.remove();
            });
            $addLink.on('click', function () {
                that._after($tr);
            });
            $operate.append($addLink).append($removeLink);
            $tr.append($operate).attr('identity', 'tr-' + api.util.generateNumber());
            $.each(fields, function (index, field) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if(field.type == 'input'){
                    var $input = $('<input class="form-control td-item-input" type="text" style="height: 30px;"/>');
                    $input.attr('name', field.name);
                    $tr.append($td.append($input));
                } else if(field.type = 'select'){
                    var chosenOptions = field.options, $selector = $('<select class="form-control"></select>');
                    $selector.attr('name', field.name);
                    chosenOptions.selector = $selector;
                    api.ui.chosenSelect(chosenOptions);
                    $tr.append($td.append($selector));
                }
            });
            jq.find('tbody').append($tr);
            return this;
        },
        _after: function ($row) {
            var that = this, conf = this.options, fields = conf.fields, $tr = $('<tr></tr>');
            var $operate = $('<td class="td-item" style="padding-left: 10px;"></td>');
            var $addLink = $('<a class="glyphicon glyphicon-plus" href="#" style="text-decoration: none; margin-top: 10px; color: #1e7e34; display: ;"></a>');
            var $removeLink = $('<a class="glyphicon glyphicon-remove" href="#" style="text-decoration: none; margin-left: 15px; margin-top: 10px; color: #ab1e1e"></a>');
            $removeLink.on('click', function () {
                $tr.remove();
            });
            $addLink.on('click', function () {
                that._after($tr);
            });
            $operate.append($addLink).append($removeLink);
            $tr.append($operate).attr('identity', 'tr-' + api.util.generateNumber());
            $.each(fields, function (index, field) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if(field.type == 'input'){
                    var $input = $('<input class="form-control td-item-input" type="text" style="height: 30px;"/>');
                    $input.attr('name', field.name);
                    $tr.append($td.append($input));
                } else if(field.type = 'select'){
                    var chosenOptions = field.options, $selector = $('<select class="form-control"></select>');
                    $selector.attr('name', field.name);
                    chosenOptions.selector = $selector;
                    api.ui.chosenSelect(chosenOptions);
                    $tr.append($td.append($selector));
                }
            });
            $row.after($tr);
            return this;
        }
    };
    param.defaults = {
        container: '',
        width: '80%',
        headers: [
            {text: '操作', width: '6%'},
            {text: '名称', width: '24%'},
            {text: '含义', width: '30%'},
            {text: '类型', width: '20%'},
            {text: 'Mock规则', width: '20%'}
        ],
        fields: [
            {name: 'name', type: 'input'},
            {name: 'desc', type: 'input'},
            {
                name: 'type', type: 'select', options: {
                    width: '100%',
                    optionField: {value: 'id', text: 'depName'},
                    data: {

                    }
                }
            },
            {name: 'rule', type: 'input'}
        ],
        footBtn: [
            {
                type: 'add', text: '添加参数', fn: function (row) {

                }
            }
        ]
    };
    api.ui.param = function (options) {
        return new param(options);
    }
})(jQuery, window, document);