package com.spring.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  21:31
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public abstract class ReflectionUtils {

    /**
     * 取消访问检查
     * @param ctor
     */
    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) // 根据modifier（可见类型）判断构造方法是否是非公有
                // 判断构造方法的类是否是非共有，构造方法是否取消了访问检查
                || (!Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && (!ctor.canAccess(null)))) {

            ctor.setAccessible(true); // 取消访问检查
        }
    }

    /**
     * 取消访问检查
     * @param field
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()))
                || (!Modifier.isPublic(field.getDeclaringClass().getModifiers()))
                || (Modifier.isFinal(field.getModifiers()) && field.canAccess(null))) {

            field.setAccessible(true);
        }
    }
}
