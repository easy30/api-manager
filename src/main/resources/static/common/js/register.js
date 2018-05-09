$('#registerBtn').on('click',function () {
    var params = {};
    params['userName'] = $('input[name=userName]').val();
    params['account'] = $('input[name=account]').val();
    params['password'] = $('input[name=password]').val();
    $.ajax({
        url: api.util.getUrl('apimanager/user/register'),
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
})