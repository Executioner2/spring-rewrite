package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:37
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：bean定义的模板类
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {

    public static final String SCOPE_DEFAULT = "";

    // 是否懒加载
    private volatile Boolean lazyInit;

    // scope：是原型模式还是单例模式
    private String scope = SCOPE_DEFAULT;

    // bean的class
    private volatile Object beanClass;

    // bean定义，与spring源码有些区别，spring源码使用的是更上层的Resource接口
    private BeanDefinitionRegistry resource;

    // 字段中依赖的bean
    private String[] dependsOn;

    public AbstractBeanDefinition() {
    }

    public AbstractBeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 是否有未注入的属性值
     * @return
     */
    @Override
    public boolean hasPropertyValues() {
        return false;
    }

    /**
     * 是否是单例bean
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * 获取bean定义
     * @return
     */
    @Override
    public BeanDefinition getOriginatingBeanDefinition() {
        return null;
    }

    /**
     * 获取beanClass
     * @return
     */
    public Class<?> getBeanClass() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject == null) {
            throw new IllegalStateException("beanClass为空");
        } else if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException("beanClass 类型不是Class");
        }
        return (Class<?>) beanClassObject;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClass = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.getBeanClass().toString();
    }

    @Override
    public void setLazyInit(boolean lazyInit) {

    }

    @Override
    public boolean isLazyInit() {
        return false;
    }

    @Override
    public void setDependsOn(String... dependsOn) {

    }

    @Override
    public String[] getDependsOn() {
        return new String[0];
    }

    @Override
    public void setScope(String scope) {

    }

    @Override
    public String getScope() {
        return null;
    }
}
