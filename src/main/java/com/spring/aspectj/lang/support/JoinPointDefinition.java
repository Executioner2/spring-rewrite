package com.spring.aspectj.lang.support;

import java.lang.reflect.Method;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  16:54
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：连接点定义
 */
public class JoinPointDefinition {

    // 切面的beanName
    private String aspectBeanName;

    // 要执行的切入点方法
    private Method pointcutMethod;

    public JoinPointDefinition() {
    }

    public JoinPointDefinition(String aspectBeanName, Method pointcutMethod) {
        this.aspectBeanName = aspectBeanName;
        this.pointcutMethod = pointcutMethod;
    }

    public String getAspectBeanName() {
        return aspectBeanName;
    }

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    public Method getPointcutMethod() {
        return pointcutMethod;
    }

    public void setPointcutMethod(Method pointcutMethod) {
        this.pointcutMethod = pointcutMethod;
    }
}
