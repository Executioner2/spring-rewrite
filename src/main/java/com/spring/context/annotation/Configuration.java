package com.spring.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  18:46
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： 配置注解，加上该注解的类表明当前类是个配置类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Configuration {

    String value() default "";
}
