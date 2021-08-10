package com.spring.aop.framework;

import com.spring.aspectj.lang.support.PointcutDefinition;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/4  16:04
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description： AOP注册
 */
public abstract class AbstractAopRegistry implements AopRegistry{
    // 切面名称集合
    private final List<String> aspectNameList = new ArrayList<>(16);

    // 切入点集合
    private final Map<String, List<PointcutDefinition>> pointcutDefinitionMap = new ConcurrentHashMap<>(16);

    // 连接点集合
    private final Map<String, List<String>> joinPointDefinitionMap = new ConcurrentHashMap<>(24);


    /**
     * 扫描连接点
     * @param factory
     * @param executionE
     * @param pointcutDefinition
     */
    @Override
    public abstract void scanJoinPoint(ConfigurableListableBeanFactory factory, String executionE, PointcutDefinition pointcutDefinition, ThreadPoolExecutor threadPool);

    /**
     * 取得joinPointDefinitionMap的迭代器
     * @return
     */
    @Override
    public Iterator<String> getJoinPointDefinitionMapIterator() {
        return this.joinPointDefinitionMap.keySet().iterator();
    }

    /**
     * 根据key取得joinPointDefinitionMap的value
     * @param className
     * @return
     */
    @Override
    public List<String> getJoinPointDefinition(String className) {
        return this.joinPointDefinitionMap.get(className);
    }

    /**
     * 注册连接点
     * @param className
     * @param joinPoints
     */
    @Override
    public void registerJoinPointDefinitionMap(String className, List<String> joinPoints) {
        if (joinPoints == null) {
            throw new IllegalStateException("连接点集合不能为空！");
        }
        this.joinPointDefinitionMap.put(className, joinPoints);
    }

    /**
     * 获取切面名称迭代器
     * @return
     */
    @Override
    public Iterator<String> getAspectNameListIterator() {
        return this.aspectNameList.iterator();
    }

    /**
     * 获取切入点迭代器
     * @return
     */
    @Override
    public Iterator<String> getPointcutDefinitionMapIterator() {
        return this.pointcutDefinitionMap.keySet().iterator();
    }

    /**
     * 获取切面中所有切入点
     * @param aspectClassName
     * @return
     */
    @Override
    public List<PointcutDefinition> getPointcutDefinition(String aspectClassName) {
        return this.pointcutDefinitionMap.get(aspectClassName);
    }

    /**
     * 注册切面名称
     * @param aspectClassName
     */
    @Override
    public void registerAspectNameList(String aspectClassName) {
        this.aspectNameList.add(aspectClassName);
    }

    /**
     * 注册切入点定义
     * @param aspectClassName
     * @param pointcuts
     */
    @Override
    public void registerPointcutDefinitionMap(String aspectClassName, List<PointcutDefinition> pointcuts) {
        this.pointcutDefinitionMap.put(aspectClassName, pointcuts);
    }
}
