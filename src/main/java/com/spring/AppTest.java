package com.spring;

import com.spring.context.ApplicationContext;
import com.spring.context.annotation.AnnotationConfigApplicationContext;
import com.spring.test.config.BeanScan;
import com.spring.test.config.BeanScan2;
import com.spring.test.service.ProductService;
import com.spring.test.service.UserService;

import java.lang.reflect.Proxy;

/**
 * Hello world!
 *
 */
public class AppTest {
    public static void main( String[] args ) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanScan.class, BeanScan2.class);

        System.out.println("");
        ProductService productService = (ProductService) applicationContext.getBean("productServiceImpl");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");

        System.out.println(productService);
        System.out.println(userService);

        System.out.println(productService.getUserService() instanceof Proxy);
        System.out.println(userService.getProductService() instanceof Proxy);

        System.out.println("p中的u：" + productService.getUserService());
        System.out.println("u中的p：" + userService.getProductService());

    }
}
