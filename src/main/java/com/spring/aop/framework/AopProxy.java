package com.spring.aop.framework;

import com.spring.beans.factory.BeanFactory;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  19:26
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface AopProxy {
    // 创建获取代理对象
    Object getProxy(ClassLoader classLoader);

    // 创建获取代理对象
    Object getProxy(Class clazz, BeanFactory beanFactory);
}
