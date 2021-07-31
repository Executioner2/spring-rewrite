package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.ConfigurableBeanFactory;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  20:58
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    // 存放所有继承了beanPostProcessors的对象
    private final List<BeanPostProcessor> beanPostProcessors = new BeanPostProcessorCacheAwareList();

    // 存放rootBeanDefinition的集合
    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String name) {
        return doGetBean(name);
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    protected List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    /**
     * 取得mbd
     * @param beanName
     * @param beanDefinition 其它继承了BeanDefinition接口的bean定义
     * @return
     */
    public RootBeanDefinition getMergedBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        RootBeanDefinition rootBeanDefinition = this.mergedBeanDefinitions.get(beanName);

        if (rootBeanDefinition == null) {
            // 深度复制
            rootBeanDefinition = new RootBeanDefinition(beanDefinition);
            this.mergedBeanDefinitions.put(beanName, rootBeanDefinition);
        }

        return rootBeanDefinition;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 移除旧的bean后置处理器
        this.beanPostProcessors.remove(beanPostProcessor);
        // 添加新的bean后置处理器
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 获取bean对象，spring官方源码有更多参数，
     * 这里先进行简单实现，复杂的处理等后续完善
     * @param name beanName
     * @return
     */
    protected Object doGetBean(String name) {
        // 1、先尝试获取单例对象
        Object singleton = this.getSingleton(name);
        if (singleton == null) {
            // 为空则创建对象，先从beanDefinitionMap中取得beanDefinition


        }

        return singleton;
    }


    /**
     * 高并发下的list集合，利用内部类继承CopyOnWriteArrayList实现
     */
    private class BeanPostProcessorCacheAwareList extends CopyOnWriteArrayList<BeanPostProcessor> {

        @Override
        public BeanPostProcessor set(int index, BeanPostProcessor element) {
            return super.set(index, element);
        }

        @Override
        public boolean add(BeanPostProcessor o) {
            return super.add(o);
        }

        @Override
        public void add(int index, BeanPostProcessor element) {
            super.add(index, element);
        }

        @Override
        public BeanPostProcessor remove(int index) {
            return super.remove(index);
        }

        @Override
        public boolean remove(Object o) {
            return super.remove(o);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return super.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return super.retainAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends BeanPostProcessor> c) {
            return super.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends BeanPostProcessor> c) {
            return super.addAll(index, c);
        }

        @Override
        public boolean removeIf(Predicate<? super BeanPostProcessor> filter) {
            return super.removeIf(filter);
        }

        @Override
        public void replaceAll(UnaryOperator<BeanPostProcessor> operator) {
            super.replaceAll(operator);
        }
    }
}
