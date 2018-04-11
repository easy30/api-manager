$('#loginBtn').on('click', function () {
    var account = $('[name=account]').val(), password = $('[name=password]').val(), params = {};
    if(!account) {
        $('#msg').text('账号不能为空！');
        return;
    }
    if(!password){
        $('#msg').text('密码不能为空！');
        return;
    }
    params['account'] = account;
    params['password'] = password;
    $.ajax({
        url: api.util.getUrl('apimanager/user/login'),
        type: 'GET',
        data: params,
        dataType: 'json',
        success: function (result) {
            var code = result.code, msg = result.msg;
            if(code == '-1'){
                $('#msg').text(msg);
            } else {
                window.location.href = 'index.html';
            }
        }
    });
});