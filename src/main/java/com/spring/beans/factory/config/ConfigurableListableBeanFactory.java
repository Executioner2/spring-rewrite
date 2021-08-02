package com.spring.beans.factory.config;

import java.util.Iterator;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:35
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory{
    // 获取beanDefinition
    BeanDefinition getBeanDefinition(String beanName);

    // 获取beanDefinition的迭代器
    Iterator<String> getBeanNamesIterator();

    // 实例化剩余所有非懒加载单例bean
    void preInstantiateSingletons();
}
