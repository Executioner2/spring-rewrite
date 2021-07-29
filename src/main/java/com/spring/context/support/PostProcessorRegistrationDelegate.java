package com.spring.context.support;

import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  23:35
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：后置注册，用于包扫描，bean后处理器注册
 */
final class PostProcessorRegistrationDelegate {
    private PostProcessorRegistrationDelegate() {}

    /**
     * 调用bean工厂后置处理
     * 也在此方法中进行包扫描
     * @param beanFactory
     * @param beanFactoryPostProcessors
     */
    public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {


    }

    /**
     * 注册bean后置处理器
     * @param beanFactory
     * @param applicationContext
     */
    public static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {

    }
}
