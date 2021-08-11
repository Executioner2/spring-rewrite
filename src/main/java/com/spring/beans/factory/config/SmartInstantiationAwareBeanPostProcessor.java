package com.spring.beans.factory.config;

import com.spring.beans.factory.BeanFactory;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  19:30
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：省略了中间的InstantiationAwareBeanPostProcessor接口
 */
public interface SmartInstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 提前创建动态代理对象
     * @param bean
     * @param beanName
     * @param beanFactory
     * @return
     */
    default Object getEarlyBeanReference(Object bean, String beanName, BeanFactory beanFactory) {
        return bean;
    }
}
