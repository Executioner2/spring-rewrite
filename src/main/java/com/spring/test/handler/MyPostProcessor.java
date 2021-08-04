package com.spring.test.handler;

import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.context.annotation.Component;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  22:20
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Component
public class MyPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
