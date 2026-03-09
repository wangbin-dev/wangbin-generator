package com.wangbing.maker.generator.main;

import com.wangbing.maker.meta.Meta;
import com.wangbing.maker.meta.MetaManager;

public class MainGenerator {

    public static void main(String[] args) {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);
    }
}

