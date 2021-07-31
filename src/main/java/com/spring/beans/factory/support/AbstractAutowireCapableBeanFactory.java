package com.spring.beans.factory.support;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/31  20:13
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：创建bean对象的模板类
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    /**
     * 开始创建bean
     * @param beanName
     * @param mbd
     * @param args
     * @return
     */
    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) {
        return doCreateBean(beanName, mbd, args);
    }

    /**
     * 正儿八经创建bean
     * @param beanName
     * @param mbd
     * @param args
     * @return
     */
    protected Object doCreateBean(String beanName, RootBeanDefinition mbd, Object[] args) {
        // 1、创建bean实例

        // 2、加入三级缓存

        // 3、依赖注入

        // 4、postProcessBeforeInitialization

        // 5、调用初始化方法

        // 6、postProcessAfterInitialization（正常情况下是在后置通知中创建动态代理对象，如果有循环依赖则提前创建动态代理对象）

        return null;
    }
}
