package com.spring.context.support;

import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.context.ConfigurableApplicationContext;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {


    /**
     * 核心方法，整个ioc流程就在此方法中
     */
    @Override
    public void refresh() {
        // 1、准备刷新 TODO 待实现
        prepareRefresh();

        // 2、创建beanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // 3、初始化beanFactory的一些参数 TODO 待实现
        prepareBeanFactory();

        // 4、beanFactory后置增强处理 TODO 待实现
        postProcessBeanFactory();

        // 5、调用beanFactory后置增强器 TODO 待实现
        invokeBeanFactoryPostProcessors();

        // 6、国际化处理 TODO 待实现
        initMessageSource();

        // 7、初始化时间多播器 TODO 待实现
        initApplicationEventMulticaster();

        // 8、初始化特定bean TODO 待实现
        onRefresh();

        // 9、注册监听器 TODO 待实现
        registerListeners();

        // 10、完成所有非懒加载单例bean的实例化 TODO 待实现
        finishBeanFactoryInitialization(beanFactory);

        // 11、发布相应事件 TODO 待实现
        finishRefresh();

    }

    /**
     * 发布相应事件
     */
    protected void finishRefresh() {

    }

    /**
     * 完成所有非懒加载单例bean的实例化
     * @param beanFactory
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {

    }

    /**
     * 注册监听器，与事件多播器构成观察者模式
     */
    protected void registerListeners() {

    }

    /**
     * 初始化特定bean，对于子类默认什么都不做
     */
    protected void onRefresh() {

    }

    /**
     * 事件多播器，与事件监听器构成观察者模式
     */
    protected void initApplicationEventMulticaster(){

    }

    /**
     * 国际化处理
     */
    protected void initMessageSource() {

    }

    /**
     * 调用beanFactory后置增强器，现在先空着
     */
    protected void invokeBeanFactoryPostProcessors() {

    }

    /**
     * beanFactory后置增强处理，先空着，官方也是空着的
     */
    protected void postProcessBeanFactory() {
    }

    /**
     * 初始化beanFactory的一些参数，现在先空着
     */
    protected void prepareBeanFactory() {

    }

    /**
     * 准备刷新，进行一些准备操作，现在先空着
     */
    protected void prepareRefresh() {

    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    // 获取创建的beanFactory，交给子类去实现
    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    // 刷新创建beanFactory，交给子类去实现
    protected abstract void refreshBeanFactory();

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
