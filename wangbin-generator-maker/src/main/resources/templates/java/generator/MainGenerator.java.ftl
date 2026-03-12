package ${basePackage}.generator;

import com.wangbin.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 静态和动态文件生成器（核心生成器）
 */
public class MainGenerator {

    /**
     * 生成
     *
     * @param model 数据模型
     * @throws TemplateException Template异常
     * @throws IOException IO异常
     */
    public static void doGenerate(DataModel model) throws TemplateException, IOException {
        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";

        String inputPath;
        String outputPath;

    <#list modelConfig.models as modelInfo>
        ${modelInfo.type} ${modelInfo.fieldName} = model.${modelInfo.fieldName};
    </#list>

	<#list fileConfig.files as fileInfo>
        <#if fileInfo.condition??>
        if(${fileInfo.condition}) {
            inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
            outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
            <#if fileInfo.generateType == "static">
            StaticGenerator.copyFilesByHutool(inputPath, outputPath);
            <#else>
            DynamicGenerator.doGenerate(inputPath, outputPath, model);
            </#if>
        }
        <#else>
        inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
        outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
        <#if fileInfo.generateType == "static">
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
        <#else>
        DynamicGenerator.doGenerate(inputPath, outputPath, model);
        </#if>
        </#if>
    </#list>
    }
}
