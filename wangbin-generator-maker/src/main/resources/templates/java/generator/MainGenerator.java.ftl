package ${basePackage}.generator;

import com.wangbin.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map; // 新增：导入Map相关包

<#macro generateFile indent fileInfo>
    ${indent}inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
    ${indent}outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
    <#if fileInfo.generateType == "static">
        ${indent}StaticGenerator.copyFilesByHutool(inputPath, outputPath);
    <#else>
    <#-- 关键修改：判断是否是MainTemplate.java.ftl模板，构建包含根层级属性的dataModel -->
        <#if fileInfo.inputPath == "src/com/wangbing/acm/MainTemplate.java.ftl">
            ${indent}// 构建模板渲染上下文，将嵌套属性提升到根层级
            ${indent}Map<String, Object> dataModel = new HashMap<>();
            ${indent}dataModel.put("model", model); // 保留原始model
            ${indent}dataModel.put("author", model.mainTemplate.author); // 根层级author
            ${indent}dataModel.put("outputText", model.mainTemplate.outputText); // 根层级outputText
            ${indent}dataModel.put("loop", model.loop);
            ${indent}dataModel.put("needGit", model.needGit);
            ${indent}DynamicGenerator.doGenerate(inputPath, outputPath, dataModel);
        <#else>
            ${indent}DynamicGenerator.doGenerate(inputPath, outputPath, model);
        </#if>
    </#if>
</#macro>

/**
* 核心生成器
*/
public class MainGenerator {

/**
* 生成
*
* @param model 数据模型
* @throws TemplateException
* @throws IOException
*/
public static void doGenerate(DataModel model) throws TemplateException, IOException {
String inputRootPath = "${fileConfig.inputRootPath}";
String outputRootPath = "${fileConfig.outputRootPath}";

String inputPath;
String outputPath;

<#-- 获取模型变量 -->
<#list modelConfig.models as modelInfo>
<#-- 有分组 -->
    <#if modelInfo.groupKey??>
        <#list modelInfo.models as subModelInfo>
            ${subModelInfo.type} ${subModelInfo.fieldName} = model.${modelInfo.groupKey}.${subModelInfo.fieldName};
        </#list>
    <#else>
        ${modelInfo.type} ${modelInfo.fieldName} = model.${modelInfo.fieldName};
    </#if>
</#list>

<#list fileConfig.files as fileInfo>
    <#if fileInfo.groupKey??>
        // groupKey = ${fileInfo.groupKey}
        <#if fileInfo.condition??>
            if(${fileInfo.condition}) {
            <#list fileInfo.files as fileInfo>
                <@generateFile fileInfo=fileInfo indent="           " />
            </#list>
            }
        <#else>
            <#list fileInfo.files as fileInfo>
                <@generateFile fileInfo=fileInfo indent="        " />
            </#list>
        </#if>
    <#else>
        <#if fileInfo.condition??>
            if(${fileInfo.condition}) {
            <@generateFile fileInfo=fileInfo indent="           " />
            }
        <#else>
            <@generateFile fileInfo=fileInfo indent="        " />
        </#if>
    </#if>
</#list>
}
}