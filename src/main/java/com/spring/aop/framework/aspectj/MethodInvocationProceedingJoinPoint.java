package com.spring.aop.framework.aspectj;

import com.spring.aspectj.lang.ProceedingJoinPoint;
import com.spring.aspectj.lang.support.JoinPointDefinition;
import com.spring.beans.factory.BeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  23:47
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint {
    // 方法中的参数
    private Object[] args;

    // 代理对象
    private Object proxy;

    // 方法
    private Method method;

    // 目标对象
    private Object target;

    // 当前执行到的通知下标
    private int currentInterceptorIndex = -1;

    // 连接点集合
    private List<JoinPointDefinition> joinPointDefinitions;

    private BeanFactory beanFactory;

    public MethodInvocationProceedingJoinPoint(Object target, Object proxy, Method method, Object[] args, List<JoinPointDefinition> joinPointDefinitions, BeanFactory beanFactory) {
        this.target = target;
        this.proxy = proxy;
        this.method = method;
        this.args = args;
        this.joinPointDefinitions = joinPointDefinitions;
        this.beanFactory = beanFactory;
    }

    /**
     * 执行代理方法
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public Object proceed() {
        try {
            ++this.currentInterceptorIndex;

            if (this.joinPointDefinitions.size() == 0 || this.joinPointDefinitions.size() == this.currentInterceptorIndex) {
                return this.method.invoke(this.target, this.args);
            }

            JoinPointDefinition joinPointDefinition = this.joinPointDefinitions.get(this.currentInterceptorIndex);
            Object bean = beanFactory.getBean(joinPointDefinition.getAspectBeanName());
            Method pointcutMethod = joinPointDefinition.getPointcutMethod();

            return pointcutMethod.invoke(bean, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object proceed(Object[] args) {
        this.args = args;
        return this.proceed();
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
