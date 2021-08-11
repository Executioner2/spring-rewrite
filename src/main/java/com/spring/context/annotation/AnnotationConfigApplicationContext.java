package com.spring.context.annotation;

import com.spring.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.BeanDefinitionReaderUtils;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.context.support.GenericApplicationContext;

import java.lang.annotation.Annotation;

public class AnnotationConfigApplicationContext extends GenericApplicationContext {
    // 这两个变量暂时没用，在spring源码中有重要作用
    private final Object reader;
    private final Object scanner;


    public AnnotationConfigApplicationContext() {
        this.reader = new Object();
        this.scanner = new Object();
    }

    public AnnotationConfigApplicationContext(Class... componentClasses) {
        this();
        register(componentClasses);
        refresh();
    }

    /**
     * 工具bean注册，这里实现与官方不一样
     * 直接将工具bean装进bean定义中去
     * XXX 需要后续改进
     * @param componentClasses
     */
    private void register(Class[] componentClasses) {
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) getBeanFactory();
        for (Class componentClass : componentClasses) {
            if (componentClass.isAnnotationPresent(ComponentScan.class)) {
                // 如果是包扫描则先把该方法注册到bean中
                BeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition();
                beanDefinition.setBeanClass(componentClass);
                // 取得beanName
                String beanName = BeanDefinitionReaderUtils.generateBeanName(beanDefinition);
                beanDefinition.setBeanName(beanName); // 设置beanName
                // 注册到beanDefinitionMap中去
                beanFactory.registerBeanDefinition(beanName, beanDefinition);
                // 加入到配置类
                ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) beanFactory;
                factory.registerConfigurationClassMap(beanDefinition.getBeanClassName(), beanDefinition.getBeanClass());
            }
        }
    }
}
