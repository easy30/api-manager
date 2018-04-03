var options = {
    container: '#departmentTable',
    headers: [
        {text: '部门编号', width: '10%'},
        {text: '部门名称', width: '20%'},
        {text: '所属部门', width: '20%'},
        {text: '部门简述', width: '30%'},
        {text: '操作', width: '30%'}
    ],
    form: '#form',
    fields: [
        {name: 'id', type: 'input', inputDesc: '部门编号', required: false},
        {name: 'depName', type: 'input', inputDesc: '部门名称', required: true},
        {
            name: 'depId', type: 'select', inputDesc: '所属部门', required: true, options: {
                optionField: {value: 'id', text: 'depName'},
                url: '/apimanager/department/list'
            }
        },
        {name: 'depDesc', type: 'input', inputDesc: '部门描述', required: true}
    ],
    rowButtons: [
        {type: 'update', text: '编辑', url: '/apimanager/department/update'},
        {type: 'save', text: '保存', url: '/apimanager/department/add'},
        {type: 'delete', text: '删除', url: '/apimanager/department/delete'},
        {
            type: 'test', text: '测试', icon: 'glyphicon glyphicon-tasks', fn: function () {
                alert('测试');
            }},
        {
            type: 'enter', text: '进入', fn: function () {
                alert("进入");
            }
        }
    ],
    headBtn: [
        {
            type: 'add', text: '添加', fn: function (row) {
                row.find('select').val(81);
            }
        },
        {
            type: 'create', text: '新增接口', icon: 'glyphicon glyphicon-plus', fn: function () {
                alert();
            }
        }
    ],
    url: '/apimanager/department/findPage'
};
api.ui.editTable(options);
