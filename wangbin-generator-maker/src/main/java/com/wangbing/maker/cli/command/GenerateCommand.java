package com.wangbing.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.wangbing.maker.generator.file.FileGenerator;
import com.wangbing.maker.model.DataModel;
import lombok.Data;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {

    @Option(names = {"-l", "--loop"}, arity = "0..1", description = "是否循环", interactive = true, echo = true)
    private boolean loop;

    @Option(names = {"-a", "--author"}, arity = "0..1", description = "作者", interactive = true, echo = true)
    private String author = "yupi";

    @Option(names = {"-o", "--outputText"}, arity = "0..1", description = "输出文本", interactive = true, echo = true)
    private String outputText = "sum = ";

    public Integer call() throws Exception {
        DataModel dataModel = new DataModel();

        // 命令行需要输入的参数如上【loop、author、outputText】
        // Hutool 将命令接收到的属性复制给MainTemplateConfig
        BeanUtil.copyProperties(this, dataModel);

        System.out.println("配置信息：" + dataModel);

        // 调用静态和动态文件生成器封装方法
        FileGenerator.doGenerate(dataModel);

        return 0;
    }
}
