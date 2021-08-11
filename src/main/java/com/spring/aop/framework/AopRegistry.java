package com.spring.aop.framework;

import com.spring.aspectj.lang.support.JoinPointDefinition;
import com.spring.aspectj.lang.support.PointcutDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/4  16:08
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public interface AopRegistry {
    // 获取aspect名称的迭代器
    Iterator<String> getAspectNameListIterator();

    // 获取切入点集合的迭代器
    Iterator<String> getPointcutDefinitionMapIterator();

    // 获取连接点map的迭代器
    Iterator<String> getJoinPointDefinitionMapIterator();

    // 获取切入点的value
    List<PointcutDefinition> getPointcutDefinition(String aspectClassName);

    // 获取连接点map的value
    Map<String, List<JoinPointDefinition>> getJoinPointDefinition(String className);

    // 注册aspect名称
    void registerAspectNameList(String aspectClassName);

    // 注册切入点
    void registerPointcutDefinitionMap(String aspectClassName, List<PointcutDefinition> pointcuts);

    // 注册连接点
    void registerJoinPointDefinitionMap(String className, Map<String, List<JoinPointDefinition>> joinPoints);

    // 注册连接点
    void registerJoinPointDefinitionMap(String className, String methodName, JoinPointDefinition joinPoint);

    // 扫描连接点
    void scanJoinPoint(ConfigurableListableBeanFactory factory, String executionE, PointcutDefinition pointcutDefinition, ThreadPoolExecutor threadPool);

}
