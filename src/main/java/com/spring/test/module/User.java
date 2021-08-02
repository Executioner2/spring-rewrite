package com.spring.test.module;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.context.annotation.Component;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  17:32
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Component
public class User {
    @Autowired
    private Product product;
}
