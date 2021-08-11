package com.spring.aop.framework.aspectj;

import com.spring.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  23:47
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint {
    private Object[] args;

    private Object proxy;

//    private Method

    public MethodInvocationProceedingJoinPoint(Object[] args) {
        this.args = args;
    }

    @Override
    public Object proceed() {
        return null;
    }

    @Override
    public Object proceed(Object[] args) {
        return null;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }
}
