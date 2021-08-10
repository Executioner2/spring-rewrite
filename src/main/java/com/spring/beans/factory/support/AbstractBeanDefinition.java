package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableBeanFactory;

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

    // 是否需要代理
    private volatile Boolean proxy;

    protected AbstractBeanDefinition() {
    }

    protected AbstractBeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    protected AbstractBeanDefinition(BeanDefinition original) {
        setBeanClass(original.getBeanClass());
        setScope(original.getScope());
        setLazyInit(original.isLazyInit());
        setProxy(original.isProxy());

        if (original instanceof AbstractBeanDefinition) {
            // TODO 待补充
        }
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
        if ("".equals(this.scope) ||
                ConfigurableBeanFactory.SCOPE_SINGLETON.equals(this.scope)) {

            return true;
        }
        return false;
    }

    /**
     * 是否是原型bean
     * @return
     */
    @Override
    public boolean isPrototype() {
        return ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(this.scope);
    }

    /**
     * 获取bean定义
     * 与官方写法大相径庭
     * @return 直接返回本身
     */
    @Override
    public BeanDefinition getOriginatingBeanDefinition() {
        return this;
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
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    /**
     * 是否懒加载
     * @return
     */
    @Override
    public boolean isLazyInit() {
        return (this.lazyInit != null && this.lazyInit.booleanValue());
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBeanClassName() {
        return this.getBeanClass().toString();
    }

    /**
     * 设置代理
     * @param proxy
     */
    @Override
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    /**
     * 是否需要代理
     * @return
     */
    @Override
    public boolean isProxy() {
        return this.proxy;
    }
}
