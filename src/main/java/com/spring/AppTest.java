package com.spring;

import com.spring.context.ApplicationContext;
import com.spring.context.annotation.AnnotationConfigApplicationContext;
import com.spring.test.config.BeanScan;
import com.spring.test.config.BeanScan2;
import com.spring.test.service.UserService;

/**
 * Hello world!
 *
 */
public class AppTest {
    public static void main( String[] args ) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanScan.class, BeanScan2.class);

//        ProductService productService = (ProductService) applicationContext.getBean("productServiceImpl");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");

//        System.out.println(productService);
        String result = userService.userTest("张三");
        System.out.println("==============最终结果===============");
        System.out.println(result);

    }
}
