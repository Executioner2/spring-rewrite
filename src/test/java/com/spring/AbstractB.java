package com.spring;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  17:25
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public abstract class AbstractB implements InterfaceA{
    public AbstractB() {
        System.out.println("AbstractB构造方法执行了");
    }

    @Override
    public void refresh() {
        speak();
        rap();
    }

    @Override
    public abstract String speak();

    public abstract String rap();
}
