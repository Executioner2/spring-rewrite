package com.spring.aop.framework.support;

import com.spring.aop.framework.AbstractAopRegistry;
import com.spring.aop.framework.AspectExecutionEUtil;
import com.spring.aspectj.lang.support.JoinPointDefinition;
import com.spring.aspectj.lang.support.PointcutDefinition;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/4  17:59
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class AopDefinitionRegistry extends AbstractAopRegistry {

    /**
     * 扫描连接点
     * @param factory
     * @param executionE
     * @param pointcutDefinition
     * @param threadPool
     */
    public void scanJoinPoint(ConfigurableListableBeanFactory factory, String executionE, PointcutDefinition pointcutDefinition, ThreadPoolExecutor threadPool) {
        // 取得executionE的正则表达式
        Pattern pattern = AspectExecutionEUtil.getExecutionERegex(executionE);
        // 设置到切入点定义中
        pointcutDefinition.setExecutionToPattern(pattern);

        // 开启多线程扫描匹配的连接点
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                doScanJoinPoint(factory, pointcutDefinition);
            }
        });

    }

    /**
     * 扫描连接点
     * @param factory
     * @param pointcutDefinition
     */
    private void doScanJoinPoint(ConfigurableListableBeanFactory factory, PointcutDefinition pointcutDefinition) {
        Pattern pattern = pointcutDefinition.getExecutionToPattern();
        Iterator<String> beanNamesIterator = factory.getBeanNamesIterator();
        while (beanNamesIterator.hasNext()) {
            String key = beanNamesIterator.next();
            BeanDefinition beanDefinition = factory.getBeanDefinition(key);
            Class<?> beanClass = beanDefinition.getBeanClass();
            Class<?>[] interfaces = beanClass.getInterfaces();

            // 扫描所有的接口，只有接口的方法才能被当作连接点
            for (Class<?> anInterface : interfaces) {
                Method[] methods = anInterface.getDeclaredMethods();
                for (Method method : methods) {
                    // 取出实现类中的接口实现方法
                    try {
                        Method implMethod = beanClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
                        Matcher matcher = pattern.matcher(implMethod.toString());

                        if (matcher.matches()) {
                            // 匹配成功，需要代理
                            if (!beanDefinition.isProxy()) {
                                beanDefinition.setProxy(true);
                            }
                            // 注册到连接点定义集合中，这里用接口方法名当作key，因为调用代理方法时是由接口调用的
                            this.registerJoinPointDefinitionMap(beanDefinition.getBeanClassName(), method.toString(),
                                    new JoinPointDefinition(pointcutDefinition.getAspectBeanName(),
                                            pointcutDefinition.getExecutionMethod()));

                        }

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
