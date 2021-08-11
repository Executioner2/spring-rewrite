package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:05
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {
    // bean定义集合
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    // 配置类
    private final Map<String, Class<?>> configurationClassMap = new ConcurrentHashMap<>(256);

    // bean定义的bean全限定名集合
    private final List<String> beanDefinitionNames = new ArrayList<>(256);

    /**
     * 注册到配置类
     * @param name
     * @param clazz
     */
    @Override
    public void registerConfigurationClassMap(String name, Class<?> clazz) {
        this.configurationClassMap.put(name, clazz);
    }

    /**
     * 获取配置类
     * @param name
     * @return
     */
    @Override
    public Class<?> getConfigurationClass(String name) {
        return this.configurationClassMap.get(name);
    }

    /**
     * 获取配置类迭代器
     * @return
     */
    @Override
    public Iterator<String> getConfigurationClassMapKeySetIterator() {
        return this.configurationClassMap.keySet().iterator();
    }

    /**
     * 注册bean定义
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (this.isBeanNameInUse(beanName)) {
            throw new IllegalStateException("beanName已被使用");
        }

        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
    }

    /**
     * 注册bean定义
     * @param beanClass
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(Class<?> beanClass, BeanDefinition beanDefinition) {
        this.registerBeanDefinition(beanClass.getName(), beanDefinition);
    }

    /**
     * 移除beanDefinition，
     * 把map和list中的信息都移除
     * @param beanName
     */
    @Override
    public void removeBeanDefinition(String beanName) {
        if (this.containsBeanDefinition(beanName)) {
            this.beanDefinitionMap.remove(beanName);
            this.beanDefinitionNames.remove(beanName);
        }
    }

    /**
     * 获取bean定义
     * @param beanName
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException("bean定义不存在：" + beanName); // XXX 将就抛空指针异常
        }
        return this.beanDefinitionMap.get(beanName);
    }

    /**
     * 获取beanNames的迭代器
     * spring源码中利用的是官方自己写的复合迭代器
     * 除了beanNames的迭代器其中还包含手动单例集合的迭代器
     * @return
     */
    @Override
    public Iterator<String> getBeanNamesIterator() {

        return this.beanDefinitionNames.iterator();
    }

    /**
     * 实例化剩余所有非懒加载单例bean
     */
    @Override
    public void preInstantiateSingletons() {
        for (String beanName : this.beanDefinitionNames) {
            BeanDefinition bd = this.beanDefinitionMap.get(beanName);
            if (bd.isSingleton() && (!bd.isLazyInit())) {
                getBean(beanName);
            }
        }
    }

    /**
     * 判断当前beanName是否在bean定义中
     * @param beanName
     * @return
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        if (this.beanDefinitionMap.containsKey(beanName)) {
            return true;
        }
        return false;
    }

    /**
     * 获取所有bean的全限定名称
     * @return
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(String[]::new);
    }

    /**
     * 获取bean定义的数量
     * @return
     */
    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionNames.size();
    }



    /**
     * 判断beanName是否被使用
     * @param beanName
     * @return
     */
    @Override
    public boolean isBeanNameInUse(String beanName) {
        if (this.beanDefinitionNames.contains(beanName)) {
            return true;
        }
        return false;
    }

    /**
     * 注册单例对象
     * @param beanName
     * @param singletonObject
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    /**
     * 是否是单例bean
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }
}
