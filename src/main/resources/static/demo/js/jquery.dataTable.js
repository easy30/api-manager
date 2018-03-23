;(function ($) {
    var $table = $('<table class="table table-hover table-sm" style="font-size: 15px;"></table>');
    $.fn.extend({
        dataTable: function (options) {
            var defaults = {
                headers: [
                    {text: '部门编号', width: '20%'},
                    {text: '部门名称', width: '20%'},
                    {text: '部门简述', width: '40%'},
                    {text: '操作', width: '20%'}
                ],
                form: 'form',
                fields: ['id', 'depName', 'depDesc'],
                defaultButtons: [
                    {type: 'update', text: '编辑', url: '/apimanager/department/update'},
                    {type: 'save', text: '保存', url: '/apimanager/department/add'},
                    {type: 'delete', text: '删除', url: '/apimanager/department/delete'}
                ],
                url: '/apimanager/department/findPage',
                width: 900
            };
            //当前节点对象
            var jq = this.jq = this;
            var conf = this.conf = $.extend({}, defaults, options);
            // 设置表格样式
            $table.css("width", conf.width + 'px');

            // 表格数据开始
            var data = conf.data;
            if (data) {
                this.build(data);
            } else {
                this.load();
            }
            return this;
        },
        load: function (params) {
            var jq = this.jq;
            $.ajax({
                url: this.conf.url,
                type: 'GET',
                data: params,
                dataType: 'json',
                success: function (result) {
                    var data = result['data']['datas'];
                    jq.build(data);
                }
            });
        },
        reload: function () {
            var $form = this.form = ('string' == typeof this.conf.form) ? $(this.conf.form) : this.conf.form;
            var params = $form.serializeObject();
            this.load(params);
        },
        showRow: function ($tBody, value) {
            var jq = this.jq;
            var fields = jq.conf.fields;
            var defaultButtons = jq.conf.defaultButtons;
            var $tr = $('<tr></tr>');
            $.each(fields, function (index, fieldName) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if (fields[index] == 'id') {
                    var $span = $('<span>' + value[fieldName] + '</span>');
                    var $input = $('<input type="text"/>').attr('name', fields[index]).val(value[fieldName]).css('display', 'none');
                    $tr.append($td.append($span).append($input));
                } else {
                    var $span = $('<span class="td-item-span">' + value[fieldName] + '</span>');
                    var $input = $('<input class="form-control td-item-input" type="text" style="height: 30px;"/>').attr('name', fields[index]).val(value[fieldName]).css('display', 'none');
                    $tr.append($td.append($span).append($input));
                }
            });

            if (defaultButtons) {
                var $td = $('<td class="td-button" style="padding-left: 10px;"></td>');
                $.each(defaultButtons, function (index, button) {
                    var type = button.type;
                    if (type == 'update') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-update" type="button" textType="update"></button>').text(button.text);
                        $button.on('click', function () {
                            var textType = $button.attr('textType');
                            if(textType == 'update'){
                                $tr.find('.td-item-input').css('display', '');
                                $tr.find('.td-item-span').css('display', 'none');
                                $td.find('.btn-delete').css('display', 'none');
                                $td.find('.btn-cancel').css('display', '');
                                $button.text('确定');
                                $button.attr('textType', 'sure');
                            } else {
                                $tr.find('.td-item-input').css('display', 'none');
                                $tr.find('.td-item-span').css('display', '');
                                $td.find('.btn-delete').css('display', '');
                                $td.find('.btn-cancel').css('display', 'none');

                                var $row = $button.parents('tr:first');
                                var params = {};
                                $.each($row.find('.td-item'), function (index, td) {
                                    // 获取输入域的值
                                    var inputName = $(td).find('input').attr('name');
                                    var inputValue = $(td).find('input').val();
                                    params[inputName] = inputValue;
                                })

                                // 调用服务修改数据
                                if (button.url) {
                                    $.ajax({
                                        url: button.url,
                                        type: 'GET',
                                        data: params,
                                        dataType: 'json',
                                        success: function (result) {
                                            jq.reload();
                                        }
                                    });
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if(type == 'save'){
                        var $button = $('<button class="btn btn-primary btn-sm btn-update" type="button"></button>').text(button.text).css('display', 'none');
                        $button.on('click', function () {
                            var $row = $button.parents('tr:first');
                            var params = {};
                            $.each($row.find('.td-item'), function (index, td) {
                                // 获取输入域的值
                                var inputName = $(td).find('input').attr('name');
                                var inputValue = $(td).find('input').val();
                                params[inputName] = inputValue;
                            })

                            // 调用服务保存数据
                            if (button.url) {
                                $.ajax({
                                    url: button.url,
                                    type: 'GET',
                                    data: params,
                                    dataType: 'json',
                                    success: function (result) {
                                        jq.reload();
                                    }
                                });
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'delete') {
                        var $button = $('<button class="btn btn-danger btn-sm btn-delete" type="button"></button>').text(button.text);
                        $button.click(function () {
                            var msg = "确认删除？";
                            if (confirm(msg)) {
                                var $row = $button.parents('tr:first');
                                var $idInput = $row.find('input[name="id"]');
                                var inputVal = $idInput.val();
                                if(!inputVal){
                                    $row.remove();
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
                                            jq.reload();
                                        }
                                    });
                                }
                            }
                        })
                        $td.append($button);
                    }
                })
                var $cancel = $('<button class="btn btn-info btn-sm btn-cancel" type="button"></button>').text('取消').css('display', 'none');
                $cancel.on('click', function () {
                    var $row = $(this).parents('tr:first');
                    $.each($row.find('.td-item'), function (index, td) {
                        var text = $(td).find('span').text();
                        $(td).find('input').val(text);
                    })

                    $row.find('.btn-update').attr('textType', 'update');
                    $row.find('.btn-update').text('编辑');
                    $row.find('.td-item-input').css('display', 'none');
                    $row.find('.td-item-span').css('display', '');
                    $row.find('.btn-delete').css('display', '');
                    $row.find('.btn-cancel').css('display', 'none');
                    $.each($row.find('.td-item'), function (index, td) {
                        var inputValue = $(td).find('input').val();
                        $(td).find('span').text(inputValue);
                    })
                    $row.find('.btn-delete').css('display', '');
                    $(this).css('display', 'none');
                });
                $td.append($cancel);
                $tr.append($td);
            }
            $tBody.append($tr);
        },
        addRow: function ($tBody) {
            var jq = this.jq;
            var fields = jq.conf.fields;
            var defaultButtons = jq.conf.defaultButtons;
            var $tr = $('<tr></tr>');
            $.each(fields, function (index, fieldName) {
                var $td = $('<td class="td-item" style="padding-left: 10px;"></td>');
                if (fields[index] == 'id') {
                    var $span = $('<span></span>');
                    var $input = $('<input type="text"/>').attr('name', fields[index]).css('display', 'none');
                    $tr.append($td.append($span).append($input));
                } else {
                    var $span = $('<span class="td-item-span" style="display: none"></span>');
                    var $input = $('<input class="form-control td-item-input" type="text" style="height: 30px;"/>').attr('name', fields[index]).css('display', '');
                    $tr.append($td.append($span).append($input));
                }
            });

            if (defaultButtons) {
                var $td = $('<td class="td-button" style="padding-left: 10px;"></td>');
                $.each(defaultButtons, function (index, button) {
                    var type = button.type;
                    if (type == 'update') {
                        var $button = $('<button class="btn btn-primary btn-sm btn-update" type="button" textType="update"></button>').css('display', 'none').text(button.text);
                        $button.on('click', function () {
                            var textType = $button.attr('textType');
                            if(textType == 'update'){
                                $tr.find('.td-item-input').css('display', '');
                                $tr.find('.td-item-span').css('display', 'none');
                                $td.find('.btn-delete').css('display', 'none');
                                $td.find('.btn-cancel').css('display', '');
                                $button.text('确定');
                                $button.attr('textType', 'sure');
                            } else {
                                $tr.find('.td-item-input').css('display', 'none');
                                $tr.find('.td-item-span').css('display', '');
                                $td.find('.btn-delete').css('display', '');
                                $td.find('.btn-cancel').css('display', 'none');

                                var $row = $button.parents('tr:first');
                                var params = {};
                                $.each($row.find('.td-item'), function (index, td) {
                                    // 获取输入域的值
                                    var inputName = $(td).find('input').attr('name');
                                    var inputValue = $(td).find('input').val();
                                    params[inputName] = inputValue;
                                })

                                // 调用服务修改数据
                                if (button.url) {
                                    $.ajax({
                                        url: button.url,
                                        type: 'GET',
                                        data: params,
                                        dataType: 'json',
                                        success: function (result) {
                                            jq.reload();
                                        }
                                    });
                                }
                            }
                        });
                        $td.append($button);
                    }
                    if(type == 'save'){
                        var $button = $('<button class="btn btn-primary btn-sm btn-save" type="button"></button>').text(button.text);
                        $button.on('click', function () {
                            var $row = $button.parents('tr:first');
                            var params = {};
                            $.each($row.find('.td-item'), function (index, td) {
                                // 获取输入域的值
                                var inputName = $(td).find('input').attr('name');
                                var inputValue = $(td).find('input').val();
                                params[inputName] = inputValue;
                            })

                            // 调用服务保存数据
                            if (button.url) {
                                $.ajax({
                                    url: button.url,
                                    type: 'GET',
                                    data: params,
                                    dataType: 'json',
                                    success: function (result) {
                                        jq.reload();
                                    }
                                });
                            }
                        });
                        $td.append($button);
                    }
                    if (type == 'delete') {
                        var $button = $('<button class="btn btn-danger btn-sm btn-delete" type="button"></button>').text(button.text);
                        $button.click(function () {
                            var msg = "确认删除？";
                            if (confirm(msg)) {
                                var $row = $button.parents('tr:first');
                                var $idInput = $row.find('input[name="id"]');
                                var inputVal = $idInput.val();
                                if(!inputVal){
                                    $row.remove();
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
                                            jq.reload();
                                        }
                                    });
                                }
                            }
                        })
                        $td.append($button);
                    }
                })
                var $cancel = $('<button class="btn btn-info btn-sm btn-cancel" type="button"></button>').text('取消').css('display', 'none');
                $cancel.on('click', function () {
                    var $row = $(this).parents('tr:first');
                    $.each($row.find('.td-item'), function (index, td) {
                        var text = $(td).find('span').text();
                        $(td).find('input').val(text);
                    })

                    $row.find('.btn-update').attr('textType', 'update');
                    $row.find('.btn-update').text('编辑');
                    $row.find('.td-item-input').css('display', 'none');
                    $row.find('.td-item-span').css('display', '');
                    $row.find('.btn-delete').css('display', '');
                    $row.find('.btn-cancel').css('display', 'none');
                    $.each($row.find('.td-item'), function (index, td) {
                        var inputValue = $(td).find('input').val();
                        $(td).find('span').text(inputValue);
                    })
                    $row.find('.btn-delete').css('display', '');
                    $(this).css('display', 'none');
                });
                $td.append($cancel);
                $tr.append($td);
            }
            $tBody.append($tr);
        },
        build: function (data) {
            $table.empty();
            var jq = this.jq;
            var $tBody = $('<tbody></tbody>');
            var fields = this.conf.fields;
            var defaultButtons = this.conf.defaultButtons;
            var headers = this.conf.headers;
            // 设置表头
            if (headers) {
                var $tr = $('<tr style="background-color: #e3e3e3;"></tr>');
                $.each(headers, function (index, value) {
                    var $th = $('<th style="padding-left: 10px;height: 10px;"></th>').css('width', value.width);
                    $tr.append($th.append(value.text));
                });
                $tBody.append($tr);
            }

            // 数据开始
            $.each(data, function (index, value) {
                jq.showRow($tBody, value);
            });

            var $addBtn = $('<button class="btn btn-info btn-sm btn-add" type="button" style="width: 80px;"></button>');
            $addBtn.html('<span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;添 加')
                   .click(function () {
                        jq.addRow($tBody);
                    });
            var $tFoot = $('<tfoot></tfoot>').append($addBtn);
            return this.append($table.append($tBody).append($tFoot));
        }
    });
})(jQuery);