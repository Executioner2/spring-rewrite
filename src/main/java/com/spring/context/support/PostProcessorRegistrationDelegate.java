package com.spring.context.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.config.ConfigurableBeanFactory;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.BeanDefinitionReaderUtils;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.beans.factory.support.RootBeanDefinition;
import com.spring.context.annotation.Component;
import com.spring.context.annotation.ComponentScan;
import com.spring.context.annotation.Lazy;
import com.spring.context.annotation.Scope;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/7/29  23:35
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：后置注册，用于包扫描，bean后处理器注册
 */
final class PostProcessorRegistrationDelegate {
    private PostProcessorRegistrationDelegate() {}

    /**
     * 调用bean工厂后置处理
     * 也在此方法中进行包扫描
     * @param beanFactory
     * @param beanFactoryPostProcessors
     */
    public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {


        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

            // XXX spring官方是用bean注册后置处理器来进行包扫描注册的
            // 这里直接进行包扫描注册，后续在进行改进
            Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
            while (beanNamesIterator.hasNext()) {
                String beanName = beanNamesIterator.next();
                BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
                Class<?> beanClass = beanDefinition.getBeanClass();

                // 判断beanDefinition上是否有ComponentScan注解
                if (beanClass.isAnnotationPresent(ComponentScan.class)) {
                    ComponentScan componentScan = beanClass.getDeclaredAnnotation(ComponentScan.class);

                    // 获得此类的类加载器
                    ClassLoader beanClassLoader = beanClass.getClassLoader();

                    // 对注解中的value进行遍历
                    for (String s : componentScan.value()) {
                        // 得到一个包名就进行包扫描
                        // 取得资源路径
                        URL resource = beanClassLoader.getResource(s.replace(".", "/"));
                        File file = new File(resource.getPath());
                        if (file.exists()) {
                            packageScan(registry, s, file);
                        }
                    }
                }
            }
        }
    }

    /**
     * 进行包扫描并注册beanDefinition
     * @param beanFactory
     * @param file
     */
    private static void packageScan(BeanDefinitionRegistry beanFactory, String packageName, File file) {
        if (file.isFile()) {
            // beanNameArray[]{首字母未转小写的类名，首字母转了小写的类名}
            String[] beanNameArray = BeanDefinitionReaderUtils.generateBeanName(file.getName());
            if (beanNameArray != null && beanNameArray.length == 2) {
                try {
                    String clazzStr = packageName + "." + beanNameArray[0];
                    Class<?> aClass = Class.forName(clazzStr);

                    // 是否有Component注解，有才加入到beanDefinition中
                    if (!(aClass.isAnnotationPresent(Component.class))) {
                        return;
                    } else {
                        // 判断component的value是否有参数，有就用value的值做beanName
                        Component component = aClass.getDeclaredAnnotation(Component.class);
                        String value = component.value();

                        // 这里需要严格的判断顺序，所以分开取反
                        if (!(value.isBlank()) && !(beanFactory.isBeanNameInUse(value))) {
                            beanNameArray[1] = value;
                        }
                    }

                    BeanDefinition beanDefinition = new RootBeanDefinition(aClass);
                    beanDefinition.setBeanClass(aClass);

                    // 实例化方式
                    if (aClass.isAnnotationPresent(Scope.class)) {
                        Scope scope = aClass.getDeclaredAnnotation(Scope.class);
                        String value = scope.value();

                        if (value == null || !("".equals(value.trim()))
                                || !(ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(value))
                                || !(ConfigurableBeanFactory.SCOPE_SINGLETON.equals(value))) {

                            throw new IllegalStateException("scope参数错误！");
                        }

                        if (ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(value)) {
                            beanDefinition.setScope(value);
                        }
                    }

                    // 是否懒加载
                    if (aClass.isAnnotationPresent(Lazy.class)) {
                        Lazy lazy = aClass.getDeclaredAnnotation(Lazy.class);
                        boolean value = lazy.value();
                        if (value) {
                            beanDefinition.setLazyInit(value);
                        }
                    }

                    // 依赖的其它bean

                    // 注册beanDefinition
                    beanFactory.registerBeanDefinition(beanNameArray[1], beanDefinition);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                packageScan(beanFactory, packageName, f);
            }
        }
    }


    /**
     * 注册bean后置处理器
     * @param beanFactory
     * @param applicationContext
     */
    public static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {

    }
}
