package com.spring.aop.framework.autoproxy;

import com.spring.aop.framework.JdkDynamicAopProxy;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.lang.reflect.Proxy;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  19:44
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public abstract class AbstractAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    public AbstractAutoProxyCreator() {
    }

    public AbstractAutoProxyCreator(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 提前创建代理对象，和官方实现有出入
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        return wrapIfNecessary(bean, beanName);
    }

    /**
     * 初始化之前执行的操作
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * 初始化之后执行的操作
     * 创建代理对象，如果提前创建了则
     * 直接返回原始对象，如果没有提前
     * 创建则返回原始对象。
     * @param bean
     * @param beanName
     * @return
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        if (beanDefinition.isProxy()) {
            // 需要代理
            return wrapIfNecessary(bean, beanName);
        }

        return bean;
    }

    /**
     * 创建代理对象
     * @param bean
     * @param beanName
     * @return
     */
    protected Object wrapIfNecessary(Object bean, String beanName) {
        if (bean instanceof Proxy) {
            // 已经是代理对象了，直接返回
            return bean;
        }

        // 直接利用jdk的proxy创建代理对象
        // 省略了工厂设计模式
        // 创建代理对象
        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(bean);
        bean = jdkDynamicAopProxy.getProxy(bean.getClass(), this.beanFactory);

        return bean;

    }
}
