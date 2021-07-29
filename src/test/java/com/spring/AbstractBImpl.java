package com.spring;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  17:37
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class AbstractBImpl extends AbstractB{
    public AbstractBImpl() {
        System.out.println("AbstractBImpl构造方法执行了");
    }

    @Override
    public String speak() {
        System.out.println("进入到AbstractC抽象类的 speak中");
        return "使劲唱";
    }

    @Override
    public String rap() {
        System.out.println("进入到AbstractC抽象类的 rap中");
        return "使劲rap";
    }

}
