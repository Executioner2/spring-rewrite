package com.spring.beans.factory.support;

import com.spring.aop.framework.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import com.spring.beans.factory.InitializingBean;
import com.spring.beans.factory.annotation.Autowired;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import com.spring.util.ReflectionUtils;

import java.lang.reflect.*;

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
            // 有参构造
            else {
                // TODO 有参构造待实现
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
        addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, factoryBeanObject));

        // 3、依赖注入 populateBean
        populateBean(beanName, mbd, beanObject);

        // 4、postProcessBeforeInitialization
        beanObject = applyBeanPostProcessorsBeforeInitialization(beanObject, beanName);

        // 5、初始化 initializeBean
        invokeInitMethods(beanName, beanObject, mbd);

        // 6、postProcessAfterInitialization（正常情况下是在后置通知中创建动态代理对象，如果有循环依赖则提前创建动态代理对象）
        beanObject = applyBeanPostProcessorsAfterInitialization(beanObject, beanName);

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
     * 初始化bean
     * @param beanName 暂时没用
     * @param beanObject
     * @param mbd 暂时没用
     * @return
     */
    protected void invokeInitMethods(String beanName, Object beanObject, RootBeanDefinition mbd) {
        if (beanObject instanceof InitializingBean) {
            ((InitializingBean) beanObject).afterPropertiesSet();
        }
    }

    /**
     * 执行postProcessBeforeInitialization方法
     * 当其中有一个初始化前后置处理器返回结果为null时
     * 则中断后续的增强处理，官方是这样写的，为什么不
     * 继续处理可能是出于增强处理的顺序考虑，前面的返回
     * 为null则是错误的处理所以就中断后续的处理，因为
     * 后续的处理可能要依赖前面的处理结果。
     * @param existingBean
     * @param beanName
     */
    protected Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 执行postProcessAfterInitialization方法
     * 当其中有一个初始化后后置处理器返回结果为null时
     * 则中断后续的增强处理，官方是这样写的，为什么不
     * 继续处理可能是出于增强处理的顺序考虑，前面的返回
     * 为null则是错误的处理所以就中断后续的处理，因为
     * 后续的处理可能要依赖前面的处理结果。
     * @param existingBean
     * @param beanName
     */
    protected Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 依赖注入
     * @param beanName 暂时没用到
     * @param mbd 暂时没用到
     * @param beanObject
     */
    protected void populateBean(String beanName, RootBeanDefinition mbd, Object beanObject) {
        // 循环所有字段
        Field[] declaredFields = beanObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
//                Autowired autowired = field.getDeclaredAnnotation(Autowired.class);
                // 获取字段类型
                Type genericType = field.getGenericType();
                RootBeanDefinition mergedBeanDefinition = getMergedBeanDefinition(genericType);
                if (mergedBeanDefinition == null) {
                    throw new NullPointerException("要注入的对象bean定义不存在");
                }
                makeAccessible(field);
                // 获取bean对象
                try {
                    field.set(beanObject, getBean(mergedBeanDefinition.getBeanClassName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从二级缓存中拿实例对象/代理对象
     * 实际上进到这个方法中就说明已经产生了循环依赖
     * 那么只需要判断是否需要进行代理就可以了
     * @param beanName
     * @param mbd
     * @param bean
     * @return
     */
    protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
        Object exposedObject = bean;

        // 判断是否开启了动态代理，和官方有出入
        if (mbd.isProxy()) {
            SmartInstantiationAwareBeanPostProcessor autoProxyCreator = (AnnotationAwareAspectJAutoProxyCreator) getBean(AnnotationAwareAspectJAutoProxyCreator.class);
            exposedObject = autoProxyCreator.getEarlyBeanReference(bean, beanName, this);
        }

        return exposedObject;
    }

    /**
     * 取消访问检查
     * @param ctor
     */
    private void makeAccessible(Constructor<?> ctor) {
        ReflectionUtils.makeAccessible(ctor);
    }

    /**
     * 取消访问检查
     * @param field
     */
    private void makeAccessible(Field field) {
        ReflectionUtils.makeAccessible(field);
    }
}
