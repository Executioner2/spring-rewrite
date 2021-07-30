package com.spring.context.annotation;

import java.lang.annotation.*;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  19:50
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lazy {

    // 是否懒加载，如果加上此注解则默认懒加载
    boolean value() default true;
}
