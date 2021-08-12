package com.spring.aspectj.lang;

import java.lang.reflect.InvocationTargetException;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  15:11
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： 切片接口
 */
public interface ProceedingJoinPoint extends JoinPoint{

    // 原始方法
    Object proceed() throws InvocationTargetException, IllegalAccessException;

    // 带参数的原始方法
    Object proceed(Object[] args) throws InvocationTargetException, IllegalAccessException;
}
