package com.spring.beans.factory.config;

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
}
