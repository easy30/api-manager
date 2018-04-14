;(function ($, window, document, undefined) {
    var editTable = function (options) {
        var options = this.options = $.extend({}, editTable.defaults, options), table = this;
        var jq = this.jq = ('string' == typeof options.container) ? $(options.container) : options.container;
        var form = this.$form = ('string' == typeof options.form) ? $(options.form) : options.form;
        form.find('button').on('click', function () {
            table._reload();
        });

        // 监听回车事件，并且屏蔽回车后浏览器刷新
        $(document).keydown(function (event) {
            if(event.keyCode == 13){
                form.find('button').click();
                return false;
            }
        });

        this._build();
        var data = options.data;
        if (data) {
            this._refresh(data);
        } else {
            this._load();
        }
    };

    editTable.prototype = {
        _build: function () {
            var editTable = this, jq = this.jq, conf = this.options, fields = conf.fields, headers = conf.headers, headBtns = conf.headBtn;
            var $table = $('<table class="table table-hover table-sm" style="font-family: \'Microsoft YaHei\',微软雅黑,\'MicrosoftJhengHei\',华文细黑,STHeiti,MingLiu; font-size: 15px;"></table>');
            var $tHead = $('<thead></thead>');
            var $tBody = $('<tbody></tbody>');
            var $tFoot = $('<tfoot></tfoot>');

            // 设置表头
            if (headers) {
                var $tr = $('<tr style="background-color: #e3e3e3;"></tr>');
                $.each(headers, function (index, header) {
                    $tr.append($('<th style="padding-left: 10px; height: 10px;"></th>').css('width', header.width).text(header.text));
                });
                $tHead.append($tr);
            }

            var pageInfoTd = $('<td></td>').attr('colspan', headers.length - 1);
            $tFoot.append($('<tr></tr>').append(pageInfoTd).append('<td style="padding-left: 10px;"></td>'));

            if(headBtns){
                $.each(headBtns, function (index, headBtn) {
                    var type = headBtn.type;
                    if(type == 'add'){
                        var $addBtn = $('<button class="btn btn-success btn-sm btn-add" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-plus"></span></button>').append('&nbsp;&nbsp;' + headBtn.text);
                        $addBtn.on('click', function () {
                            editTable._addRow();
                            headBtn.fn && headBtn.fn($tBody.find('tr:last'));
                        });
                        $tFoot.find('td:last').append($addBtn);
                    } else {
                        var $customBtn = $('<button class="btn btn-success btn-sm" style="margin-left: 10px;" type="button"><span></span></button>').append('&nbsp;&nbsp;' + headBtn.text);
                        $customBtn.find('span').addClass(headBtn.icon);
                        $customBtn.on('click', function () {
                            headBtn.fn && headBtn.fn();
                        });
                        $tFoot.find('td:last').append($customBtn);
                    }
                })
            }

            var pageOptions = {
                container: $tFoot.find('td:first'),
                onPageChange: function (pageIndex) {
                    editTable._load();
                }
            };
            var pager = api.ui.pager(pageOptions);
            this.pager = pager;

            jq.append($table.css('width', conf.width).append($tHead).append($tBody).append($tFoot));
            return this;
        },
        _refresh: function (data) {
            var editTable = this, jq = this.jq, conf = this.options, headers = conf.headers;
            jq.find('tbody').empty();
            // 数据开始
            $.each(data, function (index, rowData) {
                editTable._showRow(rowData);
            });
            return this;
        },
        _ajax: function (params) {
            var editTable = this, conf = this.options, pager = this.pager, pagerOptions = pager.options;
            $.ajax({
                url: conf.url,
                type: 'GET',
                data: params,
                dataType: 'json',
                success: function (result) {
                    var pageData = result['data'];
                    pagerOptions.currentIndex = pageData['pageIndex'];
                    pagerOptions.totalPage = pageData['totalPage'];
                    pagerOptions.totalRecord = pageData['totalRecord'];
                    pagerOptions.pageSize = pageData['pageSize'];
                    pager._refresh();
                    editTable._refresh(pageData['datas']);
                }
            });
            return this;
        },
        _load: function () {
            var params = this.options.preSend && this.options.preSend(), pagerOptions = this.pager.options;
            params['pageIndex'] = pagerOptions.currentIndex;
            params['pageSize'] = pagerOptions.pageSize;
            var options = {
                container: this.$form
            };
            params = $.extend(params, api.ui.form(options).toJson());
            this._ajax(params);
            return this;
        },
        _reload: function () {
            this.pager.options.currentIndex = 1;
            return this._load();
        },
        hasEditRow: function () {
            var len = this.jq.find('tr[operateType=edit]').length;
            if(len > 0){
                return true;
            }
            return false;
        },
        _addRow: function () {
            var editTable = this, jq = this.jq, fields = this.options.fields, rowButtons = this.options.rowButtons, $tr = $('<tr></tr>'), hasUnfinishedRow = false;
            jq.find('input[name="id"]').each(function () {
                var inputIdVal = $(this).val();
                if (!inputIdVal) {
                    hasUnfinishedRow = true;
                    return false;
                }
            });
            if (hasUnfinishedRow) {
                var options = {
                    content: '存在未完成的数据行！'
                };
                api.ui.dialog(options).open();
                return this;
            }
            if(this.hasEditRow()){
                var options = {
                    content: '请先完成正在编辑的数据行！'
                };
                api.ui.dialog(options).open();
                return this;
            }
            $.each(fields, function (index, field) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if (field.name == 'id') {
                    $tr.append($td.append($('<span></span>')).append($('<input type="text"/>').attr('name', field.name).css('display', 'none')));
                } else {
                    if(field.type == 'input'){
                        var $span = $('<span class="td-item-span" style="display: none"></span>');
                        var $input = $('<input class="form-control td-item-input" type="text" style="height: 100%; font-size: 15px;"/>');
                        $input.attr('inputDesc', field.inputDesc).attr('name', field.name).attr('required', field.required).css('display', '');
                        $tr.append($td.append($span).append($input));
                    } else if(field.type = 'select'){
                        var $span = $('<span class="td-item-span" style="display: none" selectVal=""></span>');
                        var chosenOptions = field.options, $selector = $('<select class="form-control td-item-select"></select>');
                        $selector.attr('name', field.name).attr('required', field.required);
                        chosenOptions.selector = $selector;
                        api.ui.chosenSelect(chosenOptions);
                        $tr.append($td.append($span).append($selector));
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
                                $tr.find('.td-item-select').css('display', '');
                                $td.find('.btn-cancel').css('display', '');
                                $tr.find('.td-item-span').css('display', 'none');
                                $td.find('.btn-delete').css('display', 'none');
                                $(this).text('确定').attr('textType', 'sure');
                            } else {
                                var message = {}, params = {};
                                $.each($tr.find('.td-item'), function (index, td) {
                                    var $input = $(td).find('input,select');
                                    var inputName = $input.attr('name'), inputValue = $input.val(), required = $input.attr('required');
                                    if (required && (!inputValue || inputValue.trim() == '')) {
                                        message['inputDesc'] = {};
                                        $input.css('border-color', '#FF2F2F');
                                        $input.on('blur',function () {
                                            if($.trim($input.val())){
                                                $input.css('border-color','');
                                            }
                                        })
                                    }
                                    params[inputName] = inputValue;
                                });
                                if (!message.inputDesc) {
                                    $tr.find('.td-item-input').css('display', 'none');
                                    $tr.find('.td-item-select').css('display', 'none');
                                    $td.find('.btn-cancel').css('display', 'none');
                                    $tr.find('.td-item-span').css('display', '');
                                    $td.find('.btn-delete').css('display', '');
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
                                } else {
                                    var options = {
                                        content: '存在未完成的输入项！'
                                    };
                                    api.ui.dialog(options).close();
                                }
                            }
                        });
                        $td.append($button);
                    } else if (type == 'save') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-save" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-save"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            var message = {}, params = {};
                            $.each($tr.find('.td-item'), function (index, td) {
                                var $input = $(td).find('input,select');
                                var inputName = $input.attr('name'), inputValue = $input.val(), required = $input.attr('required');
                                if (required && (!inputValue || inputValue.trim() == '')) {
                                    message['inputDesc'] = {};
                                    $input.css('border-color', '#FF2F2F');
                                    $input.on('blur',function () {
                                        if($.trim($input.val())){
                                            $input.css('border-color','');
                                        }
                                    })
                                }
                                params[inputName] = inputValue;
                            });
                            if (!message.inputDesc) {
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
                            } else {
                                var options = {
                                    content: '存在未完成的输入项！'
                                };
                                api.ui.dialog(options).open();
                            }
                        });
                        $td.append($button);
                    } else if (type == 'delete') {
                        var $button = $('<button class="btn btn-danger btn-sm btn-delete" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-trash"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            var options = {
                                content: '确认删除本条记录？',
                                buttons: [
                                    {
                                        text: '确定', type: 'sure', fn: function () {
                                            var $idInput = $tr.find('input[name="id"]'), inputVal = $idInput.val();
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
                                                        var code = result.code;
                                                        if(code == '-1'){
                                                            var options = {
                                                                content: result.data
                                                            };
                                                            api.ui.dialog(options).open();
                                                        } else {
                                                            editTable._reload();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    {
                                        text: '取消', type: 'cancel'
                                    }
                                ]
                            };
                            api.ui.dialog(options).open();
                        });
                        $td.append($button);
                    } else if(type == 'enter'){

                    } else {
                        var $button = $('<button class="btn btn-info btn-sm" style="margin-left: 10px;" type="button"><span></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text).find('span').addClass(button.icon);
                        $button.on('click', function () {
                            if (button.fn) {
                                var params = {};
                                $tr.find('input,select').each(function () {
                                    params[this.name] = $(this).val();
                                });
                                button.fn(params);
                            }
                        });
                        $td.append($button);
                    }
                });

                var $cancelBtn = $('<button class="btn btn-secondary btn-sm btn-cancel" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;取消</button>').css('display', 'none');
                $cancelBtn.on('click', function () {
                    $.each($tr.find('.td-item'), function (index, td) {
                        var $select = $(td).find('select'), $input = $(td).find('input'), $span = $(td).find('span');
                        if($select.length > 0) {
                            var selectVal = $span.attr('selectVal');
                            $select.val(selectVal);
                        } else {
                            var text = $span.text();
                            $input.val(text);
                        }
                    });

                    $tr.find('.btn-update').attr('textType', 'update');
                    $tr.find('.btn-update').html('<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;编辑');
                    $tr.find('.btn-cancel').css('display', 'none');
                    $tr.find('.td-item-input').css('display', 'none');
                    $tr.find('.td-item-select').css('display', 'none');
                    $tr.find('.td-item-span').css('display', '');
                    $tr.find('.btn-delete').css('display', '');
                    $.each($tr.find('.td-item'), function (index, td) {
                        var inputValue = $(td).find('input').val();
                        $(td).find('span').text(inputValue);
                    });
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
                    $tr.append($td.append($span).append($input));
                } else {
                    if(field.type == 'input'){
                        var $span = $('<span class="td-item-span">' + rowData[field.name] + '</span>');
                        var $input = $('<input class="form-control td-item-input" type="text" style="height: 100%; font-size: 15px;"/>');
                        $input.attr('inputDesc', field.inputDesc).attr('required', field.required).attr('name', field.name).val(rowData[field.name]).css('display', 'none');
                        $tr.append($td.append($span).append($input));
                    } else if(field.type = 'select'){
                        var $span = $('<span class="td-item-span"></span>');
                        var chosenOptions = field.options, $selector = $('<select class="form-control td-item-select"></select>');
                        $selector.attr('name', field.name).attr('required', field.required).css('display', 'none');
                        chosenOptions.selector = $selector;
                        var chosenSelect = api.ui.chosenSelect(chosenOptions);
                        chosenSelect.val(rowData[field.name]);
                        var selected = chosenSelect.selected();
                        $span.attr('selectVal', selected.value).text(selected.text);
                        $tr.append($td.append($span).append($selector));
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
                                if(editTable.hasEditRow()){
                                    var options = {
                                        content: '请先完成正在编辑的数据行！'
                                    };
                                    api.ui.dialog(options).open();
                                    return editTable;
                                }
                                $tr.find('.td-item-input').css('display', '');
                                $tr.find('.td-item-select').css('display', '');
                                $td.find('.btn-cancel').css('display', '');
                                $tr.find('.td-item-span').css('display', 'none');
                                $td.find('.btn-delete').css('display', 'none');
                                $td.find('.btn-enter').css('display', 'none');
                                $tr.find('select').attr('disabled', false);
                                $tr.attr('operateType', 'edit');
                                $(this).html('<span class="glyphicon glyphicon-ok-sign"></span>&nbsp;&nbsp;确定').attr('textType', 'sure');
                            } else {
                                // 数据校验
                                var message = {}, params = {};
                                $.each($tr.find('.td-item'), function (index, td) {
                                    var $input = $(td).find('input,select'), inputName = $input.attr('name'), inputValue = $input.val(), required = $input.attr('required');
                                    if (required && (!inputValue || inputValue.trim() == '')) {
                                        message['inputDesc'] = {};
                                        $input.css('border-color', '#FF2F2F');
                                        $input.on('blur',function () {
                                            if($.trim($input.val())){
                                                $input.css('border-color','');
                                            }
                                        })
                                    }
                                    params[inputName] = inputValue;
                                });
                                if (!message.inputDesc) {
                                    // 调用服务修改数据
                                    if (button.url) {
                                        $.ajax({
                                            url: button.url,
                                            type: 'GET',
                                            data: params,
                                            dataType: 'json',
                                            success: function (result) {
                                                $tr.attr('operateType', '');
                                                editTable._reload();
                                            }
                                        });
                                    }
                                } else {
                                    var options = {
                                        content: '存在未完成的输入项！'
                                    };
                                    api.ui.dialog(options).open();
                                }
                            }
                        });
                        $td.append($button);
                    } else if (type == 'save') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-save" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-save"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text).css('display', 'none');
                        $button.on('click', function () {
                            var message = {}, params = {};
                            $.each($tr.find('.td-item'), function (index, td) {
                                var $input = $(td).find('input,select'), inputName = $input.attr('name'), inputValue = $input.val(), required = $input.attr('required');
                                if (required && (!inputValue || inputValue.trim() == '')) {
                                    message['inputDesc'] = {};
                                    $input.css('border-color', '#FF2F2F');
                                    $input.on('blur',function () {
                                        if($.trim($input.val())){
                                            $input.css('border-color','');
                                        }
                                    })
                                }
                                params[inputName] = inputValue;
                            });

                            if (!message.inputDesc) {
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
                            } else {
                                var options = {
                                    content: '存在未完成的输入项！'
                                };
                                api.ui.dialog(options).open();
                            }
                        });
                        $td.append($button);
                    } else if (type == 'delete') {
                        var $button = $('<button class="btn btn-danger btn-sm btn-delete" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-trash"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            var options = {
                                content: '确认删除本条记录？',
                                buttons: [
                                    {
                                        text: '确定', type: 'sure', fn: function () {
                                            var $idInput = $tr.find('input[name="id"]'), inputVal = $idInput.val();
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
                                                        var code = result.code;
                                                        if(code == '-1'){
                                                            var options = {
                                                                content: result.msg
                                                            };
                                                            api.ui.dialog(options).open();
                                                        } else {
                                                            editTable._reload();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    {
                                        text: '取消', type: 'cancel'
                                    }
                                ]
                            };
                            api.ui.dialog(options).open();
                        });
                        $td.append($button);
                    } else if (type == 'enter') {
                        var $button = $('<button class="btn btn-info btn-sm btn-enter" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-share-alt"></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text);
                        $button.on('click', function () {
                            if (button.fn) {
                                var params = {};
                                $tr.find('input,select').each(function () {
                                    params[this.name] = $(this).val();
                                });
                                button.fn(params);
                            }
                        });
                        $td.append($button);
                    } else {
                        var $button = $('<button class="btn btn-info btn-sm" style="margin-left: 10px;" type="button"><span></span></button>');
                        $button.append('&nbsp;&nbsp;' + button.text).find('span').addClass(button.icon);
                        $button.on('click', function () {
                            if (button.fn) {
                                var params = {};
                                $tr.find('input,select').each(function () {
                                    params[this.name] = $(this).val();
                                });
                                button.fn(params);
                            }
                        });
                        $td.append($button);
                    }
                });
                var $cancelBtn = $('<button class="btn btn-secondary btn-sm btn-cancel" style="margin-left: 10px;" type="button"><span class="glyphicon glyphicon-remove"></span></button>');
                $cancelBtn.append('&nbsp;&nbsp;取消');
                $cancelBtn.css('display', 'none').on('click', function () {
                    $.each($tr.find('.td-item'), function (index, td) {
                        var $select = $(td).find('select'), $input = $(td).find('input'), $span = $(td).find('span');
                        if($select.length > 0) {
                            var selectVal = $span.attr('selectVal');
                            $select.val(selectVal);
                        } else {
                            var text = $span.text();
                            $input.val(text);
                        }
                    });

                    $tr.find('.btn-update').attr('textType', 'update');
                    $tr.find('.btn-update').html('<span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;编辑');
                    $tr.find('.btn-cancel').css('display', 'none');
                    $tr.find('.td-item-input').css('display', 'none');
                    $tr.find('.td-item-select').css('display', 'none');
                    $tr.find('.td-item-span').css('display', '');
                    $tr.find('.btn-delete').css('display', '');
                    $td.find('.btn-enter').css('display', '');
                    $tr.find('select').attr('disabled', true);
                    $tr.attr('operateType', '');
                    $.each($tr.find('.td-item'), function (index, td) {
                        var inputValue = $(td).find('input').val();
                        $(td).find('span').text(inputValue);
                    });

                    $(this).css('display', 'none');
                });
                $td.append($cancelBtn);
                $tr.append($td);
            }
            jq.find('tbody:first').append($tr);
            return this;
        }
    };

    editTable.defaults = {
        container: '',
        width: '100%',
        preSend: function () {
            return {};
        }
    };

    api.ui.editTable = function (options) {
        return new editTable(options);
    }
})(jQuery, window, document);