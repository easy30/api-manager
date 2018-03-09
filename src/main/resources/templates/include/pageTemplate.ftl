<#ftl encoding="utf-8">
<#assign updateDate='20170728001' />
<#include "constants.ftl">
<#macro page title="manage" customerCss=[] customerJs=[] framworkCss=[] framworkJs=[]>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="rootApp">
    <#include "head.ftl">
    <#include "detail-menu.ftl">
<body>
    <#nested>

    <#include "foot.ftl">
    <#include "foot-meta.ftl">
</body>
</html>
</#macro>