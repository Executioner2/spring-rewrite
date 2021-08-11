package com.spring.aop.framework;

import com.spring.aop.framework.support.AopDefinitionRegistry;
import com.spring.aspectj.lang.support.JoinPointDefinition;
import com.spring.beans.factory.BeanFactory;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  19:26
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：所有需要代理的bean都由此类代理
 */
public final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
    private static final long serialVersionUID = -3092292718628437361L;

    // 被代理的类的所有接口
    private final Class<?>[] proxiedInterfaces;

    // 连接点要执行的切入方法列表
    private final Map<String, List<JoinPointDefinition>> proxyMethodMap = new ConcurrentHashMap<>(16);

    // 目标对象
    private Object target;


    public JdkDynamicAopProxy(Object target) {
        this.target = target;
        // 获取所有接口
        this.proxiedInterfaces = target.getClass().getInterfaces();
    }

    /**
     * 取得代理对象
     * @param classLoader
     * @return
     */
    @Override
    public Object getProxy(ClassLoader classLoader) {

        return Proxy.newProxyInstance(classLoader, this.proxiedInterfaces, this);
    }

    /**
     * 取得/创建代理对象
     * @param clazz
     * @param beanFactory
     * @return
     */
    @Override
    public Object getProxy(Class clazz, BeanFactory beanFactory) {
        AopDefinitionRegistry adr = (AopDefinitionRegistry) beanFactory.getBean(AopDefinitionRegistry.class);
        this.proxyMethodMap.putAll(adr.getJoinPointDefinition(clazz.getName()));

        return this.getProxy(clazz.getClassLoader());
    }

    /**
     * 代理方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        return method.invoke(this.target, args);
    }
}
