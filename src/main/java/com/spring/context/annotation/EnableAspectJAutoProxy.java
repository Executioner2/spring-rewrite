package com.spring.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  18:49
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：开启AOP动态代理
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AspectJAutoProxyRegistrar.class)
public @interface EnableAspectJAutoProxy {

    boolean proxyTargetClass() default false;
}
