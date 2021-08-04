package com.spring.context.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.beans.factory.support.DefaultListableBeanFactory;


/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  20:49
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public abstract class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        // 实例化beanFactory
        this.beanFactory = new DefaultListableBeanFactory();
    }

    /**
     * 返回bean工厂
     * @return
     */
    @Override
    public final ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    /**
     * 什么也不做，官方也是这样说的
     */
    @Override
    protected void refreshBeanFactory() {

    }

    /**
     * 注册bean定义
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 注册bean定义
     * @param beanClass
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(Class<?> beanClass, BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanClass, beanDefinition);
    }

    /**
     * 移除bean定义
     * @param beanName
     */
    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanFactory.removeBeanDefinition(beanName);
    }

    /**
     * 获取bean定义
     * @param beanName
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanFactory.getBeanDefinition(beanName);
    }

    /**
     * 判断所传beanName是否在bean定义集合中
     * @param beanName
     * @return
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanFactory.containsBeanDefinition(beanName);
    }

    /**
     * 获取所有bean定义name
     * @return
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanFactory.getBeanDefinitionNames();
    }

    /**
     * 获取bean定义数量
     * @return
     */
    @Override
    public int getBeanDefinitionCount() {
        return this.beanFactory.getBeanDefinitionCount();
    }

    /**
     * 判断beanName是否被使用
     * @param beanName
     * @return
     */
    @Override
    public boolean isBeanNameInUse(String beanName) {
        return this.beanFactory.isBeanNameInUse(beanName);
    }
}
