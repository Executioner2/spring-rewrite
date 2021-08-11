package com.spring.context.support;

import com.spring.aspectj.lang.annotation.Aspect;
import com.spring.beans.factory.config.*;
import com.spring.beans.factory.support.BeanDefinitionReaderUtils;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.context.annotation.*;
import com.spring.util.ReflectionUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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

        // 包扫描，bean定义注册
        if (beanFactory instanceof DefaultListableBeanFactory) {

            // 执行bean工厂后置处理方法
            for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
                beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
            }

            DefaultListableBeanFactory registry = (DefaultListableBeanFactory) beanFactory;

            List<Object> beanDefinitionScanList = new ArrayList();

            // XXX spring官方是用bean注册后置处理器来进行包扫描注册的
            // XXX 这里直接进行包扫描注册，后续再进行改进
            Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
            while (beanNamesIterator.hasNext()) {
                String beanName = beanNamesIterator.next();
                BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
                Class<?> beanClass = beanDefinition.getBeanClass();

                // 判断bean定义上是否有ComponentScan注解
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
                            beanDefinitionScanList.addAll((Collection)packageScan(s, file));
                        }
                    }

                }
            }

            // 开始注册bean定义
            beanDefinitionScanList.stream().forEach(item -> {                ;
                BeanDefinition beanDefinition = (BeanDefinition) item;
                String beanName = beanDefinition.getBeanName();

                if (beanName.isBlank()) {
                    throw new IllegalStateException("beanName（" + beanName + "）不合法，" + beanDefinition.getBeanClassName());
                }

                if (registry.isBeanNameInUse(beanName)) {
                    throw new IllegalStateException("beanName（" + beanName + "）已存在" + beanDefinition.getBeanClassName());
                }

                // 注册bean定义
                registry.registerBeanDefinition(beanName, beanDefinition);

                // 如果是配置类则加入到配置类集合
                Class<?> beanClass = beanDefinition.getBeanClass();
                if (beanClass.isAssignableFrom(Configuration.class)) {
                    registry.registerConfigurationClassMap(beanDefinition.getBeanClassName(), beanDefinition.getBeanClass());
                }
            });

            // 把所有beanDefinitionMap中的bean定义注册到mergedBeanDefinitions中
            beanNamesIterator = beanFactory.getBeanNamesIterator();
            DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) beanFactory;
            while (beanNamesIterator.hasNext()) {
                String key = beanNamesIterator.next();
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(key);
                // 这里深度复制把ScannedGenericBeanDefinition转换为RootBeanDefinition就很cd
                dbf.getMergedBeanDefinition(key, beanDefinition);
            }

            // 配置类方法调用
            Iterator<String> iterator = registry.getConfigurationClassMapKeySetIterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Class<?> configurationClass = registry.getConfigurationClass(key);
                // 是配置类
                if (configurationClass.isAnnotationPresent(Configuration.class)) {
                    // 做配置类注册
                    // 获得所有的注解，注意这里获得的是代理注解类，需要用annotationType()获取原始注解
                    Annotation[] declaredAnnotations = configurationClass.getDeclaredAnnotations();
                    // 配置类的注解中应该带有Import注解
                    for (Annotation annotation : declaredAnnotations) {
                        if (!annotation.annotationType().isAnnotationPresent(Import.class)) {
                            continue;
                        }

                        Import anImport = annotation.annotationType().getDeclaredAnnotation(Import.class);
                        // 导入的类
                        Class<?>[] importObjectClass = anImport.value();
                        for (Class<?> objectClass : importObjectClass) {
                            try {
                                // 是否实现了ImportBeanDefinitionRegistrar类
                                if (objectClass.isAssignableFrom(ImportBeanDefinitionRegistrar.class)) {
                                    continue;
                                }

                                Constructor<?> constructor = objectClass.getDeclaredConstructor();
                                ReflectionUtils.makeAccessible(constructor);
                                ImportBeanDefinitionRegistrar o = (ImportBeanDefinitionRegistrar) constructor.newInstance();
                                // 在registerBeanDefinitions中对bean定义进行修改
                                o.registerBeanDefinitions(registry);
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }


    /**
     * 进行包扫描并注册bean定义
     * @param packageName
     * @param file
     */
    private static Object packageScan(String packageName, File file) {
        if (file.isFile()) {
            // beanNameArray[]{首字母未转小写的类名，首字母转了小写的类名}
            String[] beanNameArray = BeanDefinitionReaderUtils.generateBeanName(file);
            if (beanNameArray != null && beanNameArray.length == 2) {
                try {
                    String clazzStr = packageName + "." + beanNameArray[0];
                    Class<?> aClass = Class.forName(clazzStr);

                    // 是否有Component注解，有才加入到beanDefinition中
                    if (!(aClass.isAnnotationPresent(Component.class))) {
                        return null;
                    } else {
                        // 判断component的value是否有参数，有就用value的值做beanName
                        Component component = aClass.getDeclaredAnnotation(Component.class);
                        String value = component.value();
                        if (!("".equals(value))) {
                            beanNameArray[1] = value;
                        }
                    }

                    BeanDefinition beanDefinition = new ScannedGenericBeanDefinition(aClass);
                    beanDefinition.setBeanClass(aClass);
                    beanDefinition.setBeanName(beanNameArray[1]);

                    // 实例化方式
                    if (aClass.isAnnotationPresent(Scope.class)) {
                        Scope scope = aClass.getDeclaredAnnotation(Scope.class);
                        String value = scope.value();

                        if (value == null || !("".equals(value.trim()))
                                || !(ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(value))
                                || !(ConfigurableBeanFactory.SCOPE_SINGLETON.equals(value))) {

                            throw new IllegalStateException("scope参数错误！");
                        }

                        beanDefinition.setScope(value);
                    }

                    // 是否懒加载
                    if (aClass.isAnnotationPresent(Lazy.class)) {
                        Lazy lazy = aClass.getDeclaredAnnotation(Lazy.class);
                        boolean value = lazy.value();
                        beanDefinition.setLazyInit(value);
                    }

                    return beanDefinition;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        // 不是目录返回null
        } else if (!(file.isDirectory())){
            return null;
        }

        // 到这里file一定是个目录
        List list = new ArrayList();
        for (File f : file.listFiles()) {

            if (f.isDirectory()) {
                list.addAll((Collection) packageScan(packageName + "." + f.getName(), f));
                continue;
            }

            // 返回的第一个参数为beanName，第二个参数为beanDefinition
            BeanDefinition result = (BeanDefinition) packageScan(packageName, f);
            if (result != null) {
                list.add(result);
            }
        }
        return list;
    }


    /**
     * 注册bean后置处理器
     * @param beanFactory
     * @param applicationContext
     */
    public static void registerBeanPostProcessors(
            ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {

        // 1、先做beanPostProcessor实现类扫描
        Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
        // 暂存生成的beanPostProcessor对象
        List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) beanFactory;
        while (beanNamesIterator.hasNext()) {
            String beanName = beanNamesIterator.next();
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            Class<?> beanClass = beanDefinition.getBeanClass();

            // 判断是否实现了BeanPostProcessor接口
            if (BeanPostProcessor.class.isAssignableFrom(beanClass)) {
                // 2、生成beanPostProcessor实现类的单例bean
                beanPostProcessorList.add((BeanPostProcessor) dbf.getBean(beanName));
            }
        }

        // 3、将bean添加到beanPostProcessors集合中去
        // （此集合是进行了优先级排序的只装后置bean集合，与装单例bean的一级缓存是不同的）
        // 上面没有直接把生成的BeanPostProcessor对象放beanPostProcessors集合是为了
        // 把所有的BeanPostProcessor实现类都创建了再放入，这样就不会再创建第二个的时候
        // 执行到了第一个创建好了的BeanPostProcessor实现类的后置处理方法，因为这里只是
        // 注册，不应该执行用户创建的BeanPostProcessor实现类方法
        beanPostProcessorList.stream().forEach(item -> {
            beanFactory.addBeanPostProcessor(item);
        });

        // 这里暂时省略优先级排序

    }
}
