package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  22:01
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： beanDefinition读取工具类
 */
public abstract class BeanDefinitionReaderUtils {


    public static String generateBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        String name = beanClass.getName();
        String beanName = name.replaceFirst(beanClass.getPackageName() + "\\.", "");
        String c = String.valueOf(beanName.charAt(0));
        beanName = beanName.replaceFirst(c, c.toLowerCase());
        return beanName;
    }
}
