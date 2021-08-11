package com.spring.aop.framework.aspectj.annotation;

import com.spring.aop.framework.autoproxy.AbstractAutoProxyCreator;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  20:14
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：需要提前创建bean
 */
public class AnnotationAwareAspectJAutoProxyCreator extends AbstractAutoProxyCreator {
    public AnnotationAwareAspectJAutoProxyCreator() {
    }

    public AnnotationAwareAspectJAutoProxyCreator(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }
}
