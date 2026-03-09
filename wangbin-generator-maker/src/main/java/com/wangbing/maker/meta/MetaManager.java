package com.wangbing.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * 元信息管理类
 * 核心职责：提供元信息（Meta）的全局唯一实例
 * 设计模式：单例模式双检锁
 */
public class MetaManager {

    /**
     * 元信息核心实例
     * 关键字volatile：
     * 1. 禁止指令重排序（防止new Meta()时"半初始化"对象被其他线程读取）
     * 2. 保证多线程间的内存可见性（一个线程修改后，其他线程能立即看到最新值）
     */
    private static volatile Meta meta;

    /**
     * 私有构造函数
     * 设计目的：
     * 1. 防止外部通过new关键字实例化此类，保证单例的唯一性
     * 2. 防止子类继承后破坏单例（若类被final修饰会更严谨，此处可作为面试优化点）
     */
    private MetaManager() {
        // 私有构造函数，防止外部实例化
    }

    /**
     * 获取元信息单例对象（双检锁DCL实现）
     * 设计思路：
     * 1. 懒加载：仅在首次调用时初始化，节省内存资源
     * 2. 双检锁：外层非空判断避免每次获取实例都加锁（提升性能），内层非空判断防止多线程并发初始化
     * @return 全局唯一的Meta实例
     */
    public static Meta getMetaObject() {
        // 一重检查：非加锁判断，避免每次调用都进入同步块（性能优化）
        // 注意：此处必须配合volatile，否则可能读取到"半初始化"的meta对象
        if (meta == null) {
            // 类对象锁：保证多线程下仅能有一个线程进入初始化逻辑
            // 锁对象选择：使用Class对象而非任意对象，保证锁的唯一性（类级锁）
            synchronized (MetaManager.class) {
                // 二重检查：防止多个线程等待锁后重复初始化
                if (meta == null) {
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    /**
     * 初始化元信息（私有静态方法，仅内部调用）
     * 核心逻辑：读取配置文件 → JSON反序列化 → 生成Meta对象
     * @return 初始化后的Meta对象
     */
    private static Meta initMeta() {
        // 读取classpath下的meta.json配置文件（Hutool工具类简化IO操作）
        // 风险点：若文件不存在，会抛出IO异常，此处未捕获
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        // JSON反序列化为Meta对象（依赖Hutool的JSON工具，底层基于反射）
        // 风险点：若JSON字段与Meta类不匹配，会抛出反序列化异常
        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        // todo 校验和处理默认值
        // 面试考察：此处应补充必填字段校验（如newMeta.getXXX() != null）、默认值填充逻辑
        return newMeta;
    }
}