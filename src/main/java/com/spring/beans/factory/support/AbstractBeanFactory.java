package com.spring.beans.factory.support;

import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.config.ConfigurableBeanFactory;
import com.spring.interface_.BeanPostProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Override
    public Object getBean(String name) {
        return null;
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

    @Override
    public void addBeanPostProcessor(com.spring.beans.factory.config.BeanPostProcessor beanPostProcessor) {

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
