package com.spring.beans.factory;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/2  18:51
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface InitializingBean {

    // 初始化方法
    void afterPropertiesSet();
}
