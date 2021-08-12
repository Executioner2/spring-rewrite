package com.spring.test.service.impl;


import com.spring.beans.factory.annotation.Autowired;
import com.spring.context.annotation.Component;
import com.spring.test.service.ProductService;
import com.spring.test.service.UserService;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    public ProductService productService;

    @Override
    public String userTest(String name) {
        System.out.println("==========result=========");
        System.out.println("用户测试：" + name);
        return name;
    }
}
