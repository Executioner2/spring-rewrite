package com.spring.test.service;

import java.math.BigDecimal;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/30  22:14
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface ProductService {

    void setPrice(String id, BigDecimal price);

    BigDecimal getPrice(String id);

    void removeProduct(String id);

    UserService getUserService();
}
