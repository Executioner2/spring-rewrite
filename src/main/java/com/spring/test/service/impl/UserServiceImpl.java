package com.spring.test.service.impl;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.context.annotation.Component;
import com.spring.test.service.ProductService;
import com.spring.test.service.UserService;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  22:15
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    ProductService productService;

    @Override
    public void registerUser(String username, String password) {

    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public ProductService getProductService() {
        return this.productService;
    }

    public void test() {

    }
}
