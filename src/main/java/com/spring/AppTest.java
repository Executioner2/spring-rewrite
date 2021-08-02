package com.spring;

import com.spring.context.ApplicationContext;
import com.spring.context.annotation.AnnotationConfigApplicationContext;
import com.spring.test.config.BeanScan;
import com.spring.test.config.BeanScan2;

/**
 * Hello world!
 *
 */
public class AppTest
{
    public static void main( String[] args ) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanScan.class, BeanScan2.class);

        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
}
