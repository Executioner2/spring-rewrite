package com.spring.aspectj.lang.support;

import java.lang.reflect.Method;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/4  17:20
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class PointcutDefinition {
    // 切入位置
    private String execution;

    // 切入方法
    private Method executionMethod;

    // 切面
    private Class<?> aspect;

    public PointcutDefinition() {
    }

    public PointcutDefinition(String execution) {
        this.execution = execution;
    }

    public PointcutDefinition(String execution, Method executionMethod) {
        this(execution);
        this.executionMethod = executionMethod;
    }

    public PointcutDefinition(String execution, Method executionMethod, Class<?> aspect) {
        this(execution, executionMethod);
        this.aspect = aspect;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public Method getExecutionMethod() {
        return executionMethod;
    }

    public void setExecutionMethod(Method executionMethod) {
        this.executionMethod = executionMethod;
    }

    public Class<?> getAspect() {
        return aspect;
    }

    public void setAspect(Class<?> aspect) {
        this.aspect = aspect;
    }
}
