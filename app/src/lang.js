var lang_text=
`separator= ;
hello=Hello;您好
id=ID;编号
address=Address;地址
arrayItemParam=Array Item Parameter;数组成员参数
apiName=API Name;接口名称
back=Back;返回
changeConfirm=Data changed, continue to leave?;数据有修改，要离开吗？
copy=Copy;复制
deleteConfirm=Really delete?;真的要删除吗？
document=Document;文档
export=Export;导出
homePage=Home;主页
import=Import;导入
level=Level;级别
login=Login;登录
logout=Logout;退出
inputSaveName=please input save name;请输入保存名称
normalParam=Normal Parameter;普通参数
object=Object;对象
ok=OK;确定
password=Password;密码
passwordRequire=password require;密码不可为空
SaveSuccess=successfully saved;保存成功
server=Server;服务器
username=Username;用户名
usernameRequire=username require;账号不可为空




status=Status;状态

creator=Creator;创建者
view=View;查看
editor=Editor;修改者
operation=Operation;操作
add=Add;添加
edit=Edit;编辑
delete=Delete;删除
cancel=Cancel;取消
save=Save;保存
require=require;必填
mockRule=Moke Rule;Mock规则

lastmodified=Lastmodified;修改时间


field=Field;字段
firstPage=First;首页
prevPage=Prev;上页
nextPage=Next;下页
lastPage=Last;末页
pages=Pages;页
search=Search;搜索
record=records;条


department=Department;部门
projiect=Project;项目
module=Module;模块
api=API;接口

apiConfig=API Config;接口定义
testCase=Test Case;测试用例
description=Description;描述
desc=Description;描述
name=Name;名称
url=URL;链接
successResponse=Success Response;成功返回
fail=Fail;失败
success=Success;成功
failResponse=Fail Response;失败返回
sample=Sample;例子
addParameter=Add Parameter;添加参数
clearParameter=Clear Parameter;清空参数
type=Type;类型
project=Project;项目
validate=Validate;校验

develop=Develop;开发中
complete=Complete;已完成
pleaseSelect=Please Select;请选择`;
function lang_trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}
function lang_parse() {
    //var text= lang_text.toString().replace(/^[^/]+\/\*!?\s?/, '').replace(/\*\/[^\/]+$/, '');

    var list=lang_text.split(/[\r\n]+/);
    var ens={};
    var cns={};
    for(var i in list){
        var line=list[i];
        if(lang_trim(line)=="") continue;
        var n=line.indexOf("=");
        if(n==-1){

            ens[line]="";
            cns[line]="";
        }else{
            var key=lang_trim(line.substring(0,n));
            var value=line.substring(n+1);
            var v=value.split(";");

            ens[key]=v[0];
            cns[key]=v[1];

        }
    }
    return {"en":ens,"cn":cns};
}

var lang=lang_parse();
export default lang;