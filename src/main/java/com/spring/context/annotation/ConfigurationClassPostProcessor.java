package com.spring.context.annotation;

import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  23:41
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：主要做后置处理的配置类
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * TODO 在此进行包扫描，beanDefinition注册
     * @param registry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }
}
