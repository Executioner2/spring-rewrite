package com.spring.beans.factory.config;

public interface SingletonBeanRegistry {
    // 注册单例对象
    void registerSingleton(String beanName, Object singletonObject);

    // 获取单例对象
    Object getSingleton(String beanName);

    // 检查一级缓存中是否存在此单例对象
    boolean containsSingleton(String beanName);

    // 返回所有已经注册了的单例对象名称
    String[] getSingletonNames();

    // 已经注册了的单例对象数量
    int getSingletonCount();

    // 返回此单例对象的互斥锁
    Object getSingletonMutex();
}
