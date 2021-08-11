package com.spring.test.service.impl;

import com.spring.context.annotation.Component;
import com.spring.test.service.ProductService;

import java.math.BigDecimal;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  22:14
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@Component
public class ProductServiceImpl implements ProductService {
    @Override
    public void setPrice(String id, BigDecimal price) {

    }

    @Override
    public BigDecimal getPrice(String id) {
        return null;
    }

    @Override
    public void removeProduct(String id) {

    }

    public void test() {

    }
}
