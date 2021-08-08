package com.spring.test.handler;

import com.spring.aspectj.lang.annotation.Around;
import com.spring.aspectj.lang.annotation.Aspect;
import com.spring.context.annotation.Component;

import java.util.Date;

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

    @Around("execution(* set*(..)")
    public void pointcut02() {

    }

    @Around("execution(public *com.*(..))")
    private static String pointcut02(String str, Date create) {
        return "";
    }
}
