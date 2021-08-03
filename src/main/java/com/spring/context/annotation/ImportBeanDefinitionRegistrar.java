package com.spring.context.annotation;

import com.spring.beans.factory.support.BeanDefinitionRegistry;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  18:55
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：bean定义注册器
 */
public interface ImportBeanDefinitionRegistrar {

    // bean定义注册
    void registerBeanDefinitions(BeanDefinitionRegistry registry);
}
