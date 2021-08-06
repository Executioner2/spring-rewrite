package com.spring.context.annotation;

import com.spring.aop.framework.support.AopDefinitionRegistry;
import com.spring.aspectj.lang.annotation.Around;
import com.spring.aspectj.lang.annotation.Aspect;
import com.spring.aspectj.lang.support.PointcutDefinition;
import com.spring.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.aop.framework.AspectExecutionEUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/3  19:00
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： AOP动态代理注册
 */
public class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar{

    /**
     * 在这里标记需要代理的类
     * 执行到这个方法代表开启了aop
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(BeanDefinitionRegistry registry) {

        if (!(registry instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalStateException("registry类型错误！");
        }

        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) registry;

        // 注册aop工厂到单例池中
        AnnotatedGenericBeanDefinition definition = new AnnotatedGenericBeanDefinition();
        definition.setBeanClass(AopDefinitionRegistry.class);
        // 内部单例对象，beanName用全限定名
        registry.registerBeanDefinition(AopDefinitionRegistry.class, definition);
        // AOP注册bean
        AopDefinitionRegistry aopRegistry = (AopDefinitionRegistry) factory.getBean(AopDefinitionRegistry.class);

        Iterator<String> iterator = factory.getBeanNamesIterator();

        // 进行AOP扫描
        while (iterator.hasNext()) {
            String key = iterator.next();
            BeanDefinition beanDefinition = factory.getBeanDefinition(key);
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (!beanClass.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            // 注册切面
            aopRegistry.registerAspectNameList(beanClass.toString());

            // 扫面切入点
            Method[] methods = beanClass.getDeclaredMethods();
            List<PointcutDefinition> pointcutDefinitions = new ArrayList<>();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Around.class)) {
                    if (Modifier.isFinal(method.getModifiers())) { // 是不是final修饰的，如果是则跳过
                        continue;
                    }

                    Around around = method.getDeclaredAnnotation(Around.class);
                    pointcutDefinitions.add(new PointcutDefinition(around.value(), method, beanClass));
                    String executionE = around.value();

                    if (executionE == null) {
                        throw new IllegalStateException("execution表达式不能为空！");
                    }

                    // 判断是execution表达式还是方法
                    if (executionE.startsWith("execution(") && executionE.endsWith(")")) {
                        executionE = executionE.substring(0, executionE.lastIndexOf(")"))
                                .replaceFirst("execution\\(", "");

                        // 判断execution表达式的格式是否正确
                        if (!AspectExecutionEUtil.isLegalExecution(executionE)) {
                            throw new IllegalStateException("非法的execution表达式：" + executionE);
                        }

                        // 进行连接点扫描
                        aopRegistry.scanJoinPoint(factory, executionE);


                    } else {
                        // TODO 是方法，扫描方法上的execution表达式
                    }

                }
            }

            // 注册一个切面中的所有切入点
            aopRegistry.registerPointcutDefinitionMap(beanClass.toString(), pointcutDefinitions);
        }

    }
}
