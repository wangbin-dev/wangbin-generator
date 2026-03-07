package com.wangbing.generator;

import cn.hutool.core.io.FileUtil;
import java.io.File;

public class StaticGenerator {

    public static void main(String[] args) {
        // 获取主项目路径
        String projectPath = System.getProperty("user.dir");
        // 输入路径
        String inputPath = projectPath + File.separator + "wangbin-generator-demo-projects" + File.separator + "acm-template";
        // 输出路径
        String outputPath = projectPath;
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
