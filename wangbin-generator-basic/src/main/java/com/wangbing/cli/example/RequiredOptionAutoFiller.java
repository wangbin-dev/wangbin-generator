package com.wangbing.cli.example;

import picocli.CommandLine.Option;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class RequiredOptionAutoFiller {

    public static String[] fillMissingRequiredOptions(Object command, String[] originalArgs) {
        if (command == null || originalArgs == null) {
            return originalArgs == null ? new String[0] : originalArgs.clone();
        }

        // 步骤1：通过反射获取所有必填的选项名称（@Option(required=true)），并保持字段定义顺序
        List<String> requiredOptionNames = getRequiredOptionNamesInOrder(command);
        if (requiredOptionNames.isEmpty()) {
            return originalArgs.clone();
        }

        // 步骤2：检查原始参数中是否包含这些必填选项
        List<String> argsList = new ArrayList<>(Arrays.asList(originalArgs));
        List<String> missingOptions = findMissingOptionsInOrder(argsList, requiredOptionNames);

        // 步骤3：补充缺失的必填选项（触发交互式输入）
        if (!missingOptions.isEmpty()) {
            argsList.addAll(missingOptions);
            System.out.println("检测到缺失必填选项：" + missingOptions + "，自动补充以触发交互式输入");
        }

        return argsList.toArray(new String[0]);
    }

    /**
     * 反射解析命令类中所有 @Option(required=true) 的选项名称，并保持字段定义的顺序
     */
    private static List<String> getRequiredOptionNamesInOrder(Object command) {
        List<String> requiredOptions = new ArrayList<>();
        Class<?> commandClass = command.getClass();

        // 遍历所有字段，保持定义顺序
        for (Field field : commandClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Option.class)) {
                Option option = field.getAnnotation(Option.class);
                if (option.required()) {
                    String[] names = option.names();
                    if (names.length > 0) {
                        String optionName = Arrays.stream(names)
                                .filter(name -> name.startsWith("--"))
                                .findFirst()
                                .orElse(names[0]);
                        requiredOptions.add(optionName);
                    }
                }
            }
        }

        return requiredOptions;
    }

    /**
     * 查找原始参数中缺失的必填选项，并保持原有顺序
     */
    private static List<String> findMissingOptionsInOrder(List<String> argsList, List<String> requiredOptions) {
        Set<String> existingOptions = argsList.stream()
                .filter(arg -> arg.startsWith("-"))
                .collect(Collectors.toSet());

        return requiredOptions.stream()
                .filter(option -> !existingOptions.contains(option))
                .collect(Collectors.toList());
    }
}