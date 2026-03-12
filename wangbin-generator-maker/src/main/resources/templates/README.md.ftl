# ${name}

> ${description}
>
> 作者：${author}
>

可以通过命令行交互式输入的方式动态生成想要的项目代码

## 使用说明

执行项目根目录下的脚本文件：
示例命令：

generator generate <#list modelConfig.models as modelInfo><#if modelInfo.abbr??>-${modelInfo.abbr}</#if> </#list>

<#-- 递归处理所有 models，包括嵌套的子 models -->
<#macro renderModels models level>
    <#list models as modelInfo>
        <#if modelInfo.type != "group" && modelInfo.type != "MainTemplate">
            ${level + modelInfo?index + 1}）${modelInfo.fieldName}

            类型：${modelInfo.type!""}

            描述：${modelInfo.description!""}

            默认值：${modelInfo.defaultValue?c!""}

            缩写：-${modelInfo.abbr!""}

        <#-- 空行分隔 -->

        <#elseif modelInfo.models??>
        <#-- 处理嵌套的子 models -->
            <h${level + 2}>${modelInfo.groupName}</h${level + 2}>
            <@renderModels models=modelInfo.models level=(level + modelInfo?index + 1)/>
        </#if>
    </#list>
</#macro>

<#-- 调用递归宏，初始层级为0 -->
<@renderModels models=modelConfig.models level=0/>