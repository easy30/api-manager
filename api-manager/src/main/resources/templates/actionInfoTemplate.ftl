<#ftl encoding="utf-8">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <style type="text/css">
        body {
            margin-left: 45px;
            margin-right: 45px;
            font-family: Arial Unicode MS;
            font-size: 10px;
        }

        table {
            margin: auto;
            width: 100%;
            border-collapse: collapse;
            border: 1px solid #444444;
        }

        th, td {
            border: 1px solid #444444;
            font-size: 10px;
            margin-left: 5px;
        }

        .title {
            text-align: center;
            font-weight: bold;
            font-size: 20px;
        }
    </style>
</head>
<body>
<div>
    <div class="title">
        <p>接口说明文档</p>
    </div>
    <p></p>
    <div>
        <table>
            <caption style="font-size: larger">接口基本信息</caption>
            <tr>
                <td>接口名称</td>
                <td>${actionName}</td>
            </tr>
            <tr>
                <td>接口描述</td>
                <td>${actionDesc}</td>
            </tr>
            <tr>
                <td>接口地址</td>
                <td>${actionUrl}</td>
            </tr>
            <tr>
                <td>请求类型</td>
                <td>${method}</td>
            </tr>
        </table>
    </div>
    <p></p>
    <div class="action_params_info">
        <#if requestHeadParams?exists>
            <table class="action_request_head_params_info_table">
                <caption style="font-size: larger">请求头参数</caption>
                <thead>
                <tr>
                    <th>参数名称</th>
                    <th>数据类型</th>
                    <th>备注</th>
                </tr>
                </thead>
                <#list requestHeadParams as params>
                    <tr>
                        <td width="25%">${params.name}</td>
                        <td width="25%">${params.type}</td>
                        <td width="25%">${params.remark}</td>
                    </tr>
                </#list>
            </table>
        </#if>
        <p></p>
        <#if requestParams?exists>
            <table class="action_request_params_info_table">
                <caption style="font-size: larger">请求参数</caption>
                <thead>
                <tr>
                    <th>参数名称</th>
                    <th>数据类型</th>
                    <th>是否必填</th>
                    <th>备注</th>
                </tr>
                </thead>
                <#list requestParams as params>
                    <tr>
                        <td width="25%">${params.name}</td>
                        <td width="25%">${params.type}</td>
                        <td width="25%">${params.required}</td>
                        <td width="25%">${params.remark}</td>
                    </tr>
                </#list>
            </table>
        </#if>
        <p></p>
        <#if responseParams?exists>
            <table class="action_response_params_info_table">
                <caption style="font-size: larger">响应参数</caption>
                <thead>
                <tr>
                    <th>参数名称</th>
                    <th>数据类型</th>
                    <th>备注</th>
                </tr>
                </thead>
                <#list responseParams as params>
                    <tr>
                        <td width="25%">${params.name}</td>
                        <td width="25%">${params.type}</td>
                        <td width="25%">${params.remark}</td>
                    </tr>
                </#list>
            </table>
        </#if>
        <p></p>
        <#if responseJson?exists>
            <p style="font-size: larger;">接口返回举例：</p>
            <pre>${responseJson}</pre>
        </#if>
    </div>
</div>
</body>
</html>
