package com.spring.module;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.test.module.User;

import java.util.Date;

/**
 * @Program: spring-rewrite
 * @Author: 2Executioner
 * @Time: 2021/8/2  17:06
 * @Copyright：Copyright(c) 1205878539@qq.com
 * @Version: 1.0.0
 * @Description：
 */
public class Category {
    @Autowired
    private String id;

    private String name = "电视机";

    private Date createDate;

    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 用来测试的方法
     * @param name
     * @param createDate
     * @return
     */
    private static String search(String name, int createDate) throws Exception, IllegalAccessError{

        return "";
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
