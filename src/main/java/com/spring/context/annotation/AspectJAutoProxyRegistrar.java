package com.spring.context.annotation;

import com.spring.beans.factory.support.BeanDefinitionRegistry;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  19:00
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： AOP动态代理注册
 */
public class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar{

    /**
     * 在这里标记需要代理的类
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(BeanDefinitionRegistry registry) {

    }
}
