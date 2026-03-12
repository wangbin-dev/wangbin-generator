package com.wangbing.maker.model;

import lombok.Data;

/**
 * 动态模版配置
 */
@Data
public class DataModel {

    /**
     * 是否生成循环
     */
    public boolean loop = false;

    /**
     * 核心模板
     */
    public MainTemplate mainTemplate;
}

