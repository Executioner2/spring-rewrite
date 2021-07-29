package com.spring.beans.factory.config;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:13
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface BeanDefinition {

    // 是否为bean定义属性值
    boolean hasPropertyValues();

    // 是否是单例bean
    boolean isSingleton();

    // 获取原始单例bean定义
    BeanDefinition getOriginatingBeanDefinition();

    // 设置bean的classname
    void setBeanClassName(String className);

    // 获取bean的classname
    String getBeanClassName();

    // 设置实例化模式
    void setScope(String scope);

    // 获取实例化模式
    String getScope();

    // 设置是否懒加载
    void setLazyInit(boolean lazyInit);

    // 是否是懒加载
    boolean isLazyInit();

    // 设置属性中有那些依赖的bean
    void setDependsOn(String ...dependsOn);

    // 取出所有依赖的bean
    String[] getDependsOn();

}
