package com.wangbing.model;

import freemarker.template.*;

import java.io.*;

/**
 * 动态文件生成
 */
public class DynamicGenerator {

    public static void main(String[] args) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 项目路径拼接
        String projectPath = System.getProperty("user.dir") + File.separator + "wangbin-generator-basic";
        File parentFile = new File(projectPath);
        File file = new File(parentFile, "src/main/resources/templates");

        // 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(file);


        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("MainTemplate.java.ftl");

        // 创建数据模型
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("wangbing-dev");

        // 不使用循环
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("总和：");

        // 生成
        Writer out = new FileWriter("MainTemplate.java");
        template.process(mainTemplateConfig, out);

        // 关闭流
        out.close();
    }

}
