package com.spring.aspectj.lang;

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
    Object proceed();

    // 带参数的原始方法
    Object proceed(Object[] args);
}
