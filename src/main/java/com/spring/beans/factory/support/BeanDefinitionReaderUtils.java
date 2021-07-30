package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  22:01
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： beanDefinition读取工具类
 */
public abstract class BeanDefinitionReaderUtils {


    /**
     * 根据类名获取beanName
     * @param beanDefinition
     * @return
     */
    public static String generateBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        String name = beanClass.getName();
        return firstChartToLowerCase(name);
    }

    /**
     * 根据类文件名获取beanName
     * @param fileName
     * @return beanNameArray[]{首字母未转小写的类名，首字母转了小写的类名}
     */
    public static String[] generateBeanName(String fileName) {
        if (fileName.endsWith(".class")) {
            String className = fileName.replaceFirst("\\.class", "");
            return new String[]{className, firstChartToLowerCase(className)};
        }
        return null;
    }

    /**
     * 首字母小写
     * @param beanName
     * @return
     */
    public static String firstChartToLowerCase(String beanName) {
        try {
            String c = String.valueOf(beanName.charAt(0));
            beanName = beanName.replaceFirst(c, c.toLowerCase());
        } catch (NullPointerException e) {
            throw e;
        }

        return beanName;
    }
}
