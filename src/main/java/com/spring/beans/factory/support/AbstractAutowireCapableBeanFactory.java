package com.spring.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

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
        Class<?> beanClass = mbd.getBeanClass();
        Object beanObject = null;

        try {
            // 无参构造
            if (args == null || args.length == 0) {
                Constructor<?> constructor = beanClass.getDeclaredConstructor();
                makeAccessible(constructor);
                beanObject = constructor.newInstance(); // 原始bean对象
            }
            // TODO 有参构造
            else {

            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 2、加入三级缓存
        Object factoryBeanObject = beanObject;
        System.out.println(factoryBeanObject == beanObject);
        addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, factoryBeanObject));

        // 3、依赖注入


        // 4、postProcessBeforeInitialization

        // 5、调用初始化方法

        // 6、postProcessAfterInitialization（正常情况下是在后置通知中创建动态代理对象，如果有循环依赖则提前创建动态代理对象）

        // 7、替换对象（从二级缓存中尝试拿取实例对象）
        Object earlyBeanObject = getSingleton(beanName, false);
        if (earlyBeanObject != null) {
            if (factoryBeanObject == beanObject) { // 依赖注入初始化前的bean和之后的bean是否是同一个bean
                beanObject = earlyBeanObject; // 替换为二级缓存中的对象
            }
        }

        return beanObject;
    }

    /**
     * 从二级缓存中拿实例对象/代理对象
     * @param beanName
     * @param mbd
     * @param bean
     * @return
     */
    protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
        Object exposedObject = bean;

        // TODO 判断是否需要提前创建代理对象

        return exposedObject;
    }

    /**
     * 取消访问检查
     * @param ctor
     */
    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) // 根据modifier（可见类型）判断构造方法是否是非公有
                // 判断构造方法的类是否是非共有，构造方法是否取消了访问检查
                || (!Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && (!ctor.canAccess(null)))) {
            ctor.setAccessible(true); // 取消访问检查
        }
    }
}
