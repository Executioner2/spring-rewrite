package com.spring.context;

public interface ApplicationContext {

    // 根据beanName获取bean
    Object getBean(String beanName);
}
