package com.spring.test.service.impl;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.context.annotation.Component;
import com.spring.test.service.ProductService;
import com.spring.test.service.UserService;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    UserService userService;

    @Override
    public void productTest() {
        System.out.println("产品测试");
    }

}
