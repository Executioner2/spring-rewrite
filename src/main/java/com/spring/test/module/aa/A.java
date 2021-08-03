package com.spring.test.module.aa;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.context.annotation.Component;
import com.spring.test.module.aa.bb.B;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  20:23
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Component
public class A {
    @Autowired
    public B b;
}
