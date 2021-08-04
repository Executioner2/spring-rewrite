package com.spring.test.handler;

import com.spring.aspectj.lang.annotation.Around;
import com.spring.aspectj.lang.annotation.Aspect;
import com.spring.context.annotation.Component;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/4  18:20
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Aspect
@Component
public class MyAspect {

    @Around("execution(* com.spring.test..*(..))")
    public void pointcut() {

    }

//    @Around("execution(public com.spring.test)")
}
