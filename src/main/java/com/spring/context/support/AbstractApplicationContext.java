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
        prepareBeanFactory(beanFactory);

        // 4、beanFactory后置增强处理
        postProcessBeanFactory(beanFactory);

        // 5、调用beanFactory后置增强器 TODO 做包扫描
        invokeBeanFactoryPostProcessors(beanFactory);

        // 6、注册bean后置处理器 TODO 做了包扫描后做这个
        registerBeanPostProcessors(beanFactory);

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
     * 注册bean后置处理器
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {

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
     * 调用beanFactory后置增强器
     * 在此方法中进行包扫描。
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    }

    /**
     * beanFactory后置增强处理，先空着，官方是交给用户去实现的
     * @param beanFactory
     */
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    }

    /**
     * 初始化beanFactory的一些参数，现在先空着
     * @param beanFactory
     */
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {

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
