package com.spring.annotation;

import com.spring.context.annotation.Import;
import com.spring.module.Category;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/4  18:49
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(Category.class)
public @interface B {
}
