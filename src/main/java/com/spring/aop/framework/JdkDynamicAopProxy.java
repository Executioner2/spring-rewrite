package com.spring.aop.framework;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  19:26
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：所有需要代理的bean都由此类代理
 */
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
    private static final long serialVersionUID = -3092292718628437361L;

    /**
     * 取得代理对象
     * @return
     */
    @Override
    public Object getProxy() {
        return null;
    }

    /**
     * 取得代理对象
     * @param classLoader
     * @return
     */
    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
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
        return null;
    }
}
