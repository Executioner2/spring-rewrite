package com.spring;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  17:29
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class Application extends AbstractBImpl{
    private final Object a;
    private final Object b;

    public Application() {
        this.a = new Object();
        this.b = new Object();
    }

    public Application(String clazz) {
        this();
        refresh();
    }
}
