package com.spring.beans.factory.support;


import com.spring.beans.factory.config.BeanDefinition;

import java.util.Iterator;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  21:47
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：bean定义注册
 */
public interface BeanDefinitionRegistry {
    // 注册bean定义
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    // 移除bean定义
    void removeBeanDefinition(String beanName);

    // 获取bean定义
    BeanDefinition getBeanDefinition(String beanName);

    // 判断是否包含bean定义
    boolean containsBeanDefinition(String beanName);

    // 获取所有bean定义
    String[] getBeanDefinitionNames();

    // 有多少个bean定义
    int getBeanDefinitionCount();

    // beanName是否被使用
    boolean isBeanNameInUse(String beanName);
}
