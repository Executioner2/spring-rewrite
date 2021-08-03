package com.spring.test.config;

import com.spring.context.annotation.ComponentScan;
import com.spring.context.annotation.Configuration;
import com.spring.context.annotation.EnableAspectJAutoProxy;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/28  20:39
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
@ComponentScan({"com.spring.test.module", "com.spring.test.service"})
@Configuration
@EnableAspectJAutoProxy
public class BeanScan {
}
