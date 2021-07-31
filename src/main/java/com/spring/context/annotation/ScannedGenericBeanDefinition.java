package com.spring.context.annotation;

import com.spring.beans.factory.support.AbstractBeanDefinition;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/31  18:32
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：这个类目前没什么实际作用，仅仅是为了与RootBeanDefinition区分开
 */
public class ScannedGenericBeanDefinition extends AbstractBeanDefinition {

    public ScannedGenericBeanDefinition(Class<?> beanClass) {
        super(beanClass);
    }
}
