package com.spring.context;

import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext{

    // 完成IOC
    void refresh();

    ConfigurableListableBeanFactory getBeanFactory();
}
