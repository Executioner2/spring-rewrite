package com.spring.beans.factory.config;

import com.spring.beans.factory.BeanFactory;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  18:55
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {

    // 单例模式
    String SCOPE_SINGLETON = "singleton";

    // 原型模式
    String SCOPE_PROTOTYPE = "prototype";

    // 返回依赖此bean的所有bean名称
    String[] getDependentBeans(String beanName);

    // 注册依赖此bean的bean
    void registerDependentBean(String beanName, String dependentBeanName);

    // 添加beanPostProcessor对象
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    // 获取后置处理器数量
    int getBeanPostProcessorCount();

}
