package com.spring.service;

import java.math.BigDecimal;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/11  18:35
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface ProductService {
    void setPrice(String id, BigDecimal price);

    String getName();
}
