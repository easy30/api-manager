$('#registerBtn').on('click',function () {
   var params = {};
   params['userName'] = $('input[name=userName]').val();
   params['account'] = $('input[name=account]').val();
   params['password'] = $('input[name=password]').val();
   //表单非空校验
   var flag = false;
   $.each(params, function (name,value) {
       if(!$.trim(value)){
           flag = true;
           if (name=='userName') {
               $('#msg').text('中文名称不能为空');
           }
           if (name=='account') {
               $('#msg').text('账号不能为空');
           }
           if (name=='password'){
               $('#msg').text('密码不能为空');
           }
           return false;
       }
   })
    if (!flag) {
        $.ajax({
            url: api.util.getUrl('apimanager/user/register'),
            type: 'GET',
            data: params,
            dataType: 'json',
            success: function (result) {
                var code = result.code, msg = result.msg;
                if(code == '0'){
                    window.location.href = 'index.html';
                } else {
                    $('#msg').text(msg);
                }
            }
        });
    }
})