<!DOCTYPE html>
<html>
<head>
    <title>FreeMarker模板引擎入门</title>
</head>
<body>
<h1>FreeMarker模板引擎入门</h1>
<ul>
    <#-- 循环渲染导航条 -->
    <#list menuItems as item>
        <li><a href="${item.url}">${item.label}</a></li>
    </#list>
</ul>
<#-- 使用注释部分，不会被输出-->
<footer>
    ${currentYear} FreeMarker模板引擎入门
</footer>
</body>
</html>
