package com.spring.service.impl;

import com.spring.service.ProductService;
import com.spring.service.UserService;

import java.math.BigDecimal;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  18:36
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class ProductServiceImpl implements ProductService {
    private String name = "123";

    private UserService userService;

    @Override
    public void setPrice(String id, BigDecimal price) {

    }

    @Override
    public String getName() {
        return this.name;
    }

    public void set() {

    }
}
