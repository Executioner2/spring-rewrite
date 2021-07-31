package com.spring.beans.factory.support;

import com.spring.beans.factory.ObjectFactory;
import com.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  16:37
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：在spring源码中，此方法依次继承了实现了接口方法的模板类，这里省去那些模板类直接在此类中进行了实现
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // 一级缓存
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    // 二级缓存
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    // 三级缓存
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    // 已注册的bean的name
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    // 依赖的bean
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /**
     * 注册单例bean，添加到一级缓存中，删除一二级缓存中的对应信息
     * @param beanName
     * @param singletonObject
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    /**
     * 获取单例bean
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    /**
     * 是否有单例bean
     * @param beanName
     * @return
     */
    @Override
    public boolean containsSingleton(String beanName) {
        return false;
    }

    /**
     * 获取所有已经注册的单例bean的name
     * @return
     */
    @Override
    public String[] getSingletonNames() {
        return new String[0];
    }

    /**
     * 获取已经注册了的单例bean数量
     * @return
     */
    @Override
    public int getSingletonCount() {
        return 0;
    }

    /**
     * 获取单例bean互斥锁
     * 其实就是获取一级缓存
     * @return
     */
    @Override
    public Object getSingletonMutex() {
        return this.singletonObjects;
    }


    /**
     * TODO 暂不明白此方法的具体作用，先空着
     * 在DefaultSingletonBeanRegistry中写了此方法，
     * ConfigurableBeanFactory接口中刚好也有此方法的定义。
     * 而DefaultListableBeanFactory直接或间接继承/实现了
     * 以上类/接口。最终调用的将是此类中的该方法。
     * @param beanName
     * @return
     */
    public String[] getDependentBeans(String beanName) {
        return null;
    }


    /**
     * 为给定的bean注册一个依赖bean
     * @param beanName
     * @param dependentBeanName
     */
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    /**
     * 依次从一级缓存，二级缓存中查询bean对象，
     * 如果是需要取得提前代理对象则从三级缓存中
     * 拿到原始实例化对象并调用创建代理对象的方法
     * 创建目标实例对象的代理对象并返回，否则只尝试
     * 获取普通实例对象
     * @param beanName
     * @param allowEarlyReference 是否取得代理对象,为true表示需要提前创建代理对象
     * @return
     */
    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        // 先尝试从一级缓存中取得bean
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 尝试从二级缓存中取得bean
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null && allowEarlyReference) {
                // 取得代理对象
                synchronized (this.singletonObjects) {
                    // 再次尝试一二级缓存中取得单例对象（这里也表示要取得代理对象）
                    // 加锁后再次尝试取是为了避免加锁之前就已经创建了代理对象并放入缓存中
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        singletonObject = this.earlySingletonObjects.get(beanName);
                        if (singletonObject == null) {
                            ObjectFactory<?> objectFactory = this.singletonFactories.get(beanName);
                            if (objectFactory != null) {
                                // 调用lambda生成的方法
                                singletonObject = objectFactory.getObject();
                                // 将代理对象放入二级缓存
                                this.earlySingletonObjects.put(beanName, singletonObject);
                                // 移除三级缓存中的原始实例
                                this.singletonFactories.remove(beanName);
                            }

                        }
                    }
                }
            }
        }

        return singletonObject;
    }

    /**
     * 获取单例对象
     * @param beanName
     * @param singletonFactory 传入lambda表达式
     * @return
     */
    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        Object object = singletonFactory.getObject();

        return object;
    }

}
