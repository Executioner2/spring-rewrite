package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
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
public class DefaultListableBeanFactory extends AbstractBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {
    // bean定义集合
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    // bean定义的bean全限定名集合
    private final List<String> beanDefinitionNames = new ArrayList<>(256);


    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

    }

    @Override
    public void removeBeanDefinition(String beanName) {

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
            throw new NullPointerException("bean定义不存在"); // XXX 将就抛空指针异常
        }
        return beanDefinition;
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
        return (String[]) this.beanDefinitionNames.toArray();
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

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    @Override
    public Object getSingleton(String beanName) {
        return null;
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return false;
    }

    @Override
    public String[] getSingletonNames() {
        return new String[0];
    }

    @Override
    public int getSingletonCount() {
        return 0;
    }

    @Override
    public Object getSingletonMutex() {
        return null;
    }
}
