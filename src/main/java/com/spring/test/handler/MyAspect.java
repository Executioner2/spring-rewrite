package com.spring.test.handler;

import com.spring.aspectj.lang.ProceedingJoinPoint;
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

    @Around("execution(* com.spring.test.service..*.*(..))")
    public Object aspectTest(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入到环绕切片方法：aspectTest");
        System.out.println(pjp.hashCode());

        for (Object arg : pjp.getArgs()) {
            System.out.println(arg);
            System.out.println(arg.hashCode());
        }

        Object proceed = pjp.proceed();
        System.out.println("第一个切面方法:" + proceed);
        System.out.println();

        return "返回代理结果1";
    }

    @Around("execution(* com.spring.test.service..*.*(..))")
    public Object aspectTest1(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入到环绕切片方法：aspectTest1");
        System.out.println(pjp.hashCode());

        for (Object arg : pjp.getArgs()) {
            System.out.println(arg);
            System.out.println(arg.hashCode());
        }

        Object proceed = pjp.proceed();
        System.out.println("第二个切面方法:" + proceed);
        System.out.println();

        return "返回代理结果2";
    }

}
