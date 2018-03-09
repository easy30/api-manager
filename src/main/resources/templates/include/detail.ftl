<#ftl encoding="utf-8">
<#include "constants.ftl">
<#macro page title="" keywords="" description="" customerCss=[] customerJs=[] specificLib=[]>
<!DOCTYPE html>
<html lang="zh-CN">
    <#include "head.ftl">
<body>
    <#include "detail-menu.ftl">
    <#nested>
    <#include "foot.ftl">
    <#include "foot-meta.ftl">
</body>
</html>
</#macro>