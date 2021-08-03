package com.spring;

import com.spring.context.ApplicationContext;
import com.spring.context.annotation.AnnotationConfigApplicationContext;
import com.spring.test.config.BeanScan;
import com.spring.test.config.BeanScan2;
import com.spring.test.module.aa.A;
import com.spring.test.module.aa.bb.B;

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
        A a = (A) applicationContext.getBean("a");
        B b = (B) applicationContext.getBean("b");

        System.out.println("a：" + a);
        System.out.println("b："+ b);

        System.out.println("==============");
        System.out.println("a中b：" + a.b);
        System.out.println("b中a：" + b.a);


    }
}
