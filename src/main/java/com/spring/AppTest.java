package com.spring;

import com.spring.context.ApplicationContext;
import com.spring.context.annotation.AnnotationConfigApplicationContext;
import com.spring.test.config.BeanScan;
import com.spring.test.config.BeanScan2;
import com.spring.test.module.Product;
import com.spring.test.service.impl.ProductServiceImpl;
import com.spring.test.service.impl.UserServiceImpl;

/**
 * Hello world!
 *
 */
public class AppTest {
    public static void main( String[] args ) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanScan.class, BeanScan2.class);

        System.out.println("");
        ProductServiceImpl productService = (ProductServiceImpl) applicationContext.getBean("productServiceImpl");
        UserServiceImpl userService = (UserServiceImpl) applicationContext.getBean("userServiceImpl");

        System.out.println(productService);
        System.out.println(userService);

    }
}
