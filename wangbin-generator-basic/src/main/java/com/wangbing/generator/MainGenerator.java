package com.wangbing.generator;

import com.wangbing.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;


/**
 * 静态和动态文件生成器（核心生成器）
 */
public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        // 1.静态文件生成
        // 获取主项目路径
        String projectPath = System.getProperty("user.dir");
        // 输入路径
        String inputPath = projectPath + File.separator + "wangbin-generator-demo-projects" + File.separator + "acm-template";
        // 输出路径
        String outputPath = projectPath;
        // 复制
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);

        // 2.动态文件生成
        // 路径拼接
        String dynamicInputPath = projectPath + File.separator + "wangbin-generator-basic" + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/wangbing/acm/MainTemplate.java";

        // 创建模板数据
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("wangbing-dev");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        DynamicGenerator.doGenerate(dynamicInputPath, dynamicOutputPath, mainTemplateConfig);

    }


}
