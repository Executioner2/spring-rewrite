package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  23:40
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
    // bean定义注册后置处理
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry);
}
