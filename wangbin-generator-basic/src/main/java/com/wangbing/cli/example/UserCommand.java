package com.wangbing.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "user-cli", mixinStandardHelpOptions = true, version = "1.0",
        description = "用户信息管理工具（自动补充必填选项触发交互）")
public class UserCommand implements Runnable {

    // 必填选项：用户名（开启交互式输入）
    @Option(names = {"-u", "--username"}, required = true, interactive = true,
            arity = "0..1", description = "登录用户名（必填）")
    private String username;

    // 必填选项：密码（开启交互式输入）
    // 关键修复：添加 arity = "0..1"，表示该选项可以没有值，从而触发交互
    @Option(names = {"-p", "--password"}, required = true, interactive = true,
            arity = "0..1", description = "登录密码（必填）")
    private String password;

    // 非必填选项：是否记住密码
    @Option(names = {"-r", "--remember"}, description = "是否记住密码（默认：false）")
    private boolean remember = false;

    @Override
    public void run() {
        // 业务逻辑：输出用户输入的信息
        System.out.println("\n===== 执行结果 =====");
        System.out.println("用户名：" + username);
        System.out.println("密码：" + password);
        System.out.println("记住密码：" + remember);
    }

    public static void main(String[] args) {
        UserCommand command = new UserCommand();

        // 核心步骤：自动补充缺失的必填选项
        String[] processedArgs = RequiredOptionAutoFiller.fillMissingRequiredOptions(command, args);

        // 执行 picocli 命令
        int exitCode = new CommandLine(command).execute(processedArgs);
        System.exit(exitCode);
    }
}