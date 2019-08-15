function lang_text() {/*
separator= ,
hello=Hello,您好
id=ID,编号
apiName=API Name,接口名称

level=Level,级别
status=Status,状态

creator=Creator,创建者
view=View,查看
editor=Editor,修改者
operation=Operation,操作
add=Add,添加
edit=Edit,编辑
delete=Delete,删除
cancel=Cancel,取消
save=Save,保存

lastmodified=Lastmodified,修改时间
deleteConfirm=Really delete?,真的要删除吗？

firstPage=First,首页
prevPage=Prev,上页
nextPage=Next,下页
lastPage=Last,末页
pages=Pages,页
search=Search,搜索

department=Department,部门
projiect=Project,项目
module=Module,模块
api=API,接口

apiConfig=API Config,接口定义
testCase=Test Case,测试用例
description=Description,描述
desc=Description,描述
name=Name,名称
url=URL,链接
successResponse=Success Response,成功返回
failResponse=Fail Response,失败返回
sample=Sample,例子
addParameter=Add Parameter,添加参数
clearParameter=Clear Parameter,清空参数
type=Type,类型
project=Project,项目
*/}
function lang_trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}
function lang_parse() {
    var text= lang_text.toString().replace(/^[^\/]+\/\*!?\s?/, '').replace(/\*\/[^\/]+$/, '');

    var list=text.split(/[\r\n]+/);
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
            var v=value.split(",");

            ens[key]=v[0];
            cns[key]=v[1];

        }
    }
    return {"en":ens,"cn":cns};
}

var lang=lang_parse();
export default lang;