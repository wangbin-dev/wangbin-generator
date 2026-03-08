package com.wangbing.generator;

import com.wangbing.model.MainTemplateConfig;
import freemarker.template.*;

import java.io.*;

/**
 * 动态文件生成
 */
public class DynamicGenerator {

    public static void main(String[] args) throws IOException, TemplateException {
        // 路径拼接
        // 获取项目根路径
        String projectPath = System.getProperty("user.dir");
        // 获取整个项目根路径
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(projectPath, "src/main/resources/templates/MainTemplate.java.ftl").getAbsolutePath();
        // 输出路径
        String outputPath = new File(parentFile, "acm-template/src/com/wangbing/acm/MainTemplate.java").getAbsolutePath();

        // 创建模板数据
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("wangbing-dev");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果1：");

        // 生成文件
        doGenerate(inputPath, outputPath, mainTemplateConfig);
    }


    /**
     * 生成文件
     *
     * @param inputPath 模板文件输入路径
     * @param outputPath 输出路径
     * @param model 数据模型
     * @throws IOException IO异常
     * @throws TemplateException Template异常
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 创建模板对象，加载指定模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        // 生成
        Writer out = new FileWriter(outputPath);
        template.process(model, out);

        // 关闭流
        out.close();
    }


}
