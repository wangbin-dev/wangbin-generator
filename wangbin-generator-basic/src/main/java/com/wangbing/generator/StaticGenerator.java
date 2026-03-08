package com.wangbing.generator;

import cn.hutool.core.io.FileUtil;
import java.io.File;

public class StaticGenerator {

    public static void main(String[] args) {
        // 获取项目根路径
        String projectPath = System.getProperty("user.dir");
        // 获取整个项目根路径
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(parentFile, "wangbin-generator-demo-projects/acm-template").getAbsolutePath();
        // 输出路径
        String outputPath = parentFile.getAbsolutePath();
        // 复制
        copyFilesByHutool(inputPath, outputPath);
    }



    /**
     * 拷贝文件（Hutool 实现，会将输入目录完整拷贝到输出目录下）
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFilesByHutool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }

}
