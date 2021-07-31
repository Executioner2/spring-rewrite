package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:36
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： 根bean定义，目前没什么用，详细参数先不添加
 */
public class RootBeanDefinition extends AbstractBeanDefinition{

    // 是否允许后置处理器修改bean定义
    boolean postProcessed = false;

    // 是否是工厂bean，即存入三级缓存的bean
    volatile Boolean isFactoryBean;



    public RootBeanDefinition(Class<?> beanClass) {
        super(beanClass);
    }

    public RootBeanDefinition(BeanDefinition original) {
        super(original);
    }
}
