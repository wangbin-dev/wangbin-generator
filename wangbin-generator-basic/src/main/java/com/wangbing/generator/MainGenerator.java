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
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("wangbing-dev");
        mainTemplateConfig.setLoop(true);
        mainTemplateConfig.setOutputText("求和sum=：");
        doGenerate(mainTemplateConfig);
    }

    /**
     * 代码封装
     * @param model 数据模型
     * @throws TemplateException Template异常
     * @throws IOException IO异常
     */
    public static void doGenerate(Object model) throws TemplateException, IOException {
        // 1.静态文件生成
        // 获取项目根路径
        String projectPath = System.getProperty("user.dir");
        // 获取整个项目根路径
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(parentFile, "wangbin-generator-demo-projects/acm-template").getAbsolutePath();
        // 输出路径
        String outputPath = parentFile.getAbsolutePath();
        // 生成静态文件
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);

        // 2.动态文件生成
        // 输入路径
        String inputDynamicFilePath = new File(projectPath, "src/main/resources/templates/MainTemplate.java.ftl").getAbsolutePath();
        // 输出路径
        String outputDynamicFilePath = new File(parentFile, "acm-template/src/com/wangbing/acm/MainTemplate.java").getAbsolutePath();
        // 生成动态文件
        DynamicGenerator.doGenerate(inputDynamicFilePath, outputDynamicFilePath, model);
    }

}
