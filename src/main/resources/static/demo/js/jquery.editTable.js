;(function ($, window, document, undefined) {
    function editTable(options) {
        var options = this.options = $.extend({}, editTable.defaults, options), table = this;
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        var form = this.$form = ('string' == typeof options.form) ? $(options.form) : options.form;
        form.find('button').on('click', function () {
            table._reload();
        });
        var data = options.data;
        if (data) {
            this._build(data);
        } else {
            this._load();
        }
    }

    editTable.prototype = {
        _build: function (data) {
            var $table = $('<table class="table table-hover table-sm" style="font-size: 15px;"></table>');
            var $tBody = $('<tbody></tbody>');
            var $tFoot = $('<tfoot></tfoot>');
            var $addBtn = $('<button class="btn btn-success btn-sm btn-add" type="button"><span class="glyphicon glyphicon-plus"></span></button>');
            var editTable = this, jq = this.jq, conf = this.options, fields = conf.fields, headers = conf.headers;
            // 设置表头
            if (headers) {
                var $tr = $('<tr style="background-color: #e3e3e3;"></tr>');
                $.each(headers, function (index, header) {
                    $tr.append($('<th style="padding-left: 10px; height: 10px;"></th>').css('width', header.width).text(header.text));
                });
                $tBody.append($tr);
            }
            $addBtn.append('&nbsp;&nbsp;添加').on('click', function () {
                editTable._addRow();
            });
            $table.css('width', conf.width).append($tBody).append($tFoot.append($addBtn));
            jq.append($table);
            // 数据开始
            $.each(data, function (index, rowData) {
                editTable._showRow(rowData);
            });
            return this;
        },
        _load: function (params) {
            var editTable = this, jq = this.jq, conf = this.options;
            $.ajax({
                url: conf.url,
                type: 'GET',
                data: params,
                dataType: 'json',
                success: function (result) {
                    var data = result['data']['datas'];
                    jq.empty();
                    editTable._build(data);
                }
            })
            return this;
        },
        _reload: function () {
            var conf = this.options, $form = this.$form, params = {}, inputs = $form.serializeArray();
            $.each(inputs, function () {
                if (params[this.name]) {
                    if (!params[this.name].push) {
                        params[this.name] = [params[this.name]];
                    }
                    params[this.name].push(this.value || '');
                } else {
                    params[this.name] = this.value || '';
                }
            });
            this._load(params);
            return this;
        },
        _addRow: function () {
            var editTable = this, jq = this.jq, fields = this.options.fields, rowButtons = this.options.rowButtons, $tr = $('<tr></tr>'), hasDoingRow = false;
            jq.find('input[name="id"]').each(function () {
                var inputIdVal = $(this).val();
                if (!inputIdVal) {
                    hasDoingRow = true;
                    return false;
                }
            })
            if (hasDoingRow) {
                alert("存在未完成的行数据！");
                return jq;
            }
            $.each(fields, function (index, field) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if (field.name == 'id') {
                    $tr.append($td.append($('<span></span>')).append($('<input type="text"/>').attr('name', field.name).css('display', 'none')));
                } else {
                    if(field.type == 'input'){
                        var $span = $('<span class="td-item-span" style="display: none"></span>');
                        var $input = $('<input class="form-control td-item-input" type="text" style="height: 30px;"/>');
                        $input.attr('inputDesc', field.inputDesc).attr('name', field.name).attr('required', field.required).css('display', '');
                        $td.append($span).append($input);
                        $tr.append($td);
                    } else if(field.type = 'select'){
                        var options = field.options;
                        options.container = $td;
                        var chosenSelect = api.ui.chosenSelect(field.options);
                        $tr.append($td);
                    }
                }
            });

            if (rowButtons) {
                var $td = $('<td class="td-button" style="padding-left: 10px;"></td>');
                $.each(rowButtons, function (index, button) {
                    var type = button.type;
                    if (type == 'update') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-update" style="margin-left: 10px;" type="button" textType="update"><span class="glyphicon glyphicon-edit"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text).css('display', 'none');
                        $button.on('click', function () {
                            var textType = $button.attr('textType');
                            if (textType == 'update') {
                                $tr.find('.td-item-input').css('display', '');
                                $tr.find('.td-item-span').css('display', 'none');
                                $td.find('.btn-delete').css('display', 'none');
                                $td.find('.btn-cancel').css('display', '');
                                $(this).text('确定').attr('textType', 'sure');
                            } else {
                                // 数据校验
                                var validation = {};
                                var params = {};
                                $.each($tr.find('.td-item'), function (index, td) {
                                    var $input = $(td).find('input');
                                    var inputName = $input.attr('name');
                                    var inputDesc = $input.attr('inputDesc');
                                    var inputValue = $input.val();
                                    var required = $input.attr('required');
                                    if (required) {
                                        if (!inputValue) {
                                            validation['inputDesc'] = inputDesc;
                                            return false;
                                        }
                                    }
                                    params[inputName] = inputValue;
                                })
                                if (validation.inputDesc) {
                                    alert(validation.inputDesc + "不能为空！");
                                } else {
                                    $tr.find('.td-item-input').css('display', 'none');
                                    $tr.find('.td-item-span').css('display', '');
                                    $td.find('.btn-delete').css('display', '');
                                    $td.find('.btn-cancel').css('display', 'none');

                                    // 调用服务修改数据
                                    if (button.url) {
                                        $.ajax({
                                            url: button.url,
                                            type: 'GET',
                                            data: params,
                                            dataType: 'json',
                                            success: function (result) {
                                                editTable._reload();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'save') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-save" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-save"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            // 数据校验
                            var validation = {};
                            var params = {};
                            $.each($tr.find('.td-item'), function (index, td) {
                                var $input = $(td).find('input');
                                var inputName = $input.attr('name');
                                var inputDesc = $input.attr('inputDesc');
                                var inputValue = $input.val();
                                var required = $input.attr('required');
                                if (required) {
                                    if (!inputValue) {
                                        validation['inputDesc'] = inputDesc;
                                        return false;
                                    }
                                }
                                params[inputName] = inputValue;
                            })
                            if (validation.inputDesc) {
                                alert(validation.inputDesc + "不能为空！");
                            } else {
                                // 调用服务保存数据
                                if (button.url) {
                                    $.ajax({
                                        url: button.url,
                                        type: 'GET',
                                        data: params,
                                        dataType: 'json',
                                        success: function (result) {
                                            editTable._reload();
                                        }
                                    });
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'delete') {
                        var $button = $('<button class="btn btn-danger btn-sm btn-delete" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-trash"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            var msg = "确认删除？";
                            if (confirm(msg)) {
                                var $idInput = $tr.find('input[name="id"]');
                                var inputVal = $idInput.val();
                                if (!inputVal) {
                                    $tr.remove();
                                } else {
                                    var params = {};
                                    params[$idInput.attr('name')] = inputVal;
                                    // 调用服务删除数据
                                    $.ajax({
                                        url: button.url,
                                        type: 'GET',
                                        data: params,
                                        dataType: 'json',
                                        success: function (result) {
                                            editTable._reload();
                                        }
                                    });
                                }
                            }
                        });
                        $td.append($button);
                    }
                })

                var $cancelBtn = $('<button class="btn btn-warning btn-sm btn-cancel" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;取消</button>');
                $cancelBtn.css('display', 'none').on('click', function () {
                    $.each($tr.find('.td-item'), function (index, td) {
                        var text = $(td).find('span').text();
                        $(td).find('input').val(text);
                    })

                    $tr.find('.btn-update').attr('textType', 'update');
                    $tr.find('.btn-update').html('<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;编辑');
                    $tr.find('.td-item-input').css('display', 'none');
                    $tr.find('.td-item-span').css('display', '');
                    $tr.find('.btn-delete').css('display', '');
                    $tr.find('.btn-cancel').css('display', 'none');
                    $.each($tr.find('.td-item'), function (index, td) {
                        var inputValue = $(td).find('input').val();
                        $(td).find('span').text(inputValue);
                    })
                    $(this).css('display', 'none');
                });
                $td.append($cancelBtn);
                $tr.append($td);
            }
            jq.find('tbody:first').append($tr);
            return this;
        },
        _showRow: function (rowData) {
            var editTable = this, jq = this.jq, fields = this.options.fields, rowButtons = this.options.rowButtons, $tr = $('<tr></tr>');
            $.each(fields, function (index, field) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if (field.name == 'id') {
                    var $span = $('<span>' + rowData[field.name] + '</span>');
                    var $input = $('<input type="text"/>');
                    $input.attr('name', field.name).val(rowData[field.name]).css('display', 'none');
                    $td.append($span).append($input);
                    $tr.append($td);
                } else {
                    if(field.type == 'input'){
                        var $span = $('<span class="td-item-span">' + rowData[field.name] + '</span>');
                        var $input = $('<input class="form-control td-item-input" type="text" style="height: 30px;"/>');
                        var required = field.required;
                        $input.attr('inputDesc', field.inputDesc).attr('required', required).attr('name', field.name).val(rowData[field.name]).css('display', 'none');
                        $td.append($span).append($input);
                        $tr.append($td);
                    } else if(field.type = 'select'){
                        var options = field.options;
                        options.container = $td;
                        var chosenSelect = api.ui.chosenSelect(field.options);
                        chosenSelect.val(rowData[field.name]);
                        chosenSelect.disable();
                        $tr.append($td);
                    }
                }
            });

            if (rowButtons) {
                var $td = $('<td class="td-button" style="padding-left: 10px;"></td>');
                $.each(rowButtons, function (index, button) {
                    var type = button.type;
                    if (type == 'update') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-update" style="margin-left: 10px;" type="button" textType="update"><span class="glyphicon glyphicon-edit"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            var textType = $(this).attr('textType');
                            if (textType == 'update') {
                                // 显示输入域
                                $tr.find('.td-item-input').css('display', '');
                                // 隐藏显示域
                                $tr.find('.td-item-span').css('display', 'none');
                                $td.find('.btn-delete').css('display', 'none');
                                $td.find('.btn-enter').css('display', 'none');
                                $td.find('.btn-cancel').css('display', '');
                                $tr.find('select').attr('disabled', false);
                                $(this).html('<span class="glyphicon glyphicon-ok-sign"></span>&nbsp;&nbsp;确定').attr('textType', 'sure');
                            } else {
                                // 数据校验
                                var validation = {};
                                var params = {};
                                $.each($tr.find('.td-item'), function (index, td) {
                                    var $input = $(td).find('input');
                                    var inputName = $input.attr('name');
                                    var inputDesc = $input.attr('inputDesc');
                                    var inputValue = $input.val();
                                    var required = $input.attr('required');
                                    if (required) {
                                        if (!inputValue) {
                                            validation['inputDesc'] = inputDesc;
                                            return false;
                                        }
                                    }
                                    params[inputName] = inputValue;
                                })
                                if (validation.inputDesc) {
                                    alert(validation.inputDesc + "不能为空！");
                                } else {
                                    // 调用服务修改数据
                                    if (button.url) {
                                        $.ajax({
                                            url: button.url,
                                            type: 'GET',
                                            data: params,
                                            dataType: 'json',
                                            success: function (result) {
                                                editTable._reload();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'save') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-save" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-save"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text).css('display', 'none');
                        // 保存按钮初始隐藏
                        $button.on('click', function () {
                            // 数据校验
                            var validation = {};
                            var params = {};
                            $.each($tr.find('.td-item'), function (index, td) {
                                var $input = $(td).find('input');
                                var inputName = $input.attr('name');
                                var inputDesc = $input.attr('inputDesc');
                                var inputValue = $input.val();
                                var required = $input.attr('required');
                                if (required) {
                                    if (!inputValue) {
                                        validation['inputDesc'] = inputDesc;
                                        return false;
                                    }
                                }
                                params[inputName] = inputValue;
                            })

                            if (validation.inputDesc) {
                                alert(validation.inputDesc + "不能为空！");
                            } else {
                                // 调用服务保存数据
                                if (button.url) {
                                    $.ajax({
                                        url: button.url,
                                        type: 'GET',
                                        data: params,
                                        dataType: 'json',
                                        success: function (result) {
                                            editTable._reload();
                                        }
                                    });
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'delete') {
                        var $button = $('<button class="btn btn-danger btn-sm btn-delete" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-trash"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            var msg = "确认删除？";
                            if (confirm(msg)) {
                                var $idInput = $tr.find('input[name="id"]');
                                var inputVal = $idInput.val();
                                if (!inputVal) {
                                    $tr.remove();
                                } else {
                                    var params = {};
                                    params['id'] = inputVal;
                                    // 调用服务删除数据
                                    $.ajax({
                                        url: button.url,
                                        type: 'GET',
                                        data: params,
                                        dataType: 'json',
                                        success: function (result) {
                                            editTable._reload();
                                        }
                                    });
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'enter') {
                        var $button = $('<button class="btn btn-info btn-sm btn-enter" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-share-alt"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            if (button.fn) {
                                button.fn();
                            }
                        });
                        $td.append($button);
                    }
                })
                var $cancelBtn = $('<button class="btn btn-warning btn-sm btn-cancel" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-remove"></span></button>');
                $cancelBtn.append('&nbsp;&nbsp;取消');
                $cancelBtn.css('display', 'none').on('click', function () {
                    $.each($tr.find('.td-item'), function (index, td) {
                        var text = $(td).find('span').text();
                        $(td).find('input').val(text);
                    })

                    $tr.find('.btn-update').attr('textType', 'update');
                    $tr.find('.btn-update').html('<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;编辑');
                    $tr.find('.td-item-input').css('display', 'none');
                    $tr.find('.td-item-span').css('display', '');
                    $tr.find('.btn-delete').css('display', '');
                    $tr.find('.btn-cancel').css('display', 'none');
                    $td.find('.btn-enter').css('display', '');
                    $tr.find('select').attr('disabled', true);

                    $.each($tr.find('.td-item'), function (index, td) {
                        var inputValue = $(td).find('input').val();
                        $(td).find('span').text(inputValue);
                    })

                    $(this).css('display', 'none');
                });
                $td.append($cancelBtn);
                $tr.append($td);
            }
            jq.find('tbody:first').append($tr);
            return this;
        }
    }

    editTable.defaults = {
        container: '',
        width: '100%',
    }

    api.ui.editTable = function (options) {
        return new editTable(options);
    }
})(jQuery, window, document);