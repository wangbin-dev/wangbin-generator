package com.wangbing.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.*;
import java.io.File;
import java.util.List;

@Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    public void run() {
        // 整个项目的根路径
        String projectPath = System.getProperty("user.dir");

        File parentFile = new File(projectPath).getParentFile();

        // 输入路径
        String inputPath = new File(parentFile, "wangbin-generator-demo-projects/acm-template").getAbsolutePath();

        // Hutool 工具类遍历该目录下的所有文件
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }

}
