package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:36
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： 根bean定义
 */
public class RootBeanDefinition extends AbstractBeanDefinition{

    // 是否需要提前生成代理对象
    boolean postProcessed = false;
}
