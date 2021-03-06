package com.spring.beans.factory.support;

import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.ConfigurableBeanFactory;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;


import java.util.Collection;
import java.util.Iterator;
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
    public boolean containsBean(String name) {
//        return getBeanF;
        return false;

    }

    @Override
    public boolean isSingleton(String name) {
        RootBeanDefinition mbd = this.getMergedBeanDefinition(name);
        if (mbd != null) {
            return mbd.isSingleton();
        }
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        RootBeanDefinition mbd = this.getMergedBeanDefinition(name);
        if (mbd != null) {
            return mbd.isPrototype();
        }
        return false;
    }

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null, null);
    }

    @Override
    public Object getBean(Class<?> beanClass) {
        return doGetBean(beanClass.getName(), beanClass, null);
    }

    /**
     * 获取bean类型
     * @param name
     * @return
     */
    @Override
    public Class<?> getType(String name) {
        return getMergedBeanDefinition(name).getBeanClass();
    }

    /**
     * 获取所有bean后置处理器
     * @return
     */
    protected List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 取得mbd，如果不存在则根据传来的beanDefinition创建
     * @param beanName
     * @param beanDefinition 其它继承了BeanDefinition接口的bean定义
     * @return
     */
    public RootBeanDefinition getMergedBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        RootBeanDefinition rootBeanDefinition = this.mergedBeanDefinitions.get(beanName);

        if (rootBeanDefinition == null) {
            if (beanDefinition == null) {
                throw new NullPointerException("beanDefinition不能为空！");
            }

            // 深度复制
            rootBeanDefinition = new RootBeanDefinition(beanDefinition);
            this.mergedBeanDefinitions.put(beanName, rootBeanDefinition);
        }

        return rootBeanDefinition;
    }

    /**
     * 取得mbd
     * @param beanName
     * @return
     */
    public RootBeanDefinition getMergedBeanDefinition(String beanName) {
        RootBeanDefinition rootBeanDefinition = this.mergedBeanDefinitions.get(beanName);
        if (rootBeanDefinition == null) {
            if (this instanceof ConfigurableListableBeanFactory) {
                ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) this;
                BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
                rootBeanDefinition = this.getMergedBeanDefinition(beanName, beanDefinition);
            }
        }
        return rootBeanDefinition;
    }

    /**
     * 根据类型取得mbd
     * @param beanClass
     * @return
     */
    public RootBeanDefinition getMergedBeanDefinition(Class<?> beanClass) {
        Iterator<String> iterator = this.mergedBeanDefinitions.keySet().iterator();
        // 是不是接口
        boolean anInterface = beanClass.isInterface();

        while (iterator.hasNext()) {
            String key = iterator.next();
            RootBeanDefinition rootBeanDefinition = this.mergedBeanDefinitions.get(key);
            Class<?> rootBeanDefinitionBeanClass = rootBeanDefinition.getBeanClass();
            if (anInterface && beanClass.isAssignableFrom(rootBeanDefinitionBeanClass)) {
                // 是接口，并且该类实现了此接口就直接返回此root bean定义
                return rootBeanDefinition;
            } else if (rootBeanDefinitionBeanClass.equals(beanClass)) {
                return rootBeanDefinition;
            }
        }

        return null;
    }

    /**
     * 添加BeanPostProcessor到集合
     * @param beanPostProcessor
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 移除旧的bean后置处理器
        this.beanPostProcessors.remove(beanPostProcessor);
        // 添加新的bean后置处理器
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    /**
     * 获取bean对象，spring官方源码有更多参数，
     * 这里先进行简单实现，复杂的处理等后续完善
     * @param beanClassName
     * @param
     * @return
     */
    protected Object doGetBean(String beanClassName, Class<?> requiredType, Object[] args) {
        // 动态获取bean名称
        String beanName = this.getBeanName(beanClassName, requiredType);
        // 1、先尝试获取单例对象
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            RootBeanDefinition mbd = this.getMergedBeanDefinition(beanName);

            // 单例模式
            if (mbd.isSingleton()) {
                singleton = this.getSingleton(beanName, () -> {
                    return createBean(beanName, mbd, null);
                });
            }
            // 原型模式
            else if (mbd.isPrototype()) {
                return createBean(beanName, mbd, null);
            }

        }

        return singleton;
    }

    // 是否包含此bean定义
    protected abstract boolean containsBeanDefinition(String beanName);

    // 创建bean对象
    protected abstract Object createBean(String beanName, RootBeanDefinition mbd, Object[] args);

    /**
     * 获取bean名称
     * @param beanClassName
     * @param beanClass
     * @return
     */
    protected String getBeanName(String beanClassName, Class beanClass) {
        if (this.containsBeanDefinition(beanClassName)) {
            return beanClassName;
        }

        String beanName = BeanDefinitionReaderUtils.generateBeanName(beanClassName);
        if (this.containsBeanDefinition(beanName)) {
            return beanName;
        }

        if (beanClass == null || !this.containsBeanDefinition(beanClass.getName())) {
            throw new NullPointerException("没有这个bean的定义：" + beanClassName);
        }

        return beanClass.getName();
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
